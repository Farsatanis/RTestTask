package framework.components;

import framework.core.Component;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.stream.Stream;

public class MenuItem extends AbstractMenuItem<MenuItem> {

    public MenuItem(WebElement delegate) {
        super(delegate);
    }

    @Override
    public MenuItem expand() {
        userActions.moveTo(this);
        return this;
    }

    @Override
    public Component getSubMenu() {
        return findComponent(Component.class, By.xpath("//div[contains(@class, '_StyledDropdownContent')]"));
    }

    @Override
    public Stream<MenuItem> getSubMenuItems() {
        return getSubMenu().findComponents(MenuItem.class, By.xpath("//div[contains(@class, '_StyledLink')]"));
    }
}
