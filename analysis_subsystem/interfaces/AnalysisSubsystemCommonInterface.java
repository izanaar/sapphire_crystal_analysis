package analysis_subsystem.interfaces;

import capture_subsystem.interfaces.VideoFlowDecorable;
import core.interfaces.GUIPanelProvidable;

public interface AnalysisSubsystemCommonInterface extends
        GUIPanelProvidable, AnalysisPerformable, ImagePanelListenersProvideable, ShapeDrawerProvidable {

        void setDecorable(VideoFlowDecorable decorable);

}
