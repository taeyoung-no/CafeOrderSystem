package cafeorder.payment;

import cafeorder.domain.Payment;

public interface PaymentStrategy {
    Payment pay(int amount);
}
