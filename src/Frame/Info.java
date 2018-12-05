package Frame;

import bean.HtmlBean;
import reptile.BaseReptile;
import reptile.ReptileFactory;
import util.FileSave;

import javax.swing.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author ReStartLin
 * @data 2018/12/4 15:20
 * @classDesc: 功能描述:
 */
public class Info extends JFrame {
    private JLabel titleLab;
    private JPanel jpanel;
    private JButton 复制标题Button;
    private JButton 复制内容脚本Button;
    private JTextArea textArea1;
    private JLabel result;
    private JButton 确认发布Button;
    private int index;
    private SelectList selectList;
    private HtmlBean htmlBean;


    public Info(String title, SelectList selectList, HtmlBean htmlBean, int index) {
        super(title);
        this.selectList = selectList;
        //获得其子节点信息
        init();
        listener();
        showView(htmlBean,index);

    }
    private void clear() {
        titleLab.setText("");
        textArea1.setText("");
    }

    private void showView(HtmlBean htmlBean,int index) {
        clear();
        if (htmlBean == null) {
            this.dispose();
        }
        //处理htmlBean
        assert htmlBean != null;
        BaseReptile instance = ReptileFactory.getInstance(htmlBean.getType());
        assert instance != null;
        this.htmlBean = instance.getBean(htmlBean);
        this.index = index;
        //show
        titleLab.setText(htmlBean.getTitle());
        Pattern pattern = Pattern.compile(">");
        Matcher matcher = pattern.matcher(htmlBean.getHtml());
        textArea1.append(matcher.replaceAll(">\n"));
        textArea1.setBounds(0, 0, 1400, 300);
    }

    private void init() {
        this.setContentPane(jpanel);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.pack();
        this.setBounds(200, 200, 1400, 500);
        this.setVisible(true);
    }

    private void listener() {
        复制标题Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                FileSave.setSysClipboardText(htmlBean.getUrl());
                result.setText("复制链接成功");
            }
        });
        确认发布Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                selectList.remove(index);
                HtmlBean frist = selectList.getFrist();
                Info.this.showView(frist,0);
            }
        });
        复制内容脚本Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                StringBuilder js = new StringBuilder();
                js.append("$(\"#classid\").val(2);");
                js.append("$(\"#classid\").trigger(\"change\");");
                js.append("document.getElementById('title').value ='");
                js.append(htmlBean.getTitle().trim());
                js.append("';");
                js.append("document.getElementById('editor').innerHTML = '");
                js.append(Info.this.htmlBean.getHtml());
                js.append("';");
                FileSave.setSysClipboardText(js.toString());
                result.setText("复制脚本成功");
            }
        });
    }


}
