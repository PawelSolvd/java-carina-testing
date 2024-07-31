package com.solvd.apptest.components.android;

import com.solvd.apptest.components.base.SideMenuBase;
import com.solvd.apptest.pages.base.LoginPageBase;
import com.zebrunner.carina.webdriver.decorator.ExtendedWebElement;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;

public class SideMenu extends SideMenuBase {
    @FindBy(xpath = "//android.widget.TextView[@resource-id=\"com.saucelabs.mydemoapp.android:id/itemTV\" and @text=\"Log In\"]")
    private ExtendedWebElement loginButton;

    public SideMenu(WebDriver driver) {
        super(driver);
        // context not based on SideMenu locator
        loginButton.setSearchContext(getDriver());
    }

    public LoginPageBase openLoginPage() {
        this.click();
        loginButton.click();

        return initPage(getDriver(), LoginPageBase.class);
    }
}
