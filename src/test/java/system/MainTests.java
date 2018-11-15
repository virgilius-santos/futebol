package system;

import javafx.scene.control.Button;
import org.junit.Before;
import org.junit.Test;
import org.testfx.api.FxRobotException;

import org.testfx.matcher.base.NodeMatchers;
import system.pages.MainPage;

import static org.testfx.api.FxAssert.verifyThat;
import static org.testfx.matcher.base.NodeMatchers.isDisabled;

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
    public void openProject() {
        String play = "Play";
        String stepBack = "<<";
        String stepFor = ">>";

        verifyThat(play, NodeMatchers.isDisabled());
        verifyThat(stepBack, NodeMatchers.isDisabled());
        verifyThat(stepFor, NodeMatchers.isDisabled());

        mainPage.openProject();

        verifyThat(play, NodeMatchers.isEnabled());
        verifyThat(stepBack, NodeMatchers.isEnabled());
        verifyThat(stepFor, NodeMatchers.isEnabled());
    }
}
