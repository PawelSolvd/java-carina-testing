package solvd.webtest;

import com.zebrunner.carina.core.IAbstractTest;
import com.zebrunner.carina.utils.config.Configuration;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeOptions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Clock;
import java.util.List;

public abstract class AbstractTest implements IAbstractTest {
    static {
        System.setProperty("log4j.configurationFile", "src/main/resources/log4j2.xml");
    }

    protected static Logger LOGGER;
    protected ThreadLocal<WebDriver> driver = new ThreadLocal<>();

    public AbstractTest() {
        LOGGER = LoggerFactory.getLogger(getClass());
    }

    @BeforeMethod
    //@Parameters("browser")
    public void setup() {
        String env = Configuration.getRequired("env");
        String browser = Configuration.getRequired(env + ".capabilities.browserName").toLowerCase();
        if (env.equalsIgnoreCase("desktop")) {
            if (browser.equals("chrome")) {
                ChromeOptions options = new ChromeOptions();

                // Adding argument to disable the AutomationControlled flag
                options.addArguments("--disable-blink-features=AutomationControlled", "--incognito", "--start-maximized");

                // Exclude the collection of enable-automation switches
                options.setExperimentalOption("excludeSwitches", List.of("enable-automation"));

                // Turn-off userAutomationExtension
                options.setExperimentalOption("useAutomationExtension", false);

                driver.set(getDriver(browser, options));

                ((JavascriptExecutor) driver.get()).executeScript("Object.defineProperty(navigator, 'webdriver', {get: () => undefined})");
            } else if (browser.equals("firefox"))
                driver.set(getDriver("firefox"));
        } else
            driver.set(getDriver());

        //driver.get().manage().window().maximize();

        LOGGER.info("Setting {} driver", getBrowserDetails());
    }

    @AfterMethod
    public void dispose(ITestResult result) {
        if (!result.isSuccess()) {
            File file = ((TakesScreenshot) driver.get()).getScreenshotAs(OutputType.FILE);
            try {
                Files.copy(file.toPath(), Path.of("screenshots/img" + Clock.systemUTC().millis() + ".png"));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        driver.get().quit();

        LOGGER.info("Closing {} driver", getBrowserDetails());
    }

    public String getBrowserDetails() {
        Capabilities cap = ((HasCapabilities) getDriver()).getCapabilities();

        String browserName = cap.getBrowserName();
        String browserVersion = (String) cap.getCapability("browserVersion");
        String osName = cap.getCapability("platformName").toString();

        return browserName + " " + browserVersion + " " + osName;
        // return new WebDriverConfiguration().toString();
    }
}
