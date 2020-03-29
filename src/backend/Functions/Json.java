/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package backend.Functions;
import backend.ui.UI;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Iterator;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.json.simple.*;
/**
 *
 * @author Orlovskiy
 */
public class Json {
    public static void PharseJsonConfig(String path)
    {
               //JSON parser object to parse read file
        JSONParser jsonParser = new JSONParser();
         
        try (FileReader reader = new FileReader(path))
        {
            //Read JSON file
            Object obj = jsonParser.parse(reader);
            JSONObject object = (JSONObject) obj;
            object.keySet().forEach(keyStr -> /*there is pharse magic begins */
            {
               Object keyvalue = object.get(keyStr);
               UI.mapSet(keyStr.toString(), keyvalue.toString());
            });
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        } 
    }
    public static void SaveJsonConfig(String pathToJson, String Json)
    {
         try {
             FileWriter file = new FileWriter(pathToJson);
             file.write(Json);
             file.flush();
         }
         catch(IOException e)
         {
         
         }
    }
    public static void PharseJsonRequest(String pathToJson)
    {
        
    }
    public static String JsonToString(JSONObject json)
    {
        String result = json.toString();
        return result;
    }
    public static JSONObject StringToJson(String str) throws ParseException
    {   JSONParser parser = new JSONParser();
        return (JSONObject) parser.parse(str);
    }
}
