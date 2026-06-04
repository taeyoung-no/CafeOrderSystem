package cafeorder.repository.inmemory;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import cafeorder.domain.Order;
import cafeorder.repository.OrderRepository;

public class InMemoryOrderRepository implements OrderRepository {
    private final Map<Long, Order> store = new HashMap<>();
    private Long sequence = 1L;

    @Override
    public Order save(Order order) {
        if (order.getId() == null) {
            order.setId(sequence++);
        }
        store.put(order.getId(), order);
        return order;
    }

    @Override
    public Order findById(Long id) {
        return store.get(id);
    }

    @Override
    public List<Order> findByPeriod(LocalDate from, LocalDate to) {
        return store.values().stream()
                .filter(o -> {
                    LocalDate date = o.getOrderedAt().toLocalDate();
                    return !date.isBefore(from) && !date.isAfter(to);
                })
                .collect(Collectors.toList());
    }
}
