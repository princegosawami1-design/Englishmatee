package com.example.data.content

import com.example.data.models.DailyTask
import com.example.data.models.GrammarLesson
import com.example.data.models.GrammarQuiz
import com.example.data.models.ListeningExercise
import com.example.data.models.PronunciationExercise
import com.example.data.models.ReadingPassage
import com.example.data.models.SentenceExercise
import com.example.data.models.SpeakingExercise
import com.example.data.models.VocabWord

object LearningCurriculum {

    val grammarLessons: List<GrammarLesson> = listOf(
        GrammarLesson(
            id = "g1",
            title = "Present Simple vs. Continuous",
            level = "A1-A2",
            explanation = "Present Simple describes routines, general facts, and permanent states. Present Continuous expresses actions happening right now, temporary situations, or ongoing trends.",
            rule = "Present Simple: Subject + Verb(s/es)\nPresent Continuous: Subject + am/is/are + Verb-ing",
            examples = listOf(
                "Fact: Water boils at 100 degrees Celsius.",
                "Routine: I drink black coffee every morning.",
                "Right Now: Listen! Sarah is playing the piano.",
                "Temporary: I am living with my cousin until I find an apartment."
            ),
            quizzes = listOf(
                GrammarQuiz(
                    question = "Right now, David _____ a report for his manager.",
                    options = listOf("writes", "is writing", "wrote", "has written"),
                    correctIndex = 1,
                    explanation = "'Right now' indicates an action currently underway, requiring Present Continuous."
                ),
                GrammarQuiz(
                    question = "The train to London _____ at 8:15 AM every weekday.",
                    options = listOf("leaves", "is leaving", "left", "has left"),
                    correctIndex = 0,
                    explanation = "Timetables and fixed schedules use the Present Simple tense."
                )
            )
        ),
        GrammarLesson(
            id = "g2",
            title = "Past Simple & Irregular Verbs",
            level = "A2-B1",
            explanation = "Past Simple refers to completed events at a specific time in the past. Regular verbs add '-ed', while irregular verbs change their vowel or form entirely.",
            rule = "Affirmative: Subject + Past Verb (V2)\nNegative: Subject + did not + Base Verb\nQuestion: Did + Subject + Base Verb?",
            examples = listOf(
                "Regular: We watched an inspiring documentary yesterday.",
                "Irregular (go -> went): She went to Tokyo last summer.",
                "Irregular (buy -> bought): He bought a vintage guitar.",
                "Negative: They did not recognize him at the airport."
            ),
            quizzes = listOf(
                GrammarQuiz(
                    question = "Two weeks ago, we _____ our grandparents in Edinburgh.",
                    options = listOf("visit", "visited", "visiting", "have visited"),
                    correctIndex = 1,
                    explanation = "'Two weeks ago' is a specific past time point, requiring Past Simple 'visited'."
                ),
                GrammarQuiz(
                    question = "Why _____ you call me back last night?",
                    options = listOf("didn't", "don't", "haven't", "weren't"),
                    correctIndex = 0,
                    explanation = "Past simple questions and negatives use auxiliary 'did / didn't'."
                )
            )
        ),
        GrammarLesson(
            id = "g3",
            title = "Conditional Sentences (Zero to Third)",
            level = "B1-B2",
            explanation = "Conditionals explore real, probable, hypothetical, or counterfactual scenarios based on cause and effect.",
            rule = "Zero (Fact): If + present, present\n1st (Real Future): If + present, will + base\n2nd (Unreal Present): If + past simple, would + base\n3rd (Unreal Past): If + past perfect, would have + past participle",
            examples = listOf(
                "Zero: If you heat ice, it melts.",
                "1st: If I study tonight, I will pass tomorrow's test.",
                "2nd: If I won the lottery, I would build a solar laboratory.",
                "3rd: If we had caught the earlier flight, we would have avoided the storm."
            ),
            quizzes = listOf(
                GrammarQuiz(
                    question = "If she _____ fluent Spanish, she would apply for the Madrid posting.",
                    options = listOf("speaks", "spoke", "has spoken", "will speak"),
                    correctIndex = 1,
                    explanation = "Second conditional (hypothetical present) uses 'if + past simple'."
                ),
                GrammarQuiz(
                    question = "If you _____ an umbrella, you wouldn't have gotten soaked yesterday.",
                    options = listOf("took", "had taken", "would take", "have taken"),
                    correctIndex = 1,
                    explanation = "Third conditional (past regret) uses 'had + past participle'."
                )
            )
        ),
        GrammarLesson(
            id = "g4",
            title = "Passive Voice & Agent Omission",
            level = "B2-C1",
            explanation = "Passive voice focuses on the receiver of the action or the action itself rather than who performed it. Use it when the agent is unknown, obvious, or unimportant.",
            rule = "Passive: Subject + form of 'be' + Past Participle (+ by agent)",
            examples = listOf(
                "Active: The architect designed the skyscraper in 2021.",
                "Passive: The skyscraper was designed by the architect in 2021.",
                "Present Perfect Passive: Five hundred new trees have been planted this spring.",
                "Modal Passive: Helmets must be worn at all construction sites."
            ),
            quizzes = listOf(
                GrammarQuiz(
                    question = "The annual cybersecurity report _____ by next Friday.",
                    options = listOf("will publish", "will be published", "is publishing", "published"),
                    correctIndex = 1,
                    explanation = "Future passive: will be + past participle."
                )
            )
        )
    )

