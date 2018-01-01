package com.lazerycode.selenium.tests;

import com.lazerycode.selenium.DriverBase;
import com.lazerycode.selenium.config.Constants;
import com.lazerycode.selenium.page_objects.AccountsetupPage;
import com.lazerycode.selenium.page_objects.AddNewUserPage;
import com.lazerycode.selenium.page_objects.DatabasePage;
import com.lazerycode.selenium.page_objects.LoginPage;
import com.lazerycode.selenium.page_objects.OrderSummerPage;
import com.lazerycode.selenium.page_objects.SubMenuWidget;
import com.lazerycode.selenium.util.MailHelper;
import org.apache.commons.lang.RandomStringUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;
import java.text.DecimalFormat;
import java.util.Random;


public class AddUserTest  extends DriverBase {

    private String password;

    @Test(priority=1, groups="a")
    public void testAddUserWitProAnnualWithOrderSummary() throws Exception {
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

        loginPage.login(Constants.ADMIN_USER, Constants.ADMIN_PASS)
                .submit();

        // Google's search is rendered dynamically with JavaScript.
        // Wait for the page to load, timeout after 10 seconds
        (new WebDriverWait(driver, 10)).until(new ExpectedCondition<Boolean>() {
            public Boolean apply(WebDriver d) {
                //return d.getTitle().toLowerCase().startsWith("Logout");
                return d.getPageSource().contains("Logout");
            }
        });

        // goto Add User page
        SubMenuWidget subMenu = new SubMenuWidget("Add User");
        subMenu.click();

        // sleep for 3 sec
        Thread.sleep(3000);

        (new WebDriverWait(driver, 10)).until(new ExpectedCondition<Boolean>() {
            public Boolean apply(WebDriver d) {
                //return d.getTitle().toLowerCase().startsWith("Logout");
                return d.getPageSource().contains("Add New User");
            }
        });

        // TODO verify data field value set to current date

        // add a new user
        AddNewUserPage addNewUser = new AddNewUserPage();
        addNewUser.fillForm("pro-annual", Constants.NEW_USER);

        Thread.sleep(2000);

        addNewUser.click();

        Thread.sleep(5000);

        (new WebDriverWait(driver, 10)).until(new ExpectedCondition<Boolean>() {
            public Boolean apply(WebDriver d) {
                //return d.getTitle().toLowerCase().startsWith("Logout");
                return d.getPageSource().contains("Order Summary") && d.getPageSource().contains("$0.00");
            }
        });

        // fill order summer and click next
        OrderSummerPage orderSummerPage = new OrderSummerPage();
        orderSummerPage.fillIndutry("Real Estate");
        orderSummerPage.click("//div[@id='cb-account-title']//button[@type='button']");
        Thread.sleep(2000);

        orderSummerPage.fillBilling("123456789", "hgh-auto", "test", "1234", "sjc", "CA");
        orderSummerPage.click("//div[@id='cb-billing-title']//button[@type='button']");
        Thread.sleep(2000);

        orderSummerPage.payment(Constants.CC, Constants.EXP_MONTH, Constants.EXP_YEAR, Constants.CVV);
        orderSummerPage.click("//div[@id='cb-payment-title']//input[@value='Subscribe']");
        Thread.sleep(10000); // 10 seconds

        (new WebDriverWait(driver, 30)).until(new ExpectedCondition<Boolean>() {
            public Boolean apply(WebDriver d) {
                //return d.getTitle().toLowerCase().startsWith("Logout");
                return d.getPageSource().contains("Thank you!");
            }
        });

        // get email password
        MailHelper mailHelper = new MailHelper(Constants.NEW_USER);
        Thread.sleep(60000); // sleep for 60 secs
        this.password = mailHelper.getPassword().trim();
        System.out.print("New username and password");
        System.out.println(Constants.NEW_USER + " : " +password);
    }

