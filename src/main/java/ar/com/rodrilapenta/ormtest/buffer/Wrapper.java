package ar.com.rodrilapenta.ormtest.buffer;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Wrapper {
	
	public static Map<String, Object> wrap(String s) {
		try {
			List<String> list = new ArrayList<String>();
            BufferedReader in = new BufferedReader(new FileReader(s));
            String str;
            String tabla = in.readLine();
            Map<String, Object> mapa = new HashMap<String, Object>();
            mapa.put("tabla", tabla);
            while ((str = in.readLine()) != null) {
            	list.add(str);
            }
            in.close();
            mapa.put("registros", list);
            return mapa;
        }
		catch (IOException e) {
            System.out.println("Error al leer el archivo");
            return null;
        }
	}
	
	public static String unwrap(String[] list) {
		String result = "";
		for(int i = 0; i < list.length; i++) {
			if(i != list.length - 1) {
				result = result + list[i] + ",";
			}
			else {
				result = result + list[i];
			}
		}
		return result;
	}
}
