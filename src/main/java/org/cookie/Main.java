package org.cookie;

import org.cookie.analyzer.CookieLogAnalyzer;

import java.io.IOException;
import java.nio.file.NoSuchFileException;
import java.nio.file.Paths;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        if (args.length < 4) {
            System.out.println("Usage: java -jar most-active-cookie.jar -f <filename> -d <date>");
            return;
        }

        String filePath = null;
        String date = null;

        for (int i = 0; i < args.length; i++) {
            if ("-f".equals(args[i])) {
                filePath = args[i + 1];
            } else if ("-d".equals(args[i])) {
                date = args[i + 1];
            }
        }

        if (filePath == null || date == null) {
            System.out.println("Both -f (file) and -d (date) parameters are required.");
            return;
        }

        try {
            CookieLogAnalyzer analyzer = new CookieLogAnalyzer();
            List<String> mostActiveCookies = analyzer.findMostActiveCookies(Paths.get(filePath), date);

            mostActiveCookies.forEach(System.out::println);
        } catch (NoSuchFileException e) {
            System.err.println("Error reading the file: "+ filePath + " (No such file or directory)" );
        } catch (IOException e) {
            System.err.println("Error while reading the contents of the file: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Unknown error occurred " + e.getMessage());
        }
    }
}
