package reptile;

import bean.HtmlBean;
import util.CrawlText;
import util.FileSave;

import java.io.IOException;
import java.util.List;

/**
 * @author ReStartLin
 * @data 2018/12/4 11:26
 * @classDesc: 功能描述:
 */
public class Reptile97973 extends BaseReptile {
    private String baseUrl = "http://search.97973.com/guides/search";
    public static final String C_NODE = ".hot_con a[href$=.shtml]";
    public static final String C_TITLE_NODE = ".CONTENT .LEFT h1";
    public static final String C_HTML_NODE = ".CONTENT .LEFT #fonttext";
    public String seachAll(String fileName,String seachTitle,int pageCount) throws IOException {
        String[] docs = {
                C_NODE, //子连接节点
                C_TITLE_NODE,        //title节点
                C_HTML_NODE ,//文章节点
                seachTitle,                 //标题
                fileName  //type
        };
        int index = 1;
        while (index <= pageCount) {
            StringBuilder stringBuilder = new StringBuilder(baseUrl);
            stringBuilder.append("?search_key=").append(seachTitle).append("&page=").append(index);
            String url = stringBuilder.toString();
            List<HtmlBean> text = CrawlText.getText(url, docs);
            //序列化
            FileSave.save(fileName+"\\"+seachTitle,text);
            index++;
        }
        return "成功";
    }

    @Override
    public HtmlBean getBean(HtmlBean bean) {
        //处理内容
        try {
          return CrawlText.getText2Child(bean, new String[]{C_HTML_NODE});
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
