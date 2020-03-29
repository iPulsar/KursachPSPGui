/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package backend.ui;
import java.util.HashMap;
import backend.Connection.*;
import backend.Functions.Json;
import java.io.IOException;
import javax.swing.JOptionPane;
import org.json.simple.JSONObject;
import backend.Functions.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.simple.parser.ParseException;
/**
 *
 * @author Orlovskiy
 */

public class UI {
    private static HashMap map = new HashMap();
    private static String token = null;
    private String ConfigPath="./";
    public static void tokenSet(String t)
    {
        token = t;
    }
    public static void tokenSave()
    {
        JSONObject jconfig = new JSONObject();
        jconfig.put("token", token);
        jconfig.put("ip", map.get("ip"));
        jconfig.put("port", map.get("port"));
        Json.SaveJsonConfig("./config.json", Json.JsonToString(jconfig));
    }
    public static String mapGet(String key)
    {
        return map.get(key).toString();
    }
    public static void mapSet(String key, String value)
    {
        map.put(key, value);
    }
    public static void Initialize()
    {
        Json.PharseJsonConfig("./config.json");
        if(map.get("ip").toString().equals("") || map.get("port").toString().equals(""))
        {
            map.put("ip","localhost");
            map.put("port","9000");
        }
        Connection.ServerInfo(map.get("ip").toString(), map.get("port").toString());
        token = map.get("token").toString();
        JOptionPane.showMessageDialog(null, map.get("ip").toString().concat(":").concat(map.get("port").toString()));
    }
    public static HashMap updateData(String request)
    {
        HashMap data = new HashMap();
        JSONObject requestdata = new JSONObject();
            requestdata.put("token", token);
            requestdata.put("request", "update");
            requestdata.put("updateType", request);
        try {
                String FromServer = Connection.Recive(Json.JsonToString(requestdata));
                if(FromServer.contains("Unauthorized"))
                {
                    data.put("error","Unauthorized");
                    return data; 
                }
                else
                {
                    JSONObject responde = Json.StringToJson(FromServer);
                    responde.keySet().forEach(keyStr -> /*there is pharse magic begins */
                    {
                       Object keyvalue = responde.get(keyStr);
                       data.put(keyStr.toString(), keyvalue.toString());
                    });
                    return data;
                }
        } catch (IOException ex) {
            Logger.getLogger(UI.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, ex.toString());
        } catch (ParseException ex) {
            Logger.getLogger(UI.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, ex.toString());
        }
        return map;
    }
    public static void registration(String username, String FirstName, String LastName, String country, String password) throws IOException
    {
        if(username == "" || password == "" || FirstName == "" || country == "")
        {
            JOptionPane.showMessageDialog(null, "incorrect registration data");
        }
        else
        {
            JSONObject regdata = new JSONObject();
            regdata.put("request", "registration");
            regdata.put("username", username);
            regdata.put("FirstName", FirstName);
            regdata.put("LastName", LastName);
            regdata.put("country", country);
            regdata.put("password", password);
            JOptionPane.showMessageDialog(null,Connection.Recive(Json.JsonToString(regdata)));
        }
    }
    public static void SendBMessage(String message)
    {
                if(token != null || token != "")
        {
            JSONObject requestdata = new JSONObject();
            requestdata.put("request", "board");
            requestdata.put("token", token);
            requestdata.put("message", message);
            try {
                JOptionPane.showMessageDialog(null, Connection.Recive(Json.JsonToString(requestdata)));
            }catch (IOException e)
            {
                JOptionPane.showMessageDialog(null, e.toString());
            }
        }
        else JOptionPane.showMessageDialog(null, "You are not loginned yet. Sign in first.");
    }
    public static void SendMessage(String reciver, String message, boolean anon)
    {
        if(token != null)
        {
            JSONObject requestdata = new JSONObject();
            requestdata.put("request", "send");
            requestdata.put("anon", anon);
            requestdata.put("token", token);
            requestdata.put("recivername", reciver);
            requestdata.put("message", message);
            try {
                JOptionPane.showMessageDialog(null, Connection.Recive(Json.JsonToString(requestdata)));
            }catch (IOException e)
            {
                JOptionPane.showMessageDialog(null, e.toString());
            }
        }
        else JOptionPane.showMessageDialog(null, "You are not loginned yet. Sign in first.");
    }
}
