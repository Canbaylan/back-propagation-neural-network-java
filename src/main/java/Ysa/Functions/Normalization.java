package Ysa.Functions;

import java.util.ArrayList;
import java.util.Collections;

public class Normalization {
	public ArrayList<Double> normalizeVeri(ArrayList<Double> hamveri) {

		Double minA = Collections.min(hamveri);
		Double maxA = Collections.max(hamveri);
		ArrayList<Double> sonuc = new ArrayList<Double>();
		Double hesaplanan;
		for (Double sayi : hamveri) {
			hesaplanan = (sayi - minA) / (maxA - minA);
			sonuc.add(hesaplanan);
		}
		return sonuc;
	}
	
	public Double deNormalizeMin(ArrayList<Double> hamveri)
	{
		return Collections.min(hamveri);
	}
	
	public Double deNormalizeMax(ArrayList<Double> hamveri)
	{
		return Collections.max(hamveri);
	}
	
	public Double denormalizeVeri(ArrayList<Double> hamveri,Double denormalizeolacak) {
		Double dehesaplanan;
		Double denormmin =this.deNormalizeMin(hamveri);
		Double denormmax =this.deNormalizeMax(hamveri);
		dehesaplanan = (denormalizeolacak*(denormmax-denormmin)+denormmin);
		return dehesaplanan;
	}
}
