package gui;

import dicom.Information;
import event.Generate;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;

public class Panel extends JPanel {

    private Information dicomInfo = new Information();
    private HashMap<String,String> map = null;

    Panel(String path){
        map = dicomInfo.getInfo(path);
        add(new Label("Instance Creation Date : "+ map.get("Instance Creation Date")));
        add(new Label("Instance Creation Time : "+ map.get("Instance Creation Time")));
        add(new Label("Institution Name : "+ map.get("Institution Name")));
        add(new Label("Referring Physician’s Name : "+ map.get("Referring Physician’s Name")));
        add(new Label("Patient’s Name : "+ map.get("Patient’s Name")));
        add(new Label("Patient’s Birth Date : "+ map.get("Patient’s Birth Date")));
        add(new Label("Patient’s Sex : "+ map.get("Patient’s Sex")));
        add(new Label("Patient’s Age : "+ map.get("Patient’s Age")));
        add(new Label("Slice Thickness : "+ map.get("Slice Thickness")));
        add(new Label("Spacing Between Slices : "+ map.get("Spacing Between Slices")));
        add(new Label("Data Collection Diameter : "+ map.get("Data Collection Diameter")));
        add(new Label("Protocol Name : "+ map.get("Protocol Name")));

        JButton jButton = new JButton("Generate");
        add(jButton);
        jButton.addActionListener(e->
                new Generate(path).run()
        );

        setFont(new Font("Monaco", Font.PLAIN, 14));
    }
}
