package account;

public interface AccountService {
    void addNewUser(UserProfile userProfile);
    UserProfile getUserByLogin(String login);
    UserProfile getUserBySessionId(String sessionId);
    void addSession(String sessionId, UserProfile userProfile);
    void deleteSession(String sessionId);
}
