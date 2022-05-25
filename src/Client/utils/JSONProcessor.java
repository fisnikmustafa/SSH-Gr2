package Client.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class JSONProcessor<T> {
    private final Class<T> type;

    public JSONProcessor(Class<T> type) {
        this.type = type;
    }

    public String serialize(T object) {
        try {
            Gson gson = new GsonBuilder().create();
            String jsonContent = gson.toJson(object);
            return jsonContent;
        }catch(Exception e) {
            System.err.println(e.getMessage());
            return null;
        }
    }

    public T deserialize(String jsonContent) {
        try {
            Gson gson = new GsonBuilder().create();
            T object = gson.fromJson(jsonContent, this.type);
            return object;
        } catch(Exception e) {
            System.err.println(e.getMessage());
            return null;
        }
    }

}