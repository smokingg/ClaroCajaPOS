/**
 * MedioPagoResponseDTO.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package ws.claro.cl;

public class MedioPagoResponseDTO  extends ws.claro.cl.GenericResponseDTO  implements java.io.Serializable {
    private ws.claro.cl.TipoTransaccionDTO[] mediosPagos;

    public MedioPagoResponseDTO() {
    }

    public MedioPagoResponseDTO(
           java.lang.String retCode,
           java.lang.String retDesc,
           ws.claro.cl.TipoTransaccionDTO[] mediosPagos) {
        super(
            retCode,
            retDesc);
        this.mediosPagos = mediosPagos;
    }


    /**
     * Gets the mediosPagos value for this MedioPagoResponseDTO.
     * 
     * @return mediosPagos
     */
    public ws.claro.cl.TipoTransaccionDTO[] getMediosPagos() {
        return mediosPagos;
    }


    /**
     * Sets the mediosPagos value for this MedioPagoResponseDTO.
     * 
     * @param mediosPagos
     */
    public void setMediosPagos(ws.claro.cl.TipoTransaccionDTO[] mediosPagos) {
        this.mediosPagos = mediosPagos;
    }

    public ws.claro.cl.TipoTransaccionDTO getMediosPagos(int i) {
        return this.mediosPagos[i];
    }

    public void setMediosPagos(int i, ws.claro.cl.TipoTransaccionDTO _value) {
        this.mediosPagos[i] = _value;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof MedioPagoResponseDTO)) return false;
        MedioPagoResponseDTO other = (MedioPagoResponseDTO) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = super.equals(obj) && 
            ((this.mediosPagos==null && other.getMediosPagos()==null) || 
             (this.mediosPagos!=null &&
              java.util.Arrays.equals(this.mediosPagos, other.getMediosPagos())));
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
        if (getMediosPagos() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getMediosPagos());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getMediosPagos(), i);
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
        new org.apache.axis.description.TypeDesc(MedioPagoResponseDTO.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://cl.claro.ws/", "medioPagoResponseDTO"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("mediosPagos");
        elemField.setXmlName(new javax.xml.namespace.QName("", "mediosPagos"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://cl.claro.ws/", "tipoTransaccionDTO"));
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
