package com.example.jonat.qualite;

import android.app.AlertDialog;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;

public class LibrosActivity extends AppCompatActivity {
    Separador separador = new Separador();
    Papeles condicionales = new Papeles();
    ConfiSpinner confiSpinner = new ConfiSpinner();

    String[] pasta = {"P. BLANDA", "P. DURA"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_libros);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        final Context context = this;
        //----------------------------------------------------------
        TextView label_cantidad = findViewById(R.id.label_cantidad_libros);
        TextView label_paginas = findViewById(R.id.label_paginas_libros);
        TextView label_acabados = findViewById(R.id.label_acabados_libros);

        final EditText EditCantidad = findViewById(R.id.box_cantidad_libros);
        final EditText EditPaginas = findViewById(R.id.box_paginas_libros);
        final EditText EditAcabados = findViewById(R.id.box_acabados_libros);

        final  EditText EditLargo = findViewById(R.id.box_largo_libros);
        final  EditText EditAncho = findViewById(R.id.box_ancho_libros);

        final Switch CheckGuarda = findViewById(R.id.switch_guarda_libros);
        final Switch CheckHorizontal = findViewById(R.id.switch_horizontal_libros);
        final Switch CheckVertical = findViewById(R.id.switch_vertical_libros);

        final Spinner Pasta = findViewById(R.id.spinner_pasta_libros);
        Pasta.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, pasta));

        Button Argollados = findViewById(R.id.btn_argollados_libros);
        Button Cotizar = findViewById(R.id.btn_cotizar_libros);
        Button Volver = findViewById(R.id.btn_volver_libros);
        //----------------------------------------------------------

        Argollados.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LibrosActivity.this, LibrosArgolladosActivity.class);
                startActivity(intent);
            }
        });

        Cotizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                separador.decimalEditText(EditCantidad, EditPaginas, EditAcabados, null, null);

                int paginas;
                final int hojas;
                final int acabados;
                int guarda;
                final int[] plasti = new int[1];
                int impresion;
                final int plastificado;
                final int[] portada = new int[1];
                final int[] neto =  {0};
                final int[] plastiguarda = {0};
                final int cantidad;
                int time = 0;

                impresion = guarda = plastificado = 0;
                double largo, ancho, auxhojas;


                if (!EditCantidad.getText().toString().equals("") && !EditPaginas.getText().toString().equals("") &&
                        !EditAcabados.getText().toString().equals("")) {
                    cantidad =separador.NumeroInt(EditCantidad);
                    paginas = separador.NumeroInt(EditPaginas);
                    acabados = separador.NumeroInt(EditAcabados);

                    if (!EditLargo.getText().toString().equals("") && !EditAncho.getText().toString().equals("")){
                        largo = Double.parseDouble(EditLargo.getText().toString());
                        ancho = Double.parseDouble(EditAncho.getText().toString());

                        if (CheckVertical.isChecked()){
                            int cabidad = (condicionales.cabidad(47, 32, largo, ancho) * 2);
                            auxhojas = (double) paginas / (double) cabidad;

                            while ((auxhojas % 1) != 0){
                                paginas++;
                                auxhojas = (double) paginas / (double) cabidad;
                            }
                            hojas = (int) (auxhojas * cantidad);
                            impresion = condicionales.papel150(hojas*2);

                            if (CheckGuarda.isChecked()){
                                guarda = condicionales.papel200guarda(hojas*2, cantidad*2);
                                neto[0] += guarda;
                                time += 3000;
                                AlertDialog.Builder dialogo1 = new AlertDialog.Builder(context);
                                dialogo1.setIcon(R.mipmap.ic_launcher);
                                dialogo1.setTitle("Guarda");
                                dialogo1.setMessage("¿Desea plastificar la guarda?");
                                dialogo1.setCancelable(false);
                                dialogo1.setPositiveButton("No", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialogo1, int id) {
                                        dialogo1.cancel();
                                    }
                                });
                                dialogo1.setNegativeButton("Si", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogo1, int i) {
                                        plastiguarda[0] = condicionales.plastificado(cantidad*2);
                                        neto[0] += plastiguarda[0];
                                    }
                                });
                                dialogo1.show();
                            }//Fin Guarda

                            if (Pasta.getSelectedItem().toString().equals("P. BLANDA")){//PASTA BLANDA
                                double x = (ancho*2) + 1;
                                double y = (largo*2) + 1;
                                int cabidad2 = 0;
                                if (condicionales.validacion(47, 32, largo, x)){
                                    cabidad2 = condicionales.cabidad(47, 32, largo, x);
                                }else if (condicionales.validacion(47, 32, y, ancho)){
                                    cabidad2 = condicionales.cabidad(47, 32, y, ancho);
                                }
                                final int[] hojas2 = {1};
                                Handler handler = new Handler();
                                final int finalCabidad = cabidad2;
                                handler.postDelayed(new Runnable() {

                                    public void run() {
                                        AlertDialog.Builder dialogo1 = new AlertDialog.Builder(context);
                                        dialogo1.setIcon(R.mipmap.ic_launcher);
                                        dialogo1.setTitle("Pasta Blanda");
                                        dialogo1.setMessage("¿Pasta blanda 4x0 o 4x4?");
                                        dialogo1.setCancelable(false);
                                        final int finalCabidad1 = finalCabidad;
                                        dialogo1.setPositiveButton("4x4", new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialogo1, int id) {
                                                int aux = cantidad*2;
                                                while ((hojas2[0] * finalCabidad1) <= cantidad){
                                                    hojas2[0]++;
                                                }
                                                hojas2[0] *= 2;
                                                plasti[0] = condicionales.plastificado(hojas2[0]);
                                                portada[0] = condicionales.papel300(hojas2[0]);
                                            }
                                        });
                                        dialogo1.setNegativeButton("4x0", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialogo1, int i) {
                                                while ((hojas2[0] * finalCabidad) <= cantidad){
                                                    hojas2[0]++;
                                                }
                                                plasti[0] = condicionales.plastificado(hojas2[0]);
                                                portada[0] = condicionales.papel300(hojas2[0]);
                                            }
                                        });
                                        dialogo1.show();
                                    }
                                }, time);
                                time += 3000;
                            }else if (Pasta.getSelectedItem().toString().equals("P. DURA")){
                                portada[0] = 40000 * cantidad;
                                plasti[0] = 4000 * cantidad;
                            }
                            neto[0] += impresion + acabados;
                        }else {//Horizontal
                            int cabidad = (condicionales.cabidad(47, 32, largo, ancho) * 2);
                            auxhojas = (double) paginas / (double) cabidad;

                            while ((auxhojas % 1) != 0){
                                paginas++;
                                auxhojas = (double) paginas / (double) cabidad;
                            }
                            hojas = (int) (auxhojas * cantidad);
                            impresion = condicionales.papel150(hojas*2);
                            neto[0] += impresion + portada[0] + plasti[0] + acabados;

                            if (Pasta.getSelectedItem().toString().equals("P. DURA")){
                                portada[0] = 40000 * cantidad;
                                plasti[0] = 4000 * cantidad;
                            }
                        }

                        Handler handler = new Handler();
                        final int finalGuarda = guarda;
                        final int finalImpresion = impresion;
                        handler.postDelayed(new Runnable() {

                            public void run() {
                                int finalneto = portada[0] + plasti[0] + neto[0];

                                AlertDialog.Builder dialogo1 = new AlertDialog.Builder(context);
                                dialogo1.setIcon(R.mipmap.ic_launcher);
                                dialogo1.setTitle("Cotización");
                                dialogo1.setMessage("Cantidad:             " + separador.transformador(cantidad) +
                                        "\nHojas:                   " + separador.transformador(hojas) +
                                        "\n\nPortada:               " + separador.transformador(portada[0]) +
                                        "\nPlastificado:        " + separador.transformador(plasti[0]) +
                                        "\n\nGuarda:                " + separador.transformador(finalGuarda) +
                                        "\nPlastificado:        " + separador.transformador(plastiguarda[0]) +
                                        "\n\nCuerpo:                 " + separador.transformador(finalImpresion) +
                                        "\nAcabados:            " + separador.transformador(acabados) +
                                        "\n\nNeto:                     " + separador.transformador(finalneto));
                                dialogo1.setCancelable(false);
                                dialogo1.setNegativeButton("Ok", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogo1, int i) {
                                        dialogo1.cancel();
                                    }
                                });
                                dialogo1.show();
                            }
                        }, time);
                    }else if (EditLargo.getText().toString().equals("")){
                        EditLargo.setError("Faltan el largo.");
                        EditLargo.requestFocus();
                        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                        assert imm != null;
                        imm.showSoftInput(EditLargo, InputMethodManager.SHOW_IMPLICIT);
                        return;
                    }else if (EditAncho.getText().toString().equals("")){
                        EditAncho.setError("Faltan el ancho.");
                        EditAncho.requestFocus();
                        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                        assert imm != null;
                        imm.showSoftInput(EditAncho, InputMethodManager.SHOW_IMPLICIT);
                        return;
                    }
                }else if (EditCantidad.getText().toString().equals("")){
                    EditCantidad.setError("Falta la cantidad.");
                    EditCantidad.requestFocus();
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    assert imm != null;
                    imm.showSoftInput(EditCantidad, InputMethodManager.SHOW_IMPLICIT);
                }else if (EditPaginas.getText().toString().equals("")){
                    EditPaginas.setError("Faltan las páginas.");
                    EditPaginas.requestFocus();
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    assert imm != null;
                    imm.showSoftInput(EditPaginas, InputMethodManager.SHOW_IMPLICIT);
                }else if (EditAcabados.getText().toString().equals("")){
                    EditAcabados.setError("Falta el acabado.");
                    EditAcabados.requestFocus();
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    assert imm != null;
                    imm.showSoftInput(EditAcabados, InputMethodManager.SHOW_IMPLICIT);
                }
            }
        });//Fin Cotizar

        label_cantidad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                separador.decimalEditText(EditCantidad, EditPaginas, EditAcabados, null, null);
            }
        });

        label_paginas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                separador.decimalEditText(EditCantidad, EditPaginas, EditAcabados, null, null);
            }
        });

        label_acabados.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                separador.decimalEditText(EditCantidad, EditPaginas, EditAcabados, null, null);
            }
        });

        CheckHorizontal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (CheckHorizontal.isChecked()){
                    CheckVertical.setChecked(false);
                    CheckGuarda.setChecked(false);
                    Pasta.setSelection(1);
                    CheckGuarda.setVisibility(View.GONE);
                }else if (!CheckHorizontal.isChecked()){
                    CheckVertical.setChecked(true);
                    Pasta.setSelection(0);
                    CheckGuarda.setChecked(false);
                    CheckGuarda.setVisibility(View.GONE);
                }
            }
        });

        CheckVertical.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (CheckVertical.isChecked()){
                    CheckGuarda.setChecked(false);
                    Pasta.setSelection(0);
                    CheckHorizontal.setChecked(false);
                    CheckGuarda.setVisibility(View.GONE);
                }else if (!CheckVertical.isChecked()){
                    CheckHorizontal.setChecked(true);
                    CheckGuarda.setChecked(false);
                    Pasta.setSelection(1);
                    CheckGuarda.setVisibility(View.GONE);
                }
            }
        });

        Pasta.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                confiSpinner.confiSpinner(adapterView);
                if (Pasta.getSelectedItem().toString().equals("P. BLANDA") && CheckHorizontal.isChecked()){
                    CheckGuarda.setChecked(false);
                    Pasta.setSelection(1);
                    CheckGuarda.setVisibility(View.GONE);
                }else if (Pasta.getSelectedItem().toString().equals("P. BLANDA") && CheckVertical.isChecked()){
                    CheckGuarda.setChecked(false);
                    CheckGuarda.setVisibility(View.GONE);
                }else if (Pasta.getSelectedItem().toString().equals("P. DURA") && CheckVertical.isChecked()){
                    CheckGuarda.setChecked(false);
                    CheckGuarda.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        Volver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }//Fin onCreate

    @Override
    public void onBackPressed () {
        finish();
    }

}//Fin Clase
