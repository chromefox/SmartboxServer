package entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.NotPersistent;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.gson.annotations.Expose;

import dataManagers.UserDM;

@PersistenceCapable(identityType = IdentityType.APPLICATION)
public class Group implements Serializable {
	@PrimaryKey
	@Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
	private Key key;
	@Persistent
	@Expose
	private String groupName;
	@Persistent
	@Expose
	private String className;
	@Persistent
	@Expose
	private int sectionNumber;
	@Persistent
	@Expose
	private String userEmail;
	@NotPersistent
	@Expose
	private ArrayList<Contact> contacts;
	@Persistent
	private Set<Key> userSet = new HashSet<Key>();
	@Expose
	private String encodedKey;
	@Persistent(mappedBy = "group")
	@Expose
	private List<ChatMessage> messages = new ArrayList<ChatMessage>();
	@NotPersistent
	@Expose
	private ArrayList<String> memberNames = new ArrayList<String>();

	public String getUserDevices() {
		StringBuilder sb = new StringBuilder();
		Iterator<Key> iter = userSet.iterator();
		while (iter.hasNext()) {
			Key key = (Key) iter.next();
			Users temp = UserDM.retrieveUserWithKey(key);
			sb.append(temp.getDeviceRegId());
			if (iter.hasNext()) sb.append(";");
		}
		return sb.toString();
	}

	public void setEncodedKey() {
		encodedKey = KeyFactory.keyToString(key);
	}

	public void setMemberName() {
		// iterate through userset
	}

	public void addMessage(ChatMessage message) {
		messages.add(message);
	}

	public ArrayList<String> getMemberNames() {
		return memberNames;
	}

	public void setMemberNames(ArrayList<String> memberNames) {
		this.memberNames = memberNames;
	}

	public List<ChatMessage> getMessages() {
		for(ChatMessage msg : messages) msg.getMessage();
		return messages;
	}

	public void setMessages(List<ChatMessage> messages) {
		this.messages = messages;
	}

	public Group() {

	}

	public Group(String groupName, String className, int sectionNumber) {
		super();
		this.groupName = groupName;
		this.className = className;
		this.sectionNumber = sectionNumber;
	}

	public void addUser(Users user) {
		if (userSet == null)
			userSet = new HashSet<Key>();
		if (user.getGroupSet() == null)
			user.setGroupSet(new HashSet<Key>());
		userSet.add(user.getKey());
		user.getGroupSet().add(getKey());
		// Persist changes in key in User
		UserDM.persist(user);
	}

	public void removeUser(Users user) {
		userSet.remove(user.getKey());
		user.getGroupSet().remove(getKey());
		// Persist changes in key in User
		UserDM.persist(user);
	}

	public Set<Key> getUserSet() {
		return userSet;
	}

	public void setUserSet(Set<Key> userSet) {
		this.userSet = userSet;
	}

	public String getUserEmail() {
		return userEmail;
	}

	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}

	public ArrayList<Contact> getContacts() {
		return contacts;
	}

	public void setContacts(ArrayList<Contact> contacts) {
		this.contacts = contacts;
	}

	public Key getKey() {
		return key;
	}

	public void setKey(Key key) {
		this.key = key;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public int getSectionNumber() {
		return sectionNumber;
	}

	public void setSectionNumber(int sectionNumber) {
		this.sectionNumber = sectionNumber;
	}

}
