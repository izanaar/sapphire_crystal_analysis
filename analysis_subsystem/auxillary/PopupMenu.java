package analysis_subsystem.auxillary;

import javax.swing.*;

public class PopupMenu extends JPopupMenu {
    RegionSettingManager regionSettingManager;


    JMenuItem setMeniscus, setDeviation, editMeniscus, editDeviation;

    public PopupMenu(RegionSettingManager regionSettingManager) {
        this.regionSettingManager = regionSettingManager;
        initMenuItems();
        addActionListeners();
        addMenuItems();
    }

    private void initMenuItems(){
        setMeniscus = new JMenuItem("Set meniscus");
        setDeviation = new JMenuItem("Set deviation");
        editMeniscus = new JMenuItem("Edit meniscus width");
        editDeviation = new JMenuItem("Edit deviation width");
        setMeniscusEditable(false);
        setDeviationEditable(false);
    }

    private void addActionListeners(){
        setMeniscus.addActionListener(e -> regionSettingManager.setState(regionSettingManager.MENISCUS_BEGIN));
        setDeviation.addActionListener(e -> regionSettingManager.setState(regionSettingManager.DEVIATION_BEGIN));
        editMeniscus.addActionListener(e -> regionSettingManager.setState(regionSettingManager.MENISCUS_WIDTH));
        editDeviation.addActionListener(e -> regionSettingManager.setState(regionSettingManager.DEVIATION_WIDTH));
    }

    private void addMenuItems(){
        add(setMeniscus);
        add(setDeviation);
        add(editMeniscus);
        add(editDeviation);
    }

    void setMeniscusEditable(boolean value){
        editMeniscus.setEnabled(value);
    }

    void setDeviationEditable(boolean value){
        editDeviation.setEnabled(value);
    }
}
