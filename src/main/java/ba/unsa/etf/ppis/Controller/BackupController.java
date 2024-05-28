package ba.unsa.etf.ppis.Controller;
import ba.unsa.etf.ppis.Service.GoogleDriveService;
import ba.unsa.etf.ppis.dto.MessageDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.*;
import java.nio.file.*;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.Date;
import java.util.Optional;
import java.sql.ResultSet;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

@RestController
@RequestMapping("/backup")
public class BackupController {

    private static final String BACKUP_DIR = "C:\\Users\\naimm\\Desktop\\Backup-baze\\";
    private final GoogleDriveService googleDriveService;

    public BackupController(GoogleDriveService googleDriveService) {
        this.googleDriveService = googleDriveService;
    }

    @Scheduled(cron = "0 0 * * * ?")
    public void scheduledBackup() {
        try {
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            generateBackup(outputStream);
            saveBackupLocally(outputStream);
            System.out.println("Backup saved locally successfully.");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error during backup: " + e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity backupOnRequest() {
        try {
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            generateBackup(outputStream);
            saveBackupLocally(outputStream);
            MessageDto mess = new MessageDto("Backup saved locally successfully.");
            return ResponseEntity.ok(mess);
        } catch (Exception e) {
            e.printStackTrace();
            MessageDto mess1 = new MessageDto("Error during backup");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(mess1);
        }
    }

    @PostMapping("/restore")
    public String restoreDatabase() {
        try {
            Optional<Path> latestBackupFile = Files.list(Paths.get(BACKUP_DIR))
                    .filter(Files::isRegularFile)
                    .filter(path -> path.toString().endsWith(".sql"))
                    .max(Comparator.comparingLong(path -> path.toFile().lastModified()));

            if (!latestBackupFile.isPresent()) {
                return "No backup file found.";
            }

            try (Reader reader = Files.newBufferedReader(latestBackupFile.get())) {
                executeScript(reader);
                return "Database restored successfully from file: " + latestBackupFile.get().getFileName().toString();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "Error during database restore: " + e.getMessage();
        }
    }

    @GetMapping("/history")
    public List<String> getBackupHistory() {
        List<String> backupHistory = new ArrayList<>();
        File backupDir = new File(BACKUP_DIR);
        File[] files = backupDir.listFiles();

        if (files != null) {
            for (File file : files) {
                if (file.isFile()) {
                    String fileName = file.getName();
                    if (fileName.startsWith("backup_") && fileName.endsWith(".sql")) {
                        String timestamp = fileName.substring("backup_".length(), fileName.length() - ".sql".length());
                        backupHistory.add(timestamp);
                    }
                }
            }
        }
        return backupHistory;
    }

    private void saveBackupLocally(ByteArrayOutputStream outputStream) throws IOException {
        String timestamp = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
        String backupFilePath = BACKUP_DIR + "backup_" + timestamp + ".sql";

        try (FileOutputStream fileOutputStream = new FileOutputStream(backupFilePath)) {
            outputStream.writeTo(fileOutputStream);
        }
    }

    private void generateBackup(ByteArrayOutputStream outputStream) throws Exception {
        String url = "jdbc:mysql://avnadmin:AVNS_kzmQhPb1rZk9967wy1p@ppis-project-ppis.e.aivencloud.com:13688/defaultdb?ssl-mode=REQUIRED";
        String user = "defaultdb";
        String password = "AVNS_kzmQhPb1rZk9967wy1p";

        Map<String, String> createTableStatements = new LinkedHashMap<>();
        List<String> insertStatements = new ArrayList<>();
        Map<String, List<String>> tableDependencies = new HashMap<>();

        try (Connection con = DriverManager.getConnection(url, user, password);
             Statement stmt = con.createStatement()) {

            ResultSet tables = stmt.executeQuery("SHOW TABLES");
            while (tables.next()) {
                String tableName = tables.getString(1);

                // Collect create table statements
                try (Statement createStmt = con.createStatement();
                     ResultSet createTable = createStmt.executeQuery("SHOW CREATE TABLE " + tableName)) {
                    if (createTable.next()) {
                        String createStatement = createTable.getString(2) + ";";
                        createTableStatements.put(tableName, createStatement);

                        // Find foreign key dependencies
                        List<String> dependencies = new ArrayList<>();
                        try (Statement fkStmt = con.createStatement();
                             ResultSet fkResult = fkStmt.executeQuery("SELECT COLUMN_NAME, REFERENCED_TABLE_NAME " +
                                     "FROM INFORMATION_SCHEMA.KEY_COLUMN_USAGE " +
                                     "WHERE TABLE_NAME = '" + tableName + "' AND REFERENCED_TABLE_NAME IS NOT NULL")) {
                            while (fkResult.next()) {
                                dependencies.add(fkResult.getString("REFERENCED_TABLE_NAME"));
                            }
                        }
                        tableDependencies.put(tableName, dependencies);
                    }
                }

                // Collect insert statements
                try (Statement dataStmt = con.createStatement();
                     ResultSet data = dataStmt.executeQuery("SELECT * FROM " + tableName)) {
                    int columnCount = data.getMetaData().getColumnCount();
                    while (data.next()) {
                        StringBuilder row = new StringBuilder("INSERT INTO " + tableName + " VALUES (");
                        for (int i = 1; i <= columnCount; i++) {
                            row.append("'").append(data.getString(i)).append("'");
                            if (i < columnCount) row.append(", ");
                        }
                        row.append(");");
                        insertStatements.add(row.toString());
                    }
                }
            }
        }

        List<String> orderedTables = orderTablesByDependencies(tableDependencies);

        try (PrintWriter writer = new PrintWriter(outputStream)) {
            // Write all create table statements in the correct order
            for (String table : orderedTables) {
                writer.println(createTableStatements.get(table));
            }
            // Write all insert statements
            for (String insertStmt : insertStatements) {
                writer.println(insertStmt);
            }
        }
    }

    private List<String> orderTablesByDependencies(Map<String, List<String>> tableDependencies) {
        List<String> orderedTables = new ArrayList<>();
        Set<String> visitedTables = new HashSet<>();

        for (String table : tableDependencies.keySet()) {
            visitTable(table, tableDependencies, visitedTables, orderedTables);
        }

        return orderedTables;
    }

    @PostMapping("/drop-all-tables")
    public String dropAllTables() {
        try {
            List<String> tableNames = getAllTableNames();
            dropTables(tableNames);
            return "All tables dropped successfully.";
        } catch (Exception e) {
            e.printStackTrace();
            return "Error during dropping tables: " + e.getMessage();
        }
    }
    private void dropTables(List<String> tableNames) throws SQLException {
        String url = "jdbc:mysql://avnadmin:AVNS_kzmQhPb1rZk9967wy1p@ppis-project-ppis.e.aivencloud.com:13688/defaultdb?ssl-mode=REQUIRED";
        String user = "defaultdb";
        String password = "AVNS_kzmQhPb1rZk9967wy1p";

        try (Connection con = DriverManager.getConnection(url, user, password);
             Statement stmt = con.createStatement()) {
            stmt.execute("SET FOREIGN_KEY_CHECKS = 0;");
            for (String tableName : tableNames) {
                stmt.execute("DROP TABLE IF EXISTS " + tableName + ";");
            }
            stmt.execute("SET FOREIGN_KEY_CHECKS = 1;");
        }
    }

    private List<String> getAllTableNames() throws SQLException {
        String url = "jdbc:mysql://avnadmin:AVNS_kzmQhPb1rZk9967wy1p@ppis-project-ppis.e.aivencloud.com:13688/defaultdb?ssl-mode=REQUIRED";
        String user = "defaultdb";
        String password = "AVNS_kzmQhPb1rZk9967wy1p";

        List<String> tableNames = new ArrayList<>();
        try (Connection con = DriverManager.getConnection(url, user, password);
             Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery("SHOW TABLES")) {
            while (rs.next()) {
                tableNames.add(rs.getString(1));
            }
        }
        return tableNames;
    }
    private void visitTable(String table, Map<String, List<String>> tableDependencies, Set<String> visitedTables, List<String> orderedTables) {
        if (!visitedTables.contains(table)) {
            visitedTables.add(table);
            for (String dependency : tableDependencies.get(table)) {
                visitTable(dependency, tableDependencies, visitedTables, orderedTables);
            }
            orderedTables.add(table);
        }
    }

    private void executeScript(Reader reader) throws Exception {
        String url = "jdbc:mysql://avnadmin:AVNS_kzmQhPb1rZk9967wy1p@ppis-project-ppis.e.aivencloud.com:13688/defaultdb?ssl-mode=REQUIRED";
        String user = "defaultdb";
        String password = "AVNS_kzmQhPb1rZk9967wy1p";

        try (Connection con = DriverManager.getConnection(url, user, password);
             Statement stmt = con.createStatement();
             BufferedReader br = new BufferedReader(reader)) {

            StringBuilder sql = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) {
                sql.append(line);
                if (line.endsWith(";")) {
                    stmt.execute(sql.toString());
                    sql = new StringBuilder();
                }
            }
        }
    }
}
