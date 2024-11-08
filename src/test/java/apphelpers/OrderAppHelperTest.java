package apphelpers;

import model.Customer;
import model.Flower;
import model.Order;
import interfaces.Service;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class OrderAppHelperTest {

    private OrderAppHelper orderAppHelper;
    private Service<Flower> flowerService;
    private Service<Customer> customerService;
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;

    @BeforeEach
    public void setUp() {
        flowerService = Mockito.mock(Service.class);
        customerService = Mockito.mock(Service.class);
        orderAppHelper = new OrderAppHelper(flowerService, customerService);
        System.setOut(new PrintStream(outContent)); // Захват вывода
    }

    @AfterEach
    public void tearDown() {
        System.setOut(originalOut);
        outContent.reset();
    }

    @Test
    public void testCreate() {
        OrderAppHelper spyHelper = Mockito.spy(orderAppHelper);

        List<Flower> flowers = new ArrayList<>();
        flowers.add(new Flower("Роза", 25.5));
        when(flowerService.list()).thenReturn(flowers);

        List<Customer> customers = new ArrayList<>();
        customers.add(new Customer("Иванов", "Иван", "+1234567890"));
        when(customerService.list()).thenReturn(customers);

        doReturn("1", "1").when(spyHelper).getString(); // Выбираем первый цветок и первого клиента

        Order order = spyHelper.create();

        assertNotNull(order, "Заказ должен быть создан");
        assertEquals("Роза", order.getFlower().getName());
        assertEquals("Иванов", order.getCustomer().getFirstName());
        assertEquals(LocalDate.now(), order.getOrderDate());
    }

    @Test
    public void testPrintList() {
        List<Order> orders = new ArrayList<>();
        Flower flower = new Flower("Роза", 25.5);
        Customer customer = new Customer("Иванов", "Иван", "+1234567890");
        Order order = new Order();
        order.setFlower(flower);
        order.setCustomer(customer);
        order.setOrderDate(LocalDate.now());
        orders.add(order);

        orderAppHelper.printList(orders);

        String expectedOutput = String.format("----- Список заказов -----%n" +
                        "1. Клиент: Иванов Иван, Цветок: Роза, Дата заказа: %s%n",
                LocalDate.now().toString());
        assertEquals(expectedOutput, outContent.toString());
    }
}
