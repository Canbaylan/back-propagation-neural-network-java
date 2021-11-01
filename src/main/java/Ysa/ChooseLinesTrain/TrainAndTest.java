package Ysa.ChooseLinesTrain;

import java.util.ArrayList;
import java.util.Random;

public class TrainAndTest {
	public ArrayList<Integer> chooseTrainLines(int numberofLines) {
	int sayac = 0;
	int toplam = (int) (numberofLines * 0.7);
	ArrayList<Integer> trainLines = new ArrayList<Integer>();
	while (sayac < toplam) {
		Random r = new Random();
		int uretilen = r.nextInt(numberofLines);
		boolean temp = true;
		for (int x : trainLines) {
			if (uretilen == x) {
				temp = false;
			}
		}
		if (temp) {
			trainLines.add(uretilen);
			sayac++;
		} else {
			temp = true;
		}

	}

	return trainLines;

}

public ArrayList<Integer> chooseTestLines(ArrayList<Integer> trainLines, int numberofLines) {

	ArrayList<Integer> testSatirlari = new ArrayList<Integer>();

	for (int x = 0; x < numberofLines; x++) {
		if (!trainLines.contains(x)) {
			testSatirlari.add(x);
		}
	}
	return testSatirlari;

}
}
