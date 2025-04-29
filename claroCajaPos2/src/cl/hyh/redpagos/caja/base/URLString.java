package cl.hyh.redpagos.caja.base;

import java.util.HashMap;

public class URLString {
	
	HashMap<String, String> cache = new HashMap<String, String>();
	
	public URLString(){
		
	}
	
	public URLString(String url){
		
		String rspTmp[];
		String rspTmp2[];
		String xCodigo;
		String xValor;
		rspTmp = url.split("&");
		
		//para que no se caiga si la url no es valida.
		if (rspTmp.length == 1) {
			cache.put("", url);
			return;
		}
		
		for (String codigos : rspTmp){
			xCodigo = codigos.substring(0, codigos.indexOf("="));
			rspTmp2 =codigos.split("=");
			
			if(rspTmp2.length<=1){
				xValor = "";
			}
			else{
				 xValor =rspTmp2[1];
			}
			cache.put(xCodigo, xValor);   

		}

	}

	public String getValor(String xCodigo){
		return cache.get(xCodigo);
	}
	
	
	
}