    val vocabBank: List<VocabWord> = listOf(
        VocabWord(
            word = "Persevere",
            phonetic = "/ˌpɜː.sɪˈvɪər/",
            partOfSpeech = "verb",
            meaning = "To continue striving toward a goal despite difficulties, failure, or obstacles.",
            example = "If you persevere with daily English practice, your conversational fluency will transform.",
            level = "B1"
        ),
        VocabWord(
            word = "Articulate",
            phonetic = "/ɑːˈtɪk.jə.lət/",
            partOfSpeech = "adjective / verb",
            meaning = "Able to express thoughts, ideas, and feelings clearly and effectively in speech.",
            example = "She gave an articulate presentation that convinced the entire board.",
            level = "B2"
        ),
        VocabWord(
            word = "Resilient",
            phonetic = "/rɪˈzɪl.jənt/",
            partOfSpeech = "adjective",
            meaning = "Able to recover quickly from difficulties, setbacks, or tough conditions.",
            example = "Language learners become resilient by treating mistakes as learning milestones.",
            level = "B2"
        ),
        VocabWord(
            word = "Nuance",
            phonetic = "/ˈnjuː.ɑːns/",
            partOfSpeech = "noun",
            meaning = "A subtle distinction or variation in meaning, tone, expression, or feeling.",
            example = "Understanding the subtle nuance between 'confident' and 'arrogant' is crucial in professional discourse.",
            level = "C1"
        ),
        VocabWord(
            word = "Pragmatic",
            phonetic = "/præɡˈmæt.ɪk/",
            partOfSpeech = "adjective",
            meaning = "Dealing with things sensibly and realistically based on practical considerations rather than theory.",
            example = "Taking a pragmatic approach, he practiced ordering coffee in English before studying ancient poetry.",
            level = "B2"
        ),
        VocabWord(
            word = "Eloquence",
            phonetic = "/ˈel.ə.kwəns/",
            partOfSpeech = "noun",
            meaning = "Fluent, persuasive, and graceful speaking or writing.",
            example = "The diplomat spoke with such eloquence that listeners from both nations applauded.",
            level = "C1"
        ),
        VocabWord(
            word = "Meticulous",
            phonetic = "/məˈtɪk.jə.ləs/",
            partOfSpeech = "adjective",
            meaning = "Showing great attention to detail; very careful and precise.",
            example = "He made a meticulous review of the contract before signing.",
            level = "C1"
        ),
        VocabWord(
            word = "Collaborate",
            phonetic = "/kəˈlæb.ə.reɪt/",
            partOfSpeech = "verb",
            meaning = "To work jointly with others on an activity or project to produce something.",
            example = "Our engineering team collaborates with designers across three time zones.",
            level = "B1"
        )
    )

