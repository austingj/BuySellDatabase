
public class NFT {
	protected String uniqueName;
 	protected String description;
    protected String imageURL;
    protected double price;
    protected String duration;
    protected int NFTID;
	protected String creator;
	
	
	
    public NFT(String uniquename2, String description2, String imageurl2, int nftid2, String creator) {
    	this.uniqueName = uniquename2;
    	this.description = description2;
    	this.imageURL = imageurl2;
    	this.NFTID = nftid2;
    	this.creator = creator;
    }
    
	public NFT(String uniqueName, String description, String imageURL, double price, int NFTID) {
		this.uniqueName = uniqueName;
		this.description = description;
		this.imageURL = imageURL;
		this.price = price;
		this.NFTID = NFTID;
	}
	public NFT(String uniqueName2, String description2, String imageURL2, String creator2) {
		this.uniqueName = uniqueName2;
		this.description = description2;
		this.imageURL = imageURL2;
		this.creator = creator2;
		// TODO Auto-generated constructor stub
	}

	public NFT(String uniquename2, String description2, int nftid2) {
		this.uniqueName = uniquename2;
		this.description = description2;
		this.NFTID = nftid2;
		// TODO Auto-generated constructor stub
	}

	
	public NFT() {
		// TODO Auto-generated constructor stub
	}

	public String getUniqueName() {
		return uniqueName;
	}
	public void setUniqueName(String uniqueName) {
		this.uniqueName = uniqueName;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getImageURL() {
		return imageURL;
	}
	public void setImageURL(String imageURL) {
		this.imageURL = imageURL;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public int getNFTID() {
		return NFTID;
	}
	   public String getCreator() {
			return creator;
		}

		public void setCreator(String creator) {
			this.creator = creator;
		}
    
    
}
