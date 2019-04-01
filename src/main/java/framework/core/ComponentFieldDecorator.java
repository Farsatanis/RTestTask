package framework.core;

import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.pagefactory.DefaultElementLocatorFactory;
import org.openqa.selenium.support.pagefactory.DefaultFieldDecorator;
import org.openqa.selenium.support.pagefactory.ElementLocator;
import org.openqa.selenium.support.pagefactory.FieldDecorator;
import sun.reflect.generics.reflectiveObjects.ParameterizedTypeImpl;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Type;
import java.util.List;

/**
 * A custom implementation of {@link FieldDecorator} which handles {@link Component}
 * fields as well as WebElement fields.
 */
public class ComponentFieldDecorator extends DefaultFieldDecorator implements FieldDecorator {

    public ComponentFieldDecorator(SearchContext searchContext) {
        super(new DefaultElementLocatorFactory(searchContext));
    }

    public Object decorate(ClassLoader loader, Field field) {
        if (!(WebElement.class.isAssignableFrom(field.getType())
                || isDecoratableList(field))) {
            return null;
        }

        ElementLocator locator = factory.createLocator(field);
        if (locator == null) {
            return null;
        }

        if (WebElement.class.isAssignableFrom(field.getType())) {
            WebElement proxy = proxyForLocator(loader, locator);
            //Check if the field is a Component
            if (Component.class.isAssignableFrom(field.getType())) {
                try {
                    return createComponentForField(field, proxy);
                } catch (InstantiationException | IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
                    throw new RuntimeException("Unable to instantiate " + field.getType(), e);
                }
            } else {
                return proxy;
            }
        } else if (List.class.isAssignableFrom(field.getType())) {
            return proxyForListLocator(loader, locator);
        } else {
            return null;
        }
    }

    protected boolean isDecoratableList(Field field) {
        if (!super.isDecoratableList(field)) {
            if (!List.class.isAssignableFrom(field.getType())) {
                return false;
            }
            //Check if the field is a Components List
            Type genericType = field.getGenericType();
            Class listType = (Class)((ParameterizedTypeImpl) genericType).getActualTypeArguments()[0];

            return Component.class.isAssignableFrom(listType);
        }
        return true;
    }

    private Object createComponentForField(Field field, WebElement proxy) throws InstantiationException, IllegalAccessException, InvocationTargetException, NoSuchMethodException {
        Class<?> componentType = field.getType();
        return componentType.getConstructor(WebElement.class).newInstance(proxy);
    }
}