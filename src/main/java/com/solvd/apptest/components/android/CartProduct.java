package com.solvd.apptest.components.android;

import com.solvd.apptest.components.base.CartProductBase;
import com.solvd.apptest.pages.base.ProductPageBase;
import com.zebrunner.carina.webdriver.decorator.ExtendedWebElement;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;

import java.util.List;

public class CartProduct extends CartProductBase {
    @FindBy(id = "com.saucelabs.mydemoapp.android:id/removeBt")
    private ExtendedWebElement removeBtn;

    @FindBy(id = "com.saucelabs.mydemoapp.android:id/noTV")
    private ExtendedWebElement quantity;

    @FindBy(id = "com.saucelabs.mydemoapp.android:id/minusIV")
    private ExtendedWebElement plus;

    @FindBy(id = "com.saucelabs.mydemoapp.android:id/minusIV")
    private ExtendedWebElement minus;

    public CartProduct(WebDriver driver, SearchContext searchContext, ExtendedWebElement element) {
        super(driver, searchContext, element);
    }

    public Integer getQuantity() {
        return Integer.valueOf(quantity.getText());
    }

    public void remove() {
        removeBtn.click();
    }

    @FindBy(id = "com.saucelabs.mydemoapp.android:id/titleTV")
    private ExtendedWebElement name;

    @FindBy(id = "com.saucelabs.mydemoapp.android:id/priceTV")
    private ExtendedWebElement price;

    @FindBy(xpath = "//android.view.ViewGroup[@resource-id=\"com.saucelabs.mydemoapp.android:id/rattingV\"]//android.widget.ImageView")
    private List<ExtendedWebElement> stars;
    
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
