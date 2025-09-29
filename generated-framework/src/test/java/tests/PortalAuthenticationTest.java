package tests;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;

public class PortalAuthenticationTest extends BaseTest {

    @Test(description = "TC_SMIT_01 - Login to SMIT Portal")
    public void loginPortal() throws InterruptedException {
        driver.get("https://azqa21-dsm.testwd.com/SMITPortal/Guest/Login.htm");
        WebElement usernameField = waitForElement(By.id("cmusername"));
        WebElement passwordField = waitForElement(By.id("cmpassword"));
        WebElement loginButton = waitForElement(By.xpath("//button[@type='submit' and contains(@class,'btn-primary')]"));

        log.info("Entering username and password");
        usernameField.sendKeys("welldocsu");
        passwordField.sendKeys("welldoc123");
        loginButton.click();

        // Wait for page to load after login
        Thread.sleep(3000);
        
        // Check if we're no longer on the login page (successful login)
        String currentUrl = driver.getCurrentUrl();
        log.info("Current URL after login: " + currentUrl);
        
        // Validate successful login by checking URL change or page title
        boolean loginSuccessful = !currentUrl.contains("Login") || 
                                 !driver.getTitle().contains("Login") ||
                                 driver.findElements(By.id("cmusername")).isEmpty();
        
        log.info("Validating successful login");
        Assert.assertTrue(loginSuccessful, "Login should redirect away from login page");
    }
}