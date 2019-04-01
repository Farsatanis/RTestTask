package framework.utils;

import framework.webdriver.Driver;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

public class UserActions {

    //Each clicks awaits of overlay to disappear
    public UserActions click(WebElement webElement) {
        moveToAndClickOnWebElement(webElement).perform();
        Driver.doWait().untilJsIsReady();
        return this;
    }

    /**
     * Moves to the given element, clicks on it and then types in the given sequence of characters
     * @param webElement a non null web element
     * @param sequence a non null char sequence
     * @return this
     */
    public UserActions focusOnAndType(WebElement webElement, CharSequence sequence) {
        webElement.clear();
        Actions actions = moveToAndClickOnWebElement(webElement);
        actions.sendKeys(sequence);
        actions.perform();
        return this;
    }

    public UserActions moveTo(WebElement webElement) {
        moveToWebElement(webElement).perform();
        return this;
    }

    public void scrollTo(WebElement element) {
        JavascriptExecutor js = ((JavascriptExecutor) Driver.getWebDriver());
        js.executeScript("arguments[0].scrollIntoView()", element);
        Driver.doWait().untilJsIsReady();
    }

    public void pushTheKey(CharSequence... keys) {
        Actions keyAction = new Actions(Driver.getWebDriver());
        keyAction.sendKeys(Keys.chord(keys)).perform();
        Driver.doWait().untilJsIsReady();
    }

    private Actions moveToAndClickOnWebElement(WebElement webElement) {
        Actions actions = new Actions(Driver.getWebDriver());
        actions.moveToElement(webElement);
        actions.click(webElement);
        return actions;
    }

    private Actions moveToWebElement(WebElement webElement) {
        Actions actions = new Actions(Driver.getWebDriver());
        actions.moveToElement(webElement);
        return actions;
    }
}