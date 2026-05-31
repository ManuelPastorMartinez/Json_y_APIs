package bitcoin;

import com.google.gson.Gson;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class MirarBitcoin {
    Bitcoin bitcoin;

    static class Bitcoin {
        public double usd;
        public double eur;
    }

    public static void main(String[] args) {
        try {
            String apiUrl = "https://api.coingecko.com/api/v3/simple/price?ids=bitcoin,ethereum&vs_currencies=usd,eur";

            URL url = new URL(apiUrl);
            HttpURLConnection conexion = (HttpURLConnection) url.openConnection();
            conexion.setRequestMethod("GET");

            BufferedReader in = new BufferedReader(new InputStreamReader(conexion.getInputStream()));
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = in.readLine()) != null) {
                response.append(line);
            }
            in.close();

            Gson gson = new Gson();
            MirarBitcoin precio = gson.fromJson(response.toString(), MirarBitcoin.class);

            System.out.println("Precio del bitcoin:");
            System.out.println("En euros: "+precio.bitcoin.eur +"€" );
            System.out.println("En USD: "+precio.bitcoin.usd+"$");

        } catch (Exception e) {
            System.out.println("Algo ha ido mal.");
            e.printStackTrace();
        }
    }
}