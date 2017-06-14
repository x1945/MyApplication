package com.demo.fantasy.myapplication;

import android.app.Application;

/**
 * 共用變數
 * Created by AP4-Fantasy on 2017/6/14.
 */

public class GlobalVariable extends Application {
    private String currPage; //當前頁面
    private String fromPage; //來自頁面

    public String getCurrPage() {
        return currPage;
    }

    public void setCurrPage(String currPage) {
        this.currPage = currPage;
    }

    public String getFromPage() {
        return fromPage;
    }

    public void setFromPage(String fromPage) {
        this.fromPage = fromPage;
    }
}
