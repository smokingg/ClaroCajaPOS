/**
 * CierreCajaOutDTO.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package ws.claro.cl;

public class CierreCajaOutDTO  implements java.io.Serializable {
    private ws.claro.cl.OperacionDTO[] anulacionCierre;

    private ws.claro.cl.ArqueoConceptoDTO[] conceptosArqueo;

    private java.lang.String fecha;

    private ws.claro.cl.ItemArqueoDTO[] itemsArqueo;

    private java.lang.String retCode;

    private java.lang.String retDesc;

    private int sesion;

    public CierreCajaOutDTO() {
    }

    public CierreCajaOutDTO(
           ws.claro.cl.OperacionDTO[] anulacionCierre,
           ws.claro.cl.ArqueoConceptoDTO[] conceptosArqueo,
           java.lang.String fecha,
           ws.claro.cl.ItemArqueoDTO[] itemsArqueo,
           java.lang.String retCode,
           java.lang.String retDesc,
           int sesion) {
           this.anulacionCierre = anulacionCierre;
           this.conceptosArqueo = conceptosArqueo;
           this.fecha = fecha;
           this.itemsArqueo = itemsArqueo;
           this.retCode = retCode;
           this.retDesc = retDesc;
           this.sesion = sesion;
    }


    /**
     * Gets the anulacionCierre value for this CierreCajaOutDTO.
     * 
     * @return anulacionCierre
     */
    public ws.claro.cl.OperacionDTO[] getAnulacionCierre() {
        return anulacionCierre;
    }


    /**
     * Sets the anulacionCierre value for this CierreCajaOutDTO.
     * 
     * @param anulacionCierre
     */
    public void setAnulacionCierre(ws.claro.cl.OperacionDTO[] anulacionCierre) {
        this.anulacionCierre = anulacionCierre;
    }

    public ws.claro.cl.OperacionDTO getAnulacionCierre(int i) {
        return this.anulacionCierre[i];
    }

    public void setAnulacionCierre(int i, ws.claro.cl.OperacionDTO _value) {
        this.anulacionCierre[i] = _value;
    }


    /**
     * Gets the conceptosArqueo value for this CierreCajaOutDTO.
     * 
     * @return conceptosArqueo
     */
    public ws.claro.cl.ArqueoConceptoDTO[] getConceptosArqueo() {
        return conceptosArqueo;
    }


    /**
     * Sets the conceptosArqueo value for this CierreCajaOutDTO.
     * 
     * @param conceptosArqueo
     */
    public void setConceptosArqueo(ws.claro.cl.ArqueoConceptoDTO[] conceptosArqueo) {
        this.conceptosArqueo = conceptosArqueo;
    }

    public ws.claro.cl.ArqueoConceptoDTO getConceptosArqueo(int i) {
        return this.conceptosArqueo[i];
    }

    public void setConceptosArqueo(int i, ws.claro.cl.ArqueoConceptoDTO _value) {
        this.conceptosArqueo[i] = _value;
    }


    /**
     * Gets the fecha value for this CierreCajaOutDTO.
     * 
     * @return fecha
     */
    public java.lang.String getFecha() {
        return fecha;
    }


    /**
     * Sets the fecha value for this CierreCajaOutDTO.
     * 
     * @param fecha
     */
    public void setFecha(java.lang.String fecha) {
        this.fecha = fecha;
    }


    /**
     * Gets the itemsArqueo value for this CierreCajaOutDTO.
     * 
     * @return itemsArqueo
     */
    public ws.claro.cl.ItemArqueoDTO[] getItemsArqueo() {
        return itemsArqueo;
    }


    /**
     * Sets the itemsArqueo value for this CierreCajaOutDTO.
     * 
     * @param itemsArqueo
     */
    public void setItemsArqueo(ws.claro.cl.ItemArqueoDTO[] itemsArqueo) {
        this.itemsArqueo = itemsArqueo;
    }

    public ws.claro.cl.ItemArqueoDTO getItemsArqueo(int i) {
        return this.itemsArqueo[i];
    }

    public void setItemsArqueo(int i, ws.claro.cl.ItemArqueoDTO _value) {
        this.itemsArqueo[i] = _value;
    }


    /**
     * Gets the retCode value for this CierreCajaOutDTO.
     * 
     * @return retCode
     */
    public java.lang.String getRetCode() {
        return retCode;
    }


    /**
     * Sets the retCode value for this CierreCajaOutDTO.
     * 
     * @param retCode
     */
    public void setRetCode(java.lang.String retCode) {
        this.retCode = retCode;
    }


    /**
     * Gets the retDesc value for this CierreCajaOutDTO.
     * 
     * @return retDesc
     */
    public java.lang.String getRetDesc() {
        return retDesc;
    }


    /**
     * Sets the retDesc value for this CierreCajaOutDTO.
     * 
     * @param retDesc
     */
    public void setRetDesc(java.lang.String retDesc) {
        this.retDesc = retDesc;
    }


    /**
     * Gets the sesion value for this CierreCajaOutDTO.
     * 
     * @return sesion
     */
    public int getSesion() {
        return sesion;
    }


    /**
     * Sets the sesion value for this CierreCajaOutDTO.
     * 
     * @param sesion
     */
    public void setSesion(int sesion) {
        this.sesion = sesion;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof CierreCajaOutDTO)) return false;
        CierreCajaOutDTO other = (CierreCajaOutDTO) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.anulacionCierre==null && other.getAnulacionCierre()==null) || 
             (this.anulacionCierre!=null &&
              java.util.Arrays.equals(this.anulacionCierre, other.getAnulacionCierre()))) &&
            ((this.conceptosArqueo==null && other.getConceptosArqueo()==null) || 
             (this.conceptosArqueo!=null &&
              java.util.Arrays.equals(this.conceptosArqueo, other.getConceptosArqueo()))) &&
            ((this.fecha==null && other.getFecha()==null) || 
             (this.fecha!=null &&
              this.fecha.equals(other.getFecha()))) &&
            ((this.itemsArqueo==null && other.getItemsArqueo()==null) || 
             (this.itemsArqueo!=null &&
              java.util.Arrays.equals(this.itemsArqueo, other.getItemsArqueo()))) &&
            ((this.retCode==null && other.getRetCode()==null) || 
             (this.retCode!=null &&
              this.retCode.equals(other.getRetCode()))) &&
            ((this.retDesc==null && other.getRetDesc()==null) || 
             (this.retDesc!=null &&
              this.retDesc.equals(other.getRetDesc()))) &&
            this.sesion == other.getSesion();
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
        if (getAnulacionCierre() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getAnulacionCierre());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getAnulacionCierre(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getConceptosArqueo() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getConceptosArqueo());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getConceptosArqueo(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getFecha() != null) {
            _hashCode += getFecha().hashCode();
        }
        if (getItemsArqueo() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getItemsArqueo());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getItemsArqueo(), i);
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
        _hashCode += getSesion();
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(CierreCajaOutDTO.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://cl.claro.ws/", "cierreCajaOutDTO"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("anulacionCierre");
        elemField.setXmlName(new javax.xml.namespace.QName("", "anulacionCierre"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://cl.claro.ws/", "operacionDTO"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        elemField.setMaxOccursUnbounded(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("conceptosArqueo");
        elemField.setXmlName(new javax.xml.namespace.QName("", "conceptosArqueo"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://cl.claro.ws/", "arqueoConceptoDTO"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        elemField.setMaxOccursUnbounded(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("fecha");
        elemField.setXmlName(new javax.xml.namespace.QName("", "fecha"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("itemsArqueo");
        elemField.setXmlName(new javax.xml.namespace.QName("", "itemsArqueo"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://cl.claro.ws/", "itemArqueoDTO"));
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
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("sesion");
        elemField.setXmlName(new javax.xml.namespace.QName("", "sesion"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
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
