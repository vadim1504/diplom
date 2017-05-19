package gui;

import dicom.Information;
import event.Generate;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.File;
import java.util.HashMap;

public class MainFrame extends JFrame {

    private JMenuBar menuBar;
    private Font font;
    private JPanel panel;
    private Information dicomInfo = new Information();
    private HashMap<String,String> map = null;

    public MainFrame() {
        super("Генератор STL файла на основе КТ");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        font = new Font("SansSerif", Font.PLAIN, 14);
        menuBar = new JMenuBar();
        JMenu fileMenu = new JMenu("Файл");
        fileMenu.setFont(font);
        JMenuItem openItem = new JMenuItem("Открыть");
        openItem.setFont(font);
        fileMenu.add(openItem);
        JMenuItem exitItem = new JMenuItem("Выход");
        exitItem.setFont(font);
        fileMenu.add(exitItem);
        panel = new JPanel();

        openItem.addActionListener((ActionEvent e) ->{
                JFileChooser fileopen = new JFileChooser();
                fileopen.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                int ret = fileopen.showDialog(null, "Выберите папку с КТ изображениями");
                if (ret == JFileChooser.APPROVE_OPTION) {
                    File file = fileopen.getSelectedFile();

                    map = dicomInfo.getInfo(file.getAbsolutePath());
                    panel.add(new Label("Instance Creation Date : "+ map.get("Instance Creation Date")),SwingConstants.CENTER);
                    panel.add(new Label("Instance Creation Time : "+ map.get("Instance Creation Time")),SwingConstants.CENTER);
                    panel.add(new Label("Institution Name : "+ map.get("Institution Name")),SwingConstants.CENTER);
                    panel.add(new Label("Referring Physician’s Name : "+ map.get("Referring Physician’s Name")),SwingConstants.CENTER);
                    panel.add(new Label("Patient’s Name : "+ map.get("Patient’s Name")),SwingConstants.CENTER);
                    panel.add(new Label("Patient’s Birth Date : "+ map.get("Patient’s Birth Date")),SwingConstants.CENTER);
                    panel.add(new Label("Patient’s Sex : "+ map.get("Patient’s Sex")),SwingConstants.CENTER);
                    panel.add(new Label("Patient’s Age : "+ map.get("Patient’s Age")),SwingConstants.CENTER);
                    panel.add(new Label("Slice Thickness : "+ map.get("Slice Thickness")),SwingConstants.CENTER);
                    panel.add(new Label("Spacing Between Slices : "+ map.get("Spacing Between Slices")),SwingConstants.CENTER);
                    panel.add(new Label("Data Collection Diameter : "+ map.get("Data Collection Diameter")),SwingConstants.CENTER);
                    panel.add(new Label("Protocol Name : "+ map.get("Protocol Name")),SwingConstants.CENTER);

                    JButton jButton = new JButton("Создать");

                    Generate generate = new Generate(file.getAbsolutePath());

                    jButton.addActionListener(e1 -> {
                        generate.run();
                    });

                    panel.add(jButton,SwingConstants.CENTER);
                    panel.setFont(new Font("Monaco", Font.PLAIN, 14));
                }
            }
        );

        exitItem.addActionListener((e)-> System.exit(0));

        menuBar.add(fileMenu);

        setJMenuBar(menuBar);

        setContentPane(panel);

        setPreferredSize(new Dimension(400, 500));
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }
}
