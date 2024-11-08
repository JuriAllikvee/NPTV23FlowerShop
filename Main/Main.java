package main;

import app.App;
import apphelpers.FlowerAppHelper;
import apphelpers.CustomerAppHelper;
import apphelpers.OrderAppHelper;
import interfaces.AppHelper;
import interfaces.FileRepository;
import model.Flower;
import model.Customer;
import model.Order;
import services.FlowerService;
import services.CustomerService;
import services.OrderService;
import storage.Storage;
import interfaces.Service;

public class Main {

    public static void main(String[] args) {
        AppHelper<Flower> flowerAppHelper = new FlowerAppHelper();
        AppHelper<Customer> customerAppHelper = new CustomerAppHelper();

        FileRepository<Flower> flowerStorage = new Storage<>();
        FileRepository<Customer> customerStorage = new Storage<>();
        FileRepository<Order> orderStorage = new Storage<>();
        Service<Flower> flowerService = new FlowerService(flowerAppHelper, flowerStorage);
        Service<Customer> customerService = new CustomerService(customerAppHelper, customerStorage);
        AppHelper<Order> orderAppHelper = new OrderAppHelper(flowerService, customerService);
        Service<Order> orderService = new OrderService(orderAppHelper, flowerService, customerService, orderStorage);
        App app = new App(flowerService, customerService, orderService);
        app.run();
    }
}
