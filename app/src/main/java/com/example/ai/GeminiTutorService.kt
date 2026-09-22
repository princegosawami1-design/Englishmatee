package com.example.ai

import android.util.Log
import com.example.BuildConfig
import com.example.data.models.ChatMessage
import com.example.data.models.TutorCorrection
import com.squareup.moshi.JsonClass
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.logging.HttpLoggingInterceptor
import org.json.JSONArray
import org.json.JSONObject
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Query
import java.util.concurrent.TimeUnit

interface GeminiApi {
    @POST("v1beta/models/gemini-3.5-flash:generateContent")
    suspend fun generateContent(
        @Query("key") apiKey: String,
        @Body body: okhttp3.RequestBody
    ): retrofit2.Response<okhttp3.ResponseBody>
}

data class TutorResponseResult(
    val replyText: String,
    val correction: TutorCorrection? = null,
    val suggestedVocab: List<String> = emptyList()
)

class GeminiTutorService {

    private val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()

    private val okHttpClient = OkHttpClient.Builder()
        .connectTimeout(30, TimeUnit.SECONDS)
        .readTimeout(30, TimeUnit.SECONDS)
        .writeTimeout(30, TimeUnit.SECONDS)
        .build()

    private val retrofit = Retrofit.Builder()
        .baseUrl("https://generativelanguage.googleapis.com/")
        .client(okHttpClient)
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .build()

    private val api = retrofit.create(GeminiApi::class.java)

    suspend fun getTutorResponse(
        userMessage: String,
        history: List<ChatMessage>,
        mode: String,
        userCefrLevel: String,
        userGoal: String
    ): TutorResponseResult = withContext(Dispatchers.IO) {
        val apiKey = BuildConfig.GEMINI_API_KEY
        if (apiKey.isNotEmpty() && apiKey != "MY_GEMINI_API_KEY") {
            try {
                val systemPrompt = """
                    You are EnglishMate AI Tutor, a patient, encouraging, professional English teacher.
                    Student CEFR level: $userCefrLevel. Conversation Mode: $mode. Student's goal: $userGoal.
                    Rules:
                    1. Adapt vocabulary and speed to CEFR $userCefrLevel.
                    2. Maintain a friendly, professional teacher persona (never simulate romantic affection).
                    3. If the student made a noticeable grammar, vocabulary, or phrasing mistake in their latest message, formulate a correction.
                    4. Always ask a relevant follow-up question to keep the conversation flowing.
                    5. Respond ONLY with valid JSON in this format:
                    {
                      "reply": "Your conversational answer and next question here",
                      "has_correction": true/false,
                      "you_said": "The exact flawed phrase if any",
                      "better": "The natural native phrasing",
                      "why": "Clear, concise grammatical explanation",
                      "new_vocab": ["word1", "word2"]
                    }
                """.trimIndent()

                val rootJson = JSONObject()

                // System Instruction
                val systemInstruction = JSONObject()
                val systemParts = JSONArray().put(JSONObject().put("text", systemPrompt))
                systemInstruction.put("parts", systemParts)
                rootJson.put("systemInstruction", systemInstruction)

                // Contents
                val contents = JSONArray()
                // Include last 6 turns for context
                val recentHistory = history.takeLast(6)
                for (msg in recentHistory) {
                    val role = if (msg.sender == "user") "user" else "model"
                    val contentObj = JSONObject()
                    contentObj.put("role", role)
                    val parts = JSONArray().put(JSONObject().put("text", msg.text))
                    contentObj.put("parts", parts)
                    contents.put(contentObj)
                }

                // Add current message
                val userObj = JSONObject()
                userObj.put("role", "user")
                userObj.put("parts", JSONArray().put(JSONObject().put("text", userMessage)))
                contents.put(userObj)
                rootJson.put("contents", contents)

                // Generation config
                val genConfig = JSONObject()
                genConfig.put("temperature", 0.7)
                genConfig.put("responseMimeType", "application/json")
                rootJson.put("generationConfig", genConfig)

                val requestBody = rootJson.toString().toRequestBody("application/json".toMediaType())
                val response = api.generateContent(apiKey, requestBody)

                if (response.isSuccessful) {
                    val rawBody = response.body()?.string()
                    if (!rawBody.isNullOrEmpty()) {
                        val parsed = parseGeminiResponse(rawBody)
                        if (parsed != null) return@withContext parsed
                    }
                }
            } catch (e: Exception) {
                Log.w("GeminiTutorService", "Gemini API unavailable or network error: ${e.message}. Using intelligent offline fallback.")
            }
        }

        // Offline / Intelligent fallback engine
        return@withContext generateOfflineTutorResponse(userMessage, mode, userCefrLevel)
    }

    private fun parseGeminiResponse(jsonString: String): TutorResponseResult? {
        return try {
            val root = JSONObject(jsonString)
            val candidates = root.getJSONArray("candidates")
            val candidate = candidates.getJSONObject(0)
            val content = candidate.getJSONObject("content")
            val parts = content.getJSONArray("parts")
            val text = parts.getJSONObject(0).getString("text")

            val jsonOutput = JSONObject(text)
            val reply = jsonOutput.optString("reply", "That's a great thought! Could you tell me a little more about that?")
            val hasCorrection = jsonOutput.optBoolean("has_correction", false)
            val correction = if (hasCorrection) {
                TutorCorrection(
                    youSaid = jsonOutput.optString("you_said", ""),
                    better = jsonOutput.optString("better", ""),
                    why = jsonOutput.optString("why", "")
                )
            } else null

            val vocabArray = jsonOutput.optJSONArray("new_vocab")
            val vocabList = mutableListOf<String>()
            if (vocabArray != null) {
                for (i in 0 until vocabArray.length()) {
                    vocabList.add(vocabArray.getString(i))
                }
            }

            TutorResponseResult(replyText = reply, correction = correction, suggestedVocab = vocabList)
        } catch (e: Exception) {
            Log.e("GeminiTutorService", "Error parsing Gemini response: ${e.message}")
            null
        }
    }

