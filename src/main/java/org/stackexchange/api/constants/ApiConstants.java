package org.stackexchange.api.constants;

public final class ApiConstants {

    private ApiConstants() {
        throw new AssertionError();
    }

    public class Questions {
        public static final String order = "order";
        public static final String sort = "sort";
        public static final String min = "min";
        public static final String max = "max";
        public static final String site = "site";
        public static final String page = "page";
        public static final String DESC = "desc";
        public static final String VOTES = "votes";
        public static final String S_QUESTIONS = "/questions";
        public static final String S_TAGS_S = "/tags/";
        public static final String S_FAQ = "/faq";
    }

}
