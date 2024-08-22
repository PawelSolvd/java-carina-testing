package com.solvd.apptest.pages.android;

import com.solvd.apptest.components.android.Header;
import com.solvd.apptest.components.android.Product;
import com.solvd.apptest.components.base.ProductBase;
import com.solvd.apptest.enums.SortOption;
import com.solvd.apptest.pages.base.CartPageBase;
import com.solvd.apptest.pages.base.CatalogPageBase;
import com.solvd.apptest.pages.base.LoginPageBase;
import com.zebrunner.carina.utils.android.IAndroidUtils;
import com.zebrunner.carina.utils.factory.DeviceType;
import com.zebrunner.carina.webdriver.decorator.ExtendedWebElement;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;
import org.testng.Assert;

import java.time.Duration;
import java.util.*;
import java.util.function.Function;
import java.util.regex.Pattern;

@DeviceType(pageType = DeviceType.Type.ANDROID_PHONE, parentClass = CatalogPageBase.class)
public class CatalogPage extends CatalogPageBase implements IAndroidUtils {
    @FindBy(id = "com.saucelabs.mydemoapp.android:id/header")
    private Header header;

    @FindBy(id = "com.saucelabs.mydemoapp.android:id/sortIV")
    private ExtendedWebElement sortButton;

    @FindBy(xpath = "//android.view.ViewGroup[contains(@content-desc,\"order\")]")
    private List<ExtendedWebElement> sortOptions;

    //@FindBy(xpath = "//androidx.recyclerview.widget.RecyclerView[@content-desc=\"Displays all products of catalog\"]/android.view.ViewGroup")
    private SequencedSet<Product> products = new LinkedHashSet<>();

    @FindBy(xpath = "//android.widget.FrameLayout[@content-desc=\"Container for fragments\"]/android.view.ViewGroup")
    private ExtendedWebElement productContainer;

    @FindBy(id = "com.saucelabs.mydemoapp.android:id/socialLL")
    private ExtendedWebElement footer;

    @FindBy(id = "com.saucelabs.mydemoapp.android:id/sortTV")
    private ExtendedWebElement ratingAlert;

    @FindBy(id = "com.saucelabs.mydemoapp.android:id/closeBt")
    private ExtendedWebElement ratingContinue;

    public CatalogPage(WebDriver driver) {
        super(driver);
        setUiLoadedMarker(productContainer);
    }

    public LoginPageBase openLoginPage() {
        return header.getSideMenu().openLoginPage();
    }

    public void sort(SortOption option) {
        sortButton.click();
        sortOptions.stream().filter(so -> Pattern.compile(option.getRegex()).matcher(so.getAttribute("content-desc")).find()).findFirst().ifPresent(ExtendedWebElement::click);
    }

    public SequencedSet<Product> getProducts() {
        products = new LinkedHashSet<>();
        do {
            var items =
                    findExtendedWebElements
                            (By.xpath("//androidx.recyclerview.widget.RecyclerView[@content-desc=\"Displays all products of catalog\"]/android.view.ViewGroup"), 1);

            if (items.isEmpty())
                return products;

            for (var e : items)
                products.add(new Product(getDriver(), e.getSearchContext(), e));

            while (products.getLast().isVisible(Duration.ofMillis(10)) && !footer.isVisible(Duration.ofMillis(10)))
                swipeInContainer(productContainer, Direction.UP, 150);
        } while (!footer.isVisible(Duration.ofMillis(10)));

        scrollToTopProduct(Product::getNameElement);

        //System.out.println(getProductNames());
        return products;
    }

    public List<String> getProductNames() {
        return getProductProperty(Product::getNameElement);
    }

    @Override
    public List<String> getProductPrices() {
        return new ArrayList<>(getProductProperty(Product::getPriceElement).stream().map(s -> s.substring(1).replace(',', '.')).toList());
    }

    private List<String> getProductProperty(Function<Product, ExtendedWebElement> fun) {
        getProducts();
        List<String> strings = new ArrayList<>();
        /*
        while (!products.getFirst().isVisible(Duration.ofMillis(10)))
            swipeInContainer(productContainer, Direction.DOWN, 1000);
        int n = 0;
        for (var p : products) {
            names.add(p.getProductName());
            var r = p.getRect();
            System.out.println(r.x + " " + r.y + " " + r.height + " " + r.width);

            n++;
            if (n % 2 == 0)
                swipe(r.x, r.y + r.height, r.x, r.y, 50);
            if (n % 2 != 0)
                while (p.isVisible(Duration.ofMillis(10)) && !footer.isVisible(Duration.ofMillis(10)))
                    swipeInContainer(productContainer, Direction.UP, 1000);

            n++;
        }

        while (!footer.isVisible(10)) {
            var items =
                    findExtendedWebElements
                            (By.xpath("//androidx.recyclerview.widget.RecyclerView[@content-desc=\\\"Displays all products of catalog\\\"]/android.view.ViewGroup\""))
                            .stream().map(e -> new Product(getDriver(), e)).toList();

            while (products.getLast().isVisible(Duration.ofMillis(10)) && !footer.isVisible(Duration.ofMillis(10)))
                swipeInContainer(productContainer, Direction.UP, 1000);

            products.addAll(items);
        }*/

        scrollToTopProduct(fun);

        for (var p : products) {
            swipe(fun.apply(p), Direction.UP, 50, 150);
            strings.add(fun.apply(p).getText());
        }

        scrollToTopProduct(fun);

        return strings;
    }

    public CartPageBase openCart() {
        return header.openCart();
    }

    public ProductBase getRandomProduct() {
        Random r = new Random();

        var products = getProducts();
        int index = r.nextInt(0, products.size());

        // product index 1 is bugged
        while (index == 1)
            index = r.nextInt(0, products.size());

        LOGGER.info("Index {}", index);
        return products.stream().toList().get(index);
    }

    public void processRatingPopup() {
        Assert.assertTrue(ratingAlert.isElementPresent(), "Rating popup not present!");
        Assert.assertTrue(ratingAlert.getText().contains("Thank you for submitting your review!"), "Wrong popup text!");
        ratingContinue.click();
    }

    private void scrollToTopProduct(Function<Product, ExtendedWebElement> fun) {
        LOGGER.info("Scrolling to top");
        while (!fun.apply(products.getFirst()).isVisible(Duration.ofMillis(10)))
            swipeInContainer(productContainer, Direction.DOWN, 100);

        swipeInContainer(productContainer, Direction.DOWN, 100);
    }
}
