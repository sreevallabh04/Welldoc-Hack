package generator;

import java.util.List;

import util.TestCase;

/**
 * LLM-powered code generator for creating Selenium automation code
 * Integrates with local LLM for dynamic code generation
 */
public class LLMCodeGenerator {
    
    /**
     * Generates Page Object Model class using local LLM
     * @param pageName Name of the page class
     * @param testCases List of test cases for this page
     * @return Generated Java code for the POM class
     */
    public static String generatePOMClass(String pageName, List<TestCase> testCases) {
        String prompt = buildPOMPrompt(pageName, testCases);
        return callLocalLLM(prompt);
    }
    
    /**
     * Generates TestNG test method using local LLM
     * @param testCase Test case data
     * @return Generated Java code for the test method
     */
    public static String generateTestMethod(TestCase testCase) {
        String prompt = buildTestPrompt(testCase);
        return callLocalLLM(prompt);
    }
    
    /**
     * Builds prompt for POM class generation
     */
    private static String buildPOMPrompt(String pageName, List<TestCase> testCases) {
        StringBuilder prompt = new StringBuilder();
        
        prompt.append("Generate a Java Page Object Model class for Selenium automation.\n\n");
        prompt.append("Requirements:\n");
        prompt.append("- Class name: ").append(pageName).append("\n");
        prompt.append("- Extends BasePage class\n");
        prompt.append("- Use Selenium WebDriver\n");
        prompt.append("- Include proper Javadoc comments\n");
        prompt.append("- Add TODO comments for locator verification\n\n");
        
        prompt.append("Test cases for this page:\n");
        for (TestCase testCase : testCases) {
            prompt.append("- ").append(testCase.getSummary()).append("\n");
            prompt.append("  Steps: ").append(String.join(", ", testCase.getSteps())).append("\n");
        }
        
        prompt.append("\nGenerate locators and methods based on the test cases above.\n");
        prompt.append("Use common locator patterns like By.id(), By.name(), By.xpath().\n");
        prompt.append("Include methods for all actions mentioned in test steps.\n\n");
        prompt.append("Return only the complete Java class code, no explanations.");
        
        return prompt.toString();
    }
    
    /**
     * Builds prompt for test method generation
     */
    private static String buildTestPrompt(TestCase testCase) {
        StringBuilder prompt = new StringBuilder();
        
        prompt.append("Generate a TestNG test method for Selenium automation.\n\n");
        prompt.append("Test Case Details:\n");
        prompt.append("- ID: ").append(testCase.getId()).append("\n");
        prompt.append("- Method Name: ").append(testCase.getMethodName()).append("\n");
        prompt.append("- Summary: ").append(testCase.getSummary()).append("\n");
        prompt.append("- Steps: ").append(String.join(", ", testCase.getSteps())).append("\n");
        prompt.append("- Expected Results: ").append(testCase.getExpected()).append("\n");
        prompt.append("- Test Data: ").append(testCase.getTestData()).append("\n\n");
        
        prompt.append("Requirements:\n");
        prompt.append("- Use TestNG @Test annotation with description and priority\n");
        prompt.append("- Include proper try-catch error handling\n");
        prompt.append("- Add TestNG assertions for expected results\n");
        prompt.append("- Use Page Object Model classes (LoginPage, PatientSearchPage, etc.)\n");
        prompt.append("- Include logging statements\n");
        prompt.append("- Follow Java naming conventions\n\n");
        
        prompt.append("Generate the complete test method code, no explanations.");
        
        return prompt.toString();
    }
    
    /**
     * Calls local LLM API (Ollama, LM Studio, etc.)
     */
    private static String callLocalLLM(String prompt) {
        try {
            // This is a placeholder for actual LLM integration
            // In real implementation, you would:
            // 1. Make HTTP request to local LLM endpoint
            // 2. Parse JSON response
            // 3. Extract generated code
            // 4. Handle errors and timeouts
            
            System.out.println("🤖 Calling local LLM with prompt...");
            System.out.println("Prompt: " + prompt.substring(0, Math.min(100, prompt.length())) + "...");
            
            // Simulate LLM response (replace with actual LLM call)
            return simulateLLMResponse(prompt);
            
        } catch (Exception e) {
            System.err.println("Error calling local LLM: " + e.getMessage());
            return generateFallbackCode(prompt);
        }
    }
    
    /**
     * Simulates LLM response (replace with actual LLM integration)
     */
    private static String simulateLLMResponse(String prompt) {
        // This simulates what an LLM would return
        // In real implementation, replace with actual LLM API call
        
        if (prompt.contains("Page Object Model")) {
            return generateSimulatedPOMCode(prompt);
        } else if (prompt.contains("TestNG test method")) {
            return generateSimulatedTestCode(prompt);
        }
        
        return "// Generated by LLM\n// TODO: Implement actual LLM integration";
    }
    
    /**
     * Generates fallback code when LLM is unavailable
     */
    private static String generateFallbackCode(String prompt) {
        return "// Fallback code generated when LLM is unavailable\n" +
               "// TODO: Verify and update this code manually\n" +
               "// Original prompt: " + prompt.substring(0, Math.min(50, prompt.length()));
    }
    
    /**
     * Simulates POM code generation
     */
    private static String generateSimulatedPOMCode(String prompt) {
        return """
            package pages;
            
            import org.openqa.selenium.By;
            import org.openqa.selenium.WebDriver;
            
            /**
             * Page Object Model generated by local LLM
             * TODO: Verify locators on actual SMIT Portal page
             */
            public class GeneratedPage extends BasePage {
                
                // LLM-generated locators
                private final By usernameField = By.id("username");
                private final By passwordField = By.id("password");
                private final By loginButton = By.id("login");
                
                public GeneratedPage(WebDriver driver) {
                    super(driver);
                }
                
                public void login(String username, String password) {
                    type(usernameField, username);
                    type(passwordField, password);
                    click(loginButton);
                }
                
                public boolean isLoggedIn() {
                    return !getCurrentUrl().contains("Login");
                }
            }
            """;
    }
    
    /**
     * Simulates test code generation
     */
    private static String generateSimulatedTestCode(String prompt) {
        return """
            /**
             * Test method generated by local LLM
             */
            @Test(description = "Generated test", priority = 1)
            public void generatedTest() {
                try {
                    // LLM-generated test logic
                    LoginPage loginPage = new LoginPage(driver);
                    loginPage.open();
                    loginPage.login("welldocsu", "welldoc123");
                    Assert.assertTrue(loginPage.isLoggedIn(), "Login should be successful");
                    
                    System.out.println("Generated test completed successfully");
                } catch (Exception e) {
                    System.err.println("Generated test failed: " + e.getMessage());
                    Assert.fail("Test failed: " + e.getMessage());
                }
            }
            """;
    }
    
    /**
     * Checks if local LLM is available
     */
    public static boolean isLLMAvailable() {
        try {
            // Check if local LLM endpoint is accessible
            // This would make a simple HTTP request to check availability
            return true; // Placeholder
        } catch (Exception e) {
            return false;
        }
    }
    
    /**
     * Gets available LLM models
     */
    public static List<String> getAvailableModels() {
        // This would query the local LLM for available models
        return List.of("codellama", "llama2", "mistral", "phi");
    }
}
