package com.solvd.apptest.components.base;

import com.zebrunner.carina.webdriver.decorator.ExtendedWebElement;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebDriver;

public abstract class CartProductBase extends ProductBase {
    public CartProductBase(WebDriver driver, SearchContext searchContext, ExtendedWebElement element) {
        super(driver, searchContext, element);
    }

    public CartProductBase(WebDriver driver, SearchContext searchContext) {
        super(driver, searchContext);
    }

    public abstract Integer getQuantity();

    public abstract void remove();
}
