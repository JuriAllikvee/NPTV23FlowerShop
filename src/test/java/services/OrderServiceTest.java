package services;

import interfaces.AppHelper;
import interfaces.FileRepository;
import interfaces.Service;
import model.Order;
import model.Flower;
import model.Customer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class OrderServiceTest {

    private OrderService orderService;
    private AppHelper<Order> orderAppHelper;
    private Service<Flower> flowerService;
    private Service<Customer> customerService;
    private FileRepository<Order> storage;

    @BeforeEach
    public void setUp() {
        orderAppHelper = Mockito.mock(AppHelper.class);
        flowerService = Mockito.mock(Service.class);
        customerService = Mockito.mock(Service.class);
        storage = Mockito.mock(FileRepository.class);
        orderService = new OrderService(orderAppHelper, flowerService, customerService, storage);
    }

    @Test
    public void testAdd() {
        // Создаем заказ
        Order order = new Order();
        when(orderAppHelper.create()).thenReturn(order);

        // Проверяем, что заказ был добавлен успешно
        assertTrue(orderService.add());
        verify(storage, times(1)).save(order, "orders.dat");
    }

    @Test
    public void testAddNullOrder() {
        // Подменяем создание заказа, чтобы вернуть null
        when(orderAppHelper.create()).thenReturn(null);

        // Проверяем, что добавление не удалось
        assertFalse(orderService.add());
        verify(storage, never()).save(any(Order.class), anyString());
    }

    @Test
    public void testPrint() {
        // Создаем список заказов и подменяем загрузку
        List<Order> orders = new ArrayList<>();
        Order order = new Order();
        orders.add(order);

        when(storage.load("orders.dat")).thenReturn(orders);
        when(orderAppHelper.printList(orders)).thenReturn(true); // Убедитесь, что printList возвращает true

        // Проверяем, что print() возвращает true
        assertTrue(orderService.print());
        verify(orderAppHelper, times(1)).printList(orders);
    }

    @Test
    public void testList() {
        // Создаем список заказов и подменяем загрузку
        List<Order> orders = new ArrayList<>();
        orders.add(new Order());

        when(storage.load("orders.dat")).thenReturn(orders);

        // Проверяем, что метод list возвращает правильные данные
        List<Order> result = orderService.list();
        assertEquals(orders, result);
    }
}
