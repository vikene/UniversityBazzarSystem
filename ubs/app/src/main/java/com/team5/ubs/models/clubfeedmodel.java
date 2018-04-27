package com.team5.ubs.models;

/**
 * Created by tp on 28/02/18.
 */

public class clubfeedmodel {


    String clubname;
    String title;
    String content;

    public clubfeedmodel(String clubname, String title, String content){
        this.clubname = clubname;
        this.title = title;
        this.content = content;
    }
    public  clubfeedmodel(){
        
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        clubfeedmodel that = (clubfeedmodel) o;

        if (clubname != null ? !clubname.equals(that.clubname) : that.clubname != null)
            return false;
        if (title != null ? !title.equals(that.title) : that.title != null) return false;
        return content != null ? content.equals(that.content) : that.content == null;
    }

    @Override
    public int hashCode() {
        int result = clubname != null ? clubname.hashCode() : 0;
        result = 31 * result + (title != null ? title.hashCode() : 0);
        result = 31 * result + (content != null ? content.hashCode() : 0);
        return result;
    }


    public String getClubname() {
        return clubname;
    }

    public void setClubname(String clubname) {
        this.clubname = clubname;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }


}
