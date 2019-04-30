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

public class DiplomasActivity extends AppCompatActivity {

    Separador separador = new Separador();
    Papeles condicionales = new Papeles();
    ConfiSpinner confiSpinner = new ConfiSpinner();

    String[] papel = {"240g", "PERG.", "ESP."};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diplomas);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        final Context context = this;
        //-------------------------------------------------------
        TextView label_diplomas = findViewById(R.id.label_diplomas);
        TextView label_cantidad = findViewById(R.id.label_cantidad_diplomas);
        TextView label_corte = findViewById(R.id.label_corte_diplomas);

        final EditText EditCantidad = findViewById(R.id.box_cantidad_diplomas);
        final EditText EditCorte = findViewById(R.id.box_corte_diplomas);
        final EditText EditEspecial = findViewById(R.id.box_especial_diplomas);

        final EditText EditLargo = findViewById(R.id.box_largo_diplomas);
        final EditText EditAncho = findViewById(R.id.box_ancho_diplomas);

        final Spinner Paper = findViewById(R.id.spinner_papel_diplomas);
        Paper.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, papel));

        Button Cotizar = findViewById(R.id.btn_cotizar_diplomas);
        Button Volver = findViewById(R.id.btn_volver_diplomas);

        ImageButton Nota = findViewById(R.id.btn_ima_info_diplomas);

        final Switch Variable = findViewById(R.id.switch_variable_diplomas);
        EditCantidad.requestFocus();
        //-------------------------------------------------------

        Cotizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                separador.decimalEditText(EditCantidad, EditCorte, EditEspecial, null, null);
                int cantidad, hojas, impresion, corte, plastificado, especial, variable, neto;
                impresion = neto = variable = 0;
                double largo, ancho;

                if (!EditCantidad.getText().toString().equals("") && !EditCorte.getText().toString().equals("")){
                    cantidad = separador.NumeroInt(EditCantidad);
                    corte = separador.NumeroInt(EditCorte);

                    if (!EditLargo.getText().toString().equals("") && !EditAncho.getText().toString().equals("")){
                        largo = Double.parseDouble(EditLargo.getText().toString());
                        ancho = Double.parseDouble(EditAncho.getText().toString());

                        int cabidad = condicionales.cabidad(47, 32, largo, ancho);
                        hojas = condicionales.hojas(cantidad, cabidad);

                        if (Paper.getSelectedItem().toString().equals("240g")){
                            impresion = condicionales.papel240(hojas);
                        }else if (Paper.getSelectedItem().toString().equals("PERG.")){
                            impresion = condicionales.papelpergamino(hojas);
                        }else if (Paper.getSelectedItem().toString().equals("ESP.")){
                            if (!EditEspecial.getText().toString().equals("")){
                                especial = separador.NumeroInt(EditEspecial);
                                impresion = condicionales.papelespecial(hojas, especial);
                            }else{
                                EditEspecial.setError("Falta el papel especial.");
                                EditEspecial.requestFocus();
                                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                                assert imm != null;
                                imm.showSoftInput(EditEspecial, InputMethodManager.SHOW_IMPLICIT);
                                return;
                            }
                        }
                        neto += corte + impresion;

                        if (Variable.isChecked()){
                            variable += condicionales.variable(neto);
                            neto += variable;
                        }

                        AlertDialog.Builder dialogo1 = new AlertDialog.Builder(context);
                        dialogo1.setIcon(R.mipmap.ic_launcher);
                        dialogo1.setTitle("Cotización");
                        dialogo1.setMessage("Cantidad:              " + separador.transformador(cantidad) +
                                "\nHojas:                    " + separador.transformador(hojas) +
                                "\n\nImpresión:            " + separador.transformador(impresion) +
                                "\n\nCorte:                    " + separador.transformador(corte) +
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

        label_diplomas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                separador.decimalEditText(EditCantidad, EditCorte, EditEspecial, null, null);
            }
        });

        label_cantidad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                separador.decimalEditText(EditCantidad, EditCorte, EditEspecial, null, null);
            }
        });

        label_corte.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                separador.decimalEditText(EditCantidad, EditCorte, EditEspecial, null, null);
            }
        });

        Paper.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                confiSpinner.confiSpinner(adapterView);
                if (Paper.getSelectedItem().toString().equals("ESP.")){
                    EditEspecial.setVisibility(View.VISIBLE);
                }else {
                    EditEspecial.setVisibility(View.GONE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        Nota.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder dialogo1 = new AlertDialog.Builder(context);
                dialogo1.setIcon(R.mipmap.ic_launcher);
                dialogo1.setTitle("Nota");
                dialogo1.setMessage("El valor del papel especial sera sumado al papel 300g.\n\n" +
                        "Ejemplo: Se evaluara con 3 hojas y el papel especial sera de 850.\n\n" +
                        "Resultado = ((3100 + 850) * 3)");
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

    }//Fin onCreate

    @Override
    public void onBackPressed () {
        finish();
    }

}//Fin Clase
