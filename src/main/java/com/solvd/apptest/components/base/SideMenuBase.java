package com.solvd.apptest.components.base;

import com.solvd.apptest.pages.base.LoginPageBase;
import com.zebrunner.carina.webdriver.gui.AbstractUIObject;
import org.openqa.selenium.WebDriver;

public abstract class SideMenuBase extends AbstractUIObject {
    public SideMenuBase(WebDriver driver) {
        super(driver);
    }

    public abstract LoginPageBase openLoginPage();
}
