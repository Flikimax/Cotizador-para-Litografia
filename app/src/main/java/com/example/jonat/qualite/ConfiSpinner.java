package com.example.jonat.qualite;

import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.TextView;

public class ConfiSpinner {

    Separador separador = new Separador();

    public void confiSpinner (AdapterView adapterView){
        ((TextView) adapterView.getChildAt(0)).setGravity(Gravity.CENTER);
        ((TextView) adapterView.getChildAt(0)).setTextColor(Color.BLACK);
        ((TextView) adapterView.getChildAt(0)).setTextSize(22);
    }

}
