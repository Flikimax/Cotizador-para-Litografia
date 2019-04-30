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

public class LibrosArgolladosActivity extends AppCompatActivity {
    Separador separador = new Separador();
    Papeles condicionales = new Papeles();
    ConfiSpinner confiSpinner = new ConfiSpinner();

    String[] tipopaginas = {"4x0", "4x4"};
    String[] papelpaginas = {"150g", "200g", "240g", "ADH."};//150, 200, 240, Adhesivo
    String[] plastipaginas = {"PLAST.", "4x0","4x4"};

    String[] pasta = {"P. BLANDA", "P. DURA"};
    String[] guarda = {"G. BLANCA", "G. IMP."};

    String[] tipoportada = {"4x0", "4x4"};
    String[] papelportada = {"150g", "200g", "240g", "ADH."};//150, 200, 240, Adhesivo
    String[] plastiportada = {"PLAST.", "4x0","4x4"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_libros_argollados);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        final Context context = this;
        //----------------------------------------------------------
        TextView label_cantidad = findViewById(R.id.label_cantidad_argollados);
        TextView label_paginas = findViewById(R.id.label_paginas_argollados);
        TextView label_argollada = findViewById(R.id.label_argollada_argollados);
        TextView label_armada = findViewById(R.id.label_armada_argollados);

        final EditText EditCantidad = findViewById(R.id.box_cantidad_argollada);
        final EditText EditPaginas = findViewById(R.id.box_paginas_argollada);
        final EditText EditArgollada = findViewById(R.id.box_argollada_argollada);
        final EditText EditArmada = findViewById(R.id.box_armada_argollada);

        final  EditText EditLargo = findViewById(R.id.box_largo_argollada);
        final  EditText EditAncho = findViewById(R.id.box_ancho_argollada);