    val sentenceExercises: List<SentenceExercise> = listOf(
        SentenceExercise(
            id = "s1",
            level = "A1",
            targetSentence = "I drink hot coffee every morning",
            scrambledWords = listOf("every", "hot", "drink", "morning", "I", "coffee"),
            hint = "Start with the subject pronoun 'I' followed by the action.",
            betterSuggestion = "I enjoy a cup of fresh black coffee every morning."
        ),
        SentenceExercise(
            id = "s2",
            level = "A2",
            targetSentence = "She usually walks to the library after class",
            scrambledWords = listOf("after", "usually", "the", "library", "class", "She", "walks", "to"),
            hint = "Subject -> adverb of frequency -> verb -> destination -> time clause.",
            betterSuggestion = "Following class, she habitually strolls to the campus library."
        ),
        SentenceExercise(
            id = "s3",
            level = "B1",
            targetSentence = "If you practice speaking daily you will improve faster",
            scrambledWords = listOf("faster", "daily", "will", "you", "improve", "If", "practice", "speaking", "you"),
            hint = "First conditional condition: If + subject + verb ... then main clause.",
            betterSuggestion = "Consistent daily speaking practice accelerates your communicative fluency."
        ),
        SentenceExercise(
            id = "s4",
            level = "B2",
            targetSentence = "Although the project was challenging the team delivered remarkable results",
            scrambledWords = listOf("the", "team", "delivered", "results", "challenging", "was", "remarkable", "the", "project", "Although"),
            hint = "Begin with 'Although' subordinating conjunction.",
            betterSuggestion = "Despite considerable complexities, the team engineered an extraordinary outcome."
        )
    )

    val readingPassages: List<ReadingPassage> = listOf(
        ReadingPassage(
            id = "r1",
            title = "The Art of Active Listening",
            level = "B1",
            category = "Communication",
            passage = "Active listening is far more than simply hearing the words someone utters. It involves paying full attention to the speaker, observing their non-verbal body language, and withholding hasty judgment. When you listen actively in a second language, you resist the urge to mentally formulate your next sentence while the other person is still speaking. Instead, you reflect back key concepts with phrases such as 'So what you mean is...' or ask clarifying questions. This practice not only deepens mutual understanding but also relieves the conversational anxiety common among language learners.",
            keyWords = listOf("Utter", "Withhold", "Formulate", "Clarifying", "Mutual"),
            questions = listOf(
                GrammarQuiz(
                    question = "What is a key habit of an active listener according to the text?",
                    options = listOf(
                        "Formulating their next sentence while the other person talks.",
                        "Observing body language and asking clarifying questions.",
                        "Interrupting to give quick advice immediately.",
                        "Speaking louder than the other person."
                    ),
                    correctIndex = 1,
                    explanation = "The passage highlights observing non-verbal cues and asking clarifying questions."
                ),
                GrammarQuiz(
                    question = "How does active listening help language learners specifically?",
                    options = listOf(
                        "It guarantees a C2 placement score in one day.",
                        "It relieves conversational anxiety and deepens mutual understanding.",
                        "It eliminates the need to study vocabulary.",
                        "It allows them to avoid speaking entirely."
                    ),
                    correctIndex = 1,
                    explanation = "The text states it relieves conversational anxiety and fosters mutual understanding."
                )
            )
        ),
        ReadingPassage(
            id = "r2",
            title = "The Global Rise of Coffee Culture",
            level = "A2-B1",
            category = "Culture & Daily Life",
            passage = "From bustling cafes in Vienna to vibrant street stalls in Hanoi, coffee has transformed from a simple morning stimulant into a global social ritual. In many cultures, inviting someone 'for a coffee' rarely centers strictly on the beverage itself; it serves as a universally recognized invitation to converse, collaborate, or decompress after a strenuous workday. Specialty roasters now champion ethical bean sourcing, celebrating distinctive flavor notes ranging from citrus to toasted hazelnut.",
            keyWords = listOf("Stimulant", "Ritual", "Specialty", "Strenuous", "Sourcing"),
            questions = listOf(
                GrammarQuiz(
                    question = "What does the phrase 'inviting someone for a coffee' often symbolize?",
                    options = listOf(
                        "A formal job termination notice.",
                        "A universally recognized invitation to talk or connect.",
                        "An obligation to buy expensive equipment.",
                        "A lesson in botany."
                    ),
                    correctIndex = 1,
                    explanation = "The passage explains it is an invitation to converse, collaborate, or decompress."
                )
            )
        )
    )

