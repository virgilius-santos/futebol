package main.java.controller.screenFrameWork;

import main.java.model.Entity.ProjectData;

public interface ControlledScreen {
    void setProjectData(ProjectData projectData);
    void screenDidDisappear();
}
