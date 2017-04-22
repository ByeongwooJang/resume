package com.example.gwnu.finalproject;

import android.graphics.Color;
import android.opengl.GLSurfaceView;

import org.achartengine.chart.PointStyle;
import org.achartengine.model.TimeSeries;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.achartengine.renderer.XYSeriesRenderer;

import java.util.Date;
import java.util.List;

public abstract class AbstractDemoChart {


	//선들에 대한 설정
	protected XYMultipleSeriesRenderer buildRenderer(int[] colors,
													 PointStyle[] styles) {
		XYMultipleSeriesRenderer renderer = new XYMultipleSeriesRenderer();
		renderer.setLabelsTextSize(30);
		renderer.setLegendTextSize(30);//txt size
		renderer.setLegendHeight(50);
		renderer.setYAxisColor(Color.YELLOW);


		renderer.setPointSize(5f);

		renderer.setBackgroundColor(Color.BLACK);

		
		renderer.setMargins(new int[] { 30, 20, 80, 0 });//between first title and table margin

		int length = colors.length;
		for (int i = 0; i < length; i++) {
			XYSeriesRenderer r = new XYSeriesRenderer();
			r.setColor(colors[i]);
			r.setPointStyle(styles[i]);
			renderer.addSeriesRenderer(r);
		}
		return renderer;
	}


	//cahrt overall setting
	protected void setChartSettings(XYMultipleSeriesRenderer renderer,
									String title, String xTitle, String yTitle, double xMin,
									double xMax, double yMin, double yMax, int axesColor,
									int labelsColor) {
		renderer.setChartTitle(title);
		renderer.setXAxisMin(xMin);
		renderer.setXAxisMax(xMax);
		renderer.setYAxisMin(yMin);
		renderer.setYAxisMax(yMax);
		renderer.setAxesColor(axesColor);
		renderer.setLabelsColor(labelsColor);

		renderer.setApplyBackgroundColor(true);

		renderer.setGridColor(Color.BLACK);
		renderer.setBackgroundColor(Color.BLACK);
	}

	protected XYMultipleSeriesDataset buildDateDataset(String[] titles,
													   List<Date[]> xValues, List<double[]> yValues) {
		XYMultipleSeriesDataset dataset = new XYMultipleSeriesDataset();

		int length = titles.length;
		for (int i = 0; i < length; i++) {
			TimeSeries series = new TimeSeries(titles[i]);
			Date[] xV = xValues.get(i);
			double[] yV = yValues.get(i);
			int seriesLength = xV.length;

			for (int k = 0; k < seriesLength; k++) {
				series.add(xV[k], yV[k]);
			}

			dataset.addSeries(series);
		}



		return dataset;
	}

}
