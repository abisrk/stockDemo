package com.payconiq.stockDemo;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.payconiq.stockDemo.dao.StockRepository;
import com.payconiq.stockDemo.dto.Stock;

@SpringBootApplication
public class StockDemoApplication implements CommandLineRunner {

	Logger log = LogManager.getLogger(StockDemoApplication.class.getName());
	
	@Autowired
	StockRepository repository;

	public static void main(String[] args) {
		SpringApplication.run(StockDemoApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		if (repository == null) {
			log.error(" *** System Repository is Un-initialized.Exiting...");
			System.exit(0);
		} else {
			repository.deleteAll();
			repository.save(new Stock("MSFT", 500.35));
			repository.save(new Stock("APPL", 20.19));
			repository.save(new Stock("MAFA", 10.50));
			repository.save(new Stock("SOFA", 89.9));
			repository.save(new Stock("YHOO", 45.3));
			repository.save(new Stock("SAMG", 12.345));
			repository.save(new Stock("ABCD", 89.0));
			repository.save(new Stock("EFGH", 91));
			repository.save(new Stock("IJKL", 675));
			repository.save(new Stock("MNOP", 192));
			log.info(" *** Database initialized with records. Current collections length : " + repository.count());
		}
	}
}
