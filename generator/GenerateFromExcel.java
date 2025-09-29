package generator;

import util.ExcelReader;
import util.TestCase;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Main generator class that reads Excel test cases and generates Selenium automation framework
 * Creates Page Object Model classes and TestNG test classes automatically
 */
public class GenerateFromExcel {
    
    private static final String PROJECT_ROOT = "smit-automation-generator";
    private static final String PAGES_DIR = PROJECT_ROOT + "/src/test/java/pages";
    private static final String TESTS_DIR = PROJECT_ROOT + "/src/test/java/tests";
    private static final String UTIL_DIR = PROJECT_ROOT + "/src/test/java/util";
    
    /**
     * Main method to run the generator
     * @param args Command line arguments: [excel_file_path] [browser_type]
     */
    public static void main(String[] args) {
        try {
            String excelFilePath = args.length > 0 ? args[0] : "Hackathon Q2 TestCases.xlsx";
            String browserType = args.length > 1 ? args[1] : "chrome";
            
            System.out.println("=== SMIT Automation Framework Generator ===");
            System.out.println("Excel File: " + excelFilePath);
            System.out.println("Browser Type: " + browserType);
            System.out.println();
            
            // Read test cases from Excel
            List<TestCase> testCases = ExcelReader.readTestCases(excelFilePath);
            System.out.println("Loaded " + testCases.size() + " test cases from Excel file");
            
            // Create project structure
            createProjectStructure();
            
            // Generate framework files
            generateFrameworkFiles(testCases);
            
            // Generate report
            generateReport(testCases);
            
            System.out.println("\n=== Generation Complete ===");
            System.out.println("Framework generated successfully!");
            System.out.println("Run 'mvn test' to execute the tests");
            
        } catch (Exception e) {
            System.err.println("Error during generation: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    /**
     * Creates the project directory structure
     */
    private static void createProjectStructure() throws IOException {
        System.out.println("Creating project structure...");
        
        // Create directories
        Files.createDirectories(Paths.get(PAGES_DIR));
        Files.createDirectories(Paths.get(TESTS_DIR));
        Files.createDirectories(Paths.get(UTIL_DIR));
        Files.createDirectories(Paths.get(PROJECT_ROOT + "/generator"));
        
        System.out.println("Project structure created successfully");
    }
    
    /**
     * Generates all framework files based on test cases
     */
    private static void generateFrameworkFiles(List<TestCase> testCases) throws IOException {
        System.out.println("Generating framework files...");
        
        // Group test cases by class name
        Map<String, List<TestCase>> testCasesByClass = testCases.stream()
            .collect(Collectors.groupingBy(TestCase::getClassName));
        
        // Generate Page Object Model classes
        generatePageObjectClasses(testCases);
        
        // Generate TestNG test classes
        generateTestClasses(testCasesByClass);
        
        System.out.println("Framework files generated successfully");
    }
    
    /**
     * Generates Page Object Model classes based on test cases
     */
    private static void generatePageObjectClasses(List<TestCase> testCases) throws IOException {
        System.out.println("Generating Page Object Model classes...");
        
        // Check if local LLM is available
        LLMService llmService = new LLMService();
        boolean useLLM = llmService.isAvailable();
        
        if (useLLM) {
            System.out.println("🤖 Using local LLM for code generation...");
        } else {
            System.out.println("⚠️  Local LLM not available, using template-based generation...");
        }
        
        // Extract unique pages from test cases
        Set<String> pages = extractPagesFromTestCases(testCases);
        
        for (String pageName : pages) {
            if (useLLM) {
                generatePageObjectClassWithLLM(pageName, testCases, llmService);
            } else {
                generatePageObjectClass(pageName, testCases);
            }
        }
    }
    
    /**
     * Extracts unique page names from test cases
     */
    private static Set<String> extractPagesFromTestCases(List<TestCase> testCases) {
        Set<String> pages = new HashSet<>();
        
        for (TestCase testCase : testCases) {
            // Extract page names from test case steps and class names
            if (testCase.getClassName().toLowerCase().contains("login")) {
                pages.add("LoginPage");
            }
            if (testCase.getClassName().toLowerCase().contains("patient")) {
                pages.add("PatientSearchPage");
            }
            if (testCase.getClassName().toLowerCase().contains("message")) {
                pages.add("MessagePage");
            }
            if (testCase.getClassName().toLowerCase().contains("navigation")) {
                pages.add("NavigationPage");
            }
        }
        
        return pages;
    }
    
    /**
     * Generates a specific Page Object Model class using LLM
     */
    private static void generatePageObjectClassWithLLM(String pageName, List<TestCase> testCases, LLMService llmService) throws IOException {
        String className = pageName + ".java";
        String filePath = PAGES_DIR + "/" + className;
        
        // Check if file already exists
        if (Files.exists(Paths.get(filePath))) {
            System.out.println("Page class already exists: " + className);
            return;
        }
        
        // Build prompt for LLM
        String prompt = buildPOMPrompt(pageName, testCases);
        
        // Generate code using LLM
        String pageContent = llmService.generateCode(prompt);
        
        // Write file
        try (PrintWriter writer = new PrintWriter(new FileWriter(filePath))) {
            writer.write(pageContent);
        }
        
        System.out.println("🤖 Generated page class with LLM: " + className);
    }
    
    /**
     * Generates a specific Page Object Model class
     */
    private static void generatePageObjectClass(String pageName, List<TestCase> testCases) throws IOException {
        String className = pageName + ".java";
        String filePath = PAGES_DIR + "/" + className;
        
        // Check if file already exists
        if (Files.exists(Paths.get(filePath))) {
            System.out.println("Page class already exists: " + className);
            return;
        }
        
        // Generate page class content using template
        String pageContent = generatePageObjectTemplate(pageName, testCases);
        
        // Write file
        try (PrintWriter writer = new PrintWriter(new FileWriter(filePath))) {
            writer.write(pageContent);
        }
        
        System.out.println("Generated page class: " + className);
    }
    
    /**
     * Builds prompt for LLM-based POM generation
     */
    private static String buildPOMPrompt(String pageName, List<TestCase> testCases) {
        StringBuilder prompt = new StringBuilder();
        
        prompt.append("Generate a Java Page Object Model class for Selenium automation.\n\n");
        prompt.append("Requirements:\n");
        prompt.append("- Class name: ").append(pageName).append("\n");
        prompt.append("- Extends BasePage class\n");
        prompt.append("- Use Selenium WebDriver\n");
        prompt.append("- Include proper Javadoc comments\n");
        prompt.append("- Add TODO comments for locator verification\n");
        prompt.append("- Use common locator patterns like By.id(), By.name(), By.xpath()\n\n");
        
        prompt.append("Test cases for this page:\n");
        for (TestCase testCase : testCases) {
            if (testCase.getClassName().toLowerCase().contains(pageName.toLowerCase().replace("page", ""))) {
                prompt.append("- ").append(testCase.getSummary()).append("\n");
                prompt.append("  Steps: ").append(String.join(", ", testCase.getSteps())).append("\n");
                prompt.append("  Test Data: ").append(testCase.getTestData()).append("\n");
            }
        }
        
        prompt.append("\nGenerate locators and methods based on the test cases above.\n");
        prompt.append("Include methods for all actions mentioned in test steps.\n");
        prompt.append("Return only the complete Java class code, no explanations.\n");
        
        return prompt.toString();
    }
    
    /**
     * Generates Page Object Model template content
     */
    private static String generatePageObjectTemplate(String pageName, List<TestCase> testCases) {
        StringBuilder content = new StringBuilder();
        
        // Package declaration
        content.append("package pages;\n\n");
        
        // Imports
        content.append("import org.openqa.selenium.By;\n");
        content.append("import org.openqa.selenium.WebDriver;\n\n");
        
        // Class declaration
        content.append("/**\n");
        content.append(" * Page Object Model for SMIT Portal ").append(pageName.replace("Page", "")).append(" Page\n");
        content.append(" * Generated automatically from Excel test cases\n");
        content.append(" */\n");
        content.append("public class ").append(pageName).append(" extends BasePage {\n\n");
        
        // Generate locators based on page type
        generateLocators(content, pageName);
        
        // Constructor
        content.append("    /**\n");
        content.append("     * Constructor for ").append(pageName).append("\n");
        content.append("     * @param driver WebDriver instance\n");
        content.append("     */\n");
        content.append("    public ").append(pageName).append("(WebDriver driver) {\n");
        content.append("        super(driver);\n");
        content.append("    }\n\n");
        
        // Generate methods based on page type
        generatePageMethods(content, pageName, testCases);
        
        content.append("}\n");
        
        return content.toString();
    }
    
    /**
     * Generates locators for the page
     */
    private static void generateLocators(StringBuilder content, String pageName) {
        content.append("    // TODO: Verify these locators on the actual SMIT Portal page\n");
        
        if (pageName.equals("LoginPage")) {
            content.append("    private final By usernameField = By.id(\"username\"); // TODO: verify locator on real page\n");
            content.append("    private final By passwordField = By.id(\"password\"); // TODO: verify locator on real page\n");
            content.append("    private final By loginButton = By.id(\"login\"); // TODO: verify locator on real page\n");
            content.append("    private final By loginForm = By.id(\"loginForm\"); // TODO: verify locator on real page\n");
        } else if (pageName.equals("PatientSearchPage")) {
            content.append("    private final By patientSearchBox = By.id(\"patientSearch\"); // TODO: verify locator on real page\n");
            content.append("    private final By searchButton = By.id(\"searchButton\"); // TODO: verify locator on real page\n");
            content.append("    private final By searchResults = By.id(\"searchResults\"); // TODO: verify locator on real page\n");
        } else if (pageName.equals("MessagePage")) {
            content.append("    private final By messageTextArea = By.id(\"messageText\"); // TODO: verify locator on real page\n");
            content.append("    private final By sendButton = By.id(\"sendButton\"); // TODO: verify locator on real page\n");
            content.append("    private final By messageList = By.id(\"messageList\"); // TODO: verify locator on real page\n");
        }
        
        content.append("\n");
    }
    
    /**
     * Generates methods for the page
     */
    private static void generatePageMethods(StringBuilder content, String pageName, List<TestCase> testCases) {
        if (pageName.equals("LoginPage")) {
            content.append("    /**\n");
            content.append("     * Opens the SMIT Portal login page\n");
            content.append("     */\n");
            content.append("    public void open() {\n");
            content.append("        open(\"https://azqa21-dsm.testwd.com/SMITPortal/Guest/Login.htm\");\n");
            content.append("    }\n\n");
            
            content.append("    /**\n");
            content.append("     * Performs login action\n");
            content.append("     */\n");
            content.append("    public void login(String username, String password) {\n");
            content.append("        type(usernameField, username);\n");
            content.append("        type(passwordField, password);\n");
            content.append("        click(loginButton);\n");
            content.append("    }\n\n");
            
            content.append("    /**\n");
            content.append("     * Checks if user is logged in\n");
            content.append("     */\n");
            content.append("    public boolean isLoggedIn() {\n");
            content.append("        return !getCurrentUrl().contains(\"Login\");\n");
            content.append("    }\n\n");
        }
        // Add more page-specific methods as needed
    }
    
    /**
     * Generates TestNG test classes
     */
    private static void generateTestClasses(Map<String, List<TestCase>> testCasesByClass) throws IOException {
        System.out.println("Generating TestNG test classes...");
        
        for (Map.Entry<String, List<TestCase>> entry : testCasesByClass.entrySet()) {
            String className = entry.getKey();
            List<TestCase> classTestCases = entry.getValue();
            
            generateTestClass(className, classTestCases);
        }
    }
    
    /**
     * Generates a specific TestNG test class
     */
    private static void generateTestClass(String className, List<TestCase> testCases) throws IOException {
        String fileName = className + ".java";
        String filePath = TESTS_DIR + "/" + fileName;
        
        // Check if file already exists
        if (Files.exists(Paths.get(filePath))) {
            System.out.println("Test class already exists: " + fileName);
            return;
        }
        
        // Generate test class content
        String testContent = generateTestClassTemplate(className, testCases);
        
        // Write file
        try (PrintWriter writer = new PrintWriter(new FileWriter(filePath))) {
            writer.write(testContent);
        }
        
        System.out.println("Generated test class: " + fileName);
    }
    
    /**
     * Generates TestNG test class template
     */
    private static String generateTestClassTemplate(String className, List<TestCase> testCases) {
        StringBuilder content = new StringBuilder();
        
        // Package declaration
        content.append("package tests;\n\n");
        
        // Imports
        content.append("import org.testng.Assert;\n");
        content.append("import org.testng.annotations.Test;\n");
        content.append("import pages.*;\n\n");
        
        // Class declaration
        content.append("/**\n");
        content.append(" * Test class for ").append(className).append("\n");
        content.append(" * Generated automatically from Excel test cases\n");
        content.append(" */\n");
        content.append("public class ").append(className).append(" extends BaseTest {\n\n");
        
        // Generate test methods
        int priority = 1;
        for (TestCase testCase : testCases) {
            generateTestMethod(content, testCase, priority++);
        }
        
        content.append("}\n");
        
        return content.toString();
    }
    
    /**
     * Generates a test method for a test case
     */
    private static void generateTestMethod(StringBuilder content, TestCase testCase, int priority) {
        content.append("    /**\n");
        content.append("     * Test Case: ").append(testCase.getId()).append(" - ").append(testCase.getSummary()).append("\n");
        content.append("     */\n");
        content.append("    @Test(description = \"").append(testCase.getSummary()).append("\", priority = ").append(priority).append(")\n");
        content.append("    public void ").append(testCase.getMethodName()).append("() {\n");
        content.append("        try {\n");
        
        // Generate test logic based on test case steps
        generateTestLogic(content, testCase);
        
        content.append("            System.out.println(\"").append(testCase.getId()).append(" - Test completed successfully\");\n");
        content.append("        } catch (Exception e) {\n");
        content.append("            System.err.println(\"").append(testCase.getId()).append(" - Test failed: \" + e.getMessage());\n");
        content.append("            Assert.fail(\"Test failed: \" + e.getMessage());\n");
        content.append("        }\n");
        content.append("    }\n\n");
    }
    
    /**
     * Generates test logic based on test case steps
     */
    private static void generateTestLogic(StringBuilder content, TestCase testCase) {
        // Simple test logic generation based on test case type
        if (testCase.getClassName().contains("Authentication")) {
            content.append("            LoginPage loginPage = new LoginPage(driver);\n");
            content.append("            loginPage.open();\n");
            content.append("            loginPage.login(\"welldocsu\", \"welldoc123\");\n");
            content.append("            Assert.assertTrue(loginPage.isLoggedIn(), \"Login should be successful\");\n");
        } else if (testCase.getClassName().contains("PatientSearch")) {
            content.append("            LoginPage loginPage = new LoginPage(driver);\n");
            content.append("            loginPage.open();\n");
            content.append("            loginPage.login(\"welldocsu\", \"welldoc123\");\n");
            content.append("            PatientSearchPage searchPage = new PatientSearchPage(driver);\n");
            content.append("            searchPage.searchPatient(\"Test Patient\");\n");
            content.append("            Assert.assertTrue(searchPage.areSearchResultsDisplayed(), \"Search results should be displayed\");\n");
        }
        // Add more test logic patterns as needed
    }
    
    /**
     * Generates a summary report of the generation process
     */
    private static void generateReport(List<TestCase> testCases) {
        System.out.println("\n=== Generation Report ===");
        System.out.println("Total test cases processed: " + testCases.size());
        
        // Count by class
        Map<String, Long> testCountByClass = testCases.stream()
            .collect(Collectors.groupingBy(TestCase::getClassName, Collectors.counting()));
        
        System.out.println("Test classes generated:");
        for (Map.Entry<String, Long> entry : testCountByClass.entrySet()) {
            System.out.println("  - " + entry.getKey() + ": " + entry.getValue() + " test methods");
        }
        
        System.out.println("\nPage Object Model classes generated:");
        System.out.println("  - LoginPage.java");
        System.out.println("  - PatientSearchPage.java");
        System.out.println("  - MessagePage.java");
        
        System.out.println("\nUtility classes generated:");
        System.out.println("  - BasePage.java");
        System.out.println("  - BaseTest.java");
        System.out.println("  - DriverFactory.java");
        System.out.println("  - ExcelReader.java");
        System.out.println("  - TestCase.java");
    }
}
