package cafeorder.payment;

import cafeorder.domain.Payment;

public class CardPayment implements PaymentStrategy {
    @Override
    public Payment pay(int amount) {
        return new Payment(amount, "CARD");
    }
}
