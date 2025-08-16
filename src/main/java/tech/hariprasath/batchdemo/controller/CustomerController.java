package tech.hariprasath.batchdemo.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import tech.hariprasath.batchdemo.entity.Customer;
import tech.hariprasath.batchdemo.service.CustomerService;

import java.util.List;

@RestController
@RequestMapping("/details")
@RequiredArgsConstructor
public class CustomerController {
    private final CustomerService customerService;

    @GetMapping("get-all-customers")
    public List<Customer> getCustomers() {
        return customerService.getAllCustomers();
    }

    @GetMapping("/get-customers")
    public List<Customer> getCustomers(@RequestParam int page, @RequestParam int size) {
        return customerService.getCustomers(page, size);
    }

    @GetMapping("/search-get-customers")
    public List<Customer> getCustomers(@RequestParam String name) {
        return customerService.getByPartialName(name);
    }

    @GetMapping("/search-get-customers-page")
    public List<Customer> getCustomers(@RequestParam String name,  @RequestParam int page, @RequestParam int size) {
        return customerService.getByPartialName(name,  page, size);
    }

    @GetMapping("/health")
    public String healthCheck() {
        return "healthy";
    }
}
