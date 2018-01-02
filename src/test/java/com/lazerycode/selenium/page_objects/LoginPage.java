package com.lazerycode.selenium.page_objects;

import com.lazerycode.selenium.util.Query;
import org.openqa.selenium.By;

public class LoginPage {

    Query usernameField = new Query(By.id("UserUsername"));
    Query passwordField = new Query(By.id("UserPassword"));
    Query loginButton = new Query(By.className("btn-auth"));

    public LoginPage login(String username, String password) {
        usernameField.findWebElement().clear();
        usernameField.findWebElement().sendKeys(username);

        passwordField.findWebElement().clear();
        passwordField.findWebElement().sendKeys(password);

        return this;
    }

    public LoginPage submit() {
        loginButton.findWebElement().submit();

        return this;
    }
}
