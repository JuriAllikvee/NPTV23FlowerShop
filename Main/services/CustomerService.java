package services;

import interfaces.AppHelper;
import interfaces.FileRepository;
import interfaces.Service;
import model.Customer;

import java.util.List;

public class CustomerService implements Service<Customer> {

    private final AppHelper<Customer> customerAppHelper;
    private final FileRepository<Customer> storage;
    private final String fileName = "customers.dat";

    public CustomerService(AppHelper<Customer> customerAppHelper, FileRepository<Customer> storage) {
        this.customerAppHelper = customerAppHelper;
        this.storage = storage;
    }

    @Override
    public boolean add() {
        Customer customer = customerAppHelper.create();
        if (customer != null) {
            storage.save(customer, fileName);
            return true;
        }
        return false;
    }

    @Override
    public boolean edit(Customer customer) {
        List<Customer> customers = storage.load(fileName);
        for (int i = 0; i < customers.size(); i++) {
            if (customers.get(i).getFirstName().equals(customer.getFirstName()) &&
                    customers.get(i).getLastName().equals(customer.getLastName())) {
                System.out.println("Введите новые данные для клиента:");
                Customer updatedCustomer = customerAppHelper.create();
                customers.set(i, updatedCustomer);
                storage.saveAll(customers, fileName);
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean remove(Customer customer) {
        List<Customer> customers = storage.load(fileName);
        boolean removed = customers.removeIf(c -> c.getFirstName().equals(customer.getFirstName()) &&
                c.getLastName().equals(customer.getLastName()));
        if (removed) {
            storage.saveAll(customers, fileName);
        }
        return removed;
    }

    @Override
    public boolean print() {
        return customerAppHelper.printList(this.list());
    }

    @Override
    public List<Customer> list() {
        return storage.load(fileName);
    }
}
