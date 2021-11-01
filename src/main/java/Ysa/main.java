package Ysa;
import java.util.Scanner;

import Ysa.ReadFromExcel.ReadExcel;
import Ysa.DataTransfer;
import Ysa.Functions.Train;
import Ysa.ChooseLinesTrain.TrainAndTest;
import Ysa.Functions.Normalization;
import Ysa.Functions.NeuronBuilder;
import Ysa.Functions.Test;
import Ysa.DrawGraphic.Draw;


import java.util.ArrayList;
import java.util.Random;

public class main {
	public static void main(String[] args) {
		 ReadExcel exc = new ReadExcel(); //Excelden veri okumak için 
         Normalization norm = new Normalization(); //deðerlerde normalizasyon yapabilmek için
         TrainAndTest satýrSec = new TrainAndTest();  //egitimde kullanýlmak üzere excelde satýrlarý seciyorum
         Train egitim = new Train();
         NeuronBuilder sinirOlustur = new NeuronBuilder();
         ArrayList<Double>[] hamVeri = new ArrayList[9]; //exceldeki verileri tuttugum list
         ArrayList<Double>[] normalizeVeri = new ArrayList[9]; //excel verilerini normalizasyondan sonra tuttugum list
         Double deNormalizeHesaplanan;
         Double deNormalizeCikis;
       
         for (int a = 0; a<9; a++) {		//9 sütundan olusan verilerimi deðiþkenlere aktarýyorum
             hamVeri[a] = exc.sutun(a);
             normalizeVeri[a] = norm.normalizeVeri(hamVeri[a]); 
          }
         ArrayList<Integer> egitimSatirlari = satýrSec.chooseTrainLines(hamVeri[0].size()); 	//537 adet egitim satir numaram var.(%70)
         ArrayList<Integer> testSatirlari = satýrSec.chooseTestLines(egitimSatirlari, hamVeri[0].size());

         Scanner sinirsayisi = new Scanner(System.in);
         System.out.print("Arakatmanda kaç adet sinir hucresi olusturulsun => ");
         int noronsayisi = sinirsayisi.nextInt();
         ArrayList<Double> sinirHucreleri = sinirOlustur.hucreOlustur(noronsayisi); 
         Double ogrenmeKatsayisi = 0.01;
         Double momentumKatsayisi = 0.02;
         int maxEpoch = 101;
         int epoch = 1;
         int iterasyon = 0;
         ArrayList<Double> girisler = new ArrayList<Double>();
         Double normalizeCikis = 0.0;
         Double hesaplananCikis;
         Double mse;
         Double mape;
         Double sinirHucresicikis; 
         Double netHata = 0.0;
         Double degisim;
         Double dagitilacakCikisHatasi = 0.0;
         ArrayList<Double> dagitilacakGirisHatalari = new ArrayList<Double>();
         //dagitilacak giris hatalarým -> sinir hucresi sayýsý * girisler kadar
         Double ortalamaKareHata = 0.0;
         Double ortalamaMapeHata = 0.0;
         ArrayList<Double> ortalamaKareHatalar = new ArrayList<Double>();
         ArrayList<Double> ortalamaMapeHatalar = new ArrayList<Double>();
         ArrayList<Double> girisBiaslari = new ArrayList<Double>();
         ArrayList<Double>[] girisAgirliklari = new ArrayList[noronsayisi];
         ArrayList<Double>[] girisDegisimleri = new ArrayList[noronsayisi];
         Double gercekCikis;

         ArrayList<Double> mseler = new ArrayList<Double>();
         ArrayList<Integer> iterasyonlar = new ArrayList<Integer>();

         for (int i = 0; i < girisAgirliklari.length; i++) {
             girisAgirliklari[i] = new ArrayList<Double>();
             girisDegisimleri[i] = new ArrayList<Double>();
         }
         //girisagirliklarimin her bir elemaninda arraylist new liyorum ve 2 boyutlu olmus oluyor
         ArrayList<Double> cikisAgirliklari = new ArrayList<Double>();
         ArrayList<Double> cikisDegisimleri = new ArrayList<Double>();
         Double cikisBiasi = egitim.getRandom01(); 

         for (int x=0; x<sinirHucreleri.size(); x++) {
             cikisAgirliklari.add(egitim.getRandom01());
             cikisDegisimleri.add(egitim.getRandom01());
             dagitilacakGirisHatalari.add(egitim.getRandom01());
             girisBiaslari.add(egitim.getRandom01());
         }

         for (int y = 0; y<sinirHucreleri.size(); y++) {
             for (int x = 0; x < normalizeVeri.length - 1; x++) {
             
                 girisAgirliklari[y].add(egitim.getRandom01());
                 girisDegisimleri[y].add(egitim.getRandom01());
             }
         }
         //degiskenlerime baslangicta random degerler atiyorum.
         //girisagirliklarim 2 boyutlu oldugu için

         
        while (epoch<maxEpoch && noronsayisi > 1 && noronsayisi < 21 ) {
     	   //537 kez donuyorum = 1 epoch 
             for (Integer egitimSatirNo: egitimSatirlari
             ) {	   girisler.clear();
                 for (int x=0; x<normalizeVeri.length -1; x++){
                     girisler.add(normalizeVeri[x].get(egitimSatirNo));
                     //xa1 den xa8 e kadar verileri girisler e atiyorum  
                     if (x == normalizeVeri.length - 2) {
                         normalizeCikis = normalizeVeri[x + 1].get(egitimSatirNo);
                         gercekCikis = hamVeri[x + 1].get(egitimSatirNo);
                     }
                     //ya1'deki verileri gercekCikis'a 
                     //bu verilerin normalize edilmisleri normalizeCikis'a atýyorum
                 }
                 //egitime basliyorum
                 for (int x=0; x<sinirHucreleri.size(); x++) {
                     sinirHucreleri.set(x, egitim.sigmoid(egitim.aktivasyon(girisler, girisBiaslari.get(x), girisAgirliklari[x])));
                 }
                 //ilk olarak girisagirliklarim ile girislerimi aktivasyon/sigmoid fonk'larinda isliyorum

                 sinirHucresicikis = egitim.aktivasyon(sinirHucreleri, cikisBiasi, cikisAgirliklari);
                 hesaplananCikis = egitim.sigmoid(sinirHucresicikis);
                 netHata = egitim.netHata(hesaplananCikis, normalizeCikis);
                 //her bir iterasyonun sinir hucresi ciktisini elde ediyorum
                 //nethata hesaplamak icin degerlerimi kullaniyorum

                 dagitilacakCikisHatasi = egitim.dagitilacakHataCikis(hesaplananCikis, netHata);
                 //turev islemi

                 mse = egitim.kareHata(hesaplananCikis, normalizeCikis);
                 //her iterasyon icin mse hesaplýyorum

                 for (int x=0; x<sinirHucreleri.size(); x++) {
                     degisim = egitim.agirlikGuncellemeGizliKatmandanCikisa(momentumKatsayisi, ogrenmeKatsayisi, dagitilacakCikisHatasi, cikisAgirliklari.get(x),cikisDegisimleri.get(x));
                     cikisAgirliklari.set(x, cikisAgirliklari.get(x) + degisim);
                     cikisDegisimleri.set(x, degisim);
                     //cikisagirliklarim icin buldugum degisim miktarini ekliyorum
                 }

                 for (int x = 0; x<sinirHucreleri.size(); x++) {
                     dagitilacakGirisHatalari.set(x, egitim.dagitilacakHataGiris(dagitilacakCikisHatasi, cikisAgirliklari.get(x), sinirHucreleri.get(x)));
                     //Girisagirliklarima dagitmak icin hata hesabi yapiyorum
                 }

                 for (int x=0; x<girisAgirliklari.length; x++) {
                     for (int y=0; y<girisAgirliklari[x].size(); y++) {
                         degisim = egitim.agirlikGuncellemeGizliKatmandanGirise(momentumKatsayisi, ogrenmeKatsayisi, dagitilacakGirisHatalari.get(x), girisler.get(y), girisDegisimleri[x].get(y));
                         girisAgirliklari[x].set(y, girisAgirliklari[x].get(y) + degisim);
                         girisDegisimleri[x].set(y, degisim);
                     }
                 }

              deNormalizeHesaplanan = norm.denormalizeVeri(hamVeri[8], hesaplananCikis);
              deNormalizeCikis= norm.denormalizeVeri(hamVeri[8], normalizeCikis);
              //mape= egitim.mapeHata(deNormalizeHesaplanan, deNormalizeCikis);
              mape= egitim.mapeHata(deNormalizeHesaplanan, deNormalizeCikis);
              ortalamaMapeHatalar.add(mape);
              ortalamaKareHatalar.add(mse);
              //ciktilarimi denormalize edip hatalarima ekliyorum
              
              ortalamaKareHata = egitim.ortalamaKareHata(ortalamaKareHatalar);
              ortalamaMapeHata = egitim.ortalamaMapeHata(ortalamaMapeHatalar);
              iterasyon++;
               if (iterasyon % 50 == 0) {

                      mseler.add(ortalamaKareHata);
                      iterasyonlar.add((int)(iterasyon / 50));
                  }
             //hatalarý grafiðime eklemek için her 50 iterasyonda cizme islemi yapiyorum
             }
             System.out.print(epoch + " \t");
             System.out.print("mse -> " + ortalamaKareHata + " \t");
             System.out.println("mape -> " + ortalamaMapeHata);
             epoch++;
        }
        if(noronsayisi>1 && noronsayisi<21)
        {
     	    DataTransfer dataTransfer = new DataTransfer();
     	    dataTransfer.setMseler(mseler);
     	    dataTransfer.setIterasyonlar(iterasyonlar);
            Draw grafikCiz = new Draw("ysaProjesi", "Y1 Ýçin MSE - Iterasyon Grafiði");
            grafikCiz.graf("Egitim icin (");
            
            Test test = new Test();
            test.testEt(testSatirlari, normalizeVeri, girisBiaslari, cikisBiasi, sinirHucreleri,hamVeri, girisAgirliklari, cikisAgirliklari);
       
        }
         
     }

	}


