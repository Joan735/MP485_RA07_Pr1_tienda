package model;

public class Amount {

    private double value;
    String currency = "$";

    public Amount(double value) {
        this.value = value;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public String getCurrency() {
        return currency;
    }

    public Amount plus(Amount valor) {
        return new Amount(this.value + valor.getValue());
    }

    public Amount multiply(double value) {
        return new Amount(this.value * value);
    }

    public Amount subtract(Amount valor) {
        return new Amount(this.value - valor.getValue());
    }

    @Override
    public String toString() {
        return value + " " + currency;  // Muestra el valor seguido de la moneda
    }

}
