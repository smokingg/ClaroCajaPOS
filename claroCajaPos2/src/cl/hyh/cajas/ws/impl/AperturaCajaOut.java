/**
 * AperturaCajaOut.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package cl.hyh.cajas.ws.impl;

public class AperturaCajaOut  extends cl.hyh.cajas.ws.impl.Response  implements java.io.Serializable {
    private int codRecaudador;

    private java.lang.String estado;

    private java.lang.String nombreRecaudador;

    private int sessionId;

    public AperturaCajaOut() {
    }

    public AperturaCajaOut(
           cl.hyh.cajas.ws.impl.HeaderOut headerOut,
           int codRecaudador,
           java.lang.String estado,
           java.lang.String nombreRecaudador,
           int sessionId) {
        super(
            headerOut);
        this.codRecaudador = codRecaudador;
        this.estado = estado;
        this.nombreRecaudador = nombreRecaudador;
        this.sessionId = sessionId;
    }


    /**
     * Gets the codRecaudador value for this AperturaCajaOut.
     * 
     * @return codRecaudador
     */
    public int getCodRecaudador() {
        return codRecaudador;
    }


    /**
     * Sets the codRecaudador value for this AperturaCajaOut.
     * 
     * @param codRecaudador
     */
    public void setCodRecaudador(int codRecaudador) {
        this.codRecaudador = codRecaudador;
    }


    /**
     * Gets the estado value for this AperturaCajaOut.
     * 
     * @return estado
     */
    public java.lang.String getEstado() {
        return estado;
    }


    /**
     * Sets the estado value for this AperturaCajaOut.
     * 
     * @param estado
     */
    public void setEstado(java.lang.String estado) {
        this.estado = estado;
    }


    /**
     * Gets the nombreRecaudador value for this AperturaCajaOut.
     * 
     * @return nombreRecaudador
     */
    public java.lang.String getNombreRecaudador() {
        return nombreRecaudador;
    }


    /**
     * Sets the nombreRecaudador value for this AperturaCajaOut.
     * 
     * @param nombreRecaudador
     */
    public void setNombreRecaudador(java.lang.String nombreRecaudador) {
        this.nombreRecaudador = nombreRecaudador;
    }


    /**
     * Gets the sessionId value for this AperturaCajaOut.
     * 
     * @return sessionId
     */
    public int getSessionId() {
        return sessionId;
    }


    /**
     * Sets the sessionId value for this AperturaCajaOut.
     * 
     * @param sessionId
     */
    public void setSessionId(int sessionId) {
        this.sessionId = sessionId;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof AperturaCajaOut)) return false;
        AperturaCajaOut other = (AperturaCajaOut) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = super.equals(obj) && 
            this.codRecaudador == other.getCodRecaudador() &&
            ((this.estado==null && other.getEstado()==null) || 
             (this.estado!=null &&
              this.estado.equals(other.getEstado()))) &&
            ((this.nombreRecaudador==null && other.getNombreRecaudador()==null) || 
             (this.nombreRecaudador!=null &&
              this.nombreRecaudador.equals(other.getNombreRecaudador()))) &&
            this.sessionId == other.getSessionId();
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
        _hashCode += getCodRecaudador();
        if (getEstado() != null) {
            _hashCode += getEstado().hashCode();
        }
        if (getNombreRecaudador() != null) {
            _hashCode += getNombreRecaudador().hashCode();
        }
        _hashCode += getSessionId();
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(AperturaCajaOut.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://impl.ws.cajas.hyh.cl/", "aperturaCajaOut"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("codRecaudador");
        elemField.setXmlName(new javax.xml.namespace.QName("", "codRecaudador"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("estado");
        elemField.setXmlName(new javax.xml.namespace.QName("", "estado"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("nombreRecaudador");
        elemField.setXmlName(new javax.xml.namespace.QName("", "nombreRecaudador"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("sessionId");
        elemField.setXmlName(new javax.xml.namespace.QName("", "sessionId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
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
