package framework.components;

import framework.core.Component;
import org.openqa.selenium.WebElement;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public abstract class AbstractMenuItem<T extends AbstractMenuItem> extends Component {
    public AbstractMenuItem(WebElement delegate) {
        super(delegate);
    }

    public abstract Stream<T> getSubMenuItems();

    public abstract T expand();

    public abstract Component getSubMenu();

    public List<T> getSubMenuItemList() {
        return getSubMenuItems().collect(Collectors.toList());
    }

    public Optional<T> findSubItemWithLabel(String label) {
        return getSubMenuItems().filter(i -> label.equals(i.getText())).findAny();
    }

    public T getSubMenuItemByLabel(String label) {
        Optional<T> subMenuItem = findSubItemWithLabel(label);
        if (!subMenuItem.isPresent()) {
            throw new AssertionError(String.format("Could not find sub menu with label %s; available items were: %s",
                    label, getSubMenuItems().map(T::getText).collect(Collectors.joining(", "))));
        }
        return subMenuItem.get();
    }

}
