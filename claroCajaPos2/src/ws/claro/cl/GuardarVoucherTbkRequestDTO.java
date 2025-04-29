/**
 * GuardarVoucherTbkRequestDTO.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package ws.claro.cl;

public class GuardarVoucherTbkRequestDTO  implements java.io.Serializable {
    private java.lang.String codigoSesion;

    private java.lang.String codigoUsuarioEnvia;

    private java.lang.String comprobante;

    private java.lang.String emailPara;

    private java.lang.String numerOperacion;

    private java.lang.String propietario;

    private java.lang.String tipOperacion;

    private java.lang.String tipoComprobante;

    private java.lang.String tipoCopia;

    public GuardarVoucherTbkRequestDTO() {
    }

    public GuardarVoucherTbkRequestDTO(
           java.lang.String codigoSesion,
           java.lang.String codigoUsuarioEnvia,
           java.lang.String comprobante,
           java.lang.String emailPara,
           java.lang.String numerOperacion,
           java.lang.String propietario,
           java.lang.String tipOperacion,
           java.lang.String tipoComprobante,
           java.lang.String tipoCopia) {
           this.codigoSesion = codigoSesion;
           this.codigoUsuarioEnvia = codigoUsuarioEnvia;
           this.comprobante = comprobante;
           this.emailPara = emailPara;
           this.numerOperacion = numerOperacion;
           this.propietario = propietario;
           this.tipOperacion = tipOperacion;
           this.tipoComprobante = tipoComprobante;
           this.tipoCopia = tipoCopia;
    }


    /**
     * Gets the codigoSesion value for this GuardarVoucherTbkRequestDTO.
     * 
     * @return codigoSesion
     */
    public java.lang.String getCodigoSesion() {
        return codigoSesion;
    }


    /**
     * Sets the codigoSesion value for this GuardarVoucherTbkRequestDTO.
     * 
     * @param codigoSesion
     */
    public void setCodigoSesion(java.lang.String codigoSesion) {
        this.codigoSesion = codigoSesion;
    }


    /**
     * Gets the codigoUsuarioEnvia value for this GuardarVoucherTbkRequestDTO.
     * 
     * @return codigoUsuarioEnvia
     */
    public java.lang.String getCodigoUsuarioEnvia() {
        return codigoUsuarioEnvia;
    }


    /**
     * Sets the codigoUsuarioEnvia value for this GuardarVoucherTbkRequestDTO.
     * 
     * @param codigoUsuarioEnvia
     */
    public void setCodigoUsuarioEnvia(java.lang.String codigoUsuarioEnvia) {
        this.codigoUsuarioEnvia = codigoUsuarioEnvia;
    }


    /**
     * Gets the comprobante value for this GuardarVoucherTbkRequestDTO.
     * 
     * @return comprobante
     */
    public java.lang.String getComprobante() {
        return comprobante;
    }


    /**
     * Sets the comprobante value for this GuardarVoucherTbkRequestDTO.
     * 
     * @param comprobante
     */
    public void setComprobante(java.lang.String comprobante) {
        this.comprobante = comprobante;
    }


    /**
     * Gets the emailPara value for this GuardarVoucherTbkRequestDTO.
     * 
     * @return emailPara
     */
    public java.lang.String getEmailPara() {
        return emailPara;
    }


    /**
     * Sets the emailPara value for this GuardarVoucherTbkRequestDTO.
     * 
     * @param emailPara
     */
    public void setEmailPara(java.lang.String emailPara) {
        this.emailPara = emailPara;
    }


    /**
     * Gets the numerOperacion value for this GuardarVoucherTbkRequestDTO.
     * 
     * @return numerOperacion
     */
    public java.lang.String getNumerOperacion() {
        return numerOperacion;
    }


    /**
     * Sets the numerOperacion value for this GuardarVoucherTbkRequestDTO.
     * 
     * @param numerOperacion
     */
    public void setNumerOperacion(java.lang.String numerOperacion) {
        this.numerOperacion = numerOperacion;
    }


    /**
     * Gets the propietario value for this GuardarVoucherTbkRequestDTO.
     * 
     * @return propietario
     */
    public java.lang.String getPropietario() {
        return propietario;
    }


    /**
     * Sets the propietario value for this GuardarVoucherTbkRequestDTO.
     * 
     * @param propietario
     */
    public void setPropietario(java.lang.String propietario) {
        this.propietario = propietario;
    }


    /**
     * Gets the tipOperacion value for this GuardarVoucherTbkRequestDTO.
     * 
     * @return tipOperacion
     */
    public java.lang.String getTipOperacion() {
        return tipOperacion;
    }


    /**
     * Sets the tipOperacion value for this GuardarVoucherTbkRequestDTO.
     * 
     * @param tipOperacion
     */
    public void setTipOperacion(java.lang.String tipOperacion) {
        this.tipOperacion = tipOperacion;
    }


    /**
     * Gets the tipoComprobante value for this GuardarVoucherTbkRequestDTO.
     * 
     * @return tipoComprobante
     */
    public java.lang.String getTipoComprobante() {
        return tipoComprobante;
    }


    /**
     * Sets the tipoComprobante value for this GuardarVoucherTbkRequestDTO.
     * 
     * @param tipoComprobante
     */
    public void setTipoComprobante(java.lang.String tipoComprobante) {
        this.tipoComprobante = tipoComprobante;
    }


    /**
     * Gets the tipoCopia value for this GuardarVoucherTbkRequestDTO.
     * 
     * @return tipoCopia
     */
    public java.lang.String getTipoCopia() {
        return tipoCopia;
    }


    /**
     * Sets the tipoCopia value for this GuardarVoucherTbkRequestDTO.
     * 
     * @param tipoCopia
     */
    public void setTipoCopia(java.lang.String tipoCopia) {
        this.tipoCopia = tipoCopia;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof GuardarVoucherTbkRequestDTO)) return false;
        GuardarVoucherTbkRequestDTO other = (GuardarVoucherTbkRequestDTO) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.codigoSesion==null && other.getCodigoSesion()==null) || 
             (this.codigoSesion!=null &&
              this.codigoSesion.equals(other.getCodigoSesion()))) &&
            ((this.codigoUsuarioEnvia==null && other.getCodigoUsuarioEnvia()==null) || 
             (this.codigoUsuarioEnvia!=null &&
              this.codigoUsuarioEnvia.equals(other.getCodigoUsuarioEnvia()))) &&
            ((this.comprobante==null && other.getComprobante()==null) || 
             (this.comprobante!=null &&
              this.comprobante.equals(other.getComprobante()))) &&
            ((this.emailPara==null && other.getEmailPara()==null) || 
             (this.emailPara!=null &&
              this.emailPara.equals(other.getEmailPara()))) &&
            ((this.numerOperacion==null && other.getNumerOperacion()==null) || 
             (this.numerOperacion!=null &&
              this.numerOperacion.equals(other.getNumerOperacion()))) &&
            ((this.propietario==null && other.getPropietario()==null) || 
             (this.propietario!=null &&
              this.propietario.equals(other.getPropietario()))) &&
            ((this.tipOperacion==null && other.getTipOperacion()==null) || 
             (this.tipOperacion!=null &&
              this.tipOperacion.equals(other.getTipOperacion()))) &&
            ((this.tipoComprobante==null && other.getTipoComprobante()==null) || 
             (this.tipoComprobante!=null &&
              this.tipoComprobante.equals(other.getTipoComprobante()))) &&
            ((this.tipoCopia==null && other.getTipoCopia()==null) || 
             (this.tipoCopia!=null &&
              this.tipoCopia.equals(other.getTipoCopia())));
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
        if (getCodigoSesion() != null) {
            _hashCode += getCodigoSesion().hashCode();
        }
        if (getCodigoUsuarioEnvia() != null) {
            _hashCode += getCodigoUsuarioEnvia().hashCode();
        }
        if (getComprobante() != null) {
            _hashCode += getComprobante().hashCode();
        }
        if (getEmailPara() != null) {
            _hashCode += getEmailPara().hashCode();
        }
        if (getNumerOperacion() != null) {
            _hashCode += getNumerOperacion().hashCode();
        }
        if (getPropietario() != null) {
            _hashCode += getPropietario().hashCode();
        }
        if (getTipOperacion() != null) {
            _hashCode += getTipOperacion().hashCode();
        }
        if (getTipoComprobante() != null) {
            _hashCode += getTipoComprobante().hashCode();
        }
        if (getTipoCopia() != null) {
            _hashCode += getTipoCopia().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(GuardarVoucherTbkRequestDTO.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://cl.claro.ws/", "guardarVoucherTbkRequestDTO"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("codigoSesion");
        elemField.setXmlName(new javax.xml.namespace.QName("", "codigoSesion"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("codigoUsuarioEnvia");
        elemField.setXmlName(new javax.xml.namespace.QName("", "codigoUsuarioEnvia"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
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
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("propietario");
        elemField.setXmlName(new javax.xml.namespace.QName("", "propietario"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("tipOperacion");
        elemField.setXmlName(new javax.xml.namespace.QName("", "tipOperacion"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("tipoComprobante");
        elemField.setXmlName(new javax.xml.namespace.QName("", "tipoComprobante"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("tipoCopia");
        elemField.setXmlName(new javax.xml.namespace.QName("", "tipoCopia"));
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
