package com.solvd.webtest.pages.ios;

import com.solvd.webtest.pages.base.LoginPageBase;
import com.zebrunner.carina.utils.factory.DeviceType;
import com.zebrunner.carina.webdriver.decorator.ExtendedWebElement;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;

import java.time.Duration;

@DeviceType(pageType = DeviceType.Type.IOS_PHONE, parentClass = LoginPageBase.class)
public class LoginPageIos extends LoginPageBase {
    @FindBy(id = "userid")
    private ExtendedWebElement usernameField;

    @FindBy(id = "signin-continue-btn")
    private ExtendedWebElement continueButton;

    @FindBy(id = "signin-error-msg")
    private ExtendedWebElement signinError;

    @FindBy(id = "user-info")
    private ExtendedWebElement userInfo;

    public LoginPageIos(WebDriver driver) {
        super(driver);
        setUiLoadedMarker(usernameField);
    }

    @Override
    public boolean isOpen() {
        super.isOpen();
        return getDriver().getCurrentUrl().startsWith(url);
    }

    public boolean tryLogin(String username) {
        usernameField.type(username);
        continueButton.click();

        if (signinError.isElementPresent(Duration.ofMillis(1000)))
            return false;
        else if (userInfo.isElementPresent(Duration.ofMillis(1000)) && userInfo.getText().equals(username))
            return true;

        LOGGER.warn("Unexpected sign in problems");
        return false;
    }
}
