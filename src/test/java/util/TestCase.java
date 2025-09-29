package util;

import java.util.List;
import java.util.Map;

/**
 * Model class representing a test case from Excel
 */
public class TestCase {
    private String id;
    private String className;
    private String methodName;
    private String preConditions;
    private String summary;
    private String testData;
    private List<String> steps;
    private String expected;
    private Map<String, String> structuredTestData;
    
    // Constructors
    public TestCase() {}
    
    public TestCase(String id, String className, String methodName, String preConditions, 
                   String summary, String testData, List<String> steps, String expected) {
        this.id = id;
        this.className = className;
        this.methodName = methodName;
        this.preConditions = preConditions;
        this.summary = summary;
        this.testData = testData;
        this.steps = steps;
        this.expected = expected;
    }
    
    // Getters and Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    
    public String getClassName() { return className; }
    public void setClassName(String className) { this.className = className; }
    
    public String getMethodName() { return methodName; }
    public void setMethodName(String methodName) { this.methodName = methodName; }
    
    public String getPreConditions() { return preConditions; }
    public void setPreConditions(String preConditions) { this.preConditions = preConditions; }
    
    public String getSummary() { return summary; }
    public void setSummary(String summary) { this.summary = summary; }
    
    public String getTestData() { return testData; }
    public void setTestData(String testData) { this.testData = testData; }
    
    public List<String> getSteps() { return steps; }
    public void setSteps(List<String> steps) { this.steps = steps; }
    
    public String getExpected() { return expected; }
    public void setExpected(String expected) { this.expected = expected; }
    
    public Map<String, String> getStructuredTestData() { return structuredTestData; }
    public void setStructuredTestData(Map<String, String> structuredTestData) { 
        this.structuredTestData = structuredTestData; 
    }
    
    /**
     * Gets a specific test data value by key
     */
    public String getTestDataValue(String key) {
        if (structuredTestData == null) return null;
        return structuredTestData.get(key.toLowerCase());
    }
    
    /**
     * Checks if this test case has navigation steps (contains URL)
     */
    public boolean hasNavigationSteps() {
        if (steps == null) return false;
        return steps.stream().anyMatch(step -> 
            step.toLowerCase().contains("http") || 
            step.toLowerCase().contains("navigate") ||
            step.toLowerCase().contains("url"));
    }
    
    /**
     * Extracts URL from navigation steps
     */
    public String extractUrl() {
        if (steps == null) return null;
        for (String step : steps) {
            if (step.toLowerCase().contains("http")) {
                // Extract URL from step
                String[] words = step.split("\\s+");
                for (String word : words) {
                    if (word.toLowerCase().startsWith("http")) {
                        return word.replaceAll("[.,;]", "");
                    }
                }
            }
        }
        return null;
    }
    
    @Override
    public String toString() {
        return "TestCase{" +
                "id='" + id + '\'' +
                ", className='" + className + '\'' +
                ", methodName='" + methodName + '\'' +
                ", summary='" + summary + '\'' +
                '}';
    }
}
