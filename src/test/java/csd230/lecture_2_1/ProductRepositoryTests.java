package csd230.lecture_2_1;

import com.github.javafaker.Commerce;
import com.github.javafaker.Faker;
import csd230.lecture_2_1.Entities.Product;
import csd230.lecture_2_1.Repositories.ProductRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE) // use application db (mysql) not default h2 embedded db
@Transactional(propagation = Propagation.NOT_SUPPORTED)// dont rollback so you can see data in the db

class ProductRepositoryTests {
    @Autowired
    private ProductRepository productRepository;
    @Test
    void testSave() {
        Faker faker = new Faker();
        Commerce cm = faker.commerce();
        com.github.javafaker.Number number = faker.number();
        com.github.javafaker.Book fakeBook = faker.book();
        String name=cm.productName();
        String description=cm.material();
        Product newProduct = new Product(name, description, number.randomDouble(2,10,100));

        //testEM.persistAndFlush(b1); the same
        productRepository.save(newProduct);

        Long savedProductID = newProduct.getId();

        Product product = productRepository.findById(savedProductID).orElseThrow();
        // Product book = testEM.find(Product.class, savedProductID);

        assertEquals(savedProductID, product.getId());
    }

    @Test
    void findFirstByName() {
        Faker faker = new Faker();
        Commerce cm = faker.commerce();
        com.github.javafaker.Number number1 = faker.number();
        com.github.javafaker.Number number2 = faker.number();
        com.github.javafaker.Book fakeBook = faker.book();
        String name1=cm.productName();
        String name2=name1;
        String description1=cm.material();
        String description2=cm.material();
        Product newProduct = new Product(name1, description1, number1.randomDouble(2,10,100));
        Product newProduct2 = new Product(name2, description2, number2.randomDouble(2,10,100));
        Product newProduct3 = new Product("Unique Name", description2, number2.randomDouble(2,10,100));

        productRepository.save(newProduct);
        productRepository.save(newProduct2);
        productRepository.save(newProduct3);

        Product foundProduct = productRepository.findFirstByName(name1);
        assertTrue(foundProduct.getName().equals(newProduct.getName()));
        assertTrue(foundProduct.getDescription().equals(newProduct.getDescription()));
        assertTrue(foundProduct.getPrice()==newProduct.getPrice());
    }

    @Test
    void findAllByName() {
        Faker faker = new Faker();
        Commerce cm = faker.commerce();
        com.github.javafaker.Number number1 = faker.number();
        com.github.javafaker.Number number2 = faker.number();
        com.github.javafaker.Book fakeBook = faker.book();
        String name1=cm.productName();
        String name2=name1;
        String name3=name1;
        String name4="UniqueName";
        String description1=cm.material();
        String description2=cm.material();
        Product newProduct = new Product(name1, description1, number1.randomDouble(2,10,100));
        Product newProduct2 = new Product(name2, description2, number2.randomDouble(2,10,100));
        Product newProduct3 = new Product(name3, description2, number2.randomDouble(2,10,100));
        Product newProduct4 = new Product(name4, description2, number2.randomDouble(2,10,100));

        productRepository.save(newProduct);
        productRepository.save(newProduct2);
        productRepository.save(newProduct3);
        productRepository.save(newProduct4);

        List<Product> foundProducts = productRepository.findAllByName(name1);
        assertEquals(3, foundProducts.size());
        assertTrue(foundProducts.get(0).getName().equals(name1));
        assertTrue(foundProducts.get(1).getName().equals(name2));
        assertTrue(foundProducts.get(2).getName().equals(name3));
    }

}
