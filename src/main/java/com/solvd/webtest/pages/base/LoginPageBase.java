package com.solvd.webtest.pages.base;

import com.zebrunner.carina.utils.R;
import org.openqa.selenium.WebDriver;

public abstract class LoginPageBase extends BasePage {
    public LoginPageBase(WebDriver driver) {
        super(driver, R.CONFIG.get("loginPage.url"));
    }

    @Override
    public boolean isOpen() {
        super.isOpen();
        return getDriver().getCurrentUrl().startsWith(url);
    }

    public abstract boolean tryLogin(String username);
}
