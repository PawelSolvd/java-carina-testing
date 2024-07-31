package com.solvd.webtest;

import com.solvd.webtest.components.ResultBase;
import com.solvd.webtest.pages.base.CategoriesPageBase;
import com.solvd.webtest.pages.base.HomePageBase;
import com.solvd.webtest.pages.base.LoginPageBase;
import com.solvd.webtest.pages.base.SearchResultPageBase;
import com.zebrunner.carina.core.registrar.tag.TestTag;
import com.zebrunner.carina.utils.R;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

import static org.testng.Assert.*;

public class EBayTest extends AbstractTest {
    public HomePageBase openHomePage() {
        HomePageBase homePage = initPage(getDriver(), HomePageBase.class);
        homePage.open();
        assertTrue(homePage.isOpen(), "HomePage is not opened");

        return homePage;
    }

    public LoginPageBase openLoginPage(HomePageBase homePage) {
        LoginPageBase loginPage = homePage.clickMyEbay();
        loginPage.waitUntil(l -> loginPage.isOpen(), Duration.ofMillis(3000));
        assertTrue(loginPage.isOpen(), "LoginPage is not opened");

        return loginPage;
    }

    public SearchResultPageBase openSearchResultPage(HomePageBase homePage, String query, String category) {
        SearchResultPageBase searchResultPage = homePage.search(query, category);
        searchResultPage.waitUntil(s -> searchResultPage.isOpen(), Duration.ofMillis(3000));
        assertTrue(searchResultPage.isOpen(), "SearchResultPage is not opened");

        return searchResultPage;
    }

    public CategoriesPageBase openCategoriesPage(HomePageBase homePage) {
        CategoriesPageBase categoriesPage = homePage.openCategoriesPage();
        categoriesPage.waitUntil(c -> categoriesPage.isOpen(), Duration.ofMillis(3000));
        assertTrue(categoriesPage.isOpen(), "CategoriesPage is not opened");

        return categoriesPage;
    }


    @Test
    @TestTag(name = "platform", value = "desktop")
    @TestTag(name = "platform", value = "mobile")
    public void emptyLoginTest() {
        HomePageBase homePage = openHomePage();
        LoginPageBase loginPage = openLoginPage(homePage);

        assertFalse(loginPage.tryLogin(""), "Missing login error message");
    }

    @Test
    @TestTag(name = "platform", value = "desktop")
    @TestTag(name = "platform", value = "mobile")
    public void validLoginTest() {
        HomePageBase homePage = openHomePage();
        LoginPageBase loginPage = openLoginPage(homePage);

        assertTrue(loginPage.tryLogin(R.CONFIG.get("user.login")), "Login error");
    }

    @DataProvider(name = "searchData")
    public Object[][] searchDataProvider() {
        return new Object[][]{
                {"iphone", "Art"},
                {"ipad", "Books"}
        };
    }

    @Test(dataProvider = "searchData", threadPoolSize = 4, invocationCount = 1)
    @TestTag(name = "platform", value = "desktop")
    @TestTag(name = "platform", value = "mobile")
    public void validSearchWithCategoryTest(String query, String category) {
        HomePageBase homePage = openHomePage();
        SearchResultPageBase searchResultPage = openSearchResultPage(homePage, query, category);

        List<? extends ResultBase> results = searchResultPage.getResults();

        assertFalse(results.isEmpty(), "No search results");
        searchResultPage.printResults();

        List<ResultBase> notMatching = new ArrayList<>();
        assertTrue(
                results.stream()
                        .allMatch(r -> {
                            if (!r.getTitle().toLowerCase().contains(query.toLowerCase())) {
                                notMatching.add(r);
                                return false;
                            } else return true;
                        }) || ((double) notMatching.size() / results.size() <= 0.1), "Product title not containing query" + notMatching);
    }

    // not working on mobile
    @Test
    @TestTag(name = "platform", value = "desktop")
    public void emptySearchTest() {
        HomePageBase homePage = openHomePage();

        homePage.search("", "");
        assertTrue(initPage(getDriver(), CategoriesPageBase.class).isOpen(), "CategoriesPage is not opened");
    }

    @Test(dataProvider = "searchData")
    @TestTag(name = "platform", value = "desktop")
    @TestTag(name = "platform", value = "mobile")
    public void searchSortPriceDescTest(String query, String category) {
        HomePageBase homePage = openHomePage();
        SearchResultPageBase searchResultPage = openSearchResultPage(homePage, query, category);

        List<? extends ResultBase> results = searchResultPage.getResults();
        assertFalse(results.isEmpty(), "No search results");

        searchResultPage.setSortOption("Price + Shipping: highest first");
        results = searchResultPage.getNoSponsoredResults();
        //searchResultPage.printResults();

        List<Double> prices = results.stream().map(r -> Double.parseDouble(r.getPrice()) + Double.parseDouble(r.getShipping())).toList();

        LOGGER.info("Non sponsored prices count = {}", prices.size());
        prices.forEach(p -> LOGGER.info(p.toString()));

        assertEquals(prices.stream().sorted(Comparator.reverseOrder()).toList(), prices, "Results sorted incorrectly");
    }

    @Test
    @TestTag(name = "platform", value = "desktop")
    @TestTag(name = "platform", value = "mobile")
    public void categoriesPageContentTest() {
        HomePageBase homePage = openHomePage();
        CategoriesPageBase categoriesPage = openCategoriesPage(homePage);

        LOGGER.info(categoriesPage.getCategories().toString());

        List<String> expectedCategories = new ArrayList<>();
        String filename = "/categories" + (R.CONFIG.get("env").equalsIgnoreCase("ios") ? "Mobile.txt" : "Desktop.txt");

        try {
            File myObj = new File(getClass().getResource(filename).getFile());
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine())
                expectedCategories.add(myReader.nextLine());
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("Cant open file = " + filename + e);
        }

        LOGGER.info(expectedCategories.toString());

        assertEquals(categoriesPage.getCategories(), expectedCategories);
    }

    @Test(dataProvider = "searchData")
    @TestTag(name = "platform", value = "desktop")
    @TestTag(name = "platform", value = "mobile")
    public void correctSearchResultStructureTest(String query, String category) {
        HomePageBase homePage = openHomePage();
        SearchResultPageBase searchResultPage = openSearchResultPage(homePage, query, category);

        List<? extends ResultBase> results = searchResultPage.getResults();
        assertFalse(results.isEmpty(), "No search results");

        for (var r : results) {
            assertNotSame("missing", r.getTitle(), "Result missing title " + r);
            assertNotSame("0", r.getPrice(), "Result missing price " + r);
            assertNotSame("missing", r.getItemLocation(), "Result missing location " + r);
        }
    }
}
