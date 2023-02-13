/**
 * 
 */
package com.omf.los.controller;

/** data model for customer information */
public class CustomerInfo {
	
	/** name of the customer */
	private String name;
	
	/** age of the customer */
	private Integer age;
	
	/** contact number of the customer */
	private String contact;

	/** param constructor */
	public CustomerInfo(final String name, final String contact) {
		super();
		this.name = name;
		this.contact = contact;
	}

	/** @return the name */
	public String getName() {
		return name;
	}

	/** @param name the name to set */
	public void setName(final String name) {
		this.name = name;
	}
	
	/** @return the age */
	public Integer getAge() {
		return age;
	}

	/** @param age the age to set */
	public void setAge(final Integer age) {
		this.age = age;
	}

	/** @return the contact */
	public String getContact() {
		return contact;
	}

	/** @param contact the contact to set */
	public void setContact(final String contact) {
		this.contact = contact;
	}

	/** {@inheritDoc} */
	@Override
	public String toString() {
		return "CustomerInfo [name=" + name + ", contact=" + contact + "]";
	}
	
}
