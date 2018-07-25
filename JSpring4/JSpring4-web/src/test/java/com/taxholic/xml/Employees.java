package com.taxholic.xml;

import java.util.List;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

@JacksonXmlRootElement(localName = "employees",namespace ="http://test.org/person" )
public  class Employees {
	
    @JacksonXmlElementWrapper(localName = "employee", useWrapping = false)
    private List<Employee> employee;
    
    public Employees(List<Employee> employee) {
    	this.employee = employee;
    }

	public List<Employee> getEmployee() {
		return employee;
	}

	public void setEmployee(List<Employee> employee) {
		this.employee = employee;
	}
    
}