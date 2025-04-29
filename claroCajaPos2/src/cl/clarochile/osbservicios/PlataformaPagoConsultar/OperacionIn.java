/**
 * OperacionIn.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package cl.clarochile.osbservicios.PlataformaPagoConsultar;

public class OperacionIn  implements java.io.Serializable {
    private cl.clarochile.osbservicios.PlataformaPagoConsultar.Caja caja;

    private java.lang.String tipoOperacion;

    private java.lang.String valorOperacion;

    private java.lang.String origen;

    private java.lang.Integer codigoEmpresa;

    private java.lang.String tipoDocumento;

    private java.lang.String tipoRegistro;

    public OperacionIn() {
    }

    public OperacionIn(
           cl.clarochile.osbservicios.PlataformaPagoConsultar.Caja caja,
           java.lang.String tipoOperacion,
           java.lang.String valorOperacion,
           java.lang.String origen,
           java.lang.Integer codigoEmpresa,
           java.lang.String tipoDocumento,
           java.lang.String tipoRegistro) {
           this.caja = caja;
           this.tipoOperacion = tipoOperacion;
           this.valorOperacion = valorOperacion;
           this.origen = origen;
           this.codigoEmpresa = codigoEmpresa;
           this.tipoDocumento = tipoDocumento;
           this.tipoRegistro = tipoRegistro;
    }


    /**
     * Gets the caja value for this OperacionIn.
     * 
     * @return caja
     */
    public cl.clarochile.osbservicios.PlataformaPagoConsultar.Caja getCaja() {
        return caja;
    }


    /**
     * Sets the caja value for this OperacionIn.
     * 
     * @param caja
     */
    public void setCaja(cl.clarochile.osbservicios.PlataformaPagoConsultar.Caja caja) {
        this.caja = caja;
    }


    /**
     * Gets the tipoOperacion value for this OperacionIn.
     * 
     * @return tipoOperacion
     */
    public java.lang.String getTipoOperacion() {
        return tipoOperacion;
    }


    /**
     * Sets the tipoOperacion value for this OperacionIn.
     * 
     * @param tipoOperacion
     */
    public void setTipoOperacion(java.lang.String tipoOperacion) {
        this.tipoOperacion = tipoOperacion;
    }


    /**
     * Gets the valorOperacion value for this OperacionIn.
     * 
     * @return valorOperacion
     */
    public java.lang.String getValorOperacion() {
        return valorOperacion;
    }


    /**
     * Sets the valorOperacion value for this OperacionIn.
     * 
     * @param valorOperacion
     */
    public void setValorOperacion(java.lang.String valorOperacion) {
        this.valorOperacion = valorOperacion;
    }


    /**
     * Gets the origen value for this OperacionIn.
     * 
     * @return origen
     */
    public java.lang.String getOrigen() {
        return origen;
    }


    /**
     * Sets the origen value for this OperacionIn.
     * 
     * @param origen
     */
    public void setOrigen(java.lang.String origen) {
        this.origen = origen;
    }


    /**
     * Gets the codigoEmpresa value for this OperacionIn.
     * 
     * @return codigoEmpresa
     */
    public java.lang.Integer getCodigoEmpresa() {
        return codigoEmpresa;
    }


    /**
     * Sets the codigoEmpresa value for this OperacionIn.
     * 
     * @param codigoEmpresa
     */
    public void setCodigoEmpresa(java.lang.Integer codigoEmpresa) {
        this.codigoEmpresa = codigoEmpresa;
    }


    /**
     * Gets the tipoDocumento value for this OperacionIn.
     * 
     * @return tipoDocumento
     */
    public java.lang.String getTipoDocumento() {
        return tipoDocumento;
    }


    /**
     * Sets the tipoDocumento value for this OperacionIn.
     * 
     * @param tipoDocumento
     */
    public void setTipoDocumento(java.lang.String tipoDocumento) {
        this.tipoDocumento = tipoDocumento;
    }


    /**
     * Gets the tipoRegistro value for this OperacionIn.
     * 
     * @return tipoRegistro
     */
    public java.lang.String getTipoRegistro() {
        return tipoRegistro;
    }


    /**
     * Sets the tipoRegistro value for this OperacionIn.
     * 
     * @param tipoRegistro
     */
    public void setTipoRegistro(java.lang.String tipoRegistro) {
        this.tipoRegistro = tipoRegistro;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof OperacionIn)) return false;
        OperacionIn other = (OperacionIn) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.caja==null && other.getCaja()==null) || 
             (this.caja!=null &&
              this.caja.equals(other.getCaja()))) &&
            ((this.tipoOperacion==null && other.getTipoOperacion()==null) || 
             (this.tipoOperacion!=null &&
              this.tipoOperacion.equals(other.getTipoOperacion()))) &&
            ((this.valorOperacion==null && other.getValorOperacion()==null) || 
             (this.valorOperacion!=null &&
              this.valorOperacion.equals(other.getValorOperacion()))) &&
            ((this.origen==null && other.getOrigen()==null) || 
             (this.origen!=null &&
              this.origen.equals(other.getOrigen()))) &&
            ((this.codigoEmpresa==null && other.getCodigoEmpresa()==null) || 
             (this.codigoEmpresa!=null &&
              this.codigoEmpresa.equals(other.getCodigoEmpresa()))) &&
            ((this.tipoDocumento==null && other.getTipoDocumento()==null) || 
             (this.tipoDocumento!=null &&
              this.tipoDocumento.equals(other.getTipoDocumento()))) &&
            ((this.tipoRegistro==null && other.getTipoRegistro()==null) || 
             (this.tipoRegistro!=null &&
              this.tipoRegistro.equals(other.getTipoRegistro())));
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
        if (getCaja() != null) {
            _hashCode += getCaja().hashCode();
        }
        if (getTipoOperacion() != null) {
            _hashCode += getTipoOperacion().hashCode();
        }
        if (getValorOperacion() != null) {
            _hashCode += getValorOperacion().hashCode();
        }
        if (getOrigen() != null) {
            _hashCode += getOrigen().hashCode();
        }
        if (getCodigoEmpresa() != null) {
            _hashCode += getCodigoEmpresa().hashCode();
        }
        if (getTipoDocumento() != null) {
            _hashCode += getTipoDocumento().hashCode();
        }
        if (getTipoRegistro() != null) {
            _hashCode += getTipoRegistro().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(OperacionIn.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://osbservicios.clarochile.cl/PlataformaPagoConsultar/", "OperacionIn"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("caja");
        elemField.setXmlName(new javax.xml.namespace.QName("", "caja"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://osbservicios.clarochile.cl/PlataformaPagoConsultar/", "Caja"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("tipoOperacion");
        elemField.setXmlName(new javax.xml.namespace.QName("", "tipoOperacion"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("valorOperacion");
        elemField.setXmlName(new javax.xml.namespace.QName("", "valorOperacion"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("origen");
        elemField.setXmlName(new javax.xml.namespace.QName("", "origen"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("codigoEmpresa");
        elemField.setXmlName(new javax.xml.namespace.QName("", "codigoEmpresa"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("tipoDocumento");
        elemField.setXmlName(new javax.xml.namespace.QName("", "tipoDocumento"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("tipoRegistro");
        elemField.setXmlName(new javax.xml.namespace.QName("", "tipoRegistro"));
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
