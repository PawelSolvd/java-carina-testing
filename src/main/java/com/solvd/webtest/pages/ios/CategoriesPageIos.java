package com.solvd.webtest.pages.ios;

import com.solvd.webtest.pages.base.CategoriesPageBase;
import com.zebrunner.carina.utils.factory.DeviceType;
import com.zebrunner.carina.webdriver.decorator.ExtendedWebElement;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;

import java.util.List;

@DeviceType(pageType = DeviceType.Type.IOS_PHONE, parentClass = CategoriesPageBase.class)
public class CategoriesPageIos extends CategoriesPageBase {
    @FindBy(css = "span.cat-title , .l1-wrapper >.cat-container > li > .cat-title")
    private List<ExtendedWebElement> categoryNames;

    public CategoriesPageIos(WebDriver driver) {
        super(driver);
    }

    @Override
    public List<String> getCategories() {
        return categoryNames.stream().map(ExtendedWebElement::getText).toList();
    }
}
