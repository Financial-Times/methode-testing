package com.ft.methodetesting;

import com.google.common.base.Charsets;
import com.google.common.io.Resources;

import java.io.IOException;

/**
 * ReferenceArticles
 *
 * @author Simon.Gibbs
 */
public class ReferenceArticles {

    private static final String exampleArticleXmlTemplate = readFromFile("ArticleWithEverything.xml");
    private static final String exampleAttributesXml = readFromFile("ArticleWithEverythingAttributes.xml");
    private static final String expectedTransformedXml = readFromFile("expectedTransformedBody.txt");

    private static final String simpleArticleXmlTemplate = readFromFile("SimplifiedArticle.xml");
    private static final String simpleTransformedXml = readFromFile("simplifiedTransformedBody.txt");

    public static MethodeArticle.Builder publishedKitchenSinkArticle() {
        return MethodeArticle.builder(exampleArticleXmlTemplate, exampleAttributesXml).published();
    }

    public static MethodeArticle.Builder unpublishedKitchenSinkArticle() {
        return MethodeArticle.builder(exampleArticleXmlTemplate,exampleAttributesXml).deleted();
    }

    public static String expectedKitchenSinkTransformedXml() {
        return expectedTransformedXml;
    }

    public static MethodeArticle.Builder publishedSimpleArticle() {
        return MethodeArticle.builder(simpleArticleXmlTemplate,exampleAttributesXml).published();
    }

    public static String simplifiedTransformedXml() {
        return simpleTransformedXml;
    }


    private static String readFromFile(String resourceName) {
        String bodyFromFile = "";
        try {
            bodyFromFile = Resources.toString(ReferenceArticles.class.getResource(resourceName), Charsets.UTF_8);

            // because what we get back from the API uses UNIX line encodings, but when working locally on Windows, the expected file will have \r\n
            if (System.getProperty("line.separator").equals("\r\n")) {
                bodyFromFile = bodyFromFile.replace("\r", "");
            }
        } catch (IOException e) {
            throw new RuntimeException("Unexpected error reading from content in JAR file",e);
        }
        return bodyFromFile;
    }

}
