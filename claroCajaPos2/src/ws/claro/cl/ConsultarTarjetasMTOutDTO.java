/**
 * ConsultarTarjetasMTOutDTO.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package ws.claro.cl;

public class ConsultarTarjetasMTOutDTO  implements java.io.Serializable {
    private java.lang.String retCode;

    private java.lang.String retDesc;

    private ws.claro.cl.TarjetaMultitiendaDTO[] tajetasMultiTienda;

    public ConsultarTarjetasMTOutDTO() {
    }

    public ConsultarTarjetasMTOutDTO(
           java.lang.String retCode,
           java.lang.String retDesc,
           ws.claro.cl.TarjetaMultitiendaDTO[] tajetasMultiTienda) {
           this.retCode = retCode;
           this.retDesc = retDesc;
           this.tajetasMultiTienda = tajetasMultiTienda;
    }


    /**
     * Gets the retCode value for this ConsultarTarjetasMTOutDTO.
     * 
     * @return retCode
     */
    public java.lang.String getRetCode() {
        return retCode;
    }


    /**
     * Sets the retCode value for this ConsultarTarjetasMTOutDTO.
     * 
     * @param retCode
     */
    public void setRetCode(java.lang.String retCode) {
        this.retCode = retCode;
    }


    /**
     * Gets the retDesc value for this ConsultarTarjetasMTOutDTO.
     * 
     * @return retDesc
     */
    public java.lang.String getRetDesc() {
        return retDesc;
    }


    /**
     * Sets the retDesc value for this ConsultarTarjetasMTOutDTO.
     * 
     * @param retDesc
     */
    public void setRetDesc(java.lang.String retDesc) {
        this.retDesc = retDesc;
    }


    /**
     * Gets the tajetasMultiTienda value for this ConsultarTarjetasMTOutDTO.
     * 
     * @return tajetasMultiTienda
     */
    public ws.claro.cl.TarjetaMultitiendaDTO[] getTajetasMultiTienda() {
        return tajetasMultiTienda;
    }


    /**
     * Sets the tajetasMultiTienda value for this ConsultarTarjetasMTOutDTO.
     * 
     * @param tajetasMultiTienda
     */
    public void setTajetasMultiTienda(ws.claro.cl.TarjetaMultitiendaDTO[] tajetasMultiTienda) {
        this.tajetasMultiTienda = tajetasMultiTienda;
    }

    public ws.claro.cl.TarjetaMultitiendaDTO getTajetasMultiTienda(int i) {
        return this.tajetasMultiTienda[i];
    }

    public void setTajetasMultiTienda(int i, ws.claro.cl.TarjetaMultitiendaDTO _value) {
        this.tajetasMultiTienda[i] = _value;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof ConsultarTarjetasMTOutDTO)) return false;
        ConsultarTarjetasMTOutDTO other = (ConsultarTarjetasMTOutDTO) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.retCode==null && other.getRetCode()==null) || 
             (this.retCode!=null &&
              this.retCode.equals(other.getRetCode()))) &&
            ((this.retDesc==null && other.getRetDesc()==null) || 
             (this.retDesc!=null &&
              this.retDesc.equals(other.getRetDesc()))) &&
            ((this.tajetasMultiTienda==null && other.getTajetasMultiTienda()==null) || 
             (this.tajetasMultiTienda!=null &&
              java.util.Arrays.equals(this.tajetasMultiTienda, other.getTajetasMultiTienda())));
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
        if (getRetCode() != null) {
            _hashCode += getRetCode().hashCode();
        }
        if (getRetDesc() != null) {
            _hashCode += getRetDesc().hashCode();
        }
        if (getTajetasMultiTienda() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getTajetasMultiTienda());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getTajetasMultiTienda(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(ConsultarTarjetasMTOutDTO.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://cl.claro.ws/", "consultarTarjetasMTOutDTO"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
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
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("tajetasMultiTienda");
        elemField.setXmlName(new javax.xml.namespace.QName("", "tajetasMultiTienda"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://cl.claro.ws/", "tarjetaMultitiendaDTO"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        elemField.setMaxOccursUnbounded(true);
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