    @Test(priority=2, dependsOnGroups = "a", groups = "b")
    public void testUserOnboardingProcess() throws Exception {
        WebDriver driver = getDriver();

        // And now use this to visit Google
        driver.get(Constants.LOGIN_URL);
        // Alternatively the same thing can be done like this
        // driver.navigate().to("http://www.google.com");

        LoginPage loginPage = new LoginPage();

        // Check the title of the page
        System.out.println("Page title is: " + driver.getTitle());
        System.out.println(Constants.NEW_USER + " : " +password);
        Thread.sleep(20000);
        loginPage.login(Constants.NEW_USER, password)
                .submit();

        // Google's search is rendered dynamically with JavaScript.
        // Wait for the page to load, timeout after 10 seconds
        (new WebDriverWait(driver, 10)).until(new ExpectedCondition<Boolean>() {
            public Boolean apply(WebDriver d) {
                //return d.getTitle().toLowerCase().startsWith("Logout");
                return d.getPageSource().contains("Step 1 of 4: Account Setup");
            }
        });

        AccountsetupPage accountsetup = new AccountsetupPage();

        // goto new-password setup page
        accountsetup.next();
        (new WebDriverWait(driver, 10)).until(new ExpectedCondition<Boolean>() {
            public Boolean apply(WebDriver d) {
                //return d.getTitle().toLowerCase().startsWith("Logout");
                return d.getPageSource().contains("Step 2 of 4: Account Setup");
            }
        });
//
//        // fill new password page
        accountsetup.fillNewPassword("Grass123", "Grass123");
        accountsetup.next();
        (new WebDriverWait(driver, 10)).until(new ExpectedCondition<Boolean>() {
            public Boolean apply(WebDriver d) {
                //return d.getTitle().toLowerCase().startsWith("Logout");
                return d.getPageSource().contains("Step 3 of 4: Account Setup");
            }
        });

        // default values and goto next
        accountsetup.saveAndContinue();
        (new WebDriverWait(driver, 10)).until(new ExpectedCondition<Boolean>() {
            public Boolean apply(WebDriver d) {
                //return d.getTitle().toLowerCase().startsWith("Logout");
                return d.getPageSource().contains("Step 4 of 4: Account Setup");
            }
        });

        // approve and continue
        accountsetup.next();
        // database setup starts
        (new WebDriverWait(driver, 10)).until(new ExpectedCondition<Boolean>() {
            public Boolean apply(WebDriver d) {
                //return d.getTitle().toLowerCase().startsWith("Logout");
                return d.getPageSource().contains("Step 1 of 5: How Happy Grasshopper works");
            }
        });

        // database setup page begin
        DatabasePage databasePage = new DatabasePage();
        databasePage.next();
        (new WebDriverWait(driver, 10)).until(new ExpectedCondition<Boolean>() {
            public Boolean apply(WebDriver d) {
                //return d.getTitle().toLowerCase().startsWith("Logout");
                return d.getPageSource().contains("Step 2 of 5: How Happy Grasshopper works");
            }
        });

        // fill add contact
        String firstname = "testfirstname" + RandomStringUtils.random(2);
        String lastname = "testlastname" + RandomStringUtils.random(2);
        String phone = getPhone();
        String email = "happygrasshopperauto+" + System.currentTimeMillis() + "@gmail.com";
        String audience = "Sphere";

        databasePage.addContact(firstname, lastname, phone, email, audience);
        databasePage.nextButton();
        (new WebDriverWait(driver, 10)).until(new ExpectedCondition<Boolean>() {
            public Boolean apply(WebDriver d) {
                //return d.getTitle().toLowerCase().startsWith("Logout");
                return d.getPageSource().contains("Step 3 of 5: How Happy Grasshopper works");
            }
        });

        databasePage.saveAndSend();
        (new WebDriverWait(driver, 10)).until(new ExpectedCondition<Boolean>() {
            public Boolean apply(WebDriver d) {
                //return d.getTitle().toLowerCase().startsWith("Logout");
                return d.getPageSource().contains("Step 4 of 5: How Happy Grasshopper works");
            }
        });

        databasePage.submitToContinue();
        (new WebDriverWait(driver, 10)).until(new ExpectedCondition<Boolean>() {
            public Boolean apply(WebDriver d) {
                //return d.getTitle().toLowerCase().startsWith("Logout");
                return d.getPageSource().contains("Step 5 of 5: How Happy Grasshopper works") && d.getPageSource().contains("Congratulations, you're done!");
            }
        });
        // goto accounts setting page
        databasePage.nextButtonById();
        (new WebDriverWait(driver, 10)).until(new ExpectedCondition<Boolean>() {
            public Boolean apply(WebDriver d) {
                //return d.getTitle().toLowerCase().startsWith("Logout");
                return d.getPageSource().contains("Contact Management") && d.getPageSource().contains("Send by Audience");
            }
        });
    }

    private String getPhone(){
        Random rand = new Random();
        int num1 = (rand.nextInt(7) + 1) * 100 + (rand.nextInt(8) * 10) + rand.nextInt(8);
        int num2 = rand.nextInt(743);
        int num3 = rand.nextInt(10000);

        DecimalFormat df3 = new DecimalFormat("000"); // 3 zeros
        DecimalFormat df4 = new DecimalFormat("0000"); // 4 zeros

        String phoneNumber = df3.format(num1) + "" + df3.format(num2) + "" + df4.format(num3);

        return phoneNumber;
    }
}
