package pages;

import framework.annotations.LoadCheck;
import framework.components.Hamburger;
import framework.components.NavigationBar;
import framework.components.SearchButton;
import framework.core.Component;
import framework.core.PageBase;
import framework.webdriver.Driver;
import org.openqa.selenium.By;
import org.openqa.selenium.support.FindBy;

import java.util.List;
import java.util.stream.Collectors;

public class RevolutCommunityPage extends PageBase {

    public RevolutCommunityPage() {
        super();
        Driver.doWait().untilAttributeDoesntContainValue(By.className("loading-container"), "class", "visible");
    }

    @LoadCheck
    @FindBy(className = "ember-view")
    private Component emberView;

    @LoadCheck
    @FindBy(id = "main-outlet")
    private Component mainOutlet;

    @LoadCheck
    @FindBy(id = "search-button")
    private SearchButton searchButton;

    @LoadCheck
    @FindBy(id = "toggle-hamburger-menu")
    private Hamburger hamburger;

    @FindBy(className = "fancy-title")
    private Component title;

    @FindBy(id = "keyboard-shortcuts-help")
    private Component shortCuts;

    @FindBy(id = "navigation-bar")
    private NavigationBar navigationBar;

    public Component getTopicTitle() {
        return title;
    }

    public NavigationBar getNavigationBar() {
        return navigationBar;
    }

    public Component getShortCuts() {
        return shortCuts;
    }

    public Component getEmberView() {
        return emberView;
    }

    public Component searchForTopic(String topic) {
        return searchButton.getSearchItem(topic);
    }

    public RevolutCommunityPage openTopic(Component topic) {
        topic.click();
        Driver.doWait().untilVisible(getTopicTitle());
        return newPageObjectForClass(RevolutCommunityPage.class);
    }

    public RevolutCommunityPage openKeyboardShortCuts() {
        hamburger.expand().openShortCuts();
        Driver.doWait().untilVisible(shortCuts);
        return this;
    }

    public List<String> getShortCutsSections() {
        return getShortCuts().findComponents(Component.class, By.tagName("h4")).map(Component::getText).collect(Collectors.toList());
    }

    public Component openSearch(CharSequence... sequence) {
        _userActions.pushTheKey(sequence);
        Driver.doWait().untilVisible(searchButton.getMenu());
        return searchButton.getMenu();
    }

    public RevolutCommunityPage sendKeys(CharSequence... sequence) {
        _userActions.pushTheKey(sequence);
        return newPageObjectForClass(RevolutCommunityPage.class);
    }
}
