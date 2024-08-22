package com.solvd.apptest.pages.ios;

import com.solvd.apptest.pages.base.CatalogPageBase;
import com.solvd.apptest.pages.base.CheckoutPageBase;
import com.zebrunner.carina.utils.factory.DeviceType;
import com.zebrunner.carina.webdriver.decorator.ExtendedWebElement;
import com.zebrunner.carina.webdriver.locator.ExtendedFindBy;
import org.openqa.selenium.WebDriver;

@DeviceType(pageType = DeviceType.Type.IOS_PHONE, parentClass = CheckoutPageBase.class)
public class CheckoutPage extends CheckoutPageBase {
    // Shipping part
    @ExtendedFindBy(iosPredicate = "value == \"Rebecca Winter\"")
    private ExtendedWebElement nameField;

    public CheckoutPage(WebDriver driver) {
        super(driver);
        setUiLoadedMarker(nameField);
    }

    @Override
    public void inputShippingInfo(String name, String address1, String city, String zip, String country) {

    }

    @Override
    public void inputPaymentInfo(String name, String cardNumber, String expDate, String code) {

    }

    @Override
    public CatalogPageBase placeOrder() {
        return null;
    }
}
