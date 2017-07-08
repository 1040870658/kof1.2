package com.example.ye.kofv12.com.example.model;

/**
 * Created by yechen on 2017/6/6.
 */

public class DatasetModel {
    String logo;
    String name;
    int match_num;
    int win_num;
    int draw_num;
    int loose_num;
    int pro_score;
    int con_score;

    public String getLogo() {
        return logo;
    }

    public int getPure_score() {
        return pure_score;
    }

    public int getMark() {
        return mark;
    }

    public int getCon_score() {
        return con_score;
    }

    public int getPro_score() {
        return pro_score;
    }

    public int getLoose_num() {
        return loose_num;
    }

    public int getWin_num() {
        return win_num;
    }

    public int getDraw_num() {
        return draw_num;
    }

    public int getMatch_num() {
        return match_num;
    }

    public String getName() {
        return name;
    }

    public void setPure_score(int pure_score) {
        this.pure_score = pure_score;
    }

    public void setMark(int mark) {
        this.mark = mark;
    }

    public void setCon_score(int con_score) {
        this.con_score = con_score;
    }

    public void setPro_score(int pro_score) {
        this.pro_score = pro_score;
    }

    public void setLoose_num(int loose_num) {
        this.loose_num = loose_num;
    }

    public void setWin_num(int win_num) {
        this.win_num = win_num;
    }

    public void setDraw_num(int draw_num) {
        this.draw_num = draw_num;
    }

    public void setMatch_num(int match_num) {
        this.match_num = match_num;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public void setName(String name) {
        name = name.trim();
        this.name = name;
    }

    int pure_score;
    int mark;

}
