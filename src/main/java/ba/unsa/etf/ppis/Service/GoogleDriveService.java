package ba.unsa.etf.ppis.Service;

import com.google.api.client.http.ByteArrayContent;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.model.File;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.IOException;

@Service
public class GoogleDriveService {

    @Value("${google.api.key}")
    private String apiKey;

    private static final String APPLICATION_NAME = "Stomatology";
    private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();

    private Drive driveService;

    @PostConstruct
    public void init() {
        final NetHttpTransport HTTP_TRANSPORT = new NetHttpTransport();
        driveService = new Drive.Builder(HTTP_TRANSPORT, JSON_FACTORY, null)
                .setApplicationName(APPLICATION_NAME)
                .build();
    }

    public void uploadFile(ByteArrayInputStream fileContent, String fileName) throws IOException {
        File fileMetadata = new File();
        fileMetadata.setName(fileName);

        ByteArrayContent mediaContent = new ByteArrayContent("application/sql", fileContent.readAllBytes());

        Drive.Files.Create create = driveService.files().create(fileMetadata, mediaContent)
                .setKey(apiKey);
        create.execute();
    }
}

