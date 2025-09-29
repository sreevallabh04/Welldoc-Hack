# SMIT Automation Test Script Generator

A Gen AI-powered Selenium automation framework generator for the Welldoc SMIT Portal. This framework automatically generates Page Object Model (POM) classes and TestNG test scripts from Excel test cases.

## Project Overview

This project provides a complete automation framework that:
- Reads test cases from Excel files
- Generates Page Object Model classes for each page
- Creates TestNG test classes with proper test methods
- Provides utilities for WebDriver management and Excel reading
- Includes a generator program to create the framework automatically

## Project Structure

```
smit-automation-generator/
├── pom.xml                          # Maven configuration
├── testng.xml                       # TestNG test suite configuration
├── README.md                        # This file
├── src/test/java/
│   ├── pages/                       # Page Object Model classes
│   │   ├── BasePage.java           # Base page with common utilities
│   │   ├── LoginPage.java          # Login page POM
│   │   ├── PatientSearchPage.java  # Patient search page POM
│   │   └── MessagePage.java        # Message page POM
│   ├── tests/                       # TestNG test classes
│   │   ├── BaseTest.java           # Base test class
│   │   ├── PortalAuthenticationTest.java
│   │   ├── PatientSearchTest.java
│   │   └── PortalNavigationTest.java
│   └── util/                        # Utility classes
│       ├── ExcelReader.java        # Excel file reader
│       ├── TestCase.java           # Test case model
│       └── DriverFactory.java      # WebDriver factory
└── generator/                       # Code generator
    └── GenerateFromExcel.java      # Main generator program
```

## Prerequisites

- Java 11 or higher
- Maven 3.6 or higher
- Chrome browser (or Firefox/Edge)
- Excel file with test cases

## Excel File Format

The Excel file should contain the following columns:
- **Test Case ID**: Unique identifier (e.g., TC_SMIT_01)
- **Automation Class Name**: Test class name (e.g., PortalAuthenticationTest)
- **Automation Method Name**: Test method name (e.g., loginPortal)
- **Pre-Conditions**: Test prerequisites
- **Test Scenario Summary**: Brief description of the test
- **Test Data**: Test data in format "Username: value\nPassword: value"
- **Test Case (steps)**: Test steps separated by newlines
- **Expected Results**: Expected outcome

## Installation and Setup

1. **Clone or download the project**
   ```bash
   git clone <repository-url>
   cd smit-automation-generator
   ```

2. **Install dependencies**
   ```bash
   mvn clean install
   ```

3. **Verify installation**
   ```bash
   mvn compile
   ```

## Usage

### Running the Generator

To generate the framework from an Excel file:

```bash
# Using default Excel file (Hackathon Q2 TestCases.xlsx)
mvn exec:java -Dexec.mainClass="generator.GenerateFromExcel"

# Using custom Excel file
mvn exec:java -Dexec.mainClass="generator.GenerateFromExcel" -Dexec.args="path/to/your/testcases.xlsx"

# Using custom Excel file and browser
mvn exec:java -Dexec.mainClass="generator.GenerateFromExcel" -Dexec.args="path/to/your/testcases.xlsx chrome"
```

### Running Tests

1. **Run all tests**
   ```bash
   mvn test
   ```

2. **Run specific test class**
   ```bash
   mvn test -Dtest=PortalAuthenticationTest
   ```

3. **Run specific test method**
   ```bash
   mvn test -Dtest=PortalAuthenticationTest#loginPortal
   ```

4. **Run tests with specific browser**
   ```bash
   mvn test -Dbrowser=firefox
   ```

5. **Run tests with TestNG XML**
   ```bash
   mvn test -DsuiteXmlFile=testng.xml
   ```

### Test Execution Examples

```bash
# Run authentication tests only
mvn test -Dtest=PortalAuthenticationTest

# Run patient search tests only
mvn test -Dtest=PatientSearchTest

# Run navigation tests only
mvn test -Dtest=PortalNavigationTest

# Run with Firefox browser
mvn test -Dbrowser=firefox

# Run with Edge browser
mvn test -Dbrowser=edge
```

## SMIT Portal URL

The framework is configured to test the SMIT Portal at:
```
https://azqa21-dsm.testwd.com/SMITPortal/Guest/Login.htm
```

## Test Credentials

Default test credentials used in the framework:
- **Username**: welldocsu
- **Password**: welldoc123

## Locator Verification

⚠️ **IMPORTANT**: The generated locators are estimated and need verification on the actual SMIT Portal page.

### How to Verify Locators

1. **Open the SMIT Portal in Chrome**
2. **Open Developer Tools** (F12)
3. **Inspect elements** to find correct locators
4. **Update the POM classes** with verified locators

### Common Locator Patterns

The framework generates locators in this priority order:
1. `By.id("elementId")` - Most reliable
2. `By.name("elementName")` - Good alternative
3. `By.xpath("//input[@placeholder='...']")` - Flexible but slower
4. `By.cssSelector("...")` - CSS-based selection

### Example Locator Updates

```java
// Before (estimated)
private final By usernameField = By.id("username"); // TODO: verify locator on real page

// After (verified)
private final By usernameField = By.id("txtUsername"); // Verified on SMIT Portal
```

## Troubleshooting

### Common Issues

1. **WebDriver not found**
   - Solution: WebDriverManager will automatically download drivers
   - Manual: Download ChromeDriver and add to PATH

2. **Tests fail with element not found**
   - Solution: Verify and update locators in POM classes
   - Check if page structure has changed

3. **Excel file not found**
   - Solution: Ensure Excel file is in the project root
   - Check file path and permissions

4. **Compilation errors**
   - Solution: Run `mvn clean compile` to resolve dependencies
   - Check Java version compatibility

### Debug Mode

Run tests with debug information:
```bash
mvn test -Dmaven.surefire.debug
```

### Test Reports

Test reports are generated in:
- `target/surefire-reports/` - TestNG reports
- `target/site/surefire-report.html` - HTML report

## Framework Features

### Page Object Model (POM)
- **BasePage**: Common utilities for all pages
- **Page Classes**: Specific page implementations
- **Locator Management**: Centralized element locators
- **Action Methods**: Reusable page interactions

### TestNG Integration
- **Test Groups**: Organized test execution
- **Priority Support**: Test execution order
- **Parameter Support**: Browser and configuration parameters
- **Reporting**: Comprehensive test reports

### Utilities
- **ExcelReader**: Apache POI-based Excel parsing
- **DriverFactory**: Multi-browser WebDriver management
- **TestCase Model**: Structured test case representation

## Code Generation

The generator creates:
- **POM Classes**: One per page with estimated locators
- **Test Classes**: One per automation class with test methods
- **Utility Classes**: Excel reading and WebDriver management
- **Configuration**: Maven and TestNG setup

## Contributing

1. Fork the repository
2. Create a feature branch
3. Make your changes
4. Add tests for new functionality
5. Submit a pull request

## License

This project is part of the Welldoc Hackathon September 2025.

## Support

For issues and questions:
1. Check the troubleshooting section
2. Verify locators on the actual SMIT Portal
3. Review test execution logs
4. Contact the development team

---

**Note**: This framework is designed for the Welldoc SMIT Portal and uses open-source LLMs running locally as per hackathon requirements.
