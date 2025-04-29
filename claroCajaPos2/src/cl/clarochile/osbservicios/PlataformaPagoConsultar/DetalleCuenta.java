/**
 * DetalleCuenta.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package cl.clarochile.osbservicios.PlataformaPagoConsultar;

public class DetalleCuenta  implements java.io.Serializable {
    private java.lang.String sistemaOrigen;

    private java.lang.String numeroCuenta;

    private int cicloFacturacion;

    private long totalSaldo;

    private long totalSaldoLDI;

    private long saldoCastigado;

    private long saldoLimiteCredito;

    public DetalleCuenta() {
    }

    public DetalleCuenta(
           java.lang.String sistemaOrigen,
           java.lang.String numeroCuenta,
           int cicloFacturacion,
           long totalSaldo,
           long totalSaldoLDI,
           long saldoCastigado,
           long saldoLimiteCredito) {
           this.sistemaOrigen = sistemaOrigen;
           this.numeroCuenta = numeroCuenta;
           this.cicloFacturacion = cicloFacturacion;
           this.totalSaldo = totalSaldo;
           this.totalSaldoLDI = totalSaldoLDI;
           this.saldoCastigado = saldoCastigado;
           this.saldoLimiteCredito = saldoLimiteCredito;
    }


    /**
     * Gets the sistemaOrigen value for this DetalleCuenta.
     * 
     * @return sistemaOrigen
     */
    public java.lang.String getSistemaOrigen() {
        return sistemaOrigen;
    }


    /**
     * Sets the sistemaOrigen value for this DetalleCuenta.
     * 
     * @param sistemaOrigen
     */
    public void setSistemaOrigen(java.lang.String sistemaOrigen) {
        this.sistemaOrigen = sistemaOrigen;
    }


    /**
     * Gets the numeroCuenta value for this DetalleCuenta.
     * 
     * @return numeroCuenta
     */
    public java.lang.String getNumeroCuenta() {
        return numeroCuenta;
    }


    /**
     * Sets the numeroCuenta value for this DetalleCuenta.
     * 
     * @param numeroCuenta
     */
    public void setNumeroCuenta(java.lang.String numeroCuenta) {
        this.numeroCuenta = numeroCuenta;
    }


    /**
     * Gets the cicloFacturacion value for this DetalleCuenta.
     * 
     * @return cicloFacturacion
     */
    public int getCicloFacturacion() {
        return cicloFacturacion;
    }


    /**
     * Sets the cicloFacturacion value for this DetalleCuenta.
     * 
     * @param cicloFacturacion
     */
    public void setCicloFacturacion(int cicloFacturacion) {
        this.cicloFacturacion = cicloFacturacion;
    }


    /**
     * Gets the totalSaldo value for this DetalleCuenta.
     * 
     * @return totalSaldo
     */
    public long getTotalSaldo() {
        return totalSaldo;
    }


    /**
     * Sets the totalSaldo value for this DetalleCuenta.
     * 
     * @param totalSaldo
     */
    public void setTotalSaldo(long totalSaldo) {
        this.totalSaldo = totalSaldo;
    }


    /**
     * Gets the totalSaldoLDI value for this DetalleCuenta.
     * 
     * @return totalSaldoLDI
     */
    public long getTotalSaldoLDI() {
        return totalSaldoLDI;
    }


    /**
     * Sets the totalSaldoLDI value for this DetalleCuenta.
     * 
     * @param totalSaldoLDI
     */
    public void setTotalSaldoLDI(long totalSaldoLDI) {
        this.totalSaldoLDI = totalSaldoLDI;
    }


    /**
     * Gets the saldoCastigado value for this DetalleCuenta.
     * 
     * @return saldoCastigado
     */
    public long getSaldoCastigado() {
        return saldoCastigado;
    }


    /**
     * Sets the saldoCastigado value for this DetalleCuenta.
     * 
     * @param saldoCastigado
     */
    public void setSaldoCastigado(long saldoCastigado) {
        this.saldoCastigado = saldoCastigado;
    }


    /**
     * Gets the saldoLimiteCredito value for this DetalleCuenta.
     * 
     * @return saldoLimiteCredito
     */
    public long getSaldoLimiteCredito() {
        return saldoLimiteCredito;
    }


    /**
     * Sets the saldoLimiteCredito value for this DetalleCuenta.
     * 
     * @param saldoLimiteCredito
     */
    public void setSaldoLimiteCredito(long saldoLimiteCredito) {
        this.saldoLimiteCredito = saldoLimiteCredito;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof DetalleCuenta)) return false;
        DetalleCuenta other = (DetalleCuenta) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.sistemaOrigen==null && other.getSistemaOrigen()==null) || 
             (this.sistemaOrigen!=null &&
              this.sistemaOrigen.equals(other.getSistemaOrigen()))) &&
            ((this.numeroCuenta==null && other.getNumeroCuenta()==null) || 
             (this.numeroCuenta!=null &&
              this.numeroCuenta.equals(other.getNumeroCuenta()))) &&
            this.cicloFacturacion == other.getCicloFacturacion() &&
            this.totalSaldo == other.getTotalSaldo() &&
            this.totalSaldoLDI == other.getTotalSaldoLDI() &&
            this.saldoCastigado == other.getSaldoCastigado() &&
            this.saldoLimiteCredito == other.getSaldoLimiteCredito();
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
        if (getSistemaOrigen() != null) {
            _hashCode += getSistemaOrigen().hashCode();
        }
        if (getNumeroCuenta() != null) {
            _hashCode += getNumeroCuenta().hashCode();
        }
        _hashCode += getCicloFacturacion();
        _hashCode += new Long(getTotalSaldo()).hashCode();
        _hashCode += new Long(getTotalSaldoLDI()).hashCode();
        _hashCode += new Long(getSaldoCastigado()).hashCode();
        _hashCode += new Long(getSaldoLimiteCredito()).hashCode();
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(DetalleCuenta.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://osbservicios.clarochile.cl/PlataformaPagoConsultar/", "DetalleCuenta"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("sistemaOrigen");
        elemField.setXmlName(new javax.xml.namespace.QName("", "sistemaOrigen"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("numeroCuenta");
        elemField.setXmlName(new javax.xml.namespace.QName("", "numeroCuenta"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("cicloFacturacion");
        elemField.setXmlName(new javax.xml.namespace.QName("", "cicloFacturacion"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("totalSaldo");
        elemField.setXmlName(new javax.xml.namespace.QName("", "totalSaldo"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "long"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("totalSaldoLDI");
        elemField.setXmlName(new javax.xml.namespace.QName("", "totalSaldoLDI"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "long"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("saldoCastigado");
        elemField.setXmlName(new javax.xml.namespace.QName("", "saldoCastigado"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "long"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("saldoLimiteCredito");
        elemField.setXmlName(new javax.xml.namespace.QName("", "saldoLimiteCredito"));
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
