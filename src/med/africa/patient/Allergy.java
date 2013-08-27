package med.africa.patient;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import med.africa.hibernate.Persistable;

@Entity
@Table(name="allergies")
@org.hibernate.annotations.Table(appliesTo="allergies")
public class Allergy implements Persistable {

	private long allergyID;
	private String allergyName;
	
	public Allergy() { }

	public Allergy(String allergyName) {
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
	
	
}
