package com.solvd.apptest.components.base;

import com.solvd.apptest.pages.base.CartPageBase;
import com.zebrunner.carina.webdriver.gui.AbstractUIObject;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebDriver;

public abstract class HeaderBase extends AbstractUIObject {
    public HeaderBase(WebDriver driver, SearchContext searchContext) {
        super(driver, searchContext);
    }

    public abstract SideMenuBase getSideMenu();

    public abstract CartPageBase openCart();
}
