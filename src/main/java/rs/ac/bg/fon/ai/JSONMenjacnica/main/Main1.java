package rs.ac.bg.fon.ai.JSONMenjacnica.main;

import java.io.FileWriter;
import java.util.Date;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

import rs.ac.bg.fon.ai.JSONMenjacnica.api.CurrencyLayerAPI;
import rs.ac.bg.fon.ai.JSONMenjacnica.transakcija.Transakcija;

public class Main1 {
	
	public static void main(String[] args) {

		String sourceCurrency = "USD";
		String targetCurrency = "CAD";
		double pocetniIznos = 444;
		double konvertovaniIznos = 0;
		
		try (FileWriter file = new FileWriter("prva_transakcija.json")) {
			Gson gson = new GsonBuilder().setPrettyPrinting().create();

			JsonObject result = CurrencyLayerAPI.getExchangeRate(sourceCurrency, targetCurrency);

			if (result.get("success").getAsBoolean()) {
				double exchangeRate = result.get("quotes").getAsJsonObject().get(sourceCurrency+targetCurrency).getAsDouble();
				System.out.println(exchangeRate);
				konvertovaniIznos = pocetniIznos * exchangeRate;
			}

			Transakcija t = new Transakcija();
			t.setIzvornaValuta(sourceCurrency);
			t.setKrajnjaValuta(targetCurrency);
			t.setPocetniIznos(pocetniIznos);
			t.setKonvertovaniIznos(konvertovaniIznos);
			t.setDatumTransakcije(new Date());

			gson.toJson(t, file);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