    private fun generateOfflineTutorResponse(
        userMessage: String,
        mode: String,
        cefrLevel: String
    ): TutorResponseResult {
        val lower = userMessage.lowercase().trim()

        // Check common grammar mistakes for instant accurate correction
        var correction: TutorCorrection? = null

        if (lower.contains("yesterday i go") || lower.contains("last week i go") || lower.contains("yesterday i see")) {
            correction = TutorCorrection(
                youSaid = if (lower.contains("yesterday i go")) "yesterday I go" else "yesterday I see",
                better = if (lower.contains("yesterday i go")) "yesterday I went" else "yesterday I saw",
                why = "Use past simple tense because the action occurred at a finished time in the past."
            )
        } else if (lower.contains("she have") || lower.contains("he have") || lower.contains("it have")) {
            correction = TutorCorrection(
                youSaid = if (lower.contains("she have")) "she have" else "he have",
                better = if (lower.contains("she have")) "she has" else "he has",
                why = "Third-person singular subjects (he, she, it) take 'has' in the present tense."
            )
        } else if (lower.contains("i am agree") || lower.contains("i'm agree")) {
            correction = TutorCorrection(
                youSaid = "I am agree",
                better = "I agree",
                why = "'Agree' is already a verb, so you do not need the auxiliary verb 'am'."
            )
        } else if (lower.contains("i am student") || lower.contains("i am teacher") || lower.contains("i am engineer")) {
            correction = TutorCorrection(
                youSaid = "I am student",
                better = "I am a student / I am an engineer",
                why = "Singular countable professions require an indefinite article ('a' or 'an')."
            )
        } else if (lower.contains("more better") || lower.contains("more faster")) {
            correction = TutorCorrection(
                youSaid = if (lower.contains("more better")) "more better" else "more faster",
                better = if (lower.contains("more better")) "much better" else "much faster",
                why = "'Better' and 'faster' are already comparative; use 'much' or 'far' for emphasis, not 'more'."
            )
        }

        val reply = when (mode) {
            "Job Interview Practice" -> {
                when {
                    lower.contains("experience") || lower.contains("worked") ->
                        "That demonstrates relevant experience! In interviews, quantify your results. How did your work impact the team or company metrics?"
                    lower.contains("strength") || lower.contains("skill") ->
                        "Strong skills! How do you handle high-pressure deadlines or disagreements with colleagues?"
                    else ->
                        "Welcome to our interview session. Could you walk me through your background and why you're interested in this role?"
                }
            }
            "Travel English" -> {
                when {
                    lower.contains("ticket") || lower.contains("flight") ->
                        "Certainly! Would you prefer a window seat or an aisle seat for your upcoming journey?"
                    lower.contains("hotel") || lower.contains("room") ->
                        "Our rooms are fully equipped. Would you like a single king bed or twin beds, and what time is your expected arrival?"
                    else ->
                        "Traveling is wonderful for learning English! Where are you planning to visit next, and what sights are you excited to explore?"
                }
            }
            "Restaurant Conversation" -> {
                when {
                    lower.contains("order") || lower.contains("menu") || lower.contains("like") ->
                        "Excellent choice! That dish is prepared with fresh local herbs. Would you like still or sparkling water with your meal?"
                    lower.contains("bill") || lower.contains("check") ->
                        "Right away! We accept cards or cash. How was your dining experience overall?"
                    else ->
                        "Welcome to The Bistro! We have a delightful seasonal menu. Are you ready to order, or would you like a few more minutes?"
                }
            }
            "Speaking Confidence" -> {
                "You are speaking with great clarity! Remember that fluency comes with consistency, not perfection. What is one hobby that brings you immense joy?"
            }
            "Grammar Correction" -> {
                if (correction != null) {
                    "Notice the phrasing above—practicing that pattern will make your English sound remarkably natural! Could you try using '${correction.better}' in another sentence?"
                } else {
                    "Your grammar in that sentence was well-structured! What topic would you like to practice explaining next?"
                }
            }
            else -> { // Casual / Daily / Free Talk
                when {
                    lower.contains("hello") || lower.contains("hi") || lower.contains("hey") ->
                        "Hello! It's fantastic to practice with you today. How has your day been treating you so far?"
                    lower.contains("good") || lower.contains("fine") || lower.contains("great") ->
                        "I'm glad to hear that! What is the most exciting project or activity you are working on this week?"
                    lower.contains("learn") || lower.contains("english") ->
                        "Learning a language takes courage and curiosity. What specific skill would you like to sharpen most today?"
                    else ->
                        "That's very interesting! Could you elaborate on what you mean, and how that relates to your everyday life?"
                }
            }
        }

        val vocabList = when (mode) {
            "Job Interview Practice" -> listOf("Accomplish", "Initiative")
            "Travel English" -> listOf("Itinerary", "Boarding")
            "Restaurant Conversation" -> listOf("Appetizer", "Specialty")
            else -> listOf("Persist", "Fluency")
        }

        return TutorResponseResult(
            replyText = reply,
            correction = correction,
            suggestedVocab = vocabList
        )
    }
}
