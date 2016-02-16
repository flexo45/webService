package L1.servlet;

import templater.PageGenerator;

import javax.servlet.*;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by m.guriev on 13.02.2016.
 */
public class MirrorServlet extends HttpServlet {
    public void doGet(HttpServletRequest request,
                      HttpServletResponse response) throws ServletException, IOException {

        Map<String, Object> params = new HashMap<>();

        Map<String, String[]> parameterMap = request.getParameterMap();

        if(!parameterMap.isEmpty()){
            if(parameterMap.size() > 0){
                for(Map.Entry<String, String[]> i : parameterMap.entrySet()){
                    params.put("value", i.getValue()[0]);
                    break;
                }
            }
            response.getWriter().println(PageGenerator.instance().getPage("mirror.html", params));
            response.setContentType("text/html;charset=utf-8");
            response.setStatus(HttpServletResponse.SC_OK);
        }
        else {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
    }

    public void doPost(HttpServletRequest request,
                       HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}
