package org.obukh.pages;

public enum Currency {
    EURO("Euro", (1/0.86), '€'),
    DOLLAR("US Dollar", 0.86,'$');

    private String text;
    private double index;
    private char symbol;

    Currency (String text, double index, char symbol) {
        this.text=text;
        this.index=index;
        this.symbol=symbol;
    }

    public String getText() {
        return text;
    }
    public double getIndex() {
        return index;
    }
    public char getSymbol() {
        return symbol;
    }
}
