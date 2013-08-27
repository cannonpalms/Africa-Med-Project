package med.africa.patient;

import java.sql.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import med.africa.hibernate.Persistable;

@Entity
@Table(name="patients")
@org.hibernate.annotations.Table(appliesTo="patients")
public class Patient implements Persistable {

	private long id;
	private String firstName;
	private String middleName;
	private String lastName;
	private Date birthDate;
	
	private Set<Allergy> allergies;
	
	
	public Patient() { }

	public Patient(String firstName, String middleName, String lastName, Date birthDate) {
		this.firstName = firstName;
		this.middleName = middleName;
		this.lastName = lastName;
		this.birthDate = birthDate;
	}
	
	public Patient(String firstName, String lastName, Date birthDate) {
		this(firstName, null, lastName, birthDate);
	}
	@Id
	@GeneratedValue
	@Column(name="patient_id")
	public long getId() {
		return id;
	}

	@Column(name="first_name")
	public String getFirstName() {
		return firstName;
	}

	@Column(name="middle_name")
	public String getMiddleName() {
		return middleName;
	}

	@Column(name="last_name")
	public String getLastName() {
		return lastName;
	}

	@Column(name="dob")
	public Date getBirthDate() {
		return birthDate;
	}
	
	@OneToMany(fetch = FetchType.EAGER)
	@JoinTable(
			name="patient_allergies",
			joinColumns = @JoinColumn(name="patient_id"),
			inverseJoinColumns = @JoinColumn(name="allergy_id")
	)
	public Set<Allergy> getAllergies() {
		return allergies;
	}

	public void setId(long id) {
		this.id = id;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
	}
	
	public void setAllergies(Set<Allergy> allergies) {
		this.allergies = allergies;
	}
	
	public void addAllergy(Allergy toAdd) {
		if (allergies == null) {
			allergies = new HashSet<Allergy>();
		}
		allergies.add(toAdd);
	}
	
	public void removeAllergy(Allergy toRemove) {
		if (allergies == null) {
			return;
		}
		allergies.remove(toRemove);
	}
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		
		sb.append(getId())
			.append(" - ")
			.append(getLastName())
			.append(", ")
			.append(getFirstName());
		
		if (middleName != null) {
			sb.append(" ")
				.append(getMiddleName());
		}
		
		return sb.toString();
	}
	
}
