package gui;

import javax.swing.*;
import java.awt.*;
import java.io.File;

public class Main extends JFrame {

    public Main() {
        JFrame frame = new JFrame("Генератор STL файла на основе КТ");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        Font font = new Font("SansSerif", Font.PLAIN, 14);

        JMenuBar menuBar = new JMenuBar();
        JTabbedPane jTabbedPane = new JTabbedPane();

        JMenu fileMenu = new JMenu("Файл");
        fileMenu.setFont(font);

        JMenuItem openItem = new JMenuItem("Открыть");
        openItem.setFont(font);
        fileMenu.add(openItem);

        openItem.addActionListener((e)->{
                JFileChooser fileopen = new JFileChooser();
                fileopen.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                int ret = fileopen.showDialog(null, "Выберите папку с КТ изображениями");
                if (ret == JFileChooser.APPROVE_OPTION) {
                    File file = fileopen.getSelectedFile();
                    jTabbedPane.addTab(file.getName(),new Panel(file.getAbsolutePath()));
                }
            }
        );

        JMenuItem closeItem = new JMenuItem("Закрыть");
        closeItem.setFont(font);
        fileMenu.add(closeItem);

        closeItem.addActionListener((e)-> jTabbedPane.removeTabAt(jTabbedPane.getSelectedIndex()));

        fileMenu.addSeparator();

        JMenuItem exitItem = new JMenuItem("Выход");
        exitItem.setFont(font);
        fileMenu.add(exitItem);

        exitItem.addActionListener((e)-> System.exit(0));

        menuBar.add(fileMenu);

        frame.setJMenuBar(menuBar);

        frame.setContentPane(jTabbedPane);

        frame.setPreferredSize(new Dimension(600, 400));
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
