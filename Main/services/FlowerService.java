package services;

import interfaces.AppHelper;
import interfaces.FileRepository;
import interfaces.Service;
import model.Flower;

import java.util.List;

public class FlowerService implements Service<Flower> {

    private final AppHelper<Flower> flowerAppHelper;
    private final FileRepository<Flower> storage;
    private final String fileName = "flowers.dat";

    public FlowerService(AppHelper<Flower> flowerAppHelper, FileRepository<Flower> storage) {
        this.flowerAppHelper = flowerAppHelper;
        this.storage = storage;
    }

    @Override
    public boolean add() {
        Flower flower = flowerAppHelper.create();
        if (flower != null) {
            storage.save(flower, fileName);
            return true;
        }
        return false;
    }

    @Override
    public boolean edit(Flower flower) {
        List<Flower> flowers = storage.load(fileName);
        for (int i = 0; i < flowers.size(); i++) {
            if (flowers.get(i).getName().equals(flower.getName())) {
                System.out.println("Введите новые данные для цветка:");
                Flower updatedFlower = flowerAppHelper.create();
                flowers.set(i, updatedFlower);
                storage.saveAll(flowers, fileName);
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean remove(Flower flower) {
        List<Flower> flowers = storage.load(fileName);
        boolean removed = flowers.removeIf(f -> f.getName().equals(flower.getName()));
        if (removed) {
            storage.saveAll(flowers, fileName);
        }
        return removed;
    }

    @Override
    public boolean print() {
        return flowerAppHelper.printList(this.list());
    }

    @Override
    public List<Flower> list() {
        return storage.load(fileName);
    }
}
