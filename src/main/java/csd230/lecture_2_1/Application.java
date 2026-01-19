package csd230.lecture_2_1;

import com.github.javafaker.Commerce;
import com.github.javafaker.Faker;
import csd230.lecture_2_1.Entities.Product;
import csd230.lecture_2_1.Repositories.ProductRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;
import java.util.Optional;

@SpringBootApplication
public class Application implements CommandLineRunner {
    private final ProductRepository productRepository;

    public Application(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

    @Override
    public void run (String... args) throws Exception {
        Faker faker = new Faker();
        Commerce cm = faker.commerce();
        com.github.javafaker.Number number = faker.number();
        com.github.javafaker.Book fakeBook = faker.book();
        String name=cm.productName();
        String description=cm.material();
        Product newProduct = new Product(name, description, number.randomDouble(2,10,100));
        name=cm.productName();
        description=cm.material();
        Product newProduct2 = new Product(name, description, number.randomDouble(2,10,100));
        name=cm.productName();
        description=cm.material();
        Product newProduct3 = new Product(name, description, number.randomDouble(2,10,100));
        name=cm.productName();
        description=cm.material();
        Product newProduct4 = new Product(name, description, number.randomDouble(2,10,100));
//     Product newProduct = new Product("Laptop", "High performance laptop", 1200.00);
//     Product newProduct2 = new Product("Laptop", "Low performance laptop", 1000.00);
        productRepository.save(newProduct);
        productRepository.save(newProduct2);
        productRepository.save(newProduct3);
        productRepository.save(newProduct4);

        List<Product> allProducts = productRepository.findAll();

        Optional<Product> productOptional = productRepository.findById(1L);
        if(productOptional.isPresent()){
            Product product = productOptional.get();
            // Do something with the product.
            System.out.println(product);
        }

//     productOptional = Optional.ofNullable(productRepository.findFirstByName("Laptop"));
        productOptional = Optional.ofNullable(productRepository.findFirstByName(newProduct.getName()));
        if(productOptional.isPresent()){
            Product product = productOptional.get();
            // Do something with the product.
            System.out.println(product);
        }

        List<Product> laptops = productRepository.findAllByName(newProduct2.getName());
        laptops.forEach(System.out::println);

//     Product newProduct3 = new Product("ssd", "100GB drive", 200.00);
//     productRepository.save(newProduct3);

        allProducts = productRepository.findAll();
        allProducts.forEach(System.out::println);

        productRepository.deleteById(1L);

        allProducts = productRepository.findAll();
        allProducts.forEach(System.out::println);

    }

}
