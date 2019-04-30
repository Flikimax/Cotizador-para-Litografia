package com.example.jonat.qualite;

import android.widget.EditText;

import java.security.Principal;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.ParseException;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Separador {
    DecimalFormatSymbols simbolos = new DecimalFormatSymbols();
    DecimalFormatSymbols simbolo = DecimalFormatSymbols.getInstance(Locale.ENGLISH);
    //simbolos.setDe cimalSeparator(',');
    DecimalFormat formateador = new DecimalFormat("###,###,###,###,###,###,###,###", simbolo);

    public void separador (EditText texto){
        try {
            if (!texto.getText().toString().equals("")){
                Number numero = formateador.parse(texto.getText().toString());
                texto.setText(formateador.format(numero));
            }//SI CANTIDAD NO ESTA VACIA
        }catch (ParseException ex) {
            Logger.getLogger(Principal.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public int NumeroInt (EditText texto){
        try {
            Number numero = formateador.parse(texto.getText().toString());
            return Integer.parseInt(numero.toString());
        }catch (ParseException ex) {
            Logger.getLogger(Principal.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0;
    }

    public String deleteCaracter (EditText texto, String caracter){
        String aRemplazar = texto.getText().toString();
        String remplazado = aRemplazar.replace(caracter, "");
        return remplazado;
    }

    public void setTextInt (EditText texto) {
        texto.setText(deleteCaracter(texto, ","));
    }

    public void decimalEditText (EditText text1, EditText text2, EditText text3, EditText text4, EditText text5){
        if (text1 != null){
            setTextInt(text1);
            separador(text1);
        }if (text2 != null){
            setTextInt(text2);
            separador(text2);
        }if (text3 != null){
            setTextInt(text3);
            separador(text3);
        }if (text4 != null){
            setTextInt(text4);
            separador(text4);
        }if (text5 != null){
            setTextInt(text5);
            separador(text5);
        }
    }

    public String transformador (int numero){
        return formateador.format(numero);
    }

}
