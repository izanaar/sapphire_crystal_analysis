package capture_subsystem.frame_sources;

import javax.swing.*;
import java.awt.image.BufferedImage;

/**
 * Created by ierus on 4/29/15.
 */
public abstract class FrameSource {

    final String alias;

    protected FrameSource(String alias) {
        this.alias = alias;
    }

    public abstract BufferedImage getFrame() throws Exception;

    protected abstract JPanel completeSettingsPanel();

    public final JPanel getSettingsPanel(){
        return completeSettingsPanel();
    }

    @Override
    public String toString() {
        return alias;
    }
}
