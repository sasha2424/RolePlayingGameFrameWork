package main;

import java.io.Serializable;

public class DoubleStat implements Serializable {
	private double val;
	private double max;
	private double min;
	private double boost;

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

}
