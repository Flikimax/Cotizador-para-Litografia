package com.example.jonat.qualite;

public class Papeles {

    int papel;
    public int carton = 6000;
    public int calibre30 = 28500;
    public int calibre40 = 35000;
    public int calibre60 = 50000;

     int cabidad (double x, double y, double largo, double ancho){
        double cab1, cab2;
        int cabidad = 0;
        cab1 = ((Math.floor(x/largo)) * (Math.floor(y/ancho)));
        cab2 = ((Math.floor(y/largo)) * (Math.floor(x/ancho)));
        if (cab1 >=  cab2){
            cabidad = (int) cab1;
        }else if (cab2 >= cab1){
            cabidad = (int) cab2;
        }
        return cabidad;
    }

     int hojas (int hojas, int cabidad){
        int hojas_neto;
        double aux = (double) hojas / (double) cabidad;
        if ((aux % 1) != 0){
            aux = Math.floor(aux);
            aux += 1;
        }
        hojas_neto = (int) aux;
        return hojas_neto;
    }

    public int plastificado(int hojas){
        int plastificado = 0;
        if  ((hojas >= 1) && (hojas <= 6)){
            plastificado = 2000 * hojas;
        }else if ((hojas >= 7) && (hojas <= 15)){
            plastificado = 1700 * hojas;
        }else if ((hojas >= 16) && (hojas <= 35)){
            plastificado = 1350 * hojas;
        }else if ((hojas >= 36) && (hojas <= 60)){
            plastificado = 1250 * hojas;
        }else if ((hojas >= 61) && (hojas <= 90)){
            plastificado = 950 * hojas;
        }else if ((hojas >= 91) && (hojas <= 150)){
            plastificado = 850 * hojas;
        }else if((hojas > 150)){
            plastificado = 600 * hojas;
        }
        return plastificado;
    }

    public int plastificadobolsiilo(int bolsillo, int cantidad){
        int plastificado;
        if  ((cantidad >= 1) && (cantidad <= 6)){
            bolsillo *= 2000;
        }else if ((cantidad >= 7) && (cantidad <= 15)){
            bolsillo *= 1700;
        }else if ((cantidad >= 16) && (cantidad <= 35)){
            bolsillo *= 1350;
        }else if ((cantidad >= 36) && (cantidad <= 60)){
            bolsillo *= 1250;
        }else if ((cantidad >= 61) && (cantidad <= 90)){
            bolsillo *= 950;
        }else if ((cantidad >= 91) && (cantidad <= 150)){
            bolsillo *= 850;
        }else if((cantidad > 150)){
            bolsillo *= 600;
        }
        plastificado = bolsillo;
        return plastificado;
    }

    public int  papel115 (int hojas){
        papel = 0;
        if  ((hojas >= 1) && (hojas <= 10)){
            papel = 2900 * hojas;
        }else if ((hojas >= 11) && (hojas <= 20)){
            papel = 2500 * hojas;
        }else if ((hojas >= 21) && (hojas <= 50)){
            papel = 2200 * hojas;
        }else if ((hojas >= 51) && (hojas <= 100)){
            papel = 1950 * hojas;
        }else if ((hojas > 100)){
            papel = 1700 * hojas;
        }
        return papel;
    }

    public int papelespecial (int hojas, int aumento){
        papel = 0;
        if  ((hojas >= 1) && (hojas <= 10)){
            papel = (4000 + aumento) * hojas;
        }else if ((hojas >= 11) && (hojas <= 20)){
            papel = (3500 + aumento) * hojas;
        }else if ((hojas >= 21) && (hojas <= 50)){
            papel = (3000 + aumento) * hojas;
        }else if ((hojas >= 51) && (hojas <= 100)){
            papel = (2600 + aumento) * hojas;
        }else if ((hojas > 100)){
            papel = (2300 + aumento) * hojas;
        }
        return papel;
    }

    public int papel150 (int hojas){
        papel = 0;
        if  ((hojas >= 1) && (hojas <= 10)){
            papel = 3500 * hojas;
        }else if ((hojas >= 11) && (hojas <= 20)){
            papel = 3100 * hojas;
        }else if ((hojas >= 21) && (hojas <= 50)){
            papel = 2800 * hojas;
        }else if ((hojas >= 51) && (hojas <= 100)){
            papel = 2300 * hojas;
        }else if ((hojas > 100)){
            papel = 1900 * hojas;
        }
        return papel;
    }

     int papel200 (int hojas){
        papel = 0;
        if  ((hojas >= 1) && (hojas <= 10)){
            papel = 3700 * hojas;
        }else if ((hojas >= 11) && (hojas <= 20)){
            papel = 3300 * hojas;
        }else if ((hojas >= 21) && (hojas <= 50)){
            papel = 2800 * hojas;
        }else if ((hojas >= 51) && (hojas <= 100)){
            papel = 2400 * hojas;
        }else if ((hojas > 100)){
            papel = 2000 * hojas;
        }
        return papel;
    }

    public int papel200guarda (int evaluar, int multi){
        papel = 0;
        if  ((evaluar >= 1) && (evaluar <= 10)){
            papel = 3700 * multi;
        }else if ((evaluar >= 11) && (evaluar <= 20)){
            papel = 3300 * multi;
        }else if ((evaluar >= 21) && (evaluar <= 50)){
            papel = 2800 * multi;
        }else if ((evaluar >= 51) && (evaluar <= 100)){
            papel = 2400 * multi;
        }else if ((evaluar > 100)){
            papel = 2000 * multi;
        }
        return papel;
    }

