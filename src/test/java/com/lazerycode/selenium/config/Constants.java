package com.lazerycode.selenium.config;

public class Constants {
    public static final String ENV = "next";
    public static final String BASE_URL = "https://go-v3b-" + ENV + ".happygrasshopper.com";
    //public static final String BASE_URL = "https://go.happygrasshopper.com";
    public static final String LOGIN_URL = BASE_URL + "/users/login";
    public static final String NEW_USER = "happygrasshopperauto+" + System.currentTimeMillis() + "_"+ ENV + "@gmail.com";

    public static final String CC = "4111 1111 1111 1111";
    public static final String EXP_MONTH = "12";
    public static final String EXP_YEAR = "2019";
    public static final String CVV = "100";

    public static final String ADMIN_USER = "madhuri@happygrasshopper.com";
    public static final String ADMIN_PASS = "Grass123";

    // production setup
    public static final String PROD_URL = "https://go.happygrasshopper.com/users/login";
    public static final String PROD_ADMIN_USER = "madhuri@happygrasshopper.com";
    public static final String PROD_ADMIN_PASS = "Mother123@";
}