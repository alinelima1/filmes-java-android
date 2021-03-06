package aula.filmes.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class Conexao {

    public static String getJSON(String url){
        String retorno = "";
        try {
            URL apiEnd = new URL(url);
            int codResposta;
            HttpURLConnection connection;
            InputStream is;

            connection = (HttpURLConnection) apiEnd.openConnection();
            connection.setRequestMethod("GET");
            connection.setReadTimeout(15000);
            connection.setConnectTimeout(15000);
            connection.connect();

            codResposta = connection.getResponseCode();
            if(codResposta < HttpURLConnection.HTTP_BAD_REQUEST){
                is = connection.getInputStream();
            }else{
                is = connection.getErrorStream();
            }

            retorno = convertToString(is);
            is.close();
            connection.disconnect();

        } catch (MalformedURLException e) {
            e.printStackTrace();
        }catch (IOException e){
            e.printStackTrace();
        }
        return retorno;
    }

    private static String convertToString(InputStream is){
        StringBuffer buffer = new StringBuffer();
        try{
            BufferedReader br;
            String line;
            br = new BufferedReader(new InputStreamReader(is));
            while((line = br.readLine())!=null){
                buffer.append(line);
            }
            br.close();
        }catch(IOException e){
            e.printStackTrace();
        }
        return buffer.toString();
    }
}