    public int papel240 (int hojas){
        papel = 0;
        if  ((hojas >= 1) && (hojas <= 10)){
            papel = 4000 * hojas;
        }else if ((hojas >= 11) && (hojas <= 20)){
            papel = 3500 * hojas;
        }else if ((hojas >= 21) && (hojas <= 50)){
            papel = 3000 * hojas;
        }else if ((hojas >= 51) && (hojas <= 100)){
            papel = 2600 * hojas;
        }else if ((hojas > 100)){
            papel = 2300 * hojas;
        }
        return papel;
    }

    public int papel300 (int hojas){
        papel = 0;
        if  ((hojas >= 1) && (hojas <= 10)){
            papel = 4000 * hojas;
        }else if ((hojas >= 11) && (hojas <= 20)){
            papel = 3500 * hojas;
        }else if ((hojas >= 21) && (hojas <= 50)){
            papel = 3000 * hojas;
        }else if ((hojas >= 51) && (hojas <= 100)){
            papel = 2600 * hojas;
        }else if ((hojas > 100)){
            papel = 2300 * hojas;
        }
        return papel;
    }

    public int papelbolsi (int bolsillo, int cantidad){
        papel = 0;
        if  ((cantidad >= 1) && (cantidad <= 10)){
            bolsillo *= 4000;
        }else if ((cantidad >= 11) && (cantidad <= 20)){
            bolsillo *= 3500;
        }else if ((cantidad >= 21) && (cantidad <= 50)){
            bolsillo *= 3000;
        }else if ((cantidad >= 51) && (cantidad <= 100)){
            bolsillo *= 2600;
        }else if ((cantidad > 100)){
            bolsillo *= 2300;
        }
        papel = bolsillo;
        return papel;
    }

    public int papelpergamino (int hojas){
        papel = 0;
        if  ((hojas >= 1) && (hojas <= 10)){
            papel = 4000 * hojas;
        }else if ((hojas >= 11) && (hojas <= 20)){
            papel = 3700 * hojas;
        }else if ((hojas >= 21) && (hojas <= 50)){
            papel = 3500 * hojas;
        }else if ((hojas >= 51) && (hojas <= 100)){
            papel = 3000 * hojas;
        }else if ((hojas > 100)){
            papel = 2800 * hojas;
        }
        return papel;
    }

    public int papeladhesivo (int hojas){
        papel = 0;
        if  ((hojas >= 1) && (hojas <= 10)){
            papel = 4500 * hojas;
        }else if ((hojas >= 11) && (hojas <= 20)){
            papel = 4000 * hojas;
        }else if ((hojas >= 21) && (hojas <= 50)){
            papel = 3500 * hojas;
        }else if ((hojas >= 51) && (hojas <= 100)){
            papel = 3000 * hojas;
        }else if ((hojas > 100)){
            papel = 2800 * hojas;
        }
        return papel;
    }

    public int troquelada (int hojas){
        papel = 0;
        if  ((hojas >= 1) && (hojas <= 1000)){
            papel = 30000;
        }else if ((hojas >= 1001) && (hojas <= 2000)){
            papel = 60000;
        }else if ((hojas >= 2001) && (hojas <= 3000)){
            papel = 60000;
        }
        return papel;
    }

    public int paginas (int cantidad, int paginas, int cabidad, int multiplicador){
        double hojas = (paginas / (cabidad * multiplicador));
        while ((hojas % 1) != 0){
            paginas += 1;
            hojas = (paginas / (cabidad * multiplicador));
        }
        hojas += 1;
        return (int) hojas;
    }

    public int guardasblancas (int cantidad, double largo, double ancho){
        int cabidad = cabidad(100, 70, largo, ancho);
        int guardas = ((2000 / cabidad) * (cantidad * 2));
        return guardas;
    }

    public int guardasimpresas (int cantidad, double largo, double ancho){
        int cabidad = cabidad(47, 32, largo, ancho);
        int hojas = hojas(cantidad*2, cabidad);
        int guardas = papel200(hojas);
        return guardas;
    }

    public int variable  (int neto){
        int variable = ((neto /100) * 20);
        return variable;
    }

    public int masHojas (int cantidad){
        if((cantidad >= 1) && (cantidad <= 10)){
            cantidad += 1;
        }else if ((cantidad >= 11) && (cantidad <= 20)){
            cantidad += 2;
        }else if ((cantidad >= 21) && (cantidad <= 50)){
            cantidad += 3;
        }else if ((cantidad >= 51) && (cantidad <= 100)){
            cantidad += 4;
        }else if ((cantidad > 100)){
            cantidad += 5;
        }
        return cantidad;
    }

    public boolean validacion(int x, int y, double largo, double ancho){
        return (((ancho <= x) && largo <= y)) || ((ancho <= y) && (largo <= x));
    }

    public int poli(int calibre, int cabidad){
        double auxCal;
        int calibreb;

        auxCal = calibreb = calibre;
        calibreb /= cabidad;
        auxCal /= cabidad;
        auxCal /= 100;
        while ((auxCal % 1) != 0){
            calibreb += 1;
            auxCal = calibreb;
            auxCal /= 100;
        }
        return (int) calibreb;
    }


}
