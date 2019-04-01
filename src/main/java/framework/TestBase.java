package framework;

import framework.utils.RuntimeParameters;
import framework.webdriver.Browser;
import framework.webdriver.Driver;
import framework.webdriver.DriverDelegator;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.*;

/**
 * Parent class for all test classes
 */
public abstract class TestBase {

    @BeforeSuite
    public void initDriver() {
        Browser browser = Browser.fromString(RuntimeParameters.getParameter("browserName").orElse("chrome"));
        Driver.initWith(getDriverDelegate(browser), browser);
    }

    @BeforeTest
    public final void beforeTestSetup() {
        String serverUrl = getServerUrl();
        Driver.open(serverUrl);
        Driver.setMainWindow(Driver.getWebDriver().getWindowHandle());
    }

    protected static String getServerUrl() {
        return RuntimeParameters.requireParameter("serverUrl");
    }

    private RemoteWebDriver getDriverDelegate(Browser browser) {
        return new DriverDelegator().getWebDriver(browser);
    }

    @AfterMethod
    public void closeTabs() {
        Driver.closeTabs();
    }

    @AfterSuite
    public void tearDown() {
        Driver.stop();
    }
}