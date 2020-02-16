package com.mysql.jpa.operation;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
//import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/***
 * 
 * Spring Boot Main class to start the tomcat inside docker and provide all REST
 * end points Required to scan all packages in order to start properly
 *
 */

@SpringBootApplication
//@EnableDiscoveryClient
public class MysqlApplication {
//public class CalculateServiceApplication implements CommandLineRunner{
	

//    @Autowired
//    DataSource dataSource;
//    @Autowired
//    SystemRepository systemRepository;

	public static void main(String[] args) {
		SpringApplication.run(MysqlApplication.class, args);
	}
	
//    @Override
//    public void run(String... args) throws Exception {
//        System.out.println("Our DataSource is = " + dataSource);
//        Iterable<com.mysql.jpa.operation.model.System> systemlist = systemRepository.findAll();
//        for(com.mysql.jpa.operation.model.System systemmodel:systemlist){
//            System.out.println("Here is a system: " + systemmodel.toString());
//        }
//    }

}
