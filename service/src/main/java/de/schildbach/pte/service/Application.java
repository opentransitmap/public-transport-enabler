/*
 * Copyright 2012-2019 the original author or authors.
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package de.schildbach.pte.service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

// import java.lang.Class;
// import java.lang.reflect.Constructor;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;

import de.schildbach.pte.AbstractNetworkProvider;
import de.schildbach.pte.NicaraguaProvider;

/**
 * @author Felix Delattre
 */
@SpringBootApplication
public class Application {

    public static void main(String[] args) {

        /* Start the main web service application */
        SpringApplication.run(Application.class, args);
    }

    public static AbstractNetworkProvider get_provider() {

        /* Read configuration about provider and additional information related from config.json */
        String provider = "", token = "";
        JSONParser parser = new JSONParser();
        try (Reader reader = new FileReader("config.json")) {
            JSONObject jsonObject = (JSONObject) parser.parse(reader);
            provider = (String) jsonObject.get("provider");
            token = (String) jsonObject.get("token");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        // TODO: Create dynamically the provider object from configuration
        // 
        // Class<?> clazz = Class.forName("de.schildbach.pte." + provider + "Provider");
        // Class<?>[] parameters = new Class[] {String.class};
        // Constructor<?> constructor = clazz.getConstructor(parameters);
        // Object instance = constructor.newInstance(new Object[] {authorization});
        // if(instance instanceof AbstractNetworkProvider) {
        //    return instance;
        //}
        return new NicaraguaProvider(token);
    }
}

