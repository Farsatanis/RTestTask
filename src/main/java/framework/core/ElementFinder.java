package framework.core;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.internal.Locatable;
import org.openqa.selenium.internal.WrapsElement;
import org.openqa.selenium.support.pagefactory.ElementLocator;
import org.openqa.selenium.support.pagefactory.internal.LocatingElementHandler;
import org.openqa.selenium.support.pagefactory.internal.LocatingElementListHandler;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Proxy;
import java.util.List;
import java.util.stream.Stream;

/**
 * A utility class for locating (and proxying) {@link org.openqa.selenium.WebElement}s
 */
public class ElementFinder {

    private WebElement _searchContext;

    public ElementFinder(WebElement searchContext) {
        _searchContext = searchContext;
    }

    public WebElement findElement(By by) {
        ElementLocator locator = new SimpleElementLocator(by);

        InvocationHandler handler = new LocatingElementHandler(locator);

        WebElement proxy;
        proxy = (WebElement) Proxy.newProxyInstance(
                this.getClass().getClassLoader(), new Class[]{WebElement.class, WrapsElement.class, Locatable.class}, handler);
        return proxy;
    }

    public List<WebElement> findElements(By by) {
        ElementLocator locator = new SimpleElementLocator(by);

        InvocationHandler handler = new LocatingElementListHandler(locator);

        List<WebElement> proxy;
        proxy = (List<WebElement>) Proxy.newProxyInstance(
                this.getClass().getClassLoader(), new Class[]{List.class}, handler);
        return proxy;
    }

    public <T extends Component> T findComponent(Class<T> clazz, By by) {
        return elementToComponent(clazz, findElement(by));
    }

    public <T extends Component> Stream<T> findComponents(Class<T> clazz, By by) {
        return findElements(by).stream().map(e -> elementToComponent(clazz, e));
    }

    private <T extends Component> T elementToComponent(Class<T> clazz, WebElement webElement) {
        try {
            return clazz.getConstructor(WebElement.class).newInstance(webElement);
        } catch (InstantiationException | IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
            throw new RuntimeException("Unable to instantiate " + clazz.getSimpleName(), e);
        }
    }

    private class SimpleElementLocator implements ElementLocator {
        private By _by;

        private SimpleElementLocator(By by) {
            _by = by;
        }

        @Override
        public WebElement findElement() {
            return ElementFinder.this._searchContext.findElement(_by);
        }

        @Override
        public List<WebElement> findElements() {
            return ElementFinder.this._searchContext.findElements(_by);
        }
    }
}
