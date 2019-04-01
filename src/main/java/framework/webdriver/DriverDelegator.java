package framework.webdriver;

import framework.utils.RuntimeParameters;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.RemoteWebDriver;

/**
 * Class which delegetes us drivers depending in it's type and options which are required
 */
public class DriverDelegator {

    public RemoteWebDriver getWebDriver(Browser browser) {
        switch (browser) {
            case CHROME: return getChromeDriver();
            case FIREFOX: return getFirefoxDriver();
            default: throw new IllegalArgumentException("No Driver configured for browser: " + browser);
        }
    }

    protected ChromeOptions getChromeOptions() {
        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.addArguments("--incognito");
        chromeOptions.addArguments("start-fullscreen");  // Works for Windows and Mac
        chromeOptions.addArguments("disable-extensions");
        chromeOptions.addArguments("disable-infobars"); //Prevent "Chrome is being controlled by automated test software" notification showing up
        return chromeOptions;
    }

    private RemoteWebDriver getChromeDriver() {
        System.setProperty("webdriver.chrome.driver", RuntimeParameters.requireParameter("chromeDriverServerPath"));
        return new ChromeDriver(getChromeOptions());
    }

    protected FirefoxOptions getFirefoxOptions() {
        FirefoxOptions options = new FirefoxOptions();
        options.addArguments("-private");
        return options;
    }

    private RemoteWebDriver getFirefoxDriver() {
        System.setProperty("webdriver.gecko.driver", RuntimeParameters.requireParameter("geckoDriverServerPath"));
        //Below is needed to prevent getting a flood of Marionette DEBUG messages in the log (github mozilla/geckodriver issue #619)
        System.setProperty(FirefoxDriver.SystemProperty.BROWSER_LOGFILE, "/dev/null");
        RemoteWebDriver firefoxDriver = new FirefoxDriver(getFirefoxOptions());
        firefoxDriver.manage().window().maximize();
        return firefoxDriver;
    }
}