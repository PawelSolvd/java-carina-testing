package com.solvd.apptest.components.android;

import com.solvd.apptest.components.base.ProductBase;
import com.zebrunner.carina.webdriver.decorator.ExtendedWebElement;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;

public class Product extends ProductBase {
    @FindBy(id = "com.saucelabs.mydemoapp.android:id/titleTV")
    private ExtendedWebElement name;

    @FindBy(id = "com.saucelabs.mydemoapp.android:id/priceTV")
    private ExtendedWebElement price;

    public Product(WebDriver driver, SearchContext searchContext) {
        super(driver, searchContext);
    }
}
