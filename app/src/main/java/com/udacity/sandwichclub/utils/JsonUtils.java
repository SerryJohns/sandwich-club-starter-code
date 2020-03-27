package com.udacity.sandwichclub.utils;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonUtils {

    public static Sandwich parseSandwichJson(String json) {
        Sandwich sandwich = null;
        try {
            JSONObject jsonObject = new JSONObject(json);

            JSONObject nameObj = jsonObject.getJSONObject("name");
            String mainName = nameObj.getString("mainName");
            JSONArray alsoKnownAs = nameObj.getJSONArray("alsoKnownAs");

            String placeOfOrigin = jsonObject.getString("placeOfOrigin");
            String description = jsonObject.getString("description");
            String image = jsonObject.getString("image");
            JSONArray ingredients = jsonObject.getJSONArray("ingredients");

            // If place of origin is null or empty, set the default ot N/A
            if (placeOfOrigin == null || placeOfOrigin.isEmpty()) {
                placeOfOrigin = "N/A";
            }

            // Generate List objects from JSON Arrays
            List<String> alsoKnownAsList = getListFromJsonArray(alsoKnownAs);
            List<String> ingredientsList = getListFromJsonArray(ingredients);

            sandwich = new Sandwich(
                    mainName,
                    alsoKnownAsList,
                    placeOfOrigin,
                    description,
                    image,
                    ingredientsList);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return sandwich;
    }

    private static <T> List<T> getListFromJsonArray(JSONArray jsonArray) throws JSONException {
        List<T> result = new ArrayList<>();
        if (jsonArray.length() > 0) {
            for (int i = 0; i < jsonArray.length(); i++) {
                T element = (T) jsonArray.get(i);
                result.add(element);
            }
        }
        return result;
    }
}
