package com.solvd.apptest.components.ios;

import com.solvd.apptest.components.base.ProductBase;
import com.solvd.apptest.pages.base.ProductPageBase;
import com.zebrunner.carina.webdriver.decorator.ExtendedWebElement;
import com.zebrunner.carina.webdriver.locator.ExtendedFindBy;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;

import java.util.List;

public class Product extends ProductBase {
    @ExtendedFindBy(iosClassChain = "**/XCUIElementTypeStaticText[1]")
    private ExtendedWebElement name;

    @ExtendedFindBy(iosClassChain = "**/XCUIElementTypeStaticText[2]")
    private ExtendedWebElement price;

    @FindBy(xpath = "//XCUIElementTypeOther//XCUIElementTypeButton")
    private List<ExtendedWebElement> stars;

    public Product(WebDriver driver, SearchContext searchContext, ExtendedWebElement element) {
        super(driver, searchContext, element);
    }

    public Product(WebDriver driver, SearchContext searchContext) {
        super(driver, searchContext);
    }

    public String getProductName() {
        return name.getText();
    }

    public String getPrice() {
        return price.getText();
    }

    public ExtendedWebElement getNameElement() {
        return name;
    }

    public ExtendedWebElement getPriceElement() {
        return price;
    }

    @Override
    public ProductPageBase openProductPage() {
        this.click();

        return initPage(getDriver(), ProductPageBase.class);
    }

    public void rateProduct(int rating) {
        var rt = stars.get(rating - 1);
        rt.click();
    }
}
