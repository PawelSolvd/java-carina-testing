package com.solvd.apptest.components.android;

import com.solvd.apptest.components.base.HeaderBase;
import com.solvd.apptest.pages.base.CartPageBase;
import com.zebrunner.carina.webdriver.decorator.ExtendedWebElement;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;

public class Header extends HeaderBase {
    @FindBy(id = "com.saucelabs.mydemoapp.android:id/menuIV")
    private SideMenu sideMenu;

    @FindBy(id = "com.saucelabs.mydemoapp.android:id/cartIV")
    private ExtendedWebElement cartButton;

    public Header(WebDriver driver, SearchContext searchContext) {
        super(driver, searchContext);
    }

    @Override
    public SideMenu getSideMenu() {
        return sideMenu;
    }

    public CartPageBase openCart() {
        cartButton.click();
        return initPage(getDriver(), CartPageBase.class);
    }
}
