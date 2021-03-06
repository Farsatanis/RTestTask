package framework.components;

import framework.core.Component;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.stream.Stream;

public class SearchableMenuItem extends AbstractMenuItem<SearchableMenuItem> {
    public SearchableMenuItem(WebElement delegate) {
        super(delegate);
    }

    @Override
    public Stream<SearchableMenuItem> getSubMenuItems() {
        return getSubMenu().findComponents(SearchableMenuItem.class, By.xpath("//button[contains(@class, '_Option')]")).filter(Component::isDisplayed);
    }

    @Override
    public SearchableMenuItem expand() {
        userActions.click(this);
        return this;
    }

    @Override
    public Component getSubMenu() {
        return findComponent(Component.class, By.xpath("//div[contains(@class, '_Options')]"));
    }
}
