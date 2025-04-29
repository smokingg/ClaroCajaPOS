package cl.hyh.redpagos.caja.pos;

import java.io.Serializable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang.enums.Enum;

public class TipoTarjetaEnum extends Enum implements Serializable {

	public static final TipoTarjetaEnum RIPLEY = agregar("RP", "RIPLEY");
	public static final TipoTarjetaEnum VISA = agregar("VI", "VISA");
	public static final TipoTarjetaEnum MASTERCARD = agregar("MC", "MASTERCARD");
	public static final TipoTarjetaEnum DINERS = agregar("DC", "DINERS");
	public static final TipoTarjetaEnum AMEX = agregar("AX", "AMEX");
	public static final TipoTarjetaEnum MAGNA = agregar("MG", "MAGNA");
	public static final TipoTarjetaEnum PRESTO = agregar("CP", "PRESTO");
	public static final TipoTarjetaEnum MAS = agregar("TM", "MAS");
	public static final TipoTarjetaEnum MAESTRO = agregar("MT", "MAESTRO");
	public static final TipoTarjetaEnum CMR = agregar("TC", "CMR");
	
	
	
	private static final long serialVersionUID = 1L;
	
	 private String id = null;
	 
	 
	protected TipoTarjetaEnum(String name, String id) {
		super(name);
		this.id = id;
		
	}
	
	 private static TipoTarjetaEnum agregar(String name, String id) {
	    	return new TipoTarjetaEnum(name, id);
	    }
	
	 public static Map getEnumMap() {
	    	return getEnumMap(TipoTarjetaEnum.class);
	    }

	    public static List getEnumList() {
	    	return getEnumList(TipoTarjetaEnum.class);
	    }

	    public static Iterator iterator() {
	    	return iterator(TipoTarjetaEnum.class);
	    }
	
	    
	    public String getId() {
			return id;
		}

		public void setId(String id) {
			this.id = id;
		}

		public final Class getEnumClass() {
	    	return TipoTarjetaEnum.class;
	    }

	    public static TipoTarjetaEnum getEnum(String name) {
	    	return (TipoTarjetaEnum) getEnum(TipoTarjetaEnum.class, name);
	    }
	    
		public static TipoTarjetaEnum getEnumById(Integer id){
			for(Iterator it = getEnumMap().values().iterator();it.hasNext();){
				TipoTarjetaEnum value = (TipoTarjetaEnum)it.next();
				if(value.getId().equals(id)){
					return value;
				}
			}
			return null;
		}


}
