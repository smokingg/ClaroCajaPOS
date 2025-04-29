/**
 * ListaRecaudadoresOutDTO.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package ws.claro.cl;

public class ListaRecaudadoresOutDTO  implements java.io.Serializable {
    private ws.claro.cl.RecaudadorDTO[] listaRecaudadores;

    private java.lang.String retCode;

    private java.lang.String retDesc;

    public ListaRecaudadoresOutDTO() {
    }

    public ListaRecaudadoresOutDTO(
           ws.claro.cl.RecaudadorDTO[] listaRecaudadores,
           java.lang.String retCode,
           java.lang.String retDesc) {
           this.listaRecaudadores = listaRecaudadores;
           this.retCode = retCode;
           this.retDesc = retDesc;
    }


    /**
     * Gets the listaRecaudadores value for this ListaRecaudadoresOutDTO.
     * 
     * @return listaRecaudadores
     */
    public ws.claro.cl.RecaudadorDTO[] getListaRecaudadores() {
        return listaRecaudadores;
    }


    /**
     * Sets the listaRecaudadores value for this ListaRecaudadoresOutDTO.
     * 
     * @param listaRecaudadores
     */
    public void setListaRecaudadores(ws.claro.cl.RecaudadorDTO[] listaRecaudadores) {
        this.listaRecaudadores = listaRecaudadores;
    }

    public ws.claro.cl.RecaudadorDTO getListaRecaudadores(int i) {
        return this.listaRecaudadores[i];
    }

    public void setListaRecaudadores(int i, ws.claro.cl.RecaudadorDTO _value) {
        this.listaRecaudadores[i] = _value;
    }


    /**
     * Gets the retCode value for this ListaRecaudadoresOutDTO.
     * 
     * @return retCode
     */
    public java.lang.String getRetCode() {
        return retCode;
    }


    /**
     * Sets the retCode value for this ListaRecaudadoresOutDTO.
     * 
     * @param retCode
     */
    public void setRetCode(java.lang.String retCode) {
        this.retCode = retCode;
    }


    /**
     * Gets the retDesc value for this ListaRecaudadoresOutDTO.
     * 
     * @return retDesc
     */
    public java.lang.String getRetDesc() {
        return retDesc;
    }


    /**
     * Sets the retDesc value for this ListaRecaudadoresOutDTO.
     * 
     * @param retDesc
     */
    public void setRetDesc(java.lang.String retDesc) {
        this.retDesc = retDesc;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof ListaRecaudadoresOutDTO)) return false;
        ListaRecaudadoresOutDTO other = (ListaRecaudadoresOutDTO) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.listaRecaudadores==null && other.getListaRecaudadores()==null) || 
             (this.listaRecaudadores!=null &&
              java.util.Arrays.equals(this.listaRecaudadores, other.getListaRecaudadores()))) &&
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
        if (getListaRecaudadores() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getListaRecaudadores());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getListaRecaudadores(), i);
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
        new org.apache.axis.description.TypeDesc(ListaRecaudadoresOutDTO.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://cl.claro.ws/", "listaRecaudadoresOutDTO"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("listaRecaudadores");
        elemField.setXmlName(new javax.xml.namespace.QName("", "listaRecaudadores"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://cl.claro.ws/", "recaudadorDTO"));
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
