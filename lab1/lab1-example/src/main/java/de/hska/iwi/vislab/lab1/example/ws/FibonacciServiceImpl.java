package de.hska.iwi.vislab.lab1.example.ws;

import javax.jws.WebService;

@WebService(endpointInterface = "de.hska.iwi.vislab.lab1.example.ws.FibonacciService")
public class FibonacciServiceImpl implements FibonacciService {
    public Integer getFibonacci(Integer input) {
        return fibonacci(input);
    }

    static int fibonacci(Integer n) {
        int result = 0;

        if (n > 2) {
            result = fibonacci(n - 1) + fibonacci(n - 2);
        } else {
            result = 1;
        }

        return result;
    } 
}