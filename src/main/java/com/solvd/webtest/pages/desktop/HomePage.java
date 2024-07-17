package com.solvd.webtest.pages.desktop;

import com.solvd.webtest.pages.base.CategoriesPageBase;
import com.solvd.webtest.pages.base.HomePageBase;
import com.solvd.webtest.pages.base.LoginPageBase;
import com.solvd.webtest.pages.base.SearchResultPageBase;
import com.zebrunner.carina.utils.factory.DeviceType;
import com.zebrunner.carina.webdriver.decorator.ExtendedWebElement;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;

import java.time.Duration;

@DeviceType(pageType = DeviceType.Type.DESKTOP, parentClass = HomePageBase.class)
public class HomePage extends HomePageBase {
    @FindBy(id = "gh-eb-My")
    private ExtendedWebElement myEbayButton;

    @FindBy(id = "gh-ac")
    private ExtendedWebElement searchField;

    @FindBy(id = "gh-cat")
    private ExtendedWebElement searchCategorySelect;

    @FindBy(id = "gh-btn")
    private ExtendedWebElement searchButton;

    @FindBy(id = "gh-shop")
    private ExtendedWebElement categoriesMenu;

    @FindBy(id = "gh-shop-see-all-center")
    private ExtendedWebElement allCategoriesLink;

    public HomePage(WebDriver driver) {
        super(driver);
    }

    @Override
    public void open() {
        super.open();
        //searchCategorySelect = new Select(driver.findElement(By.id("gh-cat")));
    }

    @Override
    public LoginPageBase clickMyEbay() {
        if (myEbayButton.isElementPresent(Duration.ofMillis(100)))
            myEbayButton.click();
        return initPage(getDriver(), LoginPageBase.class);
    }

    @Override
    public SearchResultPageBase search(String query, String category) {
        searchField.type(query);

        // new WebDriverWait(driver, Duration.ofMillis(500)).until(d -> searchCategorySelect.getWrappedElement().isDisplayed());
        // if (searchCategorySelect.getOptions().stream().map(WebElement::getText).toList().contains(category))

        try {
            if (searchCategorySelect.isElementPresent(Duration.ofMillis(100)))
                searchCategorySelect.select(category);
            searchButton.click();

            LOGGER.info("Searching for '{}' in '{}'", query, category);

        } catch (Exception e) {
            LOGGER.warn("Caught exception {}", e.toString());
        }
        return initPage(getDriver(), SearchResultPageBase.class);
    }

    @Override
    public CategoriesPageBase openCategoriesPage() {
        if (categoriesMenu.isElementPresent(Duration.ofMillis(1000)))
            categoriesMenu.click();

        if (allCategoriesLink.isElementPresent(Duration.ofMillis(1000)))
            allCategoriesLink.click();

        return new CategoriesPage(getDriver());
    }
}
