package com.example.jonat.qualite;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
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

public class StickersActivity extends AppCompatActivity {

    Separador separador = new Separador();
    Papeles condicionales = new Papeles();
    ConfiSpinner confiSpinner = new ConfiSpinner();

    String[] tipo = {"4x0", "4x4"};
    String[] papel = {"ADH.", "300g"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stickers);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        final Context context = this;
        //-------------------------------------------------------
        TextView label_stickers = findViewById(R.id.label_stickers);
        TextView label_cantidad = findViewById(R.id.label_cantidad_stickers);
        TextView label_corte = findViewById(R.id.label_corte_stickers);
        TextView label_troquel = findViewById(R.id.label_troquel_stickers);

        final EditText EditCantidad = findViewById(R.id.box_cantidad_stickers);
        final EditText EditCorte = findViewById(R.id.box_corte_stickers);
        final EditText EditTroquel = findViewById(R.id.box_troquel_stickers);

        final EditText EditLargo = findViewById(R.id.box_largo_stickers);
        final EditText EditAncho = findViewById(R.id.box_ancho_stickers);

        final Spinner Tipo = findViewById(R.id.spinner_tipo_stickers);
        final Spinner Papel = findViewById(R.id.spinner_papel_stickers);

        Tipo.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, tipo));
        Papel.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, papel));

        final Switch Variable = findViewById(R.id.switch_variable_stickers);
        final Switch Plastificado = findViewById(R.id.switch_plastificado_stickers);

        Button Cotizar = findViewById(R.id.btn_cotizar_stickers);
        Button Volver = findViewById(R.id.btn_volver_stickers);
        //-------------------------------------------------------

        Cotizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                separador.decimalEditText(EditCantidad, EditCorte, EditTroquel, null, null);
                int variable, acabados, cantidad, troquel, corte, impresion, plastificado, neto, hojas;
                variable = impresion = plastificado = neto = troquel = 0;
                double largo, ancho;

                if (!EditCantidad.getText().toString().equals("") && !EditCorte.getText().toString().equals("")){
                    cantidad = separador.NumeroInt(EditCantidad);
                    corte = separador.NumeroInt(EditCorte);
                    if (!EditLargo.getText().toString().equals("") && !EditAncho.getText().toString().equals("")){
                        largo = Double.parseDouble(EditLargo.getText().toString());
                        ancho = Double.parseDouble(EditAncho.getText().toString());

                        if (!EditTroquel.getText().toString().equals("")){
                            troquel = separador.NumeroInt(EditTroquel);
                        }

                        int cabidad = condicionales.cabidad(47, 32, largo, ancho);
                        hojas = (condicionales.hojas(cantidad, cabidad) + 1) ;

                        if (Papel.getSelectedItem().toString().equals("ADH.")){//Adhesivo
                            impresion = condicionales.papeladhesivo(hojas);
                        }else if (Papel.getSelectedItem().toString().equals("300g")){//300
                            if (Tipo.getSelectedItem().toString().equals("4x0")){//4x0
                                impresion = condicionales.papel300(hojas);
                            }else if (Tipo.getSelectedItem().toString().equals("4x4")){//4x4
                                impresion = condicionales.papel300(hojas*2);
                            }
                        }
                        if (Plastificado.isChecked()){
                            plastificado = condicionales.plastificado(hojas);
                        }

                        acabados = corte + troquel;
                        neto += impresion +  acabados + plastificado;

                        if (Variable.isChecked()){
                            variable += condicionales.variable(neto);
                            neto += variable;
                        }

                        AlertDialog.Builder dialogo1 = new AlertDialog.Builder(context);
                        dialogo1.setIcon(R.mipmap.ic_launcher);
                        dialogo1.setTitle("Cotización");
                        dialogo1.setMessage("Cantidad:             " + separador.transformador(cantidad) +
                                "\n\nHojas:                   " + separador.transformador(hojas) +
                                "\nImpresion:           " + separador.transformador(impresion) +
                                "\nPlastificado:        " + separador.transformador(plastificado) +
                                "\n\nAcabados:           " + separador.transformador(acabados) +
                                "\nVariable:              " + separador.transformador(variable) +
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
                        EditLargo.setError("Falta el largo");
                        EditLargo.requestFocus();
                        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                        assert imm != null;
                        imm.showSoftInput(EditLargo, InputMethodManager.SHOW_IMPLICIT);
                        return;
                    }else if (EditAncho.getText().toString().equals("")){
                        EditAncho.setError("Falta el ancho");
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

        label_stickers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                separador.decimalEditText(EditCantidad, EditCorte, EditTroquel, null, null);
            }
        });

        label_cantidad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                separador.decimalEditText(EditCantidad, EditCorte, EditTroquel, null, null);
            }
        });

        label_corte.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                separador.decimalEditText(EditCantidad, EditCorte, EditTroquel, null, null);
            }
        });

        label_troquel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                separador.decimalEditText(EditCantidad, EditCorte, EditTroquel, null, null);
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
                if (Papel.getSelectedItem().toString().equals("ADH.")){
                    Tipo.setVisibility(View.GONE);
                }else {
                    Tipo.setVisibility(View.VISIBLE);
                    Tipo.setSelection(0);
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
