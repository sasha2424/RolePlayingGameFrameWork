package main;

import java.io.Serializable;

/**
 * This is a Variable like HP which can be incremented up or down between a
 * minimum and maximum value. This variable can also be "boosted" where its
 * value will be increased by a constant. The "boost" is added regardless of the
 * value of the DoubleStat.
 * 
 * @author Alexander Ivanov
 * @version 2018.03.15
 * 
 */

public class DoubleStat implements Serializable {

	private static final long serialVersionUID = 1L;

	private double val; // current value
	private double max; // maximum value
	private double min; // minimum value
	private double boost; // increase on val

	public DoubleStat(double val, double max) {
		this.val = val;
		this.max = max;
		min = 0;
		boost = 0;
	}

	public DoubleStat(double val, double max, double min) {
		this.val = val;
		this.max = max;
		this.min = min;
		boost = 0;
	}

	public void boost(double b) {
		boost += b;
	}

	public void clearBoost() {
		boost = 0;
	}

	public double getVal() {
		return val + boost;
	}

	public void increment(double a) {
		val += a;
		if (val > max)
			val = max;
		if (val < min)
			val = min;
	}

	public void decrement(double a) {
		val -= a;
		if (val > max)
			val = max;
		if (val < min)
			val = min;
	}

	public void set(double a) {
		val = a;
		if (val > max)
			val = max;
		if (val < min)
			val = min;
	}

	public void setMax(double m) {
		max = m;
	}

	public void setBoost(double b) {
		boost = b;
	}

	public void setMin(double m) {
		min = m;
	}

	public double getMax() {
		return max;
	}

}
