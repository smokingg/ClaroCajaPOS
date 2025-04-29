/**
 * DevuelveSaldoFavorPeticionType.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package cl.clarochile.osbservicios.PlataformaPagoNotificarSAF;

public class DevuelveSaldoFavorPeticionType  implements java.io.Serializable {
    private java.lang.String numeroCuenta;

    private java.math.BigDecimal montoDevolucion;

    private java.lang.String numeroEnvioPago;

    public DevuelveSaldoFavorPeticionType() {
    }

    public DevuelveSaldoFavorPeticionType(
           java.lang.String numeroCuenta,
           java.math.BigDecimal montoDevolucion,
           java.lang.String numeroEnvioPago) {
           this.numeroCuenta = numeroCuenta;
           this.montoDevolucion = montoDevolucion;
           this.numeroEnvioPago = numeroEnvioPago;
    }


    /**
     * Gets the numeroCuenta value for this DevuelveSaldoFavorPeticionType.
     * 
     * @return numeroCuenta
     */
    public java.lang.String getNumeroCuenta() {
        return numeroCuenta;
    }


    /**
     * Sets the numeroCuenta value for this DevuelveSaldoFavorPeticionType.
     * 
     * @param numeroCuenta
     */
    public void setNumeroCuenta(java.lang.String numeroCuenta) {
        this.numeroCuenta = numeroCuenta;
    }


    /**
     * Gets the montoDevolucion value for this DevuelveSaldoFavorPeticionType.
     * 
     * @return montoDevolucion
     */
    public java.math.BigDecimal getMontoDevolucion() {
        return montoDevolucion;
    }


    /**
     * Sets the montoDevolucion value for this DevuelveSaldoFavorPeticionType.
     * 
     * @param montoDevolucion
     */
    public void setMontoDevolucion(java.math.BigDecimal montoDevolucion) {
        this.montoDevolucion = montoDevolucion;
    }


    /**
     * Gets the numeroEnvioPago value for this DevuelveSaldoFavorPeticionType.
     * 
     * @return numeroEnvioPago
     */
    public java.lang.String getNumeroEnvioPago() {
        return numeroEnvioPago;
    }


    /**
     * Sets the numeroEnvioPago value for this DevuelveSaldoFavorPeticionType.
     * 
     * @param numeroEnvioPago
     */
    public void setNumeroEnvioPago(java.lang.String numeroEnvioPago) {
        this.numeroEnvioPago = numeroEnvioPago;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof DevuelveSaldoFavorPeticionType)) return false;
        DevuelveSaldoFavorPeticionType other = (DevuelveSaldoFavorPeticionType) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.numeroCuenta==null && other.getNumeroCuenta()==null) || 
             (this.numeroCuenta!=null &&
              this.numeroCuenta.equals(other.getNumeroCuenta()))) &&
            ((this.montoDevolucion==null && other.getMontoDevolucion()==null) || 
             (this.montoDevolucion!=null &&
              this.montoDevolucion.equals(other.getMontoDevolucion()))) &&
            ((this.numeroEnvioPago==null && other.getNumeroEnvioPago()==null) || 
             (this.numeroEnvioPago!=null &&
              this.numeroEnvioPago.equals(other.getNumeroEnvioPago())));
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
        if (getNumeroCuenta() != null) {
            _hashCode += getNumeroCuenta().hashCode();
        }
        if (getMontoDevolucion() != null) {
            _hashCode += getMontoDevolucion().hashCode();
        }
        if (getNumeroEnvioPago() != null) {
            _hashCode += getNumeroEnvioPago().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(DevuelveSaldoFavorPeticionType.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://osbcorp.vtr.cl/REC/EMP/DevolverSaldoFavor", "DevuelveSaldoFavorPeticionType"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("numeroCuenta");
        elemField.setXmlName(new javax.xml.namespace.QName("http://osbcorp.vtr.cl/REC/EMP/DevolverSaldoFavor", "numeroCuenta"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("montoDevolucion");
        elemField.setXmlName(new javax.xml.namespace.QName("http://osbcorp.vtr.cl/REC/EMP/DevolverSaldoFavor", "montoDevolucion"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "decimal"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("numeroEnvioPago");
        elemField.setXmlName(new javax.xml.namespace.QName("http://osbcorp.vtr.cl/REC/EMP/DevolverSaldoFavor", "numeroEnvioPago"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
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
