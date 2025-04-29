/**
 * EnviarVoucherTbkRequestDTO.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package ws.claro.cl;

public class EnviarVoucherTbkRequestDTO  implements java.io.Serializable {
    private java.lang.String comprobante;

    private java.lang.String emailPara;

    private java.lang.String numerOperacion;

    public EnviarVoucherTbkRequestDTO() {
    }

    public EnviarVoucherTbkRequestDTO(
           java.lang.String comprobante,
           java.lang.String emailPara,
           java.lang.String numerOperacion) {
           this.comprobante = comprobante;
           this.emailPara = emailPara;
           this.numerOperacion = numerOperacion;
    }


    /**
     * Gets the comprobante value for this EnviarVoucherTbkRequestDTO.
     * 
     * @return comprobante
     */
    public java.lang.String getComprobante() {
        return comprobante;
    }


    /**
     * Sets the comprobante value for this EnviarVoucherTbkRequestDTO.
     * 
     * @param comprobante
     */
    public void setComprobante(java.lang.String comprobante) {
        this.comprobante = comprobante;
    }


    /**
     * Gets the emailPara value for this EnviarVoucherTbkRequestDTO.
     * 
     * @return emailPara
     */
    public java.lang.String getEmailPara() {
        return emailPara;
    }


    /**
     * Sets the emailPara value for this EnviarVoucherTbkRequestDTO.
     * 
     * @param emailPara
     */
    public void setEmailPara(java.lang.String emailPara) {
        this.emailPara = emailPara;
    }


    /**
     * Gets the numerOperacion value for this EnviarVoucherTbkRequestDTO.
     * 
     * @return numerOperacion
     */
    public java.lang.String getNumerOperacion() {
        return numerOperacion;
    }


    /**
     * Sets the numerOperacion value for this EnviarVoucherTbkRequestDTO.
     * 
     * @param numerOperacion
     */
    public void setNumerOperacion(java.lang.String numerOperacion) {
        this.numerOperacion = numerOperacion;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof EnviarVoucherTbkRequestDTO)) return false;
        EnviarVoucherTbkRequestDTO other = (EnviarVoucherTbkRequestDTO) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.comprobante==null && other.getComprobante()==null) || 
             (this.comprobante!=null &&
              this.comprobante.equals(other.getComprobante()))) &&
            ((this.emailPara==null && other.getEmailPara()==null) || 
             (this.emailPara!=null &&
              this.emailPara.equals(other.getEmailPara()))) &&
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
        if (getComprobante() != null) {
            _hashCode += getComprobante().hashCode();
        }
        if (getEmailPara() != null) {
            _hashCode += getEmailPara().hashCode();
        }
        if (getNumerOperacion() != null) {
            _hashCode += getNumerOperacion().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(EnviarVoucherTbkRequestDTO.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://cl.claro.ws/", "enviarVoucherTbkRequestDTO"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("comprobante");
        elemField.setXmlName(new javax.xml.namespace.QName("", "comprobante"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("emailPara");
        elemField.setXmlName(new javax.xml.namespace.QName("", "emailPara"));
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
