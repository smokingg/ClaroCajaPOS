/**
 * ObtenerDetalleCierreResponseDTO.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package ws.claro.cl;

public class ObtenerDetalleCierreResponseDTO  extends ws.claro.cl.GenericResponseDTO  implements java.io.Serializable {
    private long cantidadJustificaciones;

    private long cantidadReversas;

    private long diferenciaCuadratura;

    private long diferenciaSencillo;

    private ws.claro.cl.JustificacionDTO[] justificaciones;

    private ws.claro.cl.RemesaDTO[] remesas;

    private long sencilloApertura;

    private long sencilloCierre;

    private long totalMontoJustificado;

    private long totalMontoReversado;

    public ObtenerDetalleCierreResponseDTO() {
    }

    public ObtenerDetalleCierreResponseDTO(
           java.lang.String retCode,
           java.lang.String retDesc,
           long cantidadJustificaciones,
           long cantidadReversas,
           long diferenciaCuadratura,
           long diferenciaSencillo,
           ws.claro.cl.JustificacionDTO[] justificaciones,
           ws.claro.cl.RemesaDTO[] remesas,
           long sencilloApertura,
           long sencilloCierre,
           long totalMontoJustificado,
           long totalMontoReversado) {
        super(
            retCode,
            retDesc);
        this.cantidadJustificaciones = cantidadJustificaciones;
        this.cantidadReversas = cantidadReversas;
        this.diferenciaCuadratura = diferenciaCuadratura;
        this.diferenciaSencillo = diferenciaSencillo;
        this.justificaciones = justificaciones;
        this.remesas = remesas;
        this.sencilloApertura = sencilloApertura;
        this.sencilloCierre = sencilloCierre;
        this.totalMontoJustificado = totalMontoJustificado;
        this.totalMontoReversado = totalMontoReversado;
    }


    /**
     * Gets the cantidadJustificaciones value for this ObtenerDetalleCierreResponseDTO.
     * 
     * @return cantidadJustificaciones
     */
    public long getCantidadJustificaciones() {
        return cantidadJustificaciones;
    }


    /**
     * Sets the cantidadJustificaciones value for this ObtenerDetalleCierreResponseDTO.
     * 
     * @param cantidadJustificaciones
     */
    public void setCantidadJustificaciones(long cantidadJustificaciones) {
        this.cantidadJustificaciones = cantidadJustificaciones;
    }


    /**
     * Gets the cantidadReversas value for this ObtenerDetalleCierreResponseDTO.
     * 
     * @return cantidadReversas
     */
    public long getCantidadReversas() {
        return cantidadReversas;
    }


    /**
     * Sets the cantidadReversas value for this ObtenerDetalleCierreResponseDTO.
     * 
     * @param cantidadReversas
     */
    public void setCantidadReversas(long cantidadReversas) {
        this.cantidadReversas = cantidadReversas;
    }


    /**
     * Gets the diferenciaCuadratura value for this ObtenerDetalleCierreResponseDTO.
     * 
     * @return diferenciaCuadratura
     */
    public long getDiferenciaCuadratura() {
        return diferenciaCuadratura;
    }


    /**
     * Sets the diferenciaCuadratura value for this ObtenerDetalleCierreResponseDTO.
     * 
     * @param diferenciaCuadratura
     */
    public void setDiferenciaCuadratura(long diferenciaCuadratura) {
        this.diferenciaCuadratura = diferenciaCuadratura;
    }


    /**
     * Gets the diferenciaSencillo value for this ObtenerDetalleCierreResponseDTO.
     * 
     * @return diferenciaSencillo
     */
    public long getDiferenciaSencillo() {
        return diferenciaSencillo;
    }


    /**
     * Sets the diferenciaSencillo value for this ObtenerDetalleCierreResponseDTO.
     * 
     * @param diferenciaSencillo
     */
    public void setDiferenciaSencillo(long diferenciaSencillo) {
        this.diferenciaSencillo = diferenciaSencillo;
    }


    /**
     * Gets the justificaciones value for this ObtenerDetalleCierreResponseDTO.
     * 
     * @return justificaciones
     */
    public ws.claro.cl.JustificacionDTO[] getJustificaciones() {
        return justificaciones;
    }


    /**
     * Sets the justificaciones value for this ObtenerDetalleCierreResponseDTO.
     * 
     * @param justificaciones
     */
    public void setJustificaciones(ws.claro.cl.JustificacionDTO[] justificaciones) {
        this.justificaciones = justificaciones;
    }

    public ws.claro.cl.JustificacionDTO getJustificaciones(int i) {
        return this.justificaciones[i];
    }

    public void setJustificaciones(int i, ws.claro.cl.JustificacionDTO _value) {
        this.justificaciones[i] = _value;
    }


    /**
     * Gets the remesas value for this ObtenerDetalleCierreResponseDTO.
     * 
     * @return remesas
     */
    public ws.claro.cl.RemesaDTO[] getRemesas() {
        return remesas;
    }


    /**
     * Sets the remesas value for this ObtenerDetalleCierreResponseDTO.
     * 
     * @param remesas
     */
    public void setRemesas(ws.claro.cl.RemesaDTO[] remesas) {
        this.remesas = remesas;
    }

    public ws.claro.cl.RemesaDTO getRemesas(int i) {
        return this.remesas[i];
    }

    public void setRemesas(int i, ws.claro.cl.RemesaDTO _value) {
        this.remesas[i] = _value;
    }


    /**
     * Gets the sencilloApertura value for this ObtenerDetalleCierreResponseDTO.
     * 
     * @return sencilloApertura
     */
    public long getSencilloApertura() {
        return sencilloApertura;
    }


    /**
     * Sets the sencilloApertura value for this ObtenerDetalleCierreResponseDTO.
     * 
     * @param sencilloApertura
     */
    public void setSencilloApertura(long sencilloApertura) {
        this.sencilloApertura = sencilloApertura;
    }


    /**
     * Gets the sencilloCierre value for this ObtenerDetalleCierreResponseDTO.
     * 
     * @return sencilloCierre
     */
    public long getSencilloCierre() {
        return sencilloCierre;
    }


    /**
     * Sets the sencilloCierre value for this ObtenerDetalleCierreResponseDTO.
     * 
     * @param sencilloCierre
     */
    public void setSencilloCierre(long sencilloCierre) {
        this.sencilloCierre = sencilloCierre;
    }


    /**
     * Gets the totalMontoJustificado value for this ObtenerDetalleCierreResponseDTO.
     * 
     * @return totalMontoJustificado
     */
    public long getTotalMontoJustificado() {
        return totalMontoJustificado;
    }


    /**
     * Sets the totalMontoJustificado value for this ObtenerDetalleCierreResponseDTO.
     * 
     * @param totalMontoJustificado
     */
    public void setTotalMontoJustificado(long totalMontoJustificado) {
        this.totalMontoJustificado = totalMontoJustificado;
    }


    /**
     * Gets the totalMontoReversado value for this ObtenerDetalleCierreResponseDTO.
     * 
     * @return totalMontoReversado
     */
    public long getTotalMontoReversado() {
        return totalMontoReversado;
    }


    /**
     * Sets the totalMontoReversado value for this ObtenerDetalleCierreResponseDTO.
     * 
     * @param totalMontoReversado
     */
    public void setTotalMontoReversado(long totalMontoReversado) {
        this.totalMontoReversado = totalMontoReversado;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof ObtenerDetalleCierreResponseDTO)) return false;
        ObtenerDetalleCierreResponseDTO other = (ObtenerDetalleCierreResponseDTO) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = super.equals(obj) && 
            this.cantidadJustificaciones == other.getCantidadJustificaciones() &&
            this.cantidadReversas == other.getCantidadReversas() &&
            this.diferenciaCuadratura == other.getDiferenciaCuadratura() &&
            this.diferenciaSencillo == other.getDiferenciaSencillo() &&
            ((this.justificaciones==null && other.getJustificaciones()==null) || 
             (this.justificaciones!=null &&
              java.util.Arrays.equals(this.justificaciones, other.getJustificaciones()))) &&
            ((this.remesas==null && other.getRemesas()==null) || 
             (this.remesas!=null &&
              java.util.Arrays.equals(this.remesas, other.getRemesas()))) &&
            this.sencilloApertura == other.getSencilloApertura() &&
            this.sencilloCierre == other.getSencilloCierre() &&
            this.totalMontoJustificado == other.getTotalMontoJustificado() &&
            this.totalMontoReversado == other.getTotalMontoReversado();
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
        _hashCode += new Long(getCantidadJustificaciones()).hashCode();
        _hashCode += new Long(getCantidadReversas()).hashCode();
        _hashCode += new Long(getDiferenciaCuadratura()).hashCode();
        _hashCode += new Long(getDiferenciaSencillo()).hashCode();
        if (getJustificaciones() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getJustificaciones());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getJustificaciones(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getRemesas() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getRemesas());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getRemesas(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        _hashCode += new Long(getSencilloApertura()).hashCode();
        _hashCode += new Long(getSencilloCierre()).hashCode();
        _hashCode += new Long(getTotalMontoJustificado()).hashCode();
        _hashCode += new Long(getTotalMontoReversado()).hashCode();
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(ObtenerDetalleCierreResponseDTO.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://cl.claro.ws/", "obtenerDetalleCierreResponseDTO"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("cantidadJustificaciones");
        elemField.setXmlName(new javax.xml.namespace.QName("", "cantidadJustificaciones"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "long"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("cantidadReversas");
        elemField.setXmlName(new javax.xml.namespace.QName("", "cantidadReversas"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "long"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("diferenciaCuadratura");
        elemField.setXmlName(new javax.xml.namespace.QName("", "diferenciaCuadratura"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "long"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("diferenciaSencillo");
        elemField.setXmlName(new javax.xml.namespace.QName("", "diferenciaSencillo"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "long"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("justificaciones");
        elemField.setXmlName(new javax.xml.namespace.QName("", "justificaciones"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://cl.claro.ws/", "justificacionDTO"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        elemField.setMaxOccursUnbounded(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("remesas");
        elemField.setXmlName(new javax.xml.namespace.QName("", "remesas"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://cl.claro.ws/", "remesaDTO"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        elemField.setMaxOccursUnbounded(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("sencilloApertura");
        elemField.setXmlName(new javax.xml.namespace.QName("", "sencilloApertura"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "long"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("sencilloCierre");
        elemField.setXmlName(new javax.xml.namespace.QName("", "sencilloCierre"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "long"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("totalMontoJustificado");
        elemField.setXmlName(new javax.xml.namespace.QName("", "totalMontoJustificado"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "long"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("totalMontoReversado");
        elemField.setXmlName(new javax.xml.namespace.QName("", "totalMontoReversado"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "long"));
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
