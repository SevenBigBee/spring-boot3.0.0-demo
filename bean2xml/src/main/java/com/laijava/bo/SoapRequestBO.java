package com.laijava.bo;

import jakarta.xml.bind.annotation.*;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = { "header", "body" })
@XmlRootElement(name = "soapenv:Envelope")
public class SoapRequestBO {
    @XmlAttribute(name = "xmlns:soapenv")
    protected String soapenv = "http://schemas.xmlsoap.org/soap/envelope/";


    @XmlElement(required = true, name = "soapenv:Header")
    protected String header;

    @XmlElement(required = true, name = "soapenv:Body")
    protected SoapRequestBodyBO body;

    /**
     * @return the soapenv
     */
    public String getSoapenv() {
        return soapenv;
    }

    /**
     * @param soapenv
     *            the soapenv to set
     */
    public void setSoapenv(String soapenv) {
        this.soapenv = soapenv;
    }


    public SoapRequestBodyBO getBody() {
        return body;
    }

    public void setBody(SoapRequestBodyBO body) {
        this.body = body;
    }

    /**
     * @return the header
     */
    public String getHeader() {
        return header;
    }

    /**
     * @param header
     *            the header to set
     */
    public void setHeader(String header) {
        this.header = header;
    }

}



