package roundrobin;
import static spark.Spark.*;

import org.example.Operations;
import spark.Spark;
public class LogBalancer {
    public static void main(String[] args) {
        port(getPort());
        HashMap<String, Integer> connection = new HashMap<>();
        String firsthash = "http://ec2-3-91-27-65.compute-1.amazonaws.com:5000";
        String seconhash = "http://ec2-18-212-208-206.compute-1.amazonaws.com:5000/";
        connection.put(firsthash, 0);
        connection.put(seconhash, 0);
        get("/tan", (request, response) -> {
            String value = request.queryParams("value");
            if (connection.get(firsthash) <= connection.get(seconhash)) {
                URL obj = new URL(firsthash + "tan?value=" + value);
                HttpURLConnection con = (HttpURLConnection) obj.openConnection();
                con.setRequestMethod("GET");
                con.setRequestProperty("Content-Type", "application/json");
                String responseStr = getResponse(con);
                connection.put(firsthash, connection.get(firsthash) + 1);
                return new JSONObject(responseStr);
            }
            return null;
        });

    }

    static String getResponse(HttpURLConnection httpURLConnection) throws IOException {
        String responseStr;
        BufferedReader in = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
        String inputLine;
        StringBuilder response = new StringBuilder();
        while ((inputLine = in.readLine())!=null){
            response.append(inputLine);
        }
        in.close();
        responseStr = response.toString();
        return responseStr;
    }

    static int getPort() {
        if (System.getenv("PORT") != null) {
            return Integer.parseInt(System.getenv("PORT"));
        }
        return 4567;
    }

}
