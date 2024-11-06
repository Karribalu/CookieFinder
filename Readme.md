## Cookie Finder

Cookie Finder is a Java application that analyzes cookie log files to determine the most frequently used cookies on a specific date. This command-line application reads a CSV file and outputs the cookie(s) with the highest frequency for a specified date.

### Table of Contents
- [Project Structure](#project-structure)
- [Installation](#installation)
- [Usage](#usage)
- [Example](#example)
- [Testing](#testing)

### Project Structure

The project has the following structure:

```plaintext
.
├── src
│   ├── main
│   │   └── java
│   │       └── org/cookie
│   │           ├── Main.java              # Main entry point for the program
│   │           └── analyzer
│   │               └── CookieLogAnalyzer.java # Handles log analysis logic
│   └── test
│       └── java
│           └── analyzer
│               └── CookieLogAnalyzerTests.java # Test cases for CookieLogAnalyzer
│           └── MainTests.java             # Test cases for Main class
└── pom.xml                                # Maven configuration file
```

### Installation
1. Clone the Repository:
    ```shell
    git clone https://github.com/karribalu/cookie-finder.git
    ```
2. Navigate to Project Directory:

    ```shell
    cd cookie-finder
    ```
3. Build the Project with Maven:
   ```shell
    mvn clean install
    ```


### Usage
To run the application, use the following command:
```shell
java -jar target/CookieFinder-1.0-SNAPSHOT.jar -f <filename> -d <date>
```
- -f specifies the absolute path to the CSV file containing cookie logs.
- -d specifies the date for which the most active cookie(s) should be found in YYYY-MM-DD format.
  
### Example

Suppose you want to analyze the cookie_log.csv file for cookies on the date 2018-12-09. Run:
```shell
java -jar target/CookieFinder-1.0-SNAPSHOT.jar -f cookie_log.csv -d 2018-12-09
```
```shell
java -jar target/CookieFinder-1.0-SNAPSHOT.jar -f  /Users/balasubramanyam/Learning/system-design/cookie_log.csv -d 2018-12-09
```

#### Example output

```plaintext
AtY0laUfhglK3lC7
```
If multiple cookies share the highest frequency, each will be displayed on a new line.

### Testing
Tests are implemented to verify various aspects of the application's functionality.

- Correct Output: Verifies correct cookies are identified as most active for a given date.
- Error Handling: Tests invalid inputs like missing files, permissions errors, and malformed files.

To run tests, execute:
```shell
mvn test
```

### Error Handling
The program handles various potential errors:

- File Not Found: Outputs a "file not found" error if the specified file does not exist.
- Permission Denied: Displays an error if the file cannot be read due to permissions.
- Malformed CSV: Displays an error if the file has invalid CSV Date formatting.

