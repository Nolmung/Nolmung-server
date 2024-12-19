package ureca.nolmung.implementation.recommend;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.springframework.stereotype.Component;
import ureca.nolmung.jpa.bookmark.Bookmark;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class CsvGeneratorManager {
    public static Path generateCsv(List<Bookmark> bookmarks) {
        try {
            // 임시 파일 생성
            Path tempFile = Files.createTempFile("bookmarks-", ".csv");

            try (BufferedWriter writer = Files.newBufferedWriter(tempFile);
                 CSVPrinter csvPrinter = new CSVPrinter(writer, CSVFormat.DEFAULT.withHeader("USER_ID", "ITEM_ID", "TIMESTAMP"))) {
                // TODO 이거 무조건 N+1
                for (Bookmark bookmark : bookmarks) {
                    csvPrinter.printRecord(bookmark.getUser().getId(), bookmark.getPlace().getId(), bookmark.getCreatedAt());
                }
            }

            return tempFile; // 생성된 파일 반환
        } catch (IOException e) {
            log.warn("CSV 파일 생성 실패: {}", e.getMessage(), e);
            return null;
        }
    }
}
