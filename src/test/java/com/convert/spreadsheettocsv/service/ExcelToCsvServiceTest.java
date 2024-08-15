package com.convert.spreadsheettocsv.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class ExcelToCsvServiceTest {
    @Autowired
    private ExcelToCsvService excelToCsvService;

    @TempDir
    Path tempDir; // Temporary directory for test files

    @Value("${inputPath}")
    public String inputPath;

    @Value("${outputPath}")
    public String outputPath;

    @Value("${fileNameTest}")
    public String fileNameTest;

    @Test
    public void testConvertXlsxToCsv() throws IOException {

        String pathAndFile = inputPath + fileNameTest;

        // Input file (Example.xlsx)
        File xlsxFile = new File(pathAndFile);

        // Output file (Example.csv)
        File csvFile = tempDir.resolve("Example.csv").toFile();

        // Convert the file
        excelToCsvService.convertXlsxToCsv(xlsxFile, csvFile);

        // Check if the file exists
        assertTrue(csvFile.exists());

        // Validate content of the CSV file
        List<String> csvLines = Files.readAllLines(csvFile.toPath());

        // Example assertions - these should match the content of Example.xlsx
        assertEquals(6, csvLines.size()); // Assuming there are 3 rows in Example.xlsx
        assertEquals("Code;Type;Description;E-mail", csvLines.get(0)); // headers
        assertEquals("00001;025;Teste 1;xxx1@gmail.com", csvLines.get(1)); // row data
        assertEquals("00002;026;Teste 2;xxx2@gmail.com", csvLines.get(2)); // row data
        assertEquals("00003;027;Teste 3;xxx3@gmail.com", csvLines.get(3)); // row data
        assertEquals("00004;028;Teste 4;xxx4@gmail.com", csvLines.get(4)); // row data
        assertEquals("00005;029;Teste 5;xxx5@gmail.com", csvLines.get(5)); // row data
        //assertEquals("Column1;Column2;Column3", csvLines.get(0)); // Assuming these are the headers
        //assertEquals("Data1;Data2;Data3", csvLines.get(1)); // Example row data
/*
Code	Type	Description	E-mail
00001	025	Teste 1	xxx1@gmail.com
00002	026	Teste 2	xxx2@gmail.com
00003	027	Teste 3	xxx3@gmail.com
00004	028	Teste 4	xxx4@gmail.com
00005	029	Teste 5	xxx5@gmail.com

 */
        // Additional assertions can be added here to match the content of Example.xlsx
    }
}
