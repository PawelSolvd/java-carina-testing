package com.solvd.apptest.pages.android;

import com.solvd.apptest.components.android.Product;
import com.solvd.apptest.components.android.SideMenu;
import com.solvd.apptest.pages.base.CatalogPageBase;
import com.solvd.apptest.pages.base.LoginPageBase;
import com.zebrunner.carina.utils.factory.DeviceType;
import com.zebrunner.carina.webdriver.decorator.ExtendedWebElement;
import com.zebrunner.carina.webdriver.decorator.PageOpeningStrategy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;

import java.util.List;

@DeviceType(pageType = DeviceType.Type.ANDROID_PHONE, parentClass = CatalogPageBase.class)
public class CatalogPage extends CatalogPageBase {
    @FindBy(id = "com.saucelabs.mydemoapp.android:id/menuIV")
    private SideMenu sideMenu;

    @FindBy
    private ExtendedWebElement cartButton;

    @FindBy
    private ExtendedWebElement sortButton;

    @FindBy(xpath = "//androidx.recyclerview.widget.RecyclerView[@content-desc=\"Displays all products of catalog\"]/android.view.ViewGroup")
    private List<Product> products;

    public CatalogPage(WebDriver driver) {
        super(driver);
        setPageOpeningStrategy(PageOpeningStrategy.BY_ELEMENT);
        setUiLoadedMarker(sideMenu);
    }

    public LoginPageBase openLoginPage() {
        return sideMenu.openLoginPage();
    }
}
