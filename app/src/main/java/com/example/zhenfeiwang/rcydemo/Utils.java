package com.example.zhenfeiwang.rcydemo;

import java.util.List;

/**
 * Created by zhenfei.wang on 2016/7/12.
 */
public class Utils {

    public static void loadMore(List<String> list){
        for(int i = 0 ; i < 5 ; i++){
            list.add(i + "-->" + System.currentTimeMillis());
        }
    }


    public static void refresh(List<String> list){
        list.clear();
        for(int i = 0 ; i < 20 ; i++){
            list.add(i + "-->" + System.currentTimeMillis());
        }
    }

}
