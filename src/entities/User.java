package entities;

public class User{
	private int uid;
	private String name;
	private int age;
	private String gender;
	private String address;
	private String phoneNo;
	private String district;
	private String state;
	private int pinCode;
	private String userName;
	private String passWord;
	
	public User() {
		super();
	}
	
	public User(String name, String userName, String passWord) {
		super();
		this.name=name;
		this.userName=userName;
		this.passWord=passWord;
	}
	
	public User(int uid, String name, int age, String gender, String address, String phoneNo, String district,
			String state, int pinCode) {
		super();
		this.uid = uid;
		this.name = name;
		this.age = age;
		this.gender = gender;
		this.address = address;
		this.phoneNo = phoneNo;
		this.district = district;
		this.state = state;
		this.pinCode = pinCode;
	}
	
	public int getUid() {
		return uid;
	}
	public void setUid(int uid) {
		this.uid = uid;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getPhoneNo() {
		return phoneNo;
	}
	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
	}
	public String getDistrict() {
		return district;
	}
	public void setDistrict(String district) {
		this.district = district;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public int getPinCode() {
		return pinCode;
	}
	public void setPinCode(int pinCode) {
		this.pinCode = pinCode;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPassWord() {
		return passWord;
	}
	public void setPassWord(String passWord) {
		this.passWord = passWord;
	}

	
	
	
}
