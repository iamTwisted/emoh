package com.owcreativ.info.covid;

public class URLs {

    private static final String ROOT_URL = "http://covid.marginelectrosec.com/api/api.php?apicall=";
    private static final String ROOT_URL_2 = "http://covid.marginelectrosec.com/api/";

    public static final String URL_REGISTER = ROOT_URL + "signup";
    public static final String URL_LOGIN= ROOT_URL + "login";

    public static final String GET_SYMPTOMS = ROOT_URL_2 + "getSymptoms.php";

    public static final String GET_MYTHS = ROOT_URL_2 + "getMyths.php";

    public static final String PRECAUTIONS = ROOT_URL_2 +"precuations.php";
    public static final String SYMPTOMS = ROOT_URL_2 +"symptoms.php";
    public static final String CATEGORIES = ROOT_URL_2 +"getCategories.php";
    public static final String GET_PRECAUTIONS = ROOT_URL_2 +"getPrecautions.php";
    public static final String GET_NEWS = ROOT_URL_2 +"getNews.php";
    public static final String GET_UPDATES = ROOT_URL_2 +"getUpdates.php";
    public static final String NEWS = ROOT_URL_2 +"news.php";
    public static final String GET_FACTS = ROOT_URL_2 +"getFacts.php";
    public static final String GET_KNOWLDGE = ROOT_URL_2 +"knowledge.php";

    public static final String GET_KNOWLDGE_DETAILS = ROOT_URL_2 +"knowledgeDetails.php?id=";
    public static final String GET_PRECAUTIONS_DETAILS = ROOT_URL_2 +"precautionsDetails.php?id=";
    public static final String GET_MYTHS_DETAILS = ROOT_URL_2 +"getMythsDetails.php?id=";

    public static final String ADD_SCORES = ROOT_URL_2 +"addScores.php";
    public static final String GET_TERMS = ROOT_URL_2 +"getTerms.php?title=";
    public static final String GET_QUESTIONS = ROOT_URL_2 +"getQuestions.php?id=";
    public static final String GET_QS = ROOT_URL_2 +"getScreenQuestions.php?status=";
    public static final String GET_ANSWER = ROOT_URL_2 +"getAnswers.php?id=";
}
