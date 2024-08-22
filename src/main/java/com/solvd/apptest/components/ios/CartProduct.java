package com.solvd.apptest.components.ios;

import com.solvd.apptest.components.base.CartProductBase;
import com.solvd.apptest.pages.base.ProductPageBase;
import com.zebrunner.carina.webdriver.decorator.ExtendedWebElement;
import com.zebrunner.carina.webdriver.locator.ExtendedFindBy;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;

public class CartProduct extends CartProductBase {
    @ExtendedFindBy(iosClassChain = "**/XCUIElementTypeButton[`name == \"Remove Item\"`]")
    private ExtendedWebElement removeBtn;

    @FindBy(xpath = "//XCUIElementTypeCell//XCUIElementTypeStaticText[5]")
    private ExtendedWebElement quantity;

    @FindBy(xpath = "//XCUIElementTypeCell/XCUIElementTypeStaticText[1]")
    private ExtendedWebElement name;

    @FindBy(xpath = "//XCUIElementTypeCell//XCUIElementTypeStaticText[2]")
    private ExtendedWebElement price;

    public CartProduct(WebDriver driver, SearchContext searchContext, ExtendedWebElement element) {
        super(driver, searchContext, element);
    }

    public CartProduct(WebDriver driver, SearchContext searchContext) {
        super(driver, searchContext);
    }

    @Override
    public String getProductName() {
        return name.getText();
    }

    @Override
    public String getPrice() {
        return price.getText();
    }

    @Override
    public ExtendedWebElement getNameElement() {
        return null;
    }

    @Override
    public ExtendedWebElement getPriceElement() {
        return null;
    }

    @Override
    public ProductPageBase openProductPage() {
        return null;
    }

    @Override
    public void rateProduct(int rating) {

    }

    public Integer getQuantity() {
        return Integer.valueOf(quantity.getText());
    }

    public void remove() {
        removeBtn.click();
    }
}
