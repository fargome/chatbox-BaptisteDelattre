package com.example.cesiapp;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.LinkedList;
import java.util.List;

//import cesi.com.tchatapp.model.Message;

//Pour traiter les flux JSON que l'on reçoit de l'API
public class JsonParser {

    public static List<Message> getMessages(String json) throws JSONException {
        List<Message> messages = new LinkedList<>();
        JSONArray array = new JSONArray(json);
        JSONObject obj;
        Message msg;
        for(int i=0; i < array.length(); i++){
            //Création d'une liste contenant tous les messages reçus dans le JSON.
            obj = array.getJSONObject(i);
            msg = new Message(obj.optString("username"), obj.optString("message"), obj.optLong("date"));
            messages.add(msg);
        }

        return messages;
    }

    public static String getToken(String response) throws JSONException {
        return new JSONObject(response).optString("token");
    }
}
