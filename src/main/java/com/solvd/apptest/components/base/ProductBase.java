package com.solvd.apptest.components.base;

import com.solvd.apptest.pages.base.ProductPageBase;
import com.zebrunner.carina.webdriver.decorator.ExtendedWebElement;
import com.zebrunner.carina.webdriver.gui.AbstractUIObject;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebDriver;

public abstract class ProductBase extends AbstractUIObject {
    public ProductBase(WebDriver driver, SearchContext searchContext, ExtendedWebElement element) {
        super(driver, searchContext);
        setElement(element.getElement());
        setBy(element.getBy());
    }

    public ProductBase(WebDriver driver, SearchContext searchContext) {
        super(driver, searchContext);
    }

    public abstract String getProductName();

    public abstract String getPrice();

    public abstract ExtendedWebElement getNameElement();

    public abstract ExtendedWebElement getPriceElement();

    public abstract ProductPageBase openProductPage();

    public abstract void rateProduct(int rating);

}
