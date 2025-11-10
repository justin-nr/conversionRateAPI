import java.io.*;
import java.net.*;
import java.util.Properties;

import com.google.gson.*;

public class Main {
    public static void main(String[] args) throws Exception {

        //calls from config file to hide API key
        Properties config = new Properties();
        try (FileInputStream input = new FileInputStream("config.properties")) {
            config.load(input);
        } catch (IOException e) {
            System.out.println("Error loading config: " + e.getMessage());
            return;
        }

        //calls from properties config and calls api_key
        String apiKey = config.getProperty("API_KEY");
        String urlString = String.format("https://v6.exchangerate-api.com/v6/%s/latest/USD", apiKey);


        //makes url
        URL url = new URL(urlString);
        //uses URL to be created as "connection"
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        //makes "get" as the request method
        connection.setRequestMethod("GET");

        //makes a buffered reader for the link
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
            JsonElement root = JsonParser.parseReader(reader);
//            System.out.println(root);
            JsonObject object = root.getAsJsonObject();

            //makes base as what your converting from (USD is default)
            String base = object.get("base_code").getAsString();

            //reads from conversion rates and the conversion rate from base currency
            //CHANGE THE 3 LETTER CODE TO WHAT COUNTRY'S CURRENCY YOU WOULD LIKE TO USE. (google a countries 3 letter currency code)
            double money = object.getAsJsonObject("conversion_rates").get("PUT 3 LETTER CODE HERE").getAsDouble();

            //displays conversion
            System.out.println("Base Currency: " + base);
            System.out.println("Conversion rate from 1 " + base + " is " + money);
        }

    }
}