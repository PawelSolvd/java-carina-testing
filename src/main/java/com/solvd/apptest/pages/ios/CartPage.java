package com.solvd.apptest.pages.ios;

import com.solvd.apptest.components.base.CartProductBase;
import com.solvd.apptest.components.ios.CartProduct;
import com.solvd.apptest.pages.base.CartPageBase;
import com.solvd.apptest.pages.base.CatalogPageBase;
import com.solvd.apptest.pages.base.LoginPageBase;
import com.zebrunner.carina.utils.factory.DeviceType;
import com.zebrunner.carina.webdriver.decorator.ExtendedWebElement;
import com.zebrunner.carina.webdriver.locator.ExtendedFindBy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.SequencedSet;

@DeviceType(pageType = DeviceType.Type.IOS_PHONE, parentClass = CartPageBase.class)
public class CartPage extends CartPageBase {
    @FindBy(className = "XCUIElementTypeCell")
    private List<CartProduct> products;

    @ExtendedFindBy(accessibilityId = "ProceedToCheckout")
    private ExtendedWebElement checkoutBtn;

    @FindBy(xpath = "//XCUIElementTypeOther[@name=\"Cart-screen\"]/XCUIElementTypeOther[5]/XCUIElementTypeStaticText[3]")
    private ExtendedWebElement totalPrice;

    @ExtendedFindBy(accessibilityId = "GoShopping")
    private ExtendedWebElement goShoppingButton;

    public CartPage(WebDriver driver) {
        super(driver);
    }

    @Override
    public LoginPageBase goCheckout() {
        checkoutBtn.click();
        return initPage(getDriver(), LoginPageBase.class);
    }

    @Override
    public SequencedSet<CartProductBase> getProducts() {
        return new LinkedHashSet<>(products);
    }

    public Float getTotalPrice() {
        return Float.valueOf(totalPrice.getText().substring(1).replace(',', '.'));
    }

    public CatalogPageBase goShopping() {
        goShoppingButton.click();
        return initPage(getDriver(), CatalogPageBase.class);
    }
}
