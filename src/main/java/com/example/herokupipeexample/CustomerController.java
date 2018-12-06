package com.example.herokupipeexample;

import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.web.bind.annotation.*;
import com.codahale.metrics.MetricRegistry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
public class CustomerController {

    private CustomerRepository customerRepository;

    public CustomerController(CustomerRepository customerRepository) {
      this.customerRepository = customerRepository;
    }

    private final MetricRegistry registry = new MetricRegistry();

    private Logger logger = LoggerFactory.getLogger(CustomerController.class);

    @RequestMapping("/")
    public String welcome() {
        registry.meter("welcome").mark();
        logger.info("Testingsss logz.io!");
        logger.warn("Winter is coming!!!!!");
        return "Welcome to this small REST service. It will accept a GET on /list with a request parameter lastName or firstName, and a POST to / with a JSON payload with firstName and lastName as values.";
    }

    @RequestMapping("/list")
    public List<Customer> findByLastName(@RequestParam(value="lastName") String lastName) {
        registry.timer("Getting customers with lastname").time();

        return customerRepository.findByLastName(lastName);
    }

    @RequestMapping("/list")
    public List<Customer> findByFirstName(@RequestParam(value = "firstName") String firstName){
        registry.timer("Getting customers with firstname").time();

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
        registry.counter("Created a Customer").inc();
    		return customerRepository.save(customer);
    	}

}
