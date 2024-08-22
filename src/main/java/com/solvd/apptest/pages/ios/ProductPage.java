package com.solvd.apptest.pages.ios;

import com.solvd.apptest.pages.base.CartPageBase;
import com.solvd.apptest.pages.base.CatalogPageBase;
import com.solvd.apptest.pages.base.ProductPageBase;
import com.zebrunner.carina.utils.factory.DeviceType;
import com.zebrunner.carina.webdriver.decorator.ExtendedWebElement;
import com.zebrunner.carina.webdriver.locator.ExtendedFindBy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;
import org.testng.Assert;

import java.util.List;

@DeviceType(pageType = DeviceType.Type.IOS_PHONE, parentClass = ProductPageBase.class)
public class ProductPage extends ProductPageBase {
    @FindBy(xpath = "//XCUIElementTypeScrollView/XCUIElementTypeOther[1]/XCUIElementTypeStaticText[1]")
    private ExtendedWebElement productName;

    @ExtendedFindBy(accessibilityId = "AddToCart")
    private ExtendedWebElement addToCartBtn;

    @ExtendedFindBy(accessibilityId = "AddPlus Icons")
    private ExtendedWebElement plus;

    @ExtendedFindBy(accessibilityId = "SubtractMinus Icons")
    private ExtendedWebElement minus;

    @ExtendedFindBy(accessibilityId = "Amount")
    private ExtendedWebElement quantity;

    @FindBy(xpath = "//XCUIElementTypeScrollView/XCUIElementTypeOther[1]/XCUIElementTypeStaticText[2]")
    private ExtendedWebElement price;

    @FindBy(xpath = "//XCUIElementTypeScrollView/XCUIElementTypeOther[1]/XCUIElementTypeOther[2]//XCUIElementTypeButton")
    private List<ExtendedWebElement> stars;

    @FindBy(xpath = "//XCUIElementTypeAlert/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther[2]/XCUIElementTypeScrollView[1]/XCUIElementTypeOther[1]/XCUIElementTypeStaticText")
    private ExtendedWebElement ratingAlert;

    @ExtendedFindBy(accessibilityId = "OK")
    private ExtendedWebElement ratingContinue;

    @ExtendedFindBy(accessibilityId = "Catalog-tab-item")
    private ExtendedWebElement catalogButton;

    @ExtendedFindBy(accessibilityId = "Cart-tab-item")
    private ExtendedWebElement cartButton;

    public ProductPage(WebDriver driver) {
        super(driver);
    }

    @Override
    public void addToCart() {
        addToCartBtn.click();
    }

    @Override
    public void clickPlus(int count) {
        for (int i = 0; i < count; i++)
            plus.click();
    }

    @Override
    public void clickMinus() {
        minus.click();
    }

    @Override
    public Integer getQuantity() {
        return Integer.parseInt(quantity.getText());
    }

    @Override
    public CartPageBase openCart() {
        cartButton.click();
        return initPage(getDriver(), CartPageBase.class);
    }

    @Override
    public CatalogPageBase openCatalog() {
        catalogButton.click();
        return initPage(getDriver(), CatalogPageBase.class);
    }

    public void rateProduct(int rating) {
        var rt = stars.get(rating - 1);
        rt.click();
    }

    public void processRatingPopup() {
        Assert.assertTrue(ratingAlert.isElementPresent(), "Rating popup not present!");
        Assert.assertTrue(ratingAlert.getText().contains("Thank you for submitting your review!"), "Wrong popup text!");
        ratingContinue.click();
    }

    public String getProductName() {
        return productName.getText();
    }

    public String getPrice() {
        return price.getText().substring(1).replace(',', '.');
    }
}
