/**
 * OperacionTbkResponseDTO.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package ws.claro.cl;

public class OperacionTbkResponseDTO  implements java.io.Serializable {
    private long cantRegistros;

    private ws.claro.cl.OperacionTbkDTO[] operacionTbkList;

    private java.lang.String retCode;

    private java.lang.String retDesc;

    public OperacionTbkResponseDTO() {
    }

    public OperacionTbkResponseDTO(
           long cantRegistros,
           ws.claro.cl.OperacionTbkDTO[] operacionTbkList,
           java.lang.String retCode,
           java.lang.String retDesc) {
           this.cantRegistros = cantRegistros;
           this.operacionTbkList = operacionTbkList;
           this.retCode = retCode;
           this.retDesc = retDesc;
    }


    /**
     * Gets the cantRegistros value for this OperacionTbkResponseDTO.
     * 
     * @return cantRegistros
     */
    public long getCantRegistros() {
        return cantRegistros;
    }


    /**
     * Sets the cantRegistros value for this OperacionTbkResponseDTO.
     * 
     * @param cantRegistros
     */
    public void setCantRegistros(long cantRegistros) {
        this.cantRegistros = cantRegistros;
    }


    /**
     * Gets the operacionTbkList value for this OperacionTbkResponseDTO.
     * 
     * @return operacionTbkList
     */
    public ws.claro.cl.OperacionTbkDTO[] getOperacionTbkList() {
        return operacionTbkList;
    }


    /**
     * Sets the operacionTbkList value for this OperacionTbkResponseDTO.
     * 
     * @param operacionTbkList
     */
    public void setOperacionTbkList(ws.claro.cl.OperacionTbkDTO[] operacionTbkList) {
        this.operacionTbkList = operacionTbkList;
    }

    public ws.claro.cl.OperacionTbkDTO getOperacionTbkList(int i) {
        return this.operacionTbkList[i];
    }

    public void setOperacionTbkList(int i, ws.claro.cl.OperacionTbkDTO _value) {
        this.operacionTbkList[i] = _value;
    }


    /**
     * Gets the retCode value for this OperacionTbkResponseDTO.
     * 
     * @return retCode
     */
    public java.lang.String getRetCode() {
        return retCode;
    }


    /**
     * Sets the retCode value for this OperacionTbkResponseDTO.
     * 
     * @param retCode
     */
    public void setRetCode(java.lang.String retCode) {
        this.retCode = retCode;
    }


    /**
     * Gets the retDesc value for this OperacionTbkResponseDTO.
     * 
     * @return retDesc
     */
    public java.lang.String getRetDesc() {
        return retDesc;
    }


    /**
     * Sets the retDesc value for this OperacionTbkResponseDTO.
     * 
     * @param retDesc
     */
    public void setRetDesc(java.lang.String retDesc) {
        this.retDesc = retDesc;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof OperacionTbkResponseDTO)) return false;
        OperacionTbkResponseDTO other = (OperacionTbkResponseDTO) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            this.cantRegistros == other.getCantRegistros() &&
            ((this.operacionTbkList==null && other.getOperacionTbkList()==null) || 
             (this.operacionTbkList!=null &&
              java.util.Arrays.equals(this.operacionTbkList, other.getOperacionTbkList()))) &&
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
        _hashCode += new Long(getCantRegistros()).hashCode();
        if (getOperacionTbkList() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getOperacionTbkList());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getOperacionTbkList(), i);
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
        new org.apache.axis.description.TypeDesc(OperacionTbkResponseDTO.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://cl.claro.ws/", "operacionTbkResponseDTO"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("cantRegistros");
        elemField.setXmlName(new javax.xml.namespace.QName("", "cantRegistros"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "long"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("operacionTbkList");
        elemField.setXmlName(new javax.xml.namespace.QName("", "operacionTbkList"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://cl.claro.ws/", "operacionTbkDTO"));
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
