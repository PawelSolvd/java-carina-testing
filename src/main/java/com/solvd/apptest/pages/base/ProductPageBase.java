package com.solvd.apptest.pages.base;

import org.openqa.selenium.WebDriver;

public abstract class ProductPageBase extends BasePage {
    public ProductPageBase(WebDriver driver) {
        super(driver);
    }

    public abstract void addToCart();

    public abstract void clickPlus(int count);

    public abstract void clickMinus();

    public abstract Integer getQuantity();

    public abstract CartPageBase openCart();

    public abstract CatalogPageBase openCatalog();

    public abstract void rateProduct(int rating);

    public abstract void processRatingPopup();

    public abstract String getProductName();

    public abstract String getPrice();

}
