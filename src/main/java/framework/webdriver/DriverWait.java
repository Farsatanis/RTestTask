package framework.webdriver;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.Collection;
import java.util.stream.Stream;

/**
 * Extension of the standard Selenium {@link WebDriverWait} that provides extra convenience functions useful
 * for testing the Pebbles UI. Also overrides the base methods to return a DriverWait object while leaving
 * their functionality unchanged; this allows chaining of the base methods plus the new convenience methods
 * e.g. {@code driverWait.withTimeout(...).untilVisible(element)}
 */
public class DriverWait extends WebDriverWait {

  /**
   * Package protected constructor; use {@link Driver#doWait()} to get a new DriverWait instance
   */
  DriverWait(WebDriver driver, long timeOutInSeconds) {
    super(driver, timeOutInSeconds);
  }

  /**
   * Ignore stale or missing elements. This is useful when waiting for a condition on an element that is changing
   * (for example being re-rendered by the server) as it is very possible the element will temporarily not
   * be present, or will be replaced by a new element.
   */
  public DriverWait ignoringStaleOrMissingElements() {
    return ignoring(StaleElementReferenceException.class, NoSuchElementException.class);
  }

  /**
   * Wait until the given element is visible - that is, it must be present and have a width/height greater than zero.
   * Ignores stale or missing element exceptions.
   * @param element a non null element
   * @return the element
   * @exception org.openqa.selenium.TimeoutException if the element does not become visible within the timeout
   */
  public WebElement untilVisible(WebElement element) {
    return ignoringStaleOrMissingElements().until(ExpectedConditions.visibilityOf(element));
  }

  public Boolean untilInVisible(WebElement element) {
    return ignoringStaleOrMissingElements().until(ExpectedConditions.invisibilityOf(element));
  }

  public void untilJsIsReady() {
      ignoring(JavascriptException.class)
              .until(driver -> (Boolean) ((JavascriptExecutor) driver)
                      .executeScript("return document.readyState == 'complete'"));
  }

  public void untilAttributeDoesntContainValue(By by, String attribute, String value) {
    ignoringStaleOrMissingElements().until(driver -> !driver.findElement(by).getAttribute(attribute).contains(value));
  }

  @Override
  public <K extends Throwable> DriverWait ignoreAll(Collection<Class<? extends K>> types) {
    super.ignoreAll(types);
    return this;
  }

  @Override
  public DriverWait ignoring(Class<? extends Throwable> exceptionType) {
    super.ignoring(exceptionType);
    return this;
  }

  @Override
  public DriverWait ignoring(Class<? extends Throwable> firstType, Class<? extends Throwable> secondType) {
    super.ignoring(firstType, secondType);
    return this;
  }
}