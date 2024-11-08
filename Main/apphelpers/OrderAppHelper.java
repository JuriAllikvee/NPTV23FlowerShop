package apphelpers;

import interfaces.AppHelper;
import interfaces.Input;
import model.Flower;
import model.Customer;
import model.Order;
import interfaces.Service;

import java.time.LocalDate;
import java.util.List;

public class OrderAppHelper implements AppHelper<Order>, Input {

    private final Service<Flower> flowerService;
    private final Service<Customer> customerService;

    public OrderAppHelper(Service<Flower> flowerService, Service<Customer> customerService) {
        this.flowerService = flowerService;
        this.customerService = customerService;
    }

    @Override
    public Order create() {
        try {
            System.out.println("----- Оформление заказа -----");

            // Display available flowers and select one
            flowerService.print();
            System.out.print("Выберите номер цветка: ");
            int flowerIndex = Integer.parseInt(getString()) - 1;
            Flower selectedFlower = flowerService.list().get(flowerIndex);

            customerService.print();
            System.out.print("Выберите номер клиента: ");
            int customerIndex = Integer.parseInt(getString()) - 1;
            Customer selectedCustomer = customerService.list().get(customerIndex);

            Order order = new Order();
            order.setFlower(selectedFlower);
            order.setCustomer(selectedCustomer);
            order.setOrderDate(LocalDate.now());

            return order;
        } catch (Exception e) {
            System.out.println("Ошибка создания заказа: " + e.getMessage());
            return null;
        }
    }

    @Override
    public boolean printList(List<Order> orders) {
        System.out.println("----- Список заказов -----");
        if (orders.isEmpty()) {
            System.out.println("Заказы отсутствуют.");
            return false;
        }

        for (int i = 0; i < orders.size(); i++) {
            Order order = orders.get(i);
            System.out.printf("%d. Клиент: %s %s, Цветок: %s, Дата заказа: %s%n",
                    i + 1,
                    order.getCustomer().getFirstName(),
                    order.getCustomer().getLastName(),
                    order.getFlower().getName(),
                    order.getOrderDate());
        }
        return true;
    }
}
