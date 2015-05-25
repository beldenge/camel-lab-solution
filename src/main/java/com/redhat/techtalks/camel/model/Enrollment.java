package com.redhat.techtalks.camel.model;

import java.util.Date;

import org.apache.camel.dataformat.bindy.annotation.CsvRecord;
import org.apache.camel.dataformat.bindy.annotation.DataField;

@CsvRecord(separator = "\\|", crlf = "WINDOWS")
public class Enrollment {
    @DataField(pos = 1)
    private String lastName;

	@DataField(pos = 2)
    private String firstName;

    @DataField(pos = 3, pattern = "yyyy-MM-dd")
    private Date dateOfBirth;

    @DataField(pos = 4)
    private String language;

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public Date getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	@Override
	public String toString() {
		return "Enrollment [firstName=" + firstName + ", lastName=" + lastName
				+ ", dateOfBirth=" + dateOfBirth + ", language=" + language
				+ "]";
	}
}
