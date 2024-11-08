package services;

import interfaces.AppHelper;
import interfaces.FileRepository;
import interfaces.Service;
import model.Order;
import model.Flower;
import model.Customer;

import java.util.List;

public class OrderService implements Service<Order> {

    private final AppHelper<Order> orderAppHelper;
    private final Service<Flower> flowerService;
    private final Service<Customer> customerService;
    private final FileRepository<Order> storage;
    private final String fileName = "orders.dat";

    public OrderService(AppHelper<Order> orderAppHelper, Service<Flower> flowerService, Service<Customer> customerService, FileRepository<Order> storage) {
        this.orderAppHelper = orderAppHelper;
        this.flowerService = flowerService;
        this.customerService = customerService;
        this.storage = storage;
    }

    @Override
    public boolean add() {
        Order order = orderAppHelper.create();
        if (order != null) {
            storage.save(order, fileName);
            return true;
        }
        return false;
    }

    @Override
    public boolean edit(Order order) {
        return false;
    }

    @Override
    public boolean remove(Order order) {
        return false;
    }

    @Override
    public boolean print() {
        return orderAppHelper.printList(this.list());
    }

    @Override
    public List<Order> list() {
        return storage.load(fileName);
    }
}
