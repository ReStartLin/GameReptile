package Frame;

import util.FileSave;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * @author ReStartLin
 * @data 2018/12/4 12:28
 * @classDesc: 功能描述:
 */
public class App extends JFrame{
    public static final String BASEFILE = "D:\\爬取游戏攻略";

    private JPanel 爬取游戏;
    private JButton a97973ComButton;

    public App(String title) {
        super(title);
        init();
        listener();
    }
    public void listener() {
        a97973ComButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                FileSave.getFile(BASEFILE+"\\97973");
                new Select("功能选择","97973");
                App.this.setVisible(false);
            }
        });
    }

    public void init() {
        this.setContentPane(爬取游戏);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.pack();
        this.setVisible(true);
    }


}
