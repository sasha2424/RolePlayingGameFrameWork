package main;

import java.io.Serializable;

public class DoubleStat implements Serializable{
	private double val;
	private double max;
	private double boost; // TODO make a "Boost" class that hold a value and
							// change this to an arraylist

	public DoubleStat(double val, double max) {
		this.val = val;
		this.max = max;
		boost = 0;
	}

	public double getVal() {
		return val + boost;
	}

	public void increment(double a) {
		val += a;
		if (val > max)
			val = max;
		if (val < 0)
			val = 0;
	}

	public void decrement(double a) {
		val -= a;
		if (val > max)
			val = max;
		if (val < 0)
			val = 0;
	}

	public void set(double a) {
		val = a;
		if (val > max)
			val = max;
		if (val < 0)
			val = 0;
	}

}
