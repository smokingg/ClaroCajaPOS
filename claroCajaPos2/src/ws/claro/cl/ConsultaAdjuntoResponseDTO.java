/**
 * ConsultaAdjuntoResponseDTO.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package ws.claro.cl;

public class ConsultaAdjuntoResponseDTO  extends ws.claro.cl.GenericResponseDTO  implements java.io.Serializable {
    private java.lang.String parametroDos;

    private java.lang.String parametroTres;

    private java.lang.String parametroUno;

    public ConsultaAdjuntoResponseDTO() {
    }

    public ConsultaAdjuntoResponseDTO(
           java.lang.String retCode,
           java.lang.String retDesc,
           java.lang.String parametroDos,
           java.lang.String parametroTres,
           java.lang.String parametroUno) {
        super(
            retCode,
            retDesc);
        this.parametroDos = parametroDos;
        this.parametroTres = parametroTres;
        this.parametroUno = parametroUno;
    }


    /**
     * Gets the parametroDos value for this ConsultaAdjuntoResponseDTO.
     * 
     * @return parametroDos
     */
    public java.lang.String getParametroDos() {
        return parametroDos;
    }


    /**
     * Sets the parametroDos value for this ConsultaAdjuntoResponseDTO.
     * 
     * @param parametroDos
     */
    public void setParametroDos(java.lang.String parametroDos) {
        this.parametroDos = parametroDos;
    }


    /**
     * Gets the parametroTres value for this ConsultaAdjuntoResponseDTO.
     * 
     * @return parametroTres
     */
    public java.lang.String getParametroTres() {
        return parametroTres;
    }


    /**
     * Sets the parametroTres value for this ConsultaAdjuntoResponseDTO.
     * 
     * @param parametroTres
     */
    public void setParametroTres(java.lang.String parametroTres) {
        this.parametroTres = parametroTres;
    }


    /**
     * Gets the parametroUno value for this ConsultaAdjuntoResponseDTO.
     * 
     * @return parametroUno
     */
    public java.lang.String getParametroUno() {
        return parametroUno;
    }


    /**
     * Sets the parametroUno value for this ConsultaAdjuntoResponseDTO.
     * 
     * @param parametroUno
     */
    public void setParametroUno(java.lang.String parametroUno) {
        this.parametroUno = parametroUno;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof ConsultaAdjuntoResponseDTO)) return false;
        ConsultaAdjuntoResponseDTO other = (ConsultaAdjuntoResponseDTO) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = super.equals(obj) && 
            ((this.parametroDos==null && other.getParametroDos()==null) || 
             (this.parametroDos!=null &&
              this.parametroDos.equals(other.getParametroDos()))) &&
            ((this.parametroTres==null && other.getParametroTres()==null) || 
             (this.parametroTres!=null &&
              this.parametroTres.equals(other.getParametroTres()))) &&
            ((this.parametroUno==null && other.getParametroUno()==null) || 
             (this.parametroUno!=null &&
              this.parametroUno.equals(other.getParametroUno())));
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
        if (getParametroDos() != null) {
            _hashCode += getParametroDos().hashCode();
        }
        if (getParametroTres() != null) {
            _hashCode += getParametroTres().hashCode();
        }
        if (getParametroUno() != null) {
            _hashCode += getParametroUno().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(ConsultaAdjuntoResponseDTO.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://cl.claro.ws/", "consultaAdjuntoResponseDTO"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("parametroDos");
        elemField.setXmlName(new javax.xml.namespace.QName("", "parametroDos"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("parametroTres");
        elemField.setXmlName(new javax.xml.namespace.QName("", "parametroTres"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("parametroUno");
        elemField.setXmlName(new javax.xml.namespace.QName("", "parametroUno"));
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
