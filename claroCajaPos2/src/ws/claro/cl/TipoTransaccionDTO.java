/**
 * TipoTransaccionDTO.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package ws.claro.cl;

public class TipoTransaccionDTO  implements java.io.Serializable {
    private java.lang.String desTransaccion;

    private java.lang.String regjournal;

    private java.lang.String tipoTransaccion;

    public TipoTransaccionDTO() {
    }

    public TipoTransaccionDTO(
           java.lang.String desTransaccion,
           java.lang.String regjournal,
           java.lang.String tipoTransaccion) {
           this.desTransaccion = desTransaccion;
           this.regjournal = regjournal;
           this.tipoTransaccion = tipoTransaccion;
    }


    /**
     * Gets the desTransaccion value for this TipoTransaccionDTO.
     * 
     * @return desTransaccion
     */
    public java.lang.String getDesTransaccion() {
        return desTransaccion;
    }


    /**
     * Sets the desTransaccion value for this TipoTransaccionDTO.
     * 
     * @param desTransaccion
     */
    public void setDesTransaccion(java.lang.String desTransaccion) {
        this.desTransaccion = desTransaccion;
    }


    /**
     * Gets the regjournal value for this TipoTransaccionDTO.
     * 
     * @return regjournal
     */
    public java.lang.String getRegjournal() {
        return regjournal;
    }


    /**
     * Sets the regjournal value for this TipoTransaccionDTO.
     * 
     * @param regjournal
     */
    public void setRegjournal(java.lang.String regjournal) {
        this.regjournal = regjournal;
    }


    /**
     * Gets the tipoTransaccion value for this TipoTransaccionDTO.
     * 
     * @return tipoTransaccion
     */
    public java.lang.String getTipoTransaccion() {
        return tipoTransaccion;
    }


    /**
     * Sets the tipoTransaccion value for this TipoTransaccionDTO.
     * 
     * @param tipoTransaccion
     */
    public void setTipoTransaccion(java.lang.String tipoTransaccion) {
        this.tipoTransaccion = tipoTransaccion;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof TipoTransaccionDTO)) return false;
        TipoTransaccionDTO other = (TipoTransaccionDTO) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.desTransaccion==null && other.getDesTransaccion()==null) || 
             (this.desTransaccion!=null &&
              this.desTransaccion.equals(other.getDesTransaccion()))) &&
            ((this.regjournal==null && other.getRegjournal()==null) || 
             (this.regjournal!=null &&
              this.regjournal.equals(other.getRegjournal()))) &&
            ((this.tipoTransaccion==null && other.getTipoTransaccion()==null) || 
             (this.tipoTransaccion!=null &&
              this.tipoTransaccion.equals(other.getTipoTransaccion())));
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
        if (getDesTransaccion() != null) {
            _hashCode += getDesTransaccion().hashCode();
        }
        if (getRegjournal() != null) {
            _hashCode += getRegjournal().hashCode();
        }
        if (getTipoTransaccion() != null) {
            _hashCode += getTipoTransaccion().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(TipoTransaccionDTO.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://cl.claro.ws/", "tipoTransaccionDTO"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("desTransaccion");
        elemField.setXmlName(new javax.xml.namespace.QName("", "desTransaccion"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("regjournal");
        elemField.setXmlName(new javax.xml.namespace.QName("", "regjournal"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("tipoTransaccion");
        elemField.setXmlName(new javax.xml.namespace.QName("", "tipoTransaccion"));
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
