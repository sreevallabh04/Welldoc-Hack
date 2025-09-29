package generator;

import org.testng.annotations.Test;

/**
 * Test class to demonstrate LLM integration with Ollama Mistral
 */
public class LLMTest {
    
    @Test
    public void testMistralLLMIntegration() {
        try {
            System.out.println("🤖 Testing LLM Integration with Ollama Mistral");
            System.out.println("================================================");
            
            // Test LLM service availability
            LLMService llmService = new LLMService();
            System.out.println("📡 LLM Endpoint: " + llmService.getEndpoint());
            System.out.println("🧠 LLM Model: " + llmService.getModel());
            
            // Check if LLM is available
            boolean isAvailable = llmService.isAvailable();
            System.out.println("✅ LLM Available: " + isAvailable);
            
            if (isAvailable) {
                System.out.println("\n🎯 Testing LLM Code Generation...");
                
                // Test POM generation
                String pomPrompt = buildTestPOMPrompt();
                System.out.println("📝 Sending prompt to Mistral...");
                String generatedPOM = llmService.generateCode(pomPrompt);
                
                System.out.println("\n🤖 Generated POM Code by Mistral:");
                System.out.println("=====================================");
                System.out.println(generatedPOM);
                
                // Test TestNG method generation
                String testPrompt = buildTestMethodPrompt();
                System.out.println("\n📝 Sending test method prompt to Mistral...");
                String generatedTest = llmService.generateCode(testPrompt);
                
                System.out.println("\n🤖 Generated Test Method by Mistral:");
                System.out.println("=====================================");
                System.out.println(generatedTest);
                
            } else {
                System.out.println("❌ LLM not available. Please ensure Ollama is running with mistral:latest model.");
            }
            
        } catch (Exception e) {
            System.err.println("❌ Error testing LLM integration: " + e.getMessage());
        }
    }
    
    private static String buildTestPOMPrompt() {
        return """
            Generate a Java Page Object Model class for Selenium automation.
            
            Requirements:
            - Class name: LoginPage
            - Extends BasePage class
            - Use Selenium WebDriver
            - Include proper Javadoc comments
            - Add TODO comments for locator verification
            - Use common locator patterns like By.id(), By.name(), By.xpath()
            
            Test cases for this page:
            - Login to SMIT Portal
              Steps: Navigate to https://azqa21-dsm.testwd.com/SMITPortal/Guest/Login.htm, Enter username welldocsu, Enter password welldoc123, Click Login
              Test Data: Username: welldocsu, Password: welldoc123
            
            Generate locators and methods based on the test cases above.
            Include methods for all actions mentioned in test steps.
            Return only the complete Java class code, no explanations.
            """;
    }
    
    private static String buildTestMethodPrompt() {
        return """
            Generate a TestNG test method for Selenium automation.
            
            Test Case Details:
            - ID: TC_SMIT_01
            - Method Name: loginPortal
            - Summary: Login to SMIT Portal
            - Steps: Navigate to URL, Enter username welldocsu, Enter password welldoc123, Click Login
            - Expected Results: System should allow the user to successfully login
            - Test Data: Username: welldocsu, Password: welldoc123
            
            Requirements:
            - Use TestNG @Test annotation with description and priority
            - Include proper try-catch error handling
            - Add TestNG assertions for expected results
            - Use Page Object Model classes (LoginPage)
            - Include logging statements
            - Follow Java naming conventions
            
            Generate the complete test method code, no explanations.
            """;
    }
}
