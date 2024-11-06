package org.cookie.analyzer;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.*;

public class CookieLogAnalyzer {

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    /**
     *
     * @param filePath an absolute path having the contents
     * @param date a date to be searching for matched cookies
     * @return List of strings consisting cookies ids which are matched with the date given
     * @throws IOException If there are any errors raised while reading the file
     */
    public List<String> findMostActiveCookies(Path filePath, String date) throws IOException {
        LocalDate targetDate = LocalDate.parse(date, DATE_FORMATTER);
        // We are using linked hash map to return the response based on the timestamp
        Map<String, Integer> cookieCountMap = new LinkedHashMap<>();
        int maxCount = 0;
        boolean skippedRowsWithInvalidDate = false;
        try (BufferedReader reader = Files.newBufferedReader(filePath)) {
            // For skipping the header row of the CSV file
            String line;
            boolean headerRowParsed = false;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length != 2 || !headerRowParsed) {
                    // We should not break the parsing of next lines if one of the lines is not good.
                    headerRowParsed = true;
                    continue;
                }

                String cookie = parts[0];
                // We are only considering the Date part of the UTC timestamp
                LocalDate logDate;
                try {
                    logDate = LocalDate.parse(parts[1].substring(0, 10), DATE_FORMATTER);
                } catch (DateTimeParseException e) {
                    skippedRowsWithInvalidDate = true;
                    continue;
                }
                if (logDate.equals(targetDate)) {
                    int count = cookieCountMap.getOrDefault(cookie, 0) + 1;
                    cookieCountMap.put(cookie, count);
                    maxCount = Math.max(maxCount, count);
                }
            }
        }
        if(skippedRowsWithInvalidDate){
            System.out.println("Warning: Skipped some entries due to the invalid Date format");
        }

        List<String> mostActiveCookies = new ArrayList<>();
        for (Map.Entry<String, Integer> entry : cookieCountMap.entrySet()) {
            if (entry.getValue() == maxCount) {
                mostActiveCookies.add(entry.getKey());
            }
        }

        return mostActiveCookies;
    }
}