        final Spinner TipoPaginas = findViewById(R.id.spinner_tipopaginas_argollados);
        final Spinner PapelPaginas = findViewById(R.id.spinner_papelpaginas_argollados);
        final Spinner PlastiPaginas = findViewById(R.id.spinner_plastipaginas_argollados);
        TipoPaginas.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, tipopaginas));
        PapelPaginas.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, papelpaginas));
        PlastiPaginas.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, plastipaginas));
        //-------------------------------------------------------------------
        final Spinner Pasta = findViewById(R.id.spinner_pasta_argollados);
        final Spinner Guarda  = findViewById(R.id.spinner_guarda_argollados);
        Pasta.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, pasta));
        Guarda.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, guarda));
        //-------------------------------------------------------------------
        final Spinner TipoPortada = findViewById(R.id.spinner_tipoportada_argollados);
        final Spinner PapelPortada = findViewById(R.id.spinner_papelportada_argollados);
        final Spinner PlastiPortada = findViewById(R.id.spinner_plastiportada_argollados);
        TipoPortada.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, tipoportada));
        PapelPortada.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, papelportada));
        PlastiPortada.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, plastiportada));
        //-------------------------------------------------------------------
        Button Cotizar = findViewById(R.id.btn_cotizar_argollada);
        Button Volver = findViewById(R.id.btn_volver_argollada);

        final Switch Variable = findViewById(R.id.switch_variable_argollados);
        //----------------------------------------------------------

        Cotizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                separador.decimalEditText(EditCantidad, EditPaginas, EditArgollada, EditArmada,  null);
                int carton, impresion, neto, hojas,cantidad, argollada, armada, variable, paginas, plastiportada, plastipaginas, impresionportada, hojasaux, guarda, acabados;
                impresionportada = guarda = hojas  = neto =plastipaginas = impresion = plastiportada = variable= 0;
                double largo, ancho;

                if (!EditCantidad.getText().toString().equals("") && !EditPaginas.getText().toString().equals("") &&
                        !EditArgollada.getText().toString().equals("") && !EditArmada.getText().toString().equals("")){
                    cantidad =separador.NumeroInt(EditCantidad);
                    paginas =separador.NumeroInt(EditPaginas);
                    argollada =separador.NumeroInt(EditArgollada);
                    armada =separador.NumeroInt(EditArmada);
                    if (!EditLargo.getText().toString().equals("") && !EditAncho.getText().toString().equals("")){
                        largo = Double.parseDouble(EditLargo.getText().toString());
                        ancho = Double.parseDouble(EditAncho.getText().toString());

                        int cabidad = condicionales.cabidad(47, 32, largo, ancho);
                        acabados = (argollada *= cantidad) + (armada *= cantidad);

                        //INTERNAS---------------------------------------------
                        if (TipoPaginas.getSelectedItem().toString().equals("4x0")){///4x0
                            hojas = condicionales.paginas(cantidad, paginas, cabidad, 1);
                            hojas *= cantidad;
                            if (PlastiPaginas.getSelectedItem().toString().equals("4x0")){
                                plastipaginas = condicionales.plastificado(hojas);
                            }else if (PlastiPaginas.getSelectedItem().toString().equals("4x4")){
                                hojasaux = condicionales.paginas(cantidad, paginas, cabidad, 2);
                                hojasaux *= cantidad;
                                hojasaux *= 2;
                                plastipaginas = condicionales.plastificado(hojasaux);
                            }
                        }else if (TipoPaginas.getSelectedItem().toString().equals("4x4")){//4x4
                            hojas = condicionales.paginas(cantidad, paginas, cabidad, 2);
                            hojas *= cantidad;
                            hojas *= 2;
                            if (PlastiPaginas.getSelectedItem().toString().equals("4x0")){
                                hojasaux = condicionales.paginas(cantidad, paginas, cabidad, 1);
                                hojasaux *= cantidad;
                                plastipaginas = condicionales.plastificado(hojasaux);
                            }else if (PlastiPaginas.getSelectedItem().toString().equals("4x4")){
                                plastipaginas = condicionales.plastificado(hojas);
                            }
                        }

                        if (PapelPaginas.getSelectedItem().toString().equals("150g")){
                            impresion = condicionales.papel150(hojas);
                        }else if (PapelPaginas.getSelectedItem().toString().equals("200g")){
                            impresion = condicionales.papel200(hojas);
                        }else if (PapelPaginas.getSelectedItem().toString().equals("300g")){
                            impresion = condicionales.papel240(hojas);
                        }else if (PapelPaginas.getSelectedItem().toString().equals("ADH.")){
                            impresion = condicionales.papeladhesivo(hojas);
                        }
                        //FIN-INTERNAS-----------------------------------------

                        //PORTADA----------------------------------------------
                        if (Pasta.getSelectedItem().toString().equals("P. BLANDA")){//{"P. BLANDA"};
                            cabidad = condicionales.cabidad(47, 32, largo, ancho);
                            hojas = cantidad * 2;
                            hojas = condicionales.hojas(hojas, cabidad);
                            if (TipoPortada.getSelectedItem().toString().equals("4x0")){//Portada 4x0
                                if (PlastiPortada.getSelectedItem().toString().equals("4x0")){
                                    plastiportada  = condicionales.plastificado(hojas);
                                }else if (PlastiPortada.getSelectedItem().toString().equals("4x4")){
                                    plastiportada = condicionales.plastificado(hojas*2);
                                }
                            }else if (TipoPortada.getSelectedItem().toString().equals("4x4")){//Portada 4x4
                                hojas *= 2;
                                if (PlastiPortada.getSelectedItem().toString().equals("4x0")){
                                    plastiportada = condicionales.plastificado(hojas/2);
                                }else if (PlastiPortada.getSelectedItem().toString().equals("4x4")){
                                    plastiportada =  condicionales.plastificado(hojas);
                                }
                            }
                        }else if (Pasta.getSelectedItem().toString().equals("P. DURA")){//{"P. DURA"};
                            cabidad = condicionales.cabidad(47, 32, (largo + 3), (ancho + 3));
                            hojas = cantidad * 2;
                            hojas = condicionales.hojas(hojas, cabidad);
                            if (TipoPortada.getSelectedItem().toString().equals("4x0")){//Portada 4x0
                                if (PlastiPortada.getSelectedItem().toString().equals("4x0")){
                                    plastiportada  = condicionales.plastificado(hojas);
                                }else if (PlastiPortada.getSelectedItem().toString().equals("4x4")){
                                    plastiportada = condicionales.plastificado(hojas*2);
                                }
                            }else if (TipoPortada.getSelectedItem().toString().equals("4x4")){//Portada 4x4
                                hojas *= 2;
                                if (PlastiPortada.getSelectedItem().toString().equals("4x0")){
                                    plastiportada = condicionales.plastificado(hojas/2);
                                }else if (PlastiPortada.getSelectedItem().toString().equals("4x4")){
                                    plastiportada =  condicionales.plastificado(hojas);
                                }
                            }
                        }//Fin Pasta Dura
                        if (PapelPortada.getSelectedItem().equals("150g")){
                            impresionportada = condicionales.papel150(hojas);
                        }else if (PapelPortada.getSelectedItem().equals("200g")){
                            impresionportada = condicionales.papel200(hojas);
                        }else if (PapelPortada.getSelectedItem().equals("300g")){
                            impresionportada = condicionales.papel240(hojas);
                        }else if (PapelPortada.getSelectedItem().equals("ADH.")){
                            impresionportada = condicionales.papeladhesivo(hojas);
                        }
                        //FIN-PORTADA------------------------------------------

                        //Carton-----------------------------------------------
                        int cabidadCarton = condicionales.cabidad(100, 70, largo, ancho);
                            double carto = (condicionales.carton / cabidadCarton) * (cantidad * 2);
                            carton = (int) carto;
                        //Guardas----------------------------------------------
                        if (Guarda.getSelectedItem().toString().equals("G. BLANCA")){//{"G. BLANCA"};
                            guarda = condicionales.guardasblancas(cantidad, largo, ancho);
                        }else  if (Guarda.getSelectedItem().toString().equals("G. IMP.")){//{"G. IMP."};
                            guarda = condicionales.guardasimpresas(cantidad, largo, ancho);
                        }
                        //Variable----------------------------------------------
                        neto = (acabados) + (plastipaginas + impresion) + (plastiportada + impresionportada) + (carton) + (guarda);

                        if (Variable.isChecked()){
                            variable += condicionales.variable(neto);
                            neto += variable;
                        }

                        AlertDialog.Builder dialogo1 = new AlertDialog.Builder(context);
                        dialogo1.setIcon(R.mipmap.ic_launcher);
                        dialogo1.setTitle("Cotización");
                        dialogo1.setMessage("Cantidad:              " + separador.transformador(cantidad) +
                                "\n\nPortada:               " + separador.transformador(impresionportada) +
                                "\nPlastificado:        " + separador.transformador(plastiportada) +
                                "\n\nPáginas:               " + separador.transformador(impresion) +
                                "\nPlastificado:        " + separador.transformador(plastipaginas) +
                                "\n\nCartón:                 " + separador.transformador(carton) +
                                "\nGuarda:                " + separador.transformador(guarda) +
                                "\n\nAcabados:           " + separador.transformador(acabados) +
                                "\n\nNeto:                     " + separador.transformador(neto));
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
                }else if (EditArgollada.getText().toString().equals("")){
                    EditArgollada.setError("Falta la argollada.");
                    EditArgollada.requestFocus();
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    assert imm != null;
                    imm.showSoftInput(EditArgollada, InputMethodManager.SHOW_IMPLICIT);
                }else if (EditArmada.getText().toString().equals("")){
                    EditArmada.setError("Falta la armada.");
                    EditArmada.requestFocus();
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    assert imm != null;
                    imm.showSoftInput(EditArmada, InputMethodManager.SHOW_IMPLICIT);
                }
            }
        });//Fin Cotizar

        label_cantidad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               separador.decimalEditText(EditCantidad, EditPaginas, EditArgollada, EditArmada,  null);
            }
        });

        label_paginas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                separador.decimalEditText(EditCantidad, EditPaginas, EditArgollada, EditArmada,  null);
            }
        });

        label_argollada.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                separador.decimalEditText(EditCantidad, EditPaginas, EditArgollada, EditArmada,  null);
            }
        });

        label_armada.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                separador.decimalEditText(EditCantidad, EditPaginas, EditArgollada, EditArmada,  null);
            }
        });

        //*---------Spinner Paginas---------------------------------------*

        TipoPaginas.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                confiSpinner.confiSpinner(adapterView);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        PapelPaginas.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                confiSpinner.confiSpinner(adapterView);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        PlastiPaginas.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                confiSpinner.confiSpinner(adapterView);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

            //*---------Spinner Pasta Guarda---------------------------------------*

        Pasta.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                confiSpinner.confiSpinner(adapterView);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        Guarda.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                confiSpinner.confiSpinner(adapterView);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

            //*---------Spinner Portada---------------------------------------*

        TipoPortada.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                confiSpinner.confiSpinner(adapterView);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        PapelPortada.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                confiSpinner.confiSpinner(adapterView);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        PlastiPortada.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                confiSpinner.confiSpinner(adapterView);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        //------------------------------------------------------------------------

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
