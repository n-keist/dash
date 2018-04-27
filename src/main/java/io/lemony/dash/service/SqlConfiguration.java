package io.lemony.dash.service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import io.lemony.dash.json.SqlCredentials;

import java.io.*;

public class SqlConfiguration {

    private File file;
    private Gson gson;

    private SqlCredentials credentials;

    public SqlConfiguration() {
        this.file = new File("sql-configuration.json");
        this.gson = new GsonBuilder().setPrettyPrinting().create();

        this.credentials = new SqlCredentials();

        if(!file.exists()) {
            try {
                file.createNewFile();
                FileWriter fileWriter = new FileWriter(file);
                this.credentials.database = "d a s h";
                this.credentials.hostname = "localhost";
                this.credentials.password = "p455w0rd";
                this.credentials.port = 3306;
                this.credentials.username = "dash";
                this.credentials.tables = new String[] {
                        "CREATE TABLE IF NOT EXISTS `login` (\n" +
                                "  `id` int(11) NOT NULL AUTO_INCREMENT,\n" +
                                "  `user` int(11) NOT NULL,\n" +
                                "  `token` varchar(32) NOT NULL,\n" +
                                "  `useragent` tinytext NOT NULL,\n" +
                                "  `timestamp` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,\n" +
                                "  PRIMARY KEY (`id`),\n" +
                                "  KEY `user` (`user`),\n" +
                                "  KEY `token` (`token`)\n" +
                                ")",
                        "CREATE TABLE IF NOT EXISTS `user` (\n" +
                                "  `id` int(11) NOT NULL AUTO_INCREMENT,\n" +
                                "  `username` varchar(16) NOT NULL,\n" +
                                "  `password` text NOT NULL,\n" +
                                "  `administrator` tinyint(4) NOT NULL DEFAULT '0',\n" +
                                "  `timestamp` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,\n" +
                                "  PRIMARY KEY (`id`),\n" +
                                "  KEY `username` (`username`),\n" +
                                "  KEY `administrator` (`administrator`)\n" +
                                ")"
                };
                fileWriter.write(gson.toJson(credentials));
                fileWriter.flush();
                fileWriter.close();
                System.out.println("[D A S H] SQL-Configuration file was created.");
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            try {
                this.credentials = gson.fromJson(new FileReader(file), SqlCredentials.class);
                System.out.println("[D A S H] Read SQL-Credentials from existing config.");
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }

    }

    public SqlCredentials getCredentials() {
        return credentials;
    }
}
