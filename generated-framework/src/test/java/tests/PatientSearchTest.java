package tests;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.PatientSearchPage;

public class PatientSearchTest extends BaseTest {

    @Test(description = "TC_SMIT_02 - Search for Patient")
    public void searchPatient() {
        logInfo("Starting test: TC_SMIT_02 - Search for Patient");

        // Login to SMIT Portal
        login();

        // Navigate to Patient Search
        driver.findElement(By.id("menu-patient")).click();
        driver.findElement(By.id("menu-search-patient")).click();

        // Enter search criteria
        WebElement firstNameField = driver.findElement(By.id("firstName"));
        firstNameField.sendKeys("John");
        WebElement lastNameField = driver.findElement(By.id("lastName"));
        lastNameField.sendKeys("Doe");
        WebElement searchButton = driver.findElement(By.id("search-button"));
        searchButton.click();

        // Wait for search results to load
        waitForElement(By.id("patient-results"));

        // Check if search results are displayed
        WebElement patientResults = driver.findElement(By.id("patient-results"));
        Assert.assertFalse(patientResults.getAttribute("style").contains("display: none;"), "Search results should be displayed");
    }
}
