package util;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import static Frame.App.BASEFILE;

/**
 * @author ReStartLin
 * @data 2018/12/5 10:45
 * @classDesc: 功能描述: 过滤检查
 */
public class FieldFilter {
    private static FieldFilter instance = null;
    private String first = "";

    public String getFirst() {
        return first;
    }

    private Map<String, Integer> noEnterMap = new HashMap<>();
    private Map<String, Integer> noTitleMap = new HashMap<>();


    public static FieldFilter getInstance() {
        if (instance == null) {
            instance = new FieldFilter();
        }
        return instance;
    }

    private FieldFilter() {
        loadData(BASEFILE + "\\禁止目录.txt",noEnterMap);
        loadData(BASEFILE + "\\禁止title目录.txt",noTitleMap);
        loadFirstString();
    }

    private void loadFirstString() {
        File file = FileSave.getFile(BASEFILE + "\\配置目录");
        File fFile = new File(file, "首行内容.txt");
        if (!fFile.exists()) {
            try {
                fFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return;
        }
        BufferedReader bufferedReader = null;
        try {
            bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(fFile),"GBK"));
            String temp;
            StringBuilder stringBuilder = new StringBuilder();
            while ((temp = bufferedReader.readLine()) != null) {
                stringBuilder.append(temp);
            }
            this.first = stringBuilder.toString();

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (bufferedReader != null) {
                try {
                    bufferedReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }
    }

    private void loadData(String path,Map<String, Integer> map) {
        File file = FileSave.getFile(path);
        BufferedReader bufferedReader = null;
        try {
            bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(file),"GBK"));
            String temp;
            while ((temp = bufferedReader.readLine()) != null) {
                map.put(temp, 1);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (bufferedReader != null) {
                try {
                    bufferedReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }
    }

    /**
     * 搜索黑名单
     *
     * @param title
     * @return
     */
    public boolean check(String title) {
        if (title == null || title.equals("")) {
            return true;
        }
        return noEnterMap.containsKey(title.trim());
    }
    /**
     * title黑名单
     *
     * @param title
     * @return
     */
    public  boolean checkTitle(String title) {
        Set<String> strings = noTitleMap.keySet();
        for (String string : strings) {
            if (title.trim().contains(string)) {
                System.out.println(title);
                System.out.println("标题含有"+string+"不通过");
                return false;
            }
        }
        return true;
    }
}
