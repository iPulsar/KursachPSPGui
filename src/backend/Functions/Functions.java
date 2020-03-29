/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package backend.Functions;
import backend.Connection.*;
import backend.ui.UI;
import java.io.IOException;
import org.json.simple.JSONObject;
/**
 *
 * @author Orlovskiy
 */
public class Functions {
    public static String Auth(String login, String pass) throws IOException
    {
        JSONObject req = new JSONObject();
        req.put("request", "auth");
        req.put("login", login);
        req.put("password", pass);
        String Request = Json.JsonToString(req);
        String result = Connection.Recive(Request);
        if(result.contains("token:"))
        {
            result = result.replaceAll("token:", "");
            UI.tokenSet(result);
        }
        return result;
    }
}
