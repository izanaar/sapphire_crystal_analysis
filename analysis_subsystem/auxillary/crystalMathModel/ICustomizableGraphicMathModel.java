package analysis_subsystem.auxillary.crystalMathModel;

import javax.swing.event.ChangeEvent;

/**
 * Created by ierus on 4/3/15.
 */
public interface ICustomizableGraphicMathModel {

    void setCrystalBorderBrightness(short val);
    void setCrystalCoreBrightness(short val);
    void setMeniscusBrightness(short val);
    void setShaperBrightness(short val);
    void setMenUpBlur(int menUpBlur);
    void setMenDwnBlur(int menDwnBlur);
    void setCrystDownBlur(int crystDownBlur);
    void setCrystBordBlur(int crystBordBlur);

    short getCrystalBorderBrightness();
    short getCrystalCoreBrightness();
    short getMeniscuslBorderBrightness();
    short getShaperBorderBrightness();
    int getMenUpBlur();
    int getMenDwnBlur();
    int getCrystBordBlur();
    int getCrystDownBlur();

}
