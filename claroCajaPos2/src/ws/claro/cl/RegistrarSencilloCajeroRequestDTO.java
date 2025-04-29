/**
 * RegistrarSencilloCajeroRequestDTO.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package ws.claro.cl;

public class RegistrarSencilloCajeroRequestDTO  implements java.io.Serializable {
    private java.lang.String fechaRecibo;

    private long idAgencia;

    private long idCaja;

    private long idCajero;

    private long montoRecibido;

    public RegistrarSencilloCajeroRequestDTO() {
    }

    public RegistrarSencilloCajeroRequestDTO(
           java.lang.String fechaRecibo,
           long idAgencia,
           long idCaja,
           long idCajero,
           long montoRecibido) {
           this.fechaRecibo = fechaRecibo;
           this.idAgencia = idAgencia;
           this.idCaja = idCaja;
           this.idCajero = idCajero;
           this.montoRecibido = montoRecibido;
    }


    /**
     * Gets the fechaRecibo value for this RegistrarSencilloCajeroRequestDTO.
     * 
     * @return fechaRecibo
     */
    public java.lang.String getFechaRecibo() {
        return fechaRecibo;
    }


    /**
     * Sets the fechaRecibo value for this RegistrarSencilloCajeroRequestDTO.
     * 
     * @param fechaRecibo
     */
    public void setFechaRecibo(java.lang.String fechaRecibo) {
        this.fechaRecibo = fechaRecibo;
    }


    /**
     * Gets the idAgencia value for this RegistrarSencilloCajeroRequestDTO.
     * 
     * @return idAgencia
     */
    public long getIdAgencia() {
        return idAgencia;
    }


    /**
     * Sets the idAgencia value for this RegistrarSencilloCajeroRequestDTO.
     * 
     * @param idAgencia
     */
    public void setIdAgencia(long idAgencia) {
        this.idAgencia = idAgencia;
    }


    /**
     * Gets the idCaja value for this RegistrarSencilloCajeroRequestDTO.
     * 
     * @return idCaja
     */
    public long getIdCaja() {
        return idCaja;
    }


    /**
     * Sets the idCaja value for this RegistrarSencilloCajeroRequestDTO.
     * 
     * @param idCaja
     */
    public void setIdCaja(long idCaja) {
        this.idCaja = idCaja;
    }


    /**
     * Gets the idCajero value for this RegistrarSencilloCajeroRequestDTO.
     * 
     * @return idCajero
     */
    public long getIdCajero() {
        return idCajero;
    }


    /**
     * Sets the idCajero value for this RegistrarSencilloCajeroRequestDTO.
     * 
     * @param idCajero
     */
    public void setIdCajero(long idCajero) {
        this.idCajero = idCajero;
    }


    /**
     * Gets the montoRecibido value for this RegistrarSencilloCajeroRequestDTO.
     * 
     * @return montoRecibido
     */
    public long getMontoRecibido() {
        return montoRecibido;
    }


    /**
     * Sets the montoRecibido value for this RegistrarSencilloCajeroRequestDTO.
     * 
     * @param montoRecibido
     */
    public void setMontoRecibido(long montoRecibido) {
        this.montoRecibido = montoRecibido;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof RegistrarSencilloCajeroRequestDTO)) return false;
        RegistrarSencilloCajeroRequestDTO other = (RegistrarSencilloCajeroRequestDTO) obj;
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
            this.idCajero == other.getIdCajero() &&
            this.montoRecibido == other.getMontoRecibido();
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
        _hashCode += new Long(getMontoRecibido()).hashCode();
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(RegistrarSencilloCajeroRequestDTO.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://cl.claro.ws/", "registrarSencilloCajeroRequestDTO"));
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
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("montoRecibido");
        elemField.setXmlName(new javax.xml.namespace.QName("", "montoRecibido"));
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
