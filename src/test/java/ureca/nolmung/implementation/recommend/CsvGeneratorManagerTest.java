package ureca.nolmung.implementation.recommend;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import ureca.nolmung.implementation.bookmark.BookmarkManager;
import ureca.nolmung.jpa.bookmark.Bookmark;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class CsvGeneratorManagerTest {

    @Autowired
    private BookmarkManager bookmarkManager;

    @Autowired
    private CsvGeneratorManager csvGeneratorManager;

    @Test
    @Transactional
    public void testGenerateCsv() throws Exception {
        List<Bookmark> bookmarks = bookmarkManager.findAll();
        assertTrue(!bookmarks.isEmpty(), "Bookmark 데이터가 있어야 합니다.");

        // 2. CSV 파일 생성 실행
        Path csvFile = csvGeneratorManager.generateCsv(bookmarks);

        // 3. CSV 파일 검증
        assertNotNull(csvFile, "CSV 파일은 null이 아니어야 합니다.");
        assertTrue(Files.exists(csvFile), "CSV 파일이 생성되어야 합니다.");

        // 4. 파일 내용 출력 (옵션)
        System.out.println("CSV 파일 경로: " + csvFile.toString());
        List<String> lines = Files.readAllLines(csvFile);
        assertTrue(lines.size() > 1, "CSV 파일에 헤더와 데이터가 포함되어야 합니다.");

        lines.forEach(System.out::println);
    }
}