package cafeorder.payment;

import cafeorder.domain.Payment;

public class CashPayment implements PaymentStrategy {
    @Override
    public Payment pay(int amount) {
        return new Payment(amount, "CASH");
    }
}
