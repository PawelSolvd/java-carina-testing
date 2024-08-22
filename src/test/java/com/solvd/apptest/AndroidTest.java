package com.solvd.apptest;

import com.solvd.apptest.enums.android.LoginError;
import com.solvd.apptest.pages.base.CatalogPageBase;
import com.solvd.apptest.pages.base.LoginPageBase;
import com.zebrunner.carina.utils.R;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class AndroidTest extends MySauceAppCommonTest {
    @Test
    public void validLoginTest() {
        CatalogPageBase catalogPage = openCatalogPage();
        LoginPageBase loginPage = openLoginPage(catalogPage);

        loginPage.tryLogin(R.TESTDATA.get("valid-username"), R.TESTDATA.get("valid-password"));
        catalogPage.assertPageOpened();
    }

    @Test
    public void blockedLoginTest() {
        CatalogPageBase catalogPage = openCatalogPage();
        LoginPageBase loginPage = openLoginPage(catalogPage);

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
        CatalogPageBase catalogPage = openCatalogPage();
        LoginPageBase loginPage = openLoginPage(catalogPage);

        loginPage.tryLogin(username, password);

        if (username.isEmpty())
            LoginError.USERNAME_MISSING.assertPresent(getDriver());
        else if (password.isEmpty())
            LoginError.PASSWORD_MISSING.assertPresent(getDriver());

        loginPage.assertPageOpened();
    }
}
