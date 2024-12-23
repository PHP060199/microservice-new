package com.example.orderservice.exception.define;

public class ErrorMessage {
    public static String USER_NOT_FOUND = "User not found.";
    public static String USERNAME_ALREADY_EXISTS = "Username already exists.";
    public static String PRODUCT_NOT_FOUND = "Product not found.";
    public static String PRODUCT_ALREADY_EXISTS = "Product name already exists.";
    public static String USERNAME_INVALID = "Username must be at least 3 characters.";
    public static String PASSWORD_INVALID = "Password must be at least 8 characters.";
    public static String UNAUTHENTICATED = "Unauthenticated.";
    public static String PERMISSION_NOT_FOUND = "Permission not found.";
    public static String PERMISSION_ALREADY_EXISTS = "Permission already exists.";
    public static String ROLE_NOT_FOUND = "Role not found.";
    public static String ROLE_ALREADY_EXISTS = "Role already exists.";
    public static String TOKEN_INVALID = "Token invalid.";
    public static String INVENTORY_NOT_FOUND = "Inventory not found.";
    public static String CODE_ALREADY_EXISTS = "Code already exists.";
    public static String ORDER_NOT_FOUND = "Order not found.";
    public static String PRODUCT_NOT_FOUND_IN_STOCK = "Product with code: %s is not found in stock.";
    public static String PRODUCT_NOT_ENOUGH_QUANTITY = "Product with code: %s is not enough quantity left.";
    public static String CANNOT_SEND_EMAIL = "Cannot send email.";
}
