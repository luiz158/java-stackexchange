package org.stackexchange.api.client;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.http.HttpHeaders;
import org.apache.http.HttpResponse;
import org.apache.http.impl.client.DecompressingHttpClient;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.stackexchange.api.constants.StackSite;

import java.io.IOException;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

public class QuestionsApiLiveTest {
    private final Logger logger = LoggerFactory.getLogger(getClass());
    private QuestionsApi questionsApi;

    // fixtures

    @Before
    public final void before() {
        questionsApi = new QuestionsApi(new DecompressingHttpClient(HttpFactory.httpClient(true)));
    }

    // tests

    // serverfault

    @Test
    public final void whenRequestIsPerformed_thenNoExceptions() throws IOException {
        questionsApi.questions(70, StackSite.ServerFault);
    }

    @Test
    public final void whenRequestIsPerformed_thenSuccess() throws IOException {
        final HttpResponse httpResponse = questionsApi.questionsAsResponse(70, StackSite.ServerFault);
        assertThat(httpResponse.getStatusLine().getStatusCode(), equalTo(200));
    }

    @Test
    public final void whenRequestIsPerformed_thenOutputIsJson() throws IOException {
        final HttpResponse httpResponse = questionsApi.questionsAsResponse(70, StackSite.ServerFault);
        final String contentType = httpResponse.getHeaders(HttpHeaders.CONTENT_TYPE)[0].getValue();
        assertThat(contentType, containsString("application/json"));
    }

    @Test
    public final void whenRequestIsPerformed_thenOutputIsCorrect() throws IOException {
        final String responseBody = questionsApi.questions(70, StackSite.ServerFault);
        logger.debug(responseBody);
        assertThat(responseBody, notNullValue());
    }

    @Test
    public final void whenParsingOutputFromQuestionsApi_thenOutputContainsSomeQuestions() throws IOException {
        final String questionsAsJson = questionsApi.questions(70, StackSite.ServerFault);
        final ObjectMapper mapper = new ObjectMapper();
        final JsonNode rootNode = mapper.readTree(questionsAsJson);
        final ArrayNode questionsArray = (ArrayNode) rootNode.get("items");
        logger.debug(questionsArray.toString());
        assertThat(questionsArray.size(), greaterThan(20));
    }

    // askubuntu

    @Test
    public final void givenOnAskUbuntu_whenInitialRequestIsPerformed_thenNoExceptions() throws IOException {
        questionsApi.questions(100, StackSite.AskUbuntu);
    }

    @Test
    public final void givenOnAskUbuntu_whenParsingOutputFromQuestionsApi_thenOutputIsParsable() throws IOException {
        final String questionsAsJson = questionsApi.questions(70, StackSite.AskUbuntu);
        final ObjectMapper mapper = new ObjectMapper();
        final JsonNode rootNode = mapper.readTree(questionsAsJson);
        final ArrayNode questionsArray = (ArrayNode) rootNode.get("items");
        assertThat(questionsArray.size(), greaterThan(20));
    }

    // character encoding

    @Test
    public final void givenOutputFromQuestionsApi_whenCharacterEncodingIsAnalyzed_thenOutputIsParsable() throws IOException {
        final String questionsAsJson = questionsApi.questions(70, StackSite.AskUbuntu);
        final ObjectMapper mapper = new ObjectMapper();
        final JsonNode rootNode = mapper.readTree(questionsAsJson);
        final ArrayNode questionsArray = (ArrayNode) rootNode.get("items");
        for (final JsonNode question : questionsArray) {
            final String title = question.get(QuestionsApi.TITLE).toString();
            final String fullTweet = title.substring(1, title.length() - 1);
            StringEscapeUtils.escapeJava(fullTweet);
            System.out.println(fullTweet);
        }
    }

    // stackoverflow tag

    @Test
    public final void givenOnSO_whenRequestOnTagIsPerformed_thenNoExceptions() throws IOException {
        questionsApi.questions(100, StackSite.AskUbuntu);
    }

    // single question by id

    @Test
    public final void whenRetrievingSingleQuestionById_thenNoExceptions() {
        final String questionById = questionsApi.questionById(StackSite.StackOverflow, 16621738);
        assertNotNull(questionById);
    }
}
