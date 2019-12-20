package com.example.notebook;

import android.net.Uri;
import android.provider.BaseColumns;

public final class Words {
    public static final String AUTHORITY="com.example.notebook.provider";//定义contentprovider的Authority

    //数据列的列名
    public static final class Word implements BaseColumns{

        public final static String _ID="_id";
        public final static String WORD="word";
        public final static String DETAIL="detail";

        //提供服务的Uri
        public final static Uri DICT_CONTENT_URI=Uri.parse("content://"+AUTHORITY+"/words");
        public final static Uri WORD_CONTENT_URI=Uri.parse("content://"+AUTHORITY+"/word");
    }
}
