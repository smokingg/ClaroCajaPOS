/**
 * ValidarSencilloCajeroRequestDTO.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package ws.claro.cl;

public class ValidarSencilloCajeroRequestDTO  implements java.io.Serializable {
    private java.lang.String fechaRecibo;

    private long idAgencia;

    private long idCaja;

    private long idCajero;

    public ValidarSencilloCajeroRequestDTO() {
    }

    public ValidarSencilloCajeroRequestDTO(
           java.lang.String fechaRecibo,
           long idAgencia,
           long idCaja,
           long idCajero) {
           this.fechaRecibo = fechaRecibo;
           this.idAgencia = idAgencia;
           this.idCaja = idCaja;
           this.idCajero = idCajero;
    }


    /**
     * Gets the fechaRecibo value for this ValidarSencilloCajeroRequestDTO.
     * 
     * @return fechaRecibo
     */
    public java.lang.String getFechaRecibo() {
        return fechaRecibo;
    }


    /**
     * Sets the fechaRecibo value for this ValidarSencilloCajeroRequestDTO.
     * 
     * @param fechaRecibo
     */
    public void setFechaRecibo(java.lang.String fechaRecibo) {
        this.fechaRecibo = fechaRecibo;
    }


    /**
     * Gets the idAgencia value for this ValidarSencilloCajeroRequestDTO.
     * 
     * @return idAgencia
     */
    public long getIdAgencia() {
        return idAgencia;
    }


    /**
     * Sets the idAgencia value for this ValidarSencilloCajeroRequestDTO.
     * 
     * @param idAgencia
     */
    public void setIdAgencia(long idAgencia) {
        this.idAgencia = idAgencia;
    }


    /**
     * Gets the idCaja value for this ValidarSencilloCajeroRequestDTO.
     * 
     * @return idCaja
     */
    public long getIdCaja() {
        return idCaja;
    }


    /**
     * Sets the idCaja value for this ValidarSencilloCajeroRequestDTO.
     * 
     * @param idCaja
     */
    public void setIdCaja(long idCaja) {
        this.idCaja = idCaja;
    }


    /**
     * Gets the idCajero value for this ValidarSencilloCajeroRequestDTO.
     * 
     * @return idCajero
     */
    public long getIdCajero() {
        return idCajero;
    }


    /**
     * Sets the idCajero value for this ValidarSencilloCajeroRequestDTO.
     * 
     * @param idCajero
     */
    public void setIdCajero(long idCajero) {
        this.idCajero = idCajero;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof ValidarSencilloCajeroRequestDTO)) return false;
        ValidarSencilloCajeroRequestDTO other = (ValidarSencilloCajeroRequestDTO) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.fechaRecibo==null && other.getFechaRecibo()==null) || 
             (this.fechaRecibo!=null &&
              this.fechaRecibo.equals(other.getFechaRecibo()))) &&
            this.idAgencia == other.getIdAgencia() &&
            this.idCaja == other.getIdCaja() &&
            this.idCajero == other.getIdCajero();
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
        if (getFechaRecibo() != null) {
            _hashCode += getFechaRecibo().hashCode();
        }
        _hashCode += new Long(getIdAgencia()).hashCode();
        _hashCode += new Long(getIdCaja()).hashCode();
        _hashCode += new Long(getIdCajero()).hashCode();
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(ValidarSencilloCajeroRequestDTO.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://cl.claro.ws/", "validarSencilloCajeroRequestDTO"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("fechaRecibo");
        elemField.setXmlName(new javax.xml.namespace.QName("", "fechaRecibo"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("idAgencia");
        elemField.setXmlName(new javax.xml.namespace.QName("", "idAgencia"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "long"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("idCaja");
        elemField.setXmlName(new javax.xml.namespace.QName("", "idCaja"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "long"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("idCajero");
        elemField.setXmlName(new javax.xml.namespace.QName("", "idCajero"));
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
