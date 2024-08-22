package com.solvd.apptest.components.android;

import com.solvd.apptest.components.base.ProductBase;
import com.solvd.apptest.pages.base.ProductPageBase;
import com.zebrunner.carina.utils.android.IAndroidUtils;
import com.zebrunner.carina.utils.mobile.IMobileUtils;
import com.zebrunner.carina.webdriver.decorator.ExtendedWebElement;
import org.openqa.selenium.By;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;

import java.util.List;

public class Product extends ProductBase implements IAndroidUtils {
    @FindBy(id = "com.saucelabs.mydemoapp.android:id/titleTV")
    private ExtendedWebElement name;

    @FindBy(id = "com.saucelabs.mydemoapp.android:id/priceTV")
    private ExtendedWebElement price;

    @FindBy(xpath = "//android.view.ViewGroup[@resource-id=\"com.saucelabs.mydemoapp.android:id/rattingV\"]//android.widget.ImageView")
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
        while (stars.isEmpty()) {
            stars = findExtendedWebElements(By.xpath("//android.view.ViewGroup[@resource-id=\"com.saucelabs.mydemoapp.android:id/rattingV\"]//android.widget.ImageView"), 1);
            swipeInContainer(this.findExtendedWebElement(By.xpath("./.."), 1), IMobileUtils.Direction.UP, 150);
        }
        var rt = stars.get(rating - 1);
        rt.click();
    }
}
