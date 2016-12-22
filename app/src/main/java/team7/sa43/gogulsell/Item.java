package team7.sa43.gogulsell;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by edwin on 21/12/16.
 */

public class Item extends java.util.HashMap<String, String>
{

    final static String host = "http://192.168.1.11/sth/Service.svc";

    public Item(String itemId, String userId, String itemName, String category, String price, String status, String description, String contact)
    {
        put("itemId", itemId);
        put("userId", userId);
        put("itemName", itemName);
        put("category", category);
        put("price", price);
        put("status", status);
        put("description", description);
        put("contact", contact);
    }

    public Item()
    {
    }
//
//    public static List<Item> listItem()
//    {
//        List<Item> l = new ArrayList<Item>();
//        Item i1 = new Item("1", "alan", "Cat", "Electronics", "20", "Available", "20 May 2016", "Really really good lah", "97506806");
//        Item i2 = new Item("2", "paul", "Dog", "Electronics", "20", "Available", "25 May 2016", "Really really good man", "97506807");
//        Item i3 = new Item("3", "sara", "Pig", "Electronics", "20", "Available", "26 May 2016", "Really really good leh", "97506808");
//        l.add(i1);
//        l.add(i2);
//        l.add(i3);
//        return l;
//    }

    public static Item getItem(String id)
    {
        return new Item(id, "alan", "Bird", "Electronics", "20", "Available",  "Really really good lah", "97506806");
    }

    public static List<Item> listItemsByCategory(String s)
    {
        List<Item> list = new ArrayList<Item>();
        try
        {
            JSONArray a = JSONParser.getJSONArrayFromUrl(host + "/Items/" + s);
            Log.d("INFO", host + "/Items/" + s);
            for (int i = 0; i < a.length(); i++)
            {
                JSONObject o = a.getJSONObject(i);
                Item item = new Item(o.getString("ItemId"), o.getString("UserId"), o.getString("ItemName"), o.getString("Category"), o.getString("Price"), o.getString("Status"),
                        o.getString("Description"), o.getString("ContactNumber"));
                list.add(item);
            }
        } catch (Exception e)
        {
            Log.d("INFO", e.getMessage());
        }
        return list;
    }

    private Item getFromJSON(JSONObject o)
    {
        Item i = new Item();
        try
        {
            i = new Item(o.getString("ItemId"), o.getString("UserId"), o.getString("ItemName"), o.getString("Category"), o.getString("Price"), o.getString("Status"),
                     o.getString("Description"), o.getString("ContactNumber"));
            return i;
        } catch (JSONException e)
        {
            Log.d("INFO", "item get from json error");
        }

        return i;

    }


    public static Item getItemById(String s)
    {
        Item item = new Item();
        try
        {
            JSONObject o = JSONParser.getJSONFromUrl(host + "/Item/" + s);
            Log.d("INFO", host + "/Items/" + s);

            item = new Item(o.getString("ItemId"), o.getString("UserId"), o.getString("ItemName"), o.getString("Category"), o.getString("Price"), o.getString("Status"),
                    o.getString("Description"), o.getString("ContactNumber"));
        } catch (Exception e)
        {
            Log.d("INFO", e.getMessage());
        }
        return item;
    }

    public static String createItem(Item i) {
        JSONObject o= new JSONObject();
        try{
            //o.put("ItemId",i.get("itemId"));
            o.put("UserId",i.get("userId"));
            o.put("ItemName",i.get("itemName"));
            o.put("Category",i.get("category"));
            o.put("Price",i.get("price"));
            o.put("Status",i.get("status"));
            o.put("Description",i.get("description"));
            o.put("ContactNumber",i.get("contact"));
        } catch (JSONException e) {
            Log.d("INFO", "json error getFromItem");
        }
        String result = JSONParser.postStream(host+"/Create", o.toString());
        return result;
    }

    public static List<Item> listItemsByUser(String s) {
        List<Item> list = new ArrayList<Item>();
        try {
            JSONArray a = JSONParser.getJSONArrayFromUrl(host+"/UserItems/"+s);
            Log.d("INFO",host+"/Items/"+s);
            for (int i=0; i<a.length(); i++) {
                JSONObject o=a.getJSONObject(i);
                Item item= new Item(o.getString("ItemId"),o.getString("UserId"),o.getString("ItemName"),o.getString("Category"),o.getString("Price"),o.getString("Status"),
                        o.getString("Description"),o.getString("ContactNumber"));
                list.add(item);
            }
        } catch (Exception e) {
            Log.d("INFO",e.getMessage());
        }
        return list;
    }

    public static void updateItem(Item i) {
        JSONObject o= new JSONObject();
        try{
            o.put("ItemId",i.get("itemId"));
            o.put("UserId",i.get("userId"));
            o.put("ItemName",i.get("itemName"));
            o.put("Category",i.get("category"));
            o.put("Price",i.get("price"));
            o.put("Status",i.get("status"));
            o.put("Description",i.get("description"));
            o.put("ContactNumber",i.get("contact"));
        } catch (JSONException e) {
            Log.d("INFO", "json error getFromItem");
        }
        String result = JSONParser.postStream(host+"/Update", o.toString());
    }

    public static void deleteItem(Item i) {
        JSONObject o= new JSONObject();
        try{
            o.put("ItemId",i.get("itemId"));
            o.put("UserId",i.get("userId"));
            o.put("ItemName",i.get("itemName"));
            o.put("Category",i.get("category"));
            o.put("Price",i.get("price"));
            o.put("Status",i.get("status"));
            o.put("Description",i.get("description"));
            o.put("ContactNumber",i.get("contact"));
        } catch (JSONException e) {
            Log.d("INFO", "json error getFromItem");
        }
        String result = JSONParser.postStream(host+"/Delete", o.toString());
    }
}