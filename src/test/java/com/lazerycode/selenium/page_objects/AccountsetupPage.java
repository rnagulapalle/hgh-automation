package com.lazerycode.selenium.page_objects;

import com.lazerycode.selenium.util.Query;
import org.openqa.selenium.By;


/**
 * Created by rnagulapalle on 1/1/18.
 */
public class AccountsetupPage {

    Query next = new Query(By.className("next-btn"));
    Query saveAndContinue = new Query(By.xpath("//div[@class='wizard-content']//div[@title='Save & Continue']"));

    // step 2
    Query userNewPassword = new Query(By.id("UserNewPassword"));
    Query userConfirmPassword = new Query(By.id("UserConfirmPassword"));

    public AccountsetupPage next() {
        next.findWebElement().click();

        return this;
    }

    public AccountsetupPage saveAndContinue() {
//        actions.moveToElement(saveAndContinue.findWebElement()).click().perform();
        saveAndContinue.findWebElement().click();
        return this;
    }

    public AccountsetupPage fillNewPassword(String userNewPasswordVal, String userConfirmPasswordVal) {
        userNewPassword.findWebElement().clear();
        userNewPassword.findWebElement().sendKeys(userNewPasswordVal);

        userConfirmPassword.findWebElement().clear();
        userConfirmPassword.findWebElement().sendKeys(userConfirmPasswordVal);

        return this;
    }
}
