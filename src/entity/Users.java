package entity;

import java.io.Serializable;
import java.util.List;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.google.appengine.api.datastore.Text;

@PersistenceCapable(identityType = IdentityType.APPLICATION)
public class Users implements Serializable {
	@PrimaryKey
	@Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
	private Long id;

	@Persistent
	private String username;
	@Persistent
	private String password;
	@Persistent
	private int priv;
	@Persistent
	private String name;
	@Persistent
	private String dob;
	@Persistent
	private String access_token;
	@Persistent
	private String uid;
	@Persistent
	private String email;
	@Persistent
	private String mobileNumber;
	@Persistent
	private Text artistInfo;
	@Persistent(mappedBy = "users")
	private List<Group> groupSet;

	public Users(String username, String password, String name, String dob,
			int priv, String access_token, String uid) {
		this.username = username;
		this.password = password;
		this.priv = priv;
		this.name = name;
		this.dob = dob;
		this.access_token = access_token;
		this.uid = uid;
		this.artistInfo = null;
	}

	public Users(String name, String password, String email, String mobileNumber) {
		this.name = name;
		this.password = password;
		this.email = email;
		this.mobileNumber = mobileNumber;
	}

	public List<Group> getGroupSet() {
		return groupSet;
	}

	public void setGroupSet(List<Group> groupSet) {
		this.groupSet = groupSet;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getEmail() {
		return email;
	}

	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}

	public String getMobileNumber() {
		return mobileNumber;
	}

	public void setArtistInfo(String artistInfo) {
		this.artistInfo = new Text(artistInfo);
	}

	public String getArtistInfo() {
		return artistInfo.getValue();
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public String getUid() {
		return uid;
	}

	public void setUser(String username) {
		this.username = username;
	}

	public String getUser() {
		return username;
	}

	public void setAccessToken(String access_token) {
		this.access_token = access_token;
		;
	}

	public String getAccessToken() {
		return access_token;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setDob(String dob) {
		this.dob = dob;
	}

	public String getDob() {
		return dob;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPassword() {
		return password;
	}

	public void setPriv(int priv) {
		this.priv = priv;
	}

	public int getPriv() {
		return priv;
	}
}
