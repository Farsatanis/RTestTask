package framework.core;

import framework.webdriver.Driver;
import framework.annotations.LoadCheck;
import framework.utils.UserActions;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.Arrays;

/**
 * Parent class for objects that contain multiple {@link WebElement} fields and
 * represent a section of the UI such as a page. It initializes any web elements
 * fields marked with {@link org.openqa.selenium.support.FindBy}. The constructor
 * ensures that any {@link WebElement} fields marked
 * with the {@link LoadCheck} annotation correspond to visible elements - this makes sure that the expected
 * section of the UI is actually visible.
 */
public class PageBase {

    protected final UserActions _userActions = new UserActions();
    private String pageUrl;

    public PageBase() {
        pageUrl = Driver.getWebDriver().getCurrentUrl();
        PageFactory.initElements(new ComponentFieldDecorator(Driver.getWebDriver()), this);
        Driver.doWait().untilJsIsReady();
        ensureContainerIsLoaded();
    }

    public PageBase(String url) {
        this();
        Driver.open(url);
    }

    public String getPageUrl() {
        return pageUrl;
    }

    /**
     * Ensure the UI represented by this page is loaded by checking the visibility of all fields of type
     * marked with the {@link LoadCheck} annotation. These fields should always be of type {@link WebElement}
     */
    private void ensureContainerIsLoaded() {
        Arrays.stream(this.getClass().getDeclaredFields())
                .filter(x -> x.isAnnotationPresent(LoadCheck.class)).forEach(field -> {
            try {
                field.setAccessible(true);
                WebElement value = (WebElement) field.get(this);
                Driver.doWait().untilVisible(value);
            } catch (Exception e) {
                String errorMessage = String.format("Page (%s) is not loaded", this.getClass().getSimpleName());
                throw new RuntimeException(errorMessage, e);
            }
        });
    }

    /**
     * Creates new class of page object
     */
    protected static <T extends PageBase> T newPageObjectForClass(Class<T> page)  {
        try {
            return page.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }
}