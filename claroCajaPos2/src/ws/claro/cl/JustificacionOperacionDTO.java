/**
 * JustificacionOperacionDTO.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package ws.claro.cl;

public class JustificacionOperacionDTO  implements java.io.Serializable {
    private java.lang.String cuentaCliente;

    private long numeroDocumento;

    private long rut;

    public JustificacionOperacionDTO() {
    }

    public JustificacionOperacionDTO(
           java.lang.String cuentaCliente,
           long numeroDocumento,
           long rut) {
           this.cuentaCliente = cuentaCliente;
           this.numeroDocumento = numeroDocumento;
           this.rut = rut;
    }


    /**
     * Gets the cuentaCliente value for this JustificacionOperacionDTO.
     * 
     * @return cuentaCliente
     */
    public java.lang.String getCuentaCliente() {
        return cuentaCliente;
    }


    /**
     * Sets the cuentaCliente value for this JustificacionOperacionDTO.
     * 
     * @param cuentaCliente
     */
    public void setCuentaCliente(java.lang.String cuentaCliente) {
        this.cuentaCliente = cuentaCliente;
    }


    /**
     * Gets the numeroDocumento value for this JustificacionOperacionDTO.
     * 
     * @return numeroDocumento
     */
    public long getNumeroDocumento() {
        return numeroDocumento;
    }


    /**
     * Sets the numeroDocumento value for this JustificacionOperacionDTO.
     * 
     * @param numeroDocumento
     */
    public void setNumeroDocumento(long numeroDocumento) {
        this.numeroDocumento = numeroDocumento;
    }


    /**
     * Gets the rut value for this JustificacionOperacionDTO.
     * 
     * @return rut
     */
    public long getRut() {
        return rut;
    }


    /**
     * Sets the rut value for this JustificacionOperacionDTO.
     * 
     * @param rut
     */
    public void setRut(long rut) {
        this.rut = rut;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof JustificacionOperacionDTO)) return false;
        JustificacionOperacionDTO other = (JustificacionOperacionDTO) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.cuentaCliente==null && other.getCuentaCliente()==null) || 
             (this.cuentaCliente!=null &&
              this.cuentaCliente.equals(other.getCuentaCliente()))) &&
            this.numeroDocumento == other.getNumeroDocumento() &&
            this.rut == other.getRut();
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
        if (getCuentaCliente() != null) {
            _hashCode += getCuentaCliente().hashCode();
        }
        _hashCode += new Long(getNumeroDocumento()).hashCode();
        _hashCode += new Long(getRut()).hashCode();
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(JustificacionOperacionDTO.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://cl.claro.ws/", "justificacionOperacionDTO"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("cuentaCliente");
        elemField.setXmlName(new javax.xml.namespace.QName("", "cuentaCliente"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("numeroDocumento");
        elemField.setXmlName(new javax.xml.namespace.QName("", "numeroDocumento"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "long"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("rut");
        elemField.setXmlName(new javax.xml.namespace.QName("", "rut"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "long"));
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
