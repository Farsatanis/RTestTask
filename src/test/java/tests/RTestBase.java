package tests;

import framework.TestBase;
import org.testng.annotations.BeforeMethod;
import pages.RevolutStartPage;

public class RTestBase extends TestBase {

    RevolutStartPage mainPage;

    @BeforeMethod
    public final void beforeTest() {
        mainPage = new RevolutStartPage(getServerUrl());
    }
}