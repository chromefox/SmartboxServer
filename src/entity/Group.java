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
public class Group implements Serializable {
	@PrimaryKey
	@Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
	private Long id;
	@Persistent
	private String groupName;
	@Persistent
	private String className;
	@Persistent
	private int sectionNumber;

	@Persistent
	private Users users;

	@Persistent(mappedBy = "group")
	private List<GroupMember> groupMemberSet;

	public Group(String groupName, String className, int sectionNumber) {
		super();
		this.groupName = groupName;
		this.className = className;
		this.sectionNumber = sectionNumber;
	}

	public Users getUsers() {
		return users;
	}

	public List<GroupMember> getGroupMemberSet() {
		return groupMemberSet;
	}

	public void setGroupMemberSet(List<GroupMember> groupMemberSet) {
		this.groupMemberSet = groupMemberSet;
	}

	public void setUsers(Users users) {
		this.users = users;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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
