package framework.components;

import framework.core.Component;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class NavigationBar extends Component {
    public NavigationBar(WebElement delegate) {
        super(delegate);
    }

    public List<Button> getBuckets() {
        return getButtonsAsStream().collect(Collectors.toList());
    }

    public Button getButton(String button) {
        return getButtonsAsStream().filter(el -> el.getText().equals(button)).findFirst().orElseThrow(NoSuchElementException::new);
    }

    private Stream<Button> getButtonsAsStream() {
        return findComponents(Button.class, By.tagName("a"));
    }
}
