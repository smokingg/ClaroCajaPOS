/**
 * RecargaOut.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package cl.hyh.cajas.ws.impl;

public class RecargaOut  extends cl.hyh.cajas.ws.impl.Response  implements java.io.Serializable {
    private long codigoAutorizacion;

    private java.lang.String mensaje;

    private java.lang.String saldoAbonado;

    public RecargaOut() {
    }

    public RecargaOut(
           cl.hyh.cajas.ws.impl.HeaderOut headerOut,
           long codigoAutorizacion,
           java.lang.String mensaje,
           java.lang.String saldoAbonado) {
        super(
            headerOut);
        this.codigoAutorizacion = codigoAutorizacion;
        this.mensaje = mensaje;
        this.saldoAbonado = saldoAbonado;
    }


    /**
     * Gets the codigoAutorizacion value for this RecargaOut.
     * 
     * @return codigoAutorizacion
     */
    public long getCodigoAutorizacion() {
        return codigoAutorizacion;
    }


    /**
     * Sets the codigoAutorizacion value for this RecargaOut.
     * 
     * @param codigoAutorizacion
     */
    public void setCodigoAutorizacion(long codigoAutorizacion) {
        this.codigoAutorizacion = codigoAutorizacion;
    }


    /**
     * Gets the mensaje value for this RecargaOut.
     * 
     * @return mensaje
     */
    public java.lang.String getMensaje() {
        return mensaje;
    }


    /**
     * Sets the mensaje value for this RecargaOut.
     * 
     * @param mensaje
     */
    public void setMensaje(java.lang.String mensaje) {
        this.mensaje = mensaje;
    }


    /**
     * Gets the saldoAbonado value for this RecargaOut.
     * 
     * @return saldoAbonado
     */
    public java.lang.String getSaldoAbonado() {
        return saldoAbonado;
    }


    /**
     * Sets the saldoAbonado value for this RecargaOut.
     * 
     * @param saldoAbonado
     */
    public void setSaldoAbonado(java.lang.String saldoAbonado) {
        this.saldoAbonado = saldoAbonado;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof RecargaOut)) return false;
        RecargaOut other = (RecargaOut) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = super.equals(obj) && 
            this.codigoAutorizacion == other.getCodigoAutorizacion() &&
            ((this.mensaje==null && other.getMensaje()==null) || 
             (this.mensaje!=null &&
              this.mensaje.equals(other.getMensaje()))) &&
            ((this.saldoAbonado==null && other.getSaldoAbonado()==null) || 
             (this.saldoAbonado!=null &&
              this.saldoAbonado.equals(other.getSaldoAbonado())));
        __equalsCalc = null;
        return _equals;
    }

    private boolean __hashCodeCalc = false;
    public synchronized int hashCode() {
        if (__hashCodeCalc) {
            return 0;
        }
        __hashCodeCalc = true;
        int _hashCode = super.hashCode();
        _hashCode += new Long(getCodigoAutorizacion()).hashCode();
        if (getMensaje() != null) {
            _hashCode += getMensaje().hashCode();
        }
        if (getSaldoAbonado() != null) {
            _hashCode += getSaldoAbonado().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(RecargaOut.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://impl.ws.cajas.hyh.cl/", "recargaOut"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("codigoAutorizacion");
        elemField.setXmlName(new javax.xml.namespace.QName("", "codigoAutorizacion"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "long"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("mensaje");
        elemField.setXmlName(new javax.xml.namespace.QName("", "mensaje"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("saldoAbonado");
        elemField.setXmlName(new javax.xml.namespace.QName("", "saldoAbonado"));
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
