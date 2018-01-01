package com.lazerycode.selenium.tests;

import com.lazerycode.selenium.DriverBase;
import com.lazerycode.selenium.config.Constants;
import com.lazerycode.selenium.page_objects.LoginPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

public class LoginTest extends DriverBase {

    @Test
    public void testLoginSuccess() throws Exception {
        // Create a new WebDriver instance
        // Notice that the remainder of the code relies on the interface,
        // not the implementation.
        WebDriver driver = getDriver();

        // And now use this to visit Google
        driver.get(Constants.LOGIN_URL);
        // Alternatively the same thing can be done like this
        // driver.navigate().to("http://www.google.com");

        LoginPage loginPage = new LoginPage();

        // Check the title of the page
        System.out.println("Page title is: " + driver.getTitle());

        loginPage.login("happygrasshopperchrome@mailinator.com", "Grass123")
                .submit();

        // Google's search is rendered dynamically with JavaScript.
        // Wait for the page to load, timeout after 10 seconds
        (new WebDriverWait(driver, 10)).until(new ExpectedCondition<Boolean>() {
            public Boolean apply(WebDriver d) {
                return d.getPageSource().contains("Logout");
            }
        });

        // Should see: "cheese! - Google Search"
        System.out.println("Page title is: " + driver.getTitle());
    }
}
