/**
 * ConsultarBancosOutDTO.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package ws.claro.cl;

public class ConsultarBancosOutDTO  implements java.io.Serializable {
    private ws.claro.cl.BancoDTO[] bancos;

    private java.lang.String retCode;

    private java.lang.String retDesc;

    public ConsultarBancosOutDTO() {
    }

    public ConsultarBancosOutDTO(
           ws.claro.cl.BancoDTO[] bancos,
           java.lang.String retCode,
           java.lang.String retDesc) {
           this.bancos = bancos;
           this.retCode = retCode;
           this.retDesc = retDesc;
    }


    /**
     * Gets the bancos value for this ConsultarBancosOutDTO.
     * 
     * @return bancos
     */
    public ws.claro.cl.BancoDTO[] getBancos() {
        return bancos;
    }


    /**
     * Sets the bancos value for this ConsultarBancosOutDTO.
     * 
     * @param bancos
     */
    public void setBancos(ws.claro.cl.BancoDTO[] bancos) {
        this.bancos = bancos;
    }

    public ws.claro.cl.BancoDTO getBancos(int i) {
        return this.bancos[i];
    }

    public void setBancos(int i, ws.claro.cl.BancoDTO _value) {
        this.bancos[i] = _value;
    }


    /**
     * Gets the retCode value for this ConsultarBancosOutDTO.
     * 
     * @return retCode
     */
    public java.lang.String getRetCode() {
        return retCode;
    }


    /**
     * Sets the retCode value for this ConsultarBancosOutDTO.
     * 
     * @param retCode
     */
    public void setRetCode(java.lang.String retCode) {
        this.retCode = retCode;
    }


    /**
     * Gets the retDesc value for this ConsultarBancosOutDTO.
     * 
     * @return retDesc
     */
    public java.lang.String getRetDesc() {
        return retDesc;
    }


    /**
     * Sets the retDesc value for this ConsultarBancosOutDTO.
     * 
     * @param retDesc
     */
    public void setRetDesc(java.lang.String retDesc) {
        this.retDesc = retDesc;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof ConsultarBancosOutDTO)) return false;
        ConsultarBancosOutDTO other = (ConsultarBancosOutDTO) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.bancos==null && other.getBancos()==null) || 
             (this.bancos!=null &&
              java.util.Arrays.equals(this.bancos, other.getBancos()))) &&
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
        if (getBancos() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getBancos());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getBancos(), i);
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
        new org.apache.axis.description.TypeDesc(ConsultarBancosOutDTO.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://cl.claro.ws/", "consultarBancosOutDTO"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("bancos");
        elemField.setXmlName(new javax.xml.namespace.QName("", "bancos"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://cl.claro.ws/", "bancoDTO"));
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
