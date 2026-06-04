package cafeorder.domain;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Order {
    private Long id;
    private List<OrderItem> items;
    private DineType dineType;
    private Payment payment;
    private OrderStatus status;
    private LocalDateTime orderedAt;

    public Order(Long id, DineType dineType) {
        this.id = id;
        this.dineType = dineType;
        this.items = new ArrayList<>();
        this.status = OrderStatus.PENDING;
        this.orderedAt = LocalDateTime.now();
    }

    public void addItem(OrderItem item) {
        items.add(item);
    }

    public int getTotalPrice() {
        return items.stream().mapToInt(OrderItem::getTotalPrice).sum();
    }

    public Long getId() {
        return id;
    }

    public List<OrderItem> getItems() {
        return items;
    }

    public DineType getDineType() {
        return dineType;
    }

    public Payment getPayment() {
        return payment;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public LocalDateTime getOrderedAt() {
        return orderedAt;
    }

    public void setPayment(Payment payment) {
        this.payment = payment;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }
}
