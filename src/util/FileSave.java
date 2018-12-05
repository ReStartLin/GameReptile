package util;

import bean.HtmlBean;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.io.*;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static Frame.App.BASEFILE;

/**
 * @author ReStartLin
 * @data 2018/12/4 12:05
 * @classDesc: 功能描述:
 */
public class FileSave {
    public static void save(String path, final List<HtmlBean> htmlBeans) {
        new Thread() {
            //判断父文件是否存在
            File baseFile = getFile("D:\\爬取游戏攻略\\" + path);
            @Override
            public void run() {
                if (htmlBeans != null && htmlBeans.size() > 0) {
                    for (HtmlBean htmlBean : htmlBeans) {
                        //判断文件是否已经在完成区
                        if (!checkFileEND(htmlBean.getTitle())) {
                            System.out.println(htmlBean.getTitle());
                            System.out.println("已在完成区");
                            continue;
                        }
                        //创建子文件
                        //在新文件区是否存在,存在则忽略
                        String title = matcherPath(htmlBean.getTitle());
                        File file = new File(baseFile, title + ".obj");
                        if (file.exists()) {
                            continue;
                        }
                        ObjectOutputStream out = null;
                        try {
                            out = new ObjectOutputStream(new FileOutputStream(file));
                            out.writeObject(htmlBean);
                            out.flush();
                        } catch (IOException e) {
                            e.printStackTrace();
                        } finally {
                            if (out != null) {
                                try {
                                    out.close();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    }
                }
            }
        }.start();
    }

    public static File getFile(String path) {
        File file = new File(path);
        if (!file.exists()) {
            file.mkdirs();
        }
        return file;

    }
    /**
     * 检查文件是否已在完成区 不在为ture
     */
    public static boolean checkFileEND(String fileName) {
        File file =new File(getFile(BASEFILE + "\\已完成"),matcherPath(fileName)+".obj") ;
        return !file.exists();

    }
    /**
     * 将字符串复制到剪切板。
     */
    public static void setSysClipboardText(String writeMe) {
        Clipboard clip = Toolkit.getDefaultToolkit().getSystemClipboard();
        Transferable tText = new StringSelection(writeMe);
        clip.setContents(tText, null);
    }

    public static String matcherPath(String path) {
        if (path == null || path.equals("")) {
            return "";
        }
        Pattern pattern = Pattern.compile("[\\s\\\\/:\\*\\?\\\"<>\\|]");
        Matcher matcher = pattern.matcher(path);
        return matcher.replaceAll("");
    }

    /**
     * 反序列化
     * @param file
     * @return
     */
    public static HtmlBean getInstance(File file) {
        //反序列化?
        ObjectInputStream in = null;
        try {
            in  = new ObjectInputStream(new FileInputStream(file));
            return (HtmlBean) in.readObject();

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }
}
