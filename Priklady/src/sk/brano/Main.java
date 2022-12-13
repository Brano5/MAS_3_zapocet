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

        //kolporter();
        //remeselnik();
        //pi();
        //namornik(3);
        vestice();
        //testovanie();
        //farmaceuticka();
        //zatva();
        //cestovatel();
    }

    private static void monteCralo(int vsetky) {
        Random rand = new Random();

        int pocet = 0;

        for (int i = 0; i < vsetky; i++) {
            double x = rand.nextInt(Integer.MAX_VALUE) / (double) Integer.MAX_VALUE;
            double y = rand.nextInt(Integer.MAX_VALUE) / (double) Integer.MAX_VALUE;

            //System.out.println("Vygeneroval som x: " + x + " a y: " + y);

            if (x >= 0.3 && x <= 0.6 && y >= 0.2 && y <= 0.6) {
                pocet++;
            }
        }
        System.out.println("V obdlzniku: " + pocet + " a vsetky: " + vsetky);
        System.out.println("Pomer: " + pocet / (double) vsetky);
    }

    private static double syr(int O) {
        UniformContinuousRNG unif = new UniformContinuousRNG(0.4, 0.7);
        TriangularRNG trian = new TriangularRNG(70.0, 90.0, 110.0);
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

    private static void kolporter() {
        TriangularRNG trian = new TriangularRNG(0.25, 0.6, 0.95);
        UniformContinuousRNG unif = new UniformContinuousRNG(250.0, 420.0);
        ExponentialRNG expo = new ExponentialRNG(2.7);
        double maxZisk = 0;
        int maxPocet = 0;
        int replikacie = 100000;
        for (int i = 0; i < 20; i++) {
            double spolu = 0;
            for (int j = 0; j < replikacie; j++) {
                double Cn = 0.15;
                double Cp = trian.sample();
                double Cv = Cn * 0.65;
                int pocetNovin = i * 10;
                double dlzkaPredaja = unif.sample();
                while (pocetNovin > 0 && dlzkaPredaja > 0) {
                    double cas = expo.sample();
                    if (dlzkaPredaja < cas)
                        break;
                    dlzkaPredaja -= cas;
                    pocetNovin--;
                }
                double vysledok = ((i * 10 - pocetNovin) * Cp) + (pocetNovin * Cv) - (i * 10 * Cn);
                spolu += vysledok;
                //System.out.println("Zisk: " + vysledok);
            }
            if (maxZisk < spolu / replikacie) {
                maxZisk = spolu / replikacie;
                maxPocet = i;
            }
        }
        System.out.println("Pocet: " + maxPocet + ", zisk: " + maxZisk);
    }

    private static void remeselnik() {
        TriangularRNG trianA = new TriangularRNG(1.0, 1.75, 2.5);
        TriangularRNG trianB = new TriangularRNG(0.7, 1.2, 1.7);
        UniformDiscreteRNG unifA = new UniformDiscreteRNG(40, 79);
        UniformDiscreteRNG unifB = new UniformDiscreteRNG(66, 154);
        int replikacie = 1000000;
        double ziskA = 0;
        double ziskB = 0;
        for (int i = 0; i < replikacie; i++) {
            int maxA = 70;
            double nakladyA = trianA.sample();
            double dopytA = unifA.sample();
            if(dopytA > maxA){
                dopytA = maxA;
            }
            double CpA = 3;

            int maxB = 90;
            double nakladyB = trianB.sample();
            double dopytB = unifB.sample();
            if(dopytB > maxB){
                dopytB = maxB;
            }
            double CpB = 2;

            ziskA += (dopytA * CpA) - (maxA * nakladyA);
            ziskB += (dopytB * CpB) - (maxB * nakladyB);
        }

        System.out.println("Zisk A: " + ziskA / replikacie + ", zisk B: " + ziskB / replikacie);
}

    private static double pi() {
        UniformContinuousRNG unif = new UniformContinuousRNG(0.0, 1.0);
        int vsetky = 10000000;
        int pocet = 0;
        for (int i = 0; i < vsetky; i++) {
            double x = unif.sample();
            double y = unif.sample();
            if ((x - 0.5) * (x - 0.5) + (y - 0.5) * (y - 0.5) <= 0.5 * 0.5) {
                pocet++;
            }
            if(String.format("%.6g", ((double) pocet / i) / (0.5 * 0.5)).equals("3.141592")){
                vsetky = i;
                break;
            }
        }
        double vysledok = ((double) pocet / vsetky) / (0.5 * 0.5);
        System.out.println("Pi: " + vysledok + ", pokusiv: " + vsetky);
        return vysledok;
    }

    private static void namornik(int smery){
        UniformDiscreteRNG unif = new UniformDiscreteRNG(0, smery * 2 - 1);
        int replikacie = 100000;
        int spolu = 0;
        for (int i = 0; i < replikacie; i++) {
            int[] nach = new int[smery];
            for (int j = 0; j < 1000; j++) {
                switch (unif.sample()){
                    case 0:
                        nach[0]++;
                        break;
                    case 1:
                        nach[0]--;
                        break;
                    case 2:
                        nach[1]++;
                        break;
                    case 3:
                        nach[1]--;
                        break;
                    case 4:
                        nach[2]++;
                        break;
                    case 5:
                        nach[2]--;
                        break;
                }
            }
            for (int j = 0; j < smery; j++) {
                spolu += abs(nach[j]);
            }
        }
        System.out.println("Vzdialenost: " + (double)spolu / replikacie);
    }

    private static void vestice(){
        UniformContinuousRNG uni = new UniformContinuousRNG(0.0, 100.0);
        int replikacie = 100000;
        int spoluPravda = 0;
        int spoluLoz = 0;
        for (int i = 0; i < replikacie; i++) {
            boolean prvaPravda = uni.sample() <= 80;
            boolean druhaPravda = uni.sample() <= 80;
            if(prvaPravda && druhaPravda){
                spoluPravda++;
            } else if(!prvaPravda && !druhaPravda){
                spoluLoz++;
            }
        }
        System.out.println("Pravda: " + (double)spoluPravda / (spoluPravda + spoluLoz));
    }

    private static void testovanie(){
        TriangularRNG trianFast = new TriangularRNG(40.0, 50.0, 75.0);
        TriangularRNG trianFurious = new TriangularRNG(35.0, 52.0, 80.0);
        int replikacie = 100000;
        int spolu = 0;
        for (int i = 0; i < replikacie; i++) {
            double[] auta = new double[10];
            double[] vysledky = new double[10];
            for (int j = 0; j < 5; j++) {
                auta[j] = trianFast.sample();
                auta[j+5] = trianFurious.sample();
            }
            double min = auta[0];
            int index = 0;
            for (int j = 10; j > 0; j--) {
                for (int k = 0; k < 10; k++) {
                    if (auta[k] < min){
                        min = auta[k];
                        index = k;
                    }
                }
                vysledky[index] = j;
                auta[index] = 99999;
                min = 99999;
            }
            int sedi = 0;
            for (int j = 0; j < 5; j++) {
                if(vysledky[j] >= 9){
                    sedi++;
                }
            }
            if (sedi >= 2){
                spolu++;
            }
        }
        System.out.println("Fast na prvych 2: " + (double)spolu / replikacie);
    }

    private static void farmaceuticka(){
        TriangularRNG trian = new TriangularRNG(1000.0, 4000.0, 8500.0);
        double min = 9999999;
        int minI = 0;
        for (int i = 5500; i < 5700; i++) {
            double spolu = 0;
            int replikacie = 8000000;
            for (int j = 0; j < replikacie; j++) {
                double pocet = trian.sample();
                double strata = 0;
                if (pocet < i){
                    strata = (i - pocet) * 50;
                } else if (pocet > i){
                    strata = (pocet - i) * 150;
                }
                spolu += strata;
            }
            if (spolu / replikacie < min){
                min = spolu / replikacie;
                minI = i;
            }
        }
        System.out.println("Min: " + min + ", i: " + minI);
    }

    private static void zatva(){
        TriangularRNG trian = new TriangularRNG(1.0, 3.0, 3.5);
        for (int i = 0; i < 100; i++) {
            int replikacie = 1000;
            int neuspech = 0;
            for (int j = 0; j < replikacie; j++) {
                double trava = 0;
                for (int k = 0; k < 20 * i; k++) {
                    trava += trian.sample();
                }
                if (trava < 300)
                    neuspech++;
            }
            System.out.println("Pocet: " + i + ", sanca: " + (double)neuspech / replikacie);
            if((double)neuspech / replikacie < 0.1)
                break;
        }
    }

    private static void cestovatel() {
        TriangularRNG trian = new TriangularRNG(0.01, 0.04, 0.11);
        UniformContinuousRNG unif = new UniformContinuousRNG(0.05, 0.14);
        int replikacie = 100000;
        double spolu = 0;
        double spolu2 = 0;
        for (int i = 0; i < replikacie; i++) {
            double cena = 500;
            double naplnenost = 0.27;
            int den = 0;
            double za = cena;
            for (int j = 0; j < 6; j++) {
                double x = trian.sample();
                naplnenost += unif.sample();
                if (naplnenost >= 0.75) {
                    cena *= 1.3;
                    break;
                }
                cena *= (1 - x);
                if (cena < za) {
                    den = j;
                    za = cena;
                }
            }
            System.out.println("Den: " + den + ", za: " + za);
            spolu += den;
            spolu2 += za;
        }
        System.out.println("Den: " + spolu / replikacie + ", za: " + spolu2 / replikacie);
    }
}