/**
 * ResultadoEjecucionType.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package cl.clarochile.osbservicios.PlataformaPagoConsultaSAF;

public class ResultadoEjecucionType  implements java.io.Serializable {
    private boolean ejecucionExitosa;

    private java.lang.String codigoError;

    private java.lang.String mensaje;

    public ResultadoEjecucionType() {
    }

    public ResultadoEjecucionType(
           boolean ejecucionExitosa,
           java.lang.String codigoError,
           java.lang.String mensaje) {
           this.ejecucionExitosa = ejecucionExitosa;
           this.codigoError = codigoError;
           this.mensaje = mensaje;
    }


    /**
     * Gets the ejecucionExitosa value for this ResultadoEjecucionType.
     * 
     * @return ejecucionExitosa
     */
    public boolean isEjecucionExitosa() {
        return ejecucionExitosa;
    }


    /**
     * Sets the ejecucionExitosa value for this ResultadoEjecucionType.
     * 
     * @param ejecucionExitosa
     */
    public void setEjecucionExitosa(boolean ejecucionExitosa) {
        this.ejecucionExitosa = ejecucionExitosa;
    }


    /**
     * Gets the codigoError value for this ResultadoEjecucionType.
     * 
     * @return codigoError
     */
    public java.lang.String getCodigoError() {
        return codigoError;
    }


    /**
     * Sets the codigoError value for this ResultadoEjecucionType.
     * 
     * @param codigoError
     */
    public void setCodigoError(java.lang.String codigoError) {
        this.codigoError = codigoError;
    }


    /**
     * Gets the mensaje value for this ResultadoEjecucionType.
     * 
     * @return mensaje
     */
    public java.lang.String getMensaje() {
        return mensaje;
    }


    /**
     * Sets the mensaje value for this ResultadoEjecucionType.
     * 
     * @param mensaje
     */
    public void setMensaje(java.lang.String mensaje) {
        this.mensaje = mensaje;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof ResultadoEjecucionType)) return false;
        ResultadoEjecucionType other = (ResultadoEjecucionType) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            this.ejecucionExitosa == other.isEjecucionExitosa() &&
            ((this.codigoError==null && other.getCodigoError()==null) || 
             (this.codigoError!=null &&
              this.codigoError.equals(other.getCodigoError()))) &&
            ((this.mensaje==null && other.getMensaje()==null) || 
             (this.mensaje!=null &&
              this.mensaje.equals(other.getMensaje())));
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
        _hashCode += (isEjecucionExitosa() ? Boolean.TRUE : Boolean.FALSE).hashCode();
        if (getCodigoError() != null) {
            _hashCode += getCodigoError().hashCode();
        }
        if (getMensaje() != null) {
            _hashCode += getMensaje().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(ResultadoEjecucionType.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://osbcorp.vtr.cl/GLOBAL/EMP/ResultadoEjecucion", "ResultadoEjecucionType"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("ejecucionExitosa");
        elemField.setXmlName(new javax.xml.namespace.QName("http://osbcorp.vtr.cl/GLOBAL/EMP/ResultadoEjecucion", "ejecucionExitosa"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "boolean"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("codigoError");
        elemField.setXmlName(new javax.xml.namespace.QName("http://osbcorp.vtr.cl/GLOBAL/EMP/ResultadoEjecucion", "codigoError"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("mensaje");
        elemField.setXmlName(new javax.xml.namespace.QName("http://osbcorp.vtr.cl/GLOBAL/EMP/ResultadoEjecucion", "mensaje"));
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
