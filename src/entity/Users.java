package entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.jdo.PersistenceManager;
import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.NotPersistent;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.Text;
import com.google.gson.annotations.Expose;

import dataManagers.PMF;

@PersistenceCapable(identityType = IdentityType.APPLICATION)
public class Users implements Serializable {
	@PrimaryKey
	@Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
	private Key key;
	@Persistent
	@Expose
	private String username;
	@Persistent
	@Expose
	private String deviceRegId;
	@Persistent
	@Expose
	private String password;
	@Persistent
	@Expose
	private int priv;
	@Persistent
	@Expose
	private String name;
	@Persistent
	@Expose
	private String dob;
	@Persistent
	@Expose
	private String access_token;
	@Persistent
	@Expose
	private String uid;
	@Persistent
	@Expose
	private String email;
	@Persistent
	@Expose
	private String mobileNumber;
	@Persistent
	@Expose
	private Text artistInfo;
	@Persistent
	private Set<Key> groupSet = new HashSet<Key>();
	@Expose
	private ArrayList<Group> groupList;
	@Expose
	private String encodedKey;
	@Persistent(mappedBy = "users") 
	@Expose
	private List<UserEvent> userEvents = new ArrayList<UserEvent>();
	
	public List<UserEvent> getUserEvents() {
		return userEvents;
	}

	public void setUserEvents(List<UserEvent> userEvents) {
		this.userEvents = userEvents;
	}
	
	public void addUserEvents(UserEvent event) {
		userEvents.add(event);
	}

	public String getEncodedKey() {
		return encodedKey;
	}
	
	public void setEncodedKey() {
		encodedKey = KeyFactory.keyToString(key);
	}
	
	public ArrayList<Group> getGroupList() {
		return groupList;
	}

	public void setGroupList(ArrayList<Group> groupList) {
		this.groupList = groupList;
	}

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

	public void addGroup(Group group) {
		groupSet.add(group.getKey());
		group.getUserSet().add(getKey());
	}

	public void removeGroup(Group group) {
		groupSet.remove(group.getKey());
		group.getUserSet().remove(getKey());
	}

	// public String getKeyName() {
	// return keyName;
	// }
	//
	// public void setKeyName(String keyName) {
	// this.keyName = keyName;
	// }

	// public String getEncodedKey() {
	// return encodedKey;
	// }
	//
	// public void setEncodedKey(String encodedKey) {
	// this.encodedKey = encodedKey;
	// }

	public Key getKey() {
		return key;
	}

	public void setKey(Key key) {
		this.key = key;
	}

	public Users() {
		groupList = new ArrayList<Group>();
	}

	public Users(String name, String password, String email, String mobileNumber) {
		this.name = name;
		this.password = password;
		this.email = email;
		this.mobileNumber = mobileNumber;
	}

	public Set<Key> getGroupSet() {
		return groupSet;
	}

	public void setGroupSet(Set<Key> groupSet) {
		this.groupSet = groupSet;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getEmail() {
		return email;
	}

	public String getDeviceRegId() {
		return deviceRegId;
	}

	public void setDeviceRegId(String deviceRegId) {
		this.deviceRegId = deviceRegId;
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
