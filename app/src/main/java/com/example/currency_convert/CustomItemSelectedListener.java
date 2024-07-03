package com.example.currency_convert;

import android.view.View;
import android.widget.AdapterView;

public class CustomItemSelectedListener implements AdapterView.OnItemSelectedListener {

    private final Runnable callback;

    public CustomItemSelectedListener(Runnable callback) {
        this.callback = callback;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
        callback.run();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) { }
}