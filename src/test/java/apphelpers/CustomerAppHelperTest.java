package apphelpers;

import model.Customer;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;

public class CustomerAppHelperTest {

    private CustomerAppHelper customerAppHelper;
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;

    @BeforeEach
    public void setUp() {
        customerAppHelper = new CustomerAppHelper();
        System.setOut(new PrintStream(outContent));
    }

    @AfterEach
    public void tearDown() {
        System.setOut(originalOut);
        outContent.reset();
    }

    @Test
    public void testCreate() {
        // Мокируем методы ввода
        CustomerAppHelper spyHelper = Mockito.spy(customerAppHelper);
        doReturn("Иванов", "Иван", "+1234567890").when(spyHelper).getString();

        Customer customer = spyHelper.create();

        assertNotNull(customer);
        assertEquals("Иванов", customer.getFirstName());
        assertEquals("Иван", customer.getLastName());
        assertEquals("+1234567890", customer.getPhone());
    }

    @Test
    public void testPrintList() {
        List<Customer> customers = new ArrayList<>();
        customers.add(new Customer("Иванов", "Иван", "+1234567890"));
        customers.add(new Customer("Петров", "Петр", "+0987654321"));

        customerAppHelper.printList(customers);

        String expectedOutput1 = "1. Иванов Иван, Телефон: +1234567890";
        String expectedOutput2 = "2. Петров Петр, Телефон: +0987654321";

        assertTrue(outContent.toString().contains(expectedOutput1));
        assertTrue(outContent.toString().contains(expectedOutput2));
    }

}
