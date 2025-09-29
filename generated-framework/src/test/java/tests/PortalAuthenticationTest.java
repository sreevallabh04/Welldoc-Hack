package tests;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;

public class PortalAuthenticationTest extends BaseTest {

    @Test(description = "TC_SMIT_01 - Login to SMIT Portal")
    public void loginPortal() {
        driver.get("https://azqa21-dsm.testwd.com/SMITPortal/Guest/Login.htm");
        WebElement usernameField = waitForElement(By.id("username"));
        WebElement passwordField = waitForElement(By.id("password"));
        WebElement loginButton = waitForElement(By.id("btnSubmit"));

        log.info("Entering username and password");
        usernameField.sendKeys("welldocsu");
        passwordField.sendKeys("welldoc123");
        loginButton.click();

        WebElement welcomeText = waitForElement(By.xpath("//h1[contains(text(),'Welcome')]"));

        log.info("Validating successful login");
        Assert.assertTrue(welcomeText.isDisplayed());
    }
}