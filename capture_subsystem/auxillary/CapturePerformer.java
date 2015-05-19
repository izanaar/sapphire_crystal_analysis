package capture_subsystem.auxillary;


import capture_subsystem.frame_sources.FrameSource;
import capture_subsystem.gui.ImagePanel;
import capture_subsystem.interfaces.CaptureGUIComponentsProvideable;
import capture_subsystem.interfaces.FrameProvideable;

import javax.swing.*;
import java.awt.image.BufferedImage;

public class CapturePerformer implements Runnable {

    BufferedImage frame;
    JTextArea outputArea;
    FrameProvideable frameSource;
    ImagePanel imagePanel;
    boolean performCapture;
    Integer fps;

    public CapturePerformer(CaptureGUIComponentsProvideable guiComponents,
                            FrameProvideable frameSource,  Integer fps) {
        this.outputArea = guiComponents.getOutpArea();
        this.imagePanel = guiComponents.getImagePanel();
        this.frameSource = frameSource;
        this.fps = fps;
        performCapture = true;
    }

    @Override
    public void run() {
        outputArea.append("capture is performing...\n");
        outputArea.append("fps: " + fps + ".\n");
        outputArea.append("delay between frames capture: " + String.valueOf(1000 / fps) + ".\n");
        outputArea.append("Source: " + frameSource.toString() + ".");
        do {
            try {
                frame = frameSource.getFrame();
            } catch (Exception e) {
                System.out.println("Frame grab failed because of exception.\n" +
                        e.getMessage() +
                        "\nProgram failure.");
                return;
            }
            imagePanel.setImage(frame);
            try {
                Thread.sleep(1000/fps);
            } catch (InterruptedException e) {
                System.out.println("exception in thread \"" + Thread.currentThread().getName() + "\"");
            }
        }while (performCapture);
        System.out.println("capture stopped.");
    }

    public BufferedImage getFrame() {
        return frame;
    }

    public void setFPS(Integer FPS) {
        fps = FPS;
    }

    public void stopCapture(){
        performCapture = false;
    }
}