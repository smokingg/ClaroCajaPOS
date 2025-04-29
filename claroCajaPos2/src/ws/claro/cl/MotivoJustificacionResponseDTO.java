/**
 * MotivoJustificacionResponseDTO.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package ws.claro.cl;

public class MotivoJustificacionResponseDTO  extends ws.claro.cl.GenericResponseDTO  implements java.io.Serializable {
    private ws.claro.cl.MotivoJustificacionDTO[] motivosJustificacion;

    public MotivoJustificacionResponseDTO() {
    }

    public MotivoJustificacionResponseDTO(
           java.lang.String retCode,
           java.lang.String retDesc,
           ws.claro.cl.MotivoJustificacionDTO[] motivosJustificacion) {
        super(
            retCode,
            retDesc);
        this.motivosJustificacion = motivosJustificacion;
    }


    /**
     * Gets the motivosJustificacion value for this MotivoJustificacionResponseDTO.
     * 
     * @return motivosJustificacion
     */
    public ws.claro.cl.MotivoJustificacionDTO[] getMotivosJustificacion() {
        return motivosJustificacion;
    }


    /**
     * Sets the motivosJustificacion value for this MotivoJustificacionResponseDTO.
     * 
     * @param motivosJustificacion
     */
    public void setMotivosJustificacion(ws.claro.cl.MotivoJustificacionDTO[] motivosJustificacion) {
        this.motivosJustificacion = motivosJustificacion;
    }

    public ws.claro.cl.MotivoJustificacionDTO getMotivosJustificacion(int i) {
        return this.motivosJustificacion[i];
    }

    public void setMotivosJustificacion(int i, ws.claro.cl.MotivoJustificacionDTO _value) {
        this.motivosJustificacion[i] = _value;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof MotivoJustificacionResponseDTO)) return false;
        MotivoJustificacionResponseDTO other = (MotivoJustificacionResponseDTO) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = super.equals(obj) && 
            ((this.motivosJustificacion==null && other.getMotivosJustificacion()==null) || 
             (this.motivosJustificacion!=null &&
              java.util.Arrays.equals(this.motivosJustificacion, other.getMotivosJustificacion())));
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
        if (getMotivosJustificacion() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getMotivosJustificacion());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getMotivosJustificacion(), i);
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
        new org.apache.axis.description.TypeDesc(MotivoJustificacionResponseDTO.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://cl.claro.ws/", "motivoJustificacionResponseDTO"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("motivosJustificacion");
        elemField.setXmlName(new javax.xml.namespace.QName("", "motivosJustificacion"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://cl.claro.ws/", "motivoJustificacionDTO"));
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
