/**
 * DenominacionDTO.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package ws.claro.cl;

public class DenominacionDTO  implements java.io.Serializable {
    private int codDenominacion;

    private java.lang.String denominacion;

    private int montoMaximo;

    private int montoMinimo;

    public DenominacionDTO() {
    }

    public DenominacionDTO(
           int codDenominacion,
           java.lang.String denominacion,
           int montoMaximo,
           int montoMinimo) {
           this.codDenominacion = codDenominacion;
           this.denominacion = denominacion;
           this.montoMaximo = montoMaximo;
           this.montoMinimo = montoMinimo;
    }


    /**
     * Gets the codDenominacion value for this DenominacionDTO.
     * 
     * @return codDenominacion
     */
    public int getCodDenominacion() {
        return codDenominacion;
    }


    /**
     * Sets the codDenominacion value for this DenominacionDTO.
     * 
     * @param codDenominacion
     */
    public void setCodDenominacion(int codDenominacion) {
        this.codDenominacion = codDenominacion;
    }


    /**
     * Gets the denominacion value for this DenominacionDTO.
     * 
     * @return denominacion
     */
    public java.lang.String getDenominacion() {
        return denominacion;
    }


    /**
     * Sets the denominacion value for this DenominacionDTO.
     * 
     * @param denominacion
     */
    public void setDenominacion(java.lang.String denominacion) {
        this.denominacion = denominacion;
    }


    /**
     * Gets the montoMaximo value for this DenominacionDTO.
     * 
     * @return montoMaximo
     */
    public int getMontoMaximo() {
        return montoMaximo;
    }


    /**
     * Sets the montoMaximo value for this DenominacionDTO.
     * 
     * @param montoMaximo
     */
    public void setMontoMaximo(int montoMaximo) {
        this.montoMaximo = montoMaximo;
    }


    /**
     * Gets the montoMinimo value for this DenominacionDTO.
     * 
     * @return montoMinimo
     */
    public int getMontoMinimo() {
        return montoMinimo;
    }


    /**
     * Sets the montoMinimo value for this DenominacionDTO.
     * 
     * @param montoMinimo
     */
    public void setMontoMinimo(int montoMinimo) {
        this.montoMinimo = montoMinimo;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof DenominacionDTO)) return false;
        DenominacionDTO other = (DenominacionDTO) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            this.codDenominacion == other.getCodDenominacion() &&
            ((this.denominacion==null && other.getDenominacion()==null) || 
             (this.denominacion!=null &&
              this.denominacion.equals(other.getDenominacion()))) &&
            this.montoMaximo == other.getMontoMaximo() &&
            this.montoMinimo == other.getMontoMinimo();
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
        _hashCode += getCodDenominacion();
        if (getDenominacion() != null) {
            _hashCode += getDenominacion().hashCode();
        }
        _hashCode += getMontoMaximo();
        _hashCode += getMontoMinimo();
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(DenominacionDTO.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://cl.claro.ws/", "denominacionDTO"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("codDenominacion");
        elemField.setXmlName(new javax.xml.namespace.QName("", "codDenominacion"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("denominacion");
        elemField.setXmlName(new javax.xml.namespace.QName("", "denominacion"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("montoMaximo");
        elemField.setXmlName(new javax.xml.namespace.QName("", "montoMaximo"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("montoMinimo");
        elemField.setXmlName(new javax.xml.namespace.QName("", "montoMinimo"));
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
