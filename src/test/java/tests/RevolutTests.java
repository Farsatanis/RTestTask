package tests;

import framework.core.Component;
import framework.webdriver.Driver;
import org.openqa.selenium.Keys;
import org.testng.annotations.Test;
import pages.RevolutCommunityPage;

import java.util.Arrays;
import java.util.List;

import static org.testng.Assert.*;

public class RevolutTests extends RTestBase {

    @Test
    public void verifyChangeLocale() {
        String currentUrl = mainPage.getPageUrl();
        mainPage = mainPage.switchLocale("United States");
        Driver.doWait().untilJsIsReady();
        assertNotEquals(mainPage.getPageUrl(), currentUrl, "Url must be different");
        assertTrue(mainPage.getPageUrl().contains("US"), "Url must contain 'US'");
    }

    @Test
    public void verifyCommunityTopicSearch() {
        RevolutCommunityPage communityPage = mainPage.openCommunity("Help");
        Component topic = communityPage.searchForTopic("We got a banking licence");
        assertTrue(topic.isDisplayed(), "Searched topic must be present");
    }

    @Test
    public void verifyCommunityTopicSearchNavigation() {
        String strTopic = "We got a banking licence";
        RevolutCommunityPage communityPage = mainPage.openCommunity("Help");
        String currentUrl = communityPage.getPageUrl();
        Component topic = communityPage.searchForTopic(strTopic);
        communityPage = communityPage.openTopic(topic);
        assertNotEquals(communityPage.getPageUrl(), currentUrl, "Url must be different");
        assertEquals(communityPage.getTopicTitle().getText(), strTopic, "Topics must be the same");
    }

    @Test
    public void verifyHamburgerMenu() {
        RevolutCommunityPage communityPage = mainPage.openCommunity("Help");
        communityPage.openKeyboardShortCuts();
        assertTrue(communityPage.getShortCuts().isDisplayed(), "Shortcuts window must be displayed");
    }

    @Test
    public void verifyShortCutsSections() {
        List<String> expectedSections = Arrays.asList("Jump To", "Navigation", "Application", "Composing", "Actions");
        RevolutCommunityPage communityPage = mainPage.openCommunity("Help");
        List<String> sections = communityPage.openKeyboardShortCuts().getShortCutsSections();
        assertTrue(sections.containsAll(expectedSections), "Sections must be the same");
    }

    @Test
    public void verifySearchShortCut() {
        RevolutCommunityPage communityPage = mainPage.openCommunity("Help");
        assertTrue(communityPage.openSearch("/").isDisplayed(), "SearchMenu must be displayed");
        communityPage.getEmberView().click();
        assertTrue(communityPage.openSearch(Keys.CONTROL, Keys.ALT, "f").isDisplayed(), "SearchMenu must be displayed");
    }

    @Test
    public void testJumpingToTopViaShortCut() {
        RevolutCommunityPage communityPage = mainPage.openCommunity("Help");
        boolean oldBarState = communityPage.getNavigationBar().getButton("Top").isActive();
        communityPage = communityPage.sendKeys("g", "t");
        assertTrue(communityPage.getNavigationBar().getButton("Top").isActive());
        assertTrue(oldBarState != communityPage.getNavigationBar().getButton("Top").isActive());
    }

    @Test
    public void testJumpingToLatestViaShortCut() {
        RevolutCommunityPage communityPage = mainPage.openCommunity("Help");
        boolean oldBarState = communityPage.getNavigationBar().getButton("Latest").isActive();
        communityPage = communityPage.sendKeys("g", "l");
        assertTrue(communityPage.getNavigationBar().getButton("Latest").isActive());
        assertTrue(oldBarState != communityPage.getNavigationBar().getButton("Latest").isActive());
    }

    @Test
    public void testJumpingNavigationBarViaShortCuts() {
        RevolutCommunityPage communityPage = mainPage.openCommunity("Help");

        communityPage = communityPage.sendKeys("g", "t");
        assertTrue(communityPage.getNavigationBar().getButton("Top").isActive());

        communityPage = communityPage.sendKeys("g", "l");
        assertTrue(communityPage.getNavigationBar().getButton("Latest").isActive());

        communityPage = communityPage.sendKeys("g", "c");
        assertTrue(communityPage.getNavigationBar().getButton("Categories").isActive());

        communityPage = communityPage.sendKeys("g", "u");
        assertTrue(communityPage.getNavigationBar().getButton("Latest").isActive());

    }
}