package tests;

import org.testng.annotations.*;
import pages.MessagePage;
import org.testng.Assert;

public class PortalNavigationTest extends BaseTest {

    @Test(description = "TC_SMIT_03 - Navigate to Messages")
    public void navigateToMessages() {
        logInfo("Starting TC_SMIT_03 - Navigate to Messages");

        try {
            login(); // Use the login method from BaseTest

            MessagePage messagePage = new MessagePage(driver);
            messagePage.navigateToMessages();

            String expectedTitle = "Messages";
            String actualTitle = driver.getTitle();
            Assert.assertEquals(actualTitle, expectedTitle, "Failed to navigate to Messages page");

            logInfo("TC_SMIT_03 - Navigate to Messages passed");
        } catch (Exception e) {
            logError("TC_SMIT_03 - Navigate to Messages failed: " + e.getMessage());
            Assert.fail(e.getMessage());
        }
    }
}