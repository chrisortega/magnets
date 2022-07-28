package com.example.demo;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.zip.GZIPInputStream;

public class Magnet {
    String magnet;
    String id;
    String Path = "/Users/ivan.ortega";
    public Magnet(String magnet){
     this.magnet = magnet;
    }

    public Boolean saveFile() throws IOException {
        String link = magnet;
        String path = Path;
        URL website = new URL(link);

        ReadableByteChannel rbc = Channels.newChannel(website.openStream());
        File f = new File(path + File.separator + id +".torrent");
        FileOutputStream fos = new FileOutputStream(f);
        fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
        fos.close();

        return null;
    }

    public Integer delete() throws IOException {

        URL url = new URL("https://a63kwevq4w7nqvv6zzxgvog2xe0wfiun.lambda-url.us-west-1.on.aws/");
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestProperty("Content-Type", "application/json");
        conn.setDoOutput(true);
        conn.setDoInput(true);
        conn.setRequestMethod("POST");
        String body = "{\"id\": \""+id+"\"}";
        System.out.println(body);
        OutputStream os = conn.getOutputStream();
        os.write(body.getBytes("UTF-8"));
        os.close();
        // read the response
        conn.connect();







        return conn.getResponseCode();
    }
    @Override
    public String toString() {
        return "Magnet{" +
                "url='" + magnet + '\'' +
                ", id='" + id + '\'' +
                '}';
    }
}
