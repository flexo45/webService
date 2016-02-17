package L3.accounts;

import L3.DBException;
import L3.DBManager;
import L3.dataSet.UsersDataSet;
import account.UserProfile;
import java.util.HashMap;
import java.util.Map;

public class AccountServiceDbImpl implements account.AccountService {
    private final DBManager dbManager;
    private final Map<String, UserProfile> sessionIdToProfile;

    public AccountServiceDbImpl() {
        dbManager = new DBManager();
        sessionIdToProfile = new HashMap<>();
    }

    @Override
    public void addNewUser(UserProfile userProfile) {
        try {
            dbManager.addUser(userProfile.getLogin(), userProfile.getPass(), userProfile.getEmail());
        }
        catch (DBException ex){
            ex.printStackTrace();
        }
    }
    @Override
    public UserProfile getUserByLogin(String login) {
        try {
            UsersDataSet user = dbManager.getUser(login);
            if(user != null){
                return new UserProfile(user.getLogin(), user.getPassword(), user.getEmail());
            }
        }
        catch (DBException ex){
            ex.printStackTrace();
        }
        return null;
    }
    @Override
    public UserProfile getUserBySessionId(String sessionId) {
        return sessionIdToProfile.get(sessionId);
    }
    @Override
    public void addSession(String sessionId, UserProfile userProfile) {
        sessionIdToProfile.put(sessionId, userProfile);
    }
    @Override
    public void deleteSession(String sessionId) {
        sessionIdToProfile.remove(sessionId);
    }
}
