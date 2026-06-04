package cafeorder;

import java.time.LocalDate;
import java.util.List;

import cafeorder.domain.DineType;
import cafeorder.domain.Menu;
import cafeorder.domain.Option;
import cafeorder.domain.Order;
import cafeorder.payment.CardPayment;
import cafeorder.repository.MenuRepository;
import cafeorder.repository.OptionRepository;
import cafeorder.repository.OrderRepository;
import cafeorder.repository.inmemory.InMemoryMenuRepository;
import cafeorder.repository.inmemory.InMemoryOptionRepository;
import cafeorder.repository.inmemory.InMemoryOrderRepository;
import cafeorder.sevice.MenuService;
import cafeorder.sevice.OptionService;
import cafeorder.sevice.OrderHistoryService;
import cafeorder.sevice.OrderService;

public class App {
    public static void main(String[] args) {
        // 1. 레포지토리
        MenuRepository menuRepository = new InMemoryMenuRepository();
        OptionRepository optionRepository = new InMemoryOptionRepository();
        OrderRepository orderRepository = new InMemoryOrderRepository();

        // 2. 서비스
        MenuService menuService = new MenuService(menuRepository);
        OptionService optionService = new OptionService(optionRepository);
        OrderService orderService = new OrderService(orderRepository, menuRepository, optionRepository);
        OrderHistoryService orderHistoryService = new OrderHistoryService(orderRepository);

        // 3. 메뉴 추가
        Menu americano = menuService.addMenu("아메리카노", 4000, "음료");
        Menu latte = menuService.addMenu("카페라떼", 4500, "음료");

        // 4. 옵션 추가
        Option iceOption = optionService.addOption("아이스", 0);
        Option hotOption = optionService.addOption("핫", 0);
        Option extraShot = optionService.addOption("샷 추가", 500);

        // 5. 주문 생성
        Order order = orderService.createOrder(DineType.DINE_IN);

        // 6. 아이템 추가
        orderService.addItem(order.getId(), americano.getId(),
                List.of(iceOption.getId(), extraShot.getId()), 2);
        orderService.addItem(order.getId(), latte.getId(),
                List.of(hotOption.getId()), 1);

        // 7. 결제
        order = orderService.checkout(order.getId(), new CardPayment());

        // 8. 결과 출력
        System.out.println("=== 주문 완료 ===");
        System.out.println("주문 번호: " + order.getId());
        System.out.println("매장/포장: " + order.getDineType());
        System.out.println("결제 수단: " + order.getPayment().getMethod());
        System.out.println("총 금액: " + order.getTotalPrice() + "원");
        System.out.println("상태: " + order.getStatus());

        // 9. 주문 내역 조회
        System.out.println("\n=== 주문 내역 ===");
        orderHistoryService.getOrders(LocalDate.now(), LocalDate.now())
                .forEach(o -> System.out.println("주문 id=" + o.getId()
                        + " 금액=" + o.getTotalPrice() + "원"
                        + " 상태=" + o.getStatus()));
    }
}
