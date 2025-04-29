/**
 * AutorizaChequeIn.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package cl.hyh.cajas.ws.impl;

public class AutorizaChequeIn  extends cl.hyh.cajas.ws.impl.Request  implements java.io.Serializable {
    private java.lang.String banco;

    private java.lang.String cuenta;

    private java.lang.String fecha;

    private java.lang.String monto;

    private java.lang.String numeroCheque;

    private java.lang.String numeroSerie;

    private java.lang.String perfilValidacion;

    private java.lang.String rut;

    public AutorizaChequeIn() {
    }

    public AutorizaChequeIn(
           cl.hyh.cajas.ws.impl.HeaderIn headerIn,
           java.lang.String banco,
           java.lang.String cuenta,
           java.lang.String fecha,
           java.lang.String monto,
           java.lang.String numeroCheque,
           java.lang.String numeroSerie,
           java.lang.String perfilValidacion,
           java.lang.String rut) {
        super(
            headerIn);
        this.banco = banco;
        this.cuenta = cuenta;
        this.fecha = fecha;
        this.monto = monto;
        this.numeroCheque = numeroCheque;
        this.numeroSerie = numeroSerie;
        this.perfilValidacion = perfilValidacion;
        this.rut = rut;
    }


    /**
     * Gets the banco value for this AutorizaChequeIn.
     * 
     * @return banco
     */
    public java.lang.String getBanco() {
        return banco;
    }


    /**
     * Sets the banco value for this AutorizaChequeIn.
     * 
     * @param banco
     */
    public void setBanco(java.lang.String banco) {
        this.banco = banco;
    }


    /**
     * Gets the cuenta value for this AutorizaChequeIn.
     * 
     * @return cuenta
     */
    public java.lang.String getCuenta() {
        return cuenta;
    }


    /**
     * Sets the cuenta value for this AutorizaChequeIn.
     * 
     * @param cuenta
     */
    public void setCuenta(java.lang.String cuenta) {
        this.cuenta = cuenta;
    }


    /**
     * Gets the fecha value for this AutorizaChequeIn.
     * 
     * @return fecha
     */
    public java.lang.String getFecha() {
        return fecha;
    }


    /**
     * Sets the fecha value for this AutorizaChequeIn.
     * 
     * @param fecha
     */
    public void setFecha(java.lang.String fecha) {
        this.fecha = fecha;
    }


    /**
     * Gets the monto value for this AutorizaChequeIn.
     * 
     * @return monto
     */
    public java.lang.String getMonto() {
        return monto;
    }


    /**
     * Sets the monto value for this AutorizaChequeIn.
     * 
     * @param monto
     */
    public void setMonto(java.lang.String monto) {
        this.monto = monto;
    }


    /**
     * Gets the numeroCheque value for this AutorizaChequeIn.
     * 
     * @return numeroCheque
     */
    public java.lang.String getNumeroCheque() {
        return numeroCheque;
    }


    /**
     * Sets the numeroCheque value for this AutorizaChequeIn.
     * 
     * @param numeroCheque
     */
    public void setNumeroCheque(java.lang.String numeroCheque) {
        this.numeroCheque = numeroCheque;
    }


    /**
     * Gets the numeroSerie value for this AutorizaChequeIn.
     * 
     * @return numeroSerie
     */
    public java.lang.String getNumeroSerie() {
        return numeroSerie;
    }


    /**
     * Sets the numeroSerie value for this AutorizaChequeIn.
     * 
     * @param numeroSerie
     */
    public void setNumeroSerie(java.lang.String numeroSerie) {
        this.numeroSerie = numeroSerie;
    }


    /**
     * Gets the perfilValidacion value for this AutorizaChequeIn.
     * 
     * @return perfilValidacion
     */
    public java.lang.String getPerfilValidacion() {
        return perfilValidacion;
    }


    /**
     * Sets the perfilValidacion value for this AutorizaChequeIn.
     * 
     * @param perfilValidacion
     */
    public void setPerfilValidacion(java.lang.String perfilValidacion) {
        this.perfilValidacion = perfilValidacion;
    }


    /**
     * Gets the rut value for this AutorizaChequeIn.
     * 
     * @return rut
     */
    public java.lang.String getRut() {
        return rut;
    }


    /**
     * Sets the rut value for this AutorizaChequeIn.
     * 
     * @param rut
     */
    public void setRut(java.lang.String rut) {
        this.rut = rut;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof AutorizaChequeIn)) return false;
        AutorizaChequeIn other = (AutorizaChequeIn) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = super.equals(obj) && 
            ((this.banco==null && other.getBanco()==null) || 
             (this.banco!=null &&
              this.banco.equals(other.getBanco()))) &&
            ((this.cuenta==null && other.getCuenta()==null) || 
             (this.cuenta!=null &&
              this.cuenta.equals(other.getCuenta()))) &&
            ((this.fecha==null && other.getFecha()==null) || 
             (this.fecha!=null &&
              this.fecha.equals(other.getFecha()))) &&
            ((this.monto==null && other.getMonto()==null) || 
             (this.monto!=null &&
              this.monto.equals(other.getMonto()))) &&
            ((this.numeroCheque==null && other.getNumeroCheque()==null) || 
             (this.numeroCheque!=null &&
              this.numeroCheque.equals(other.getNumeroCheque()))) &&
            ((this.numeroSerie==null && other.getNumeroSerie()==null) || 
             (this.numeroSerie!=null &&
              this.numeroSerie.equals(other.getNumeroSerie()))) &&
            ((this.perfilValidacion==null && other.getPerfilValidacion()==null) || 
             (this.perfilValidacion!=null &&
              this.perfilValidacion.equals(other.getPerfilValidacion()))) &&
            ((this.rut==null && other.getRut()==null) || 
             (this.rut!=null &&
              this.rut.equals(other.getRut())));
        __equalsCalc = null;
        return _equals;
    }

    private boolean __hashCodeCalc = false;
    public synchronized int hashCode() {
        if (__hashCodeCalc) {
            return 0;
        }
        __hashCodeCalc = true;
        int _hashCode = super.hashCode();
        if (getBanco() != null) {
            _hashCode += getBanco().hashCode();
        }
        if (getCuenta() != null) {
            _hashCode += getCuenta().hashCode();
        }
        if (getFecha() != null) {
            _hashCode += getFecha().hashCode();
        }
        if (getMonto() != null) {
            _hashCode += getMonto().hashCode();
        }
        if (getNumeroCheque() != null) {
            _hashCode += getNumeroCheque().hashCode();
        }
        if (getNumeroSerie() != null) {
            _hashCode += getNumeroSerie().hashCode();
        }
        if (getPerfilValidacion() != null) {
            _hashCode += getPerfilValidacion().hashCode();
        }
        if (getRut() != null) {
            _hashCode += getRut().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(AutorizaChequeIn.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://impl.ws.cajas.hyh.cl/", "autorizaChequeIn"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("banco");
        elemField.setXmlName(new javax.xml.namespace.QName("", "banco"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("cuenta");
        elemField.setXmlName(new javax.xml.namespace.QName("", "cuenta"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
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
        elemField.setFieldName("monto");
        elemField.setXmlName(new javax.xml.namespace.QName("", "monto"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("numeroCheque");
        elemField.setXmlName(new javax.xml.namespace.QName("", "numeroCheque"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("numeroSerie");
        elemField.setXmlName(new javax.xml.namespace.QName("", "numeroSerie"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("perfilValidacion");
        elemField.setXmlName(new javax.xml.namespace.QName("", "perfilValidacion"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("rut");
        elemField.setXmlName(new javax.xml.namespace.QName("", "rut"));
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
