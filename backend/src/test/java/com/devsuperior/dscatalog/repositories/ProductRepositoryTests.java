package com.devsuperior.dscatalog.repositories;

import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.EmptyResultDataAccessException;

import com.devsuperior.dscatalog.entities.Product;
import com.devsuperior.dscatalog.services.ProductService;
import com.devsuperior.dscatalog.tests.Factory;


@DataJpaTest
public class ProductRepositoryTests {

	private long existingId; 
	private long nonExistinfId;
	private long countTotalProducts;
	
	@BeforeEach
	void setUp() throws Exception{
		existingId = 1L;
		nonExistinfId = 1000L;		
		countTotalProducts = 25;
	}
	
	@Autowired
	private ProductRepository repository;
	
	@Test
	public void deleteShouldDeleteObjectWhenIdExists() {
		
		repository.deleteById(existingId);
		
		Optional<Product> result= repository.findById(existingId);
		
		Assertions.assertFalse(result.isPresent());
		
	}
	
	@Test
	public void deleteShouldThrowEmptyResultDataAccessExceptionWhenIdDoesNotExist() {
		
		Assertions.assertThrows(EmptyResultDataAccessException.class, ()->{
			
			repository.deleteById(nonExistinfId);
		});
		
	}
	
	@Test
	public void saveShouldPersistWithAutoincrementWhenIdIsNull() {
		
		Product product = Factory.createProduct();
		product.setId(null);
		
		product = repository.save(product);
		
		Assertions.assertNotNull(product.getId());
		
		Assertions.assertEquals(countTotalProducts + 1, product.getId());
		
	}
	
	@Test 
	public void findByIdShouldReturnedNonExistinOptionalWhenIdExists() {
		
		Optional<Product> result =  repository.findById(existingId);
		Assertions.assertTrue(result.isPresent());
	
	}

	@Test 
	public void findByIdShouldReturnedEmptyOptionalWhenIdDoesNotExists() {
		
		Optional<Product> result =  repository.findById(nonExistinfId);
		Assertions.assertTrue(result.isEmpty());
	
	}
	
}
