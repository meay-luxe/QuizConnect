package com.example.quizconnect.network;

/**
 * Single source of truth for all message type strings.
 *
 * Both the client AND server import this file.
 * This guarantees they always use the exact same strings.
 *
 * HOW TO USE:
 *   import static com.example.quizconnect.network.ProtocolConstants.*;
 *   then just write:  LOGIN  instead of  "LOGIN"
 */
public final class ProtocolConstants {

    // ── Prevent anyone from creating an instance ──
    // This class is just a holder for constants, never instantiated
    private ProtocolConstants() {}

    // ════════════════════════════════════════
    //  REQUEST TYPES  (Client → Server)
    // ════════════════════════════════════════

    /** User wants to log in */
    public static final String LOGIN            = "LOGIN";

    /** New user wants to create an account */
    public static final String REGISTER         = "REGISTER";

    /** Teacher wants to save a new quiz */
    public static final String CREATE_QUIZ      = "CREATE_QUIZ";

    /** Student/Teacher wants the list of all quizzes */
    public static final String FETCH_QUIZZES    = "FETCH_QUIZZES";

    /** Student wants the questions for a specific quiz */
    public static final String FETCH_QUESTIONS  = "FETCH_QUESTIONS";

    /** Student finished quiz, sending their answers */
    public static final String SUBMIT_QUIZ      = "SUBMIT_QUIZ";

    /** Teacher wants to see results/scores */
    public static final String FETCH_RESULTS    = "FETCH_RESULTS";

    /** Someone sent a chat message */
    public static final String CHAT_MESSAGE     = "CHAT_MESSAGE";

    /** User wants recent chat history when they first log in */
    public static final String FETCH_CHAT_HISTORY = "FETCH_CHAT_HISTORY";


    // ════════════════════════════════════════
    //  RESPONSE STATUS  (Server → Client)
    // ════════════════════════════════════════

    /** Server processed the request successfully */
    public static final String SUCCESS          = "SUCCESS";

    /** Server could not process the request */
    public static final String FAILURE          = "FAILURE";
}