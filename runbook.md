# SMIT Automation Framework - Quick Runbook

## Quick Start Commands

### 1. Compile the Project
```bash
mvn clean compile
```

### 2. Run All Tests
```bash
mvn test
```

### 3. Run Specific Test Class
```bash
mvn test -Dtest=PortalAuthenticationTest
```

### 4. Run Single Test Method
```bash
mvn test -Dtest=PortalAuthenticationTest#loginPortal
```

### 5. Run with Different Browser
```bash
mvn test -Dbrowser=firefox
```

## Expected Output

### Successful Compilation
```
[INFO] Scanning for projects...
[INFO] 
[INFO] -----------------< com.welldoc:smit-automation-generator >-----------------
[INFO] Building SMIT Automation Test Generator 1.0.0
[INFO] --------------------------------[ jar ]--------------------------------
[INFO] 
[INFO] --- maven-compiler-plugin:3.11.0:compile (default-compile) @ smit-automation-generator ---
[INFO] Changes detected - recompiling the module
[INFO] Compiling 12 source files to target/classes
[INFO] ------------------------------------------------------------------------
[INFO] BUILD SUCCESS
[INFO] ------------------------------------------------------------------------
```

### Successful Test Execution
```
[INFO] Scanning for projects...
[INFO] 
[INFO] -----------------< com.welldoc:smit-automation-generator >-----------------
[INFO] Building SMIT Automation Test Generator 1.0.0
[INFO] --------------------------------[ jar ]--------------------------------
[INFO] 
[INFO] --- maven-surefire-plugin:3.1.2:test (default-test) @ smit-automation-generator ---
[INFO] Using auto detected provider org.apache.maven.surefire.testng.TestNGProvider
[INFO] 
[INFO] -------------------------------------------------------
[INFO]  T E S T S
[INFO] -------------------------------------------------------
[INFO] Running tests.PortalAuthenticationTest
[INFO] Tests run: 4, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 15.234 s
[INFO] Running tests.PatientSearchTest
[INFO] Tests run: 4, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 12.456 s
[INFO] Running tests.PortalNavigationTest
[INFO] Tests run: 5, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 18.789 s
[INFO] 
[INFO] Results:
[INFO] 
[INFO] Tests run: 13, Failures: 0, Errors: 0, Skipped: 0
[INFO] 
[INFO] ------------------------------------------------------------------------
[INFO] BUILD SUCCESS
[INFO] ------------------------------------------------------------------------
```

## Troubleshooting Commands

### Check Java Version
```bash
java -version
```

### Check Maven Version
```bash
mvn -version
```

### Clean and Rebuild
```bash
mvn clean install
```

### Run with Debug Output
```bash
mvn test -X
```

### Skip Tests (Compile Only)
```bash
mvn compile -DskipTests
```

## Locator Verification Steps

### 1. Open SMIT Portal
Navigate to: `https://azqa21-dsm.testwd.com/SMITPortal/Guest/Login.htm`

### 2. Inspect Elements
- Right-click on login elements
- Select "Inspect Element"
- Note the actual IDs, names, and XPaths

### 3. Update POM Classes
Edit the following files with verified locators:
- `src/test/java/pages/LoginPage.java`
- `src/test/java/pages/PatientSearchPage.java`
- `src/test/java/pages/MessagePage.java`

### 4. Re-run Tests
```bash
mvn test -Dtest=PortalAuthenticationTest
```

## Common Issues and Solutions

### Issue: WebDriver not found
**Solution:**
```bash
# WebDriverManager will auto-download, or manually:
# Download ChromeDriver from https://chromedriver.chromium.org/
# Add to system PATH
```

### Issue: Element not found errors
**Solution:**
1. Verify locators in POM classes
2. Check if page structure changed
3. Add explicit waits if needed

### Issue: Excel file not found
**Solution:**
1. Ensure Excel file is in project root
2. Check file permissions
3. Verify file path in generator command

### Issue: Compilation errors
**Solution:**
```bash
mvn clean compile
# Check Java version compatibility
# Verify all dependencies are downloaded
```

## Performance Optimization

### Run Tests in Parallel
```bash
mvn test -Dparallel=methods -DthreadCount=2
```

### Run Specific Test Groups
```bash
mvn test -Dgroups=authentication
```

### Generate Test Reports
```bash
mvn test
# Reports available in target/surefire-reports/
```

## Maintenance Commands

### Update Dependencies
```bash
mvn versions:display-dependency-updates
```

### Generate Project Site
```bash
mvn site
```

### Run Code Quality Checks
```bash
mvn checkstyle:check
```

---

**Note**: Always verify locators on the actual SMIT Portal before running tests in production environment.
