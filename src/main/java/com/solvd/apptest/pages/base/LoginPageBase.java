package com.solvd.apptest.pages.base;

import org.openqa.selenium.WebDriver;

public abstract class LoginPageBase extends BasePage {
    public LoginPageBase(WebDriver driver) {
        super(driver);
    }

    public abstract void tryLogin(String username, String password);
}
