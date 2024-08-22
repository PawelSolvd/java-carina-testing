package com.solvd.apptest.pages.android;

import com.solvd.apptest.components.android.Header;
import com.solvd.apptest.pages.base.CartPageBase;
import com.solvd.apptest.pages.base.CatalogPageBase;
import com.solvd.apptest.pages.base.ProductPageBase;
import com.zebrunner.carina.utils.factory.DeviceType;
import com.zebrunner.carina.webdriver.decorator.ExtendedWebElement;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Interactive;
import org.openqa.selenium.interactions.Pause;
import org.openqa.selenium.interactions.PointerInput;
import org.openqa.selenium.interactions.Sequence;
import org.openqa.selenium.support.FindBy;
import org.testng.Assert;

import java.time.Duration;
import java.util.List;

@DeviceType(pageType = DeviceType.Type.ANDROID_PHONE, parentClass = ProductPageBase.class)
public class ProductPage extends ProductPageBase {
    @FindBy(id = "com.saucelabs.mydemoapp.android:id/header")
    private Header header;

    @FindBy(id = "com.saucelabs.mydemoapp.android:id/productTV")
    private ExtendedWebElement productName;

    @FindBy(id = "com.saucelabs.mydemoapp.android:id/cartBt")
    private ExtendedWebElement addToCartBtn;

    @FindBy(id = "com.saucelabs.mydemoapp.android:id/plusIV")
    private ExtendedWebElement plus;

    @FindBy(id = "com.saucelabs.mydemoapp.android:id/minusIV")
    private ExtendedWebElement minus;

    @FindBy(id = "com.saucelabs.mydemoapp.android:id/noTV")
    private ExtendedWebElement quantity;

    @FindBy(id = "com.saucelabs.mydemoapp.android:id/priceTV")
    private ExtendedWebElement price;

    @FindBy(xpath = "//android.view.ViewGroup[@resource-id=\"com.saucelabs.mydemoapp.android:id/rattingV\"]//android.widget.ImageView")
    private List<ExtendedWebElement> stars;

    @FindBy(id = "com.saucelabs.mydemoapp.android:id/sortTV")
    private ExtendedWebElement ratingAlert;

    @FindBy(id = "com.saucelabs.mydemoapp.android:id/closeBt")
    private ExtendedWebElement ratingContinue;

    public ProductPage(WebDriver driver) {
        super(driver);
    }

    @Override
    public void addToCart() {
        addToCartBtn.click();
    }

    @Override
    public void clickPlus(int count) {
        final var finger = new PointerInput(PointerInput.Kind.TOUCH, "finger");
        var tapPoint = new Point(plus.getRect().x, plus.getRect().y);
        var tap = new Sequence(finger, 1);
        tap.addAction(finger.createPointerMove(Duration.ofMillis(0),
                PointerInput.Origin.viewport(), tapPoint.x, tapPoint.y));

        for (int i = 0; i < count; i++) {
            tap.addAction(finger.createPointerDown(PointerInput.MouseButton.LEFT.asArg()));
            tap.addAction(finger.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));
            tap.addAction(new Pause(finger, Duration.ofMillis(50)));
        }


        ((Interactive) getDriver()).perform(List.of(tap));

        //((JavascriptExecutor) getDriver())
        //      .executeScript("mobile: clickGesture", ImmutableMap.of("x", plus.getRect().x, "y", plus.getRect().y));

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
        return header.openCart();
    }

    @Override
    public CatalogPageBase openCatalog() {
        return header.getSideMenu().openCatalogPage();
    }

    public void rateProduct(int rating) {
        waitUntil(s -> !stars.isEmpty(), Duration.ofMillis(1000));
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
