package framework.webdriver;

import org.openqa.selenium.*;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.util.ArrayList;
import java.util.Set;

import static com.jayway.restassured.RestAssured.given;

public class Driver {

    private static ThreadLocal<Browser> _browserType = new ThreadLocal<>();
    //web driver related member variables
    private static ThreadLocal<WebDriver> _webDriver = new ThreadLocal<>();

    private static String _mainWindow;

    private static final ArrayList<String> tabs = new ArrayList<>();

    public static Browser getBrowserType() {
        return _browserType.get();
    }

    /**
     * Returns an instance of web driver if it is not null.
     */
    public static WebDriver getWebDriver() {
        return _webDriver.get();
    }

    /**
     * Initializes the ThreadLocals with the given BrowserType and WebDriver
     */
    public static void initWith(RemoteWebDriver driver, Browser browser) {
        if(_browserType.get() != null || _webDriver.get() != null) {
            throw new IllegalStateException("Driver has already been initialized in this thread: " + _browserType + ", " + _webDriver);
        }
        _browserType.set(browser);
        _webDriver.set(driver);
    }

    /**
     * @return new driver wait instance with default timeout 30 seconds
     */
    public static DriverWait doWait() {
        return new DriverWait(_webDriver.get(), 30);
    }

    public static void open(String serverUrl) {
        int returnCode;
        try {
            returnCode = given().when().get(serverUrl).getStatusCode();
        } catch (Exception e) {
            throw new RuntimeException("Unable to reach configured URL: " + serverUrl, e);
        }
        if (returnCode == 200) {
            WebDriver driver = Driver.getWebDriver();
            driver.get(serverUrl);
        } else {
            throw new RuntimeException("This url " + serverUrl + " is returning the following code: " + returnCode);
        }
    }

    public static void setMainWindow(String window) {
        _mainWindow = window;
    }

    private static String getMainWindow() {
        return _mainWindow;
    }

    public static void switchToTab(int tabIndex) {
        WebDriver driver = getWebDriver();
        Driver.doWait().until(webDriver -> webDriver.getWindowHandles().size() != 1);
        if(tabs.isEmpty()) {
            tabs.addAll(driver.getWindowHandles());
        }
        Driver.doWait().until(driver1 -> driver.switchTo().window((String) driver.getWindowHandles().toArray()[tabIndex]));
    }

    public static void closeTabs() {
        WebDriver driver = getWebDriver();
        Set<String> windowHandles = driver.getWindowHandles();
        if (windowHandles.size() != 1) {
            for(String handle : windowHandles) {
                if (!handle.equals(getMainWindow())) {
                    Driver.doWait().until(action -> driver.switchTo().window(handle));
                    driver.close();
                }
            }
            driver.switchTo().window(getMainWindow());
        }
    }

    public static void stop() {
        try {
            _webDriver.get().quit();
        } catch (Exception e) {
            _webDriver.get().close();
        } finally {
            _webDriver.remove();
            _browserType.remove();
        }
    }
}