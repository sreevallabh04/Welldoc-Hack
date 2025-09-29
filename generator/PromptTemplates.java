package generator;

/**
 * Internal prompt templates for code generation
 * These templates are used by the generator to create consistent Java code
 */
public class PromptTemplates {
    
    /**
     * Template for generating Page Object Model classes
     * This template ensures consistent POM class structure
     */
    public static final String POM_CLASS_TEMPLATE = """
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
        """;
    
    /**
     * Template for generating TestNG test methods
     * This template ensures consistent test method structure
     */
    public static final String TEST_METHOD_TEMPLATE = """
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
        """;
    
    /**
     * Template for generating locator declarations
     * Provides consistent locator patterns with TODO comments
     */
    public static final String LOCATOR_TEMPLATE = """
        private final By {ELEMENT_NAME} = By.{LOCATOR_TYPE}("{LOCATOR_VALUE}"); // TODO: verify locator on real page
        """;
    
    /**
     * Template for generating page action methods
     * Provides consistent method structure for page interactions
     */
    public static final String ACTION_METHOD_TEMPLATE = """
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
        """;
    
    /**
     * Template for generating test class structure
     * Provides consistent test class organization
     */
    public static final String TEST_CLASS_TEMPLATE = """
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
        """;
    
    /**
     * Template for generating locator alternatives
     * Provides fallback locators for robustness
     */
    public static final String ALTERNATIVE_LOCATOR_TEMPLATE = """
        // Alternative locators in case primary ones don't work
        private final By {ELEMENT_NAME}Alt = By.{LOCATOR_TYPE}("{LOCATOR_VALUE}"); // TODO: verify locator on real page
        """;
    
    /**
     * Template for generating validation methods
     * Provides consistent validation method structure
     */
    public static final String VALIDATION_METHOD_TEMPLATE = """
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
        """;
    
    /**
     * Template for generating navigation methods
     * Provides consistent navigation method structure
     */
    public static final String NAVIGATION_METHOD_TEMPLATE = """
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
        """;
    
    /**
     * Template for generating data extraction methods
     * Provides consistent data extraction method structure
     */
    public static final String DATA_EXTRACTION_TEMPLATE = """
        /**
         * {EXTRACTION_DESCRIPTION}
         * @return {RETURN_DESCRIPTION}
         */
        public {RETURN_TYPE} {METHOD_NAME}() {
            try {
                {EXTRACTION_CODE}
            } catch (Exception e) {
                System.err.println("Error {EXTRACTION_DESCRIPTION}: " + e.getMessage());
                return {DEFAULT_VALUE};
            }
        }
        """;
    
    /**
     * Template for generating test data setup
     * Provides consistent test data initialization
     */
    public static final String TEST_DATA_TEMPLATE = """
        // Test data for {TEST_CASE_ID}
        private static final String {DATA_NAME} = "{DATA_VALUE}";
        """;
    
    /**
     * Template for generating assertion patterns
     * Provides consistent assertion structures
     */
    public static final String ASSERTION_TEMPLATE = """
        Assert.{ASSERTION_TYPE}({CONDITION}, "{ASSERTION_MESSAGE}");
        """;
    
    /**
     * Template for generating error handling
     * Provides consistent error handling patterns
     */
    public static final String ERROR_HANDLING_TEMPLATE = """
        try {
            {OPERATION_CODE}
        } catch (Exception e) {
            System.err.println("Error {OPERATION_DESCRIPTION}: " + e.getMessage());
            {ERROR_ACTION}
        }
        """;
    
    /**
     * Template for generating logging statements
     * Provides consistent logging patterns
     */
    public static final String LOGGING_TEMPLATE = """
        System.out.println("{LOG_MESSAGE}");
        """;
    
    /**
     * Template for generating method documentation
     * Provides consistent Javadoc structure
     */
    public static final String JAVADOC_TEMPLATE = """
        /**
         * {METHOD_DESCRIPTION}
         * {ADDITIONAL_DESCRIPTION}
         * @param {PARAM_NAME} {PARAM_DESCRIPTION}
         * @return {RETURN_DESCRIPTION}
         */
        """;
    
    /**
     * Template for generating class documentation
     * Provides consistent class documentation structure
     */
    public static final String CLASS_JAVADOC_TEMPLATE = """
        /**
         * {CLASS_DESCRIPTION}
         * {ADDITIONAL_DESCRIPTION}
         * 
         * @author SMIT Automation Generator
         * @version 1.0
         * @since {GENERATION_DATE}
         */
        """;
    
    /**
     * Template for generating TODO comments
     * Provides consistent TODO comment structure
     */
    public static final String TODO_TEMPLATE = """
        // TODO: {TODO_DESCRIPTION}
        """;
    
    /**
     * Template for generating warning comments
     * Provides consistent warning comment structure
     */
    public static final String WARNING_TEMPLATE = """
        // WARNING: {WARNING_DESCRIPTION}
        """;
    
    /**
     * Template for generating locator verification comments
     * Provides consistent locator verification structure
     */
    public static final String LOCATOR_VERIFICATION_TEMPLATE = """
        // TODO: verify locator on real page - {ELEMENT_DESCRIPTION}
        """;
    
    /**
     * Template for generating test case documentation
     * Provides consistent test case documentation structure
     */
    public static final String TEST_CASE_DOC_TEMPLATE = """
        /**
         * Test Case: {TEST_CASE_ID}
         * Description: {TEST_DESCRIPTION}
         * Pre-conditions: {PRE_CONDITIONS}
         * Test Data: {TEST_DATA}
         * Expected Results: {EXPECTED_RESULTS}
         */
        """;
}
