package com.solvd.apptest;

import com.solvd.apptest.pages.android.LoginError;
import com.solvd.apptest.pages.base.CatalogPageBase;
import com.solvd.apptest.pages.base.LoginPageBase;
import com.zebrunner.carina.core.IAbstractTest;
import com.zebrunner.carina.utils.R;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.time.Duration;

public class AndroidTest implements IAbstractTest {
    @Test
    public void validLoginTest() {
        CatalogPageBase catalogPage = initPage(getDriver(), CatalogPageBase.class);
        catalogPage.assertPageOpened(3000);
        LoginPageBase loginPage = catalogPage.openLoginPage();

        loginPage.tryLogin(R.TESTDATA.get("valid-username"), R.TESTDATA.get("valid-password"));
        catalogPage.assertPageOpened(Duration.ofMillis(2000));
    }

    @Test
    public void blockedLoginTest() {
        CatalogPageBase catalogPage = initPage(getDriver(), CatalogPageBase.class);
        LoginPageBase loginPage = catalogPage.openLoginPage();

        loginPage.tryLogin(R.TESTDATA.get("blocked-username"), R.TESTDATA.get("blocked-password"));
        loginPage.assertPageOpened();

        LoginError.USER_BLOCKED.assertPresent(getDriver());
    }

    @DataProvider(name = "invalidLoginData")
    public Object[][] invalidLoginData() {
        return new Object[][]{
                {"adam", ""},
                {"", "adam"},
                {"", ""}
        };
    }

    @Test(dataProvider = "invalidLoginData")
    public void invalidLoginTest(String username, String password) {
        CatalogPageBase catalogPage = initPage(getDriver(), CatalogPageBase.class);
        LoginPageBase loginPage = catalogPage.openLoginPage();

        loginPage.tryLogin(username, password);

        if (username.isEmpty())
            LoginError.USERNAME_MISSING.assertPresent(getDriver());
        else if (password.isEmpty())
            LoginError.PASSWORD_MISSING.assertPresent(getDriver());

        loginPage.assertPageOpened();
    }
}
