package miage.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import miage.model.Ligne;

import java.io.*;

public class JSONProcessor {
    public static void serialize(Ligne ligne, String filename) throws IOException {
        Writer out = new FileWriter(filename);
        GsonBuilder builder = new GsonBuilder();
        builder.setPrettyPrinting();
        Gson gson = builder.create();
        out.write(gson.toJson(ligne));
        out.close();
    }

    public static Ligne deserialize(String filename) throws FileNotFoundException {
        Reader in = new BufferedReader(new FileReader(filename));
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();
        Ligne res = gson.fromJson(in, Ligne.class);
        return res;
    }
}