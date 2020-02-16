package com.mysql.jpa.operation.controller;

import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.mysql.jpa.operation.dao.SystemRepository;
import com.mysql.jpa.operation.model.CustomErrorType;

@RestController
@RequestMapping("/api")
public class PostPatchGetDeleteController {

	@Autowired
	DataSource dataSource;
	@Autowired
	SystemRepository systemRepository;
	public static final Logger logger = LoggerFactory.getLogger(PostPatchGetDeleteController.class);

//http://localhost:9100/crudapicontext/api/system/1
	@RequestMapping(value = "/system/{id}", method = RequestMethod.GET)
	public ResponseEntity<?> getSystem(@PathVariable("id") long id) {
		logger.info("Fetching User with id {}", id);
		com.mysql.jpa.operation.model.System system = null;
		try {
			system = systemRepository.findById(Long.valueOf(id)).get();
		} catch (Exception e) {
			logger.error("System with id {} not found in catch " + e.getStackTrace(), id);
		}
		if (system == null) {
			logger.error("System with id {} not found.", id);
			return new ResponseEntity(
					new CustomErrorType("0001", "SYSTEM_ID_NOT_FOUND", "System with id " + id + " not found"),
					HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<com.mysql.jpa.operation.model.System>(system, HttpStatus.OK);
	}

	// http://localhost:9100/crudapicontext/api/system/
	/*
	for example
	{
	    "id": 1,
	    "name": "Windows Server 2012 R2",
	    "lastaudit": "2017-08-10T18:30:00.000+0000"
	  },
	  {
	    "id": 2,
	    "name": "RHEL 7",
	    "lastaudit": "2017-07-20T18:30:00.000+0000"
	  },
	  {
	    "id": 3,
	    "name": "Solaris 11",
	    "lastaudit": "2017-08-12T18:30:00.000+0000"
	  }
	  */
	@RequestMapping(value = "/system/", method = RequestMethod.GET)
	public ResponseEntity<List<com.mysql.jpa.operation.model.System>> listAllUsers() {
		List<com.mysql.jpa.operation.model.System> lstSystem = new ArrayList<>();
		Iterable<com.mysql.jpa.operation.model.System> systemlist = systemRepository.findAll();
		for (com.mysql.jpa.operation.model.System systemmodel : systemlist) {
			System.out.println("Here is a system: " + systemmodel.toString());

			lstSystem.add(systemmodel);
		}

		if (lstSystem.isEmpty()) {
			return new ResponseEntity(HttpStatus.NO_CONTENT);
			// You many decide to return HttpStatus.NOT_FOUND
		}
		return new ResponseEntity<List<com.mysql.jpa.operation.model.System>>(lstSystem, HttpStatus.OK);
	}

	// http://localhost:9100/crudapicontext/api/system/
	/*
	 * { "name": "Solaris 11", "lastaudit": "2017-08-12T18:30:00.000+0000" }
	 */
	@RequestMapping(value = "/system/", method = RequestMethod.POST)
	public ResponseEntity<?> createUser(@RequestBody com.mysql.jpa.operation.model.System system,
			UriComponentsBuilder ucBuilder) {
		logger.info("Creating system : {}", system);
		com.mysql.jpa.operation.model.System aSystem = null;
		try {
			aSystem = systemRepository.findById(Long.valueOf(system.getId() + "")).get();
		} catch (Exception e) {
			logger.error("System with id {} not found in catch in POST " + e.getStackTrace(), system.getId());
		}
		if (aSystem != null) {
			logger.error("Unable to create. A System with ID {} already exist", system.getId());
			return new ResponseEntity(new CustomErrorType("0002", "SYSTEM_ID_ALREADY_PRESENT_NOT_CREATED",
					"Unable to create. A System with ID " + " already exist."), HttpStatus.CONFLICT);
		}
		systemRepository.save(system);

		HttpHeaders headers = new HttpHeaders();
		headers.setLocation(ucBuilder.path("/api/system/{id}").buildAndExpand(system.getId()).toUri());
		// return new ResponseEntity<String>(headers, HttpStatus.CREATED);
		return new ResponseEntity<com.mysql.jpa.operation.model.System>(system, headers, HttpStatus.CREATED);
	}

	// http://localhost:9100/crudapicontext/api/system/8
	/*
	 * { "name": "Solaris 111", "lastaudit": "2017-08-12T18:30:00.000+0000" }
	 */
	@RequestMapping(value = "/system/{id}", method = RequestMethod.PUT)
	public ResponseEntity<?> updateUser(@PathVariable("id") long id,
			@RequestBody com.mysql.jpa.operation.model.System system) {
		logger.info("Updating System with id {}", id);
		com.mysql.jpa.operation.model.System aSystem = null;
		com.mysql.jpa.operation.model.System currentSystem = new com.mysql.jpa.operation.model.System();
		try {
			aSystem = systemRepository.findById(id).get();
		} catch (Exception e) {
			logger.error("System with id {} not found in catch in POST " + e.getStackTrace(), id);
		}

		if (aSystem == null) {
			logger.error("Unable to update. System with id {} not found.", id);
			return new ResponseEntity(new CustomErrorType("0003", "SYSTEM_ID_NOT_PRESENT_NOT_BE_UPDATED",
					"Unable to upate. User with id " + id + " not found."), HttpStatus.NOT_FOUND);
		}

		aSystem.setName(system.getName());
		aSystem.setLastaudit(system.getLastaudit());

		systemRepository.save(aSystem);
		return new ResponseEntity<com.mysql.jpa.operation.model.System>(aSystem, HttpStatus.OK);
	}

	// http://localhost:9100/crudapicontext/api/system/8
	@RequestMapping(value = "/system/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<?> deleteUser(@PathVariable("id") long id) {
		logger.info("Fetching & Deleting User with id {}", id);

		com.mysql.jpa.operation.model.System aSystem = null;
		com.mysql.jpa.operation.model.System currentSystem = new com.mysql.jpa.operation.model.System();
		try {
			aSystem = systemRepository.findById(Long.valueOf(id + "")).get();
		} catch (Exception e) {
			logger.error("System with id {} not found in catch in POST " + e.getStackTrace(), id);
		}
		if (aSystem == null) {
			logger.error("Unable to delete. System with id {} not found.", id);
			return new ResponseEntity(new CustomErrorType("0004", "SYSTEM_ID_NOT_PRESENT_NOT_BE_UPDATED",
					"Unable to delete. User with id " + id + " not found."), HttpStatus.NOT_FOUND);
		}
		systemRepository.deleteById(id);
		return new ResponseEntity<com.mysql.jpa.operation.model.System>(HttpStatus.NO_CONTENT);
	}

	// http://localhost:9100/crudapicontext/api/system/

	@RequestMapping(value = "/system/", method = RequestMethod.DELETE)
	public ResponseEntity<com.mysql.jpa.operation.model.System> deleteAllUsers() {
		logger.info("Deleting All Systems");

		systemRepository.deleteAll();
		return new ResponseEntity<com.mysql.jpa.operation.model.System>(HttpStatus.NO_CONTENT);
	}
}
