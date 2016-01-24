package org.stackexchange.api.constants;

public final class ApiConstants {

    private ApiConstants() {
        throw new AssertionError();
    }

    public class Questions {
        public static final String ORDER = "ORDER";
        public static final String SORT = "SORT";
        public static final String MIN = "MIN";
        public static final String MAX = "MAX";
        public static final String SITE = "SITE";
        public static final String PAGE = "PAGE";
        public static final String DESC = "desc";
        public static final String VOTES = "votes";
        public static final String S_QUESTIONS = "/questions";
        public static final String QUESTIONS = "questions";
        public static final String S_TAGS_S = "/tags/";
        public static final String TAGS = "tags";
        public static final String S_FAQ = "/faq";
        public static final String FAQ = "faq";
    }

}
