package entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Set;

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

@PersistenceCapable(identityType = IdentityType.APPLICATION)
public class ChatMessage implements Serializable {
	@PrimaryKey
	@Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
	private Key key;
	
	@Persistent
    private Group group;
	
	@Persistent
	@Expose
	private String message;
	
	public ChatMessage() { }
	
	public ChatMessage(String message) {
		this.message = message;
	}
	
	public Key getKey() {
		return key;
	}


	public void setKey(Key key) {
		this.key = key;
	}


	public Group getGroup() {
		return group;
	}


	public void setGroup(Group group) {
		this.group = group;
	}


	public String getMessage() {
		return message;
	}


	public void setMessage(String message) {
		this.message = message;
	}



}
