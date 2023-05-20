
public class Own {
	public String getUniqueName() {
		return uniqueName;
	}

	public void setUniqueName(String uniqueName) {
		this.uniqueName = uniqueName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Own(String uniqueName, String email) {
		super();
		this.uniqueName = uniqueName;
		this.email = email;
	}

	protected String uniqueName;
 	protected String email;
 	
 	  public Own() {
 	    	
 	    }
 	
}
