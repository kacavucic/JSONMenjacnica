package rs.ac.bg.fon.ai.JSONMenjacnica.api;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

import rs.ac.bg.fon.ai.JSONMenjacnica.constants.CurrencyLayerConfig;

public class CurrencyLayerAPI {

	public static JsonObject getExchangeRate(String source, String currencies) throws Exception {
		URL url = new URL(CurrencyLayerConfig.BASE_URL + "/live?access_key=" + CurrencyLayerConfig.API_KEY + "&source="
				+ source + "&currencies=" + currencies);
		HttpURLConnection con = (HttpURLConnection) url.openConnection();
		con.setRequestMethod("GET");
		BufferedReader reader = new BufferedReader(new InputStreamReader(con.getInputStream()));
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		return gson.fromJson(reader, JsonObject.class);
	}

	public static JsonObject getHistoricalCurrencies(String source, String currencies, String date) throws Exception {
		URL url = new URL(CurrencyLayerConfig.BASE_URL + "/historical?access_key=" + CurrencyLayerConfig.API_KEY
				+ "&date=" + date + "&source=" + source + "&currencies=" + currencies);
		HttpURLConnection con = (HttpURLConnection) url.openConnection();
		con.setRequestMethod("GET");
		BufferedReader reader = new BufferedReader(new InputStreamReader(con.getInputStream()));
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		return gson.fromJson(reader, JsonObject.class);
	}
}
