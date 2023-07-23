package com.mizuledevelopment;

import com.mizuledevelopment.utils.Color;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class LicenseAdapter {

    public void validation(Plugin plugin, String license) {
        String validationURL = "http://192.95.44.39:8000";
        boolean valid = false;
        try {
            valid = validate(validationURL, license);
        } catch (IOException e) {
            Bukkit.getConsoleSender().sendMessage(Color.translate("&cAn error occurred while validating the license."));
            e.printStackTrace();
        }

        if (!valid) {
            String invalidMessage = Color.translate("&4&m-----------------------------------\n" +
                    "&cLicense for zQueue is invalid.\n\n" +
                    "&cPlease correct your license.\n\n" +
                    "&cHelp -> https://discord.gg/Tp3wNEZmED\n" +
                    "&4&m-----------------------------------");
            Bukkit.getConsoleSender().sendMessage(invalidMessage);
            Bukkit.getPluginManager().disablePlugin(plugin);
        } else {
            String validMessage = Color.translate("&2&m-----------------------------------\n" +
                    "&aLicense for zQueue is valid.\n\n" +
                    "&aThanks for purchasing the license.\n\n" +
                    "&aHelp -> https://discord.gg/Tp3wNEZmED\n" +
                    "&2&m-----------------------------------");
            Bukkit.getConsoleSender().sendMessage(validMessage);
        }
    }

    private boolean validate(String address, String val) throws IOException {
        String URL = address + "/license/" + val + "/";
        boolean validation = false;

        try {
            URL obj = new URL(URL);
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();
            con.setRequestMethod("GET");
            con.setRequestProperty("User-Agent", "Mozilla/5.0");

            con.setConnectTimeout(5000);
            con.setReadTimeout(10000);

            if (con.getResponseCode() == HttpURLConnection.HTTP_OK) {
                try (BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()))) {
                    String inputLine;
                    StringBuilder response = new StringBuilder();

                    while ((inputLine = in.readLine()) != null) {
                        response.append(inputLine);
                    }
                    validation = true;
                }
            }
        } catch (IOException exception) {
            exception.printStackTrace();
            throw exception;
        }

        return validation;
    }
}
