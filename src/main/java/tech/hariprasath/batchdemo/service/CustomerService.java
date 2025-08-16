package tech.hariprasath.batchdemo.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import tech.hariprasath.batchdemo.entity.Customer;
import tech.hariprasath.batchdemo.repository.CustomersRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomerService {
    private final CustomersRepository customersRepository;

    public List<Customer> getAllCustomers() {
        return customersRepository.findAll();
    }

    public List<Customer> getCustomers(int page, int size) {
        return customersRepository.findAll(PageRequest.of(page, size)).getContent();
    }

    public List<Customer> getByPartialName(String name) {
        return customersRepository.getCustomerByPartialName(name);
    }

    public List<Customer> getByPartialName(String partialName, int page, int size) {
        return customersRepository.getCustomerByPartialName(partialName, PageRequest.of(page, size));
    }
}
