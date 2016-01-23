package org.stackexchange.api.client;

import org.stackexchange.api.constants.ApiConstants.Questions;
import org.stackexchange.api.constants.StackSite;

public class ApiUris {
    private static final String API_2_1 = "https://api.stackexchange.com/2.1";
    private static final String API_2_2 = "https://api.stackexchange.com/2.2";

    private ApiUris() {
        throw new AssertionError();
    }

    // API

    public static String getQuestionsUri(final int min, final StackSite site) {
        return getQuestionsUri(min, site, 1);
    }

    public static String getQuestionsUri(final int min, final StackSite site, final int page) {
        return getMultipleUri(min, site, Questions.S_QUESTIONS, page);
    }

    public static String getSingleQuestionUri(final StackSite site, final long id) {
        final String operation = Questions.S_QUESTIONS + "/" + id;
        return getSingleUri(site, operation);
    }

    public static String getTagUri(final int min, final StackSite site, final String tag) {
        return getTagUri(min, site, tag, 1);
    }

    public static String getTagUri(final int min, final StackSite site, final String tag, final int page) {
        return getMultipleUri(min, site, Questions.S_TAGS_S + tag + Questions.S_FAQ, page);
    }

    // util
    static String getMultipleUri(final int min, final StackSite site, final String operation, final int page) {
        final String params = new RequestBuilder().add(Questions.order, Questions.DESC).add(Questions.sort, Questions.VOTES).add(Questions.min, min).add(Questions.site, site).add(Questions.page, page).build();
        return getApiString() + operation + params;
    }

    static String getSingleUri(final StackSite site, final String operation) {
        final String params = new RequestBuilder().add(Questions.site, site).build();
        return getApiString() + operation + params;
    }

    private static String getApiString() {
        return API_2_1;
    }

}
