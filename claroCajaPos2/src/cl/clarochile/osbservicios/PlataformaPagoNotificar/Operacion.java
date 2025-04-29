/**
 * Operacion.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package cl.clarochile.osbservicios.PlataformaPagoNotificar;

public class Operacion  implements java.io.Serializable {
    private int tipoOperacion;

    private java.lang.Long numeroOperacion;

    private java.lang.String fechaPago;

    private long monto;

    private cl.clarochile.osbservicios.PlataformaPagoNotificar.Caja caja;

    private cl.clarochile.osbservicios.PlataformaPagoNotificar.Transaccion[] transaccion;

    private cl.clarochile.osbservicios.PlataformaPagoNotificar.MedioPago[] medioPago;

    private java.lang.String numEdicionPago;

    public Operacion() {
    }

    public Operacion(
           int tipoOperacion,
           java.lang.Long numeroOperacion,
           java.lang.String fechaPago,
           long monto,
           cl.clarochile.osbservicios.PlataformaPagoNotificar.Caja caja,
           cl.clarochile.osbservicios.PlataformaPagoNotificar.Transaccion[] transaccion,
           cl.clarochile.osbservicios.PlataformaPagoNotificar.MedioPago[] medioPago,
           java.lang.String numEdicionPago) {
           this.tipoOperacion = tipoOperacion;
           this.numeroOperacion = numeroOperacion;
           this.fechaPago = fechaPago;
           this.monto = monto;
           this.caja = caja;
           this.transaccion = transaccion;
           this.medioPago = medioPago;
           this.numEdicionPago = numEdicionPago;
    }


    /**
     * Gets the tipoOperacion value for this Operacion.
     * 
     * @return tipoOperacion
     */
    public int getTipoOperacion() {
        return tipoOperacion;
    }


    /**
     * Sets the tipoOperacion value for this Operacion.
     * 
     * @param tipoOperacion
     */
    public void setTipoOperacion(int tipoOperacion) {
        this.tipoOperacion = tipoOperacion;
    }


    /**
     * Gets the numeroOperacion value for this Operacion.
     * 
     * @return numeroOperacion
     */
    public java.lang.Long getNumeroOperacion() {
        return numeroOperacion;
    }


    /**
     * Sets the numeroOperacion value for this Operacion.
     * 
     * @param numeroOperacion
     */
    public void setNumeroOperacion(java.lang.Long numeroOperacion) {
        this.numeroOperacion = numeroOperacion;
    }


    /**
     * Gets the fechaPago value for this Operacion.
     * 
     * @return fechaPago
     */
    public java.lang.String getFechaPago() {
        return fechaPago;
    }


    /**
     * Sets the fechaPago value for this Operacion.
     * 
     * @param fechaPago
     */
    public void setFechaPago(java.lang.String fechaPago) {
        this.fechaPago = fechaPago;
    }


    /**
     * Gets the monto value for this Operacion.
     * 
     * @return monto
     */
    public long getMonto() {
        return monto;
    }


    /**
     * Sets the monto value for this Operacion.
     * 
     * @param monto
     */
    public void setMonto(long monto) {
        this.monto = monto;
    }


    /**
     * Gets the caja value for this Operacion.
     * 
     * @return caja
     */
    public cl.clarochile.osbservicios.PlataformaPagoNotificar.Caja getCaja() {
        return caja;
    }


    /**
     * Sets the caja value for this Operacion.
     * 
     * @param caja
     */
    public void setCaja(cl.clarochile.osbservicios.PlataformaPagoNotificar.Caja caja) {
        this.caja = caja;
    }


    /**
     * Gets the transaccion value for this Operacion.
     * 
     * @return transaccion
     */
    public cl.clarochile.osbservicios.PlataformaPagoNotificar.Transaccion[] getTransaccion() {
        return transaccion;
    }


    /**
     * Sets the transaccion value for this Operacion.
     * 
     * @param transaccion
     */
    public void setTransaccion(cl.clarochile.osbservicios.PlataformaPagoNotificar.Transaccion[] transaccion) {
        this.transaccion = transaccion;
    }

    public cl.clarochile.osbservicios.PlataformaPagoNotificar.Transaccion getTransaccion(int i) {
        return this.transaccion[i];
    }

    public void setTransaccion(int i, cl.clarochile.osbservicios.PlataformaPagoNotificar.Transaccion _value) {
        this.transaccion[i] = _value;
    }


    /**
     * Gets the medioPago value for this Operacion.
     * 
     * @return medioPago
     */
    public cl.clarochile.osbservicios.PlataformaPagoNotificar.MedioPago[] getMedioPago() {
        return medioPago;
    }


    /**
     * Sets the medioPago value for this Operacion.
     * 
     * @param medioPago
     */
    public void setMedioPago(cl.clarochile.osbservicios.PlataformaPagoNotificar.MedioPago[] medioPago) {
        this.medioPago = medioPago;
    }

    public cl.clarochile.osbservicios.PlataformaPagoNotificar.MedioPago getMedioPago(int i) {
        return this.medioPago[i];
    }

    public void setMedioPago(int i, cl.clarochile.osbservicios.PlataformaPagoNotificar.MedioPago _value) {
        this.medioPago[i] = _value;
    }


    /**
     * Gets the numEdicionPago value for this Operacion.
     * 
     * @return numEdicionPago
     */
    public java.lang.String getNumEdicionPago() {
        return numEdicionPago;
    }


    /**
     * Sets the numEdicionPago value for this Operacion.
     * 
     * @param numEdicionPago
     */
    public void setNumEdicionPago(java.lang.String numEdicionPago) {
        this.numEdicionPago = numEdicionPago;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof Operacion)) return false;
        Operacion other = (Operacion) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            this.tipoOperacion == other.getTipoOperacion() &&
            ((this.numeroOperacion==null && other.getNumeroOperacion()==null) || 
             (this.numeroOperacion!=null &&
              this.numeroOperacion.equals(other.getNumeroOperacion()))) &&
            ((this.fechaPago==null && other.getFechaPago()==null) || 
             (this.fechaPago!=null &&
              this.fechaPago.equals(other.getFechaPago()))) &&
            this.monto == other.getMonto() &&
            ((this.caja==null && other.getCaja()==null) || 
             (this.caja!=null &&
              this.caja.equals(other.getCaja()))) &&
            ((this.transaccion==null && other.getTransaccion()==null) || 
             (this.transaccion!=null &&
              java.util.Arrays.equals(this.transaccion, other.getTransaccion()))) &&
            ((this.medioPago==null && other.getMedioPago()==null) || 
             (this.medioPago!=null &&
              java.util.Arrays.equals(this.medioPago, other.getMedioPago()))) &&
            ((this.numEdicionPago==null && other.getNumEdicionPago()==null) || 
             (this.numEdicionPago!=null &&
              this.numEdicionPago.equals(other.getNumEdicionPago())));
        __equalsCalc = null;
        return _equals;
    }

    private boolean __hashCodeCalc = false;
    public synchronized int hashCode() {
        if (__hashCodeCalc) {
            return 0;
        }
        __hashCodeCalc = true;
        int _hashCode = 1;
        _hashCode += getTipoOperacion();
        if (getNumeroOperacion() != null) {
            _hashCode += getNumeroOperacion().hashCode();
        }
        if (getFechaPago() != null) {
            _hashCode += getFechaPago().hashCode();
        }
        _hashCode += new Long(getMonto()).hashCode();
        if (getCaja() != null) {
            _hashCode += getCaja().hashCode();
        }
        if (getTransaccion() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getTransaccion());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getTransaccion(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getMedioPago() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getMedioPago());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getMedioPago(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getNumEdicionPago() != null) {
            _hashCode += getNumEdicionPago().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(Operacion.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://osbservicios.clarochile.cl/PlataformaPagoNotificar/", "Operacion"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("tipoOperacion");
        elemField.setXmlName(new javax.xml.namespace.QName("", "tipoOperacion"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("numeroOperacion");
        elemField.setXmlName(new javax.xml.namespace.QName("", "numeroOperacion"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "long"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("fechaPago");
        elemField.setXmlName(new javax.xml.namespace.QName("", "fechaPago"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("monto");
        elemField.setXmlName(new javax.xml.namespace.QName("", "monto"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "long"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("caja");
        elemField.setXmlName(new javax.xml.namespace.QName("", "caja"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://osbservicios.clarochile.cl/PlataformaPagoNotificar/", "Caja"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("transaccion");
        elemField.setXmlName(new javax.xml.namespace.QName("", "transaccion"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://osbservicios.clarochile.cl/PlataformaPagoNotificar/", "Transaccion"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        elemField.setMaxOccursUnbounded(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("medioPago");
        elemField.setXmlName(new javax.xml.namespace.QName("", "medioPago"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://osbservicios.clarochile.cl/PlataformaPagoNotificar/", "MedioPago"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        elemField.setMaxOccursUnbounded(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("numEdicionPago");
        elemField.setXmlName(new javax.xml.namespace.QName("", "numEdicionPago"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
    }

    /**
     * Return type metadata object
     */
    public static org.apache.axis.description.TypeDesc getTypeDesc() {
        return typeDesc;
    }

    /**
     * Get Custom Serializer
     */
    public static org.apache.axis.encoding.Serializer getSerializer(
           java.lang.String mechType, 
           java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType) {
        return 
          new  org.apache.axis.encoding.ser.BeanSerializer(
            _javaType, _xmlType, typeDesc);
    }

    /**
     * Get Custom Deserializer
     */
    public static org.apache.axis.encoding.Deserializer getDeserializer(
           java.lang.String mechType, 
           java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType) {
        return 
          new  org.apache.axis.encoding.ser.BeanDeserializer(
            _javaType, _xmlType, typeDesc);
    }

}
