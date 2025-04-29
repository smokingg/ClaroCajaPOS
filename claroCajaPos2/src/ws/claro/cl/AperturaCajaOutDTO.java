/**
 * AperturaCajaOutDTO.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package ws.claro.cl;

public class AperturaCajaOutDTO  implements java.io.Serializable {
    private int canal;

    private int codRecaudador;

    private int entidad;

    private java.lang.String estado;

    private java.lang.String nombreRecaudador;

    private java.lang.String retCode;

    private java.lang.String retDesc;

    private int sessionId;

    public AperturaCajaOutDTO() {
    }

    public AperturaCajaOutDTO(
           int canal,
           int codRecaudador,
           int entidad,
           java.lang.String estado,
           java.lang.String nombreRecaudador,
           java.lang.String retCode,
           java.lang.String retDesc,
           int sessionId) {
           this.canal = canal;
           this.codRecaudador = codRecaudador;
           this.entidad = entidad;
           this.estado = estado;
           this.nombreRecaudador = nombreRecaudador;
           this.retCode = retCode;
           this.retDesc = retDesc;
           this.sessionId = sessionId;
    }


    /**
     * Gets the canal value for this AperturaCajaOutDTO.
     * 
     * @return canal
     */
    public int getCanal() {
        return canal;
    }


    /**
     * Sets the canal value for this AperturaCajaOutDTO.
     * 
     * @param canal
     */
    public void setCanal(int canal) {
        this.canal = canal;
    }


    /**
     * Gets the codRecaudador value for this AperturaCajaOutDTO.
     * 
     * @return codRecaudador
     */
    public int getCodRecaudador() {
        return codRecaudador;
    }


    /**
     * Sets the codRecaudador value for this AperturaCajaOutDTO.
     * 
     * @param codRecaudador
     */
    public void setCodRecaudador(int codRecaudador) {
        this.codRecaudador = codRecaudador;
    }


    /**
     * Gets the entidad value for this AperturaCajaOutDTO.
     * 
     * @return entidad
     */
    public int getEntidad() {
        return entidad;
    }


    /**
     * Sets the entidad value for this AperturaCajaOutDTO.
     * 
     * @param entidad
     */
    public void setEntidad(int entidad) {
        this.entidad = entidad;
    }


    /**
     * Gets the estado value for this AperturaCajaOutDTO.
     * 
     * @return estado
     */
    public java.lang.String getEstado() {
        return estado;
    }


    /**
     * Sets the estado value for this AperturaCajaOutDTO.
     * 
     * @param estado
     */
    public void setEstado(java.lang.String estado) {
        this.estado = estado;
    }


    /**
     * Gets the nombreRecaudador value for this AperturaCajaOutDTO.
     * 
     * @return nombreRecaudador
     */
    public java.lang.String getNombreRecaudador() {
        return nombreRecaudador;
    }


    /**
     * Sets the nombreRecaudador value for this AperturaCajaOutDTO.
     * 
     * @param nombreRecaudador
     */
    public void setNombreRecaudador(java.lang.String nombreRecaudador) {
        this.nombreRecaudador = nombreRecaudador;
    }


    /**
     * Gets the retCode value for this AperturaCajaOutDTO.
     * 
     * @return retCode
     */
    public java.lang.String getRetCode() {
        return retCode;
    }


    /**
     * Sets the retCode value for this AperturaCajaOutDTO.
     * 
     * @param retCode
     */
    public void setRetCode(java.lang.String retCode) {
        this.retCode = retCode;
    }


    /**
     * Gets the retDesc value for this AperturaCajaOutDTO.
     * 
     * @return retDesc
     */
    public java.lang.String getRetDesc() {
        return retDesc;
    }


    /**
     * Sets the retDesc value for this AperturaCajaOutDTO.
     * 
     * @param retDesc
     */
    public void setRetDesc(java.lang.String retDesc) {
        this.retDesc = retDesc;
    }


    /**
     * Gets the sessionId value for this AperturaCajaOutDTO.
     * 
     * @return sessionId
     */
    public int getSessionId() {
        return sessionId;
    }


    /**
     * Sets the sessionId value for this AperturaCajaOutDTO.
     * 
     * @param sessionId
     */
    public void setSessionId(int sessionId) {
        this.sessionId = sessionId;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof AperturaCajaOutDTO)) return false;
        AperturaCajaOutDTO other = (AperturaCajaOutDTO) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            this.canal == other.getCanal() &&
            this.codRecaudador == other.getCodRecaudador() &&
            this.entidad == other.getEntidad() &&
            ((this.estado==null && other.getEstado()==null) || 
             (this.estado!=null &&
              this.estado.equals(other.getEstado()))) &&
            ((this.nombreRecaudador==null && other.getNombreRecaudador()==null) || 
             (this.nombreRecaudador!=null &&
              this.nombreRecaudador.equals(other.getNombreRecaudador()))) &&
            ((this.retCode==null && other.getRetCode()==null) || 
             (this.retCode!=null &&
              this.retCode.equals(other.getRetCode()))) &&
            ((this.retDesc==null && other.getRetDesc()==null) || 
             (this.retDesc!=null &&
              this.retDesc.equals(other.getRetDesc()))) &&
            this.sessionId == other.getSessionId();
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
        _hashCode += getCanal();
        _hashCode += getCodRecaudador();
        _hashCode += getEntidad();
        if (getEstado() != null) {
            _hashCode += getEstado().hashCode();
        }
        if (getNombreRecaudador() != null) {
            _hashCode += getNombreRecaudador().hashCode();
        }
        if (getRetCode() != null) {
            _hashCode += getRetCode().hashCode();
        }
        if (getRetDesc() != null) {
            _hashCode += getRetDesc().hashCode();
        }
        _hashCode += getSessionId();
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(AperturaCajaOutDTO.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://cl.claro.ws/", "aperturaCajaOutDTO"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("canal");
        elemField.setXmlName(new javax.xml.namespace.QName("", "canal"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("codRecaudador");
        elemField.setXmlName(new javax.xml.namespace.QName("", "codRecaudador"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("entidad");
        elemField.setXmlName(new javax.xml.namespace.QName("", "entidad"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("estado");
        elemField.setXmlName(new javax.xml.namespace.QName("", "estado"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("nombreRecaudador");
        elemField.setXmlName(new javax.xml.namespace.QName("", "nombreRecaudador"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
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
        elemField.setFieldName("sessionId");
        elemField.setXmlName(new javax.xml.namespace.QName("", "sessionId"));
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
