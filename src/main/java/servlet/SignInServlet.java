package servlet;

import account.IAccountService;
import account.UserProfile;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class SignInServlet extends HttpServlet {
    private final IAccountService accountService;

    public SignInServlet(IAccountService accountService) {
        this.accountService = accountService;
    }

    //forbidden
    public void doGet(HttpServletRequest request,
                      HttpServletResponse response) throws ServletException, IOException {
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
    }

    //signUP
    public void doPost(HttpServletRequest request,
                       HttpServletResponse response) throws ServletException, IOException {
        String login = request.getParameter("login");
        String pass = request.getParameter("password");

        if (login == null || pass == null) {
            response.setContentType("text/html;charset=utf-8");
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        UserProfile profile = accountService.getUserByLogin(login);
        if (profile == null || !profile.getPass().equals(pass)) {
            response.setContentType("text/html;charset=utf-8");
            response.getWriter().println("Unauthorized");
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }

        response.setContentType("text/html;charset=utf-8");
        response.getWriter().println("Authorized: " + profile.getLogin());
        response.setStatus(HttpServletResponse.SC_OK);
    }
}
