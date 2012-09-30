package dataManagers;

import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import entity.Users;

public enum UserDM{
    INSTANCE;

private UserDM(){}
public static void createUser(Users user){
        PersistenceManager pm = PMF.get().getPersistenceManager();
        try{
            pm.makePersistent(user);
        }catch(Exception e){

        }finally{
            pm.close();
        }
    }

    public static Users[] retrieveAll() {
        PersistenceManager pm = PMF.get().getPersistenceManager();
        List<Users> result = null;
        Query query=pm.newQuery(Users.class);
        try {

            result=(List<Users>)query.execute();
            result.size();
        } catch (Exception e) {
            e.printStackTrace();
        }finally{
            query.closeAll();
            pm.close();
        }
        return result.toArray(new Users[result.size()]);
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
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally{
            query.closeAll();
            pm.close();
        }
        return user;
    }


    public static void remove(String username) {
        PersistenceManager pm = PMF.get().getPersistenceManager();
        Query query=pm.newQuery(Users.class);
        query.setFilter("usernames == usernameParam");
        query.declareParameters("String usernameParam");

        try {
            query.deletePersistentAll(username);
        } catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            query.closeAll();
            pm.close();
        }
    }

    public static void modify(Users user) {
        PersistenceManager pm = PMF.get().getPersistenceManager();
        List<Users> list=null;
        Query query=pm.newQuery(Users.class);
        query.setFilter("username == usernameParam");
        query.declareParameters("String usernameParam");

        try {
            list = (List<Users>)query.execute(user.getUser());
            if (list.iterator().hasNext()){
                 for(Users e: list){
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
        }
        finally {
            query.closeAll();
            pm.close();
        }
    }

}
