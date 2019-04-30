package com.example.jonat.qualite;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class PrincipalActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        final Context context = this;

        Button Afiches = findViewById(R.id.afiches);
        Button Calendarios = findViewById(R.id.calendarios);
        Button Carpetas = findViewById(R.id.carpetas);
        Button Cuadernos = findViewById(R.id.cuadernos);
        Button Diplomas = findViewById(R.id.diplomas);
        Button Menu = findViewById(R.id.menu);
        Button Libros = findViewById(R.id.libros);
        Button Revistas = findViewById(R.id.revistas);
        Button Salir = findViewById(R.id.cerrar);
        Button Señaleticas = findViewById(R.id.señaleticas);
        Button Stickers = findViewById(R.id.stickers);
        Button Tarjetas = findViewById(R.id.tarjetas);
        Button Volantes = findViewById(R.id.volantes);

        Afiches.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PrincipalActivity.this, AfichesActivity.class);
                startActivity(intent);
            }
        });

        Carpetas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PrincipalActivity.this, CarpetasActivity.class);
                startActivity(intent);
            }
        });

        Cuadernos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PrincipalActivity.this, CuadernosActivity.class);
                startActivity(intent);
            }
        });

        Diplomas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PrincipalActivity.this, DiplomasActivity.class);
                startActivity(intent);
            }
        });

        Menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PrincipalActivity.this, CartaMenuActivity.class);
                startActivity(intent);
            }
        });

        Libros.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PrincipalActivity.this, LibrosActivity.class);
                startActivity(intent);
            }
        });

        Revistas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PrincipalActivity.this, RevistasActivity.class);
                startActivity(intent);
            }
        });

        Salir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder dialogo1 = new AlertDialog.Builder(context);
                dialogo1.setIcon(R.mipmap.ic_launcher);
                dialogo1.setTitle("Salir");
                dialogo1.setMessage("¿Desea salir de la aplicación?");
                dialogo1.setCancelable(false);
                dialogo1.setPositiveButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogo1, int id) {
                        dialogo1.cancel();
                    }
                });
                dialogo1.setNegativeButton("Si", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogo1, int i) {
                        finish();
                    }
                });
                dialogo1.show();
            }
        });

        Señaleticas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PrincipalActivity.this, SenaleticasActivity.class);
                startActivity(intent);
            }
        });

        Stickers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PrincipalActivity.this, StickersActivity.class);
                startActivity(intent);
            }
        });

        Tarjetas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PrincipalActivity.this, TarjetasActivity.class);
                startActivity(intent);
            }
        });

        Volantes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent inten = new Intent(PrincipalActivity.this, VolantesActivity.class);
                startActivity(inten);
            }
        });

    }//Fin onCreate

    @Override
    public void onBackPressed () {
        AlertDialog.Builder dialogo1 = new AlertDialog.Builder(this);
        dialogo1.setIcon(R.mipmap.ic_launcher);
        dialogo1.setTitle("Salir");
        dialogo1.setMessage("¿Desea salir de la aplicación?");
        dialogo1.setCancelable(false);
        dialogo1.setPositiveButton("No", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogo1, int id) {
                dialogo1.cancel();
            }
        });
        dialogo1.setNegativeButton("Si", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogo1, int i) {
                finish();
            }
        });
        dialogo1.show();
    }

}//Fin Clase
