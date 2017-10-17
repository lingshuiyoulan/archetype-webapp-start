package com.util;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Node;

import java.util.ArrayList;
import java.util.List;

/**
 * @author chencaihua
 * @description
 * @date 2017-09-28 14:43
 * @modified By
 */
public class XmlUtil {

    private static XmlUtil xmlUtil = new XmlUtil();

    private Document document;

    private XmlUtil() {
    }

    /**
     * 字符串转xml对象
     * @param xmlStr
     * @return
     */
    public static XmlUtil getXmlUtil(String xmlStr) {
        xmlUtil.setDocument(xmlStr);
        return xmlUtil;
    }

    /**
     * 单例模式，只能设置一次值
     * @param xmlStr
     */
    private void setDocument(String xmlStr) {
        try {
            this.document = DocumentHelper.parseText(xmlStr);
        } catch (DocumentException e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取指定节点的值
     *
     * @param xpath xpath
     * @return
     */
    public String getNodeValue(String xpath) {
        if (document != null) {
            Node node = document.selectSingleNode(xpath);
            if (node != null) {
                return node.getStringValue();
            }
        }
        return null;
    }

    /**
     * 获取指定节点的集合
     *
     * @param xpath xpath
     * @return
     */
    public List<String> getNodeValueList(String xpath) {
        List<String> result = null;
        if (document != null) {
            List nodes = document.selectNodes(xpath);
            if (nodes.size() > 0) {
                result = new ArrayList<>();
                for (Object node : nodes) {
                    if (node != null) {
                        result.add(((Node)node).getStringValue());
                    }
                }
                return result;
            }
        }
        return null;
    }

    /**
     * 测试工具类
     * @param args
     * @throws DocumentException
     */
    public static void main(String[] args) throws DocumentException {
        String dataStr = "<?xml version=\"1.0\" encoding=\"utf-8\" ?>" +
                "<Order>" +
                "<Status>0000</Status>" +
                "<OrderStatus>" +
                "<Code price=\"1\">111</Code>" +
                "<Code>111</Code>" +
                "</OrderStatus>" +
                "<XYOrderNo price=\"2\" id=\"2\">123456</XYOrderNo>" +
                "<OrderNo>123456</OrderNo>" +
                "<msg>充值中</msg>" +
                "</Order>";
        XmlUtil xmlUtil = XmlUtil.getXmlUtil(dataStr);
        System.out.println(xmlUtil.getNodeValue("/Order/Status"));
        System.out.println(xmlUtil.getNodeValue("/Order/OrderStatus"));
        System.out.println(xmlUtil.getNodeValue("/Order/OrderStatus/Code"));
        System.out.println(xmlUtil.getNodeValue("/Order/XYOrderNo"));
        System.out.println(xmlUtil.getNodeValue("/Order/OrderNo"));
        System.out.println(xmlUtil.getNodeValue("/Order/msg"));
//        System.out.println(xmlUtil.getNodeValue("/Order/XYOrderNo@id"));
        System.out.println(xmlUtil.getNodeValue("/Order/XYOrderNo//@price"));
    }
}
