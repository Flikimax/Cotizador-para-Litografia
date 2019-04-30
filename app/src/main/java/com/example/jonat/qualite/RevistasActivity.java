package com.example.jonat.qualite;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;

public class RevistasActivity extends AppCompatActivity {

    Separador separador = new Separador();
    Papeles condicionales = new Papeles();
    ConfiSpinner confiSpinner = new ConfiSpinner();

    String [] tipo = {"4x0", "4x4"};
    String[] plast = {"PLAST.","4x0","4x4"};
    String[] papel = {"150g","240g"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_revistas);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        final Context context = this;
        //-------------------------------------------------------
        TextView label_revistas = findViewById(R.id.label_revistas);
        TextView label_cantidad = findViewById(R.id.label_cantidad_revistas);
        TextView label_paginas = findViewById(R.id.label_paginas_revistas);
        TextView label_corte = findViewById(R.id.label_corte_revistas);
        TextView label_grafa= findViewById(R.id.label_grafa_revistas);
        TextView label_grapa = findViewById(R.id.label_grapa_revistas);

        final EditText EditCantidad = findViewById(R.id.box_cantidad_revistas);
        final EditText EditPaginas = findViewById(R.id.box_paginas_revistas);
        final EditText EditCorte = findViewById(R.id.box_corte_revistas);
        final EditText EditGrafa = findViewById(R.id.box_grafa_revistas);
        final EditText EditGrapa = findViewById(R.id.box_grapa_revistas);

        final EditText EditLargo = findViewById(R.id.box_largo_revistas);
        final EditText EditAncho = findViewById(R.id.box_ancho_revistas);

        final Spinner Tipo = findViewById(R.id.spinner_tipo_revistas);
        final Spinner Plasti = findViewById(R.id.spinner_plasti_revistas);
        final Spinner Papel = findViewById(R.id.spinner_papel_revistas);

