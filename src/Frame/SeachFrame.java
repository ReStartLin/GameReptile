package Frame;

import util.FieldFilter;
import reptile.Reptile97973;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;

/**
 * @author ReStartLin
 * @data 2018/12/4 12:51
 * @classDesc: 功能描述:
 */
public class SeachFrame extends JFrame  {
    private JTextField 梦幻西游TextField;
    private JTextField a1TextField;
    private JButton 开始Button;
    private JPanel panel1;
    private JLabel result;



    public SeachFrame(String title) {
        super(title);
        init();
        listener();

    }

    protected void listener() {
        开始Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                开始Button.setEnabled(false);
                String pager = a1TextField.getText();
                String title = 梦幻西游TextField.getText();
                //检查
                if (FieldFilter.getInstance().check(title)) {
                    result.setText("不能搜索'"+title+"'游戏");
                    return;
                }
                Reptile97973 reptile97973 = new Reptile97973();
                try {
                    String s = reptile97973.seachAll("97973", title, Integer.parseInt(pager));
                    result.setText(s);
                    开始Button.setEnabled(true);
                } catch (IOException e) {
                    e.printStackTrace();
                }finally {
                    开始Button.setEnabled(true);
                }
            }
        });
    }




    protected void init() {
        this.setContentPane(panel1);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.pack();
        this.setBounds(200,0,400,150);
        this.setVisible(true);
    }


}
