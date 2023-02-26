package Guru.QA;

import com.codeborne.pdftest.PDF;
import com.codeborne.xlstest.XLS;
import com.opencsv.CSVReader;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import static org.assertj.core.api.Assertions.assertThat;

public class ZipDZ {

  ClassLoader cl = ZipDZ.class.getClassLoader();

  @DisplayName("Парсинг из zip архива")
  @Test
  void zipParseTest() throws Exception {
    try (
            InputStream resource = cl.getResourceAsStream("test/testDZ.zip");
            ZipInputStream zis = resource == null ?
                    new ZipInputStream(null)
                    :
                    new ZipInputStream(resource))
            {
      ZipEntry entry = zis.getNextEntry();
      while (entry != null) {
        String name = entry.getName();

        if (name.contains("testxl88xs.xls")) {
          XLS content = new XLS(zis);
          assertThat(content.excel.getSheetAt(0).getRow(2).getCell(2).getStringCellValue()).contains("1");
        } else if (name.contains("igor.pdf")) {
          PDF content = new PDF(zis);
          assertThat(content.text).contains("Игорь Григорьев");
        } else if (name.contains("testcsv.csv")) {
          CSVReader reader = new CSVReader(new InputStreamReader(zis));
          List<String[]> content = reader.readAll();
          assertThat(content.get(1)[1]).contains("79151111111");
        }
        entry = zis.getNextEntry();
      }
    }
  }
}
