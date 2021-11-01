package Ysa.ReadFromExcel;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
public class ReadExcel {
	private static final String EXCEL_FILE_LOCATION = "enerji-veri-seti.xls";

	public double strToDouble(String a) {
		Double s = Double.parseDouble(a);
		return s;
	}

	public ArrayList<Double> sutun(int sutunNo) {

		ArrayList<Double> liste = new ArrayList<Double>();

		Workbook workbook = null;
		try {

			workbook = Workbook.getWorkbook(new File(EXCEL_FILE_LOCATION));

			Sheet sheet = workbook.getSheet(0);

			int rowCount = sheet.getRows(); 
			for (int i = 1; i < rowCount; i++) {
				Cell cell1 = sheet.getCell(sutunNo, i); 
				liste.add(this.strToDouble(cell1.getContents().replace(',', '.')));
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (BiffException e) {
			e.printStackTrace();
		} finally {

			if (workbook != null) {
				workbook.close();
			}

		}
		return liste;
	}
}