    val listeningExercises: List<ListeningExercise> = listOf(
        ListeningExercise(
            id = "l1",
            title = "Booking a Hotel Room",
            level = "A2",
            textToSpeak = "Good afternoon, Grand Horizon Hotel. How may I assist your booking today? We have deluxe double rooms overlooking the harbor available for this coming weekend at ninety-five dollars per night, inclusive of complimentary breakfast.",
            question = "What is included in the ninety-five dollar room rate?",
            options = listOf("Airport taxi transfer", "Complimentary breakfast", "Spa treatments", "Late checkout only"),
            correctIndex = 1,
            explanation = "The speaker mentioned the rate is 'inclusive of complimentary breakfast'."
        ),
        ListeningExercise(
            id = "l2",
            title = "Airport Gate Change Announcement",
            level = "B1",
            textToSpeak = "Attention passengers on Flight British Airways two-four-nine bound for Chicago O'Hare. Due to late incoming aircraft maintenance, departure gate has been relocated from Gate B-twelve to Gate D-twenty-four. Boarding will commence in twenty minutes.",
            question = "Where should passengers on Flight 249 go now?",
            options = listOf("Gate B-12", "Gate D-24", "The baggage carousel", "Terminal 1 entrance"),
            correctIndex = 1,
            explanation = "The gate was relocated to Gate D-24."
        )
    )

    val speakingExercises: List<SpeakingExercise> = listOf(
        SpeakingExercise(
            id = "sp1",
            title = "Introducing Your Professional Goals",
            level = "B1",
            prompt = "Introduce yourself and explain why you want to master English this year.",
            targetPhrase = "I am focusing on enhancing my spoken English because it opens doors for international teamwork.",
            contextTip = "Keep your shoulders relaxed, speak at a measured pace, and emphasize keywords like 'enhancing' and 'teamwork'."
        ),
        SpeakingExercise(
            id = "sp2",
            title = "Politely Ordering at a Cafe",
            level = "A2",
            prompt = "Order an oat-milk cappuccino and ask if they have gluten-free pastries.",
            targetPhrase = "Could I please get an oat-milk cappuccino and do you have any gluten-free bakery items today?",
            contextTip = "Use friendly rising intonation on the polite question."
        ),
        SpeakingExercise(
            id = "sp3",
            title = "Describing a Memorable Travel Experience",
            level = "B2",
            prompt = "Share a story about a memorable trip and one unexpected lesson you learned.",
            targetPhrase = "What made that journey unforgettable was stepping outside my comfort zone to navigate unfamiliar streets.",
            contextTip = "Use past descriptive vocabulary and dynamic transitions like 'unexpectedly' and 'furthermore'."
        )
    )

