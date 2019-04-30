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
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;

public class CartaMenuActivity extends AppCompatActivity {

    Separador separador = new Separador();
    Papeles condicionales = new Papeles();
    ConfiSpinner confiSpinner = new ConfiSpinner();

    String[] tip = {"4x0","4x4"};
    String[] plast = {"PLAST", "4x0", "4x4"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_carta_menu);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        final Context context = this;
//-------------------------------------------------------
        final Spinner tipo = findViewById(R.id.spinner_tipo_menu);
        final Spinner plasti = findViewById(R.id.spinner_plasti_menu);

        tipo.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, tip));
        plasti.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, plast));

        TextView label_menu = findViewById(R.id.label_menu);
        TextView label_cantidad = findViewById(R.id.label_cantidad_menu);
        TextView label_paginas = findViewById(R.id.label_paginas_menu);
        TextView label_argollada = findViewById(R.id.label_argollada_menu);
        TextView label_grafa = findViewById(R.id.label_grafa_menu);
        TextView label_grapa = findViewById(R.id.label_grapa_menu);
        TextView label_corte = findViewById(R.id.label_corte_menu);

        final EditText EditCantidad = findViewById(R.id.box_cantidad_menu);
        final EditText EditPaginas = findViewById(R.id.box_paginas_menu);
        final EditText EditArgollada = findViewById(R.id.box_argollada_menu);
        final EditText EditGrafa = findViewById(R.id.box_grafa_menu);
        final EditText EditGrapa = findViewById(R.id.box_grapa_menu);
        final EditText EditCorte = findViewById(R.id.box_corte_menu);
        final EditText EditLargo = findViewById(R.id.box_largo_menu);
        final EditText EditAncho = findViewById(R.id.box_ancho_menu);

        final Switch Variable = findViewById(R.id.switch_variable_menu);

        Button Cotizar = findViewById(R.id.btn_cotizar_menu);
        Button Volver = findViewById(R.id.btn_volver_menu);
        //-------------------------------------------------------

        Cotizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                separador.decimalEditText(EditCantidad, EditPaginas, EditArgollada, EditGrafa, EditGrapa);
                separador.decimalEditText(EditCorte, null, null, null, null);
                int acabados, cantidad, paginas, grafa, grapa, argollada, corte, impresion, plastificado, hojas, variable, neto;
                acabados = plastificado = impresion = argollada = hojas = neto = variable = 0;
                double largo, ancho;

                if (!EditCantidad.getText().toString().equals("") && !EditPaginas.getText().toString().equals("") &&
                        !EditGrafa.getText().toString().equals("") && !EditGrapa.getText().toString().equals("") &&
                        !EditCorte.getText().toString().equals("")){
                    cantidad = separador.NumeroInt(EditCantidad);
                    paginas = separador.NumeroInt(EditPaginas);
                    grafa = separador.NumeroInt(EditGrafa);
                    grapa = separador.NumeroInt(EditGrapa);
                    corte = separador.NumeroInt(EditCorte);
                    if (!EditLargo.getText().toString().equals("") && !EditAncho.getText().toString().equals("")){
                        if (!EditArgollada.getText().toString().equals("")){
                            argollada = separador.NumeroInt(EditArgollada);
                        }

                        largo = Double.parseDouble (EditLargo.getText().toString());
                        ancho = Double.parseDouble(EditAncho.getText().toString());
                        int cabidad = condicionales.cabidad(47, 32, largo, ancho);

                        if (tipo.getSelectedItem().equals("4x0")){
                            hojas = condicionales.hojas(paginas, cabidad);
                            hojas *= cantidad;
                            impresion = condicionales.papel300(hojas);
                        }else if (tipo.getSelectedItem().equals("4x4")){
                            hojas = condicionales.paginas(cantidad, paginas, cabidad, 2);
                            hojas *= cantidad;
                            impresion = condicionales.papel300(hojas);
                        }
                        if (plasti.getSelectedItem().equals("4x0")){
                            plastificado = condicionales.plastificado(hojas);
                        }else if (plasti.getSelectedItem().equals("4x4")){
                            plastificado = condicionales.plastificado(hojas*2);
                        }

                        acabados = grapa + grafa + corte + (argollada * cantidad);
                        neto += acabados + plastificado + impresion;

                        if (Variable.isChecked()){
                            variable += condicionales.variable(neto);
                            neto += variable;
                        }

                        AlertDialog.Builder dialogo1 = new AlertDialog.Builder(context);
                        dialogo1.setIcon(R.mipmap.ic_launcher);
                        dialogo1.setTitle("Cotización");
                        dialogo1.setMessage("Cantidad:             " + separador.transformador(cantidad) +
                                "\nHojas:                   " + separador.transformador(hojas) +
                                "\n\nImpresión:           " + separador.transformador(impresion) +
                                "\nPlastificado:        " + separador.transformador(plastificado) +
                                "\n\nAcabados:           " + separador.transformador(acabados) +
                                "\nVariable:               " + separador.transformador(variable) +
                                "\n\nNeto:                    " + separador.transformador(neto));
                        dialogo1.setCancelable(false);
                        dialogo1.setNegativeButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogo1, int i) {
                                dialogo1.cancel();
                            }
                        });
                        dialogo1.show();
                    }else if (EditLargo.getText().toString().equals("")) {
                        EditLargo.setError("Falta el largo.");
                        EditLargo.requestFocus();
                        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                        assert imm != null;
                        imm.showSoftInput(EditLargo, InputMethodManager.SHOW_IMPLICIT);
                        return;
                    }else if (EditAncho.getText().toString().equals("")) {
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
                    EditPaginas.setError("Falta las páginas.");
                    EditPaginas.requestFocus();
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    assert imm != null;
                    imm.showSoftInput(EditPaginas, InputMethodManager.SHOW_IMPLICIT);
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
                }else if (EditCorte.getText().toString().equals("")){
                    EditCorte.setError("Falta el corte.");
                    EditCorte.requestFocus();
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    assert imm != null;
                    imm.showSoftInput(EditCorte, InputMethodManager.SHOW_IMPLICIT);
                }
            }
        });//Fin Cotizar

        label_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                separador.decimalEditText(EditCantidad, EditPaginas, EditArgollada, EditGrafa, EditGrapa);
                separador.decimalEditText(EditCorte, null, null, null, null);
            }
        });

        label_cantidad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                separador.decimalEditText(EditCantidad, EditPaginas, EditArgollada, EditGrafa, EditGrapa);
                separador.decimalEditText(EditCorte, null, null, null, null);
            }
        });

        label_paginas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                separador.decimalEditText(EditCantidad, EditPaginas, EditArgollada, EditGrafa, EditGrapa);
                separador.decimalEditText(EditCorte, null, null, null, null);
            }
        });

        label_argollada.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                separador.decimalEditText(EditCantidad, EditPaginas, EditArgollada, EditGrafa, EditGrapa);
                separador.decimalEditText(EditCorte, null, null, null, null);
            }
        });

        label_grafa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                separador.decimalEditText(EditCantidad, EditPaginas, EditArgollada, EditGrafa, EditGrapa);
                separador.decimalEditText(EditCorte, null, null, null, null);
            }
        });

        label_grapa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                separador.decimalEditText(EditCantidad, EditPaginas, EditArgollada, EditGrafa, EditGrapa);
                separador.decimalEditText(EditCorte, null, null, null, null);
            }
        });

        label_corte.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                separador.decimalEditText(EditCantidad, EditPaginas, EditArgollada, EditGrafa, EditGrapa);
                separador.decimalEditText(EditCorte, null, null, null, null);
            }
        });

        plasti.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                confiSpinner.confiSpinner(adapterView);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        tipo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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
