package com.thuanpx.ktext;

import android.content.Context;
import android.widget.Toast;

/**
 * Copyright © 2020 Neolab VN.
 * Created by ThuanPx on 5/22/20.
 */
class ToasterMessage {
    public static void s(Context c, String message){

        Toast.makeText(c,message,Toast.LENGTH_SHORT).show();

    }
}
