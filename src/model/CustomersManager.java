package model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class CustomersManager implements Serializable {

    private HashMap<Integer, Customer> customers;

    public CustomersManager() {
	customers = new HashMap<Integer, Customer>();
    }

    public boolean contains(int id) {
	return this.customers.containsKey(id);
    }

    public boolean addCustomer(Customer customer) {
	if (!this.customers.containsKey(customer.getId())) {
	    this.customers.put(customer.getId(), customer);
	    return true;
	} else {
	    return false;
	}
    }

    public Customer getCustomerInfo(int id) {
	return this.customers.get(id);
    }

    public String print() {
	StringBuffer buffer = new StringBuffer();
	for (Map.Entry<Integer, Customer> entrySet : customers.entrySet()) {
	    Customer value = entrySet.getValue();
	    buffer.append(value.print() + "\n");
	}
	return buffer.toString().trim();
    }
}
