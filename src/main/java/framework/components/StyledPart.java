package framework.components;

import framework.core.Component;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.NoSuchElementException;
import java.util.stream.Stream;

public class StyledPart extends Component {

    public StyledPart(WebElement delegate) {
        super(delegate);
    }

    private Stream<MenuItem> getMenuItems() {
        return findComponents(MenuItem.class, By.xpath("//div[contains(@class, '_StyledDropdownTrigger')]")).filter(Component::isDisplayed);
    }

    public MenuItem getMenuItem(String text) {
        return getMenuItems().filter(el -> el.getText().contains(text)).findFirst().orElseThrow(NoSuchElementException::new);
    }

    public MenuItem expandMenu(String text) {
        return getMenuItem(text).expand();
    }
}
