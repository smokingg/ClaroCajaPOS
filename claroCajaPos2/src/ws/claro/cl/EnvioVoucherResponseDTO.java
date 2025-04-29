/**
 * EnvioVoucherResponseDTO.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package ws.claro.cl;

public class EnvioVoucherResponseDTO  extends ws.claro.cl.GenericResponseDTO  implements java.io.Serializable {
    private ws.claro.cl.VoucherClienteDTO[] vouchersCliente;

    public EnvioVoucherResponseDTO() {
    }

    public EnvioVoucherResponseDTO(
           java.lang.String retCode,
           java.lang.String retDesc,
           ws.claro.cl.VoucherClienteDTO[] vouchersCliente) {
        super(
            retCode,
            retDesc);
        this.vouchersCliente = vouchersCliente;
    }


    /**
     * Gets the vouchersCliente value for this EnvioVoucherResponseDTO.
     * 
     * @return vouchersCliente
     */
    public ws.claro.cl.VoucherClienteDTO[] getVouchersCliente() {
        return vouchersCliente;
    }


    /**
     * Sets the vouchersCliente value for this EnvioVoucherResponseDTO.
     * 
     * @param vouchersCliente
     */
    public void setVouchersCliente(ws.claro.cl.VoucherClienteDTO[] vouchersCliente) {
        this.vouchersCliente = vouchersCliente;
    }

    public ws.claro.cl.VoucherClienteDTO getVouchersCliente(int i) {
        return this.vouchersCliente[i];
    }

    public void setVouchersCliente(int i, ws.claro.cl.VoucherClienteDTO _value) {
        this.vouchersCliente[i] = _value;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof EnvioVoucherResponseDTO)) return false;
        EnvioVoucherResponseDTO other = (EnvioVoucherResponseDTO) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = super.equals(obj) && 
            ((this.vouchersCliente==null && other.getVouchersCliente()==null) || 
             (this.vouchersCliente!=null &&
              java.util.Arrays.equals(this.vouchersCliente, other.getVouchersCliente())));
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
        if (getVouchersCliente() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getVouchersCliente());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getVouchersCliente(), i);
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
        new org.apache.axis.description.TypeDesc(EnvioVoucherResponseDTO.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://cl.claro.ws/", "envioVoucherResponseDTO"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("vouchersCliente");
        elemField.setXmlName(new javax.xml.namespace.QName("", "vouchersCliente"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://cl.claro.ws/", "voucherClienteDTO"));
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
