package futAges.controller.screenFrameWork;

import futAges.controller.DataController;

public interface ControlledScreen {
    void setDataController(DataController dataController);
    void screenDidDisappear();
}
