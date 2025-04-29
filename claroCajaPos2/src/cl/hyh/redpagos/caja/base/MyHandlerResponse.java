package cl.hyh.redpagos.caja.base;

import javax.xml.rpc.handler.MessageContext;
import javax.xml.rpc.handler.soap.SOAPMessageContext;
import javax.xml.soap.SOAPMessage;

import org.apache.axis.AxisFault;
import org.apache.axis.handlers.BasicHandler;

public class MyHandlerResponse extends BasicHandler  {

		@Override
		public void invoke(org.apache.axis.MessageContext arg0) {			
			 
	    try {
		    SOAPMessageContext smc = (SOAPMessageContext) arg0;
		    SOAPMessage message = smc.getMessage();
		    if( message != null ) {
		    	Base.logger.info( "ResponseBody  : " + message.getSOAPBody() );
		    }
	    }
	    catch( Exception e ) {
	      e.printStackTrace();
	    }
	  }

}
