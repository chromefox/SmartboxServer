package entity;

public class Contact{
	private String name;
	private String mobileNumber;

	public Contact() {
	};
	
	public Contact(String name, String mobileNumber) {
		this.name = name;
		this.mobileNumber = mobileNumber;
	}

//	public void mapObject(String JSONResponse) {
//		try {
//			JSONObject json = new JSONObject(JSONResponse);
//			this.setName(json.getString("name"));
//			this.setEmail(json.getString("email"));
//			this.setMobileNumber(json.getString("mobileNumber"));
//			this.setEncodedKey(json.getString("encodedKey"));
//
//			try {
//				this.setDeviceRegId(json.getString("deviceRegId"));
//			} catch (Exception e) {
//				this.setDeviceRegId("");
//			}
//		} catch (Exception e) {
//			Log.e("User mapping", "Mapping exception");
//		}
//	}

	/*
	 * ==========================================================================
	 * Default Getters and Setters
	 * ============================================
	 */
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getMobileNumber() {
		return mobileNumber;
	}

	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}
}
