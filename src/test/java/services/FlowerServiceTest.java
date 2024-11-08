package services;

import interfaces.AppHelper;
import interfaces.FileRepository;
import model.Flower;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class FlowerServiceTest {

    private FlowerService flowerService;
    private AppHelper<Flower> flowerAppHelper;
    private FileRepository<Flower> storage;

    @BeforeEach
    public void setUp() {
        flowerAppHelper = Mockito.mock(AppHelper.class);
        storage = Mockito.mock(FileRepository.class);
        flowerService = new FlowerService(flowerAppHelper, storage);
    }

    @Test
    public void testAdd() {
        // Подменяем создание цветка
        Flower flower = new Flower("Роза", 25.5);
        when(flowerAppHelper.create()).thenReturn(flower);

        // Проверяем, что добавление прошло успешно
        assertTrue(flowerService.add());
        verify(storage, times(1)).save(flower, "flowers.dat");
    }

    @Test
    public void testAddNullFlower() {
        // Подменяем создание цветка, чтобы вернуть null
        when(flowerAppHelper.create()).thenReturn(null);

        // Проверяем, что добавление не удалось
        assertFalse(flowerService.add());
        verify(storage, never()).save(any(Flower.class), anyString());
    }

    @Test
    public void testEditExistingFlower() {
        // Создаем список цветов, где будет один цветок "Роза"
        Flower flower = new Flower("Роза", 25.5);
        List<Flower> flowers = new ArrayList<>();
        flowers.add(flower);

        // Подменяем загрузку цветов и создание обновленного цветка
        when(storage.load("flowers.dat")).thenReturn(flowers);
        Flower updatedFlower = new Flower("Роза", 30.0);
        when(flowerAppHelper.create()).thenReturn(updatedFlower);

        // Проверяем, что редактирование прошло успешно
        assertTrue(flowerService.edit(flower));
        verify(storage, times(1)).saveAll(flowers, "flowers.dat");
    }

    @Test
    public void testEditNonExistentFlower() {
        // Создаем список цветов без целевого цветка
        List<Flower> flowers = new ArrayList<>();
        flowers.add(new Flower("Лилия", 15.0));

        // Подменяем загрузку цветов
        when(storage.load("flowers.dat")).thenReturn(flowers);

        // Проверяем, что редактирование не удалось
        assertFalse(flowerService.edit(new Flower("Роза", 25.5)));
        verify(storage, never()).saveAll(anyList(), anyString());
    }

    @Test
    public void testRemoveExistingFlower() {
        // Создаем список цветов с целевым цветком
        Flower flower = new Flower("Роза", 25.5);
        List<Flower> flowers = new ArrayList<>();
        flowers.add(flower);

        // Подменяем загрузку цветов
        when(storage.load("flowers.dat")).thenReturn(flowers);

        // Проверяем, что удаление прошло успешно
        assertTrue(flowerService.remove(flower));
        verify(storage, times(1)).saveAll(flowers, "flowers.dat");
    }

    @Test
    public void testRemoveNonExistentFlower() {
        // Создаем список цветов без целевого цветка
        List<Flower> flowers = new ArrayList<>();
        flowers.add(new Flower("Лилия", 15.0));

        // Подменяем загрузку цветов
        when(storage.load("flowers.dat")).thenReturn(flowers);

        // Проверяем, что удаление не удалось
        assertFalse(flowerService.remove(new Flower("Роза", 25.5)));
        verify(storage, never()).saveAll(anyList(), anyString());
    }

    @Test
    public void testPrint() {
        // Создаем список цветов и подменяем загрузку
        List<Flower> flowers = new ArrayList<>();
        flowers.add(new Flower("Роза", 25.5));
        flowers.add(new Flower("Лилия", 15.0));

        when(storage.load("flowers.dat")).thenReturn(flowers);
        when(flowerAppHelper.printList(flowers)).thenReturn(true);  // Убедитесь, что printList возвращает true

        // Проверяем, что print возвращает true
        boolean result = flowerService.print();
        assertTrue(result, "Метод print() должен возвращать true, если печать выполнена успешно");
    }


    @Test
    public void testList() {
        // Создаем список цветов и подменяем загрузку
        List<Flower> flowers = new ArrayList<>();
        flowers.add(new Flower("Роза", 25.5));
        flowers.add(new Flower("Лилия", 15.0));

        when(storage.load("flowers.dat")).thenReturn(flowers);

        // Проверяем, что метод list возвращает правильные данные
        List<Flower> result = flowerService.list();
        assertEquals(flowers, result);
    }
}
