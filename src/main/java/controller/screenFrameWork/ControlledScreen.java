package controller.screenFrameWork;

import model.Entity.ProjectData;

public interface ControlledScreen {
    void setProjectData(ProjectData projectData);
    void screenDidDisappear();
}
