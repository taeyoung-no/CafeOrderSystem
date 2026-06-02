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
- 매출 기록 조회

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
    UC_ViewSales(["매출 기록 조회"])
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
