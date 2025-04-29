package cl.hyh.redpagos.caja.base;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;

public class ObjectOutputStreamAligare extends ObjectOutputStream {

	  /** Constructor que recibe OutputStream */
    public ObjectOutputStreamAligare(OutputStream out) throws IOException
    {
        super(out);
    }

    /** Constructor sin parámetros */
    protected ObjectOutputStreamAligare() throws IOException, SecurityException
    {
        super();
    }

    /** Redefinición del método de escribir la cabecera para que no haga nada. */
    protected void writeStreamHeader() throws IOException
    {
    }



}
