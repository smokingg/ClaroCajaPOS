/**
 * InicializarCajaDTO.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package ws.claro.cl;

public class InicializarCajaDTO  implements java.io.Serializable {
    private ws.claro.cl.ParametroCajaDTO[] parametrosCaja;

    private java.lang.String retCode;

    private java.lang.String retDesc;

    public InicializarCajaDTO() {
    }

    public InicializarCajaDTO(
           ws.claro.cl.ParametroCajaDTO[] parametrosCaja,
           java.lang.String retCode,
           java.lang.String retDesc) {
           this.parametrosCaja = parametrosCaja;
           this.retCode = retCode;
           this.retDesc = retDesc;
    }


    /**
     * Gets the parametrosCaja value for this InicializarCajaDTO.
     * 
     * @return parametrosCaja
     */
    public ws.claro.cl.ParametroCajaDTO[] getParametrosCaja() {
        return parametrosCaja;
    }


    /**
     * Sets the parametrosCaja value for this InicializarCajaDTO.
     * 
     * @param parametrosCaja
     */
    public void setParametrosCaja(ws.claro.cl.ParametroCajaDTO[] parametrosCaja) {
        this.parametrosCaja = parametrosCaja;
    }

    public ws.claro.cl.ParametroCajaDTO getParametrosCaja(int i) {
        return this.parametrosCaja[i];
    }

    public void setParametrosCaja(int i, ws.claro.cl.ParametroCajaDTO _value) {
        this.parametrosCaja[i] = _value;
    }


    /**
     * Gets the retCode value for this InicializarCajaDTO.
     * 
     * @return retCode
     */
    public java.lang.String getRetCode() {
        return retCode;
    }


    /**
     * Sets the retCode value for this InicializarCajaDTO.
     * 
     * @param retCode
     */
    public void setRetCode(java.lang.String retCode) {
        this.retCode = retCode;
    }


    /**
     * Gets the retDesc value for this InicializarCajaDTO.
     * 
     * @return retDesc
     */
    public java.lang.String getRetDesc() {
        return retDesc;
    }


    /**
     * Sets the retDesc value for this InicializarCajaDTO.
     * 
     * @param retDesc
     */
    public void setRetDesc(java.lang.String retDesc) {
        this.retDesc = retDesc;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof InicializarCajaDTO)) return false;
        InicializarCajaDTO other = (InicializarCajaDTO) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.parametrosCaja==null && other.getParametrosCaja()==null) || 
             (this.parametrosCaja!=null &&
              java.util.Arrays.equals(this.parametrosCaja, other.getParametrosCaja()))) &&
            ((this.retCode==null && other.getRetCode()==null) || 
             (this.retCode!=null &&
              this.retCode.equals(other.getRetCode()))) &&
            ((this.retDesc==null && other.getRetDesc()==null) || 
             (this.retDesc!=null &&
              this.retDesc.equals(other.getRetDesc())));
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
        if (getParametrosCaja() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getParametrosCaja());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getParametrosCaja(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getRetCode() != null) {
            _hashCode += getRetCode().hashCode();
        }
        if (getRetDesc() != null) {
            _hashCode += getRetDesc().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(InicializarCajaDTO.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://cl.claro.ws/", "inicializarCajaDTO"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("parametrosCaja");
        elemField.setXmlName(new javax.xml.namespace.QName("", "parametrosCaja"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://cl.claro.ws/", "parametroCajaDTO"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        elemField.setMaxOccursUnbounded(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("retCode");
        elemField.setXmlName(new javax.xml.namespace.QName("", "retCode"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("retDesc");
        elemField.setXmlName(new javax.xml.namespace.QName("", "retDesc"));
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
