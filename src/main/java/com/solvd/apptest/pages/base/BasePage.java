package com.solvd.apptest.pages.base;

import com.zebrunner.carina.webdriver.gui.AbstractPage;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class BasePage extends AbstractPage {
    protected static Logger LOGGER;

    public BasePage(WebDriver driver) {
        super(driver);
        LOGGER = LoggerFactory.getLogger(getClass());
    }
}
