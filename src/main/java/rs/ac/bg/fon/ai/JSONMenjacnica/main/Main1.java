package rs.ac.bg.fon.ai.JSONMenjacnica.main;

import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Date;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

import rs.ac.bg.fon.ai.JSONMenjacnica.transakcija.Transakcija;

public class Main1 {

	private static final String BASE_URL = "http://api.currencylayer.com";
	private static final String API_KEY = "0cbf084b58d56ec181fdf321cdcb1f6c";
	private static final String SOURCE = "USD";
	private static final String CURRENCIES = "CAD";

	public static void main(String[] args) {

		double pocetniIznos = 444;
		double konvertovaniIznos = 0;
		
		try (FileWriter file = new FileWriter("prva_transakcija.json")) {
			Gson gson = new GsonBuilder().setPrettyPrinting().create();

			URL url = new URL(
					BASE_URL + "/live?access_key=" + API_KEY + "&source=" + SOURCE + "&currencies=" + CURRENCIES);
			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			con.setRequestMethod("GET");
			BufferedReader reader = new BufferedReader(new InputStreamReader(con.getInputStream()));
			JsonObject result = gson.fromJson(reader, JsonObject.class);

			if (result.get("success").getAsBoolean()) {
				double exchangeRate = result.get("quotes").getAsJsonObject().get("USDCAD").getAsDouble();
				System.out.println(exchangeRate);
				konvertovaniIznos = pocetniIznos * exchangeRate;
			}

			Transakcija t = new Transakcija();
			t.setIzvornaValuta(SOURCE);
			t.setKrajnjaValuta(CURRENCIES);
			t.setPocetniIznos(pocetniIznos);
			t.setKonvertovaniIznos(konvertovaniIznos);
			t.setDatumTransakcije(new Date());

			gson.toJson(t, file);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
