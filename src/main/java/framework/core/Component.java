package framework.core;

import framework.annotations.DoNotLocate;
import framework.utils.UserActions;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Point;
import org.openqa.selenium.Rectangle;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.internal.Coordinates;
import org.openqa.selenium.interactions.internal.Locatable;
import org.openqa.selenium.internal.WrapsElement;
import org.openqa.selenium.support.PageFactory;

import java.util.List;
import java.util.stream.Stream;

/**
 * A Component that delegates all {@link WebElement} methods to a backing {@link WebElement}.
 */
public class Component implements WebElement, Locatable, WrapsElement {

    @DoNotLocate
    private final WebElement _delegate;
    private final ElementFinder _finder;

    protected final UserActions userActions = new UserActions();

    public Component(WebElement delegate) {
        _delegate = delegate;
        _finder = new ElementFinder(_delegate);
    }

    @Override
    public void click() {
        _delegate.click();
    }

    @Override
    public void submit() {
        _delegate.submit();
    }

    @Override
    public void sendKeys(CharSequence... keysToSend) {
        _delegate.sendKeys(keysToSend);
    }

    @Override
    public void clear() {
        _delegate.clear();
    }

    @Override
    public String getTagName() {
        return _delegate.getTagName();
    }

    @Override
    public String getAttribute(String name) {
        return _delegate.getAttribute(name);
    }

    @Override
    public boolean isSelected() {
        return _delegate.isSelected();
    }

    @Override
    public boolean isEnabled() {
        return _delegate.isEnabled();
    }

    @Override
    public String getText() {
        return _delegate.getText();
    }

    @Override
    public List<WebElement> findElements(By by) {
        return _finder.findElements(by);
    }

    @Override
    public WebElement findElement(By by) {
        return _finder.findElement(by);
    }

    public <T extends Component> T findComponent(Class<T> componentClass, By by) {
        return _finder.findComponent(componentClass, by);
    }

    public <T extends Component> Stream<T> findComponents(Class<T> componentClass, By by) {
        return _finder.findComponents(componentClass, by);
    }

    @Override
    public boolean isDisplayed() {
        return _delegate.isDisplayed();
    }

    @Override
    public Point getLocation() {
        return _delegate.getLocation();
    }

    @Override
    public Dimension getSize() {
        return _delegate.getSize();
    }

    @Override
    public Rectangle getRect() {
        return new Rectangle(getLocation(), getSize());
    }

    @Override
    public String getCssValue(String propertyName) {
        return _delegate.getCssValue(propertyName);
    }

    @Override
    public <X> X getScreenshotAs(OutputType<X> target) throws WebDriverException {
        return _delegate.getScreenshotAs(target);
    }

    @Override
    public Coordinates getCoordinates() {
        return ((Locatable) _delegate).getCoordinates();
    }

    /**
     * Only intended for sub classers and internal use by Selenium. Implementing the {@link WrapsElement} interface
     * allows an object to be treated as a DOM element when it is passed as an argument to a Selenium command. Sub
     * classers may also need to access the underlying element.
     */
    @Override
    public WebElement getWrappedElement() {
        return _delegate;
    }
}
