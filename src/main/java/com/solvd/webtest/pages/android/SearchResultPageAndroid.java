package com.solvd.webtest.pages.android;

import com.solvd.webtest.components.ResultMobile;
import com.solvd.webtest.pages.base.SearchResultPageBase;
import com.zebrunner.carina.utils.factory.DeviceType;
import com.zebrunner.carina.webdriver.decorator.ExtendedWebElement;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;

import java.time.Duration;
import java.util.*;

@DeviceType(pageType = DeviceType.Type.ANDROID_PHONE, parentClass = SearchResultPageBase.class)
public class SearchResultPageAndroid extends SearchResultPageBase {
    @FindBy(css = ".srp-results > li:not(.srp-river-answer)  .s-item__wrapper")
    private List<ResultMobile> results;

    @FindBy(css = ".srp-controls__control--link.srp-controls__control--link-right.srp-controls__control--link-enabled.btn")
    private ExtendedWebElement sortingMenuBtn;

    @FindBy(css = "ul.srp-sort__menu span.s-sort-label")
    private List<ExtendedWebElement> sortOptions;

    public SearchResultPageAndroid(WebDriver driver) {
        super(driver);
    }

    @Override
    public boolean isOpen() {
        super.isOpen();
        return getDriver().getCurrentUrl().startsWith(url);
    }

    public void printResults() {
        for (var r : results)
            LOGGER.info(r.print());
    }

    public List<ResultMobile> getResults() {
        waitUntil(s -> !results.isEmpty(), Duration.ofMillis(2000));
        return results;
    }

    public void setSortOption(String option) {
        sortingMenuBtn.click();

        if (!sortOptions.isEmpty())
            for (var o : sortOptions)
                if (o.getText().equals(option)) {
                    o.click();
                    results.clear();
                    for (var e : findExtendedWebElements(By.cssSelector(".srp-results > li:not(.srp-river-answer)  .s-item__wrapper")))
                        results.add(new ResultMobile(driver, e.getSearchContext()));
                    break;
                }
    }

    public List<ResultMobile> getNoSponsoredResults() {
        Map<String, List<ResultMobile>> resultsGrouped = new HashMap<>();

        for (var r : results) {
            resultsGrouped.computeIfAbsent(r.getSponsored(), k -> new ArrayList<>());
            resultsGrouped.get(r.getSponsored()).add(r);
        }

        return resultsGrouped.values().stream().max(Comparator.comparing(List::size)).orElse(new ArrayList<>());
    }
}
