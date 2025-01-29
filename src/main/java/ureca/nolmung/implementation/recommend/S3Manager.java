package ureca.nolmung.implementation.recommend;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.nio.file.Path;

@Slf4j
@Component
@RequiredArgsConstructor
public class S3Manager {
    private final S3Client s3Client;
    private final String bucketName;

    public void uploadFile(Path filePath, String key) {
        s3Client.putObject(PutObjectRequest.builder()
                .bucket(bucketName)
                .key(key)
                .build(), filePath);
        log.info("File uploaded to S3 with key: {}", key);
    }

}
