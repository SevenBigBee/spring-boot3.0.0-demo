package com.laijava;


import java.io.StringReader;
import java.io.StringWriter;

import javax.xml.parsers.SAXParserFactory;
import javax.xml.transform.Source;
import javax.xml.transform.sax.SAXSource;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.Marshaller;
import jakarta.xml.bind.Unmarshaller;
import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;

/**
 * @author
 * @date 2023年6月20日 下午5:03:00
 * @Description Jaxb工具类 xml和java类相互转换
 */
public class JaxbXmlUtil {
    public static final String DEFAULT_ENCODING = "UTF-8";

    /**
     * pojo转换成xml 默认编码UTF-8
     *
     * @param obj
     *            待转化的对象
     * @return xml格式字符串
     * @throws Exception
     *             JAXBException
     */
    public static String convertToXml(Object obj) throws Exception {
        return convertToXml(obj, DEFAULT_ENCODING);
    }

    /**
     * pojo转换成xml
     *
     * @param obj
     *            待转化的对象
     * @param encoding
     *            编码
     * @return xml格式字符串
     * @throws Exception
     *             JAXBException
     */
    public static String convertToXml(Object obj, String encoding) throws Exception {
        String result = null;

        JAXBContext context = JAXBContext.newInstance(obj.getClass());
        Marshaller marshaller = context.createMarshaller();
        // 指定是否使用换行和缩排对已编组 XML 数据进行格式化的属性名称。
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, false);
        marshaller.setProperty(Marshaller.JAXB_ENCODING, encoding);
        marshaller.setListener(new MarshallerListener());
        StringWriter writer = new StringWriter();
        marshaller.marshal(obj, writer);
        result = writer.toString();

        return result;
    }

    /**
     * xml转换成JavaBean
     *
     * @param xml xml格式字符串
     * @param t 待转化的对象
     * @return 转化后的对象
     * @throws Exception JAXBException
     */
    @SuppressWarnings("unchecked")
    public static <T> T convertToJavaBean(String xml, Class<T> t) throws Exception {

        JAXBContext jaxbContext = JAXBContext.newInstance(t);
        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
        StringReader reader = new StringReader(xml);

        SAXParserFactory sax = SAXParserFactory.newInstance();
        sax.setNamespaceAware(false);
        XMLReader xmlReader = sax.newSAXParser().getXMLReader();

        Source source = new SAXSource(xmlReader, new InputSource(reader));
        Object o = unmarshaller.unmarshal(source);
        return (T) o;
    }

}

