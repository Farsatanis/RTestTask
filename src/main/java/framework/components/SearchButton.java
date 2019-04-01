package framework.components;

import framework.core.Component;
import framework.webdriver.Driver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.NoSuchElementException;

public class SearchButton extends Button {
    public SearchButton(WebElement delegate) {
        super(delegate);
    }

    private WebElement getInputElement() {
        getWrappedElement().click();
        return getMenu().findElement(By.tagName("input"));
    }

    public Component searchFor(String searchItem) {
        userActions.focusOnAndType(getInputElement(), searchItem);
        Driver.doWait().untilVisible(findElement(By.xpath("//div[contains(@class, 'main-results')]")));
        Driver.doWait().untilJsIsReady();
        return getMenu();
    }

    public Component getSearchItem(String searchItem) {
        return searchFor(searchItem)
                .findComponents(Component.class, By.className("item"))
                .filter(el -> el.getText().trim().contains(searchItem))
                .findFirst().orElseThrow(NoSuchElementException::new);
    }
}
