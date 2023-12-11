package com.cafe.utils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import net.bytebuddy.description.method.MethodDescription;
import org.apache.logging.log4j.util.Strings;
import org.json.JSONArray;
import org.json.JSONException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import javax.xml.crypto.Data;
import java.io.File;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class cafeUtils {
    /* Utlis: for les fonctions reutilisables*/
    private cafeUtils(){}

    public static ResponseEntity<String> getResponseEntity(String msg , HttpStatus status){
        return new ResponseEntity<String>(
                "{\"message\":\""+msg+"\"}", status);
    }

    public static String getUUID(){
        Date date = new Date() ;
        long time = date.getTime();
        return  "Facture-"+time;
    }

    /* la conversion une chaine Json à un tableau de Json */
    public static JSONArray getJsonFromString(String data) throws JSONException{
        JSONArray jsonArray = new JSONArray(data)  ;
        return jsonArray ;
    }

    /* la conversion du Json à Map<String,Object*/
   /*Gson : bibliotheque par google facilite la conversion entrre JSON et les objets java */
    public static Map<String, Object> getMapFromJson(String data) {
        if (!Strings.isEmpty(data)) {
            return new Gson().fromJson(data, new TypeToken<Map<String, Object>>(){}.getType());
        }
        return new HashMap<>();
    }

    /* verifier si le fichier existe */
    public static Boolean isFileExist(String path){
        try{
            File file = new File(path) ;
            return (file != null && file.exists()) ? Boolean.TRUE : Boolean.FALSE ;
        }catch(Exception e){
            e.printStackTrace();
        }
        return  false ;
    }
}
