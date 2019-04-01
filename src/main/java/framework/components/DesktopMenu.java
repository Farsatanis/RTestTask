package framework.components;

import framework.core.Component;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class DesktopMenu extends Component {

    public DesktopMenu(WebElement delegate) {
        super(delegate);
    }

    public StyledPart getLeftPart() {
        return findComponent(StyledPart.class, By.xpath("//div[contains(@class, \"_StyledLeftPart\")]"));
    }

    public StyledPart getRightPart() {
        return findComponent(StyledPart.class, By.xpath("//div[contains(@class, \"_StyledRightPart\")]"));
    }
}
