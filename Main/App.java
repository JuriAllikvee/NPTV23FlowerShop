package app;

import interfaces.Input;
import model.Flower;
import model.Customer;
import model.Order;
import interfaces.Service;

public class App implements Input {

    private final Service<Flower> flowerService;
    private final Service<Customer> customerService;
    private final Service<Order> orderService;

    public App(Service<Flower> flowerService, Service<Customer> customerService, Service<Order> orderService) {
        this.flowerService = flowerService;
        this.customerService = customerService;
        this.orderService = orderService;
    }

    public void run() {
        System.out.println("------ Магазин цветов ------");
        System.out.println("----------------------------");
        boolean repeat = true;
        do {
            System.out.println("Список задач: ");
            System.out.println("0. Выйти из программы");
            System.out.println("1. Добавить цветок");
            System.out.println("2. Список цветов");
            System.out.println("3. Редактировать цветок");
            System.out.println("4. Удалить цветок");
            System.out.println("5. Добавить клиента");
            System.out.println("6. Список клиентов");
            System.out.println("7. Редактировать клиента");
            System.out.println("8. Удалить клиента");
            System.out.println("9. Оформить заказ");
            System.out.println("10. Список заказов");

            System.out.print("Введите номер задачи: ");
            int task = Integer.parseInt(getString());
            switch (task) {
                case 0:
                    repeat = false;
                    break;
                case 1:
                    System.out.println("----- Добавление цветка -----");
                    if (flowerService.add()) {
                        System.out.println("Цветок добавлен");
                    } else {
                        System.out.println("Цветок добавить не удалось");
                    }
                    break;
                case 2:
                    System.out.println("----- Список цветов -----");
                    flowerService.print();
                    break;
                case 3:
                    System.out.println("----- Редактирование цветка -----");
                    System.out.print("Введите название цветка для редактирования: ");
                    String flowerName = getString();
                    Flower flowerToEdit = new Flower(flowerName, 0); // Передаем только имя для поиска
                    if (flowerService.edit(flowerToEdit)) {
                        System.out.println("Цветок успешно отредактирован");
                    } else {
                        System.out.println("Цветок не найден");
                    }
                    break;
                case 4:
                    System.out.println("----- Удаление цветка -----");
                    System.out.print("Введите название цветка для удаления: ");
                    String flowerNameToDelete = getString();
                    Flower flowerToDelete = new Flower(flowerNameToDelete, 0);
                    if (flowerService.remove(flowerToDelete)) {
                        System.out.println("Цветок успешно удален");
                    } else {
                        System.out.println("Цветок не найден");
                    }
                    break;
                case 5:
                    System.out.println("----- Добавление клиента -----");
                    if (customerService.add()) {
                        System.out.println("Клиент добавлен");
                    } else {
                        System.out.println("Клиента добавить не удалось");
                    }
                    break;
                case 6:
                    System.out.println("----- Список клиентов -----");
                    customerService.print();
                    break;
                case 7:
                    System.out.println("----- Редактирование клиента -----");
                    System.out.print("Введите фамилию клиента для редактирования: ");
                    String customerLastName = getString();
                    System.out.print("Введите имя клиента для редактирования: ");
                    String customerFirstName = getString();
                    Customer customerToEdit = new Customer(customerFirstName, customerLastName);
                    if (customerService.edit(customerToEdit)) {
                        System.out.println("Клиент успешно отредактирован");
                    } else {
                        System.out.println("Клиент не найден");
                    }
                    break;
                case 8:
                    System.out.println("----- Удаление клиента -----");
                    System.out.print("Введите фамилию клиента для удаления: ");
                    String customerLastNameToDelete = getString();
                    System.out.print("Введите имя клиента для удаления: ");
                    String customerFirstNameToDelete = getString();
                    Customer customerToDelete = new Customer(customerFirstNameToDelete, customerLastNameToDelete);
                    if (customerService.remove(customerToDelete)) {
                        System.out.println("Клиент успешно удален");
                    } else {
                        System.out.println("Клиент не найден");
                    }
                    break;
                case 9:
                    System.out.println("----- Оформление заказа -----");
                    if (orderService.add()) {
                        System.out.println("Заказ оформлен");
                    } else {
                        System.out.println("Не удалось оформить заказ");
                    }
                    break;
                case 10:
                    System.out.println("----- Список заказов -----");
                    orderService.print();
                    break;
                default:
                    System.out.println("Неверный номер задачи, попробуйте снова.");
            }
            System.out.println("----------------------------");
        } while (repeat);
        System.out.println("До свидания :)");
    }
}
