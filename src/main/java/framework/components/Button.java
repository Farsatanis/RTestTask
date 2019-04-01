package framework.components;

import framework.core.Component;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class Button extends Component {
    public Button(WebElement delegate) {
        super(delegate);
    }

    public Component getMenu() {
        return findComponent(Component.class, By.xpath("//div[contains(@class, 'menu-panel')]"));
    }

    public boolean isActive() {
        return getWrappedElement().getAttribute("class").contains("active");
    }
}
