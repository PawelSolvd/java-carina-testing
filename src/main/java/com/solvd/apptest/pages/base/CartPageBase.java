package com.solvd.apptest.pages.base;

import com.solvd.apptest.components.base.CartProductBase;
import org.openqa.selenium.WebDriver;

import java.util.SequencedSet;

public abstract class CartPageBase extends BasePage {
    public CartPageBase(WebDriver driver) {
        super(driver);
    }

    public abstract LoginPageBase goCheckout();

    public abstract SequencedSet<? extends CartProductBase> getProducts();

    public abstract Float getTotalPrice();

    public abstract CatalogPageBase goShopping();
}
