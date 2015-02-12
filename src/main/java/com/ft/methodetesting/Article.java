package com.ft.methodetesting;


import com.ft.methodeapi.model.EomFile;
import com.ft.methodetesting.util.FileUtil;

import java.io.IOException;
import java.nio.charset.Charset;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import static java.nio.charset.StandardCharsets.UTF_8;

public class Article {

    private static final String STORY_TYPE = "EOM::CompoundStory";
    private static final String SOURCE_CODE = "FT";
    private static final String CHANEL = "FTcom";
    private static final String DATE_FORMAT = "yyyyMMddHHmmss";
    private static final String STORY_TEMPLATE = "/SysConfig/Templates/FT/Base-Story.xml";
    private static final Charset UTF_8 = Charset.forName("UTF-8");
    public static final String IMAGE_SET_ONTOLOGY = "http://www.ft.com/ontology/content/ImageSet";
    private static final String simpleArticleXmlTemplate = FileUtil.loadFile("article/simple_article_value.xml");
    private static final String articleWithImagesXmlTemplate = FileUtil.loadFile("article/article_value_with_image.xml");
    private static final String articleAttributesXml = FileUtil.loadFile("article/article_attributes.xml");
    private static final String articleSystemAttributesXml = FileUtil.loadFile("article/article_system_attributes.xml");

    public static EomFile buildSimpleArticle(String articleUuid, String markedDeleted, String workflowStatus) throws IOException {

        return new EomFile.Builder()
                .withUuid(articleUuid)
                .withType(STORY_TYPE)
                .withValue(simpleArticleXmlTemplate.getBytes(UTF_8))
                .withAttributes(String.format(articleAttributesXml, methodeFormatDate(), markedDeleted, SOURCE_CODE))
                .withSystemAttributes(String.format(articleSystemAttributesXml, CHANEL, STORY_TEMPLATE))
                .withWorkflowStatus(workflowStatus)
                .build();
    }

    public static EomFile buildArticleWithImage(String articleUuid, String mainImageUuid, String markedDeleted, String workflowStatus) throws IOException {

        return new EomFile.Builder()
                .withUuid(articleUuid)
                .withType(STORY_TYPE)
                .withValue(String.format(articleWithImagesXmlTemplate, mainImageUuid).getBytes(UTF_8))
                .withAttributes(String.format(articleAttributesXml, methodeFormatDate(),markedDeleted,SOURCE_CODE))
                .withSystemAttributes(String.format(articleSystemAttributesXml, CHANEL, STORY_TEMPLATE))
                .withWorkflowStatus(workflowStatus)
                .build();
    }

    private static String methodeFormatDate() {
        Date currentDate = new Date(System.currentTimeMillis());
        Calendar cal = Calendar.getInstance();
        cal.setTime(currentDate);

        DateFormat methodeDateFormat = new SimpleDateFormat(DATE_FORMAT);
        methodeDateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
        return methodeDateFormat.format(cal.getTime());
    }

}