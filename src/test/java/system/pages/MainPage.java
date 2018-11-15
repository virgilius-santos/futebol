package system.pages;

import system.TestFXBase;

import static system.JavaFXIds.MENU_FILE;
import static system.JavaFXIds.MENU_ITEM_NEW_PROJECT;

public class MainPage {

    private final TestFXBase driver;

    public MainPage(TestFXBase driver) {
        this.driver = driver;
    }

    public MainPage pressMenuFile() {

        driver.clickOn(MENU_FILE);
        return this;
    }

    public MainPage pressMenuOpenProject() {
        driver.clickOn(MENU_ITEM_NEW_PROJECT);
        return this;
    }

}
