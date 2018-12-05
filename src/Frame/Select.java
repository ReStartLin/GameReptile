package Frame;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * @author ReStartLin
 * @data 2018/12/4 13:38
 * @classDesc: 功能描述:
 */
public class Select extends JFrame {
    private JPanel jpanel;
    private JButton 获取攻略Button;
    private JButton 选择攻略Button;
    private String type;

    public Select(String title,String type) {
        super(title);
        this.type = type;
        init();
        listener();
    }

    private void listener() {
        获取攻略Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                new SeachFrame("搜索攻略");
            }
        });
        选择攻略Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                new SelectList("列表", type);
            }
        });
    }

    private void init() {
        this.setContentPane(jpanel);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.pack();
        this.setVisible(true);
    }
}
