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
        Flower flower = new Flower("Роза", 25.5);
        when(flowerAppHelper.create()).thenReturn(flower);

        assertTrue(flowerService.add());
        verify(storage, times(1)).save(flower, "flowers.dat");
    }

    @Test
    public void testAddNullFlower() {
        when(flowerAppHelper.create()).thenReturn(null);

        assertFalse(flowerService.add());
        verify(storage, never()).save(any(Flower.class), anyString());
    }

    @Test
    public void testEditExistingFlower() {
        Flower flower = new Flower("Роза", 25.5);
        List<Flower> flowers = new ArrayList<>();
        flowers.add(flower);

        when(storage.load("flowers.dat")).thenReturn(flowers);
        Flower updatedFlower = new Flower("Роза", 30.0);
        when(flowerAppHelper.create()).thenReturn(updatedFlower);

        assertTrue(flowerService.edit(flower));
        verify(storage, times(1)).saveAll(flowers, "flowers.dat");
    }

    @Test
    public void testEditNonExistentFlower() {
        List<Flower> flowers = new ArrayList<>();
        flowers.add(new Flower("Лилия", 15.0));

        when(storage.load("flowers.dat")).thenReturn(flowers);

        assertFalse(flowerService.edit(new Flower("Роза", 25.5)));
        verify(storage, never()).saveAll(anyList(), anyString());
    }

    @Test
    public void testRemoveExistingFlower() {
        Flower flower = new Flower("Роза", 25.5);
        List<Flower> flowers = new ArrayList<>();
        flowers.add(flower);

        when(storage.load("flowers.dat")).thenReturn(flowers);

        assertTrue(flowerService.remove(flower));
        verify(storage, times(1)).saveAll(flowers, "flowers.dat");
    }

    @Test
    public void testRemoveNonExistentFlower() {
        List<Flower> flowers = new ArrayList<>();
        flowers.add(new Flower("Лилия", 15.0));

        when(storage.load("flowers.dat")).thenReturn(flowers);

        assertFalse(flowerService.remove(new Flower("Роза", 25.5)));
        verify(storage, never()).saveAll(anyList(), anyString());
    }

    @Test
    public void testPrint() {
        List<Flower> flowers = new ArrayList<>();
        flowers.add(new Flower("Роза", 25.5));
        flowers.add(new Flower("Лилия", 15.0));

        when(storage.load("flowers.dat")).thenReturn(flowers);
        when(flowerAppHelper.printList(flowers)).thenReturn(true);

        boolean result = flowerService.print();
        assertTrue(result, "Метод print() должен возвращать true, если печать выполнена успешно");
    }


    @Test
    public void testList() {
        List<Flower> flowers = new ArrayList<>();
        flowers.add(new Flower("Роза", 25.5));
        flowers.add(new Flower("Лилия", 15.0));

        when(storage.load("flowers.dat")).thenReturn(flowers);

        List<Flower> result = flowerService.list();
        assertEquals(flowers, result);
    }
}
