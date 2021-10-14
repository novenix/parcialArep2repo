package org.example;

import org.primefaces.json.JSONObject;

import static spark.Spark.*;

import spark.Spark;

public class App
{
    public static JSONObject json=new JSONObject();
    static int port() {
        if (System.getenv("PORT") != null) {
            return Integer.parseInt(System.getenv("PORT"));
        }
        return 5000;
    }
    public static void  main(String[] args )  {
        Spark.port(port());
        Operations operations = new Operations();
        get("/tan",(req,res) -> {
            double value = Double.parseDouble(req.queryParams("val"));
            double conection = operations.tan(value);


            return toObject(conection ,value);
        });
        get("/sin",(req,res) -> {
            double value = Double.parseDouble(req.queryParams("val"));
            double conection = operations.seno(value);


            return toObject(conection ,value);
        });



    }
    public static JSONObject toObject(double conection, double val){
        json.put("output",conection);
        json.put("input",val);
        json.put("operation","tan");
        return json;
    }

}