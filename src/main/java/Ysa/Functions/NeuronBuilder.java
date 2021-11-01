package Ysa.Functions;
import java.util.ArrayList;
import java.util.Random;
public class NeuronBuilder {
	Random rand = new Random();
	public ArrayList<Double> hucreOlustur(int hucresayisi) {
		ArrayList<Double> sinirHucreleri = new ArrayList<Double>();
		if(hucresayisi < 2 || hucresayisi > 20) {
			System.out.println("Sinir hucrelerinizin sayisi 2 ile 20 arasinda olmali.");
			System.out.println("Cikis yaptiniz.");
			return sinirHucreleri;
			
		} else {
			for (int x = 0; x < hucresayisi; x++) {
				sinirHucreleri.add(rand.nextDouble());
			}
			return sinirHucreleri;
		}
	}
}
