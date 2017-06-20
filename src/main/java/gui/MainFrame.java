package gui;

import dicom.Information;
import event.CreateModel;
import event.Segment;
import stl.CreateSTL;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.File;
import java.util.*;

public class MainFrame extends JFrame {

    private JMenuBar menuBar;
    private Font font;
    private JPanel panel;
    private Information dicomInfo = new Information();
    private HashMap<String,String> map = null;
    Segment segmentation;
    CreateModel createModelEvent;

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


        JMenu fileMenu2 = new JMenu("Действие");
        fileMenu2.setFont(font);
        fileMenu2.setEnabled(false);


        JMenuItem fileMenu1 = new JMenuItem("Быстрый запуск");
        JMenuItem segment = new JMenuItem("Произвести сегментацию");
        JMenuItem createModel = new JMenuItem("Создать трёхмерную модель");
        JMenuItem generateFile = new JMenuItem("Сгенерировать STL файл");

        fileMenu1.setFont(font);
        fileMenu1.addActionListener((ActionEvent e) -> {
            try {
                segmentation.run();
                createModelEvent = new CreateModel(segmentation.newPath, segmentation.calibration);
                createModelEvent.run();
                if (createModelEvent.triangles != null) {
                    File file = new File(map.get("Patient’s Name") + ".stl");
                    CreateSTL createSTL = new CreateSTL();
                    createSTL.writeBinary(createModelEvent.triangles, file);
                }
            }catch (Exception e1){
                JOptionPane.showMessageDialog(null, "Вы время выполнения произобшла ошибка. попробуйте ещё раз");
            }
        });
        fileMenu2.add(fileMenu1);

        segment.setFont(font);
        segment.addActionListener((ActionEvent e) -> {
            segmentation.run();
            createModelEvent = new CreateModel(segmentation.newPath,segmentation.calibration);
            createModel.setEnabled(true);

        });
        fileMenu2.add(segment);

        createModel.setFont(font);
        createModel.setEnabled(false);
        createModel.addActionListener((ActionEvent e) ->{
            createModelEvent.run();
            generateFile.setEnabled(true);

        });
        fileMenu2.add(createModel);

        generateFile.setFont(font);
        generateFile.setEnabled(false);
        generateFile.addActionListener((ActionEvent e) ->{
            java.util.List tr = createModelEvent.triangles;
            if(tr!=null) {
                File file = new File(map.get("Patient’s Name") + ".stl");
                CreateSTL createSTL = new CreateSTL();
                createSTL.writeBinary(tr, file);
            }
        });

        fileMenu2.add(generateFile);

        JMenu fileMenu3 = new JMenu("Помощь");
        fileMenu3.setFont(font);
        JMenuItem rec = new JMenuItem("Рекомендации");
        rec.addActionListener((ActionEvent e) ->{
            JOptionPane.showMessageDialog(null, "Для корректного выполнения программы необходимы выбрать папку с исходными данными\n" +
                    "исходные данные для приложения являются DICOM файлы компьютерной томографии поясничного отдела позвоночника человека\n" +
                    "папка должна содержать только файлы формата DICOM, после загрузки следует выполнить действия:\n" +
                    "1) Произвести сегментацю\n" +
                    "2) Созать трёхмерную модель\n" +
                    "3) Сгенерировать STL файл\n");
        });
        rec.setFont(font);
        fileMenu3.add(rec);
        JMenuItem info = new JMenuItem("О программе");
        info.addActionListener((ActionEvent e) ->{
            JOptionPane.showMessageDialog(null, "Приложения реализует генерацию STL файла на основе изображений\nкомпьютерной томографии поясничного отдела позвоночника человека.\nВерсия 1.0.0\n2017 Шестов В.В");
        });
        info.setFont(font);
        fileMenu3.add(info);





        fileMenu.add(exitItem);
        panel = new JPanel();

        openItem.addActionListener((ActionEvent e) ->{
                JFileChooser fileopen = new JFileChooser();
                File file;
                fileopen.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                int ret = fileopen.showDialog(null, "Выберите папку с КТ изображениями");
                if (ret == JFileChooser.APPROVE_OPTION) {
                    file = fileopen.getSelectedFile();
                    map = dicomInfo.getInfo(file.getAbsolutePath());
                    panel.add(new Label("Instance Creation Date : " + map.get("Instance Creation Date")), SwingConstants.CENTER);
                    panel.add(new Label("Instance Creation Time : " + map.get("Instance Creation Time")), SwingConstants.CENTER);
                    panel.add(new Label("Institution Name : " + map.get("Institution Name")), SwingConstants.CENTER);
                    panel.add(new Label("Referring Physician’s Name : " + map.get("Referring Physician’s Name")), SwingConstants.CENTER);
                    panel.add(new Label("Patient’s Name : " + map.get("Patient’s Name")), SwingConstants.CENTER);
                    panel.add(new Label("Patient’s Birth Date : " + map.get("Patient’s Birth Date")), SwingConstants.CENTER);
                    panel.add(new Label("Patient’s Sex : " + map.get("Patient’s Sex")), SwingConstants.CENTER);
                    panel.add(new Label("Patient’s Age : " + map.get("Patient’s Age")), SwingConstants.CENTER);
                    panel.add(new Label("Slice Thickness : " + map.get("Slice Thickness")), SwingConstants.CENTER);
                    panel.add(new Label("Spacing Between Slices : " + map.get("Spacing Between Slices")), SwingConstants.CENTER);
                    panel.add(new Label("Data Collection Diameter : " + map.get("Data Collection Diameter")), SwingConstants.CENTER);
                    panel.add(new Label("Protocol Name : " + map.get("Protocol Name")), SwingConstants.CENTER);
                    panel.setFont(new Font("Monaco", Font.PLAIN, 14));
                    segmentation = new Segment(file.getAbsolutePath());
                    fileMenu2.setEnabled(true);
                }
        }
        );

        exitItem.addActionListener((e)-> System.exit(0));

        menuBar.add(fileMenu);
        menuBar.add(fileMenu2);
        menuBar.add(fileMenu3);

        setJMenuBar(menuBar);

        setContentPane(panel);

        setPreferredSize(new Dimension(400, 500));
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }
}
