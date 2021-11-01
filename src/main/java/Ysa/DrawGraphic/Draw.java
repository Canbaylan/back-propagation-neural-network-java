package Ysa.DrawGraphic;

import java.util.ArrayList;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.ui.ApplicationFrame;
import org.jfree.chart.ui.UIUtils;
import org.jfree.data.category.DefaultCategoryDataset;

import Ysa.DataTransfer;


public class Draw extends ApplicationFrame {
	private UIUtils RefineryUtilities;
	DataTransfer dataTransfer = new DataTransfer();
	ArrayList<Double> mseler = dataTransfer.getMseler();
	ArrayList<Integer> iterasyonlar = dataTransfer.getIterasyonlar();

	public Draw(String applicationTitle, String chartTitle) {
		super(applicationTitle);
		DefaultCategoryDataset dataset = new DefaultCategoryDataset();
		for (int x = 0; x < iterasyonlar.size(); x++) {
			dataset.addValue(mseler.get(x), "Iterasyon", iterasyonlar.get(x).toString());
		}

		JFreeChart lineChart = ChartFactory.createLineChart(chartTitle, "Iterasyon", "MSE", dataset,
				PlotOrientation.VERTICAL, true, true, false);

		ChartPanel chartPanel = new ChartPanel(lineChart);
		chartPanel.setPreferredSize(new java.awt.Dimension(1000, 1000));
		setContentPane(chartPanel);
	}

	public void graf(String tur) {
		Draw chart = new Draw("MSE - Iterasyon",tur + "  Hesaplanan MSE - Iterasyon(x50) )");
		chart.pack();
		RefineryUtilities.centerFrameOnScreen(chart);
		chart.setVisible(true);
	}
}
