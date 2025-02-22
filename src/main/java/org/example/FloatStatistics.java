package org.example;

public class FloatStatistics {
    private int count = 0;
    private double min = Double.MAX_VALUE;
    private double max = Double.MIN_VALUE;
    private double sum = 0.0;

    public void update(double value) {
        count++;
        sum += value;
        if (value < min) min = value;
        if (value > max) max = value;
    }

    public int getCount() { return count; }
    public double getMin() { return min; }
    public double getMax() { return max; }
    public double getSum() { return sum; }
}