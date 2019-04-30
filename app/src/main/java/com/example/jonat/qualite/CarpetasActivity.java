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
import android.widget.Switch;
import android.widget.TextView;

public class CarpetasActivity extends AppCompatActivity {
    Separador separador = new Separador();
    Papeles condicionales = new Papeles();
    ConfiSpinner confiSpinner = new ConfiSpinner();

    String[] tipo = {"4x0", "4x4"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_carpetas);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        final Context context = this;
        //----------------------------------------------------------
        TextView label_carpetas = findViewById(R.id.label_carpetas);
        TextView label_cantidad = findViewById(R.id.label_cantidad_carp);
        TextView label_corte = findViewById(R.id.label_corte_carp);
        TextView label_grafa = findViewById(R.id.label_grafa_carp);
        final TextView label_pegada = findViewById(R.id.label_pegada_carp);
        final TextView label_troquel = findViewById(R.id.label_troquel_carp);
        final TextView label_cabidad = findViewById(R.id.label_cab_carp);

        final EditText EditCantidad = findViewById(R.id.box_cantidad_carp);
        final EditText EditCorte = findViewById(R.id.box_corte_carp);
        final EditText EditGrafa = findViewById(R.id.box_grafa_carp);
        final EditText EditPegada = findViewById(R.id.box_pegada_carp);
        final EditText EditCabidad = findViewById(R.id.box_cabidad_carp);
        final EditText EditTroquel = findViewById(R.id.box_troquel_carp);

        final Switch CheckTroquelada = findViewById(R.id.switch_troquelada_carp);
        final Switch CheckBolsillo = findViewById(R.id.switch_bolsillo_carp);
        final Switch CheckVariable = findViewById(R.id.switch_variable_carp);

