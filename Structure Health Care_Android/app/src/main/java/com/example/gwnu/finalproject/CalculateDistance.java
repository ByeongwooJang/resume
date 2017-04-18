package com.example.gwnu.finalproject;

import org.apache.commons.math3.analysis.interpolation.*;
import org.apache.commons.math3.analysis.polynomials.*;
/**
 * Created by Ryong on 2016-11-11.
 */

public class CalculateDistance {

    double[] magnetZaxisData = {
            1191, 1223, 1244, 1282, 1303, 1329, 1365, 1406, 1436, 1468,
            1509, 1545, 1581, 1610, 1650, 1701, 1758, 1807, 1835, 1879,
            1940, 1995, 2086, 2137, 2221, 2272, 2340, 2413, 2484, 2556,
            2633, 2747, 2857, 2944, 3040, 3133, 3200, 3288, 3383, 3528,
            3656, 3783, 3878, 4059, 4207, 4432, 4570, 4850, 5084, 5296,
            5612, 5851, 6072, 6337, 6568, 6854, 7144, 7458, 7925, 8260,
            8652, 8973, 9489, 9824, 10374, 11106, 11504, 12129, 12981, 13296,
            14425, 15682, 17295, 19767, 21053, 22886, 25277, 26533, 29684, 31599,
            32672
    };

    double[] magnetZaxisDistance = {
            120.0, 118.33, 117.39, 115.55, 114.41, 113.31, 111.90, 110.25, 109.08, 107.92,
            106.54, 105.33, 104.34, 103.32, 102.14, 101.11, 99.51, 98.20, 97.45, 96.47,
            95.08, 93.88, 92.04, 90.93, 89.39, 88.50, 87.36, 86.18, 85.08, 84.01,
            82.99, 81.45, 80.12, 79.11, 78.01, 77.03, 76.30, 75.44, 74.51, 73.19,
            72.09, 71.08, 70.32, 68.99, 67.95, 66.45, 65.60, 63.97, 62.73, 61.65,
            60.17, 59.11, 58.20, 57.16, 56.29, 55.29, 54.33, 53.35, 52.00, 51.10,
            50.10, 49.33, 48.17, 47.46, 46.36, 45.03, 44.35, 43.35, 42.11, 41.66,
            40.22, 38.79, 37.15, 35.01, 34.04, 32.78, 31.34, 30.66, 29.11, 28.17,
            27.85
    };

    public double getTmagnetInterpolationZ(double zData)
    {
        double distance = 0;

        try
        {
            SplineInterpolator li = new SplineInterpolator();
            PolynomialSplineFunction psf = li.interpolate(magnetZaxisData, magnetZaxisDistance);

            distance = psf.value(zData);
        }
        catch(Exception e) { }

        return distance;
    }
}
