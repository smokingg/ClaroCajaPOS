/**
 * ConsultaSaldoFavorRespuestaType.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package cl.clarochile.osbservicios.PlataformaPagoConsultaSAF;

public class ConsultaSaldoFavorRespuestaType  implements java.io.Serializable {
    private java.lang.String rutCliente;

    private java.lang.String nombreCliente;

    private java.lang.String saldoTotal;

    private cl.clarochile.osbservicios.PlataformaPagoConsultaSAF.ListaCuentaType[] listaCuenta;

    private cl.clarochile.osbservicios.PlataformaPagoConsultaSAF.ResultadoEjecucionType resultadoEjecucion;

    public ConsultaSaldoFavorRespuestaType() {
    }

    public ConsultaSaldoFavorRespuestaType(
           java.lang.String rutCliente,
           java.lang.String nombreCliente,
           java.lang.String saldoTotal,
           cl.clarochile.osbservicios.PlataformaPagoConsultaSAF.ListaCuentaType[] listaCuenta,
           cl.clarochile.osbservicios.PlataformaPagoConsultaSAF.ResultadoEjecucionType resultadoEjecucion) {
           this.rutCliente = rutCliente;
           this.nombreCliente = nombreCliente;
           this.saldoTotal = saldoTotal;
           this.listaCuenta = listaCuenta;
           this.resultadoEjecucion = resultadoEjecucion;
    }


    /**
     * Gets the rutCliente value for this ConsultaSaldoFavorRespuestaType.
     * 
     * @return rutCliente
     */
    public java.lang.String getRutCliente() {
        return rutCliente;
    }


    /**
     * Sets the rutCliente value for this ConsultaSaldoFavorRespuestaType.
     * 
     * @param rutCliente
     */
    public void setRutCliente(java.lang.String rutCliente) {
        this.rutCliente = rutCliente;
    }


    /**
     * Gets the nombreCliente value for this ConsultaSaldoFavorRespuestaType.
     * 
     * @return nombreCliente
     */
    public java.lang.String getNombreCliente() {
        return nombreCliente;
    }


    /**
     * Sets the nombreCliente value for this ConsultaSaldoFavorRespuestaType.
     * 
     * @param nombreCliente
     */
    public void setNombreCliente(java.lang.String nombreCliente) {
        this.nombreCliente = nombreCliente;
    }


    /**
     * Gets the saldoTotal value for this ConsultaSaldoFavorRespuestaType.
     * 
     * @return saldoTotal
     */
    public java.lang.String getSaldoTotal() {
        return saldoTotal;
    }


    /**
     * Sets the saldoTotal value for this ConsultaSaldoFavorRespuestaType.
     * 
     * @param saldoTotal
     */
    public void setSaldoTotal(java.lang.String saldoTotal) {
        this.saldoTotal = saldoTotal;
    }


    /**
     * Gets the listaCuenta value for this ConsultaSaldoFavorRespuestaType.
     * 
     * @return listaCuenta
     */
    public cl.clarochile.osbservicios.PlataformaPagoConsultaSAF.ListaCuentaType[] getListaCuenta() {
        return listaCuenta;
    }


    /**
     * Sets the listaCuenta value for this ConsultaSaldoFavorRespuestaType.
     * 
     * @param listaCuenta
     */
    public void setListaCuenta(cl.clarochile.osbservicios.PlataformaPagoConsultaSAF.ListaCuentaType[] listaCuenta) {
        this.listaCuenta = listaCuenta;
    }

    public cl.clarochile.osbservicios.PlataformaPagoConsultaSAF.ListaCuentaType getListaCuenta(int i) {
        return this.listaCuenta[i];
    }

    public void setListaCuenta(int i, cl.clarochile.osbservicios.PlataformaPagoConsultaSAF.ListaCuentaType _value) {
        this.listaCuenta[i] = _value;
    }


    /**
     * Gets the resultadoEjecucion value for this ConsultaSaldoFavorRespuestaType.
     * 
     * @return resultadoEjecucion
     */
    public cl.clarochile.osbservicios.PlataformaPagoConsultaSAF.ResultadoEjecucionType getResultadoEjecucion() {
        return resultadoEjecucion;
    }


    /**
     * Sets the resultadoEjecucion value for this ConsultaSaldoFavorRespuestaType.
     * 
     * @param resultadoEjecucion
     */
    public void setResultadoEjecucion(cl.clarochile.osbservicios.PlataformaPagoConsultaSAF.ResultadoEjecucionType resultadoEjecucion) {
        this.resultadoEjecucion = resultadoEjecucion;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof ConsultaSaldoFavorRespuestaType)) return false;
        ConsultaSaldoFavorRespuestaType other = (ConsultaSaldoFavorRespuestaType) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.rutCliente==null && other.getRutCliente()==null) || 
             (this.rutCliente!=null &&
              this.rutCliente.equals(other.getRutCliente()))) &&
            ((this.nombreCliente==null && other.getNombreCliente()==null) || 
             (this.nombreCliente!=null &&
              this.nombreCliente.equals(other.getNombreCliente()))) &&
            ((this.saldoTotal==null && other.getSaldoTotal()==null) || 
             (this.saldoTotal!=null &&
              this.saldoTotal.equals(other.getSaldoTotal()))) &&
            ((this.listaCuenta==null && other.getListaCuenta()==null) || 
             (this.listaCuenta!=null &&
              java.util.Arrays.equals(this.listaCuenta, other.getListaCuenta()))) &&
            ((this.resultadoEjecucion==null && other.getResultadoEjecucion()==null) || 
             (this.resultadoEjecucion!=null &&
              this.resultadoEjecucion.equals(other.getResultadoEjecucion())));
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
        if (getRutCliente() != null) {
            _hashCode += getRutCliente().hashCode();
        }
        if (getNombreCliente() != null) {
            _hashCode += getNombreCliente().hashCode();
        }
        if (getSaldoTotal() != null) {
            _hashCode += getSaldoTotal().hashCode();
        }
        if (getListaCuenta() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getListaCuenta());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getListaCuenta(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getResultadoEjecucion() != null) {
            _hashCode += getResultadoEjecucion().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(ConsultaSaldoFavorRespuestaType.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://osbcorp.vtr.cl/REC/EMP/ConsultarSaldoFavor", "ConsultaSaldoFavorRespuestaType"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("rutCliente");
        elemField.setXmlName(new javax.xml.namespace.QName("http://osbcorp.vtr.cl/REC/EMP/ConsultarSaldoFavor", "rutCliente"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("nombreCliente");
        elemField.setXmlName(new javax.xml.namespace.QName("http://osbcorp.vtr.cl/REC/EMP/ConsultarSaldoFavor", "nombreCliente"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("saldoTotal");
        elemField.setXmlName(new javax.xml.namespace.QName("http://osbcorp.vtr.cl/REC/EMP/ConsultarSaldoFavor", "saldoTotal"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("listaCuenta");
        elemField.setXmlName(new javax.xml.namespace.QName("http://osbcorp.vtr.cl/REC/EMP/ConsultarSaldoFavor", "listaCuenta"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://osbcorp.vtr.cl/REC/EMP/ConsultarSaldoFavor", "ListaCuentaType"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        elemField.setMaxOccursUnbounded(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("resultadoEjecucion");
        elemField.setXmlName(new javax.xml.namespace.QName("http://osbcorp.vtr.cl/REC/EMP/ConsultarSaldoFavor", "resultadoEjecucion"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://osbcorp.vtr.cl/GLOBAL/EMP/ResultadoEjecucion", "ResultadoEjecucionType"));
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
