package com.solvd.webtest.pages.android;

import com.solvd.webtest.pages.base.CategoriesPageBase;
import com.zebrunner.carina.utils.factory.DeviceType;
import com.zebrunner.carina.webdriver.decorator.ExtendedWebElement;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;

import java.util.List;

@DeviceType(pageType = DeviceType.Type.ANDROID_PHONE, parentClass = CategoriesPageBase.class)
public class CategoriesPageAndroid extends CategoriesPageBase {
    @FindBy(css = ".cat-title")
    private List<ExtendedWebElement> categoryNames;

    public CategoriesPageAndroid(WebDriver driver) {
        super(driver);
    }

    @Override
    public List<String> getCategories() {
        return categoryNames.stream().map(ExtendedWebElement::getText).toList();
    }
}
