package gmbh.gil;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class WicketConnection {

	@Id
	private String UserSessionID;
	private String loginName;
	private int age;
	private int zipCode;
	private String city;
	private String street;
	private int houseNumber;
	
	
	public String getUserSessionID() {
		return UserSessionID;
	}
	public void setUserSessionID(String userSessionID) {
		UserSessionID = userSessionID;
	}
	
	public String getLoginName() {
		return loginName;
	}
	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}
	
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	
	public int getZipCode() {
		return zipCode;
	}
	public void setZipCode(int zipCode) {
		this.zipCode = zipCode;
	}
	
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	
	public String getStreet() {
		return street;
	}
	public void setStreet(String street) {
		this.street = street;
	}
	
	public int getHouseNumber() {
		return houseNumber;
	}
	public void setHouseNumber(int houseNumber) {
		this.houseNumber = houseNumber;
	}
	
	@Override
	public String toString() {
		return "Wicketconnection [loginName=" + loginName + ", age=" + age + ", zipCode=" + zipCode + ", city=" + city
				+ ", street=" + street + ", houseNumber=" + houseNumber + "]";
	}
	
}