    val pronunciationExercises: List<PronunciationExercise> = listOf(
        PronunciationExercise(
            id = "p1",
            soundLabel = "/θ/ vs /s/ (Think vs Sink)",
            word1 = "Think",
            word2 = "Sink",
            tip = "For /θ/ (think), gently place the tip of your tongue between your upper and lower front teeth. For /s/ (sink), keep your tongue behind your teeth.",
            exampleSentence = "I think this boat will sink if we do not bail the water."
        ),
        PronunciationExercise(
            id = "p2",
            soundLabel = "/v/ vs /w/ (Vest vs West)",
            word1 = "Vest",
            word2 = "West",
            tip = "For /v/ (vest), your top teeth gently touch your lower bottom lip. For /w/ (west), round your lips into a circle without touching your teeth.",
            exampleSentence = "Wear your warm vest as we travel west."
        ),
        PronunciationExercise(
            id = "p3",
            soundLabel = "/iː/ vs /ɪ/ (Sheep vs Ship)",
            word1 = "Sheep",
            word2 = "Ship",
            tip = "/iː/ in 'sheep' is a long tense smiling vowel. /ɪ/ in 'ship' is a short, relaxed vowel with mouth slightly open.",
            exampleSentence = "The white sheep boarded the large cargo ship."
        )
    )

    fun generateDailyPlan(cefrLevel: String, weakestSkill: String, dailyTime: String): List<DailyTask> {
        val durationPerTask = when (dailyTime) {
            "10 minutes" -> 5
            "20 minutes" -> 7
            "30 minutes" -> 10
            "45 minutes" -> 15
            else -> 12
        }

        val tasks = mutableListOf<DailyTask>()

        // 1. Weakest skill task gets priority
        val weakSkillTask = when (weakestSkill.lowercase()) {
            "grammar" -> DailyTask(
                id = "task_weak_grammar",
                title = "Targeted Grammar: $cefrLevel Structure Focus",
                category = "Grammar",
                cefrLevel = cefrLevel,
                durationMinutes = durationPerTask,
                xpReward = 20
            )
            "vocabulary" -> DailyTask(
                id = "task_weak_vocab",
                title = "Vocabulary Booster: 5 High-Impact Words",
                category = "Vocabulary",
                cefrLevel = cefrLevel,
                durationMinutes = durationPerTask,
                xpReward = 20
            )
            "sentence formation" -> DailyTask(
                id = "task_weak_sentence",
                title = "Sentence Builder: Clean Clause Arrangement",
                category = "Sentence Builder",
                cefrLevel = cefrLevel,
                durationMinutes = durationPerTask,
                xpReward = 20
            )
            "reading" -> DailyTask(
                id = "task_weak_reading",
                title = "Active Reading: Graded Comprehension",
                category = "Reading",
                cefrLevel = cefrLevel,
                durationMinutes = durationPerTask,
                xpReward = 20
            )
            else -> DailyTask(
                id = "task_weak_speaking",
                title = "Speaking Confidence: Clear Articulation Drill",
                category = "Speaking",
                cefrLevel = cefrLevel,
                durationMinutes = durationPerTask,
                xpReward = 20
            )
        }
        tasks.add(weakSkillTask)

        // 2. Daily Core Vocabulary
        tasks.add(
            DailyTask(
                id = "task_core_vocab",
                title = "Daily Vocab: Master 3 Key Terms",
                category = "Vocabulary",
                cefrLevel = cefrLevel,
                durationMinutes = durationPerTask,
                xpReward = 15
            )
        )

        // 3. Spoken Practice / Live Tutor Session
        tasks.add(
            DailyTask(
                id = "task_live_tutor",
                title = "AI Tutor Conversation: 5-Min Warmup",
                category = "Live Tutor",
                cefrLevel = cefrLevel,
                durationMinutes = durationPerTask,
                xpReward = 25
            )
        )

        // 4. Pronunciation or Sentence building
        tasks.add(
            DailyTask(
                id = "task_daily_pronun",
                title = "Pronunciation Lab: Clean Minimal Pairs",
                category = "Pronunciation",
                cefrLevel = cefrLevel,
                durationMinutes = durationPerTask,
                xpReward = 15
            )
        )

        return tasks
    }
}
