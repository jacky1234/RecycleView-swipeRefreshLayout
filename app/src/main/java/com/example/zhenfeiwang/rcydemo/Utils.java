package com.example.zhenfeiwang.rcydemo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhenfei.wang on 2016/7/12.
 */
public class Utils {

    private static int time = 3;
    public static List<String> loadMore(){
        List<String> s = new ArrayList<>();
        for(int i = 0 ; i < 5 ; i++){
            s.add(i + "-->" + System.currentTimeMillis());
        }
        return time-- == 0 ? null : s;
    }


    public static void refresh(List<String> list){
        list.clear();
        for(int i = 0 ; i < 20 ; i++){
            list.add(i + "-->" + System.currentTimeMillis());
        }
    }

}
