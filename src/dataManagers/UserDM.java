package dataManagers;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import appspot.helper.Util;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;

import entity.Users;

public enum UserDM {
	INSTANCE;

	private UserDM() {
	}

	protected final static Logger logger = Logger.getLogger("abc");

	public static void createUser(Users user) {
		PersistenceManager pm = PMF.get().getPersistenceManager();
		try {
			// Key key = KeyFactory.createKey(Users.class.getSimpleName(),
			// user.getEmail());
			// user.setEncodedKey(KeyFactory.keyToString(key));
			Key key = KeyFactory.createKey(Users.class.getSimpleName(),
					user.getEmail().toLowerCase());
			user.setKey(key);
			pm.makePersistent(user);
		} catch (Exception e) {
			logger.log(Level.SEVERE, e.toString());
		} finally {
			pm.close();
		}
	}

	public static List<Users> retrieveAll() {
		PersistenceManager pm = PMF.get().getPersistenceManager();
		List<Users> result = null;
		Query query = pm.newQuery(Users.class);
		try {
			result = (List<Users>) query.execute();
			result.size();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			query.closeAll();
			pm.close();
		}
		return result;
	}

	public static Users retrieve(String email) {
		PersistenceManager pm = PMF.get().getPersistenceManager();
		Users user = null;
		Query query = pm.newQuery(Users.class);
		query.setFilter("email == emailParam");
		query.declareParameters("String emailParam");
		try {
			List<Users> results = (List<Users>) query.execute(email);
			if (results.iterator().hasNext()) {
				for (Users e : results) {
					user = e;
					user.setEncodedKey();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			query.closeAll();
			pm.close();
		}
		return user;
	}
	
	public static Users retrieveFromMobile(String rawNumber) {
		String mobileNumber = Util.parseMobileNumber(rawNumber);
		PersistenceManager pm = PMF.get().getPersistenceManager();
		Users user = null;
		Query query = pm.newQuery(Users.class);
		query.setFilter("mobileNumber == mobileNumberParam");
		query.declareParameters("String mobileNumberParam");
		try {
			List<Users> results = (List<Users>) query.execute(mobileNumber);
			if (results.iterator().hasNext()) {
				for (Users e : results) {
					user = e;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			query.closeAll();
			pm.close();
		}
		return user;
	}
	
	public static void persist(Users user) {
		PersistenceManager pm = PMF.get().getPersistenceManager();
		try {
			pm.makePersistent(user);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			pm.close();
		}
	}

	public static Users retrieveUser(String email) {
		PersistenceManager pm = PMF.get().getPersistenceManager();
		Users user = null;
		try {
			user = (Users) pm.getObjectById(Users.class, email);
			user.getGroupSet();	
			user.setEncodedKey();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			pm.close();
		}
		return user;
	}
	
	public static Users retrieveUserWithKey(Key key) {
		PersistenceManager pm = PMF.get().getPersistenceManager();
		Users user = null;
		try {
			user = (Users) pm.getObjectById(Users.class, key);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			pm.close();
		}
		return user;
	}

	public static void remove(String username) {
		PersistenceManager pm = PMF.get().getPersistenceManager();
		Query query = pm.newQuery(Users.class);
		query.setFilter("usernames == usernameParam");
		query.declareParameters("String usernameParam");

		try {
			query.deletePersistentAll(username);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			query.closeAll();
			pm.close();
		}
	}

	public static void modify(Users user) {
		PersistenceManager pm = PMF.get().getPersistenceManager();
		List<Users> list = null;
		Query query = pm.newQuery(Users.class);
		query.setFilter("username == usernameParam");
		query.declareParameters("String usernameParam");

		try {
			list = (List<Users>) query.execute(user.getUser());
			if (list.iterator().hasNext()) {
				for (Users e : list) {
					e.setUser(user.getUser());
					e.setPassword(user.getPassword());
					e.setPriv(user.getPriv());
					e.setUid(user.getUid());
					e.setAccessToken(user.getAccessToken());
					e.setArtistInfo(user.getArtistInfo());
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			query.closeAll();
			pm.close();
		}
	}

	public static void modifyRegId(Users user) {
		PersistenceManager pm = PMF.get().getPersistenceManager();
		try {
			Users e = UserDM.retrieve(user.getEmail());
			e.setDeviceRegId(user.getDeviceRegId());
			pm.makePersistent(e);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			pm.close();
		}
	}

	public static void deleteRegId(Users user) {
		PersistenceManager pm = PMF.get().getPersistenceManager();
		List<Users> list = null;
		Query query = pm.newQuery(Users.class);
		query.setFilter("encodedKey == encodedKeyParam");
		query.declareParameters("String encodedKeyParam");
		try {
			list = (List<Users>) query.execute(user.getKey());
			if (list.iterator().hasNext()) {
				for (Users e : list) {
					e.setDeviceRegId("");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			query.closeAll();
			pm.close();
		}
	}

}
