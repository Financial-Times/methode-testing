package com.ft.methodetesting;

import com.google.common.base.Charsets;
import com.google.common.io.Resources;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ReferenceLists {

    private static final String exampleArticleXmlTemplate = readFromFile("ArticleWithEverything.xml");
    private static final String exampleAttributesXml = readFromFile("ArticleWithEverythingAttributes.xml");
    private static final String exampleWebChannelXml = readFromFile("ArticleWithEverythingSystemAttributes.xml");
    private static final String expectedTransformedXml = readFromFile("expectedTransformedBody.txt");
    private static final String exampleListXmlTemplate = readFromFile("ArticleWithEverything.xml");

    private static final String simpleArticleXmlTemplate = readFromFile("SimplifiedArticle.xml");
    private static final String simpleTransformedXml = readFromFile("simplifiedTransformedBody.txt");
    private static final String simplifiedArticleWebChannelXml = readFromFile("SimplifiedArticleSystemAttributes.xml");

    public static MethodeContent.Builder publishedKitchenSinkArticle() {
        return MethodeContent.builder(exampleArticleXmlTemplate, exampleAttributesXml, MethodeContent.WEB_READY, exampleWebChannelXml).published();
    }

    public static MethodeContent.Builder unpublishedKitchenSinkArticle() {
        return MethodeContent.builder(exampleArticleXmlTemplate, exampleAttributesXml, MethodeContent.WEB_READY, exampleWebChannelXml).deleted();
    }

    public static String expectedKitchenSinkTransformedXml() {
        return expectedTransformedXml;
    }

    public static MethodeContent.Builder publishedSimpleArticle() {
        return MethodeContent.builder(simpleArticleXmlTemplate, exampleAttributesXml, MethodeContent.WEB_READY, simplifiedArticleWebChannelXml).published();
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

    public static String replaceToken(String xmlBody, String token, String replacementString){
        Pattern pattern = Pattern.compile(token);
        Matcher matcher = pattern.matcher(xmlBody);
        StringBuilder builder = new StringBuilder();
        int i = 0;
        while (matcher.find()) {
            builder.append(xmlBody.substring(i, matcher.start()));
            builder.append(replacementString);
            i = matcher.end();
        }
        builder.append(xmlBody.substring(i, xmlBody.length()));
        return builder.toString();
    }

}

