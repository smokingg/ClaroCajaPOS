/**
 * CierreDiarioOut.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package cl.hyh.cajas.ws.impl;

public class CierreDiarioOut  extends cl.hyh.cajas.ws.impl.Response  implements java.io.Serializable {
    private cl.hyh.cajas.ws.impl.ArqueoConcepto[] conceptos;

    private java.lang.String fecha;

    private long folioSMF;

    private long folioTango;

    private cl.hyh.cajas.ws.impl.ItemArqueo[] itemsArqueo;

    private java.lang.String nombreUsuario;

    private int sesion;

    private java.lang.String usuarioSMF;

    private java.lang.String usuarioTango;

    public CierreDiarioOut() {
    }

    public CierreDiarioOut(
           cl.hyh.cajas.ws.impl.HeaderOut headerOut,
           cl.hyh.cajas.ws.impl.ArqueoConcepto[] conceptos,
           java.lang.String fecha,
           long folioSMF,
           long folioTango,
           cl.hyh.cajas.ws.impl.ItemArqueo[] itemsArqueo,
           java.lang.String nombreUsuario,
           int sesion,
           java.lang.String usuarioSMF,
           java.lang.String usuarioTango) {
        super(
            headerOut);
        this.conceptos = conceptos;
        this.fecha = fecha;
        this.folioSMF = folioSMF;
        this.folioTango = folioTango;
        this.itemsArqueo = itemsArqueo;
        this.nombreUsuario = nombreUsuario;
        this.sesion = sesion;
        this.usuarioSMF = usuarioSMF;
        this.usuarioTango = usuarioTango;
    }


    /**
     * Gets the conceptos value for this CierreDiarioOut.
     * 
     * @return conceptos
     */
    public cl.hyh.cajas.ws.impl.ArqueoConcepto[] getConceptos() {
        return conceptos;
    }


    /**
     * Sets the conceptos value for this CierreDiarioOut.
     * 
     * @param conceptos
     */
    public void setConceptos(cl.hyh.cajas.ws.impl.ArqueoConcepto[] conceptos) {
        this.conceptos = conceptos;
    }

    public cl.hyh.cajas.ws.impl.ArqueoConcepto getConceptos(int i) {
        return this.conceptos[i];
    }

    public void setConceptos(int i, cl.hyh.cajas.ws.impl.ArqueoConcepto _value) {
        this.conceptos[i] = _value;
    }


    /**
     * Gets the fecha value for this CierreDiarioOut.
     * 
     * @return fecha
     */
    public java.lang.String getFecha() {
        return fecha;
    }


    /**
     * Sets the fecha value for this CierreDiarioOut.
     * 
     * @param fecha
     */
    public void setFecha(java.lang.String fecha) {
        this.fecha = fecha;
    }


    /**
     * Gets the folioSMF value for this CierreDiarioOut.
     * 
     * @return folioSMF
     */
    public long getFolioSMF() {
        return folioSMF;
    }


    /**
     * Sets the folioSMF value for this CierreDiarioOut.
     * 
     * @param folioSMF
     */
    public void setFolioSMF(long folioSMF) {
        this.folioSMF = folioSMF;
    }


    /**
     * Gets the folioTango value for this CierreDiarioOut.
     * 
     * @return folioTango
     */
    public long getFolioTango() {
        return folioTango;
    }


    /**
     * Sets the folioTango value for this CierreDiarioOut.
     * 
     * @param folioTango
     */
    public void setFolioTango(long folioTango) {
        this.folioTango = folioTango;
    }


    /**
     * Gets the itemsArqueo value for this CierreDiarioOut.
     * 
     * @return itemsArqueo
     */
    public cl.hyh.cajas.ws.impl.ItemArqueo[] getItemsArqueo() {
        return itemsArqueo;
    }


    /**
     * Sets the itemsArqueo value for this CierreDiarioOut.
     * 
     * @param itemsArqueo
     */
    public void setItemsArqueo(cl.hyh.cajas.ws.impl.ItemArqueo[] itemsArqueo) {
        this.itemsArqueo = itemsArqueo;
    }

    public cl.hyh.cajas.ws.impl.ItemArqueo getItemsArqueo(int i) {
        return this.itemsArqueo[i];
    }

    public void setItemsArqueo(int i, cl.hyh.cajas.ws.impl.ItemArqueo _value) {
        this.itemsArqueo[i] = _value;
    }


    /**
     * Gets the nombreUsuario value for this CierreDiarioOut.
     * 
     * @return nombreUsuario
     */
    public java.lang.String getNombreUsuario() {
        return nombreUsuario;
    }


    /**
     * Sets the nombreUsuario value for this CierreDiarioOut.
     * 
     * @param nombreUsuario
     */
    public void setNombreUsuario(java.lang.String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }


    /**
     * Gets the sesion value for this CierreDiarioOut.
     * 
     * @return sesion
     */
    public int getSesion() {
        return sesion;
    }


    /**
     * Sets the sesion value for this CierreDiarioOut.
     * 
     * @param sesion
     */
    public void setSesion(int sesion) {
        this.sesion = sesion;
    }


    /**
     * Gets the usuarioSMF value for this CierreDiarioOut.
     * 
     * @return usuarioSMF
     */
    public java.lang.String getUsuarioSMF() {
        return usuarioSMF;
    }


    /**
     * Sets the usuarioSMF value for this CierreDiarioOut.
     * 
     * @param usuarioSMF
     */
    public void setUsuarioSMF(java.lang.String usuarioSMF) {
        this.usuarioSMF = usuarioSMF;
    }


    /**
     * Gets the usuarioTango value for this CierreDiarioOut.
     * 
     * @return usuarioTango
     */
    public java.lang.String getUsuarioTango() {
        return usuarioTango;
    }


    /**
     * Sets the usuarioTango value for this CierreDiarioOut.
     * 
     * @param usuarioTango
     */
    public void setUsuarioTango(java.lang.String usuarioTango) {
        this.usuarioTango = usuarioTango;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof CierreDiarioOut)) return false;
        CierreDiarioOut other = (CierreDiarioOut) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = super.equals(obj) && 
            ((this.conceptos==null && other.getConceptos()==null) || 
             (this.conceptos!=null &&
              java.util.Arrays.equals(this.conceptos, other.getConceptos()))) &&
            ((this.fecha==null && other.getFecha()==null) || 
             (this.fecha!=null &&
              this.fecha.equals(other.getFecha()))) &&
            this.folioSMF == other.getFolioSMF() &&
            this.folioTango == other.getFolioTango() &&
            ((this.itemsArqueo==null && other.getItemsArqueo()==null) || 
             (this.itemsArqueo!=null &&
              java.util.Arrays.equals(this.itemsArqueo, other.getItemsArqueo()))) &&
            ((this.nombreUsuario==null && other.getNombreUsuario()==null) || 
             (this.nombreUsuario!=null &&
              this.nombreUsuario.equals(other.getNombreUsuario()))) &&
            this.sesion == other.getSesion() &&
            ((this.usuarioSMF==null && other.getUsuarioSMF()==null) || 
             (this.usuarioSMF!=null &&
              this.usuarioSMF.equals(other.getUsuarioSMF()))) &&
            ((this.usuarioTango==null && other.getUsuarioTango()==null) || 
             (this.usuarioTango!=null &&
              this.usuarioTango.equals(other.getUsuarioTango())));
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
        if (getConceptos() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getConceptos());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getConceptos(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getFecha() != null) {
            _hashCode += getFecha().hashCode();
        }
        _hashCode += new Long(getFolioSMF()).hashCode();
        _hashCode += new Long(getFolioTango()).hashCode();
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
        if (getNombreUsuario() != null) {
            _hashCode += getNombreUsuario().hashCode();
        }
        _hashCode += getSesion();
        if (getUsuarioSMF() != null) {
            _hashCode += getUsuarioSMF().hashCode();
        }
        if (getUsuarioTango() != null) {
            _hashCode += getUsuarioTango().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(CierreDiarioOut.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://impl.ws.cajas.hyh.cl/", "cierreDiarioOut"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("conceptos");
        elemField.setXmlName(new javax.xml.namespace.QName("", "conceptos"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://impl.ws.cajas.hyh.cl/", "arqueoConcepto"));
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
        elemField.setFieldName("folioSMF");
        elemField.setXmlName(new javax.xml.namespace.QName("", "folioSMF"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "long"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("folioTango");
        elemField.setXmlName(new javax.xml.namespace.QName("", "folioTango"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "long"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("itemsArqueo");
        elemField.setXmlName(new javax.xml.namespace.QName("", "itemsArqueo"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://impl.ws.cajas.hyh.cl/", "itemArqueo"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        elemField.setMaxOccursUnbounded(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("nombreUsuario");
        elemField.setXmlName(new javax.xml.namespace.QName("", "nombreUsuario"));
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
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("usuarioSMF");
        elemField.setXmlName(new javax.xml.namespace.QName("", "usuarioSMF"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("usuarioTango");
        elemField.setXmlName(new javax.xml.namespace.QName("", "usuarioTango"));
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
