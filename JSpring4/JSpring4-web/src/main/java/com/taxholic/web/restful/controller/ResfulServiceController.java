package com.taxholic.web.restful.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.taxholic.web.restful.vo.Address;
import com.taxholic.web.restful.vo.Company;
import com.taxholic.web.restful.vo.Person;

@RestController
@RequestMapping("/rest/*")
public class ResfulServiceController{
	
	@RequestMapping(value= "/json/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public Person getForObjectJson(
			@PathVariable(value = "id") Integer id , 	
			@RequestParam(value = "name", required = false) String name
	) {
		
		Address address = new Address("신림","관악", "서울");
		
		return new Person(id,name, address);
	} 

	@RequestMapping(value= "/json/{id}/{name}",  method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Person> getForPostObjectJson(
			@PathVariable(value = "id") Integer id,
			@PathVariable(value = "name") String name,
			@RequestBody Address address
	) {
		
		Person person = new Person(id, name, address);
		
		return new ResponseEntity<Person>(person, HttpStatus.CREATED);
	} 
	
	
	@RequestMapping(value= "/xml/{id}", produces = MediaType.APPLICATION_XML_VALUE)
	public Company getForObjectXML(@PathVariable(value = "id") Integer id) {
		
		Company comp = new Company();
		comp.setId(id);
		comp.setCompanyName("XYZ");
		comp.setCeoName("ABCD");
		comp.setNoEmp(100);
		
		return comp;
	}	
	
}