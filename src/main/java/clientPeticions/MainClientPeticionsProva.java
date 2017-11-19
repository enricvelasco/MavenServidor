package clientPeticions;

import com.fasterxml.jackson.databind.util.JSONPObject;
import net.minidev.json.JSONObject;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class MainClientPeticionsProva {
    public static void main(String[] args) {
        try {
            //System.out.println(getHTML("http://localhost:8080/ciudades/1"));
            System.out.println(getHTMLAllTable("http://localhost:8080/ciudades/"));
            System.out.println(getHTML("http://localhost:8080/ciudades/5a0de3c6bcfd37a42145ca8b"));
            System.out.println(postHTML("http://localhost:8080/ciudades/"));//save
            //System.out.println(putHTML("http://localhost:8080/ciudades/1"));//update
            //System.out.println(deleteHTML("http://localhost:8080/ciudades/1"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static String deleteHTML(String urlToRead) throws Exception{
        StringBuilder result = new StringBuilder();
        URL url = new URL(urlToRead);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("DELETE");
        BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        String line;
        while ((line = rd.readLine()) != null) {
            result.append(line);
        }
        rd.close();
        return result.toString();
    }

    private static String putHTML(String urlToRead) throws Exception{
        StringBuilder result = new StringBuilder();
        URL url = new URL(urlToRead);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("PUT");
        BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        String line;
        while ((line = rd.readLine()) != null) {
            result.append(line);
        }
        rd.close();
        return result.toString();
    }

    private static String postHTML(String urlToRead) throws Exception{

        /*String urlParameters  = "param1=a&param2=b&param3=c";
        byte[] postData       = urlParameters.getBytes( StandardCharsets.UTF_8 );
        int    postDataLength = postData.length;
        //String request        = "http://example.com/index.php";
        URL    url            = new URL( urlToRead );
        HttpURLConnection conn= (HttpURLConnection) url.openConnection();
        conn.setDoOutput( true );
        conn.setInstanceFollowRedirects( false );
        conn.setRequestMethod( "POST" );
        conn.setRequestProperty( "Content-Type", "application/x-www-form-urlencoded");
        conn.setRequestProperty( "charset", "utf-8");
        conn.setRequestProperty( "Content-Length", Integer.toString( postDataLength ));
        conn.setUseCaches( false );
        try( DataOutputStream wr = new DataOutputStream( conn.getOutputStream())) {
            wr.write( postData );
        }

        return "RESULTADO";*/

        StringBuilder result = new StringBuilder();
        URL url = new URL(urlToRead);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("POST");
        String urlParameters = "{'name':'prueba'}";
        // Send post request
        conn.setDoOutput(true);
        /*DataOutputStream wr = new DataOutputStream(conn.getOutputStream());
        wr.writeBytes(urlParameters);//%7B%22name%22%3A%22prueba%22%7D=
        wr.flush();
        wr.close();*/
        DataOutputStream wr = new DataOutputStream(conn.getOutputStream());
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(wr, "UTF-8"));
        writer.write(urlParameters);
        writer.close();
        wr.close();

        BufferedReader in = new BufferedReader(
                new InputStreamReader(conn.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();
        return result.toString();
    }

    private static String getHTMLAllTable(String urlToRead) throws Exception{
        StringBuilder result = new StringBuilder();
        URL url = new URL(urlToRead);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        String line;
        while ((line = rd.readLine()) != null) {
            result.append(line);
        }
        rd.close();
        return result.toString();
    }

    public static String getHTML(String urlToRead) throws Exception {
        StringBuilder result = new StringBuilder();
        URL url = new URL(urlToRead);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        String line;
        while ((line = rd.readLine()) != null) {
            result.append(line);
        }
        rd.close();
        return result.toString();
    }
}
