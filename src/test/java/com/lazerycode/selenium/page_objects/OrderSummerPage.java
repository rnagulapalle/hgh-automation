package com.lazerycode.selenium.page_objects;

import com.lazerycode.selenium.util.Query;
import org.openqa.selenium.By;

public class OrderSummerPage {

    // industry fields
    Query industry = new Query(By.id("customer[cf_industry_category]"));
    // billing fields
    Query firstname = new Query(By.id("billing_address[first_name]"));
    Query lastname = new Query(By.id("billing_address[last_name]"));
    Query address1 = new Query(By.id("billing_address[line1]"));
    Query city = new Query(By.id("billing_address[city]"));
    Query state = new Query(By.id("billing_address[state_code]"));
    Query phone = new Query(By.id("billing_address[phone]"));

    // payment
    Query cc = new Query(By.id("card[number]"));
    Query month = new Query(By.xpath("//div[@id='cb-payment-title']//select[@id='card[expiry_month]']"));
    Query year = new Query(By.xpath("//div[@id='cb-payment-title']//select[@id='card[expiry_year]']"));
    Query cvv = new Query(By.id("card[cvv]"));

    public OrderSummerPage fillIndutry(String industryValue) {
        // select industry
        industry.findSelectElement().selectByValue(industryValue);

        return this;
    }

    public OrderSummerPage fillBilling(String phoneValue, String firstnameValue, String lastnameValue, String addressValue,
            String cityValue, String stateValue) {

        firstname.findWebElement().clear();
        firstname.findWebElement().sendKeys(firstnameValue);

        lastname.findWebElement().clear();
        lastname.findWebElement().sendKeys(lastnameValue);

        address1.findWebElement().clear();
        address1.findWebElement().sendKeys(addressValue);

        city.findWebElement().clear();
        city.findWebElement().sendKeys(cityValue);

        phone.findWebElement().clear();
        phone.findWebElement().sendKeys(phoneValue);
        // select state
        state.findSelectElement().selectByValue(stateValue);

        return this;
    }

    public OrderSummerPage payment(String ccValue, String expMonth, String expYear, String cvv2) {

        cc.findWebElement().clear();
        cc.findWebElement().sendKeys(ccValue);

        month.findSelectElement().selectByValue(expMonth);

        year.findSelectElement().selectByValue(expYear);

        cvv.findWebElement().clear();
        cvv.findWebElement().sendKeys(cvv2);

        return this;
    }

    public OrderSummerPage click(String xpath) {
        // next button
        Query next = new Query(By.xpath(xpath));
        next.findWebElement().click();

        return this;
    }
}
