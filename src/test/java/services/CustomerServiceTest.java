package services;

import interfaces.AppHelper;
import interfaces.FileRepository;
import model.Customer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class CustomerServiceTest {

    private CustomerService customerService;
    private AppHelper<Customer> customerAppHelper;
    private FileRepository<Customer> storage;

    @BeforeEach
    public void setUp() {
        customerAppHelper = Mockito.mock(AppHelper.class);
        storage = Mockito.mock(FileRepository.class);
        customerService = new CustomerService(customerAppHelper, storage);
    }

    @Test
    public void testAdd() {
        // Подменяем создание клиента
        Customer customer = new Customer("Иванов", "Иван", "+1234567890");
        when(customerAppHelper.create()).thenReturn(customer);

        // Проверяем, что добавление прошло успешно
        assertTrue(customerService.add());
        verify(storage, times(1)).save(customer, "customers.dat");
    }

    @Test
    public void testAddNullCustomer() {
        // Подменяем создание клиента, чтобы вернуть null
        when(customerAppHelper.create()).thenReturn(null);

        // Проверяем, что добавление не удалось
        assertFalse(customerService.add());
        verify(storage, never()).save(any(Customer.class), anyString());
    }

    @Test
    public void testEditExistingCustomer() {
        // Создаем список клиентов, где будет один клиент "Иван Иванов"
        Customer customer = new Customer("Иванов", "Иван", "+1234567890");
        List<Customer> customers = new ArrayList<>();
        customers.add(customer);

        // Подменяем загрузку клиентов и создание обновленного клиента
        when(storage.load("customers.dat")).thenReturn(customers);
        Customer updatedCustomer = new Customer("Иванов", "Иван", "+0987654321");
        when(customerAppHelper.create()).thenReturn(updatedCustomer);

        // Проверяем, что редактирование прошло успешно
        assertTrue(customerService.edit(customer));
        verify(storage, times(1)).saveAll(customers, "customers.dat");
    }

    @Test
    public void testEditNonExistentCustomer() {
        // Создаем список клиентов без целевого клиента
        List<Customer> customers = new ArrayList<>();
        customers.add(new Customer("Петров", "Петр", "+1234567890"));

        // Подменяем загрузку клиентов
        when(storage.load("customers.dat")).thenReturn(customers);

        // Проверяем, что редактирование не удалось
        assertFalse(customerService.edit(new Customer("Иванов", "Иван", "+1234567890")));
        verify(storage, never()).saveAll(anyList(), anyString());
    }

    @Test
    public void testRemoveExistingCustomer() {
        // Создаем список клиентов с целевым клиентом
        Customer customer = new Customer("Иванов", "Иван", "+1234567890");
        List<Customer> customers = new ArrayList<>();
        customers.add(customer);

        // Подменяем загрузку клиентов
        when(storage.load("customers.dat")).thenReturn(customers);

        // Проверяем, что удаление прошло успешно
        assertTrue(customerService.remove(customer));
        verify(storage, times(1)).saveAll(customers, "customers.dat");
    }

    @Test
    public void testRemoveNonExistentCustomer() {
        // Создаем список клиентов без целевого клиента
        List<Customer> customers = new ArrayList<>();
        customers.add(new Customer("Петров", "Петр", "+1234567890"));

        // Подменяем загрузку клиентов
        when(storage.load("customers.dat")).thenReturn(customers);

        // Проверяем, что удаление не удалось
        assertFalse(customerService.remove(new Customer("Иванов", "Иван", "+1234567890")));
        verify(storage, never()).saveAll(anyList(), anyString());
    }

    @Test
    public void testPrint() {
        // Создаем список клиентов и подменяем загрузку
        List<Customer> customers = new ArrayList<>();
        customers.add(new Customer("Иванов", "Иван", "+1234567890"));
        customers.add(new Customer("Петров", "Петр", "+0987654321"));

        when(storage.load("customers.dat")).thenReturn(customers);
        when(customerAppHelper.printList(customers)).thenReturn(true); // Убедитесь, что printList возвращает true

        // Проверяем, что print() возвращает true
        boolean result = customerService.print();
        assertTrue(result, "Метод print() должен возвращать true, если печать выполнена успешно");
    }


    @Test
    public void testList() {
        // Создаем список клиентов и подменяем загрузку
        List<Customer> customers = new ArrayList<>();
        customers.add(new Customer("Иванов", "Иван", "+1234567890"));
        customers.add(new Customer("Петров", "Петр", "+0987654321"));

        when(storage.load("customers.dat")).thenReturn(customers);

        // Проверяем, что метод list возвращает правильные данные
        List<Customer> result = customerService.list();
        assertEquals(customers, result);
    }
}
