package org.pac4j.demo.spring;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.ArrayList;

class Answer {
    private String aid;
    private String qid;
    private String content;
    private String answererid;
    private String timeCreate;

    public Answer() {
        this.aid = null;
        this.qid = null;
        this.content = null;
        this.answererid = null;
        this.timeCreate = null;
    }

    public Answer(String qid, String content, String answererid){
        this.qid = qid;
        this.content = content;
        this.answererid = answererid;
        // generate create time
        this.timeCreate = (new SimpleDateFormat("yyyy:MM:dd:HH:mm:ss")).format(new Date());
        // generate unique id
        this.aid = this.timeCreate + "_" + answererid;
    }

    public void setAid(String aid) { this.aid = aid; }
    public String getAid() { return this.aid; }

    public void setQid(String qid) { this.qid = qid; }
    public String getQid() { return this.qid; }

    public void setContent(String content) { this.content = content; }
    public String getContent() { return this.content; }

    public void setAnswererid(String answererid) { this.answererid = answererid; }
    public String getAnswererid() { return this.answererid; }

    public void setTimeCreate(String timeCreate) { this.timeCreate = timeCreate; }
    public String getTimeCreate() { return this.timeCreate; }

    public boolean hasQCAfield() {
        return (this.qid.compareTo("") != 0
                && this.content.compareTo("") != 0
                && this.answererid.compareTo("") != 0);
    }

    public List<String> toStringList() {
        List<String> infoList = new ArrayList<>();
        infoList.add(this.aid);
        infoList.add(this.qid);
        infoList.add(this.content);
        infoList.add(this.answererid);
        infoList.add(this.timeCreate);

        return infoList;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("(aid:");
        sb.append(this.aid);
        sb.append(",\n qid: ");
        sb.append(this.qid);
        sb.append(",\n content: ");
        sb.append(this.content);
        sb.append(",\n answererid: ");
        sb.append(this.answererid);
        sb.append(", timeCreate: ");
        sb.append(this.timeCreate);
        sb.append(")");
        return sb.toString();
    }

}
