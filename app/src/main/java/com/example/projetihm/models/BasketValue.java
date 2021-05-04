package com.example.projetihm.models;

public class BasketValue {
    private int value;

    public BasketValue () {
        this.value = 1;
    }

    public BasketValue(int value) {
        this.value = Math.max(1, value);
    }

    public void increment () {
        value++;
    }

    public void decrement() {
        if (value <= 1) {
            value = 0;
        } else {
            value--;
        }
    }

    public int get () {
        return value;
    }


    @Override
    public String toString() {
        return Integer.toString(value);
    }
}
