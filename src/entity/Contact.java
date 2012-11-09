package entity;

import java.util.ArrayList;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.google.appengine.api.datastore.Key;
import com.google.gson.annotations.Expose;

@PersistenceCapable(identityType = IdentityType.APPLICATION)
public class Contact {
	@PrimaryKey
	@Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
	private Key key;
	@Expose
	private String name;
	@Expose
	private String mobileNumber;

	public Contact() {
	};

	public Contact(String name, String mobileNumber) {
		this.name = name;
		this.mobileNumber = mobileNumber;
	}

	// public void mapObject(String JSONResponse) {
	// try {
	// JSONObject json = new JSONObject(JSONResponse);
	// this.setName(json.getString("name"));
	// this.setEmail(json.getString("email"));
	// this.setMobileNumber(json.getString("mobileNumber"));
	// this.setEncodedKey(json.getString("encodedKey"));
	//
	// try {
	// this.setDeviceRegId(json.getString("deviceRegId"));
	// } catch (Exception e) {
	// this.setDeviceRegId("");
	// }
	// } catch (Exception e) {
	// Log.e("User mapping", "Mapping exception");
	// }
	// }

	/*
	 * ==========================================================================
	 * Default Getters and Setters ============================================
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
