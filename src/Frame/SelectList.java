package Frame;

import bean.HtmlBean;
import sun.reflect.generics.tree.BaseType;
import util.FileSave;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static Frame.App.BASEFILE;
import static util.FileSave.getFile;
import static util.FileSave.matcherPath;

/**
 * @author ReStartLin
 * @data 2018/12/4 13:47
 * @classDesc: 功能描述:
 */
public class SelectList extends JFrame {
    private JPanel jpanel;
    private JComboBox comboBox1;
    private JList list1;
    private String type;
    private List<HtmlBean> htmlBeans = new ArrayList<>();
    private List<File> htmlBeanFiles = new ArrayList<>();

    public SelectList(String title,String type)   {
        super(title);
        this.type = type;
        init();
        listener();
        initComboBox();
    }
    private void initComboBox() {
        File file = getFile(BASEFILE + "\\" + type);
        if (file.isDirectory()) {
            comboBox1.removeAllItems();
            File[] files = file.listFiles();
            if (files.length == 0) {
                comboBox1.addItem("内容为空");
                return;
            }
            for (File file1 : files) {
                if (file1.isDirectory()) {
                    comboBox1.addItem(file1.getName());
                }
            }
        }
    }

    /**
     * 去除
     * @param index
     */
    public void remove(int index) {
        if (index < htmlBeans.size()) {
            File file = htmlBeanFiles.get(index);
            HtmlBean htmlBean1 = htmlBeans.get(index);
            File newFile = new File(BASEFILE + "\\已完成\\"+matcherPath(htmlBean1.getTitle())+".obj");
            if (!newFile.exists()) {
                try {
                    newFile.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            file.delete();
            DefaultComboBoxModel listModel = new DefaultComboBoxModel();
            htmlBeans.remove(index);
            htmlBeanFiles.remove(index);
            for (HtmlBean htmlBean : htmlBeans) {
                listModel.addElement(htmlBean.getTitle());
                list1.setModel(listModel);
            }
        }
    }
    public HtmlBean getFrist() {
        if (htmlBeans.size() >= 1) {
            return htmlBeans.get(0);
        }
        return null;
    }
    private void clear() {
        htmlBeans.clear();
        htmlBeanFiles.clear();
    }

    private void listener() {
        comboBox1.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent itemEvent) {
                if (itemEvent.getStateChange() == ItemEvent.SELECTED)
                {
                    DefaultComboBoxModel listModel = new DefaultComboBoxModel();

                    clear();

                    //这里写你的任务 ，比如取到现在的值
                    String text=(String) comboBox1.getSelectedItem();
                    if (text.trim().equals("内容为空")) {
                        return;
                    }
                    File file = getFile(BASEFILE + "\\" + type + "\\" + text);
                    if (file.isDirectory()) {
                        File[] files = file.listFiles();
                        for (File fileTemp : files) {
                            if (fileTemp.isFile()&&fileTemp.getName().endsWith(".obj")) {
                                HtmlBean obj = FileSave.getInstance(fileTemp);
                                htmlBeans.add(obj);
                                //记录当前文件
                                htmlBeanFiles.add(fileTemp);
                                listModel.addElement(obj.getTitle());
                            }
                        }
                    }
                    list1.setModel(listModel);
                }
            }
        });
        list1.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if(e.getClickCount() == 2){
                    JList myList = (JList) e.getSource();
                    int index = myList.getSelectedIndex();    //已选项的下标
                    HtmlBean htmlBean = htmlBeans.get(index);
                    new Info("Info", SelectList.this, htmlBean, index);
                }
            }
        });
    }

    private void init() {
        this.setContentPane(jpanel);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.pack();
        this.setBounds(200, 0, 500, 500);
        this.setVisible(true);
    }
}
