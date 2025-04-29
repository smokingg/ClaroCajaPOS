/**
 * ServicioVTR.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package cl.hyh.cajas.ws.impl;

public class ServicioVTR  implements java.io.Serializable {
    private java.lang.String cuentaUnica;

    private java.lang.String estadoServicio;

    private int identificadorServicio;

    private long numeroCuenta;

    private java.lang.String numeroDocumento;

    private java.lang.String numeroServicio;

    private java.lang.String producto;

    private long saldoServicio;

    private java.lang.String sistemaOrigen;

    public ServicioVTR() {
    }

    public ServicioVTR(
           java.lang.String cuentaUnica,
           java.lang.String estadoServicio,
           int identificadorServicio,
           long numeroCuenta,
           java.lang.String numeroDocumento,
           java.lang.String numeroServicio,
           java.lang.String producto,
           long saldoServicio,
           java.lang.String sistemaOrigen) {
           this.cuentaUnica = cuentaUnica;
           this.estadoServicio = estadoServicio;
           this.identificadorServicio = identificadorServicio;
           this.numeroCuenta = numeroCuenta;
           this.numeroDocumento = numeroDocumento;
           this.numeroServicio = numeroServicio;
           this.producto = producto;
           this.saldoServicio = saldoServicio;
           this.sistemaOrigen = sistemaOrigen;
    }


    /**
     * Gets the cuentaUnica value for this ServicioVTR.
     * 
     * @return cuentaUnica
     */
    public java.lang.String getCuentaUnica() {
        return cuentaUnica;
    }


    /**
     * Sets the cuentaUnica value for this ServicioVTR.
     * 
     * @param cuentaUnica
     */
    public void setCuentaUnica(java.lang.String cuentaUnica) {
        this.cuentaUnica = cuentaUnica;
    }


    /**
     * Gets the estadoServicio value for this ServicioVTR.
     * 
     * @return estadoServicio
     */
    public java.lang.String getEstadoServicio() {
        return estadoServicio;
    }


    /**
     * Sets the estadoServicio value for this ServicioVTR.
     * 
     * @param estadoServicio
     */
    public void setEstadoServicio(java.lang.String estadoServicio) {
        this.estadoServicio = estadoServicio;
    }


    /**
     * Gets the identificadorServicio value for this ServicioVTR.
     * 
     * @return identificadorServicio
     */
    public int getIdentificadorServicio() {
        return identificadorServicio;
    }


    /**
     * Sets the identificadorServicio value for this ServicioVTR.
     * 
     * @param identificadorServicio
     */
    public void setIdentificadorServicio(int identificadorServicio) {
        this.identificadorServicio = identificadorServicio;
    }


    /**
     * Gets the numeroCuenta value for this ServicioVTR.
     * 
     * @return numeroCuenta
     */
    public long getNumeroCuenta() {
        return numeroCuenta;
    }


    /**
     * Sets the numeroCuenta value for this ServicioVTR.
     * 
     * @param numeroCuenta
     */
    public void setNumeroCuenta(long numeroCuenta) {
        this.numeroCuenta = numeroCuenta;
    }


    /**
     * Gets the numeroDocumento value for this ServicioVTR.
     * 
     * @return numeroDocumento
     */
    public java.lang.String getNumeroDocumento() {
        return numeroDocumento;
    }


    /**
     * Sets the numeroDocumento value for this ServicioVTR.
     * 
     * @param numeroDocumento
     */
    public void setNumeroDocumento(java.lang.String numeroDocumento) {
        this.numeroDocumento = numeroDocumento;
    }


    /**
     * Gets the numeroServicio value for this ServicioVTR.
     * 
     * @return numeroServicio
     */
    public java.lang.String getNumeroServicio() {
        return numeroServicio;
    }


    /**
     * Sets the numeroServicio value for this ServicioVTR.
     * 
     * @param numeroServicio
     */
    public void setNumeroServicio(java.lang.String numeroServicio) {
        this.numeroServicio = numeroServicio;
    }


    /**
     * Gets the producto value for this ServicioVTR.
     * 
     * @return producto
     */
    public java.lang.String getProducto() {
        return producto;
    }


    /**
     * Sets the producto value for this ServicioVTR.
     * 
     * @param producto
     */
    public void setProducto(java.lang.String producto) {
        this.producto = producto;
    }


    /**
     * Gets the saldoServicio value for this ServicioVTR.
     * 
     * @return saldoServicio
     */
    public long getSaldoServicio() {
        return saldoServicio;
    }


    /**
     * Sets the saldoServicio value for this ServicioVTR.
     * 
     * @param saldoServicio
     */
    public void setSaldoServicio(long saldoServicio) {
        this.saldoServicio = saldoServicio;
    }


    /**
     * Gets the sistemaOrigen value for this ServicioVTR.
     * 
     * @return sistemaOrigen
     */
    public java.lang.String getSistemaOrigen() {
        return sistemaOrigen;
    }


    /**
     * Sets the sistemaOrigen value for this ServicioVTR.
     * 
     * @param sistemaOrigen
     */
    public void setSistemaOrigen(java.lang.String sistemaOrigen) {
        this.sistemaOrigen = sistemaOrigen;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof ServicioVTR)) return false;
        ServicioVTR other = (ServicioVTR) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.cuentaUnica==null && other.getCuentaUnica()==null) || 
             (this.cuentaUnica!=null &&
              this.cuentaUnica.equals(other.getCuentaUnica()))) &&
            ((this.estadoServicio==null && other.getEstadoServicio()==null) || 
             (this.estadoServicio!=null &&
              this.estadoServicio.equals(other.getEstadoServicio()))) &&
            this.identificadorServicio == other.getIdentificadorServicio() &&
            this.numeroCuenta == other.getNumeroCuenta() &&
            ((this.numeroDocumento==null && other.getNumeroDocumento()==null) || 
             (this.numeroDocumento!=null &&
              this.numeroDocumento.equals(other.getNumeroDocumento()))) &&
            ((this.numeroServicio==null && other.getNumeroServicio()==null) || 
             (this.numeroServicio!=null &&
              this.numeroServicio.equals(other.getNumeroServicio()))) &&
            ((this.producto==null && other.getProducto()==null) || 
             (this.producto!=null &&
              this.producto.equals(other.getProducto()))) &&
            this.saldoServicio == other.getSaldoServicio() &&
            ((this.sistemaOrigen==null && other.getSistemaOrigen()==null) || 
             (this.sistemaOrigen!=null &&
              this.sistemaOrigen.equals(other.getSistemaOrigen())));
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
        if (getCuentaUnica() != null) {
            _hashCode += getCuentaUnica().hashCode();
        }
        if (getEstadoServicio() != null) {
            _hashCode += getEstadoServicio().hashCode();
        }
        _hashCode += getIdentificadorServicio();
        _hashCode += new Long(getNumeroCuenta()).hashCode();
        if (getNumeroDocumento() != null) {
            _hashCode += getNumeroDocumento().hashCode();
        }
        if (getNumeroServicio() != null) {
            _hashCode += getNumeroServicio().hashCode();
        }
        if (getProducto() != null) {
            _hashCode += getProducto().hashCode();
        }
        _hashCode += new Long(getSaldoServicio()).hashCode();
        if (getSistemaOrigen() != null) {
            _hashCode += getSistemaOrigen().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(ServicioVTR.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://impl.ws.cajas.hyh.cl/", "servicioVTR"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("cuentaUnica");
        elemField.setXmlName(new javax.xml.namespace.QName("", "cuentaUnica"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("estadoServicio");
        elemField.setXmlName(new javax.xml.namespace.QName("", "estadoServicio"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("identificadorServicio");
        elemField.setXmlName(new javax.xml.namespace.QName("", "identificadorServicio"));
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
        elemField.setFieldName("numeroServicio");
        elemField.setXmlName(new javax.xml.namespace.QName("", "numeroServicio"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("producto");
        elemField.setXmlName(new javax.xml.namespace.QName("", "producto"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("saldoServicio");
        elemField.setXmlName(new javax.xml.namespace.QName("", "saldoServicio"));
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