        final Spinner Tipo = findViewById(R.id.spinner_tipo_carpetas);
        Tipo.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, tipo));

        ImageButton Nota = findViewById(R.id.btn_ima_ayuda_carpetas);
        Button Cotizar = findViewById(R.id.btn_cotizar_carp);
        Button Volver = findViewById(R.id.btn_volver_carp);
        //----------------------------------------------------------

        Cotizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                separador.decimalEditText(EditCantidad, EditCorte, EditGrafa, EditPegada, EditTroquel);
                int troquelada, plastibolsi, impresionbolsi, hojasbolsi, carpetas, cantidad, corte, grafa,troquel, cabidad, pegada, impresion, plastificado, hojas, variable, neto;
               troquel = plastificado = impresionbolsi = plastibolsi = troquelada = hojasbolsi = impresion = pegada = cabidad = carpetas = neto = variable = 0;

                    if (!EditCantidad.getText().toString().equals("") && !EditCorte.getText().toString().equals("") &&
                            !EditGrafa.getText().toString().equals("")){
                        cantidad = separador.NumeroInt(EditCantidad);
                        corte = separador.NumeroInt(EditCorte);
                        grafa = separador.NumeroInt(EditGrafa);

                        if (cantidad >= 1 && cantidad <= 10){
                            cantidad += 1;
                        }else if (cantidad >= 11 && cantidad <= 20){
                            cantidad += 2;
                        }else if (cantidad >= 21 && cantidad <= 50){
                            cantidad += 3;
                        }else if (cantidad >= 51 && cantidad <= 100){
                            cantidad += 4;
                        }else if (cantidad > 100){
                            cantidad += 5;
                        }
                        if (cantidad != 0){
                            carpetas = condicionales.masHojas(cantidad);
                        }

                        hojas = carpetas;

                        if (CheckBolsillo.isChecked()){
                            if (!EditPegada.getText().toString().equals("")){
                                pegada = separador.NumeroInt(EditPegada);

                                if (!EditTroquel.getText().toString().equals("")){
                                    troquel = separador.NumeroInt(EditTroquel);
                                }
                                if (!EditCabidad.getText().toString().equals("")){
                                    cabidad = separador.NumeroInt(EditCabidad);
                                }
                                if (Tipo.getSelectedItem().toString().equals("4x0")){
                                    impresion = impresion = condicionales.papel300(cantidad);
                                    plastificado = condicionales.plastificado(cantidad);

                                    if (cabidad == 0){
                                        hojasbolsi = condicionales.hojas(cantidad, 4);
                                        hojasbolsi = condicionales.masHojas(hojasbolsi);
                                    }else if (cabidad > 0){
                                        hojasbolsi = condicionales.hojas(cantidad, cabidad);
                                        hojasbolsi = condicionales.masHojas(hojasbolsi);
                                    }
                                    impresionbolsi = condicionales.papelbolsi(hojasbolsi, cantidad);
                                    plastibolsi = condicionales.plastificadobolsiilo(hojasbolsi, cantidad);
                                }else if (Tipo.getSelectedItem().toString().equals("4x4")){
                                    impresion = impresion = condicionales.papel300(cantidad*2);
                                    plastificado = condicionales.plastificado(cantidad*2);

                                    if (cabidad == 0){
                                        hojasbolsi = condicionales.hojas(cantidad, 4);
                                        hojasbolsi = condicionales.masHojas(hojasbolsi);
                                    }else if (cabidad > 0){
                                        hojasbolsi = condicionales.hojas(cantidad, cabidad);
                                        hojasbolsi = condicionales.masHojas(hojasbolsi);
                                    }
                                    impresionbolsi = condicionales.papelbolsi(hojasbolsi, cantidad*2);
                                    plastibolsi = condicionales.plastificadobolsiilo(hojasbolsi, cantidad*2);
                                }
                            }else if (EditPegada.getText().toString().equals("")){
                                EditPegada.setError("Falta la pegada.");
                                EditPegada.requestFocus();
                                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                                assert imm != null;
                                imm.showSoftInput(EditPegada, InputMethodManager.SHOW_IMPLICIT);
                                return;
                            }
                        }else {//Si bolsillo no Check
                            if (Tipo.getSelectedItem().toString().equals("4x0")){
                                impresion = condicionales.papel300(carpetas);
                                plastificado = condicionales.plastificado(carpetas);
                            }else if (Tipo.getSelectedItem().toString().equals("4x4")){
                                impresion = impresion = condicionales.papel300(carpetas*2);
                                plastificado = condicionales.plastificado(carpetas*2);
                            }
                        }//Fin bolsillos

                        if (CheckTroquelada.isChecked()){
                            troquelada = condicionales.troquelada(cantidad);
                        }

                        int acabados = grafa + pegada + corte + troquelada;
                        neto += impresion + plastificado + acabados + troquel + impresionbolsi + plastibolsi;

                        if (CheckVariable.isChecked()){
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
                                "\n\nImpresión B:        " + separador.transformador(impresionbolsi) +
                                "\nPlastificado B:     " + separador.transformador(plastibolsi) +
                                "\n\nAcabados:            " + separador.transformador(acabados) +
                                "\n\nVariable:               " + separador.transformador(variable) +
                                "\nNeto:                     " + separador.transformador(neto));
                        dialogo1.setCancelable(false);
                        dialogo1.setNegativeButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogo1, int i) {
                                dialogo1.cancel();
                            }
                        });
                        dialogo1.show();

                    }else if (EditCantidad.getText().toString().equals("")){
                        EditCantidad.setError("Falta la cantidad.");
                        EditCantidad.requestFocus();
                        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                        assert imm != null;
                        imm.showSoftInput(EditCantidad, InputMethodManager.SHOW_IMPLICIT);
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
                    }
            }
        });//Fin Cotizar

        label_carpetas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                separador.decimalEditText(EditCantidad, EditCorte, EditGrafa, EditPegada, EditTroquel);
            }
        });

        label_cantidad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                separador.decimalEditText(EditCantidad, EditCorte, EditGrafa, EditPegada, EditTroquel);
            }
        });

        label_corte.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                separador.decimalEditText(EditCantidad, EditCorte, EditGrafa, EditPegada, EditTroquel);
            }
        });

        label_grafa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                separador.decimalEditText(EditCantidad, EditCorte, EditGrafa, EditPegada, EditTroquel);
            }
        });

        label_pegada.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                separador.decimalEditText(EditCantidad, EditCorte, EditGrafa, EditPegada, EditTroquel);
            }
        });

        label_cabidad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                separador.decimalEditText(EditCantidad, EditCorte, EditGrafa, EditPegada, EditTroquel);
            }
        });

        label_troquel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                separador.decimalEditText(EditCantidad, EditCorte, EditGrafa, EditPegada, EditTroquel);
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

        CheckBolsillo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (CheckBolsillo.isChecked()){
                    label_cabidad.setVisibility(View.VISIBLE);
                    label_troquel.setVisibility(View.VISIBLE);
                    label_pegada.setVisibility(View.VISIBLE);
                    EditCabidad.setVisibility(View.VISIBLE);
                    EditTroquel.setVisibility(View.VISIBLE);
                    EditPegada.setVisibility(View.VISIBLE);
                    CheckTroquelada.setVisibility(View.VISIBLE);
                    CheckTroquelada.setChecked(false);
                }else {
                    label_cabidad.setVisibility(View.GONE);
                    label_troquel.setVisibility(View.GONE);
                    label_pegada.setVisibility(View.GONE);
                    EditCabidad.setVisibility(View.GONE);
                    EditTroquel.setVisibility(View.GONE);
                    EditPegada.setVisibility(View.GONE);
                    CheckTroquelada.setVisibility(View.GONE);
                    CheckTroquelada.setChecked(false);
                }
            }
        });

        Nota.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder dialogo1 = new AlertDialog.Builder(context);
                dialogo1.setIcon(R.mipmap.ic_launcher);
                dialogo1.setTitle("Nota");
                dialogo1.setMessage("1   - 10 --> Cantidad + 1" +
                                    "\n11 - 20 --> Cantidad + 2" +
                                    "\n21 - 50 --> Cantidad + 3" +
                                    "\n51-100 --> Cantidad + 4" +
                                    "\n   >100 --> Cantidad + 5");
                dialogo1.setCancelable(false);
                dialogo1.setNegativeButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogo1, int i) {
                        dialogo1.cancel();
                    }
                });
                dialogo1.show();
            }
        });

        Volver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }//Fin Oncreate

    @Override
    public void onBackPressed () {
        finish();
    }

}//Fin Clase
