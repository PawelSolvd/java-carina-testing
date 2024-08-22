package com.solvd.apptest.pages.android;

import com.solvd.apptest.components.android.CartProduct;
import com.solvd.apptest.components.android.Header;
import com.solvd.apptest.pages.base.CartPageBase;
import com.solvd.apptest.pages.base.CatalogPageBase;
import com.solvd.apptest.pages.base.LoginPageBase;
import com.zebrunner.carina.utils.android.IAndroidUtils;
import com.zebrunner.carina.utils.factory.DeviceType;
import com.zebrunner.carina.utils.mobile.IMobileUtils;
import com.zebrunner.carina.webdriver.decorator.ExtendedWebElement;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;

import java.time.Duration;
import java.util.LinkedHashSet;
import java.util.SequencedSet;

@DeviceType(pageType = DeviceType.Type.ANDROID_PHONE, parentClass = CartPageBase.class)
public class CartPage extends CartPageBase implements IAndroidUtils {
    @FindBy(id = "com.saucelabs.mydemoapp.android:id/header")
    private Header header;

    private SequencedSet<CartProduct> products;

    @FindBy(id = "com.saucelabs.mydemoapp.android:id/cartBt")
    private ExtendedWebElement checkoutBtn;

    @FindBy(id = "com.saucelabs.mydemoapp.android:id/itemsTV")
    private ExtendedWebElement itemCount;

    @FindBy(id = "com.saucelabs.mydemoapp.android:id/totalPriceTV")
    private ExtendedWebElement totalPrice;

    @FindBy(id = "com.saucelabs.mydemoapp.android:id/socialLL")
    private ExtendedWebElement footer;

    @FindBy(id = "com.saucelabs.mydemoapp.android:id/cartCL")
    private ExtendedWebElement productContainer;

    @FindBy(id = "com.saucelabs.mydemoapp.android:id/shoppingBt")
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
    public SequencedSet<CartProduct> getProducts() {
        products = new LinkedHashSet<>();
        do {
            var items =
                    findExtendedWebElements
                            (By.xpath("//androidx.recyclerview.widget.RecyclerView[@content-desc=\"Displays list of selected products\"]/android.view.ViewGroup"), 1);

            if (items.isEmpty())
                return products;

            for (var e : items)
                products.add(new CartProduct(getDriver(), e.getSearchContext(), e));

            while (products.getLast().isVisible(Duration.ofMillis(10)) && !footer.isVisible(Duration.ofMillis(10)))
                swipeInContainer(productContainer, IMobileUtils.Direction.UP, 150);
        } while (!footer.isVisible(Duration.ofMillis(10)));

        return products;
    }

    public Float getTotalPrice() {
        return Float.valueOf(totalPrice.getText().substring(2).replace(',', '.'));
    }

    public CatalogPageBase goShopping() {
        goShoppingButton.click();
        return initPage(getDriver(), CatalogPageBase.class);
    }
}
