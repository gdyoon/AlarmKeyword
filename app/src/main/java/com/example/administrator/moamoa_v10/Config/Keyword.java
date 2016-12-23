package com.example.administrator.moamoa_v10.Config;

/**
 * Created by Administrator on 2016-12-23.
 */

// 사용하지 않는 클래스
public class Keyword {

    private String  m_keyword;
    private String  m_createDate;



    public String getM_keyword() {
        return m_keyword;
    }

    public void setM_keyword(String m_keyword) {
        this.m_keyword = m_keyword;
    }

    public String getM_createDate() {
        return m_createDate;
    }

    public void setM_createDate(String m_createDate) {
        this.m_createDate = m_createDate;
    }


    public String[] getKeywordArray()
    {
        return new String[] {this.m_keyword, this.m_createDate};
    }




}
