package com.solvd.apptest.pages.base;

import com.solvd.apptest.components.base.ProductBase;
import com.solvd.apptest.enums.SortOption;
import org.openqa.selenium.WebDriver;

import java.util.List;
import java.util.SequencedSet;

public abstract class CatalogPageBase extends BasePage {
    public CatalogPageBase(WebDriver driver) {
        super(driver);
    }

    public abstract LoginPageBase openLoginPage();

    public abstract void sort(SortOption option);

    public abstract SequencedSet<? extends ProductBase> getProducts();

    public abstract List<String> getProductNames();

    public abstract List<String> getProductPrices();

    public abstract CartPageBase openCart();

    public abstract ProductBase getRandomProduct();

    public abstract void processRatingPopup();

}
