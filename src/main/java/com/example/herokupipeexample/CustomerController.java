package com.example.herokupipeexample;

import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.web.bind.annotation.*;

@RestController
public class CustomerController {

    private CustomerRepository customerRepository;

    public CustomerController(CustomerRepository customerRepository) {
      this.customerRepository = customerRepository;
    }

    @RequestMapping("/")
    public String welcome() {
        return "Welcome to this small REST service. It will accept a GET on /list with a request parameter lastName or firstName, and a POST to / with a JSON payload with firstName and lastName as values.";
    }

    @RequestMapping("/list")
    public List<Customer> findByLastName(@RequestParam(value="lastName") String lastName) {
        return customerRepository.findByLastName(lastName);
    }

    @RequestMapping("/list")
    public List<Customer> findByFirstName(@RequestParam(value = "firstName") String firstName){
        return customerRepository.findByFirstName(firstName);
    }

    @DeleteMapping(path = "/list/{id}")
    public boolean deleteCustomer(@PathVariable("id") Long id){
        try{
            customerRepository.deleteById(id);
            return true;
        } catch(Exception e){
            return false;
        }
    }

    @PostMapping("/")
    	Customer newCustomer(@RequestBody Customer customer) {
        System.out.println(customer);
    		return customerRepository.save(customer);
    	}

}
