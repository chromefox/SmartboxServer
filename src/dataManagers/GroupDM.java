package dataManagers;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;

import entity.Group;
import entity.ChatMessage;
import entity.Users;

public enum GroupDM {
	INSTANCE;

	private GroupDM() {
	}

	protected final static Logger logger = Logger.getLogger("group");

	public static Group createGroup(Group group) {
		PersistenceManager pm = PMF.get().getPersistenceManager();
		try {
			Key key = KeyFactory.createKey(Group.class.getSimpleName(),
					group.getGroupName().toLowerCase());
			group.setKey(key);
			pm.makePersistent(group);
			return group;
		} catch (Exception e) {
			logger.log(Level.SEVERE, e.toString());
		} finally {
			pm.close();
		}
		return null;
	}
	
	public static void persist(Group group) {
		PersistenceManager pm = PMF.get().getPersistenceManager();
		try {
			pm.makePersistent(group);
		} catch (Exception e) {
			logger.log(Level.SEVERE, e.toString());
		} finally {
			pm.close();
		}
	}
	
	public static List<Group> retrieveUserGroup(Users user) {
		PersistenceManager pm = PMF.get().getPersistenceManager();
		List<Group> result = new ArrayList<Group>();
		try {
			Iterator<Key> iter = user.getGroupSet().iterator();
        	while(iter.hasNext()) {
        		Key key = iter.next();
        		Group group = (Group) pm.getObjectById(Group.class, key);
        		//get all the user data so that we can set the group member names
        		Iterator userIter = group.getUserSet().iterator();
        		while(userIter.hasNext()) {
        			Users tempUser = (Users) pm.getObjectById(Users.class, userIter.next());
        			if(user.getKey() != tempUser.getKey()) group.getMemberNames().add(tempUser.getName()); 
        		}
        		
        		try {
        		group.getMessages();
        		} catch(Exception e) {}
        		group.setEncodedKey();
        		result.add(group);
        	}
		} catch (Exception e) {
			logger.log(Level.SEVERE, e.toString());
		} finally {
			pm.close();
		}
		return result;
	}
	
	public static void addChatMessage(Group group, String msg) {
		PersistenceManager pm = PMF.get().getPersistenceManager();
		try {
			pm.currentTransaction().begin();
			//Add a message into the group
			ChatMessage message = new ChatMessage(msg);
			group.addMessage(message);
			pm.makePersistent(group);
			pm.currentTransaction().commit();
		} catch (Exception e) {
			logger.severe(e.toString());
			logger.severe(e.getStackTrace().toString());
		} finally {
			 if (pm.currentTransaction().isActive()) {
			        pm.currentTransaction().rollback();
			    }
			pm.close();
		}
	}
	

	public static List<Group> retrieveAll() {
		PersistenceManager pm = PMF.get().getPersistenceManager();
		List<Group> result = null;
		Query query = pm.newQuery(Group.class);
		try {
			result = (List<Group>) query.execute();
			result.size();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			query.closeAll();
			pm.close();
		}
		return result;
	}

	public static Group retrieve(String groupName) {
		PersistenceManager pm = PMF.get().getPersistenceManager();
		Group group = null;
		try {
			group = (Group) pm.getObjectById(Group.class,
					groupName.toLowerCase());
			group.getMessages();
			group.getUserSet();
			for (ChatMessage msg : group.getMessages()) {
				String a = msg.getMessage();
				int b = 0;
			
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			pm.close();
		}
		return group;
	}

	public static Group retrieve(Key key) {
		PersistenceManager pm = PMF.get().getPersistenceManager();
		Group group = null;
		try {
			group = (Group) pm.getObjectById(Group.class, key);
			group.getMessages();
			group.getUserSet();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			pm.close();
		}
		return group;
	}
	 
	public static void remove(String groupName) {
		PersistenceManager pm = PMF.get().getPersistenceManager();
		try {
			Group group = (Group) retrieve(groupName);
			pm.deletePersistent(group);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			pm.close();
		}
	}

	public static void modify(Group group) {
		PersistenceManager pm = PMF.get().getPersistenceManager();
		try {
			Group tempGroup = retrieve(group.getGroupName());
			tempGroup.setGroupName(group.getGroupName());
			tempGroup.setSectionNumber(group.getSectionNumber());
			tempGroup.setClassName(group.getClassName());
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			pm.close();
		}
	}
}
