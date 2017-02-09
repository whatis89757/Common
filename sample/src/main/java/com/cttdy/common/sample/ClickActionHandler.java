package com.cttdy.common.sample;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by what on 2017/2/9.
 */
public class ClickActionHandler{
    private Context context;

    public ClickActionHandler(Context context) {
        this.context = context;
    }

    public void showString(String s){
        Toast.makeText(context, s, Toast.LENGTH_SHORT).show();
    }
}
