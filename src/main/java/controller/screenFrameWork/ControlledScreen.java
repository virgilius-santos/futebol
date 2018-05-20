package futAges.controller.screenFrameWork;

import futAges.model.Entity.ProjectData;

public interface ControlledScreen {
    void setProjectData(ProjectData projectData);
    void screenDidDisappear();
}
