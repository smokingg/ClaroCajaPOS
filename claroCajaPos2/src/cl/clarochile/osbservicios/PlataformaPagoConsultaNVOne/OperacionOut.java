/**
 * OperacionOut.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package cl.clarochile.osbservicios.PlataformaPagoConsultaNVOne;

public class OperacionOut  implements java.io.Serializable {
    private java.lang.String codigoPais;

    private java.lang.String empresa;

    private long saldo;

    private long idFactura;

    private long numLegalfactura;

    private long monto;

    private java.lang.String fechaCreacion;

    private java.lang.String fechaVencimiento;

    private java.lang.String vencimiento;

    private cl.clarochile.osbservicios.PlataformaPagoConsultaNVOne.Respuesta respuesta;

    public OperacionOut() {
    }

    public OperacionOut(
           java.lang.String codigoPais,
           java.lang.String empresa,
           long saldo,
           long idFactura,
           long numLegalfactura,
           long monto,
           java.lang.String fechaCreacion,
           java.lang.String fechaVencimiento,
           java.lang.String vencimiento,
           cl.clarochile.osbservicios.PlataformaPagoConsultaNVOne.Respuesta respuesta) {
           this.codigoPais = codigoPais;
           this.empresa = empresa;
           this.saldo = saldo;
           this.idFactura = idFactura;
           this.numLegalfactura = numLegalfactura;
           this.monto = monto;
           this.fechaCreacion = fechaCreacion;
           this.fechaVencimiento = fechaVencimiento;
           this.vencimiento = vencimiento;
           this.respuesta = respuesta;
    }


    /**
     * Gets the codigoPais value for this OperacionOut.
     * 
     * @return codigoPais
     */
    public java.lang.String getCodigoPais() {
        return codigoPais;
    }


    /**
     * Sets the codigoPais value for this OperacionOut.
     * 
     * @param codigoPais
     */
    public void setCodigoPais(java.lang.String codigoPais) {
        this.codigoPais = codigoPais;
    }


    /**
     * Gets the empresa value for this OperacionOut.
     * 
     * @return empresa
     */
    public java.lang.String getEmpresa() {
        return empresa;
    }


    /**
     * Sets the empresa value for this OperacionOut.
     * 
     * @param empresa
     */
    public void setEmpresa(java.lang.String empresa) {
        this.empresa = empresa;
    }


    /**
     * Gets the saldo value for this OperacionOut.
     * 
     * @return saldo
     */
    public long getSaldo() {
        return saldo;
    }


    /**
     * Sets the saldo value for this OperacionOut.
     * 
     * @param saldo
     */
    public void setSaldo(long saldo) {
        this.saldo = saldo;
    }


    /**
     * Gets the idFactura value for this OperacionOut.
     * 
     * @return idFactura
     */
    public long getIdFactura() {
        return idFactura;
    }


    /**
     * Sets the idFactura value for this OperacionOut.
     * 
     * @param idFactura
     */
    public void setIdFactura(long idFactura) {
        this.idFactura = idFactura;
    }


    /**
     * Gets the numLegalfactura value for this OperacionOut.
     * 
     * @return numLegalfactura
     */
    public long getNumLegalfactura() {
        return numLegalfactura;
    }


    /**
     * Sets the numLegalfactura value for this OperacionOut.
     * 
     * @param numLegalfactura
     */
    public void setNumLegalfactura(long numLegalfactura) {
        this.numLegalfactura = numLegalfactura;
    }


    /**
     * Gets the monto value for this OperacionOut.
     * 
     * @return monto
     */
    public long getMonto() {
        return monto;
    }


    /**
     * Sets the monto value for this OperacionOut.
     * 
     * @param monto
     */
    public void setMonto(long monto) {
        this.monto = monto;
    }


    /**
     * Gets the fechaCreacion value for this OperacionOut.
     * 
     * @return fechaCreacion
     */
    public java.lang.String getFechaCreacion() {
        return fechaCreacion;
    }


    /**
     * Sets the fechaCreacion value for this OperacionOut.
     * 
     * @param fechaCreacion
     */
    public void setFechaCreacion(java.lang.String fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }


    /**
     * Gets the fechaVencimiento value for this OperacionOut.
     * 
     * @return fechaVencimiento
     */
    public java.lang.String getFechaVencimiento() {
        return fechaVencimiento;
    }


    /**
     * Sets the fechaVencimiento value for this OperacionOut.
     * 
     * @param fechaVencimiento
     */
    public void setFechaVencimiento(java.lang.String fechaVencimiento) {
        this.fechaVencimiento = fechaVencimiento;
    }


    /**
     * Gets the vencimiento value for this OperacionOut.
     * 
     * @return vencimiento
     */
    public java.lang.String getVencimiento() {
        return vencimiento;
    }


    /**
     * Sets the vencimiento value for this OperacionOut.
     * 
     * @param vencimiento
     */
    public void setVencimiento(java.lang.String vencimiento) {
        this.vencimiento = vencimiento;
    }


    /**
     * Gets the respuesta value for this OperacionOut.
     * 
     * @return respuesta
     */
    public cl.clarochile.osbservicios.PlataformaPagoConsultaNVOne.Respuesta getRespuesta() {
        return respuesta;
    }


    /**
     * Sets the respuesta value for this OperacionOut.
     * 
     * @param respuesta
     */
    public void setRespuesta(cl.clarochile.osbservicios.PlataformaPagoConsultaNVOne.Respuesta respuesta) {
        this.respuesta = respuesta;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof OperacionOut)) return false;
        OperacionOut other = (OperacionOut) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.codigoPais==null && other.getCodigoPais()==null) || 
             (this.codigoPais!=null &&
              this.codigoPais.equals(other.getCodigoPais()))) &&
            ((this.empresa==null && other.getEmpresa()==null) || 
             (this.empresa!=null &&
              this.empresa.equals(other.getEmpresa()))) &&
            this.saldo == other.getSaldo() &&
            this.idFactura == other.getIdFactura() &&
            this.numLegalfactura == other.getNumLegalfactura() &&
            this.monto == other.getMonto() &&
            ((this.fechaCreacion==null && other.getFechaCreacion()==null) || 
             (this.fechaCreacion!=null &&
              this.fechaCreacion.equals(other.getFechaCreacion()))) &&
            ((this.fechaVencimiento==null && other.getFechaVencimiento()==null) || 
             (this.fechaVencimiento!=null &&
              this.fechaVencimiento.equals(other.getFechaVencimiento()))) &&
            ((this.vencimiento==null && other.getVencimiento()==null) || 
             (this.vencimiento!=null &&
              this.vencimiento.equals(other.getVencimiento()))) &&
            ((this.respuesta==null && other.getRespuesta()==null) || 
             (this.respuesta!=null &&
              this.respuesta.equals(other.getRespuesta())));
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
        if (getCodigoPais() != null) {
            _hashCode += getCodigoPais().hashCode();
        }
        if (getEmpresa() != null) {
            _hashCode += getEmpresa().hashCode();
        }
        _hashCode += new Long(getSaldo()).hashCode();
        _hashCode += new Long(getIdFactura()).hashCode();
        _hashCode += new Long(getNumLegalfactura()).hashCode();
        _hashCode += new Long(getMonto()).hashCode();
        if (getFechaCreacion() != null) {
            _hashCode += getFechaCreacion().hashCode();
        }
        if (getFechaVencimiento() != null) {
            _hashCode += getFechaVencimiento().hashCode();
        }
        if (getVencimiento() != null) {
            _hashCode += getVencimiento().hashCode();
        }
        if (getRespuesta() != null) {
            _hashCode += getRespuesta().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(OperacionOut.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://osbservicios.clarochile.cl/PlataformaPagoConsultaNVOne/", "OperacionOut"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("codigoPais");
        elemField.setXmlName(new javax.xml.namespace.QName("", "codigoPais"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("empresa");
        elemField.setXmlName(new javax.xml.namespace.QName("", "empresa"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("saldo");
        elemField.setXmlName(new javax.xml.namespace.QName("", "saldo"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "long"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("idFactura");
        elemField.setXmlName(new javax.xml.namespace.QName("", "idFactura"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "long"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("numLegalfactura");
        elemField.setXmlName(new javax.xml.namespace.QName("", "numLegalfactura"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "long"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("monto");
        elemField.setXmlName(new javax.xml.namespace.QName("", "monto"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "long"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("fechaCreacion");
        elemField.setXmlName(new javax.xml.namespace.QName("", "fechaCreacion"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("fechaVencimiento");
        elemField.setXmlName(new javax.xml.namespace.QName("", "fechaVencimiento"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("vencimiento");
        elemField.setXmlName(new javax.xml.namespace.QName("", "vencimiento"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("respuesta");
        elemField.setXmlName(new javax.xml.namespace.QName("", "respuesta"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://osbservicios.clarochile.cl/PlataformaPagoConsultaNVOne/", "Respuesta"));
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
