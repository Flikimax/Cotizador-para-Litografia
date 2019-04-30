package com.example.jonat.qualite;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;

public class AfichesActivity extends AppCompatActivity {
    Separador separador = new Separador();
    Papeles condicionales = new Papeles();
    ConfiSpinner confiSpinner = new ConfiSpinner();

    String[] papel = {"150g","200g","240g","ADH."};
    String[] plast = {"PLAST", "4x0", "4x4"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_afiches);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        final Context context = this;
        //-------------------------------------------------------
        final Spinner paper = findViewById(R.id.spinner_papel_afiches);
        final Spinner plasti = findViewById(R.id.spinner_plasti_afiches);

        paper.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, papel));
        plasti.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, plast));

        TextView label_afiches = findViewById(R.id.label_afiches);
        TextView label_cantidad = findViewById(R.id.label_cantidad_afiches);
        TextView label_corte = findViewById(R.id.label_acabados_afic);

        final EditText EditCantidad = findViewById(R.id.box_cantidad_afic);
        final EditText EditCorte = findViewById(R.id.box_corte_afic);
        final EditText EditLargo = findViewById(R.id.box_largo_afiches);
        final EditText EditAncho = findViewById(R.id.box_ancho_afiches);

        final Switch Variable = findViewById(R.id.switch_variable_afic);

        Button Cotizar = findViewById(R.id.btn_cotizar_afic);
        Button Volver = findViewById(R.id.btn_volver_afic);
        //-------------------------------------------------------

        Cotizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                separador.decimalEditText(EditCantidad, EditCorte, null, null, null);
                int cantidad, corte, impresion, plastificado, hojas, variable, neto;
                neto = variable = 0;
                double largo, ancho;

                if (!EditCantidad.getText().toString().equals("") && !EditCorte.getText().toString().equals("")){
                    cantidad = separador.NumeroInt(EditCantidad);
                    corte = separador.NumeroInt(EditCorte);
                    if (!EditLargo.getText().toString().equals("") && !EditAncho.getText().toString().equals("")){
                        largo = Double.parseDouble (EditLargo.getText().toString());
                        ancho = Double.parseDouble(EditAncho.getText().toString());

                        int cabidad = condicionales.cabidad(47, 32, largo, ancho);
                        hojas = condicionales.hojas(cantidad, cabidad);

                        if (paper.getSelectedItem().toString().equals("150g")){
                            impresion = condicionales.papel150(hojas);
                        }else if (paper.getSelectedItem().toString().equals("200g")){
                            impresion = condicionales.papel200(hojas);
                        }else if (paper.getSelectedItem().toString().equals("240g")){
                            impresion = condicionales.papel240(hojas);
                        }else if (paper.getSelectedItem().toString().equals("ADH.")){
                            impresion = condicionales.papeladhesivo(hojas);
                        }else {impresion = 0;}

                        if (plasti.getSelectedItem().toString().equals("4x0")){
                            plastificado = condicionales.plastificado(hojas);
                        }else if (plasti.getSelectedItem().toString().equals("4x4")){
                            plastificado = condicionales.plastificado(hojas*2);
                        }else {plastificado = 0;}

                        neto += impresion + plastificado + corte;

                        if (Variable.isChecked()){
                            variable += condicionales.variable(neto);
                            neto += variable;
                        }

                        AlertDialog.Builder dialogo1 = new AlertDialog.Builder(context);
                        dialogo1.setIcon(R.mipmap.ic_launcher);
                        dialogo1.setTitle("Cotización");
                        dialogo1.setMessage("Cantidad:              " + separador.transformador(cantidad) +
                                "\nHojas:                    " + separador.transformador(hojas) +
                                "\nCabidad:                " + separador.transformador(cabidad) +
                                "\n\nImpresión:            " + separador.transformador(impresion) +
                                "\nPlastificado:         " + separador.transformador(plastificado) +
                                "\n\nCorte:                    " + separador.transformador(corte) +
                                "\n\nVariable:                " + separador.transformador(variable) +
                                "\nNeto:                      " + separador.transformador(neto));
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

        label_afiches.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                separador.decimalEditText(EditCantidad, EditCorte, null, null, null);
            }
        });

        label_cantidad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                separador.decimalEditText(EditCantidad, EditCorte, null, null, null);
            }
        });

        label_corte.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                separador.decimalEditText(EditCantidad, EditCorte, null, null, null);
            }
        });

        paper.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                confiSpinner.confiSpinner(adapterView);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

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
