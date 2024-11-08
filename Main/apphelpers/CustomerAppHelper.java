package apphelpers;

import interfaces.AppHelper;
import interfaces.Input;
import model.Customer;

import java.util.List;

public class CustomerAppHelper implements AppHelper<Customer>, Input {

    @Override
    public Customer create() {
        System.out.print("Фамилия клиента: ");
        String firstName = getString();
        System.out.print("Имя клиента: ");
        String lastName = getString();
        System.out.print("Номер телефона клиента: ");
        String phone = getString();

        return new Customer(firstName, lastName, phone);
    }

    @Override
    public boolean printList(List<Customer> customers) {
        System.out.println("----- Список клиентов -----");
        for (int i = 0; i < customers.size(); i++) {
            Customer customer = customers.get(i);
            System.out.printf("%d. %s %s, Телефон: %s%n", i + 1, customer.getFirstName(), customer.getLastName(), customer.getPhone());
        }
        return true;
    }
}
