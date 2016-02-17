package L2.accounts;

import account.IAccountService;
import account.UserProfile;

import java.util.HashMap;
import java.util.Map;

public class AccountService implements IAccountService {
    private final Map<String, UserProfile> loginToProfile;
    private final Map<String, UserProfile> sessionIdToProfile;

    public AccountService() {
        loginToProfile = new HashMap<>();
        sessionIdToProfile = new HashMap<>();
    }

    @Override
    public void addNewUser(UserProfile userProfile) {
        loginToProfile.put(userProfile.getLogin(), userProfile);
    }
    @Override
    public UserProfile getUserByLogin(String login) {
        return loginToProfile.get(login);
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
