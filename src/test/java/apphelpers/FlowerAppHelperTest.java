package apphelpers;

import model.Flower;
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
import static org.mockito.Mockito.spy;

public class FlowerAppHelperTest {

    private FlowerAppHelper flowerAppHelper;
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;

    @BeforeEach
    public void setUp() {
        flowerAppHelper = new FlowerAppHelper();
        System.setOut(new PrintStream(outContent)); // Перенаправляем System.out для захвата вывода
    }

    @AfterEach
    public void tearDown() {
        System.setOut(originalOut); // Восстанавливаем System.out
        outContent.reset();
    }

    @Test
    public void testCreate() {
        // Создаем spy объект для FlowerAppHelper
        FlowerAppHelper spyHelper = spy(flowerAppHelper);

        // Мокаем ввод для метода getString
        doReturn("Роза", "25.5").when(spyHelper).getString();

        // Вызываем метод create и проверяем результат
        Flower flower = spyHelper.create();

        // Проверяем, что объект Flower создан с ожидаемыми значениями
        assertNotNull(flower);
        assertEquals("Роза", flower.getName());
        assertEquals(25.5, flower.getPrice(), 0.01);
    }

    @Test
    public void testPrintList() {
        // Создаем список цветов для теста
        List<Flower> flowers = new ArrayList<>();
        flowers.add(new Flower("Роза", 25.5));
        flowers.add(new Flower("Лилия", 15.0));

        // Вызываем метод printList и проверяем вывод
        flowerAppHelper.printList(flowers);

        String expectedOutput = "1. Роза - $25.50" + System.lineSeparator() +
                "2. Лилия - $15.00" + System.lineSeparator();

        assertEquals(expectedOutput, outContent.toString());
    }
}
