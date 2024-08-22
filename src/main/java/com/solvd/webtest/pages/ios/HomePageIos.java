package com.solvd.webtest.pages.ios;

import com.solvd.webtest.pages.base.CategoriesPageBase;
import com.solvd.webtest.pages.base.HomePageBase;
import com.solvd.webtest.pages.base.LoginPageBase;
import com.solvd.webtest.pages.base.SearchResultPageBase;
import com.zebrunner.carina.utils.factory.DeviceType;
import com.zebrunner.carina.webdriver.decorator.ExtendedWebElement;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;

import java.time.Duration;

@DeviceType(pageType = DeviceType.Type.IOS_PHONE, parentClass = HomePageBase.class)
public class HomePageIos extends HomePageBase {
    @FindBy(css = ".gh-header-item__icon-link.gh-header-item__profile")
    private ExtendedWebElement myEbayButton;

    @FindBy(id = "kw")
    private ExtendedWebElement searchField;

    @FindBy(className = "gh-search__submitbtn")
    private ExtendedWebElement searchButton;

    @FindBy(css = ".gh-header-item__button.gh__needs-js.gh-header-item__menu")
    private ExtendedWebElement burgerMenu;

    @FindBy(css = ".gh__needs-js.gh-ham-menu__btn[data-gh-tracking*=\"CATEGORIES\"]")
    private ExtendedWebElement categoriesMenu;

    @FindBy(css = "li a[href*=\"all-categories\"]")
    private ExtendedWebElement allCategoriesLink;

    public HomePageIos(WebDriver driver) {
        super(driver);
    }

    @Override
    public void open() {
        super.open();
    }

    public LoginPageBase clickMyEbay() {
        myEbayButton.click();
        return initPage(getDriver(), LoginPageBase.class);
    }

    public SearchResultPageBase search(String query, String category) {
        searchField.click();
        searchField.type(query);
        searchButton.click();

        LOGGER.info("Searching for '{}' without category", query);

        return initPage(getDriver(), SearchResultPageBase.class);
    }

    @Override
    public CategoriesPageBase openCategoriesPage() {
        if (burgerMenu.isElementPresent(Duration.ofMillis(1000)))
            burgerMenu.click();

        if (categoriesMenu.isElementPresent(Duration.ofMillis(1000)))
            categoriesMenu.click();
        
        if (allCategoriesLink.isElementPresent(Duration.ofMillis(1000)))
            allCategoriesLink.click();

        return initPage(getDriver(), CategoriesPageBase.class);
    }
}
