package pages;

import framework.annotations.LoadCheck;
import framework.components.DesktopMenu;
import framework.components.MenuItem;
import framework.components.SearchableMenuItem;
import framework.core.PageBase;
import framework.webdriver.Driver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class RevolutStartPage extends PageBase {

    public RevolutStartPage(String serverUrl) {
        super(serverUrl);
    }

    public RevolutStartPage() {
        super();
    }

    @LoadCheck
    @FindBy(xpath = "//div[contains(@class, '_StyledHeader')]")
    private WebElement header;

    @FindBy(xpath = "//div[contains(@class, '_StyledDesktopMenu')]")
    private DesktopMenu desktopMenu;

    @FindBy(xpath = "//div[contains(@class, '_StyledSwitcher')]")
    private SearchableMenuItem localeSwitcher;

    @FindBy(className = "rvl-IndexPage-country")
    private WebElement indexPageCountry;

    public DesktopMenu getDesktopMenu() {
        return desktopMenu;
    }

    public SearchableMenuItem getLocaleSwitcher() {
        return localeSwitcher;
    }

    public MenuItem hoverMenu(String menu) {
        return getDesktopMenu().getRightPart().expandMenu(menu);
    }

    public RevolutCommunityPage openCommunity(String menu) {
        _userActions.click(hoverMenu(menu).getSubMenuItemByLabel("Community"));
        Driver.switchToTab(1);
        return newPageObjectForClass(RevolutCommunityPage.class);
    }

    public RevolutStartPage switchLocale(String locale) {
        getLocaleSwitcher().click();
        SearchableMenuItem newLocale = getLocaleSwitcher().getSubMenuItemByLabel(locale);
        _userActions.scrollTo(newLocale);
        _userActions.click(newLocale);
        Driver.doWait().untilVisible(indexPageCountry);
        return newPageObjectForClass(RevolutStartPage.class);
    }
}
