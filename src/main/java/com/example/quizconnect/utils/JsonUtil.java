package com.example.quizconnect.utils;

import com.google.gson.*;
import com.example.quizconnect.network.ProtocolConstants;

/**
 * Central utility for all JSON operations.
 *
 * Two responsibilities:
 *   1. Convert objects to/from JSON  (toJson / fromJson)
 *   2. Build protocol messages       (buildRequest / buildResponse)
 */
public class JsonUtil {

    // One Gson instance shared by everyone
    // Gson is thread-safe, so this is fine
    private static final Gson GSON = new GsonBuilder()
            .setPrettyPrinting()   // makes JSON readable for debugging
            .create();

    private JsonUtil() {} // Prevent instantiation

    // ════════════════════════════════════════
    //  BASIC CONVERSION
    // ════════════════════════════════════════

    /**
     * Convert ANY Java object to a JSON string.
     *
     * Example:
     *   User user = new User("john", "pass", "john@x.com", "STUDENT");
     *   String json = JsonUtil.toJson(user);
     *   // json is now: {"username":"john","role":"STUDENT",...}
     */
    public static String toJson(Object object) {
        return GSON.toJson(object);
    }

    /**
     * Convert a JSON string back into a Java object.
     *
     * Example:
     *   String json = "{\"username\":\"john\",\"role\":\"STUDENT\"}";
     *   User user = JsonUtil.fromJson(json, User.class);
     *   // user.getUsername() → "john"
     */
    public static <T> T fromJson(String json, Class<T> classType) {
        return GSON.fromJson(json, classType);
    }

    /**
     * Parse a JSON string into a JsonObject
     * so you can read individual fields.
     *
     * Example:
     *   JsonObject obj = JsonUtil.parse(json);
     *   String type = obj.get("type").getAsString();
     */
    public static JsonObject parse(String json) {
        return JsonParser.parseString(json).getAsJsonObject();
    }

    /**
     * Get the "data" block from a protocol message as a JsonObject.
     * Useful on the server when reading what the client sent.
     *
     * Example:
     *   JsonObject data = JsonUtil.getData(message);
     *   String username = data.get("username").getAsString();
     */
    public static JsonObject getData(JsonObject message) {
        return message.getAsJsonObject("data");
    }

    /**
     * Get the "data" block and convert it straight to a Java object.
     *
     * Example:
     *   User user = JsonUtil.getData(message, User.class);
     */
    public static <T> T getData(JsonObject message, Class<T> classType) {
        JsonObject data = message.getAsJsonObject("data");
        return GSON.fromJson(data, classType);
    }

    // ════════════════════════════════════════
    //  BUILD REQUEST MESSAGES  (Client → Server)
    // ════════════════════════════════════════

    /**
     * Every request looks like:
     * {
     *   "type": "LOGIN",
     *   "data": { ...whatever data... }
     * }
     */
    public static String buildRequest(String type, Object data) {
        JsonObject message = new JsonObject();
        message.addProperty("type", type);
        message.add("data", GSON.toJsonTree(data));
        return message.toString(); // compact, one line for sending
    }

    // ── Specific request builders ──
    // These are convenience methods so controllers
    // don't have to remember the field names

    public static String buildLoginRequest(String username,
                                           String password,
                                           String role) {
        JsonObject data = new JsonObject();
        data.addProperty("username", username);
        data.addProperty("password", password);
        data.addProperty("role",     role);
        return buildRequest(ProtocolConstants.LOGIN, data);
    }

    public static String buildRegisterRequest(String username,
                                              String email,
                                              String password,
                                              String role) {
        JsonObject data = new JsonObject();
        data.addProperty("username", username);
        data.addProperty("email",    email);
        data.addProperty("password", password);
        data.addProperty("role",     role);
        return buildRequest(ProtocolConstants.REGISTER, data);
    }

    public static String buildFetchQuizzesRequest() {
        return buildRequest(ProtocolConstants.FETCH_QUIZZES,
                new JsonObject());
    }

    public static String buildFetchQuestionsRequest(int quizId) {
        JsonObject data = new JsonObject();
        data.addProperty("quizId", quizId);
        return buildRequest(ProtocolConstants.FETCH_QUESTIONS, data);
    }

    // ════════════════════════════════════════
    //  BUILD RESPONSE MESSAGES  (Server → Client)
    // ════════════════════════════════════════

    /**
     * Every response looks like:
     * {
     *   "type":    "LOGIN",
     *   "status":  "SUCCESS",
     *   "message": "Welcome back!",
     *   "data":    { ...result data... }
     * }
     */
    public static String buildResponse(String type,
                                       String status,
                                       String message,
                                       Object data) {
        JsonObject response = new JsonObject();
        response.addProperty("type",    type);
        response.addProperty("status",  status);
        response.addProperty("message", message);
        if (data != null) {
            response.add("data", GSON.toJsonTree(data));
        }
        return response.toString();
    }

    public static String buildSuccess(String type,
                                      String message,
                                      Object data) {
        return buildResponse(type,
                ProtocolConstants.SUCCESS,
                message,
                data);
    }

    public static String buildFailure(String type, String message) {
        return buildResponse(type,
                ProtocolConstants.FAILURE,
                message,
                null);
    }
}