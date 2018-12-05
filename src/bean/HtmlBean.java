package bean;


import java.io.Serializable;

/**
 * @author ReStartLin
 * @data 2018/12/4 10:33
 * @classDesc: 功能描述:
 */
public class HtmlBean implements Serializable {
    private String title;
    private String html;
    private String type;//所属
    private String url;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getHtml() {
        return html;
    }

    public void setHtml(String html) {
        this.html = html;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return "HtmlBean{" +
                "title='" + title + '\'' +
                ", html='" + html + '\'' +
                '}';
    }
}