package com.amigoscode;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@SpringBootApplication // without this annotation app will not start
//@ComponentScan(basePackages = "com.amigoscode")
//@EnableAutoConfiguration
//@Configuration
@RestController
@RequestMapping("api/v1/customers")
public class Main {
  private final CustomerRepository customerRepository;

  public Main(CustomerRepository customerRepository) {
    this.customerRepository = customerRepository;
  }

  public static void main(String[] args) {
    SpringApplication.run(Main.class, args);
    System.out.println("Hello");
  }
  @GetMapping
  public List<Customer> getCustomers(){
    return customerRepository.findAll();
  }
  @PostMapping
  public void addCustomer(@RequestBody NewCustomerRequest request){
    Customer customer = new Customer();
    customer.setName(request.name);
    customer.setEmail(request.email);
    customer.setAge(request.age);
    customerRepository.save(customer);
  }
  record NewCustomerRequest(String name, String email, Integer age){}

  @DeleteMapping("{customerId}")
  public void deleteCustomer(@PathVariable("customerId") Integer id){
    customerRepository.deleteById(id);
  }

  @PutMapping("{customerId}")
  public void updateCustomer(@PathVariable("customerId") Integer id, @RequestBody NewCustomerRequest request){
    Customer customer = customerRepository.getReferenceById(id);
    customer.setName(request.name);
    customer.setEmail(request.email);
    customer.setAge(request.age);
    customerRepository.save(customer);
  }

  //*******************************************************************

  @GetMapping("/greet")
  public GreetResponse greet(){
    GreetResponse response = new GreetResponse(
      "Hello",
      List.of("Java, Golang", "Javascritp"),
      new Person("Alex", 24, 900_000)
    );
    return response;
  }

  record Person(String name, int age, double savings){}

  record GreetResponse(String greet, List<String> favProgrammingLanguages, Person person){}
  //class GreetResponse{
  //  private final String greet;
  //  public GreetResponse(String greet) {
  //    this.greet = greet;
  //  }
  //  public String getGreet() {
  //    return greet;
  //  }
  //  @Override
  //  public String toString() {
  //    return "GreetResponse{" +
  //      "greet='" + greet + '\'' +
  //      '}';
  //  }
  //
  //  @Override
  //  public boolean equals(Object o) {
  //    if (this == o) return true;
  //    if (o == null || getClass() != o.getClass()) return false;
  //    GreetResponse that = (GreetResponse) o;
  //    return Objects.equals(greet, that.greet);
  //  }
  //
  //  @Override
  //  public int hashCode() {
  //    return Objects.hash(greet);
  //  }
  //}

}


