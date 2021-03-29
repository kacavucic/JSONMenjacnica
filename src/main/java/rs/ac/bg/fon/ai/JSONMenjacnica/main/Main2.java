package rs.ac.bg.fon.ai.JSONMenjacnica.main;

import java.io.FileWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

import rs.ac.bg.fon.ai.JSONMenjacnica.api.CurrencyLayerAPI;
import rs.ac.bg.fon.ai.JSONMenjacnica.transakcija.Transakcija;

public class Main2 {


	public static void main(String[] args) {

		String date = "2021-01-02";
		String sourceCurrency = "USD";
		String targetCurrencies = "EUR,CHF,CAD";
		double pocetniIznos = 100;
		double konvertovaniIznosEUR = 0, konvertovaniIznosCHF = 0, konvertovaniIznosCAD = 0;

		try (FileWriter file = new FileWriter("ostale_transakcije.json")) {
			Gson gson = new GsonBuilder().setPrettyPrinting().create();

			JsonObject result = CurrencyLayerAPI.getHistoricalCurrencies(sourceCurrency, targetCurrencies, date);

			if (result.get("success").getAsBoolean()) {
				double exchangeRateEUR = result.get("quotes").getAsJsonObject().get("USDEUR").getAsDouble();
				double exchangeRateCHF = result.get("quotes").getAsJsonObject().get("USDCHF").getAsDouble();
				double exchangeRateCAD = result.get("quotes").getAsJsonObject().get("USDCAD").getAsDouble();
				System.out.println("EUR: " + exchangeRateEUR + ", CHF: " + exchangeRateCHF + ", CAD: " + exchangeRateCAD);
				konvertovaniIznosEUR = pocetniIznos * exchangeRateEUR;
				konvertovaniIznosCHF = pocetniIznos * exchangeRateCHF;
				konvertovaniIznosCAD = pocetniIznos * exchangeRateCAD;
			}

			Date datumTransakcije = new SimpleDateFormat("yyyy-MM-dd").parse(date);

			Transakcija tEUR = new Transakcija();
			tEUR.setIzvornaValuta(sourceCurrency);
			tEUR.setKrajnjaValuta("EUR");
			tEUR.setPocetniIznos(pocetniIznos);
			tEUR.setKonvertovaniIznos(konvertovaniIznosEUR);
			tEUR.setDatumTransakcije(datumTransakcije);

			Transakcija tCHF = new Transakcija();
			tCHF.setIzvornaValuta(sourceCurrency);
			tCHF.setKrajnjaValuta("CHF");
			tCHF.setPocetniIznos(pocetniIznos);
			tCHF.setKonvertovaniIznos(konvertovaniIznosCHF);
			tCHF.setDatumTransakcije(datumTransakcije);

			Transakcija tCAD = new Transakcija();
			tCAD.setIzvornaValuta(sourceCurrency);
			tCAD.setKrajnjaValuta("CAD");
			tCAD.setPocetniIznos(pocetniIznos);
			tCAD.setKonvertovaniIznos(konvertovaniIznosCAD);
			tCAD.setDatumTransakcije(datumTransakcije);

			Transakcija[] transakcije = { tEUR, tCHF, tCAD };
			gson.toJson(transakcije, file);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
