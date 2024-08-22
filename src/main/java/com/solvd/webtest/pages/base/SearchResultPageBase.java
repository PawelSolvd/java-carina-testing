package com.solvd.webtest.pages.base;

import com.solvd.webtest.components.ResultBase;
import com.zebrunner.carina.utils.R;
import org.openqa.selenium.WebDriver;

import java.util.List;

public abstract class SearchResultPageBase extends BasePage {
    public SearchResultPageBase(WebDriver driver) {
        super(driver, R.CONFIG.get("searchResultPage.url"));
    }

    @Override
    public boolean isOpen() {
        super.isOpen();
        return getDriver().getCurrentUrl().startsWith(url);
    }

    public abstract void printResults();

    public abstract List<? extends ResultBase> getResults();

    public abstract void setSortOption(String option);

    public abstract List<? extends ResultBase> getNoSponsoredResults();
}

