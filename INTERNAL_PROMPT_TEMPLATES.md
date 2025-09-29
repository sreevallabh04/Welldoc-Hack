# Internal Prompt Templates for Code Generation

These are the exact prompt templates used by the generator for creating consistent Java code.

## 1. Generate POM Template

**Template Name**: `POM_CLASS_TEMPLATE`

**Purpose**: Generate Page Object Model classes following BasePage pattern

**Template**:
```java
package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * Page Object Model for SMIT Portal {PAGE_NAME} Page
 * Contains all elements and actions related to {PAGE_DESCRIPTION}
 */
public class {PAGE_NAME} extends BasePage {
    
    // TODO: Verify these locators on the actual SMIT Portal page
    {LOCATORS}
    
    /**
     * Constructor for {PAGE_NAME}
     * @param driver WebDriver instance
     */
    public {PAGE_NAME}(WebDriver driver) {
        super(driver);
    }
    
    {METHODS}
}
```

**Usage Example**:
```java
// Input: pageName="LoginPage", pageDescription="user authentication"
// Output: Complete LoginPage.java class with estimated locators and methods
```

## 2. Generate Test Template

**Template Name**: `TEST_METHOD_TEMPLATE`

**Purpose**: Generate TestNG test methods with proper structure

**Template**:
```java
/**
 * Test Case: {TEST_CASE_ID} - {TEST_SUMMARY}
 * {TEST_DESCRIPTION}
 */
@Test(description = "{TEST_SUMMARY}", priority = {PRIORITY})
public void {METHOD_NAME}() {
    try {
        {TEST_LOGIC}
        
        System.out.println("{TEST_CASE_ID} - Test completed successfully");
    } catch (Exception e) {
        System.err.println("{TEST_CASE_ID} - Test failed: " + e.getMessage());
        Assert.fail("Test failed: " + e.getMessage());
    }
}
```

**Usage Example**:
```java
// Input: testCaseId="TC_SMIT_01", methodName="loginPortal", testSummary="Login to SMIT Portal"
// Output: Complete test method with login logic and assertions
```

## 3. Locator Generation Template

**Template Name**: `LOCATOR_TEMPLATE`

**Purpose**: Generate consistent locator declarations with TODO comments

**Template**:
```java
private final By {ELEMENT_NAME} = By.{LOCATOR_TYPE}("{LOCATOR_VALUE}"); // TODO: verify locator on real page
```

**Usage Examples**:
```java
// Input: elementName="usernameField", locatorType="id", locatorValue="username"
// Output: private final By usernameField = By.id("username"); // TODO: verify locator on real page

// Input: elementName="loginButton", locatorType="xpath", locatorValue="//button[contains(text(),'Login')]"
// Output: private final By loginButton = By.xpath("//button[contains(text(),'Login')]"); // TODO: verify locator on real page
```

## 4. Action Method Template

**Template Name**: `ACTION_METHOD_TEMPLATE`

**Purpose**: Generate consistent page action methods

**Template**:
```java
/**
 * {METHOD_DESCRIPTION}
 * @param {PARAM_NAME} {PARAM_DESCRIPTION}
 */
public void {METHOD_NAME}({PARAM_TYPE} {PARAM_NAME}) {
    try {
        {ACTION_CODE}
        System.out.println("{ACTION_DESCRIPTION}: " + {PARAM_NAME});
    } catch (Exception e) {
        System.err.println("Error {ACTION_DESCRIPTION}: " + e.getMessage());
        throw e;
    }
}
```

**Usage Example**:
```java
// Input: methodName="enterUsername", paramType="String", paramName="username", actionCode="type(usernameField, username);"
// Output: Complete method with parameter validation and error handling
```

## 5. Test Class Template

**Template Name**: `TEST_CLASS_TEMPLATE`

**Purpose**: Generate complete TestNG test classes

**Template**:
```java
package tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import pages.*;

/**
 * Test class for {CLASS_NAME}
 * Tests {CLASS_DESCRIPTION} functionality
 */
public class {CLASS_NAME} extends BaseTest {
    
    {TEST_METHODS}
}
```

**Usage Example**:
```java
// Input: className="PortalAuthenticationTest", classDescription="SMIT Portal Authentication"
// Output: Complete test class with all authentication test methods
```

## 6. Validation Method Template

**Template Name**: `VALIDATION_METHOD_TEMPLATE`

**Purpose**: Generate validation methods for page state checking

**Template**:
```java
/**
 * {VALIDATION_DESCRIPTION}
 * @return true if {CONDITION}, false otherwise
 */
public boolean {METHOD_NAME}() {
    try {
        {VALIDATION_CODE}
    } catch (Exception e) {
        System.err.println("Error {VALIDATION_DESCRIPTION}: " + e.getMessage());
        return false;
    }
}
```

**Usage Example**:
```java
// Input: methodName="isLoggedIn", validationDescription="Checks if user is successfully logged in", condition="login was successful"
// Output: Complete validation method with error handling
```

## 7. Navigation Method Template

