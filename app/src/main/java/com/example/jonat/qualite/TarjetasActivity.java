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

public class TarjetasActivity extends AppCompatActivity {

    Separador separador = new Separador();
    Papeles condicionales = new Papeles();
    ConfiSpinner confiSpinner = new ConfiSpinner();

    String[] tipo = {"4x0","4x4"};
    String[] papel = {"200g","300g", "ESP."};
    String[] plasti = {"PLAST.","4x0", "4x4"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tarjetas);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        final Context context = this;
        //-------------------------------------------------------
        TextView label_tarjetas = findViewById(R.id.label_tarjetas);
        TextView label_cantidad = findViewById(R.id.label_cantidad_tarjetas);
        TextView label_corte = findViewById(R.id.label_corte_tarjetas);
        TextView label_grafa = findViewById(R.id.label_grafa_tarjetas);
        TextView label_despuntada = findViewById(R.id.label_despuntada_tarjetas);

        final EditText EditCantidad = findViewById(R.id.box_cantidad_tarjetas);
        final EditText EditCorte = findViewById(R.id.box_corte_tarjetas);
        final EditText EditGrafa = findViewById(R.id.box_grafa_tarjetas);
        final EditText EditDespuntada = findViewById(R.id.box_despuntada_tarjetas);
        final EditText EditEspecial = findViewById(R.id.box_especial_tarjetas);

        final EditText EditLargo = findViewById(R.id.box_largo_tarjetas);
        final EditText EditAncho = findViewById(R.id.box_ancho_tarjetas);

        final Spinner Tipo = findViewById(R.id.spinner_tipo_tarjetas);
        final Spinner Papel = findViewById(R.id.spinner_papel_tarjetas);
        final Spinner Plasti = findViewById(R.id.spinner_plastificado_tarjetas);

        Tipo.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, tipo));
        Papel.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, papel));
        Plasti.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, plasti));

        Button Cotizar = findViewById(R.id.btn_cotizar_tarjetas);
        Button Volver = findViewById(R.id.btn_volver_tarjetas);

        final Switch Variable = findViewById(R.id.switch_variable_tarjetas);
        //-------------------------------------------------------

        Cotizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                separador.decimalEditText(EditCantidad, EditCorte, EditGrafa, EditDespuntada, EditEspecial);
                int especial, hojas, cantidad, corte, grafa, despuntada, impresion, plastificado, neto, variable;
                grafa = despuntada = neto = variable = impresion = plastificado = especial = 0;
                double largo, ancho;

                if (!EditCantidad.getText().toString().equals("") && !EditCorte.getText().toString().equals("")){
                    cantidad = separador.NumeroInt(EditCantidad);
                    corte = separador.NumeroInt(EditCorte);
                    if (!EditLargo.getText().toString().equals("") && !EditAncho.getText().toString().equals("")){
                        largo = Double.parseDouble(EditLargo.getText().toString());
                        ancho = Double.parseDouble(EditAncho.getText().toString());

                        if (!EditGrafa.getText().toString().equals("")){
                            grafa = separador.NumeroInt(EditGrafa);
                        }if (!EditDespuntada.getText().toString().equals("")){
                            despuntada = separador.NumeroInt(EditDespuntada);
                        }

                        int cabidad = condicionales.cabidad(47, 32, largo, ancho);
                        boolean doble = false;
                        hojas = condicionales.hojas(cantidad, cabidad);

                        if (Tipo.getSelectedItem().toString().equals("4x4")){
                            doble = true;
                            hojas *= 2;
                        }

                        if  (Papel.getSelectedItem().toString().equals("200g")){//{"200g","300g", "ESP."};
                            impresion = condicionales.papel200(hojas);
                        }else if (Papel.getSelectedItem().toString().equals("300g")){
                            impresion = condicionales.papel300(hojas);
                        }else if (Papel.getSelectedItem().toString().equals("ESP.")){
                            if (!EditEspecial.getText().toString().equals("")){
                                especial = separador.NumeroInt(EditEspecial);
                            }else{
                                EditEspecial.setError("Falta el papel especial.");
                                EditEspecial.requestFocus();
                                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                                assert imm != null;
                                imm.showSoftInput(EditEspecial, InputMethodManager.SHOW_IMPLICIT);
                                return;}
                            impresion = condicionales.papelespecial(hojas, especial);
                        }//Fin Papel Especial

                        if (Plasti.getSelectedItem().toString().equals("4x0")){//{"PLAST.","4x0", "4x4"};
                            plastificado = condicionales.plastificado(hojas);
                        }else if (Plasti.getSelectedItem().toString().equals("4x4")){
                            if (doble){
                                plastificado = condicionales.plastificado(hojas);
                            }else{
                                plastificado = condicionales.plastificado(hojas*2);
                            }
                        }

                        int acabados = corte + grafa + despuntada;
                        neto = acabados + impresion + plastificado;

                        if (Variable.isChecked()){
                            variable += condicionales.variable(neto);
                            neto += variable;
                        }

                        AlertDialog.Builder dialogo1 = new AlertDialog.Builder(context);
                        dialogo1.setIcon(R.mipmap.ic_launcher);
                        dialogo1.setTitle("Cotización");
                        dialogo1.setMessage("Cantidad:             " + separador.transformador(cantidad) +
                                "\n\nHojas:                   " + separador.transformador(hojas) +
                                "\nImpresion            " + separador.transformador(impresion) +
                                "\nPlastificado:        " + separador.transformador(plastificado) +
                                "\n\nAcabados:            " + separador.transformador(acabados) +
                                "\n\nVariable:               " + separador.transformador(variable) +
                                "\n\nNeto:                      " + separador.transformador(neto));
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
                }else if (EditCorte.getText().toString().equals("")){
                    EditCorte.setError("Falta el corte.");
                    EditCorte.requestFocus();
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    assert imm != null;
                    imm.showSoftInput(EditCorte, InputMethodManager.SHOW_IMPLICIT);
                }
            }
        });//Fin Cotizar



        label_tarjetas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                separador.decimalEditText(EditCantidad, EditCorte, EditGrafa, EditDespuntada, EditEspecial);
            }
        });

        label_cantidad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                separador.decimalEditText(EditCantidad, EditCorte, EditGrafa, EditDespuntada, EditEspecial);
            }
        });

        label_corte.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                separador.decimalEditText(EditCantidad, EditCorte, EditGrafa, EditDespuntada, EditEspecial);
            }
        });

        label_grafa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                separador.decimalEditText(EditCantidad, EditCorte, EditGrafa, EditDespuntada, EditEspecial);
            }
        });

        label_despuntada.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                separador.decimalEditText(EditCantidad, EditCorte, EditGrafa, EditDespuntada, EditEspecial);
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

        Papel.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                confiSpinner.confiSpinner(adapterView);
                if (Papel.getSelectedItem().toString().equals("ESP.")){
                    EditEspecial.setVisibility(View.VISIBLE);
                }else {EditEspecial.setVisibility(View.GONE);}
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
