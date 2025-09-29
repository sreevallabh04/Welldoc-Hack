package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class MessagePage extends BasePage {

    private final By messagesLink = By.linkText("Messages");

    public MessagePage(WebDriver driver) {
        super(driver);
    }

    public void navigateToMessages() {
        click(messagesLink);
    }
}