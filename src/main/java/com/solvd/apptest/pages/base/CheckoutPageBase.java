package com.solvd.apptest.pages.base;

import org.openqa.selenium.WebDriver;

public abstract class CheckoutPageBase extends BasePage {
    public CheckoutPageBase(WebDriver driver) {
        super(driver);
    }

    public abstract void inputShippingInfo(String name, String address1, String city, String zip, String country);

    public abstract void inputPaymentInfo(String name, String cardNumber, String expDate, String code);

    public abstract CatalogPageBase placeOrder();

}
