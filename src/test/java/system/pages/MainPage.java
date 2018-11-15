package system.pages;

import javafx.scene.input.KeyCode;
import system.TestFXBase;

import static system.JavaFXIds.MENU_FILE;
import static system.JavaFXIds.MENU_ITEM_NEW_PROJECT;

public class MainPage {

    private final TestFXBase driver;

    public MainPage(TestFXBase driver) {
        this.driver = driver;
    }

    public MainPage openProject() {

        driver.clickOn(MENU_FILE)
                .clickOn(MENU_ITEM_NEW_PROJECT);
        driver.press(KeyCode.V);
        driver.press(KeyCode.ENTER);
        driver.ensureEventQueueComplete();
        return this;
    }

    public MainPage pressMenuOpenProject() {
        driver.clickOn(MENU_ITEM_NEW_PROJECT);
        return this;
    }

}
