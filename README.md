## 요구사항 분석

### 주문 (고객)

- 메뉴 선택
- 옵션 선택
- 수량 선택
- 매장, 포장 선택
- 결제수단 선택
- 결제하기

### 관리 (직원)

- 메뉴 추가, 수정, 삭제
- 옵션 추가, 수정, 삭제
- 주문 내역 조회

### 유스케이스 다이어그램

```mermaid
flowchart LR
  %% 액터
  Customer(["고객"])
  Staff(["직원"])

  %% 시스템 경계
  subgraph SYS["카페 주문 시스템"]
    %% 고객 유스케이스
    UC_Order(["주문하기"])
    UC_SelectMenu(["메뉴 선택"])
    UC_SelectOption(["옵션 선택"])
    UC_SelectQuantity(["수량 선택"])
    UC_SelectDineType(["매장/포장 선택"])
    UC_SelectPayment(["결제수단 선택"])
    UC_Pay(["결제하기"])

    %% 직원 유스케이스
    UC_ManageMenu(["메뉴 관리\n추가/수정/삭제"])
    UC_ManageOption(["옵션 관리\n추가/수정/삭제"])
    UC_ViewSales(["주문 내역 조회"])
  end

  %% 액터 → 유스케이스
  Customer --> UC_Order
  Staff --> UC_ManageMenu
  Staff --> UC_ManageOption
  Staff --> UC_ViewSales

  %% include 관계
  UC_Order -.-> UC_SelectMenu
  UC_Order -.-> UC_SelectOption
  UC_Order -.-> UC_SelectQuantity
  UC_Order -.-> UC_SelectDineType
  UC_Order -.-> UC_SelectPayment
  UC_Order -.-> UC_Pay
```

## 설계

### 클래스 다이어그램

```mermaid
classDiagram
direction LR

%% Service Layer
class MenuService {
  -MenuRepository menuRepository
  +addMenu(String name, int price, String category) Menu
  +updateMenu(Long id, String name, int price, String category, boolean available) Menu
  +removeMenu(Long id) void
  +getMenus() List~Menu~
}
class OptionService {
  -OptionRepository optionRepository
  +addOption(String name, int extraPrice) Option
  +updateOption(Long id, String name, int extraPrice) Option
  +removeOption(Long id) void
  +getOptions() List~Option~
}
class OrderService {
  -OrderRepository orderRepository
  -MenuRepository menuRepository
  -OptionRepository optionRepository
  +createOrder(DineType dineType) Order
  +addItem(Long orderId, Long menuId, List~Long~ optionIds, int quantity) Order
  +checkout(Long orderId, PaymentStrategy payment) Order
}
class OrderHistoryService {
  -OrderRepository orderRepository
  +getOrders(LocalDate from, LocalDate to) List~Order~
  +getOrder(Long id) Order
}

%% Domain
class Menu {
  -Long id
  -String name
  -int price
  -String category
  -boolean available
  -List~Option~ availableOptions
}
class Option {
  -Long id
  -String name
  -int extraPrice
}
class Order {
  -Long id
  -List~OrderItem~ items
  -DineType dineType
  -Payment payment
  -OrderStatus status
  -LocalDateTime orderedAt
  +addItem(OrderItem item) void
  +getTotalPrice() int
}
class OrderItem {
  -Menu menu
  -List~Option~ options
  -int quantity
  +getSubtotal() int
}
class Payment {
  -int amount
  -String method
}
class DineType {
  <<enumeration>>
  DINE_IN
  TAKEOUT
}
class OrderStatus {
  <<enumeration>>
  PENDING
  PAID
}

%% Strategy Pattern: 결제수단
class PaymentStrategy {
  <<interface>>
  +pay(int amount) Payment
}

%% Repository Pattern
class MenuRepository {
  <<interface>>
  +save(Menu menu) Menu
  +findById(Long id) Menu
  +findAll() List~Menu~
  +deleteById(Long id) void
}
class OptionRepository {
  <<interface>>
  +save(Option option) Option
  +findById(Long id) Option
  +findAll() List~Option~
  +deleteById(Long id) void
}
class OrderRepository {
  <<interface>>
  +save(Order order) Order
  +findById(Long id) Order
  +findByPeriod(LocalDate from, LocalDate to) List~Order~
}

%% Service --> Repository (Association, 필드로 보유)
MenuService --> MenuRepository
OptionService --> OptionRepository
OrderService --> OrderRepository
OrderService --> MenuRepository
OrderService --> OptionRepository
OrderHistoryService --> OrderRepository

%% Dependency
MenuRepository ..> Menu
OptionRepository ..> Option
OrderRepository ..> Order
OrderService ..> PaymentStrategy
PaymentStrategy ..> Payment

%% Domain 연관 관계
Order "1" *-- "1..*" OrderItem
OrderItem "1" --> "1" Menu
OrderItem "1" --> "0..*" Option
Menu "1" --> "0..*" Option
Order "1" *-- "0..1" Payment
Order --> DineType
Order --> OrderStatus
```

### ERD

