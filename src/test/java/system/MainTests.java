package system;

import javafx.scene.Node;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import org.junit.Before;
import org.junit.Test;
import org.testfx.api.FxRobotException;
import system.pages.MainPage;

import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static system.JavaFXIds.MENU_FILE;
import static system.JavaFXIds.MENU_ITEM_NEW_PROJECT;

public class MainTests extends TestFXBase {

    private MainPage mainPage;

    @Before
    public void beforeEachLoginTest() {
        mainPage = new MainPage(this);
    }

    @Test(expected = FxRobotException.class)
    public void clickOnBogusElement()
    {
        clickOn("#sector09");
    }

    @Test
    public void clickOnMenu() {

        moveTo("#menuFile");
        sleep(2000);
        clickOn("#menuFile");
        sleep(2000);

    }
}
