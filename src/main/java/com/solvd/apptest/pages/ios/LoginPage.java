package com.solvd.apptest.pages.ios;

import com.solvd.apptest.pages.base.LoginPageBase;
import com.zebrunner.carina.utils.factory.DeviceType;
import com.zebrunner.carina.webdriver.decorator.ExtendedWebElement;
import com.zebrunner.carina.webdriver.locator.ExtendedFindBy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;
import org.testng.Assert;

import java.time.Duration;
import java.util.List;

@DeviceType(pageType = DeviceType.Type.IOS_PHONE, parentClass = LoginPageBase.class)
public class LoginPage extends LoginPageBase {
    @FindBy(className = "XCUIElementTypeTextField")
    private ExtendedWebElement usernameField;

    @FindBy(className = "XCUIElementTypeSecureTextField")
    private ExtendedWebElement passwordField;

    @FindBy(xpath = "//XCUIElementTypeButton[contains(@name,'Login')]")
    private ExtendedWebElement loginButton;

    @FindBy(xpath = "//XCUIElementTypeScrollView/XCUIElementTypeOther[1]/XCUIElementTypeButton")
    private List<ExtendedWebElement> usernamesList;

    @FindBy(className = "XCUIElementTypeAlert")
    private ExtendedWebElement alert;

    @ExtendedFindBy(accessibilityId = "OK")
    private ExtendedWebElement okButton;

    public LoginPage(WebDriver driver) {
        super(driver);
        setUiLoadedMarker(loginButton);
    }

    public void tryLogin(String username, String password) {
        LOGGER.info("Trying to login with user \"{}\"  password \"{}\"", username, password);

        for (var e : usernamesList)
            if (e.getText().equals(username))
                e.click();

        loginButton.click();

        if (alert.isVisible(Duration.ofMillis(200)))
            Assert.assertEquals(alert.getText(), "Validation Error!");
        if (okButton.isElementPresent(Duration.ofMillis(200)))
            okButton.click();
    }
}
