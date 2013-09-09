package med.africa.patient;

import java.sql.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name="patients")
@org.hibernate.annotations.Table(appliesTo="patients")
public class Patient {

	
	private long id;
	private String firstName;
	private String middleName;
	private String lastName;
	private Date birthDate;
	private Gender gender;
	
	private Set<Allergy> allergies;
	
	
	protected Patient() { }

	protected Patient(String firstName, String middleName, String lastName, Date birthDate, Gender gender) {
		this.firstName = firstName;
		this.middleName = middleName;
		this.lastName = lastName;
		this.birthDate = birthDate;
		this.gender = gender;
	}
	
	protected Patient(String firstName, String lastName, Date birthDate, Gender gender) {
		this(firstName, null, lastName, birthDate, gender);
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

	@OneToOne(fetch = FetchType.LAZY)
	@Column(name="dob")
	public Date getBirthDate() {
		return birthDate;
	}
	
	@OneToMany(fetch = FetchType.LAZY)
	@JoinTable(
			name="patient_allergies",
			joinColumns = @JoinColumn(name="patient_id"),
			inverseJoinColumns = @JoinColumn(name="allergy_id")
	)
	public Set<Allergy> getAllergies() {
		return allergies;
	}
	
	@OneToOne(fetch = FetchType.LAZY)
	@Column(name="gender")
	@Enumerated(EnumType.STRING)
	public Gender getGender() {
		return gender;
	}
	
	public void setGender(Gender gender) {
		this.gender = gender;
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
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((allergies == null) ? 0 : allergies.hashCode());
		result = prime * result
				+ ((birthDate == null) ? 0 : birthDate.hashCode());
		result = prime * result
				+ ((firstName == null) ? 0 : firstName.hashCode());
		result = prime * result + ((gender == null) ? 0 : gender.hashCode());
		result = prime * result + (int) (id ^ (id >>> 32));
		result = prime * result
				+ ((lastName == null) ? 0 : lastName.hashCode());
		result = prime * result
				+ ((middleName == null) ? 0 : middleName.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Patient other = (Patient) obj;
		if (allergies == null) {
			if (other.allergies != null)
				return false;
		} else if (!allergies.equals(other.allergies))
			return false;
		if (birthDate == null) {
			if (other.birthDate != null)
				return false;
		} else if (!birthDate.equals(other.birthDate))
			return false;
		if (firstName == null) {
			if (other.firstName != null)
				return false;
		} else if (!firstName.equals(other.firstName))
			return false;
		if (gender != other.gender)
			return false;
		if (id != other.id)
			return false;
		if (lastName == null) {
			if (other.lastName != null)
				return false;
		} else if (!lastName.equals(other.lastName))
			return false;
		if (middleName == null) {
			if (other.middleName != null)
				return false;
		} else if (!middleName.equals(other.middleName))
			return false;
		return true;
	}

	@Override
	public String toString() {
		String str = String.format("[Patient #%d:"
							+ " last: %s,"
							+ " first: %s,"
							+ " middle: %s,"
							+ " gender: %s"
							+ " dob: %s,]",
						id, lastName, firstName, middleName, gender, birthDate);
					
		return str;
	}
	
	static class Builder {

		private Long id;
		private String firstName;
		private String middleName;
		private String lastName;
		private Date birthDate;
		private Gender gender;
		private Set<Allergy> allergies;
		
		public Builder setID(Long id) {
			this.id = id;
			return this;
		}
		public Builder setFirstName(String firstName) {
			this.firstName = firstName;
			return this;
		}
		
		//this value CAN be null
		public Builder setMiddleName(String middleName) {
			this.middleName = middleName;
			return this;
		}
		
		public Builder setLastName(String lastName) {
			this.lastName = lastName;
			return this;
		}
		
		public Builder setBirthDate(Date birthDate) {
			this.birthDate = birthDate;
			return this;
		}
		
		public Builder setGender(Gender gender) {
			this.gender = gender;
			return this;
		}
		
		public Builder setAllergies(Set<Allergy> allergies) {
			this.allergies = allergies;
			return this;
		}
		
		public Builder addAllergy(Allergy allergy) {
			if (allergies == null) {
				allergies = new HashSet<Allergy>();
			}
			allergies.add(allergy);
			
			return this;
		}
		
		public Patient build() {
			checkForNull(); //check all fields (except middleName) for null references
			Patient p = new Patient(
					firstName,
					middleName, //this value CAN be null
					lastName,
					birthDate,
					gender);
			
			return p;
		}
		
		//Middle name is only nullable field
		private void checkForNull() {
			if (id == null)
				throwStateExceptionForField("Patient ID");
			else if (firstName == null)
				throwStateExceptionForField("First name");
			else if (lastName == null)
				throwStateExceptionForField("Last name");
			else if (birthDate == null)
				throwStateExceptionForField("Birth date");
			else if (gender == null)
				throwStateExceptionForField("Gender");
		}
		
		private void throwStateExceptionForField(String field) {
			throw new IllegalStateException(field + " cannot be null.");
		}
	}
}