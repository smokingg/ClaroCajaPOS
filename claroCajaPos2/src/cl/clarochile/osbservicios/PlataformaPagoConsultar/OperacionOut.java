/**
 * OperacionOut.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package cl.clarochile.osbservicios.PlataformaPagoConsultar;

public class OperacionOut  implements java.io.Serializable {
    private java.lang.String rutCliente;

    private java.lang.String dvCliente;

    private long totalSaldo;

    private cl.clarochile.osbservicios.PlataformaPagoConsultar.DetalleCuenta[] detalleCuentas;

    private cl.clarochile.osbservicios.PlataformaPagoConsultar.DetalleDocumento[] detalleDocumentos;

    private cl.clarochile.osbservicios.PlataformaPagoConsultar.DetalleProducto[] detalleProductos;

    public OperacionOut() {
    }

    public OperacionOut(
           java.lang.String rutCliente,
           java.lang.String dvCliente,
           long totalSaldo,
           cl.clarochile.osbservicios.PlataformaPagoConsultar.DetalleCuenta[] detalleCuentas,
           cl.clarochile.osbservicios.PlataformaPagoConsultar.DetalleDocumento[] detalleDocumentos,
           cl.clarochile.osbservicios.PlataformaPagoConsultar.DetalleProducto[] detalleProductos) {
           this.rutCliente = rutCliente;
           this.dvCliente = dvCliente;
           this.totalSaldo = totalSaldo;
           this.detalleCuentas = detalleCuentas;
           this.detalleDocumentos = detalleDocumentos;
           this.detalleProductos = detalleProductos;
    }


    /**
     * Gets the rutCliente value for this OperacionOut.
     * 
     * @return rutCliente
     */
    public java.lang.String getRutCliente() {
        return rutCliente;
    }


    /**
     * Sets the rutCliente value for this OperacionOut.
     * 
     * @param rutCliente
     */
    public void setRutCliente(java.lang.String rutCliente) {
        this.rutCliente = rutCliente;
    }


    /**
     * Gets the dvCliente value for this OperacionOut.
     * 
     * @return dvCliente
     */
    public java.lang.String getDvCliente() {
        return dvCliente;
    }


    /**
     * Sets the dvCliente value for this OperacionOut.
     * 
     * @param dvCliente
     */
    public void setDvCliente(java.lang.String dvCliente) {
        this.dvCliente = dvCliente;
    }


    /**
     * Gets the totalSaldo value for this OperacionOut.
     * 
     * @return totalSaldo
     */
    public long getTotalSaldo() {
        return totalSaldo;
    }


    /**
     * Sets the totalSaldo value for this OperacionOut.
     * 
     * @param totalSaldo
     */
    public void setTotalSaldo(long totalSaldo) {
        this.totalSaldo = totalSaldo;
    }


    /**
     * Gets the detalleCuentas value for this OperacionOut.
     * 
     * @return detalleCuentas
     */
    public cl.clarochile.osbservicios.PlataformaPagoConsultar.DetalleCuenta[] getDetalleCuentas() {
        return detalleCuentas;
    }


    /**
     * Sets the detalleCuentas value for this OperacionOut.
     * 
     * @param detalleCuentas
     */
    public void setDetalleCuentas(cl.clarochile.osbservicios.PlataformaPagoConsultar.DetalleCuenta[] detalleCuentas) {
        this.detalleCuentas = detalleCuentas;
    }

    public cl.clarochile.osbservicios.PlataformaPagoConsultar.DetalleCuenta getDetalleCuentas(int i) {
        return this.detalleCuentas[i];
    }

    public void setDetalleCuentas(int i, cl.clarochile.osbservicios.PlataformaPagoConsultar.DetalleCuenta _value) {
        this.detalleCuentas[i] = _value;
    }


    /**
     * Gets the detalleDocumentos value for this OperacionOut.
     * 
     * @return detalleDocumentos
     */
    public cl.clarochile.osbservicios.PlataformaPagoConsultar.DetalleDocumento[] getDetalleDocumentos() {
        return detalleDocumentos;
    }


    /**
     * Sets the detalleDocumentos value for this OperacionOut.
     * 
     * @param detalleDocumentos
     */
    public void setDetalleDocumentos(cl.clarochile.osbservicios.PlataformaPagoConsultar.DetalleDocumento[] detalleDocumentos) {
        this.detalleDocumentos = detalleDocumentos;
    }

    public cl.clarochile.osbservicios.PlataformaPagoConsultar.DetalleDocumento getDetalleDocumentos(int i) {
        return this.detalleDocumentos[i];
    }

    public void setDetalleDocumentos(int i, cl.clarochile.osbservicios.PlataformaPagoConsultar.DetalleDocumento _value) {
        this.detalleDocumentos[i] = _value;
    }


    /**
     * Gets the detalleProductos value for this OperacionOut.
     * 
     * @return detalleProductos
     */
    public cl.clarochile.osbservicios.PlataformaPagoConsultar.DetalleProducto[] getDetalleProductos() {
        return detalleProductos;
    }


    /**
     * Sets the detalleProductos value for this OperacionOut.
     * 
     * @param detalleProductos
     */
    public void setDetalleProductos(cl.clarochile.osbservicios.PlataformaPagoConsultar.DetalleProducto[] detalleProductos) {
        this.detalleProductos = detalleProductos;
    }

    public cl.clarochile.osbservicios.PlataformaPagoConsultar.DetalleProducto getDetalleProductos(int i) {
        return this.detalleProductos[i];
    }

    public void setDetalleProductos(int i, cl.clarochile.osbservicios.PlataformaPagoConsultar.DetalleProducto _value) {
        this.detalleProductos[i] = _value;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof OperacionOut)) return false;
        OperacionOut other = (OperacionOut) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.rutCliente==null && other.getRutCliente()==null) || 
             (this.rutCliente!=null &&
              this.rutCliente.equals(other.getRutCliente()))) &&
            ((this.dvCliente==null && other.getDvCliente()==null) || 
             (this.dvCliente!=null &&
              this.dvCliente.equals(other.getDvCliente()))) &&
            this.totalSaldo == other.getTotalSaldo() &&
            ((this.detalleCuentas==null && other.getDetalleCuentas()==null) || 
             (this.detalleCuentas!=null &&
              java.util.Arrays.equals(this.detalleCuentas, other.getDetalleCuentas()))) &&
            ((this.detalleDocumentos==null && other.getDetalleDocumentos()==null) || 
             (this.detalleDocumentos!=null &&
              java.util.Arrays.equals(this.detalleDocumentos, other.getDetalleDocumentos()))) &&
            ((this.detalleProductos==null && other.getDetalleProductos()==null) || 
             (this.detalleProductos!=null &&
              java.util.Arrays.equals(this.detalleProductos, other.getDetalleProductos())));
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
        if (getRutCliente() != null) {
            _hashCode += getRutCliente().hashCode();
        }
        if (getDvCliente() != null) {
            _hashCode += getDvCliente().hashCode();
        }
        _hashCode += new Long(getTotalSaldo()).hashCode();
        if (getDetalleCuentas() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getDetalleCuentas());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getDetalleCuentas(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getDetalleDocumentos() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getDetalleDocumentos());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getDetalleDocumentos(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getDetalleProductos() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getDetalleProductos());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getDetalleProductos(), i);
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
        new org.apache.axis.description.TypeDesc(OperacionOut.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://osbservicios.clarochile.cl/PlataformaPagoConsultar/", "OperacionOut"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("rutCliente");
        elemField.setXmlName(new javax.xml.namespace.QName("", "rutCliente"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("dvCliente");
        elemField.setXmlName(new javax.xml.namespace.QName("", "dvCliente"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("totalSaldo");
        elemField.setXmlName(new javax.xml.namespace.QName("", "totalSaldo"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "long"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("detalleCuentas");
        elemField.setXmlName(new javax.xml.namespace.QName("", "detalleCuentas"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://osbservicios.clarochile.cl/PlataformaPagoConsultar/", "DetalleCuenta"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        elemField.setMaxOccursUnbounded(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("detalleDocumentos");
        elemField.setXmlName(new javax.xml.namespace.QName("", "detalleDocumentos"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://osbservicios.clarochile.cl/PlataformaPagoConsultar/", "DetalleDocumento"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        elemField.setMaxOccursUnbounded(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("detalleProductos");
        elemField.setXmlName(new javax.xml.namespace.QName("", "detalleProductos"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://osbservicios.clarochile.cl/PlataformaPagoConsultar/", "DetalleProducto"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
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
