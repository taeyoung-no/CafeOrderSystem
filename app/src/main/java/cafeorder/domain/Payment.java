package cafeorder.domain;

public class Payment {
    private int amount;
    private String method;

    public Payment(int amount, String method) {
        this.amount = amount;
        this.method = method;
    }

    public int getAmount() {
        return amount;
    }

    public String getMethod() {
        return method;
    }
}
