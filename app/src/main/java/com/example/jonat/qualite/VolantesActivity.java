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
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;

public class VolantesActivity extends AppCompatActivity {

    Separador separador = new Separador();
    Papeles condicionales = new Papeles();
    ConfiSpinner confiSpinner = new ConfiSpinner();

    String[] tipo = {"4x0","4x4"};
    String[] papel = {"150g","200g", "240g"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_volantes);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        final Context context = this;
        //-------------------------------------------------------
        TextView label_cantidad = findViewById(R.id.label_cantidad_volantes);
        TextView label_corte = findViewById(R.id.label_corte_volantes);
        final TextView label_doblada = findViewById(R.id.label_doblada_volantes);

        final EditText EditCantidad = findViewById(R.id.box_cantidad_volantes);
        final EditText EditCorte = findViewById(R.id.box_corte_volantes);
        final EditText EditDoblada = findViewById(R.id.box_doblada_volante);

        final EditText EditLargo = findViewById(R.id.box_largo_volantes);
        final EditText EditAncho = findViewById(R.id.box_ancho_volantes);

        final Spinner Tipo = findViewById(R.id.spinner_tipo_volantes);
        final Spinner Papel = findViewById(R.id.spinner_papel_volantes);

        Tipo.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, tipo));
        Papel.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, papel));

        final Switch Volantes = findViewById(R.id.switch_volantes);
        final Switch Plegables = findViewById(R.id.switch_plegables);
        final Switch Plastificado = findViewById(R.id.switch_plastificado_volantes);

        Button Cotizar = findViewById(R.id.btn_cotizar_volantes);
        Button Volver = findViewById(R.id.btn_volver_volantes);
        //-------------------------------------------------------

        Cotizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                separador.decimalEditText(EditCantidad, EditCorte, EditDoblada, null, null);
                int hojas, cantidad, corte, doblada, impresion, plastificado, neto;
                doblada = neto = impresion = plastificado = 0;
                double largo, ancho;

                if (!EditCantidad.getText().toString().equals("") && !EditCorte.getText().toString().equals("")){
                    cantidad = separador.NumeroInt(EditCantidad);
                    corte = separador.NumeroInt(EditCorte);
                    if (!EditLargo.getText().toString().equals("") && !EditAncho.getText().toString().equals("")){
                        largo = Double.parseDouble(EditLargo.getText().toString());
                        ancho = Double.parseDouble(EditAncho.getText().toString());
                        if (Plegables.isChecked()){
                            if (!EditDoblada.getText().toString().equals("")){
                                doblada = separador.NumeroInt(EditDoblada);
                            }else {
                                EditDoblada.setError("Falta la doblada.");
                                EditDoblada.requestFocus();
                                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                                assert imm != null;
                                imm.showSoftInput(EditDoblada, InputMethodManager.SHOW_IMPLICIT);
                                return;
                            }
                        }//Plegable is Checked

                        int cabidad = condicionales.cabidad(47, 32, largo, ancho);
                        hojas = condicionales.hojas(cantidad, cabidad);

                        if (Tipo.getSelectedItem().toString().equals("4x4")){
                            hojas *= 2;
                        }

                        if (Papel.getSelectedItem().toString().equals("150g")){
                            impresion = condicionales.papel150(hojas);
                        }else if (Papel.getSelectedItem().toString().equals("200g")){
                            impresion = condicionales.papel200(hojas);
                            if (Plastificado.isChecked()){
                                plastificado = condicionales.plastificado(hojas);
                            }
                        }else if (Papel.getSelectedItem().toString().equals("240g")){
                            impresion = condicionales.papel240(hojas);
                            if (Plastificado.isChecked()){
                                plastificado = condicionales.plastificado(hojas);
                            }
                        }

                        int acabados = corte + doblada;
                        neto = impresion + plastificado + acabados;

                        AlertDialog.Builder dialogo1 = new AlertDialog.Builder(context);
                        dialogo1.setIcon(R.mipmap.ic_launcher);
                        dialogo1.setTitle("Cotización");
                        dialogo1.setMessage("Cantidad:             " + separador.transformador(cantidad) +
                                "\n\nHojas:                   " + separador.transformador(hojas) +
                                "\nImpresion            " + separador.transformador(impresion) +
                                "\nPlastificado:        " + separador.transformador(plastificado) +
                                "\n\nAcabados             " + separador.transformador(acabados) +
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
                    EditCorte.setError("Falta el corte");
                    EditCorte.requestFocus();
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    assert imm != null;
                    imm.showSoftInput(EditCorte, InputMethodManager.SHOW_IMPLICIT);
                }
            }
        });//Fin Cotizar

        label_cantidad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                separador.decimalEditText(EditCantidad, EditCorte, EditDoblada, null, null);
            }
        });

        label_corte.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                separador.decimalEditText(EditCantidad, EditCorte, EditDoblada, null, null);
            }
        });

        label_doblada.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                separador.decimalEditText(EditCantidad, EditCorte, EditDoblada, null, null);
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
                if (Papel.getSelectedItem().toString().equals("150g")){
                    Plastificado.setVisibility(View.GONE);
                }else {
                    Plastificado.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        Volantes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(Volantes.isChecked()){
                    Plegables.setChecked(false);
                    label_doblada.setVisibility(View.GONE);
                    EditDoblada.setVisibility(View.GONE);
                }else{
                    Plegables.setChecked(true);
                    label_doblada.setVisibility(View.VISIBLE);
                    EditDoblada.setVisibility(View.VISIBLE);
                }
            }
        });

        Plegables.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(Plegables.isChecked()){
                    Volantes.setChecked(false);
                    label_doblada.setVisibility(View.VISIBLE);
                    EditDoblada.setVisibility(View.VISIBLE);
                }else {
                    Volantes.setChecked(true);
                    label_doblada.setVisibility(View.GONE);
                    EditDoblada.setVisibility(View.GONE);
                }
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



