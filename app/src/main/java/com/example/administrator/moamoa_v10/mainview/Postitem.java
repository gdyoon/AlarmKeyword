package com.example.administrator.moamoa_v10.mainview;

/**
 * Created by Administrator on 2016-11-20.
 */

public class Postitem {

    String m_userName;
    String m_postUrl;
    String m_postPic;
    String m_postTitle;
    String m_postContent;





    public Postitem(String m_userName, String m_postUrl, String m_postTitle, String m_postContent, String m_postPic) {

        this.m_userName = m_userName;
        this.m_postUrl = m_postUrl;
        this.m_postTitle = m_postTitle;
        this.m_postContent = m_postContent;
        this.m_postPic = m_postPic;
    }

    public String getM_postPic() {
        return m_postPic;
    }
    public String getM_userName() {
        return m_userName;
    }
    public String getM_postUrl() {
        return m_postUrl;
    }
    public String getM_postTitle() {
        return m_postTitle;
    }
    public String getM_postContent() {
        return m_postContent;
    }
}
