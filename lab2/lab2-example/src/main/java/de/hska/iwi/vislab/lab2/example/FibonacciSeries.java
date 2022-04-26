package de.hska.iwi.vislab.lab2.example;

public class FibonacciSeries {
    Integer id;

    int n1, n2;

    public FibonacciSeries(Integer id) {
        this.id = id;

        n1 = 0;
        n2 = 1;
    }

    public int getNext() {
        int result = n1 + n2;
        n1 = n2;
        n2 = result;
        return result;
    }
}
