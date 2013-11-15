package com.ft.methodtesting.xml;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.io.StringReader;

/**
 * Xml testing util
 *
 * @author Simon.Gibbs
 */
public class Xml {

    public static String removeInsignificantXml(String xmlSource, String[] insignificantPaths) throws Exception {

        Document xmlDom = parseAndSimplify(xmlSource);

        stripPaths(xmlDom, insignificantPaths);

        ByteArrayOutputStream out = new ByteArrayOutputStream(xmlSource.length());

        emitDocumentWithoutWhitespace(xmlDom, out);

        return new String(out.toByteArray(),"UTF-8");

    }

    public static void stripPaths(Document xmlDom, String[] insignificantPaths) throws XPathExpressionException {
        XPath pathUtil = XPathFactory.newInstance().newXPath();

        for(String xpath : insignificantPaths) {
            NodeList nodes = (NodeList) pathUtil.evaluate(xpath, xmlDom, XPathConstants.NODESET );

            for (int i = 0; i < nodes.getLength(); ++i) {

                Node node = nodes.item(i);
                node.getParentNode().removeChild(node);

            }
        }
    }

    public static void emitDocumentWithoutWhitespace(Node document, OutputStream oFileOut)
            throws TransformerException {

        Source xsltSource = new StreamSource( Xml.class.getResourceAsStream("strip-space.xslt") );

        TransformerFactory oTransFactory = TransformerFactory.newInstance();
        Transformer oTrans = oTransFactory.newTransformer(xsltSource);

        DOMSource oSource = new DOMSource(document);

        StreamResult oResult = new StreamResult(oFileOut);

        oTrans.transform(oSource,oResult);

    }

    public static Document parseAndSimplify(String xmlSource) throws Exception {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setCoalescing(true);
        factory.setIgnoringComments(true);
        factory.setExpandEntityReferences(false);
        factory.setValidating(false);
        factory.setFeature("http://apache.org/xml/features/nonvalidating/load-external-dtd", false);
        factory.setIgnoringElementContentWhitespace(true);

        return factory.newDocumentBuilder().parse( new InputSource( new StringReader(xmlSource)  ));
    }

}
