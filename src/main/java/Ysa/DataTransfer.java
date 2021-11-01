package Ysa;

import java.util.ArrayList;

import Ysa.DataTransfer;

public class DataTransfer {
	public static ArrayList<Double> mseler;
	public static ArrayList<Integer> iterasyonlar;

	public static ArrayList<Double> getMseler() {
		return mseler;
	}

	public static void setMseler(ArrayList<Double> mseler) {
		DataTransfer.mseler = mseler;
	}

	public static ArrayList<Integer> getIterasyonlar() {
		return iterasyonlar;
	}

	public static void setIterasyonlar(ArrayList<Integer> iterasyonlar) {
		DataTransfer.iterasyonlar = iterasyonlar;
	}
}
