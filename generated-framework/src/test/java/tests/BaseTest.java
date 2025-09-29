package tests;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import pages.LoginPage;

import java.time.Duration;
import java.util.logging.Logger;

public class BaseTest {
    protected static WebDriver driver;
    protected static LoginPage loginPage;
    protected static final Logger log = Logger.getLogger(BaseTest.class.getName());
    private static boolean isDriverInitialized = false;

    @BeforeClass
    public void setUp() {
        if (!isDriverInitialized) {
            WebDriverManager.chromedriver().setup();
            driver = new ChromeDriver();
            driver.manage().window().maximize();
            loginPage = new LoginPage(driver);
            isDriverInitialized = true;
            log.info("Global browser session initialized");
        }
    }

    @AfterClass
    public void tearDown() {
        // Don't close the driver here - let it run for all test classes
        // The driver will be closed when all tests are complete
    }
    
    // Static method to close driver when all tests are done
    public static void closeGlobalDriver() {
        if (driver != null) {
            driver.quit();
            isDriverInitialized = false;
            log.info("Global browser session closed");
        }
    }

    public void login() {
        loginPage.navigateToLoginPage();
        loginPage.login("welldocsu", "welldoc123");
    }

    protected WebElement waitForElement(By by) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        return wait.until(ExpectedConditions.presenceOfElementLocated(by));
    }

    public void logInfo(String message) {
        log.info(message);
    }

    public void logError(String message) {
        log.severe(message);
    }
}
