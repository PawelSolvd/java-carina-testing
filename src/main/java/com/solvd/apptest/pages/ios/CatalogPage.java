package com.solvd.apptest.pages.ios;

import com.solvd.apptest.components.base.ProductBase;
import com.solvd.apptest.components.ios.Product;
import com.solvd.apptest.enums.SortOption;
import com.solvd.apptest.pages.base.CartPageBase;
import com.solvd.apptest.pages.base.CatalogPageBase;
import com.solvd.apptest.pages.base.LoginPageBase;
import com.solvd.apptest.pages.base.ProductPageBase;
import com.zebrunner.carina.utils.factory.DeviceType;
import com.zebrunner.carina.webdriver.decorator.ExtendedWebElement;
import com.zebrunner.carina.webdriver.locator.ExtendedFindBy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;
import org.testng.Assert;

import java.util.*;
import java.util.regex.Pattern;

@DeviceType(pageType = DeviceType.Type.IOS_PHONE, parentClass = CatalogPageBase.class)
public class CatalogPage extends CatalogPageBase {
    @ExtendedFindBy(accessibilityId = "Cart-tab-item")
    private ExtendedWebElement cartButton;

    @ExtendedFindBy(iosClassChain = "**/XCUIElementTypeButton[`name == \"Button\"`]")
    private ExtendedWebElement sortButton;

    @FindBy(xpath = "//XCUIElementTypeStaticText[@name=\"Sort by:\"]/parent::*//XCUIElementTypeButton")
    private List<ExtendedWebElement> sortOptions;

    @ExtendedFindBy(iosClassChain = "**/XCUIElementTypeOther[`name == \"ProductItem\"`]")
    private List<Product> products;

    @FindBy(xpath = "//XCUIElementTypeAlert/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther[2]/XCUIElementTypeScrollView[1]/XCUIElementTypeOther[1]/XCUIElementTypeStaticText")
    private ExtendedWebElement ratingAlert;

    @ExtendedFindBy(accessibilityId = "OK")
    private ExtendedWebElement ratingContinue;

    public CatalogPage(WebDriver driver) {
        super(driver);
        setUiLoadedMarker(products.getFirst());
    }

    @Override
    public LoginPageBase openLoginPage() {
        var product = getRandomProduct();

        ProductPageBase productPage = product.openProductPage();

        productPage.clickPlus(1);
        productPage.addToCart();

        CartPageBase cartPage = productPage.openCart();

        return cartPage.goCheckout();
    }

    @Override
    public void sort(SortOption option) {
        sortButton.click();
        sortOptions.stream().filter(so -> Pattern.compile(option.getRegex()).matcher(so.getText()).find()).findFirst().ifPresent(ExtendedWebElement::click);
    }

    @Override
    public SequencedSet<? extends ProductBase> getProducts() {
        return new LinkedHashSet<>(products);
    }

    @Override
    public List<String> getProductNames() {
        List<String> result = new ArrayList<>();
        for (var s : products)
            result.add(s.getProductName());

        return result;
    }

    @Override
    public List<String> getProductPrices() {
        List<String> result = new ArrayList<>();
        for (var s : products)
            result.add(s.getPrice().substring(1).replace(',', '.'));

        return result;
    }

    @Override
    public CartPageBase openCart() {
        cartButton.click();
        return initPage(getDriver(), CartPageBase.class);
    }

    @Override
    public ProductBase getRandomProduct() {
        Random r = new Random();

        return products.stream().toList().get(r.nextInt(0, products.size()));
    }

    @Override
    public void processRatingPopup() {
        Assert.assertTrue(ratingAlert.isElementPresent(), "Rating popup not present!");
        Assert.assertTrue(ratingAlert.getText().contains("Thank you for submitting your review!"), "Wrong popup text!");
        ratingContinue.click();
    }
}
