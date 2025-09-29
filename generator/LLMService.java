package generator;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.HashMap;

/**
 * Service for integrating with local LLM APIs
 * Supports Ollama, LM Studio, and other local LLM endpoints
 */
public class LLMService {
    
    private static final String DEFAULT_ENDPOINT = "http://localhost:11434/api/generate";
    private static final String DEFAULT_MODEL = "mistral:latest";
    private static final ObjectMapper objectMapper = new ObjectMapper();
    
    private String endpoint;
    private String model;
    
    public LLMService() {
        this.endpoint = DEFAULT_ENDPOINT;
        this.model = DEFAULT_MODEL;
    }
    
    public LLMService(String endpoint, String model) {
        this.endpoint = endpoint;
        this.model = model;
    }
    
    /**
     * Generates code using local LLM
     * @param prompt The prompt to send to the LLM
     * @return Generated code response
     */
    public String generateCode(String prompt) {
        try {
            System.out.println("🤖 Calling local LLM: " + model);
            System.out.println("📡 Endpoint: " + endpoint);
            
            // Prepare request payload
            Map<String, Object> request = new HashMap<>();
            request.put("model", model);
            request.put("prompt", prompt);
            request.put("stream", false);
            request.put("options", Map.of(
                "temperature", 0.1,
                "top_p", 0.9,
                "max_tokens", 2000
            ));
            
            // Make HTTP request
            String response = makeHttpRequest(request);
            
            // Parse response
            return parseLLMResponse(response);
            
        } catch (Exception e) {
            System.err.println("❌ LLM generation failed: " + e.getMessage());
            return generateFallbackCode(prompt);
        }
    }
    
    /**
     * Makes HTTP request to local LLM endpoint
     */
    private String makeHttpRequest(Map<String, Object> request) throws IOException {
        URL url = new URL(endpoint);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        
        connection.setRequestMethod("POST");
        connection.setRequestProperty("Content-Type", "application/json");
        connection.setDoOutput(true);
        
        // Send request
        String requestBody = objectMapper.writeValueAsString(request);
        try (OutputStream os = connection.getOutputStream()) {
            byte[] input = requestBody.getBytes(StandardCharsets.UTF_8);
            os.write(input, 0, input.length);
        }
        
        // Read response
        StringBuilder response = new StringBuilder();
        try (BufferedReader br = new BufferedReader(
                new InputStreamReader(connection.getInputStream(), StandardCharsets.UTF_8))) {
            String responseLine;
            while ((responseLine = br.readLine()) != null) {
                response.append(responseLine.trim());
            }
        }
        
        return response.toString();
    }
    
    /**
     * Parses LLM response to extract generated code
     */
    private String parseLLMResponse(String response) {
        try {
            // Parse JSON response from Ollama/LM Studio
            Map<String, Object> responseMap = objectMapper.readValue(response, Map.class);
            String generatedText = (String) responseMap.get("response");
            
            if (generatedText != null && !generatedText.trim().isEmpty()) {
                return cleanGeneratedCode(generatedText);
            } else {
                throw new RuntimeException("Empty response from LLM");
            }
            
        } catch (Exception e) {
            System.err.println("Error parsing LLM response: " + e.getMessage());
            return generateFallbackCode("Error parsing response");
        }
    }
    
    /**
     * Cleans and formats generated code
     */
    private String cleanGeneratedCode(String code) {
        // Remove markdown code blocks if present
        code = code.replaceAll("```java\\s*", "").replaceAll("```\\s*", "");
        
        // Remove any leading/trailing whitespace
        code = code.trim();
        
        // Ensure proper Java formatting
        if (!code.startsWith("package ")) {
            code = "package pages;\n\n" + code;
        }
        
        return code;
    }
    
    /**
     * Generates fallback code when LLM is unavailable
     */
    private String generateFallbackCode(String prompt) {
        return """
            // Fallback code generated when local LLM is unavailable
            // TODO: Update this code manually or ensure LLM is running
            
            package pages;
            
            import org.openqa.selenium.By;
            import org.openqa.selenium.WebDriver;
            
            /**
             * Fallback Page Object Model
             * Generated when LLM integration failed
             */
            public class FallbackPage extends BasePage {
                
                // TODO: Verify these locators on actual SMIT Portal page
                private final By usernameField = By.id("username");
                private final By passwordField = By.id("password");
                private final By loginButton = By.id("login");
                
                public FallbackPage(WebDriver driver) {
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
     * Checks if local LLM is available
     */
    public boolean isAvailable() {
        try {
            URL url = new URL(endpoint.replace("/generate", "/api/tags"));
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setConnectTimeout(5000);
            connection.setReadTimeout(5000);
            
            int responseCode = connection.getResponseCode();
            return responseCode == 200;
            
        } catch (Exception e) {
            System.err.println("Local LLM not available: " + e.getMessage());
            return false;
        }
    }
    
    /**
     * Gets available models from local LLM
     */
    public String[] getAvailableModels() {
        try {
            URL url = new URL(endpoint.replace("/generate", "/api/tags"));
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            
            StringBuilder response = new StringBuilder();
            try (BufferedReader br = new BufferedReader(
                    new InputStreamReader(connection.getInputStream(), StandardCharsets.UTF_8))) {
                String responseLine;
                while ((responseLine = br.readLine()) != null) {
                    response.append(responseLine.trim());
                }
            }
            
            // Parse models from response
            Map<String, Object> responseMap = objectMapper.readValue(response.toString(), Map.class);
            // Extract model names from response
            return new String[]{"codellama", "llama2", "mistral", "phi"}; // Simplified
            
        } catch (Exception e) {
            System.err.println("Error getting available models: " + e.getMessage());
            return new String[]{"codellama"}; // Default fallback
        }
    }
    
    // Getters and setters
    public String getEndpoint() { return endpoint; }
    public void setEndpoint(String endpoint) { this.endpoint = endpoint; }
    
    public String getModel() { return model; }
    public void setModel(String model) { this.model = model; }
}
