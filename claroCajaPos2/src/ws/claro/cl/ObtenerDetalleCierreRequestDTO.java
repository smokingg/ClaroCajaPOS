/**
 * ObtenerDetalleCierreRequestDTO.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package ws.claro.cl;

public class ObtenerDetalleCierreRequestDTO  implements java.io.Serializable {
    private long codSesion;

    private java.lang.String fecha;

    private long idAgencia;

    private long idCaja;

    private long idCajero;

    public ObtenerDetalleCierreRequestDTO() {
    }

    public ObtenerDetalleCierreRequestDTO(
           long codSesion,
           java.lang.String fecha,
           long idAgencia,
           long idCaja,
           long idCajero) {
           this.codSesion = codSesion;
           this.fecha = fecha;
           this.idAgencia = idAgencia;
           this.idCaja = idCaja;
           this.idCajero = idCajero;
    }


    /**
     * Gets the codSesion value for this ObtenerDetalleCierreRequestDTO.
     * 
     * @return codSesion
     */
    public long getCodSesion() {
        return codSesion;
    }


    /**
     * Sets the codSesion value for this ObtenerDetalleCierreRequestDTO.
     * 
     * @param codSesion
     */
    public void setCodSesion(long codSesion) {
        this.codSesion = codSesion;
    }


    /**
     * Gets the fecha value for this ObtenerDetalleCierreRequestDTO.
     * 
     * @return fecha
     */
    public java.lang.String getFecha() {
        return fecha;
    }


    /**
     * Sets the fecha value for this ObtenerDetalleCierreRequestDTO.
     * 
     * @param fecha
     */
    public void setFecha(java.lang.String fecha) {
        this.fecha = fecha;
    }


    /**
     * Gets the idAgencia value for this ObtenerDetalleCierreRequestDTO.
     * 
     * @return idAgencia
     */
    public long getIdAgencia() {
        return idAgencia;
    }


    /**
     * Sets the idAgencia value for this ObtenerDetalleCierreRequestDTO.
     * 
     * @param idAgencia
     */
    public void setIdAgencia(long idAgencia) {
        this.idAgencia = idAgencia;
    }


    /**
     * Gets the idCaja value for this ObtenerDetalleCierreRequestDTO.
     * 
     * @return idCaja
     */
    public long getIdCaja() {
        return idCaja;
    }


    /**
     * Sets the idCaja value for this ObtenerDetalleCierreRequestDTO.
     * 
     * @param idCaja
     */
    public void setIdCaja(long idCaja) {
        this.idCaja = idCaja;
    }


    /**
     * Gets the idCajero value for this ObtenerDetalleCierreRequestDTO.
     * 
     * @return idCajero
     */
    public long getIdCajero() {
        return idCajero;
    }


    /**
     * Sets the idCajero value for this ObtenerDetalleCierreRequestDTO.
     * 
     * @param idCajero
     */
    public void setIdCajero(long idCajero) {
        this.idCajero = idCajero;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof ObtenerDetalleCierreRequestDTO)) return false;
        ObtenerDetalleCierreRequestDTO other = (ObtenerDetalleCierreRequestDTO) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            this.codSesion == other.getCodSesion() &&
            ((this.fecha==null && other.getFecha()==null) || 
             (this.fecha!=null &&
              this.fecha.equals(other.getFecha()))) &&
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
        _hashCode += new Long(getCodSesion()).hashCode();
        if (getFecha() != null) {
            _hashCode += getFecha().hashCode();
        }
        _hashCode += new Long(getIdAgencia()).hashCode();
        _hashCode += new Long(getIdCaja()).hashCode();
        _hashCode += new Long(getIdCajero()).hashCode();
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(ObtenerDetalleCierreRequestDTO.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://cl.claro.ws/", "obtenerDetalleCierreRequestDTO"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("codSesion");
        elemField.setXmlName(new javax.xml.namespace.QName("", "codSesion"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "long"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("fecha");
        elemField.setXmlName(new javax.xml.namespace.QName("", "fecha"));
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
