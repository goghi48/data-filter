package org.example;

public class StringStatistics {
    private int count = 0;
    private int minLength = Integer.MAX_VALUE;
    private int maxLength = Integer.MIN_VALUE;

    public void update(int length) {
        count++;
        if (length < minLength) minLength = length;
        if (length > maxLength) maxLength = length;
    }

    public int getCount() { return count; }
    public int getMinLength() { return minLength; }
    public int getMaxLength() { return maxLength; }
}