package com.solvd.apptest.pages.android;

import com.zebrunner.carina.webdriver.decorator.ExtendedWebElement;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public enum LoginError {
    USERNAME_MISSING("com.saucelabs.mydemoapp.android:id/nameErrorTV", "Username is required"),
    PASSWORD_MISSING("com.saucelabs.mydemoapp.android:id/passwordErrorTV", "Enter Password"),
    USER_BLOCKED("com.saucelabs.mydemoapp.android:id/passwordErrorTV", "Sorry this user has been locked out.");
    private ExtendedWebElement errorLabel;
    private final String text;
    private final String locator;

    private LoginError(String locator, String text) {
        this.locator = locator;
        this.text = text;
    }

    public void assertPresent(WebDriver driver) {
        errorLabel = new ExtendedWebElement(By.id(locator), "n/a", driver, driver);
        errorLabel.assertElementWithTextPresent(text);
    }
}
