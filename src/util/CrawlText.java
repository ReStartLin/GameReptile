package util;

import bean.HtmlBean;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author ReStartLin
 * @data 2018/12/4 10:41
 * @classDesc: 功能描述:
 */
public class CrawlText {
    /**
     * @param url 网站链接
     * @throws IOException
     */
    public static List<HtmlBean> getText(String url, String[] docs) throws IOException {
        //存储结果对象
        List<HtmlBean> htmlBeans = new ArrayList<>();
        //获得doc
        Document document = getHtml(url);
        //获得当前页面的所有攻略文章list
        Elements nodes = document.select(docs[0]);
        //得到所需要节点后的 节点信息并保存
        for (Element node : nodes) {
            //标题
            String title = node.html();
            //检查标题是否含有必须和禁止词汇
            if (!FieldFilter.getInstance().checkTitle(title)) {
                continue;
            }
            if (!title.trim().contains(docs[3])) {
                System.out.println(title);
                System.out.println("没有游戏名字驳回" + docs[3]);
                continue;
            }
            //标题检查通过
            //链接
            String href = node.attr("href");
            HtmlBean htmlBean = new HtmlBean();
            htmlBean.setTitle(title);
            htmlBean.setUrl(href);
            htmlBean.setType(docs[4]);
            htmlBeans.add(htmlBean);

        }
        return htmlBeans;
    }
//97973
    public static HtmlBean getText2Child(HtmlBean htmlBean, String[] docs) throws IOException {
        Document document = getHtml(htmlBean.getUrl());
        StringBuilder html = new StringBuilder();
        Element element = document.selectFirst(docs[0]);
        Elements children = element.children();
        html.append("<p style=\"text-align: left;\">");
        html.append("  《王国纪元》是一款策略类战争游戏，游戏中玩家将化身国王一边发展自己的领地一边不断的对敌人发起进攻，喜欢这类游戏的玩家一定不要错过哦。" +
                "壮阔的领土和精致的建筑，以全3D化的形式呈现在一张大地图上，将游戏的宏观世界一览无遗。");
        html.append("</p>");
        if (children.hasText()) {
            for (Element child : children) {
                if (child.is("p")) {
                    child.attr("style","text-align: left;");
                    html.append(child.toString());
                    continue;
                }
                if (child.is("div")) {
                    Element img = child.selectFirst("img");
                    if (img != null) {
                        //图片处理
                        html.append("<p style=\"text-align: center;\"><img src=\"" + img.attr("src") + "\"><br/></p>");
                    }
                }
            }
        }
        html.append("<p style=\"text-align: left;\">");
        html.append("  想要了解该游戏的更多攻略,敬请关注酷乐米");
        html.append("</p>");
        html.append("<p style=\"text-align: left;\"><b>");
        html.append("  关于酷乐米");
        html.append("</b></p>");
        html.append("<p style=\"text-align: left;\">");
        html.append("  酷乐米手游攻略致力于为广大玩家提供最新、最全、最详尽的手游攻略，在这里你可以查询到任何你想要了解的内容。" +
                "问答社区服务为玩家们提供了相互交流并相互解惑的平台，让玩家们的疑问尽快得到解决。");
        html.append("</p>");
        htmlBean.setHtml(html.toString());
        return htmlBean;

    }

    private static Document getHtml(String Url) throws IOException {
        Document document = Jsoup.connect(Url)
                .timeout(4000)
                .ignoreContentType(true)
                .userAgent("Mozilla\" to \"Mozilla/5.0 (App NT 10.0; WOW64; rv:50.0)")
                .get();
        return document;
    }
}
