package com.solvd.webtest.pages.base;

import com.zebrunner.carina.utils.R;
import org.openqa.selenium.WebDriver;

import java.util.List;

public abstract class CategoriesPageBase extends BasePage {
    public CategoriesPageBase(WebDriver driver) {
        super(driver, R.CONFIG.get("categoriesPage.url"));
    }

    @Override
    public boolean isOpen() {
        super.isOpen();
        return getDriver().getCurrentUrl().startsWith(url);
    }

    public abstract List<String> getCategories();
}

