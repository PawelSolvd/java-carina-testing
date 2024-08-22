package com.solvd.webtest.components;

import com.zebrunner.carina.webdriver.gui.AbstractUIObject;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebDriver;

public abstract class ResultBase extends AbstractUIObject {
    public ResultBase(WebDriver driver, SearchContext searchContext) {
        super(driver, searchContext);
    }

    public abstract String getItemTitle();
    
    public abstract String getPrice();

    public abstract String getItemLocation();

    public abstract String getShipping();

    public abstract String getSponsored();

    // toString() causes stack overflow when element is missing
    public abstract String print();
}

