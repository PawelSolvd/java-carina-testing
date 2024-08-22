package com.solvd.apptest.pages.android;

import com.solvd.apptest.pages.base.CatalogPageBase;
import com.solvd.apptest.pages.base.CheckoutPageBase;
import com.zebrunner.carina.utils.factory.DeviceType;
import com.zebrunner.carina.webdriver.decorator.ExtendedWebElement;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;

import static org.testng.AssertJUnit.assertTrue;

@DeviceType(pageType = DeviceType.Type.ANDROID_PHONE, parentClass = CheckoutPageBase.class)
public class CheckoutPage extends CheckoutPageBase {
    // Shipping part
    @FindBy(id = "com.saucelabs.mydemoapp.android:id/fullNameET")
    private ExtendedWebElement nameField;

    @FindBy(id = "com.saucelabs.mydemoapp.android:id/address1ET")
    private ExtendedWebElement address1Field;

    @FindBy(id = "com.saucelabs.mydemoapp.android:id/cityET")
    private ExtendedWebElement cityField;

    @FindBy(id = "com.saucelabs.mydemoapp.android:id/zipET")
    private ExtendedWebElement zipInput;

    @FindBy(id = "com.saucelabs.mydemoapp.android:id/countryET")
    private ExtendedWebElement countryInput;

    @FindBy(id = "com.saucelabs.mydemoapp.android:id/paymentBtn")
    private ExtendedWebElement paymentBtn;

    // Payment part
    @FindBy(id = "com.saucelabs.mydemoapp.android:id/nameET")
    private ExtendedWebElement cardOwnerField;

    @FindBy(id = "com.saucelabs.mydemoapp.android:id/cardNumberET")
    private ExtendedWebElement cardNumberField;

    @FindBy(id = "com.saucelabs.mydemoapp.android:id/expirationDateET")
    private ExtendedWebElement cardExpirationInput;

    @FindBy(id = "com.saucelabs.mydemoapp.android:id/securityCodeET")
    private ExtendedWebElement cardSecurityCodeInput;

    @FindBy(id = "com.saucelabs.mydemoapp.android:id/paymentBtn")
    private ExtendedWebElement reviewOrderBtn;

    // Review part
    @FindBy(id = "com.saucelabs.mydemoapp.android:id/paymentBtn")
    private ExtendedWebElement placeOrderBtn;

    @FindBy(id = "com.saucelabs.mydemoapp.android:id/completeTV")
    private ExtendedWebElement checkoutComplete;

    @FindBy(id = "com.saucelabs.mydemoapp.android:id/shoopingBt")
    private ExtendedWebElement continueShoppingBtn;

    public CheckoutPage(WebDriver driver) {
        super(driver);
    }

    @Override
    public void inputShippingInfo(String name, String address1, String city, String zip, String country) {
        nameField.type(name);
        address1Field.type(address1);
        cityField.type(city);
        zipInput.type(zip);
        countryInput.type(country);

        paymentBtn.click();
    }

    @Override
    public void inputPaymentInfo(String name, String cardNumber, String expDate, String code) {
        cardOwnerField.type(name);
        cardNumberField.type(cardNumber);
        cardExpirationInput.type(expDate);
        cardSecurityCodeInput.type(code);

        reviewOrderBtn.click();
    }

    public CatalogPageBase placeOrder() {
        placeOrderBtn.click();
        assertTrue(checkoutComplete.getText().contains("Checkout Complete"));
        continueShoppingBtn.click();

        return initPage(getDriver(), CatalogPageBase.class);
    }
}
