package com.solvd.apptest.pages.base;

import com.zebrunner.carina.webdriver.gui.AbstractPage;
import org.openqa.selenium.WebDriver;

public abstract class CatalogPageBase extends AbstractPage {
    public CatalogPageBase(WebDriver driver) {
        super(driver);
    }

    public abstract LoginPageBase openLoginPage();
}
