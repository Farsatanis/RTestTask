package framework.components;

import framework.core.Component;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class Hamburger extends Button {
    public Hamburger(WebElement delegate) {
        super(delegate);
    }

    public Hamburger expand() {
        getWrappedElement().click();
        return this;
    }

    public Component getKeyboardShortcutsLink() {
        return getMenu().findComponent(Component.class, By.className("keyboard-shortcuts-link"));
    }

    public void openShortCuts() {
        getKeyboardShortcutsLink().click();
    }
}
