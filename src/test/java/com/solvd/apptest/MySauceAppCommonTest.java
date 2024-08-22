package com.solvd.apptest;

import com.solvd.apptest.enums.SortOption;
import com.solvd.apptest.pages.base.*;
import com.zebrunner.carina.utils.R;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.List;
import java.util.Random;
import java.util.function.Supplier;

import static org.testng.Assert.*;

public class MySauceAppCommonTest extends AbstractTest {
    public CatalogPageBase openCatalogPage() {
        CatalogPageBase catalogPage = initPage(getDriver(), CatalogPageBase.class);
        catalogPage.assertPageOpened();
        return catalogPage;
    }

    public LoginPageBase openLoginPage(CatalogPageBase catalogPage) {
        LoginPageBase loginPage = catalogPage.openLoginPage();
        loginPage.assertPageOpened();
        return loginPage;
    }

    public CatalogPageBase addRandomProductToCart(CatalogPageBase catalogPage) {
        Random r = new Random();
        var product = catalogPage.getRandomProduct();

        ProductPageBase productPage = product.openProductPage();
        for (int i = 0; i < r.nextInt(0, 100); i++)
            productPage.clickPlus(10);
        productPage.addToCart();

        return productPage.openCatalog();
    }

    @DataProvider(name = "sortOptions")
    public Object[] sortOptions() {
        return SortOption.values();
    }

    @Test(dataProvider = "sortOptions")
    public void sortTest(SortOption option) {
        CatalogPageBase catalogPage = openCatalogPage();
        Supplier<List<String>> fun = null;

        switch (option) {
            case BY_NAME_ASC:
            case BY_NAME_DSC:
                fun = catalogPage::getProductNames;
                break;

            case BY_PRICE_ASC:
            case BY_PRICE_DSC:
                fun = catalogPage::getProductPrices;
                break;
        }

        List<String> strings = fun.get();
        LOGGER.info("Unsorted = {}", strings);

        strings.sort(option.getComparator());
        LOGGER.info("Expected = {}", strings);

        catalogPage.sort(option);
        var sorted = fun.get();
        LOGGER.info("Sorted = {}", sorted);

        assertEquals(strings, sorted);
    }

    @Test
    public void addToCartTest() {
        CatalogPageBase catalogPage = openCatalogPage();
        Random r = new Random();
        var product = catalogPage.getRandomProduct();

        ProductPageBase productPage = product.openProductPage();
        for (int i = 0; i < r.nextInt(0, 10); i++)
            productPage.clickPlus(10);

        int count = productPage.getQuantity();
        String name = productPage.getProductName();
        String price = productPage.getPrice();
        productPage.addToCart();
        LOGGER.info("Details on product page {} {} {}", name, count, price);

        CartPageBase cartPage = productPage.openCart();
        var prod = cartPage.getProducts().getFirst();

        LOGGER.info("Details in cart {} {} {}", prod.getProductName(), prod.getQuantity(), prod.getPrice());

        assertEquals(name, prod.getProductName(), "Product names are different");
        assertEquals(count, prod.getQuantity(), "Product quantities are different");
        assertEquals(price, prod.getPrice().substring(1).replace(',', '.'), "Product prices are different");


        LOGGER.info("Total price = {}", cartPage.getTotalPrice());
        assertEquals(count * Float.parseFloat(cartPage.getProducts().getFirst().getPrice().substring(1).replace(',', '.')), cartPage.getTotalPrice(), "Wrong total price");
    }

    @DataProvider(name = "ratingValues")
    public Object[] ratingValues() {
        return new Object[]{1, 2, 3, 4, 5};
    }

    @Test(dataProvider = "ratingValues")
    public void reviewProductOnCatalogTest(int rating) {
        CatalogPageBase catalogPage = openCatalogPage();
        var product = catalogPage.getRandomProduct();

        product.rateProduct(rating);
        catalogPage.processRatingPopup();
    }

    @Test(dataProvider = "ratingValues")
    public void reviewProductOnProductPageTest(int rating) {
        CatalogPageBase catalogPage = openCatalogPage();
        ProductPageBase productPage = catalogPage.getRandomProduct().openProductPage();

        productPage.rateProduct(rating);
        productPage.processRatingPopup();
    }

    @Test
    public void checkoutTest() {
        CatalogPageBase catalogPage = openCatalogPage();

        Random r = new Random();
        var product = catalogPage.getRandomProduct();

        ProductPageBase productPage = product.openProductPage();
        productPage.addToCart();

        CartPageBase cartPage = catalogPage.openCart();
        LoginPageBase loginPage = cartPage.goCheckout();

        loginPage.tryLogin(R.TESTDATA.get("valid-username"), R.TESTDATA.get("valid-password"));

        CheckoutPageBase checkoutPage = initPage(getDriver(), CheckoutPageBase.class);

        checkoutPage.inputShippingInfo("1", "1", "1", "1", "1");
        checkoutPage.inputPaymentInfo("1", "1", "1", "1");

        catalogPage = checkoutPage.placeOrder();
        catalogPage.assertPageOpened();
    }

    @Test
    public void removeCartProductTest() {
        CatalogPageBase catalogPage = openCatalogPage();

        Random r = new Random();
        var product = catalogPage.getRandomProduct();

        ProductPageBase productPage = product.openProductPage();
        productPage.addToCart();

        CartPageBase cartPage = productPage.openCart();
        assertFalse(cartPage.getProducts().isEmpty(), "Cart is empty!");

        cartPage.getProducts().getFirst().remove();
        assertTrue(cartPage.getProducts().isEmpty(), "Cart is not empty!");
    }

    @Test
    public void emptyCartTest() {
        CatalogPageBase catalogPage = openCatalogPage();
        CartPageBase cartPage = catalogPage.openCart();

        assertTrue(cartPage.getProducts().isEmpty(), "Cart is not empty!");
        catalogPage = cartPage.goShopping();
        catalogPage.assertPageOpened();
    }
}
