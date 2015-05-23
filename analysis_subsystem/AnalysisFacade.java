package analysis_subsystem;

import analysis_subsystem.auxillary.capture_regions_management.RegionSettingManager;
import analysis_subsystem.auxillary.capture_regions_management.VideoFlowDecorator;
import analysis_subsystem.gui.FrameAnalysisPanel;
import analysis_subsystem.interfaces.AnalysisSubsystemCommonInterface;
import analysis_subsystem.interfaces.CaptureRegionsViewable;
import capture_subsystem.interfaces.ImagePanelActionListenable;
import capture_subsystem.interfaces.VideoFlowDecorable;
import core.auxillary.ShapeDrawers.ShapeDrawer;

import javax.swing.*;

public class AnalysisFacade implements AnalysisSubsystemCommonInterface {

    JPanel componentGUI;
    RegionSettingManager regionSettingManager;
    AnalysisPerformer analysisPerformer;
    ShapeDrawer drawer;
    VideoFlowDecorator videoFlowDecorator;
    Thread analysisThread;

    public AnalysisFacade(ShapeDrawer drawer) {
        componentGUI = new FrameAnalysisPanel();
        this.drawer = drawer;
    }

    @Override
    public JPanel getGUIPanel() {
        return componentGUI;
    }

    @Override
    public void performInstantAnalysis() {
        analysisPerformer = new AnalysisPerformer(regionSettingManager.getMeniscusInf(),regionSettingManager.getDeviationInf(),
                regionSettingManager.getShaperInf());
        analysisThread = new Thread(analysisPerformer,"analysis thread");
    }

    @Override
    public void performAnalysisIteration() {
        System.out.println("iteration");
    }

    @Override
    public void performAnalysis() {
        System.out.println("analysis");
    }

    @Override
    public void setActionListenable(ImagePanelActionListenable actionListenable, CaptureRegionsViewable regionsViewable) {
        regionSettingManager = new RegionSettingManager(actionListenable, regionsViewable);
        regionSettingManager.addCaptureCoordEditable(videoFlowDecorator);
        regionSettingManager.addCaptureCoordEditable(analysisPerformer);
    }

    public void setDecorable(VideoFlowDecorable decorable) {
        videoFlowDecorator = new VideoFlowDecorator(decorable, drawer);
    }

    @Override
    public ShapeDrawer getShapeDrawer() {
        return drawer;
    }
}
