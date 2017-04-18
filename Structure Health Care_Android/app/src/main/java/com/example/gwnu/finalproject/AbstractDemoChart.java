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
		renderer.setLegendTextSize(30);//각 선이 무엇을 의미하는지가 밑에 써있는데 그 글자의 크기 이다.
		renderer.setLegendHeight(50);
//		renderer.setLabelsColor(Color.YELLOW);
		renderer.setYAxisColor(Color.YELLOW);


		renderer.setPointSize(5f);

		renderer.setBackgroundColor(Color.BLACK);

		
		renderer.setMargins(new int[] { 30, 20, 80, 0 });//첫번째건 타이틀과 표 사이의 여백
														//세번째는 날짜와 각 선의 의미들 텍스트 사이 여백
//		renderer.setMargins(new int[] { 0, 0, 0, 0 });

		int length = colors.length;
		for (int i = 0; i < length; i++) {
			XYSeriesRenderer r = new XYSeriesRenderer();
			r.setColor(colors[i]);
			r.setPointStyle(styles[i]);
			renderer.addSeriesRenderer(r);
		}
		return renderer;
	}


	//차트 전체적인 설정
	protected void setChartSettings(XYMultipleSeriesRenderer renderer,
									String title, String xTitle, String yTitle, double xMin,
									double xMax, double yMin, double yMax, int axesColor,
									int labelsColor) {
		renderer.setChartTitle(title);
//		renderer.setXTitle(xTitle);
//		renderer.setYTitle(yTitle);
		renderer.setXAxisMin(xMin);
		renderer.setXAxisMax(xMax);
		renderer.setYAxisMin(yMin);
		renderer.setYAxisMax(yMax);
		renderer.setAxesColor(axesColor);
		renderer.setLabelsColor(labelsColor);

//		renderer.setXLabelsPadding(10f);//표로부터 날짜를 얼마나 떨어트릴 건지
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
