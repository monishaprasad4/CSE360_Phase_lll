public class VisitDetails {

	private String healthConcerns;
	private String physicalHealthFindings;
	private String prescriptions;
	public Vitals vitals;

	public Vitals getVitals() {
		return vitals;
	}
	
	public void setVitals(Vitals vitals) {
		this.vitals = vitals;
	}
	
	public String getHealthConcerns() {
		return healthConcerns;
	}

	public void setHealthConcerns(String concerns) {
		this.healthConcerns = concerns;
	}

	public String getPhysicalHealthFindings() {
		return physicalHealthFindings;
	}

	public void setPhysicalHealthFindings(String value) {
		this.physicalHealthFindings = value;
	}

	public String getPrescriptions() {
		return prescriptions;
	}

	public void setPrescriptions(String prescriptions) {
		this.prescriptions = prescriptions;
	}
}
