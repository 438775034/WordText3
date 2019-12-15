package com.example.wordtext3;

public class WordBook {
    private int id;
    private String eng;
    private String chi;
    private String exp;

    public WordBook(int id, String eng, String chi, String exp){
        this.id = id;
        this.eng = eng;
        this.chi = chi;
        this.exp = exp;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEng() {
        return eng;
    }

    public void setEng(String eng) {
        this.eng = eng;
    }

    public String getChi() {
        return chi;
    }

    public void setChi(String chi) {
        this.chi = chi;
    }

    public String getExp() {
        return exp;
    }

    public void setExp(String sample) {

        this.exp = exp;
    }
}
