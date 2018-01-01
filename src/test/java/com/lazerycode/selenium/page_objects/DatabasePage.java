package com.lazerycode.selenium.page_objects;

import com.lazerycode.selenium.util.Query;
import org.openqa.selenium.By;


/**
 * Created by rnagulapalle on 1/1/18.
 */
public class DatabasePage {

    Query next = new Query(By.xpath("//a[@href='/contacts/add']"));
    Query nextButton = new Query(By.className("next-btn"));
    Query nextButtonById = new Query(By.id("next-btn"));
    Query saveAndSend = new Query(By.id("send_first_touch_message_btn"));
    Query continueButton = new Query(By.xpath("//a[@href='/wizard/get_started']"));

    Query contactFirstName = new Query(By.id("ContactFirstName"));
    Query contactLastName = new Query(By.id("ContactLastName"));
    Query contactPhone = new Query(By.id("ContactPhone"));
    Query contactEmail = new Query(By.id("ContactEmail"));
    Query contactAudienceId = new Query(By.id("ContactAudienceId"));

    public DatabasePage next() {
        next.findWebElement().click();

        return this;
    }

    public DatabasePage addContact(String firstname, String lastname, String phone, String email, String option) {
        contactFirstName.findWebElement().clear();
        contactFirstName.findWebElement().sendKeys(firstname);

        contactLastName.findWebElement().clear();
        contactLastName.findWebElement().sendKeys(lastname);

        contactPhone.findWebElement().clear();
        contactPhone.findWebElement().sendKeys(phone);

        contactEmail.findWebElement().clear();
        contactEmail.findWebElement().sendKeys(email);

        contactAudienceId.findSelectElement().selectByVisibleText(option);

        return this;
    }

    public DatabasePage nextButton() {
        nextButton.findWebElement().click();

        return this;
    }

    public DatabasePage saveAndSend() {
        saveAndSend.findWebElement().submit();

        return this;
    }

    public DatabasePage submitToContinue() {
        continueButton.findWebElement().click();

        return this;
    }

    public DatabasePage nextButtonById() {
        nextButtonById.findWebElement().click();

        return this;
    }

}
