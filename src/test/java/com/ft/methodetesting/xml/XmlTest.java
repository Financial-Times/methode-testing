package com.ft.methodetesting.xml;

import org.junit.Test;

/**
 * XmlTest
 *
 * @author Simon.Gibbs
 */
public class XmlTest {

    @Test
    public void  shouldBeAbleToIdentifyGoodXml() throws Exception {
        String goodXml = "<xml />";
        Xml.assertParseable(goodXml);

    }

    @Test(expected = java.lang.AssertionError.class)
    public void  shouldBeAbleToIdentifyBadXml() throws Exception {
        String badXml = "adrgbjlafbgj,sfbgjldf";
        Xml.assertParseable(badXml);

    }

}
