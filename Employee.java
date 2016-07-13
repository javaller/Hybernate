package employee;

	public class Employee {
	private String firstName;
	private String lastName;
	private int salary;
	private int id;

	public Employee() {}
	
	public Employee( String fName, String lName, int salary) {
		super();
		this.firstName = fName;
		this.lastName = lName;
		this.salary = salary;			
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

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

	public int getSalary() {
		return salary;
	}

	public void setSalary(int salary) {
		this.salary = salary;
	}
}
