package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class LoginPage extends BasePage {

    private final By usernameField = By.id("cmusername");
    private final By passwordField = By.id("cmpassword");
    private final By loginButton = By.xpath("//button[@type='submit' and contains(@class,'btn-primary')]");
    private final By messagesLink = By.xpath("//a[text()='Messages']");

    public LoginPage(WebDriver driver) {
        super(driver);
    }

    public void navigateToLoginPage() {
        driver.get("https://azqa21-dsm.testwd.com/SMITPortal/Guest/Login.htm");
    }

    public void login(String username, String password) {
        type(usernameField, username);
        type(passwordField, password);
        click(loginButton);
    }

    public void navigateToMessages() {
        click(messagesLink);
    }

    // TODO: Implement method for patient search with given criteria
    public void searchPatient(String criteria) {
        // TODO: Fill the search criteria field and click search button
    }

    public void open() {
        navigateToLoginPage();
    }

    public boolean isLoggedIn() {
        return !getCurrentUrl().contains("Login");
    }

    public boolean isLoginFormDisplayed() {
        return driver.findElement(usernameField).isDisplayed();
    }

    public boolean hasErrorMessage() {
        try {
            return driver.findElement(By.className("error")).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public String getPageTitle() {
        return driver.getTitle();
    }
}