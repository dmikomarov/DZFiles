package Guru.QA;

import Guru.QA.model.Glossary;
import com.codeborne.pdftest.PDF;
import com.codeborne.xlstest.XLS;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.opencsv.CSVReader;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.util.List;
import java.util.zip.*;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static org.assertj.core.api.Assertions.assertThat;


public class FilesParsingTest {

  ClassLoader cl = FilesParsingTest.class.getClassLoader();

  @Test
  void pdfParseTest() throws Exception {
    open("https://junit.org/junit5/docs/current/user-guide/");
    File downloadPDF = $("a[href='junit-user-guide-5.9.2.pdf']").download();
    PDF content = new PDF(downloadPDF);
    assertThat(content.author).contains("Sam Brannen");
  }

  @Test
  void xlsParseTest() throws Exception {
    try (InputStream resourceAsStream = cl.getResourceAsStream("test/Pairwise.xlsx")) {
      XLS content = new XLS(resourceAsStream);
      assertThat(content.excel.getSheetAt(0).getRow(1).getCell(1).getStringCellValue().contains("Dulce"));
    }


  }

  @Test
  void csvParserTest() throws Exception {
    try (
            InputStream resource = cl.getResourceAsStream("test/qa_guru.csv");
            CSVReader reader = new CSVReader(new InputStreamReader(resource))) {
      List<String[]> content = reader.readAll();
      assertThat(content.get(0)[1]).contains("lesson");
    }
  }

  @Test
  void zipParserTest() throws Exception {
    try (
            InputStream resource = cl.getResourceAsStream("test/testArchive.zip");
            ZipInputStream zis = new ZipInputStream(resource)) {
      ZipEntry entry;
      while ((entry = zis.getNextEntry()) != null) {
        assertThat(entry.getName()).contains("descriptions.xlsx");
      }
    }
  }


  @Test
  void jsonParseTest() throws Exception {
    Gson gson = new Gson();
    try (
            InputStream resource = cl.getResourceAsStream("test/glossary.json");
            InputStreamReader reader = new InputStreamReader(resource)) {

      JsonObject jsonObject = gson.fromJson(reader, JsonObject.class);
      assertThat(jsonObject.get("title").getAsString()).isEqualTo("example glossary");
      assertThat(jsonObject.get("GlossDiv").getAsJsonObject().getAsString()).isEqualTo("example glossary");
      assertThat(jsonObject.get("GlossDiv").getAsJsonObject().get("flag").getAsBoolean()).isTrue();

    }
  }

  @Test
  void jsonParseImprovedTest() throws Exception {
    Gson gson = new Gson();
    try (
            InputStream resource = cl.getResourceAsStream("test/glossary.json");
            InputStreamReader reader = new InputStreamReader(resource)) {

      Glossary jsonObject = gson.fromJson(reader, Glossary.class);
      assertThat(jsonObject.title).isEqualTo("example glossary");
      assertThat(jsonObject.glossDiv.title).isEqualTo("example glossary");
      assertThat(jsonObject.glossDiv.flag).isTrue();

    }
  }
}
