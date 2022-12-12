package sk.brano;

import OSPRNG.ExponentialRNG;
import OSPRNG.TriangularRNG;
import OSPRNG.UniformContinuousRNG;
import OSPRNG.UniformDiscreteRNG;

import java.util.Random;

import static java.lang.Math.*;

public class Main {
    public static void main(String[] args) {
        //monteCralo(100000);
        //syr(100);

        //pi();
        cestovatel();
        int replikacie = 100000;
        double spolu = 0;
        double spolu2 = 0;
        for (int i = 0; i < replikacie; i++) {
            Number[] n = cestovatel();
            spolu += n[0].intValue();
            spolu2 += n[1].doubleValue();
        }
        System.out.println("Den: " + spolu / replikacie + ", za: " + spolu2 / replikacie);
    }

    private static void monteCralo(int vsetky){
        Random rand = new Random();

        int pocet = 0;

        for (int i = 0; i < vsetky; i++){
            double x = rand.nextInt(Integer.MAX_VALUE) / (double)Integer.MAX_VALUE;
            double y = rand.nextInt(Integer.MAX_VALUE) / (double)Integer.MAX_VALUE;

            //System.out.println("Vygeneroval som x: " + x + " a y: " + y);

            if(x >= 0.3 && x <= 0.6 && y >= 0.2 && y <= 0.6){
                pocet++;
            }
        }
        System.out.println("V obdlzniku: " + pocet + " a vsetky: " + vsetky);
        System.out.println("Pomer: " + pocet / (double)vsetky);
    }

    private static double syr(int O){
        UniformContinuousRNG unif = new UniformContinuousRNG(0.4, 0.7);
        TriangularRNG trian = new TriangularRNG(70.0, 90.0 ,110.0);
        double Cn = unif.sample();//0.6
        double D = round(trian.sample());//80
        double P = min(D, O);
        double Cp = 0.89;
        double Z = O - P;
        double Cv = Cn / 2.0;
        //System.out.println("Cn: " + Cn + " D: " + D + " P: " + P + " Cp: " + Cp + " Z: " + Z + " Cv: " + Cv);

        double vysledok = (P * Cp) + (Z * Cv) - (O * Cn);
        //System.out.println("Vysledok: " + vysledok);

        return vysledok;
    }

    private static double kolporter(int pocetBalikov){
        TriangularRNG trian = new TriangularRNG(0.25, 0.6 ,0.95);
        UniformContinuousRNG unif = new UniformContinuousRNG(250.0, 420.0);
        ExponentialRNG expo = new ExponentialRNG(2.7);
        double Cn = 0.15;
        double Cp = trian.sample();
        double Cv = Cn * 0.65;
        int pocetNovin = pocetBalikov * 10;
        double dlzkaPredaja = unif.sample();
        while (pocetNovin > 0 && dlzkaPredaja > 0){
            double cas = expo.sample();
            if(dlzkaPredaja < cas)
                break;
            dlzkaPredaja -= cas;
            pocetNovin--;
        }
        double vysledok = ((pocetBalikov * 10 - pocetNovin) * Cp) + (pocetNovin * Cv) - (pocetBalikov * 10 * Cn);
        System.out.println("Zisk: " + vysledok);
        return vysledok;
    }

    private static double remeselnik(int typ){
        TriangularRNG trian;
        UniformDiscreteRNG unif;
        int max = 0;
        double naklady = 0;
        double dopyt = 0;
        double Cp = 0;
        switch(typ){
            case 0:
                max = 70;
                trian = new TriangularRNG(1.0, 1.75 ,2.5);
                naklady = trian.sample();
                unif = new UniformDiscreteRNG(40, 80);
                dopyt = unif.sample();
                Cp = 3;
                break;
            case 1:
                max = 90;
                trian = new TriangularRNG(0.7, 1.2 ,1.7);
                naklady = trian.sample();
                unif = new UniformDiscreteRNG(66, 155);
                dopyt = unif.sample();
                Cp = 2;
                break;
        }
        double vysledok = (dopyt * Cp) - (max * naklady);
        System.out.println("Zisk: " + vysledok);
        return vysledok;
    }

    private static double pi(){
        UniformContinuousRNG unif = new UniformContinuousRNG(0.0, 1.0);
        int vsetky = 1000000;
        int pocet = 0;
        for (int i = 0; i < vsetky; i++){
            double x = unif.sample();
            double y = unif.sample();
            if((x - 0.5)*(x - 0.5) + (y - 0.5)*(y - 0.5) <= 0.5*0.5){
                pocet++;
            }
        }
        double vysledok = ((double)pocet / vsetky) / (0.5*0.5);
        System.out.println("Pi: " + vysledok);
        return vysledok;
    }

    private static Number[] cestovatel(){
        TriangularRNG trian = new TriangularRNG(0.01, 0.04, 0.11);
        UniformContinuousRNG unif = new UniformContinuousRNG(0.05, 0.14);
        double cena = 500;
        double naplnenost = 0.27;
        int den = 0;
        double za = cena;
        for (int i = 0; i < 6; i++) {
            double x = trian.sample();
            naplnenost += unif.sample();
            if (naplnenost >= 0.75){
                cena *= 1.3;
                break;
            }
            cena *= (1-x);
            if(cena < za){
                den = i;
                za = cena;
            }
        }
        System.out.println("Den: " + den + ", za: " + za);
        Number[] r = new Number[2];
        r[0] = den;
        r[1] = za;
        return r;
    }
}