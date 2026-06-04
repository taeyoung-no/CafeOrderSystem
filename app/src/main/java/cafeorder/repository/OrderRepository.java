package cafeorder.repository;

import java.time.LocalDate;
import java.util.List;

import cafeorder.domain.Order;

public interface OrderRepository {
    Order save(Order order);
    Order findById(Long id);
    List<Order> findByPeriod(LocalDate from, LocalDate to);
}
