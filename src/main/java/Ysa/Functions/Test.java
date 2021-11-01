package Ysa.Functions;

import java.util.ArrayList;
import Ysa.DataTransfer;
import Ysa.Functions.Train;
import Ysa.Functions.Normalization;
import Ysa.DrawGraphic.Draw;

public class Test {
	Normalization norm = new Normalization();
    public void testEt(ArrayList<Integer> testSatirlari,
                         ArrayList<Double>[] normalizeVeri,
                         ArrayList<Double> girisBiaslari,
                         Double cikisBiasi,
                         ArrayList<Double> sinirHucreleri,
                         ArrayList<Double>[] hamVeri,
                         ArrayList<Double>[] girisAgirliklari,
                         ArrayList<Double> cikisAgirliklari) {
    	System.out.println("VERISETINDE EGITIM BITTI .");
        System.out.println("BULUNAN SON AGIRLIKLARI TEST VERISIYLE TEST EDILIYOR");
        int epoch = 1;
        Double gercekCikis = 0.0;
        Train egitim = new Train();
        ArrayList<Double> girisler = new ArrayList<Double>();
        Double hesaplananCikis;
        Double mse;
        Double mape;
        Double ortalamaKareHata = 0.0;
        Double ortalamaMapeHata = 0.0;
        ArrayList<Double> ortalamaKareHatalar = new ArrayList<Double>();
        ArrayList<Double> ortalamaMapeHatalar = new ArrayList<Double>();
        ArrayList<Double> testMseler = new ArrayList<Double>();
        ArrayList<Integer> iterasyonlar = new ArrayList<Integer>();
        int iterasyon = 0;
        while (epoch < 101 && sinirHucreleri.size() > 1 && sinirHucreleri.size() < 21) {
            for (Integer satirNo: testSatirlari
            ) {
                girisler.clear();
                for (int x=0; x<normalizeVeri.length -1; x++){
                    girisler.add(normalizeVeri[x].get(satirNo));
                    if (x == normalizeVeri.length - 2) {
                        gercekCikis = normalizeVeri[x + 1].get(satirNo);
                    }
                }

                for (int x=0; x<sinirHucreleri.size(); x++) {
                    sinirHucreleri.set(x, egitim.aktivasyon(girisler, girisBiaslari.get(x), girisAgirliklari[x]));
                }
                hesaplananCikis = egitim.aktivasyon(sinirHucreleri, cikisBiasi, cikisAgirliklari);
                mse = egitim.kareHata(hesaplananCikis, gercekCikis);

                Double deNormalizeHesaplanan;
                Double deNormalizeCikis;
                deNormalizeHesaplanan = norm.denormalizeVeri(hamVeri[8], hesaplananCikis);
                deNormalizeCikis= norm.denormalizeVeri(hamVeri[8], gercekCikis);    
                mape= egitim.mapeHata(deNormalizeHesaplanan, deNormalizeCikis);
                ortalamaMapeHatalar.add(mape);
                ortalamaKareHatalar.add(mse);
                ortalamaKareHata = egitim.ortalamaKareHata(ortalamaKareHatalar);
                ortalamaMapeHata = egitim.ortalamaMapeHata(ortalamaMapeHatalar);
                iterasyon++;
                if (iterasyon % 50 == 0) {

                    testMseler.add(ortalamaKareHata);
                    iterasyonlar.add((int)(iterasyon / 50));
                }

            }

            System.out.print(epoch + " \t");
            System.out.print("mse -> " + ortalamaKareHata + " \t");
            System.out.println("mape -> " + ortalamaMapeHata);
            epoch++;
        }
        DataTransfer dataTransfer = new DataTransfer();
        dataTransfer.setMseler(testMseler);
        dataTransfer.setIterasyonlar(iterasyonlar);
        Draw grafikCiz = new Draw("ysaProjesi", "Y2 Ýçin MSE - Iterasyon Grafiði");
        grafikCiz.graf("Test icin (");
    }
   
}
