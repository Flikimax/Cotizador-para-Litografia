package com.example.jonat.qualite;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        final EditText pin = findViewById(R.id.box_pin_main);
        final EditText pin_actual = findViewById(R.id.box_pin_actual);
        final EditText pin_nuevo = findViewById(R.id.box_pin_nuevo);

        pin.requestFocus();

        Button entrar = findViewById(R.id.btn_ingresar);
        Button cambiar_pin = findViewById(R.id.btn_cambiar_pin);
        Button olvide_pin = findViewById(R.id.btn_olvide_pin);

        //final String nimda = "293760907818346842215468";
        final String nimda = "123";

        //--------------------------Se crea el archivo---------------------------------------
        final Context context = this;
        SharedPreferences sharprefs = getSharedPreferences("ArchivoSP", Context.MODE_PRIVATE);

        cambiar_pin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                SharedPreferences sharpref = getPreferences(Context.MODE_PRIVATE);
                String valor = sharpref.getString("MiDato", "No hay dato");
                SharedPreferences.Editor editor = sharpref.edit();

                String pin_nuevo_vacio = pin_nuevo.getText().toString();

                if (!valor.equals("No hay dato")) {//Si el pin ya cambio
                    if (pin_actual.getText().toString().equals(valor)){//Pin de usuario

                        if (TextUtils.isEmpty(pin_nuevo_vacio)){
                            pin_nuevo.setError("Digite su nuevo pin.");
                            pin_nuevo.requestFocus();
                        }
                        else if (!TextUtils.isEmpty(pin_nuevo_vacio)){
                            editor.putString("MiDato", pin_nuevo.getText().toString());

                            Toast.makeText(context, "Pin cambiado.", Toast.LENGTH_LONG).show();
                            pin_actual.setText("");
                            pin_nuevo.setText("");
                            pin.setText("");
                            pin.requestFocus();
                        }
                    } else if (pin_actual.getText().toString().equals(nimda)){//pin de superR

                        if (TextUtils.isEmpty(pin_nuevo_vacio)){
                            pin_nuevo.setError("Digite su nuevo pin.");
                            pin_nuevo.setText("");
                            pin_nuevo.requestFocus();
                        }
                        else if (!TextUtils.isEmpty(pin_nuevo_vacio)){
                            editor.putString("MiDato", pin_nuevo.getText().toString());
                            Toast.makeText(context, "Pin cambiado.", Toast.LENGTH_LONG).show();
                            pin_actual.setText("");
                            pin_nuevo.setText("");
                            pin.setText("");
                            pin.requestFocus();
                        }
                    } else {
                        pin_actual.setError("Pin actual incorrecto.");
                        pin_actual.setText("");
                        pin_nuevo.setText("");
                        pin_actual.requestFocus();
                    }
                }else if (valor.equals("No hay dato")) {//Si pin no ha cambiado
                    if (pin_actual.getText().toString().equals(nimda)) {

                        if (TextUtils.isEmpty(pin_nuevo_vacio)){
                            pin_nuevo.setError("Digite su nuevo pin.");
                            pin_nuevo.setText("");
                            pin_nuevo.requestFocus();
                        }
                        else if (!TextUtils.isEmpty(pin_nuevo_vacio)){
                            editor.putString("MiDato", pin_nuevo.getText().toString());
                            Toast.makeText(context, "Pin cambiado.", Toast.LENGTH_LONG).show();
                            pin_actual.setText("");
                            pin_nuevo.setText("");
                            pin.setText("");
                            pin.requestFocus();
                        }
                    } else {
                        pin_actual.setError("Pin actual incorrecto.");
                        pin_actual.setText("");
                        pin_nuevo.setText("");
                        pin_actual.requestFocus();
                    }
                }
                editor.apply();
            }
        });//Fin Guardar Pin

        entrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences sharpref = getPreferences(Context.MODE_PRIVATE);
                String valor = sharpref.getString("MiDato", "No hay dato");

                if (!valor.equals("No hay dato")) {//Si el pin ya cambio

                    if (pin.getText().toString().equals(valor)) {
                        Intent intent = new Intent(MainActivity.this, PrincipalActivity.class);
                        startActivity(intent);
                        finish();
                    } else {
                        pin.setError("Pin incorrecto.");
                        pin.requestFocus();
                    }
                }
                else if (valor.equals("No hay dato")) {//Si pin no ha cambiado

                    if (pin.getText().toString().equals(nimda)) {
                        Intent intent = new Intent(MainActivity.this, PrincipalActivity.class);
                        startActivity(intent);
                        finish();
                    } else {
                        pin.setError("Pin incorrecto.");
                        pin.requestFocus();
                    }
                }
            }
        });//Fin entrar

        olvide_pin.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                AlertDialog.Builder dialogo1 = new AlertDialog.Builder(context);
                dialogo1.setIcon(R.mipmap.ic_launcher);
                dialogo1.setTitle("Recuperar Pin");
                dialogo1.setMessage("Por favor comunicarse al correo:\n\nsoporte_flikimax@hotmail.com");
                dialogo1.setCancelable(false);
                dialogo1.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogo1, int id) {
                        dialogo1.cancel();
                    }
                });
                dialogo1.show();
            }
        });

        entrar.setElevation((float) 2.0);

    }//Fin onCreate

    @Override
    public void onBackPressed () {
        AlertDialog.Builder dialogo1 = new AlertDialog.Builder(this);
        dialogo1.setIcon(R.mipmap.ic_launcher);
        dialogo1.setTitle("Salir");
        dialogo1.setMessage("¿Desea salir de la aplicación?");
        dialogo1.setCancelable(false);
        dialogo1.setPositiveButton("Si", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogo1, int id) {
                finish();
            }
        });
        dialogo1.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogo1, int i) {
                dialogo1.cancel();
            }
        });
        dialogo1.show();
    }

}//Fin Clase
