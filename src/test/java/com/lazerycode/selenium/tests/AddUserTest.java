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
import org.apache.commons.lang.StringUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;
import java.text.DecimalFormat;
import java.util.Random;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;


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
                assertTrue("Failed to found Logout text on this page",  d.getPageSource().contains("Logout"));
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
                assertTrue("Failed to found Add New User text on this page",  d.getPageSource().contains("Add New User"));
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
                assertTrue("Failed to found Order Summary text on this page",  d.getPageSource().contains("Order Summary"));
                assertTrue("Failed to found $0.00 text on this page",  d.getPageSource().contains("$0.00"));
                return d.getPageSource().contains("Order Summary") && d.getPageSource().contains("$0.00");
            }
        });

        // fill order summer and click next
        OrderSummerPage orderSummerPage = new OrderSummerPage();
        orderSummerPage.fillIndutry("Real Estate");
        if(!Constants.ENV.equals("prod")) {
            orderSummerPage.click("//div[@id='cb-account-title']//button[@type='button']");
            Thread.sleep(2000);
        }

        orderSummerPage.fillBilling(getPhone(), "hgh-auto", "test", "1234", "sjc", "CA", "95035");
        if(!Constants.ENV.equals("prod")) {
            orderSummerPage.click("//div[@id='cb-billing-title']//button[@type='button']");
            Thread.sleep(2000);
        }

        orderSummerPage.payment(Constants.CC, Constants.EXP_MONTH, Constants.EXP_YEAR, Constants.CVV);
        orderSummerPage.click("//div[@id='cb-payment-title']//input[@value='Subscribe']");
        Thread.sleep(10000); // 10 seconds

        // chargbee is taking too long sometimes
        (new WebDriverWait(driver, 60)).until(new ExpectedCondition<Boolean>() {
            public Boolean apply(WebDriver d) {
                //return d.getTitle().toLowerCase().startsWith("Logout");
                assertTrue("Failed to found Thank you! text on this page",  d.getPageSource().contains("Thank you!"));
                return d.getPageSource().contains("Thank you!");
            }
        });

        // get email password
        MailHelper mailHelper = new MailHelper(Constants.NEW_USER);
        Thread.sleep(120000); // sleep for 60 secs
        this.password = mailHelper.getPassword().trim();
        assertNotNull("Password cant not be null", password);
        assertFalse("Password cant not be null or empty", StringUtils.isEmpty(password));
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
        int retry = 3;
        while (retry > 0) {
            Thread.sleep(20000);
            loginPage.login(Constants.NEW_USER, password)
                    .submit();

            // TODO:  account is not yet read try multiple times every 5 seconds
            if(driver.getPageSource().contains("Step 1 of 4: Account Setup"))
                break;
            else
                retry--;
        }

        // Google's search is rendered dynamically with JavaScript.
        // Wait for the page to load, timeout after 10 seconds
        (new WebDriverWait(driver, 20)).until(new ExpectedCondition<Boolean>() {
            public Boolean apply(WebDriver d) {
                //return d.getTitle().toLowerCase().startsWith("Logout");
                assertTrue("Failed to found Step 1 of 4: Account Setup text on this page",
                        d.getPageSource().contains("Step 1 of 4: Account Setup"));
                return d.getPageSource().contains("Step 1 of 4: Account Setup");
            }
        });
        AccountsetupPage accountsetup = new AccountsetupPage();

        // goto new-password setup page
        accountsetup.next();
        (new WebDriverWait(driver, 20)).until(new ExpectedCondition<Boolean>() {
            public Boolean apply(WebDriver d) {
                //return d.getTitle().toLowerCase().startsWith("Logout");
                assertTrue("Failed to found Step 2 of 4: Account Setup text on this page",  d.getPageSource().contains("Step 2 of 4: Account Setup"));
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
                assertTrue("Failed to found Step 3 of 4: Account Setup text on this page",  d.getPageSource().contains("Step 3 of 4: Account Setup"));
                return d.getPageSource().contains("Step 3 of 4: Account Setup");
            }
        });

        // default values and goto next
        accountsetup.saveAndContinue();
        Thread.sleep(30000);
        (new WebDriverWait(driver, 10)).until(new ExpectedCondition<Boolean>() {
            public Boolean apply(WebDriver d) {
                //return d.getTitle().toLowerCase().startsWith("Logout");
                assertTrue("Failed to found Step 4 of 4: Account Setup text on this page",  d.getPageSource().contains("Step 4 of 4: Account Setup"));
                return d.getPageSource().contains("Step 4 of 4: Account Setup");
            }
        });

        // approve and continue
        accountsetup.next();
        Thread.sleep(60000);
        // database setup starts
        (new WebDriverWait(driver, 30)).until(new ExpectedCondition<Boolean>() {
            public Boolean apply(WebDriver d) {
                //return d.getTitle().toLowerCase().startsWith("Logout");
                assertTrue("Failed to found Step 1 of 5: How Happy Grasshopper works text on this page",  d.getPageSource().contains("Step 1 of 5: How Happy Grasshopper works"));
                return d.getPageSource().contains("Step 1 of 5: How Happy Grasshopper works");
            }
        });

        // database setup page begin
        DatabasePage databasePage = new DatabasePage();
        Thread.sleep(30000);
        databasePage.next();
        (new WebDriverWait(driver, 10)).until(new ExpectedCondition<Boolean>() {
            public Boolean apply(WebDriver d) {
                //return d.getTitle().toLowerCase().startsWith("Logout");
                assertTrue("Failed to found Step 2 of 5: How Happy Grasshopper works text on this page",  d.getPageSource().contains("Step 2 of 5: How Happy Grasshopper works"));
                return d.getPageSource().contains("Step 2 of 5: How Happy Grasshopper works");
            }
        });

        // fill add contact
        String firstname = "testfirstname" + getRandomNumber();
        String lastname = "testlastname" + getRandomNumber();
        String phone = getPhone();
        String email = "happygrasshopperauto+" + System.currentTimeMillis() + "@gmail.com";
        String audience = "Sphere";

        databasePage.addContact(firstname, lastname, phone, email, audience);
        databasePage.nextButton();
        Thread.sleep(30000);
        (new WebDriverWait(driver, 10)).until(new ExpectedCondition<Boolean>() {
            public Boolean apply(WebDriver d) {
                //return d.getTitle().toLowerCase().startsWith("Logout");
                assertTrue("Failed to found Step 3 of 5: How Happy Grasshopper works text on this page",  d.getPageSource().contains("Step 3 of 5: How Happy Grasshopper works"));
                return d.getPageSource().contains("Step 3 of 5: How Happy Grasshopper works");
            }
        });

        databasePage.saveAndSend();
        Thread.sleep(30000);
        (new WebDriverWait(driver, 10)).until(new ExpectedCondition<Boolean>() {
            public Boolean apply(WebDriver d) {
                //return d.getTitle().toLowerCase().startsWith("Logout");
                assertTrue("Failed to found Step 4 of 5: How Happy Grasshopper works text on this page",  d.getPageSource().contains("Step 4 of 5: How Happy Grasshopper works"));
                return d.getPageSource().contains("Step 4 of 5: How Happy Grasshopper works");
            }
        });

        databasePage.submitToContinue();
        Thread.sleep(30000);
        (new WebDriverWait(driver, 10)).until(new ExpectedCondition<Boolean>() {
            public Boolean apply(WebDriver d) {
                //return d.getTitle().toLowerCase().startsWith("Logout");
                assertTrue("Failed to found Step 5 of 5: How Happy Grasshopper works text on this page",  d.getPageSource().contains("Step 5 of 5: How Happy Grasshopper works"));
                return d.getPageSource().contains("Step 5 of 5: How Happy Grasshopper works") && d.getPageSource().contains("Congratulations, you're done!");
            }
        });
        // goto accounts setting page
        databasePage.nextButtonById();
        Thread.sleep(30000);
        (new WebDriverWait(driver, 30)).until(new ExpectedCondition<Boolean>() {
            public Boolean apply(WebDriver d) {
                //return d.getTitle().toLowerCase().startsWith("Logout");
                assertTrue("Failed to found Send by Audience text on this page",  d.getPageSource().contains("Send by Audience"));
                assertTrue("Failed to found Contact Management text on this page",  d.getPageSource().contains("Contact Management"));
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

    private String getRandomNumber(){
        Random rand = new Random();

        return String.valueOf(rand.nextInt(50) + 1);
    }
}
