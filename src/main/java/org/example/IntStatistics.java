package org.example;

public class IntStatistics {
    private int count = 0;
    private long min = Long.MAX_VALUE;
    private long max = Long.MIN_VALUE;
    private long sum = 0;

    public void update(long value) {
        count++;
        sum += value;
        if (value < min) min = value;
        if (value > max) max = value;
    }

    public int getCount() { return count; }
    public long getMin() { return min; }
    public long getMax() { return max; }
    public long getSum() { return sum; }
}