package util;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;

/**
 * Factory class for creating WebDriver instances
 * Supports Chrome, Firefox, and Edge browsers
 */
public class DriverFactory {
    
    private static final String DEFAULT_BROWSER = "chrome";
    
    /**
     * Creates a WebDriver instance based on browser type
     * @param browserType Type of browser (chrome, firefox, edge)
     * @return WebDriver instance
     */
    public static WebDriver createDriver(String browserType) {
        if (browserType == null || browserType.trim().isEmpty()) {
            browserType = DEFAULT_BROWSER;
        }
        
        WebDriver driver;
        browserType = browserType.toLowerCase();
        
        switch (browserType) {
            case "chrome":
                driver = createChromeDriver();
                break;
            case "firefox":
                driver = createFirefoxDriver();
                break;
            case "edge":
                driver = createEdgeDriver();
                break;
            default:
                System.out.println("Unknown browser type: " + browserType + ". Using Chrome as default.");
                driver = createChromeDriver();
        }
        
        // Set common driver properties
        driver.manage().window().maximize();
        driver.manage().deleteAllCookies();
        
        return driver;
    }
    
    /**
     * Creates a Chrome WebDriver instance
     */
    private static WebDriver createChromeDriver() {
        WebDriverManager.chromedriver().setup();
        
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-blink-features=AutomationControlled");
        options.addArguments("--disable-extensions");
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");
        
        return new ChromeDriver(options);
    }
    
    /**
     * Creates a Firefox WebDriver instance
     */
    private static WebDriver createFirefoxDriver() {
        WebDriverManager.firefoxdriver().setup();
        
        FirefoxOptions options = new FirefoxOptions();
        options.addArguments("--disable-extensions");
        
        return new FirefoxDriver(options);
    }
    
    /**
     * Creates an Edge WebDriver instance
     */
    private static WebDriver createEdgeDriver() {
        WebDriverManager.edgedriver().setup();
        
        EdgeOptions options = new EdgeOptions();
        options.addArguments("--disable-extensions");
        
        return new EdgeDriver(options);
    }
    
    /**
     * Creates a default Chrome WebDriver instance
     */
    public static WebDriver createDriver() {
        return createDriver(DEFAULT_BROWSER);
    }
    
    /**
     * Quits the WebDriver instance safely
     */
    public static void quitDriver(WebDriver driver) {
        if (driver != null) {
            try {
                driver.quit();
            } catch (Exception e) {
                System.err.println("Error quitting driver: " + e.getMessage());
            }
        }
    }
}
