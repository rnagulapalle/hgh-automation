package com.lazerycode.selenium.page_objects;

import com.lazerycode.selenium.util.Query;
import org.openqa.selenium.By;

public class SubMenuWidget {

    Query link;

    public SubMenuWidget(String linkText) {
        this.link = new Query(By.linkText(linkText));
    }

    public SubMenuWidget click() {
        this.link.findWebElement().click();

        return this;
    }
}
