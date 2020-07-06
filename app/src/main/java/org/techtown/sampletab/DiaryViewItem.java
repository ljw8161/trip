package org.techtown.sampletab;

public class DiaryViewItem {

    private String title;
    private String content;
    private String name;
    private String imgurl;
    private String date;

    public DiaryViewItem(String name, String title, String content, String date, String imgurl){
        this.name=name;
        this.title=title;
        this.content=content;
        this.date = date;
        this.imgurl = imgurl;
    }
    public String getTitle() { return title; }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() { return content; }

    public void setContent(String content) {
        this.content = content;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDate() { return date; }

    public void setDate(String date) {
        this.date=date;
    }

    public void setImgurl(String imgurl){ this.imgurl=imgurl;}

    public String getImgurl() { return imgurl;}
}
