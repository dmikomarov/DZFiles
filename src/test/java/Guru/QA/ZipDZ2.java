package Guru.QA;

import com.codeborne.pdftest.PDF;
import com.codeborne.xlstest.XLS;
import com.opencsv.CSVReader;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class ZipDZ2 {
  ClassLoader cl = ZipDZ2.class.getClassLoader();

  @DisplayName("Парсинг из zip архива")
  @Test
  void readFileFromArchive() throws Exception {
    try (
            InputStream resource = cl.getResourceAsStream("test123.zip");
            ZipInputStream zis = new ZipInputStream(resource);
    ) {

      ZipEntry entry;
      while ((entry = zis.getNextEntry()) != null) {

        if (entry.getName().endsWith(".csv")) {
          CSVReader reader = new CSVReader(new InputStreamReader(zis));
          List<String[]> content = reader.readAll();
          assertThat(content.get(1)[1]).contains("Elmax");

        } else if (entry.getName().contains(".xlsx")) {

          XLS content = new XLS(zis);
          assertThat(
                  content.excel.getSheetAt(0).getRow(1).getCell(1).
                          getStringCellValue()).contains("Специалист");

        } else if (entry.getName().endsWith(".pdf")) {

          PDF content = new PDF(zis);
          assertThat(content.text)
                  .contains("Игорь Григорьев");
        }
      }
    }
  }
}
