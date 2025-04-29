/**
 * DevuelveSaldoFavorRespuestaType.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package cl.clarochile.osbservicios.PlataformaPagoNotificarSAF;

public class DevuelveSaldoFavorRespuestaType  implements java.io.Serializable {
    private java.lang.String identificadorTransaccion;

    private java.lang.String fechaTransaccion;

    private cl.clarochile.osbservicios.PlataformaPagoNotificarSAF.ResultadoEjecucionType resultadoEjecucion;

    public DevuelveSaldoFavorRespuestaType() {
    }

    public DevuelveSaldoFavorRespuestaType(
           java.lang.String identificadorTransaccion,
           java.lang.String fechaTransaccion,
           cl.clarochile.osbservicios.PlataformaPagoNotificarSAF.ResultadoEjecucionType resultadoEjecucion) {
           this.identificadorTransaccion = identificadorTransaccion;
           this.fechaTransaccion = fechaTransaccion;
           this.resultadoEjecucion = resultadoEjecucion;
    }


    /**
     * Gets the identificadorTransaccion value for this DevuelveSaldoFavorRespuestaType.
     * 
     * @return identificadorTransaccion
     */
    public java.lang.String getIdentificadorTransaccion() {
        return identificadorTransaccion;
    }


    /**
     * Sets the identificadorTransaccion value for this DevuelveSaldoFavorRespuestaType.
     * 
     * @param identificadorTransaccion
     */
    public void setIdentificadorTransaccion(java.lang.String identificadorTransaccion) {
        this.identificadorTransaccion = identificadorTransaccion;
    }


    /**
     * Gets the fechaTransaccion value for this DevuelveSaldoFavorRespuestaType.
     * 
     * @return fechaTransaccion
     */
    public java.lang.String getFechaTransaccion() {
        return fechaTransaccion;
    }


    /**
     * Sets the fechaTransaccion value for this DevuelveSaldoFavorRespuestaType.
     * 
     * @param fechaTransaccion
     */
    public void setFechaTransaccion(java.lang.String fechaTransaccion) {
        this.fechaTransaccion = fechaTransaccion;
    }


    /**
     * Gets the resultadoEjecucion value for this DevuelveSaldoFavorRespuestaType.
     * 
     * @return resultadoEjecucion
     */
    public cl.clarochile.osbservicios.PlataformaPagoNotificarSAF.ResultadoEjecucionType getResultadoEjecucion() {
        return resultadoEjecucion;
    }


    /**
     * Sets the resultadoEjecucion value for this DevuelveSaldoFavorRespuestaType.
     * 
     * @param resultadoEjecucion
     */
    public void setResultadoEjecucion(cl.clarochile.osbservicios.PlataformaPagoNotificarSAF.ResultadoEjecucionType resultadoEjecucion) {
        this.resultadoEjecucion = resultadoEjecucion;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof DevuelveSaldoFavorRespuestaType)) return false;
        DevuelveSaldoFavorRespuestaType other = (DevuelveSaldoFavorRespuestaType) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.identificadorTransaccion==null && other.getIdentificadorTransaccion()==null) || 
             (this.identificadorTransaccion!=null &&
              this.identificadorTransaccion.equals(other.getIdentificadorTransaccion()))) &&
            ((this.fechaTransaccion==null && other.getFechaTransaccion()==null) || 
             (this.fechaTransaccion!=null &&
              this.fechaTransaccion.equals(other.getFechaTransaccion()))) &&
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
        if (getIdentificadorTransaccion() != null) {
            _hashCode += getIdentificadorTransaccion().hashCode();
        }
        if (getFechaTransaccion() != null) {
            _hashCode += getFechaTransaccion().hashCode();
        }
        if (getResultadoEjecucion() != null) {
            _hashCode += getResultadoEjecucion().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(DevuelveSaldoFavorRespuestaType.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://osbcorp.vtr.cl/REC/EMP/DevolverSaldoFavor", "DevuelveSaldoFavorRespuestaType"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("identificadorTransaccion");
        elemField.setXmlName(new javax.xml.namespace.QName("http://osbcorp.vtr.cl/REC/EMP/DevolverSaldoFavor", "identificadorTransaccion"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("fechaTransaccion");
        elemField.setXmlName(new javax.xml.namespace.QName("http://osbcorp.vtr.cl/REC/EMP/DevolverSaldoFavor", "fechaTransaccion"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("resultadoEjecucion");
        elemField.setXmlName(new javax.xml.namespace.QName("http://osbcorp.vtr.cl/REC/EMP/DevolverSaldoFavor", "resultadoEjecucion"));
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
