package com.example.cookie;

import org.cookie.Main;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MainTests {

    private final PrintStream originalOut = System.out;
    private final PrintStream originalErr = System.err;

    private ByteArrayOutputStream outputStream;
    private ByteArrayOutputStream errorStream;

    @BeforeEach
    public void setUp() {
        outputStream = new ByteArrayOutputStream();
        errorStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));
        System.setErr(new PrintStream(errorStream));
    }

    @AfterEach
    public void tearDown() {
        System.setOut(originalOut);
        System.setErr(originalErr);
    }

    @Test
    public void testMain_withValidArguments_singleMaxCookie() throws Exception {
        String[] args = {"-f", "src/test/resources/cookie_log_test.csv", "-d", "2018-12-09"};
        Main.main(args);

        String output = outputStream.toString().trim();
        assertEquals("AtY0laUfhglK3lC7", output);
    }

    @Test
    public void testMain_withValidArguments_multipleMaxCookies() throws Exception {

        String[] args = {"-f", "src/test/resources/cookie_log_test.csv", "-d", "2018-12-08"};
        Main.main(args);

        String output = outputStream.toString().trim();
        assertEquals("SAZuXPGUrfbcn5UA\n" +
                        "4sMM2LxV07bPJzwf\n" +
                        "fbcn5UAVanZf6UtG", output);
    }

    @Test
    public void testMain_withValidArguments_with_one_invalid_date() throws Exception {

        String[] args = {"-f", "src/test/resources/cookie_log_test_invalid_date.csv", "-d", "2018-12-08"};
        Main.main(args);

        String output = outputStream.toString().trim();
        assertEquals("Warning: Skipped some entries due to the invalid Date format\n" +
                "4sMM2LxV07bPJzwf\n" +
                "fbcn5UAVanZf6UtG", output);
    }

    @Test
    public void testMain_withInvalidArguments_missingDate() {
        String[] args = {"-f", "src/test/resources/cookie_log_test.csv"};
        Main.main(args);

        // Verify the error output
        String output = outputStream.toString().trim();
        assertEquals("Usage: java -jar most-active-cookie.jar -f <filename> -d <date>", output);
    }

    @Test
    public void testMain_withInvalidArguments_invalidFilePath() {
        String[] args = {"-f", "non_existent_file.csv", "-d", "2018-12-09"};
        Main.main(args);

        // Verify the error output
        String output = errorStream.toString().trim();
        assertEquals("Error reading the file: non_existent_file.csv (No such file or directory)", output);
    }

    @Test
    public void testMain_withInvalidArguments_without_f_parameter() {
        String[] args = {"-d", "non_existent_file.csv", "-d", "2018-12-09"};
        Main.main(args);

        String output = outputStream.toString().trim();
        assertEquals("Both -f (file) and -d (date) parameters are required.", output);
    }
}
