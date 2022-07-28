package com.example.demo;

import com.google.gson.Gson;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.json.JsonParser;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;


@SpringBootApplication
public class DemoApplication {

	static String getMagnets() throws IOException {
		URL url = new URL("https://af323d53243mw3lykc25kn6bnu0gocdg.lambda-url.us-west-1.on.aws/");
		HttpURLConnection  conn = (HttpURLConnection) url.openConnection();
		conn.setRequestMethod("GET");
		conn.connect();
		if (conn.getResponseCode() != 200){
			throw  new RuntimeException("error connection to lambda ");
		}

		try(BufferedReader br = new BufferedReader(
				new InputStreamReader(conn.getInputStream(), "utf-8"))) {
			StringBuilder response = new StringBuilder();
			String responseLine = null;
			while ((responseLine = br.readLine()) != null) {
				response.append(responseLine.trim());
			}
			return response.toString();
		}

	}

	public static void main(String[] args) throws InterruptedException, IOException {

		Scanner scan= new Scanner(System.in);
		System.out.println(" Initial Setup ");
		System.out.println("Type the folder where files are going to be saved:");

		String magnet_location = scan.nextLine();

		Integer interval;
		while (true) {
			try {
				System.out.println("Typ the interval in minutes default to 15 minutes: ");
				interval  = scan.nextInt();
				break;
			} catch (Exception e) {
				System.out.println(e.toString());
				scan = new Scanner(System.in);

			}
		}


		System.out.println(" Settings \n" +
				"location: " + magnet_location + "\n" +
				"interval: " + interval + " m \n");

		if (interval == null){
			interval = 90000;
		}else{
			interval = interval * 60000;
		}

		while(true){


			Gson gson = new Gson();
			System.out.println(" Running program...");

			String resp = getMagnets();
			Response items = gson.fromJson(resp, Response.class);
			for (int x=0;x<items.Items.length ;x++){
				if ( magnet_location != null){
					items.Items[x].Path = magnet_location;
				}
				items.Items[x].saveFile();
				System.out.println(items.Items[x].delete());
			}
			System.out.println(" Running program completed, wait " + interval / 60000 + " m for the next run");

			Thread.sleep(interval);
		}

	}

}
