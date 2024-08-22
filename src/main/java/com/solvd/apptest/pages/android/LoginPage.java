package com.solvd.apptest.pages.android;

import com.solvd.apptest.pages.base.LoginPageBase;
import com.zebrunner.carina.utils.factory.DeviceType;
import com.zebrunner.carina.webdriver.decorator.ExtendedWebElement;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;

@DeviceType(pageType = DeviceType.Type.ANDROID_PHONE, parentClass = LoginPageBase.class)
public class LoginPage extends LoginPageBase {
    @FindBy(id = "nameET")
    private ExtendedWebElement usernameField;

    @FindBy(id = "com.saucelabs.mydemoapp.android:id/passwordET")
    private ExtendedWebElement passwordField;

    @FindBy(id = "com.saucelabs.mydemoapp.android:id/loginBtn")
    private ExtendedWebElement loginButton;

    public LoginPage(WebDriver driver) {
        super(driver);
        setUiLoadedMarker(loginButton);
    }

    public void tryLogin(String username, String password) {
        LOGGER.info("Trying to login with user \"{}\"  password \"{}\"", username, password);
        usernameField.type(username);
        passwordField.type(password);

        loginButton.click();
    }
}
