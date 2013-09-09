package med.africa.patient;

public enum Gender {
	MALE, FEMALE;
	
	@Override
	public String toString() {
		if (this == MALE) {
			return "male";
		}
		return "female";
	}
}
