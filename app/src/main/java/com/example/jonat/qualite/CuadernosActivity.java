package com.example.jonat.qualite;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.ActivityInfo;
import android.os.Handler;
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

public class CuadernosActivity extends AppCompatActivity {

    Separador separador = new Separador();
    Papeles condicionales = new Papeles();
    ConfiSpinner confiSpinner = new ConfiSpinner();

    String[] guar = {"G. BLANCA","G. IMPR."};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cuadernos);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        final Context context = this;
        //-------------------------------------------------------
        TextView label_cuadernos = findViewById(R.id.label_cuadernos);
        TextView label_cantidad = findViewById(R.id.label_cantidad_cuadernos);
        TextView label_hojas = findViewById(R.id.label_hojas_cuadernos);
        TextView label_insertos = findViewById(R.id.label_insertos_cuadernos);
        TextView label_argollada = findViewById(R.id.label_argollada_cuadernos);
        TextView label_armada = findViewById(R.id.label_armada_cuadernos);

        final EditText EditCantidad = findViewById(R.id.box_cantidad_cuadernos);
        final EditText EditHojas = findViewById(R.id.box_hojas_cuadernos);
        final EditText EditInsertos = findViewById(R.id.box_insertos_cuadernos);
        final EditText EditArgollada = findViewById(R.id.box_argollada_cuadernos);
        final EditText EditArmada = findViewById(R.id.box_armada_cuadernos);

        final EditText EditLargo = findViewById(R.id.box_largo_cuadernos);
        final EditText EditAncho = findViewById(R.id.box_ancho_cuadernos);

        final Spinner Guarda = findViewById(R.id.spinner_guarda_cuadernos);
        Guarda.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, guar));

        Button Cotizar = findViewById(R.id.btn_cotizar_cuadernos);
        Button Volver = findViewById(R.id.btn_volver_cuadernos);

        ImageButton Nota = findViewById(R.id.btn_ima_info_cuadernos);
        //-------------------------------------------------------

        Cotizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                separador.decimalEditText(EditCantidad, EditHojas, EditInsertos, EditArgollada, EditArmada);
                final int cantidad;
                final int[] hojas2 = {0};
                int plastificado, impresion, corte, guarda, hoja, carton, armada, argollada, hojas;
                hoja = impresion = plastificado = guarda = carton = hojas = 0;
                final int[] neto = {0};
                final int[] insertos = {0};
                final double largo, ancho;

                if (!EditCantidad.getText().toString().equals("") && !EditHojas.getText().toString().equals("")
                        && !EditArgollada.getText().toString().equals("") && !EditArmada.getText().toString().equals("")){
                    cantidad = separador.NumeroInt(EditCantidad);
                    hoja = separador.NumeroInt(EditHojas);
                    argollada = separador.NumeroInt(EditArgollada);
                    armada = separador.NumeroInt(EditArmada);
                    if (!EditLargo.getText().toString().equals("") && !EditAncho.getText().toString().equals("")){
                        largo = Double.parseDouble(EditLargo.getText().toString());
                        ancho = Double.parseDouble(EditAncho.getText().toString());

                        final int[] cabidad = {condicionales.cabidad(47, 32, (largo + 2), (ancho + 2))};
                        hojas = condicionales.hojas(cantidad*2, cabidad[0]);
                        impresion = condicionales.papel150(hojas);
                        plastificado = condicionales.plastificado(hojas);

                        if (!EditInsertos.getText().toString().equals("")){
                            insertos[0] = separador.NumeroInt(EditInsertos);

                            AlertDialog.Builder dialogo1 = new AlertDialog.Builder(context);
                            dialogo1.setIcon(R.mipmap.ic_launcher);
                            dialogo1.setTitle("Insertos");
                            dialogo1.setMessage("¿Insertos 4x0 o 4x4?");
                            dialogo1.setCancelable(false);

                            dialogo1.setPositiveButton("4x4", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialogo1, int id) {
                                    cabidad[0] = condicionales.cabidad(47, 32, largo, ancho);
                                    hojas2[0] = (insertos[0] * cantidad);
                                    hojas2[0] = condicionales.hojas(hojas2[0], cabidad[0]);
                                    insertos[0] = condicionales.papel200(hojas2[0] *2);
                                }
                            });
                            dialogo1.setNegativeButton("4x0", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogo1, int i) {
                                    cabidad[0] = condicionales.cabidad(47, 32, largo, ancho);
                                    hojas2[0] = (insertos[0] * cantidad);
                                    hojas2[0] = condicionales.hojas(hojas2[0], cabidad[0]);
                                    insertos[0] = condicionales.papel200(hojas2[0]);
                                }
                            });
                            dialogo1.show();
                        }//Fin insertos

                        argollada *= cantidad;
                        armada *= cantidad;

                        int cabidadCarton = condicionales.cabidad(100, 70, largo, ancho);
                        double carto = (condicionales.carton / cabidadCarton) * (cantidad * 2);
                        carton = (int) carto;

                        if (Guarda.getSelectedItem().toString().equals("G. BLANCA")){
                            guarda = condicionales.guardasblancas(cantidad, largo, ancho);
                        }else if (Guarda.getSelectedItem().toString().equals("G. IMPR.")){
                            guarda = condicionales.guardasimpresas(cantidad, largo, ancho);
                        }

                        Handler handler = new Handler();
                        final int finalImpresion = impresion;
                        final int finalHojas = hojas;
                        final int finalHoja = hoja;
                        final int finalPlastificado = plastificado;
                        final int finalArgollada = argollada;
                        final int finalGuarda = guarda;
                        final int finalCarton = carton;
                        final int finalArmada = armada;
                        handler.postDelayed(new Runnable() {

                            public void run() {
                                neto[0] += finalImpresion + finalHoja + finalPlastificado + insertos[0] + finalArgollada + finalArmada + finalCarton + finalGuarda;

                                AlertDialog.Builder dialogo1 = new AlertDialog.Builder(context);
                                dialogo1.setIcon(R.mipmap.ic_launcher);
                                dialogo1.setTitle("Cotización");
                                dialogo1.setMessage("Cantidad:             " + separador.transformador(cantidad) +
                                        "\nHojas:                   " + separador.transformador(finalHoja) +
                                        "\n\nPortada:               " + separador.transformador(finalImpresion) +
                                        "\nPlastificado:        " + separador.transformador(finalPlastificado) +
                                        "\n\nImpresión I:         " + separador.transformador(insertos[0]) +
                                        "\n\nArgollada:            " + separador.transformador(finalArgollada) +
                                        "\nArmada:               " + separador.transformador(finalArmada) +
                                        "\nCartón:                 " + separador.transformador(finalCarton) +
                                        "\nGuarda:                " + separador.transformador(finalGuarda) +
                                        "\n\nNeto:                     " + separador.transformador(neto[0]));
                                dialogo1.setCancelable(false);
                                dialogo1.setNegativeButton("Ok", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogo1, int i) {
                                        dialogo1.cancel();
                                    }
                                });
                                dialogo1.show();
                            }
                        }, 3500);

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
                }else if (EditHojas.getText().toString().equals("")){
                    EditHojas.setError("Faltan las hojas.");
                    EditHojas.requestFocus();
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    assert imm != null;
                    imm.showSoftInput(EditHojas, InputMethodManager.SHOW_IMPLICIT);
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

        label_cuadernos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                separador.decimalEditText(EditCantidad, EditHojas, EditInsertos, EditArgollada, EditArmada);
            }
        });

        label_cantidad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                separador.decimalEditText(EditCantidad, EditHojas, EditInsertos, EditArgollada, EditArmada);
            }
        });

        label_hojas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                separador.decimalEditText(EditCantidad, EditHojas, EditInsertos, EditArgollada, EditArmada);
            }
        });

        label_insertos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                separador.decimalEditText(EditCantidad, EditHojas, EditInsertos, EditArgollada, EditArmada);
            }
        });

        label_argollada.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                separador.decimalEditText(EditCantidad, EditHojas, EditInsertos, EditArgollada, EditArmada);
            }
        });

        label_armada.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                separador.decimalEditText(EditCantidad, EditHojas, EditInsertos, EditArgollada, EditArmada);
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

        Nota.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder dialogo1 = new AlertDialog.Builder(context);
                dialogo1.setIcon(R.mipmap.ic_launcher);
                dialogo1.setTitle("Nota");
                dialogo1.setMessage("Despues de precionar el botón 'Cotizar' la cotización tardara 3.5 segundos en mostrarse en pantalla.");
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
