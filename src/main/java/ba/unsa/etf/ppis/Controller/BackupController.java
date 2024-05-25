package ba.unsa.etf.ppis.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

@RestController
public class BackupController {

    @PostMapping("/backup")
    public String createBackup() throws IOException, InterruptedException {
        String url = "mysql://avnadmin:AVNS_kzmQhPb1rZk9967wy1p@ppis-project-ppis.e.aivencloud.com:13688/defaultdb?ssl-mode=REQUIRED";
        String command = "C:\\Program Files\\MySQL\\MySQL Server 8.4\\bin\\mysqldump --host=ppis-project-ppis.e.aivencloud.com --port=13688 --user=avnadmin --password=AVNS_kzmQhPb1rZk9967wy1p --ssl-mode=REQUIRED defaultdb > C:\\Users\\naimm\\Desktop\\backup.sql";
        ProcessBuilder builder = new ProcessBuilder("cmd.exe", "/c", "runas /user:Administrator \"" + command + "\"");
        builder.redirectErrorStream(true);
        Process process = builder.start();
        process.waitFor();

        BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
        StringBuilder output = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            output.append(line).append("\n");
        }

        int exitCode = process.exitValue();
        if (exitCode == 0) {
            return "Backup successful";
        } else {
            return "Backup failed";
        }
    }
}
