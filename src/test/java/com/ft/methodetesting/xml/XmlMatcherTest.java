package com.ft.methodetesting.xml;

import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.assertThat;

import org.junit.Test;

/**
 * Plug from XmlMatchers to establish what XmlMatcher supports
 * https://code.google.com/p/xml-matchers/source/browse/trunk/xml-matchers/src/test/java/org/xmlmatchers/equivalence/StringIsEquivalentToTest.java
 */
public class XmlMatcherTest {

    @Test
    public void stringReflexiveEquivalence() {
        String xml = "<mountains><mountain>K2</mountain></mountains>";
        assertThat(xml, XmlMatcher.identicalXmlTo(xml));
    }

    @Test
    public void elementTextDiffers() {
        String k2Xml = "<mountains><mountain>K2</mountain></mountains>";
        String everestXml = "<mountains><mountain>Everest</mountain></mountains>";
        assertThat(k2Xml, not(XmlMatcher.identicalXmlTo(everestXml)));
    }

    @Test
    public void emptyNodes() {
        String xml = "<mountains></mountains>";
        String singleElement = "<mountains />";
        assertThat(xml, XmlMatcher.identicalXmlTo(singleElement));
    }

    @Test
    public void attributesInAnyOrder() {
        String xml = "<mountains><mountain height=\"28,251\" firstClimbed=\"1954\">K2</mountain></mountains>";
        String xmlWithUnorderedAttributes = "<mountains><mountain firstClimbed=\"1954\" height=\"28,251\">K2</mountain></mountains>";
        assertThat(xml, XmlMatcher.identicalXmlTo(xmlWithUnorderedAttributes));
    }

    @Test
    public void textAndCdataAreIdentical() {
        String xml = "<mountains><mountain>K2</mountain></mountains>";
        String xmlWithCdata = "<mountains><mountain><![CDATA[K2]]></mountain></mountains>";
        assertThat(xml, not(XmlMatcher.identicalXmlTo(xmlWithCdata)));
    }
}
