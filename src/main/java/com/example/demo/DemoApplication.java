
package com.example.demo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.math3.fitting.PolynomialCurveFitter;
import org.apache.commons.math3.fitting.WeightedObservedPoints;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class DemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }

    @GetMapping("/hello")
    public List<Double> hello() {
        List<String> input = Arrays.asList("1", "2", "3", "4", "1", "6", "7", "8", "9", "9", "9.1", "1", "9", "9.5",
                "8.8", "9");
        List<Double> result = this.polynomial(input);
        Iterator<Double> it1 = result.iterator();
        while (it1.hasNext()) {
            System.out.println(it1.next());
        }
        return result;
    }

    /**
     * 最小二乘法曲线拟合
     * https://commons.apache.org/proper/commons-math/userguide/fitting.html
     * 
     * @param data
     * @return
     */
    public List<Double> polynomial(List<String> data) {
        final WeightedObservedPoints obs = new WeightedObservedPoints();
        for (int i = 0; i < data.size(); i++) {
            obs.add(i, Double.valueOf(data.get(i)));
        }

        /**
         * 实例化一个2次多项式拟合器
         */
        final PolynomialCurveFitter fitter = PolynomialCurveFitter.create(3);// degree 阶数，一般为 3

        /**
         * 实例化检索拟合参数(多项式函数的系数)
         */
        final double[] coeff = fitter.fit(obs.toList());// size 0-3 阶数
        List<Double> result = new ArrayList<>();
        for (int i = 0; i < data.size(); i++) {
            double a = coeff[0] * Math.pow(i, 0);
            double b = coeff[1] * Math.pow(i, 1);
            double c = coeff[2] * Math.pow(i, 2);
            double d = coeff[3] * Math.pow(i, 3);
            /**
             * 多项式函数f(x) = a0 * x + a1 * pow(x, 2) + .. + an * pow(x, n).
             */
            double tmp = (a + b + c + d);
            result.add(tmp);
        }
        return result;
    }

}
