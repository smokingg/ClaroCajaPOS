/**
 * DocumentoVTR.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package cl.hyh.cajas.ws.impl;

public class DocumentoVTR  implements java.io.Serializable {
    private java.lang.String codTipoDocumento;

    private java.lang.String cuentaUnica;

    private java.lang.String direccionCobranza;

    private java.util.Calendar fecEmisionDocumento;

    private java.util.Calendar fecVctoDocumento;

    private int numeroCorrelativo;

    private long numeroCuenta;

    private java.lang.String numeroDocumento;

    private long saldoDocumento;

    private java.lang.String sistemaOrigen;

    private java.lang.String tipoCorrelativo;

    public DocumentoVTR() {
    }

    public DocumentoVTR(
           java.lang.String codTipoDocumento,
           java.lang.String cuentaUnica,
           java.lang.String direccionCobranza,
           java.util.Calendar fecEmisionDocumento,
           java.util.Calendar fecVctoDocumento,
           int numeroCorrelativo,
           long numeroCuenta,
           java.lang.String numeroDocumento,
           long saldoDocumento,
           java.lang.String sistemaOrigen,
           java.lang.String tipoCorrelativo) {
           this.codTipoDocumento = codTipoDocumento;
           this.cuentaUnica = cuentaUnica;
           this.direccionCobranza = direccionCobranza;
           this.fecEmisionDocumento = fecEmisionDocumento;
           this.fecVctoDocumento = fecVctoDocumento;
           this.numeroCorrelativo = numeroCorrelativo;
           this.numeroCuenta = numeroCuenta;
           this.numeroDocumento = numeroDocumento;
           this.saldoDocumento = saldoDocumento;
           this.sistemaOrigen = sistemaOrigen;
           this.tipoCorrelativo = tipoCorrelativo;
    }


    /**
     * Gets the codTipoDocumento value for this DocumentoVTR.
     * 
     * @return codTipoDocumento
     */
    public java.lang.String getCodTipoDocumento() {
        return codTipoDocumento;
    }


    /**
     * Sets the codTipoDocumento value for this DocumentoVTR.
     * 
     * @param codTipoDocumento
     */
    public void setCodTipoDocumento(java.lang.String codTipoDocumento) {
        this.codTipoDocumento = codTipoDocumento;
    }


    /**
     * Gets the cuentaUnica value for this DocumentoVTR.
     * 
     * @return cuentaUnica
     */
    public java.lang.String getCuentaUnica() {
        return cuentaUnica;
    }


    /**
     * Sets the cuentaUnica value for this DocumentoVTR.
     * 
     * @param cuentaUnica
     */
    public void setCuentaUnica(java.lang.String cuentaUnica) {
        this.cuentaUnica = cuentaUnica;
    }


    /**
     * Gets the direccionCobranza value for this DocumentoVTR.
     * 
     * @return direccionCobranza
     */
    public java.lang.String getDireccionCobranza() {
        return direccionCobranza;
    }


    /**
     * Sets the direccionCobranza value for this DocumentoVTR.
     * 
     * @param direccionCobranza
     */
    public void setDireccionCobranza(java.lang.String direccionCobranza) {
        this.direccionCobranza = direccionCobranza;
    }


    /**
     * Gets the fecEmisionDocumento value for this DocumentoVTR.
     * 
     * @return fecEmisionDocumento
     */
    public java.util.Calendar getFecEmisionDocumento() {
        return fecEmisionDocumento;
    }


    /**
     * Sets the fecEmisionDocumento value for this DocumentoVTR.
     * 
     * @param fecEmisionDocumento
     */
    public void setFecEmisionDocumento(java.util.Calendar fecEmisionDocumento) {
        this.fecEmisionDocumento = fecEmisionDocumento;
    }


    /**
     * Gets the fecVctoDocumento value for this DocumentoVTR.
     * 
     * @return fecVctoDocumento
     */
    public java.util.Calendar getFecVctoDocumento() {
        return fecVctoDocumento;
    }


    /**
     * Sets the fecVctoDocumento value for this DocumentoVTR.
     * 
     * @param fecVctoDocumento
     */
    public void setFecVctoDocumento(java.util.Calendar fecVctoDocumento) {
        this.fecVctoDocumento = fecVctoDocumento;
    }


    /**
     * Gets the numeroCorrelativo value for this DocumentoVTR.
     * 
     * @return numeroCorrelativo
     */
    public int getNumeroCorrelativo() {
        return numeroCorrelativo;
    }


    /**
     * Sets the numeroCorrelativo value for this DocumentoVTR.
     * 
     * @param numeroCorrelativo
     */
    public void setNumeroCorrelativo(int numeroCorrelativo) {
        this.numeroCorrelativo = numeroCorrelativo;
    }


    /**
     * Gets the numeroCuenta value for this DocumentoVTR.
     * 
     * @return numeroCuenta
     */
    public long getNumeroCuenta() {
        return numeroCuenta;
    }


    /**
     * Sets the numeroCuenta value for this DocumentoVTR.
     * 
     * @param numeroCuenta
     */
    public void setNumeroCuenta(long numeroCuenta) {
        this.numeroCuenta = numeroCuenta;
    }


    /**
     * Gets the numeroDocumento value for this DocumentoVTR.
     * 
     * @return numeroDocumento
     */
    public java.lang.String getNumeroDocumento() {
        return numeroDocumento;
    }


    /**
     * Sets the numeroDocumento value for this DocumentoVTR.
     * 
     * @param numeroDocumento
     */
    public void setNumeroDocumento(java.lang.String numeroDocumento) {
        this.numeroDocumento = numeroDocumento;
    }


    /**
     * Gets the saldoDocumento value for this DocumentoVTR.
     * 
     * @return saldoDocumento
     */
    public long getSaldoDocumento() {
        return saldoDocumento;
    }


    /**
     * Sets the saldoDocumento value for this DocumentoVTR.
     * 
     * @param saldoDocumento
     */
    public void setSaldoDocumento(long saldoDocumento) {
        this.saldoDocumento = saldoDocumento;
    }


    /**
     * Gets the sistemaOrigen value for this DocumentoVTR.
     * 
     * @return sistemaOrigen
     */
    public java.lang.String getSistemaOrigen() {
        return sistemaOrigen;
    }


    /**
     * Sets the sistemaOrigen value for this DocumentoVTR.
     * 
     * @param sistemaOrigen
     */
    public void setSistemaOrigen(java.lang.String sistemaOrigen) {
        this.sistemaOrigen = sistemaOrigen;
    }


    /**
     * Gets the tipoCorrelativo value for this DocumentoVTR.
     * 
     * @return tipoCorrelativo
     */
    public java.lang.String getTipoCorrelativo() {
        return tipoCorrelativo;
    }


    /**
     * Sets the tipoCorrelativo value for this DocumentoVTR.
     * 
     * @param tipoCorrelativo
     */
    public void setTipoCorrelativo(java.lang.String tipoCorrelativo) {
        this.tipoCorrelativo = tipoCorrelativo;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof DocumentoVTR)) return false;
        DocumentoVTR other = (DocumentoVTR) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.codTipoDocumento==null && other.getCodTipoDocumento()==null) || 
             (this.codTipoDocumento!=null &&
              this.codTipoDocumento.equals(other.getCodTipoDocumento()))) &&
            ((this.cuentaUnica==null && other.getCuentaUnica()==null) || 
             (this.cuentaUnica!=null &&
              this.cuentaUnica.equals(other.getCuentaUnica()))) &&
            ((this.direccionCobranza==null && other.getDireccionCobranza()==null) || 
             (this.direccionCobranza!=null &&
              this.direccionCobranza.equals(other.getDireccionCobranza()))) &&
            ((this.fecEmisionDocumento==null && other.getFecEmisionDocumento()==null) || 
             (this.fecEmisionDocumento!=null &&
              this.fecEmisionDocumento.equals(other.getFecEmisionDocumento()))) &&
            ((this.fecVctoDocumento==null && other.getFecVctoDocumento()==null) || 
             (this.fecVctoDocumento!=null &&
              this.fecVctoDocumento.equals(other.getFecVctoDocumento()))) &&
            this.numeroCorrelativo == other.getNumeroCorrelativo() &&
            this.numeroCuenta == other.getNumeroCuenta() &&
            ((this.numeroDocumento==null && other.getNumeroDocumento()==null) || 
             (this.numeroDocumento!=null &&
              this.numeroDocumento.equals(other.getNumeroDocumento()))) &&
            this.saldoDocumento == other.getSaldoDocumento() &&
            ((this.sistemaOrigen==null && other.getSistemaOrigen()==null) || 
             (this.sistemaOrigen!=null &&
              this.sistemaOrigen.equals(other.getSistemaOrigen()))) &&
            ((this.tipoCorrelativo==null && other.getTipoCorrelativo()==null) || 
             (this.tipoCorrelativo!=null &&
              this.tipoCorrelativo.equals(other.getTipoCorrelativo())));
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
        if (getCodTipoDocumento() != null) {
            _hashCode += getCodTipoDocumento().hashCode();
        }
        if (getCuentaUnica() != null) {
            _hashCode += getCuentaUnica().hashCode();
        }
        if (getDireccionCobranza() != null) {
            _hashCode += getDireccionCobranza().hashCode();
        }
        if (getFecEmisionDocumento() != null) {
            _hashCode += getFecEmisionDocumento().hashCode();
        }
        if (getFecVctoDocumento() != null) {
            _hashCode += getFecVctoDocumento().hashCode();
        }
        _hashCode += getNumeroCorrelativo();
        _hashCode += new Long(getNumeroCuenta()).hashCode();
        if (getNumeroDocumento() != null) {
            _hashCode += getNumeroDocumento().hashCode();
        }
        _hashCode += new Long(getSaldoDocumento()).hashCode();
        if (getSistemaOrigen() != null) {
            _hashCode += getSistemaOrigen().hashCode();
        }
        if (getTipoCorrelativo() != null) {
            _hashCode += getTipoCorrelativo().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(DocumentoVTR.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://impl.ws.cajas.hyh.cl/", "documentoVTR"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("codTipoDocumento");
        elemField.setXmlName(new javax.xml.namespace.QName("", "codTipoDocumento"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("cuentaUnica");
        elemField.setXmlName(new javax.xml.namespace.QName("", "cuentaUnica"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("direccionCobranza");
        elemField.setXmlName(new javax.xml.namespace.QName("", "direccionCobranza"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("fecEmisionDocumento");
        elemField.setXmlName(new javax.xml.namespace.QName("", "fecEmisionDocumento"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "dateTime"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("fecVctoDocumento");
        elemField.setXmlName(new javax.xml.namespace.QName("", "fecVctoDocumento"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "dateTime"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("numeroCorrelativo");
        elemField.setXmlName(new javax.xml.namespace.QName("", "numeroCorrelativo"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("numeroCuenta");
        elemField.setXmlName(new javax.xml.namespace.QName("", "numeroCuenta"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "long"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("numeroDocumento");
        elemField.setXmlName(new javax.xml.namespace.QName("", "numeroDocumento"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("saldoDocumento");
        elemField.setXmlName(new javax.xml.namespace.QName("", "saldoDocumento"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "long"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("sistemaOrigen");
        elemField.setXmlName(new javax.xml.namespace.QName("", "sistemaOrigen"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("tipoCorrelativo");
        elemField.setXmlName(new javax.xml.namespace.QName("", "tipoCorrelativo"));
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
