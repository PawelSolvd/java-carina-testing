package com.solvd.webtest.pages.desktop;

import com.solvd.webtest.pages.base.CategoriesPageBase;
import com.zebrunner.carina.utils.factory.DeviceType;
import com.zebrunner.carina.webdriver.decorator.ExtendedWebElement;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;

import java.util.List;

@DeviceType(pageType = DeviceType.Type.DESKTOP, parentClass = CategoriesPageBase.class)
public class CategoriesPage extends CategoriesPageBase {
    @FindBy(className = "cat-title")
    private List<ExtendedWebElement> categoryNames;

    public CategoriesPage(WebDriver driver) {
        super(driver);
    }

    @Override
    public List<String> getCategories() {
        return categoryNames.stream().map(ExtendedWebElement::getText).toList();
    }
}
