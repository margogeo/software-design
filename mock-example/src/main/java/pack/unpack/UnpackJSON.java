package pack.unpack;

import net.minidev.json.JSONObject;
import net.minidev.json.parser.JSONParser;
import net.minidev.json.parser.ParseException;

public class UnpackJSON {
    public static Object get(String object, String[] fields) throws ParseException {
        Object json = new JSONParser().parse(object);
        for (int i = 0; i < fields.length; i++) {
          if (json == null)
            return null;
          json = ((JSONObject) json).get(fields[i]);
        }
        return json;
    }
}
