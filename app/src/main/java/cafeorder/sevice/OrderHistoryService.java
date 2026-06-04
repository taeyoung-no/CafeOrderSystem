package cafeorder.sevice;

import java.time.LocalDate;
import java.util.List;

import cafeorder.domain.Order;
import cafeorder.repository.OrderRepository;

public class OrderHistoryService {
    private final OrderRepository orderRepository;

    public OrderHistoryService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public List<Order> getOrders(LocalDate from, LocalDate to) {
        return orderRepository.findByPeriod(from, to);
    }

    public Order getOrder(Long id) {
        Order order = orderRepository.findById(id);
        if (order == null) {
            throw new IllegalArgumentException("존재하지 않는 주문입니다. id=" + id);
        }
        return order;
    }
}
