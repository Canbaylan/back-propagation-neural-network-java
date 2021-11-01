package Ysa.Functions;
import java.util.ArrayList;
import java.util.Random;

public class Train {

    public Double getRandom01() {
        Double s;
        Random r = new Random();
        s = r.nextDouble();
        return s;
    }

    public Double sigmoid(Double x) {
        return 1 / (1 + Math.exp(-1 * x));
    }

    public Double sigmoidTurev(Double x) {
        return x * (1 - x);
    }

    public Double aktivasyon(ArrayList<Double> girisler, Double bias, ArrayList<Double> agirliklar) {
        Double toplam = 0.0;
        for (int x=0;x<girisler.size();x++) {
            toplam = toplam + girisler.get(x) * agirliklar.get(x);
        }
        toplam = toplam + bias;
        return toplam;
    }
    public Double cikisicinakt( ArrayList<Double> sinir, Double bias,ArrayList<ArrayList<Double>> agirliklar) {
    	 Double toplam = 0.0;
    	 for(int y=0;y<sinir.size();y++) {
         for (int x=0;x<agirliklar.size();x++) {		 
        	 toplam = toplam + sinir.get(y) * agirliklar.get(x).get(y);
         	}
         }
         toplam = toplam + bias;
         return toplam;
    }
    
    public Double netHata(Double hesaplanan, Double gercek) {
        return gercek - hesaplanan;
    }

    public Double dagitilacakHataCikis(Double cikis, Double hata) {
        return cikis * (1 - cikis) * hata;
    }

    public Double dagitilacakHataGiris(Double cikisHatasi,Double agirlikMiktari, Double noronCiktisi) {
    	Double toplam=0.0;
    	toplam=noronCiktisi * (1 - noronCiktisi) * agirlikMiktari * cikisHatasi;
        return toplam;
    }

    public Double kareHata(Double hesaplanan, Double gercek) {
        return Math.pow((gercek - hesaplanan),2);
    }

    public Double mapeHata(Double hesaplanan, Double gercek) {
        if (gercek != 0.0) {
            return Math.abs(gercek - hesaplanan) / gercek; }
        else {
            return gercek;
        }
    }

    public Double ortalamaKareHata(ArrayList<Double> hatalar) {
        double sum = 0;
        for(Double d : hatalar)
            sum += d;
        return sum / hatalar.size();
    }

    public Double ortalamaMapeHata(ArrayList<Double> hatalar) {
        double sum = 0;
        for(Double d : hatalar)
            sum += d;
        return 100 * (sum / hatalar.size());
    }

    public Double aktivasyontest(ArrayList<Double> sinirHucreleri, Double cikisBiasi,
			ArrayList<Double> cikisAgirliklari) {
		 Double toplam = 0.0;
	        for (int x=0;x<sinirHucreleri.size();x++) {
	            toplam = toplam + sinirHucreleri.get(x) * cikisAgirliklari.get(x);
	        }
	        toplam = toplam + cikisBiasi;
	        return toplam;
	}
    public Double agirlikGuncellemeGizliKatmandanCikisa(Double momentumKatsayisi, Double ogrenmeKatsayisi, Double dagitilacakHata, Double noronCiktisi, Double birOncekiDegisim) {
        Double degisimMiktari = ogrenmeKatsayisi * dagitilacakHata * noronCiktisi + momentumKatsayisi*birOncekiDegisim;
        return degisimMiktari;
    }

    public Double agirlikGuncellemeGizliKatmandanGirise(Double momentumKatsayisi, Double ogrenmeKatsayisi, Double dagitilacakHata, Double noronCiktisi, Double birOncekiDegisim) {
        Double degisimMiktari = ogrenmeKatsayisi*dagitilacakHata*noronCiktisi + momentumKatsayisi*birOncekiDegisim;
        return degisimMiktari;
    }
}
