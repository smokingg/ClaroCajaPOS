/**
 * RegistrarSencilloCierreRequestDTO.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package ws.claro.cl;

public class RegistrarSencilloCierreRequestDTO  implements java.io.Serializable {
    private java.lang.String fechaRecibo;

    private long idAgencia;

    private long idCaja;

    private long idCajero;

    private long montoCierre;

    public RegistrarSencilloCierreRequestDTO() {
    }

    public RegistrarSencilloCierreRequestDTO(
           java.lang.String fechaRecibo,
           long idAgencia,
           long idCaja,
           long idCajero,
           long montoCierre) {
           this.fechaRecibo = fechaRecibo;
           this.idAgencia = idAgencia;
           this.idCaja = idCaja;
           this.idCajero = idCajero;
           this.montoCierre = montoCierre;
    }


    /**
     * Gets the fechaRecibo value for this RegistrarSencilloCierreRequestDTO.
     * 
     * @return fechaRecibo
     */
    public java.lang.String getFechaRecibo() {
        return fechaRecibo;
    }


    /**
     * Sets the fechaRecibo value for this RegistrarSencilloCierreRequestDTO.
     * 
     * @param fechaRecibo
     */
    public void setFechaRecibo(java.lang.String fechaRecibo) {
        this.fechaRecibo = fechaRecibo;
    }


    /**
     * Gets the idAgencia value for this RegistrarSencilloCierreRequestDTO.
     * 
     * @return idAgencia
     */
    public long getIdAgencia() {
        return idAgencia;
    }


    /**
     * Sets the idAgencia value for this RegistrarSencilloCierreRequestDTO.
     * 
     * @param idAgencia
     */
    public void setIdAgencia(long idAgencia) {
        this.idAgencia = idAgencia;
    }


    /**
     * Gets the idCaja value for this RegistrarSencilloCierreRequestDTO.
     * 
     * @return idCaja
     */
    public long getIdCaja() {
        return idCaja;
    }


    /**
     * Sets the idCaja value for this RegistrarSencilloCierreRequestDTO.
     * 
     * @param idCaja
     */
    public void setIdCaja(long idCaja) {
        this.idCaja = idCaja;
    }


    /**
     * Gets the idCajero value for this RegistrarSencilloCierreRequestDTO.
     * 
     * @return idCajero
     */
    public long getIdCajero() {
        return idCajero;
    }


    /**
     * Sets the idCajero value for this RegistrarSencilloCierreRequestDTO.
     * 
     * @param idCajero
     */
    public void setIdCajero(long idCajero) {
        this.idCajero = idCajero;
    }


    /**
     * Gets the montoCierre value for this RegistrarSencilloCierreRequestDTO.
     * 
     * @return montoCierre
     */
    public long getMontoCierre() {
        return montoCierre;
    }


    /**
     * Sets the montoCierre value for this RegistrarSencilloCierreRequestDTO.
     * 
     * @param montoCierre
     */
    public void setMontoCierre(long montoCierre) {
        this.montoCierre = montoCierre;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof RegistrarSencilloCierreRequestDTO)) return false;
        RegistrarSencilloCierreRequestDTO other = (RegistrarSencilloCierreRequestDTO) obj;
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
            this.montoCierre == other.getMontoCierre();
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
        _hashCode += new Long(getMontoCierre()).hashCode();
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(RegistrarSencilloCierreRequestDTO.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://cl.claro.ws/", "registrarSencilloCierreRequestDTO"));
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
        elemField.setFieldName("montoCierre");
        elemField.setXmlName(new javax.xml.namespace.QName("", "montoCierre"));
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