        Tipo.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, tipo));
        Plasti.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, plast));
        Papel.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, papel));

        Button Cotizar = findViewById(R.id.btn_cotizar_revistas);
        Button Volver = findViewById(R.id.btn_volver_revistas);
        //-------------------------------------------------------

        Cotizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                separador.decimalEditText(EditCantidad, EditPaginas, EditCorte, EditGrafa, EditGrapa);
                int cabidad2, hojas , neto, portada, cuerpo, acabados, cantidad, paginas, corte, grafa, grapa, impresion, plastificado;
                impresion = cabidad2 = plastificado = acabados = portada = cuerpo = hojas = neto = 0;
                double largo, ancho;

                if (!EditCantidad.getText().toString().equals("") && !EditPaginas.getText().toString().equals("") && !EditCorte.getText().toString().equals("") &&
                        !EditGrafa.getText().toString().equals("") && !EditGrapa.getText().toString().equals("")){
                    cantidad = separador.NumeroInt(EditCantidad);
                    paginas = separador.NumeroInt(EditPaginas);
                    corte = separador.NumeroInt(EditCorte);
                    grafa = separador.NumeroInt(EditGrafa);
                    grapa = separador.NumeroInt(EditGrapa);

                    if (!EditLargo.getText().toString().equals("") && !EditAncho.getText().toString().equals("")){
                        largo = Double.parseDouble(EditLargo.getText().toString());
                        ancho = Double.parseDouble(EditAncho.getText().toString());
                        double x = ((ancho * 2) + 1);
                        double y = ((largo * 2) + 1);
                        boolean validacion = condicionales.validacion(47, 32, largo, ancho);
                        boolean validacion2;
                        if (!validacion){
                            return;
                        }

                        validacion = condicionales.validacion(32, 47, largo, x);
                        validacion2 = condicionales.validacion(32, 47, y, ancho);
                        if (validacion){
                            cabidad2 = condicionales.cabidad(47, 32, x, ancho);
                        }else if (validacion2){
                            cabidad2 = condicionales.cabidad(47, 32, y, ancho);
                        }else {
                            AlertDialog.Builder dialogo1 = new AlertDialog.Builder(context);
                            dialogo1.setIcon(R.mipmap.ic_launcher);
                            dialogo1.setTitle("Mensaje");
                            dialogo1.setMessage("Tamaño no valido.");
                            dialogo1.setCancelable(false);
                            dialogo1.setNegativeButton("Ok", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogo1, int i) {
                                    dialogo1.cancel();
                                }
                            });
                            dialogo1.show();
                            return;
                        }

                        int cabidad = condicionales.cabidad(47, 32, largo, ancho);

                        cabidad *= 2;
                        hojas = condicionales.hojas(cantidad, cabidad2);

                        if (Tipo.getSelectedItem().toString().equals("4x0")){//4x0
                            if (Papel.getSelectedItem().toString().equals("150g")){//150
                                portada = condicionales.papel150(hojas);
                            }else if (Papel.getSelectedItem().toString().equals("240g")){//240
                                portada = condicionales.papel240(hojas);
                            }
                            if (Plasti.getSelectedItem().toString().equals("4x0")){//plastificado 4x0
                                plastificado = condicionales.plastificado(hojas);
                            }else if (Plasti.getSelectedItem().toString().equals("4x4")){//plastificado 4x4
                                plastificado = condicionales.plastificado(hojas*2);
                            }
                        }else if (Tipo.getSelectedItem().toString().equals("4x4")){//4x4
                            hojas *= 2;
                            if (Papel.getSelectedItem().toString().equals("150g")){//150
                                portada = condicionales.papel150(hojas);
                            }else if (Papel.getSelectedItem().toString().equals("240g")){//240
                                portada = condicionales.papel240(hojas);
                            }
                            if (Plasti.getSelectedItem().toString().equals("4x0")){//plastificado 4x0
                                plastificado = condicionales.plastificado(hojas/2);
                            }else if (Plasti.getSelectedItem().toString().equals("4x4")){//plastificado 4x4
                                plastificado = condicionales.plastificado(hojas);
                            }
                        }
                        int hojas2 = (((condicionales.paginas(cantidad, paginas, cabidad, 1) * cantidad)) * 2);
                        cuerpo = condicionales.papel150(hojas2);

                        acabados = corte + grafa + grapa;
                        neto = portada + plastificado + cuerpo + acabados;

                        AlertDialog.Builder dialogo1 = new AlertDialog.Builder(context);
                        dialogo1.setIcon(R.mipmap.ic_launcher);
                        dialogo1.setTitle("Cotización");
                        dialogo1.setMessage("Cantidad:             " + separador.transformador(cantidad) +
                                "\nHojas:                   " + separador.transformador(hojas2) +
                                "\n\nPortada:               " + separador.transformador(portada) +
                                "\nPlastificado:        " + separador.transformador(plastificado) +
                                "\nCuerpo:                " + separador.transformador(cuerpo) +
                                "\n\nAcabados:           " + separador.transformador(acabados) +
                                "\n\nNeto:                    " + separador.transformador(neto));
                        dialogo1.setCancelable(false);
                        dialogo1.setNegativeButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogo1, int i) {
                                dialogo1.cancel();
                            }
                        });
                        dialogo1.show();

                    }else if (EditLargo.getText().toString().equals("")){
                        EditLargo.setError("Falta el largo.");
                        EditLargo.requestFocus();
                        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                        assert imm != null;
                        imm.showSoftInput(EditLargo, InputMethodManager.SHOW_IMPLICIT);
                        return;
                    }else if (EditAncho.getText().toString().equals("")){
                        EditAncho.setError("Falta el ancho.");
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
                }else if (EditCorte.getText().toString().equals("")){
                    EditCorte.setError("Falta el corte.");
                    EditCorte.requestFocus();
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    assert imm != null;
                    imm.showSoftInput(EditCorte, InputMethodManager.SHOW_IMPLICIT);
                }else if (EditGrafa.getText().toString().equals("")){
                    EditGrafa.setError("Falta la grafa.");
                    EditGrafa.requestFocus();
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    assert imm != null;
                    imm.showSoftInput(EditGrafa, InputMethodManager.SHOW_IMPLICIT);
                }else if (EditGrapa.getText().toString().equals("")){
                    EditGrapa.setError("Falta la grapa.");
                    EditGrapa.requestFocus();
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    assert imm != null;
                    imm.showSoftInput(EditGrapa, InputMethodManager.SHOW_IMPLICIT);
                }
            }
        });//Fin Cotizar

        label_revistas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                separador.decimalEditText(EditCantidad, EditPaginas, EditCorte, EditGrafa, EditGrapa);
            }
        });

        label_cantidad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                separador.decimalEditText(EditCantidad, EditPaginas, EditCorte, EditGrafa, EditGrapa);
            }
        });

        label_paginas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                separador.decimalEditText(EditCantidad, EditPaginas, EditCorte, EditGrafa, EditGrapa);
            }
        });

        label_corte.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                separador.decimalEditText(EditCantidad, EditPaginas, EditCorte, EditGrafa, EditGrapa);
            }
        });

        label_grafa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                separador.decimalEditText(EditCantidad, EditPaginas, EditCorte, EditGrafa, EditGrapa);
            }
        });

        label_grapa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                separador.decimalEditText(EditCantidad, EditPaginas, EditCorte, EditGrafa, EditGrapa);
            }
        });

        Tipo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                confiSpinner.confiSpinner(adapterView);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        Plasti.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                confiSpinner.confiSpinner(adapterView);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        Papel.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                confiSpinner.confiSpinner(adapterView);
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
