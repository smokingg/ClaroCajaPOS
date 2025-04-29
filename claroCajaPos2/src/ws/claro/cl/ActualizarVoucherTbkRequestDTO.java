/**
 * ActualizarVoucherTbkRequestDTO.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package ws.claro.cl;

public class ActualizarVoucherTbkRequestDTO  implements java.io.Serializable {
    private java.lang.String idComprobanteAdjunto;

    private java.lang.String idFilenet;

    private java.lang.String numerOperacion;

    public ActualizarVoucherTbkRequestDTO() {
    }

    public ActualizarVoucherTbkRequestDTO(
           java.lang.String idComprobanteAdjunto,
           java.lang.String idFilenet,
           java.lang.String numerOperacion) {
           this.idComprobanteAdjunto = idComprobanteAdjunto;
           this.idFilenet = idFilenet;
           this.numerOperacion = numerOperacion;
    }


    /**
     * Gets the idComprobanteAdjunto value for this ActualizarVoucherTbkRequestDTO.
     * 
     * @return idComprobanteAdjunto
     */
    public java.lang.String getIdComprobanteAdjunto() {
        return idComprobanteAdjunto;
    }


    /**
     * Sets the idComprobanteAdjunto value for this ActualizarVoucherTbkRequestDTO.
     * 
     * @param idComprobanteAdjunto
     */
    public void setIdComprobanteAdjunto(java.lang.String idComprobanteAdjunto) {
        this.idComprobanteAdjunto = idComprobanteAdjunto;
    }


    /**
     * Gets the idFilenet value for this ActualizarVoucherTbkRequestDTO.
     * 
     * @return idFilenet
     */
    public java.lang.String getIdFilenet() {
        return idFilenet;
    }


    /**
     * Sets the idFilenet value for this ActualizarVoucherTbkRequestDTO.
     * 
     * @param idFilenet
     */
    public void setIdFilenet(java.lang.String idFilenet) {
        this.idFilenet = idFilenet;
    }


    /**
     * Gets the numerOperacion value for this ActualizarVoucherTbkRequestDTO.
     * 
     * @return numerOperacion
     */
    public java.lang.String getNumerOperacion() {
        return numerOperacion;
    }


    /**
     * Sets the numerOperacion value for this ActualizarVoucherTbkRequestDTO.
     * 
     * @param numerOperacion
     */
    public void setNumerOperacion(java.lang.String numerOperacion) {
        this.numerOperacion = numerOperacion;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof ActualizarVoucherTbkRequestDTO)) return false;
        ActualizarVoucherTbkRequestDTO other = (ActualizarVoucherTbkRequestDTO) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.idComprobanteAdjunto==null && other.getIdComprobanteAdjunto()==null) || 
             (this.idComprobanteAdjunto!=null &&
              this.idComprobanteAdjunto.equals(other.getIdComprobanteAdjunto()))) &&
            ((this.idFilenet==null && other.getIdFilenet()==null) || 
             (this.idFilenet!=null &&
              this.idFilenet.equals(other.getIdFilenet()))) &&
            ((this.numerOperacion==null && other.getNumerOperacion()==null) || 
             (this.numerOperacion!=null &&
              this.numerOperacion.equals(other.getNumerOperacion())));
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
        if (getIdComprobanteAdjunto() != null) {
            _hashCode += getIdComprobanteAdjunto().hashCode();
        }
        if (getIdFilenet() != null) {
            _hashCode += getIdFilenet().hashCode();
        }
        if (getNumerOperacion() != null) {
            _hashCode += getNumerOperacion().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(ActualizarVoucherTbkRequestDTO.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://cl.claro.ws/", "actualizarVoucherTbkRequestDTO"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("idComprobanteAdjunto");
        elemField.setXmlName(new javax.xml.namespace.QName("", "idComprobanteAdjunto"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("idFilenet");
        elemField.setXmlName(new javax.xml.namespace.QName("", "idFilenet"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("numerOperacion");
        elemField.setXmlName(new javax.xml.namespace.QName("", "numerOperacion"));
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
