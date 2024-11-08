package apphelpers;

import interfaces.AppHelper;
import interfaces.Input;
import model.Flower;

import java.util.List;

public class FlowerAppHelper implements AppHelper<Flower>, Input {

    @Override
    public Flower create() {
        System.out.print("Название цветка: ");
        String name = getString();
        System.out.print("Цена цветка: ");
        double price = Double.parseDouble(getString());

        return new Flower(name, price);
    }

    @Override
    public boolean printList(List<Flower> flowers) {
        for (int i = 0; i < flowers.size(); i++) {
            Flower flower = flowers.get(i);
            System.out.printf("%d. %s - $%.2f%n", i + 1, flower.getName(), flower.getPrice());
        }
        return true;
    }
}
