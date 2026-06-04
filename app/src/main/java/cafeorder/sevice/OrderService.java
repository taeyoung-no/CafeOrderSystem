package cafeorder.sevice;

import java.util.List;
import java.util.stream.Collectors;

import cafeorder.domain.DineType;
import cafeorder.domain.Menu;
import cafeorder.domain.Option;
import cafeorder.domain.Order;
import cafeorder.domain.OrderItem;
import cafeorder.domain.OrderStatus;
import cafeorder.domain.Payment;
import cafeorder.payment.PaymentStrategy;
import cafeorder.repository.MenuRepository;
import cafeorder.repository.OptionRepository;
import cafeorder.repository.OrderRepository;

public class OrderService {
    private final OrderRepository orderRepository;
    private final MenuRepository menuRepository;
    private final OptionRepository optionRepository;

    public OrderService(
            OrderRepository orderRepository,
            MenuRepository menuRepository,
            OptionRepository optionRepository) {
        this.orderRepository = orderRepository;
        this.menuRepository = menuRepository;
        this.optionRepository = optionRepository;
    }

    public Order createOrder(DineType dineType) {
        Order order = new Order(null, dineType);
        return orderRepository.save(order);
    }

    public Order addItem(Long orderId, Long menuId, List<Long> optionIds, int quantity) {
        Order order = orderRepository.findById(orderId);
        if (order == null) {
            throw new IllegalArgumentException("존재하지 않는 주문입니다. id=" + orderId);
        }
        if (order.getStatus() == OrderStatus.PAID) {
            throw new IllegalStateException("이미 결제된 주문입니다. id=" + orderId);
        }

        Menu menu = menuRepository.findById(menuId);
        if (menu == null) {
            throw new IllegalArgumentException("존재하지 않는 메뉴입니다. id=" + menuId);
        }
        if (!menu.isAvailable()) {
            throw new IllegalStateException("주문 불가능한 메뉴입니다. id=" + menuId);
        }

        List<Option> options = optionIds.stream()
                .map(optionId -> {
                    Option option = optionRepository.findById(optionId);
                    if (option == null) {
                        throw new IllegalArgumentException("존재하지 않는 옵션입니다. id=" + optionId);
                    }
                    return option;
                })
                .collect(Collectors.toList());

        order.addItem(new OrderItem(menu, options, quantity));
        return orderRepository.save(order);
    }

    public Order checkout(Long orderId, PaymentStrategy paymentStrategy) {
        Order order = orderRepository.findById(orderId);
        if (order == null) {
            throw new IllegalArgumentException("존재하지 않는 주문입니다. id=" + orderId);
        }
        if (order.getStatus() == OrderStatus.PAID) {
            throw new IllegalStateException("이미 결제된 주문입니다. id=" + orderId);
        }
        if (order.getItems().isEmpty()) {
            throw new IllegalStateException("주문 항목이 없습니다. id=" + orderId);
        }

        Payment payment = paymentStrategy.pay(order.getTotalPrice());
        order.setPayment(payment);
        order.setStatus(OrderStatus.PAID);
        return orderRepository.save(order);
    }
}
