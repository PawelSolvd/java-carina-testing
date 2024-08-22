package com.solvd.apptest;

import com.solvd.apptest.pages.base.CatalogPageBase;
import com.solvd.apptest.pages.base.CheckoutPageBase;
import com.solvd.apptest.pages.base.LoginPageBase;
import com.zebrunner.carina.utils.R;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class MySauceAppIosTest extends MySauceAppCommonTest {
    @Test
    public void validLoginTest() {
        CatalogPageBase catalogPage = openCatalogPage();
        LoginPageBase loginPage = openLoginPage(catalogPage);

        loginPage.tryLogin(R.TESTDATA.get("valid-username"), R.TESTDATA.get("valid-password"));
        initPage(getDriver(), CheckoutPageBase.class).assertPageOpened();
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
        loginPage.assertPageOpened();
    }
}
