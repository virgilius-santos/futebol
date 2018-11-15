package system;

import javafx.scene.Node;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import main.Main;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.testfx.api.FxToolkit;
import org.testfx.framework.junit.ApplicationTest;
import javafx.stage.Stage;
import org.testfx.util.WaitForAsyncUtils;

import java.util.concurrent.TimeoutException;


public abstract class TestFXBase extends ApplicationTest {

    private Stage primaryStage;

    @Before
    public void setupClass() throws Exception {
        ApplicationTest.launch(Main.class);
    }

    @Override
    public void start(Stage stage) {
        primaryStage = stage;
        stage.show();
    }

    @BeforeClass
    public static void setupHeadLessMode() {
//        System.setProperty("testfx.robot", "glass");
//        System.setProperty("testfx.headless", "true");
//        System.setProperty("prism.order", "sw");
//        System.setProperty("prism.text", "t2k");
//        System.setProperty("java.awt.headless", "true");
    }

    @After
    public void afterEachTest() throws TimeoutException {
        FxToolkit.hideStage();
        release(new KeyCode[]{});
        release(new MouseButton[]{});
    }

    public void ensureEventQueueComplete(){
        WaitForAsyncUtils.waitForFxEvents(1);
    }

    public <T extends Node> T find(final String query) {
        return (T) lookup(query).queryAll().iterator().next();
    }
}
