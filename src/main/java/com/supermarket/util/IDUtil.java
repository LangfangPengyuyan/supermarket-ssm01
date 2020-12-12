package com.supermarket.util;


import java.text.SimpleDateFormat;
import java.util.Date;

public class IDUtil {

    public static Integer getId() {
        String sdf = new SimpleDateFormat("HHmmSS").format(new Date());
        return Integer.parseInt(sdf);
    }

}
