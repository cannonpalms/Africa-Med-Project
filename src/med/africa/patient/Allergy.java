package med.africa.patient;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="allergies")
@org.hibernate.annotations.Table(appliesTo="allergies")
public class Allergy {

	private long allergyID;
	private String allergyName;
	
	public Allergy() { }

	public Allergy(long allergyID) {
		this.allergyID = allergyID;
	}
	
	public Allergy(String allergyName) {
		this.allergyName = allergyName;
	}
	
	public Allergy(long allergyID, String allergyName) {
		this.allergyID = allergyID;
		this.allergyName = allergyName;
	}
	
	@Id
	@Column(name="allergy_id")
	public long getAllergyID() {
		return allergyID;
	}
	
	@Column(name="allergy_name")
	public String getAllergyName() {
		return allergyName;
	}

	public void setAllergyID(long allergyID) {
		this.allergyID = allergyID;
	}

	public void setAllergyName(String allergyName) {
		this.allergyName = allergyName;
	}
	
	@Override
	public String toString() {
		return allergyName;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (allergyID ^ (allergyID >>> 32));
		result = prime * result
				+ ((allergyName == null) ? 0 : allergyName.hashCode());
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
		Allergy other = (Allergy) obj;
		if (allergyID != other.allergyID)
			return false;
		if (allergyName == null) {
			if (other.allergyName != null)
				return false;
		} else if (!allergyName.equals(other.allergyName))
			return false;
		return true;
	}
}
