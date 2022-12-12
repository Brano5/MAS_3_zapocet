package sk.brano;

import OSPRNG.ExponentialRNG;
import OSPRNG.TriangularRNG;
import OSPRNG.UniformContinuousRNG;
import OSPRNG.UniformDiscreteRNG;

import java.util.Random;

import static java.lang.Math.min;
import static java.lang.Math.round;

public class Main {
    public static void main(String[] args) {
        //monteCralo(100000);
        //syr(100);

        int replikacie = 1000000;
        double spolu = 0;
        for (int i = 0; i < replikacie; i++) {
            spolu += kolporter(15);
        }
        System.out.println("Priemerny zisk: " + spolu / replikacie);
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
}