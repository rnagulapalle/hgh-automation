package com.lazerycode.selenium.page_objects;

import com.lazerycode.selenium.util.Query;
import org.openqa.selenium.By;

public class AddNewUserPage {

    Query product = new Query(By.id("product-options"));
    Query email = new Query(By.id("customer_email"));
    Query checkbox = new Query(By.id("MyFormFreeAccount"));
    Query next = new Query(By.className("btn-primary"));

    public AddNewUserPage fillForm(String option, String username) {
        // select product
        product.findSelectElement().selectByValue(option);

        // fill email
        email.findWebElement().clear();
        email.findWebElement().sendKeys(username);

        // select checkbox
        checkbox.findWebElement().click();

        return this;
    }

    public AddNewUserPage click() {
        next.findWebElement().click();

        return this;
    }
}
