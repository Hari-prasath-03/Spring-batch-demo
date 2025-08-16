package tech.hariprasath.batchdemo.config.batch;

import org.springframework.batch.item.ItemProcessor;
import tech.hariprasath.batchdemo.entity.Customer;

public class CustomerProcessor implements ItemProcessor<Customer, Customer> {

    @Override
    public Customer process(Customer customer) throws Exception {

        customer.setFirstName(customer.getFirstName().toUpperCase());
        customer.setLastName(customer.getLastName().toUpperCase());

        return customer;
    }
}
