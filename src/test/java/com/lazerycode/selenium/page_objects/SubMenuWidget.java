package com.lazerycode.selenium.page_objects;

import com.lazerycode.selenium.util.Query;
import org.openqa.selenium.By;


/**
 * Created by rnagulapalle on 12/30/17.
 */
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
