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

public class SenaleticasActivity extends AppCompatActivity {

    Separador separador = new Separador();
    Papeles condicionales = new Papeles();
    ConfiSpinner confiSpinner = new ConfiSpinner();

    String[] calibre = {"Cal.30", "Cal.40", "Cal.60"};
    String[] tipo = {"4x0", "4x4"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_senaleticas);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        final Context context = this;
        //-------------------------------------------------------
        TextView label_senaleticas = findViewById(R.id.label_senaleticas);
        TextView label_cantidad = findViewById(R.id.label_cantidad_senaleticas);
        TextView label_cortea = findViewById(R.id.label_cortea_senaleticas);
        TextView label_cortep = findViewById(R.id.label_cortep_senaleticas);
        TextView label_pegada = findViewById(R.id.label_pegada_senaleticas);

        final EditText EditCantidad = findViewById(R.id.box_cantidad_senaleticas);
        final EditText EditCorteA = findViewById(R.id.box_cortea_senaleticas);
        final EditText EditCorteP = findViewById(R.id.box_cortep_senaleticas);
        final EditText EditPegada = findViewById(R.id.box_pegada_senaleticas);

        final EditText EditLargo = findViewById(R.id.box_largo_senaleticas);
        final EditText EditAncho = findViewById(R.id.box_ancho_senaleticas);

        final EditText EditCalibre = findViewById(R.id.box_calibre_senaleticas);

        final Spinner Tipo = findViewById(R.id.spinner_tipo_senaletica);
        final Spinner Calibre = findViewById(R.id.spinner_calibre_senaletica);

        Tipo.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, tipo));
        Calibre.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, calibre));

        Button Cotizar = findViewById(R.id.btn_cotizar_senaleticas);
        Button Volver = findViewById(R.id.btn_volver_senaleticas);
        //-------------------------------------------------------

        Cotizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                separador.decimalEditText(EditCantidad, EditCorteA, EditCorteP, EditPegada, EditCalibre);
                int acabados, plastificado, cantidad, cortea, cortep, pegada, calibre, neto, impresion, hojas;
                impresion = plastificado = 0;
                double largo, ancho;
                if (!EditCalibre.getText().toString().equals("") && !EditCorteA.getText().toString().equals("") &&
                        !EditCorteP.getText().toString().equals("") && !EditPegada.getText().toString().equals("") &&
                        !EditCalibre.getText().toString().equals("")){
                    cantidad = separador.NumeroInt(EditCantidad);
                    cortea = separador.NumeroInt(EditCorteA);
                    cortep = separador.NumeroInt(EditCorteP);
                    pegada = separador.NumeroInt(EditPegada);
                    calibre = separador.NumeroInt(EditCalibre);
                    if (!EditLargo.getText().toString().equals("") && !EditAncho.getText().toString().equals("")){
                        largo = Double.parseDouble(EditLargo.getText().toString());
                        ancho = Double.parseDouble(EditAncho.getText().toString());

                        int cabidadP = condicionales.cabidad(200, 100, largo, ancho);
                        int cabidad = condicionales.cabidad(47, 32, largo, ancho);
                        int poli = ((condicionales.poli(calibre, cabidadP)) * cantidad);
                        hojas = ((condicionales.hojas(cantidad, cabidad)) + 2);


                        if (Tipo.getSelectedItem().toString().equals("4x0")){//4x0
                            impresion = condicionales.papeladhesivo(hojas);
                            plastificado = condicionales.plastificado(hojas);
                        }else if (Tipo.getSelectedItem().toString().equals("4x4")){//4x0
                            impresion = ((condicionales.papeladhesivo(hojas)) * 2);
                            plastificado = (condicionales.plastificado(hojas) * 2);
                        }

                        acabados = cortea + cortep + pegada;
                        neto = acabados + impresion + plastificado + poli;

                        AlertDialog.Builder dialogo1 = new AlertDialog.Builder(context);
                        dialogo1.setIcon(R.mipmap.ic_launcher);
                        dialogo1.setTitle("Cotización");
                        dialogo1.setMessage("Cantidad:              " + separador.transformador(cantidad) +
                                "\n\nPoliestireno:         " + separador.transformador(poli) +
                                "\nAdhesivo:              " + separador.transformador(impresion) +
                                "\nPlastificado:         " + separador.transformador(plastificado) +
                                "\n\nAcabados:             " + separador.transformador(acabados) +
                                "\n\nNeto:                       " + separador.transformador(neto));
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
                }else if (EditCorteA.getText().toString().equals("")){
                    EditCorteA.setError("Falta el corte A.");
                    EditCorteA.requestFocus();
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    assert imm != null;
                    imm.showSoftInput(EditCorteA, InputMethodManager.SHOW_IMPLICIT);
                }else if (EditCorteP.getText().toString().equals("")){
                    EditCorteP.setError("Falta el corte P.");
                    EditCorteP.requestFocus();
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    assert imm != null;
                    imm.showSoftInput(EditCorteP, InputMethodManager.SHOW_IMPLICIT);
                }else if (EditPegada.getText().toString().equals("")){
                    EditPegada.setError("Falta la pegada.");
                    EditPegada.requestFocus();
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    assert imm != null;
                    imm.showSoftInput(EditPegada, InputMethodManager.SHOW_IMPLICIT);
                }else if (EditCalibre.getText().toString().equals("")){
                    EditCalibre.setError("Falta el calibre.");
                    EditCalibre.requestFocus();
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    assert imm != null;
                    imm.showSoftInput(EditCalibre, InputMethodManager.SHOW_IMPLICIT);
                }
            }
        });//Fin Cotizar

        label_senaleticas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                separador.decimalEditText(EditCantidad, EditCorteA, EditCorteP, EditPegada, EditCalibre);
            }
        });

        label_cantidad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                separador.decimalEditText(EditCantidad, EditCorteA, EditCorteP, EditPegada, EditCalibre);
            }
        });

        label_cortea.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                separador.decimalEditText(EditCantidad, EditCorteA, EditCorteP, EditPegada, EditCalibre);
            }
        });

        label_cortep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                separador.decimalEditText(EditCantidad, EditCorteA, EditCorteP, EditPegada, EditCalibre);
            }
        });

        label_pegada.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                separador.decimalEditText(EditCantidad, EditCorteA, EditCorteP, EditPegada, EditCalibre);
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

        Calibre.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                confiSpinner.confiSpinner(adapterView);
                if (Calibre.getSelectedItem().toString().equals("Cal.30")){
                    EditCalibre.setText(String.valueOf(condicionales.calibre30));
                }else if (Calibre.getSelectedItem().toString().equals("Cal.40")){
                    EditCalibre.setText(String.valueOf(condicionales.calibre40));
                }else if (Calibre.getSelectedItem().toString().equals("Cal.60")){
                    EditCalibre.setText(String.valueOf(condicionales.calibre60));
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
