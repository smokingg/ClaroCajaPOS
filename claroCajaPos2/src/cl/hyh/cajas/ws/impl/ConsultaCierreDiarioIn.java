/**
 * ConsultaCierreDiarioIn.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package cl.hyh.cajas.ws.impl;

public class ConsultaCierreDiarioIn  extends cl.hyh.cajas.ws.impl.Request  implements java.io.Serializable {
    private int agencia;

    private java.lang.String fechaArqueo;

    private java.lang.String usuario;

    public ConsultaCierreDiarioIn() {
    }

    public ConsultaCierreDiarioIn(
           cl.hyh.cajas.ws.impl.HeaderIn headerIn,
           int agencia,
           java.lang.String fechaArqueo,
           java.lang.String usuario) {
        super(
            headerIn);
        this.agencia = agencia;
        this.fechaArqueo = fechaArqueo;
        this.usuario = usuario;
    }


    /**
     * Gets the agencia value for this ConsultaCierreDiarioIn.
     * 
     * @return agencia
     */
    public int getAgencia() {
        return agencia;
    }


    /**
     * Sets the agencia value for this ConsultaCierreDiarioIn.
     * 
     * @param agencia
     */
    public void setAgencia(int agencia) {
        this.agencia = agencia;
    }


    /**
     * Gets the fechaArqueo value for this ConsultaCierreDiarioIn.
     * 
     * @return fechaArqueo
     */
    public java.lang.String getFechaArqueo() {
        return fechaArqueo;
    }


    /**
     * Sets the fechaArqueo value for this ConsultaCierreDiarioIn.
     * 
     * @param fechaArqueo
     */
    public void setFechaArqueo(java.lang.String fechaArqueo) {
        this.fechaArqueo = fechaArqueo;
    }


    /**
     * Gets the usuario value for this ConsultaCierreDiarioIn.
     * 
     * @return usuario
     */
    public java.lang.String getUsuario() {
        return usuario;
    }


    /**
     * Sets the usuario value for this ConsultaCierreDiarioIn.
     * 
     * @param usuario
     */
    public void setUsuario(java.lang.String usuario) {
        this.usuario = usuario;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof ConsultaCierreDiarioIn)) return false;
        ConsultaCierreDiarioIn other = (ConsultaCierreDiarioIn) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = super.equals(obj) && 
            this.agencia == other.getAgencia() &&
            ((this.fechaArqueo==null && other.getFechaArqueo()==null) || 
             (this.fechaArqueo!=null &&
              this.fechaArqueo.equals(other.getFechaArqueo()))) &&
            ((this.usuario==null && other.getUsuario()==null) || 
             (this.usuario!=null &&
              this.usuario.equals(other.getUsuario())));
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
        _hashCode += getAgencia();
        if (getFechaArqueo() != null) {
            _hashCode += getFechaArqueo().hashCode();
        }
        if (getUsuario() != null) {
            _hashCode += getUsuario().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(ConsultaCierreDiarioIn.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://impl.ws.cajas.hyh.cl/", "consultaCierreDiarioIn"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("agencia");
        elemField.setXmlName(new javax.xml.namespace.QName("", "agencia"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("fechaArqueo");
        elemField.setXmlName(new javax.xml.namespace.QName("", "fechaArqueo"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("usuario");
        elemField.setXmlName(new javax.xml.namespace.QName("", "usuario"));
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
