public class Hospital {

	private String name;
	private ITService itService;

	public Hospital() {
		name = "?";
		itService = null;
	}
	
	public String getName() {
		return name;
	}

	public ITService getITService() {
		return itService;
	}

}