**Template Name**: `NAVIGATION_METHOD_TEMPLATE`

**Purpose**: Generate navigation methods for page transitions

**Template**:
```java
/**
 * {NAVIGATION_DESCRIPTION}
 */
public void {METHOD_NAME}() {
    try {
        {NAVIGATION_CODE}
    } catch (Exception e) {
        System.err.println("Error {NAVIGATION_DESCRIPTION}: " + e.getMessage());
    }
}
```

**Usage Example**:
```java
// Input: methodName="navigateToHome", navigationDescription="Navigates to home page", navigationCode="click(homeLink);"
// Output: Complete navigation method with error handling
```

## 8. Assertion Template

**Template Name**: `ASSERTION_TEMPLATE`

**Purpose**: Generate consistent TestNG assertions

**Template**:
```java
Assert.{ASSERTION_TYPE}({CONDITION}, "{ASSERTION_MESSAGE}");
```

**Usage Examples**:
```java
// Input: assertionType="assertTrue", condition="loginPage.isLoggedIn()", assertionMessage="User should be successfully logged in"
// Output: Assert.assertTrue(loginPage.isLoggedIn(), "User should be successfully logged in");

// Input: assertionType="assertFalse", condition="loginPage.hasErrorMessage()", assertionMessage="No error message should be displayed"
// Output: Assert.assertFalse(loginPage.hasErrorMessage(), "No error message should be displayed");
```

## 9. Error Handling Template

**Template Name**: `ERROR_HANDLING_TEMPLATE`

**Purpose**: Generate consistent error handling patterns

**Template**:
```java
try {
    {OPERATION_CODE}
} catch (Exception e) {
    System.err.println("Error {OPERATION_DESCRIPTION}: " + e.getMessage());
    {ERROR_ACTION}
}
```

**Usage Example**:
```java
// Input: operationCode="loginPage.login(username, password);", operationDescription="performing login", errorAction="throw e;"
// Output: Complete try-catch block with error logging and re-throwing
```

## 10. Javadoc Template

**Template Name**: `JAVADOC_TEMPLATE`

**Purpose**: Generate consistent Javadoc documentation

**Template**:
```java
/**
 * {METHOD_DESCRIPTION}
 * {ADDITIONAL_DESCRIPTION}
 * @param {PARAM_NAME} {PARAM_DESCRIPTION}
 * @return {RETURN_DESCRIPTION}
 */
```

**Usage Example**:
```java
// Input: methodDescription="Performs login action", paramName="username", paramDescription="Username to enter", returnDescription="void"
// Output: Complete Javadoc with parameter and return documentation
```

## Template Usage in Generator

The generator uses these templates by:

1. **Reading Excel test cases** and extracting page information
2. **Applying POM template** to generate page classes with estimated locators
3. **Applying test template** to generate test methods with proper assertions
4. **Combining templates** to create complete, compilable Java files
5. **Adding TODO comments** for manual locator verification

## Example Generated Code

### Generated LoginPage.java (using POM template):
```java
package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * Page Object Model for SMIT Portal Login Page
 * Contains all elements and actions related to user authentication
 */
public class LoginPage extends BasePage {
    
    // TODO: Verify these locators on the actual SMIT Portal page
    private final By usernameField = By.id("username"); // TODO: verify locator on real page
    private final By passwordField = By.id("password"); // TODO: verify locator on real page
    private final By loginButton = By.id("login"); // TODO: verify locator on real page
    
    /**
     * Constructor for LoginPage
     * @param driver WebDriver instance
     */
    public LoginPage(WebDriver driver) {
        super(driver);
    }
    
    /**
     * Performs login action
     * @param username Username to enter
     * @param password Password to enter
     */
    public void login(String username, String password) {
        try {
            type(usernameField, username);
            type(passwordField, password);
            click(loginButton);
            System.out.println("Login action performed for user: " + username);
        } catch (Exception e) {
            System.err.println("Error performing login: " + e.getMessage());
            throw e;
        }
    }
}
```

### Generated Test Method (using test template):
```java
/**
 * Test Case: TC_SMIT_01 - Login to SMIT Portal
 * Tests successful login with valid credentials
 */
@Test(description = "Login to SMIT Portal", priority = 1)
public void loginPortal() {
    try {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.open();
        loginPage.login("welldocsu", "welldoc123");
        Assert.assertTrue(loginPage.isLoggedIn(), "User should be successfully logged in");
        
        System.out.println("TC_SMIT_01 - Test completed successfully");
    } catch (Exception e) {
        System.err.println("TC_SMIT_01 - Test failed: " + e.getMessage());
        Assert.fail("Test failed: " + e.getMessage());
    }
}
```

## Template Customization

These templates can be customized by:

1. **Modifying template strings** in `PromptTemplates.java`
2. **Adding new template types** for specific code patterns
3. **Updating placeholder variables** to match new requirements
4. **Extending template logic** in the generator program

---

**Note**: These templates ensure consistent, maintainable, and well-documented code generation for the SMIT automation framework.
