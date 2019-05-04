import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Scanner;

public class Wheather {
//0f83f23142c39b5f52d84bf97adeb039
    public static String getWeather(String message, Model model) throws IOException {

        URL url = new URL("http://api.openweathermap.org/data/2.5/weather?q="+message+"&units=metric&appid=0f83f23142c39b5f52d84bf97adeb039");

        Scanner in =new Scanner((InputStream) url.getContent());
        String  result="";
        while(in.hasNext()){
            result +=in.nextLine();
        }

        JSONObject object= new JSONObject(result);
        model.setName(object.getString("name"));

        JSONObject main= object.getJSONObject("main");
        model.setTemp(main.getDouble("temp"));
        model.setHumidity(main.getDouble("humidity"));

        JSONArray getArray = object.getJSONArray("weather");
        for(int i= 0;i<getArray.length();i++){
            JSONObject obj = getArray.getJSONObject(i);
            model.setIcon((String) obj.get("icon"));
            model.setMain((String) obj.get("main"));
        }
        //String picture = ""http://openweathermap.org/img/w/"+model.getIcon()+".png""
        return "City: "+model.getName()+"\n"+"Temperature "+model.getTemp()+"C"+"\n"+
                "Humidity: "+model.getHumidity()+"%"+"\n"+
                "Main: "+model.getMain()+"\n"+ "http://openweathermap.org/img/w/"+model.getIcon()+".png";
    }

}
