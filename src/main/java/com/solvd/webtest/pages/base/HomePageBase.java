package com.solvd.webtest.pages.base;

import com.zebrunner.carina.utils.R;
import org.openqa.selenium.WebDriver;

public abstract class HomePageBase extends BasePage {
    public HomePageBase(WebDriver driver) {
        super(driver, R.CONFIG.get("homePage.url"));
    }

    @Override
    public void open() {
        super.open();
    }

    public abstract LoginPageBase clickMyEbay();

    public abstract SearchResultPageBase search(String query, String category);

    public abstract CategoriesPageBase openCategoriesPage();
}
