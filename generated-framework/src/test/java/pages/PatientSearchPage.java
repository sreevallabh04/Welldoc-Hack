package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class PatientSearchPage extends BasePage {

    private final By searchCriteriaField = By.id("NameSearch");
    private final By searchButton = By.xpath("//span[contains(text(),'Search') or @data-localize='Main.Search']");

    public PatientSearchPage(WebDriver driver) {
        super(driver);
    }

    public void enterSearchCriteria(String criteria) {
        type(searchCriteriaField, criteria);
    }

    public void clickSearch() {
        click(searchButton);
    }

    public boolean isPageLoaded() {
        try {
            return driver.findElement(searchCriteriaField).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public void searchPatient(String criteria) {
        enterSearchCriteria(criteria);
        clickSearch();
    }

    public boolean areSearchResultsDisplayed() {
        try {
            return driver.findElement(By.id("search-results")).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isNoResultsMessageDisplayed() {
        try {
            return driver.findElement(By.className("no-results")).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public void navigateToHome() {
        driver.findElement(By.linkText("Home")).click();
    }

    public void navigateToDashboard() {
        driver.findElement(By.linkText("Dashboard")).click();
    }

    public void logout() {
        driver.findElement(By.linkText("Logout")).click();
    }

    public String getPageTitle() {
        return driver.getTitle();
    }
}