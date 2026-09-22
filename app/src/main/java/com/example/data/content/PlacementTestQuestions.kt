package com.example.data.content

import com.example.data.models.PlacementQuestion

object PlacementTestQuestions {
    val questions: List<PlacementQuestion> = listOf(
        // ================= GRAMMAR (12 Questions) =================
        PlacementQuestion(
            id = 1,
            section = "Grammar",
            questionText = "She _____ to school by bus every weekday morning.",
            options = listOf("goes", "go", "is going", "went"),
            correctOptionIndex = 0,
            explanation = "Use simple present 'goes' for habitual third-person singular actions.",
            levelWeight = "A1"
        ),
        PlacementQuestion(
            id = 2,
            section = "Grammar",
            questionText = "Look! The children _____ in the garden right now.",
            options = listOf("play", "are playing", "played", "have played"),
            correctOptionIndex = 1,
            explanation = "Use present continuous 'are playing' for an action happening at the moment of speech.",
            levelWeight = "A1"
        ),
        PlacementQuestion(
            id = 3,
            section = "Grammar",
            questionText = "Yesterday afternoon, I _____ an old friend at the library.",
            options = listOf("meet", "have met", "met", "meeting"),
            correctOptionIndex = 2,
            explanation = "'Yesterday' specifies a completed past time, requiring past simple 'met'.",
            levelWeight = "A2"
        ),
        PlacementQuestion(
            id = 4,
            section = "Grammar",
            questionText = "They haven't finished their homework _____. They need ten more minutes.",
            options = listOf("already", "yet", "still", "since"),
            correctOptionIndex = 1,
            explanation = "'Yet' is used in negative sentences with present perfect to indicate an expected action has not occurred.",
            levelWeight = "A2"
        ),
        PlacementQuestion(
            id = 5,
            section = "Grammar",
            questionText = "If it rains tomorrow, we _____ the outdoor picnic.",
            options = listOf("cancel", "would cancel", "will cancel", "canceled"),
            correctOptionIndex = 2,
            explanation = "First conditional uses: If + present simple, will + base verb.",
            levelWeight = "B1"
        ),
        PlacementQuestion(
            id = 6,
            section = "Grammar",
            questionText = "He has been working at this international firm _____ five years.",
            options = listOf("since", "for", "during", "from"),
            correctOptionIndex = 1,
            explanation = "'For' is used with a duration of time (five years), whereas 'since' refers to a starting point.",
            levelWeight = "B1"
        ),
        PlacementQuestion(
            id = 7,
            section = "Grammar",
            questionText = "The historic museum _____ by thousands of tourists every summer.",
            options = listOf("is visited", "visited", "is visiting", "has visit"),
            correctOptionIndex = 0,
            explanation = "Passive voice in present simple: is/are + past participle (is visited).",
            levelWeight = "B1"
        ),
        PlacementQuestion(
            id = 8,
            section = "Grammar",
            questionText = "If I _____ more time during college, I would have studied abroad.",
            options = listOf("have had", "had had", "had", "would have"),
            correctOptionIndex = 1,
            explanation = "Third conditional for unreal past condition uses: If + past perfect (had had).",
            levelWeight = "B2"
        ),
        PlacementQuestion(
            id = 9,
            section = "Grammar",
            questionText = "The doctor recommended that she _____ more water and rest thoroughly.",
            options = listOf("drinks", "drink", "drank", "is drinking"),
            correctOptionIndex = 1,
            explanation = "The subjunctive mood following verbs of recommendation uses the base form 'drink'.",
            levelWeight = "B2"
        ),
        PlacementQuestion(
            id = 10,
            section = "Grammar",
            questionText = "Seldom _____ such an astonishing performance in a local theater.",
            options = listOf("I have seen", "have I seen", "did I saw", "I saw"),
            correctOptionIndex = 1,
            explanation = "Negative adverbs like 'seldom' at the front of a clause trigger subject-auxiliary inversion (have I seen).",
            levelWeight = "C1"
        ),
        PlacementQuestion(
            id = 11,
            section = "Grammar",
            questionText = "Had you notified us earlier, arrangements _____ in advance.",
            options = listOf("could be made", "could have been made", "were made", "will be made"),
            correctOptionIndex = 1,
            explanation = "Inverted conditional without 'if' expresses past counterfactual: could have been made.",
            levelWeight = "C1"
        ),
        PlacementQuestion(
            id = 12,
            section = "Grammar",
            questionText = "The committee insisted upon the proposal _____ re-evaluated before ratification.",
            options = listOf("be", "being", "having been", "to be"),
            correctOptionIndex = 1,
            explanation = "Preposition 'upon' governs a gerund structure: 'being re-evaluated'.",
            levelWeight = "C2"
        ),

        // ================= VOCABULARY (8 Questions) =================
        PlacementQuestion(
            id = 13,
            section = "Vocabulary",
            questionText = "Please turn off the lights before you leave to save _____.",
            options = listOf("energy", "water", "paper", "noise"),
            correctOptionIndex = 0,
            explanation = "Turning off electric lights saves energy.",
            levelWeight = "A1"
        ),
        PlacementQuestion(
            id = 14,
            section = "Vocabulary",
            questionText = "The flight was delayed due to severe _____ conditions.",
            options = listOf("traffic", "weather", "crowd", "language"),
            correctOptionIndex = 1,
            explanation = "Flights are commonly delayed by severe weather conditions like storms or fog.",
            levelWeight = "A2"
        ),
        PlacementQuestion(
            id = 15,
            section = "Vocabulary",
            questionText = "She received a _____ at work because of her exceptional leadership skills.",
            options = listOf("promotion", "punishment", "receipt", "vacation"),
            correctOptionIndex = 0,
            explanation = "A 'promotion' is an advancement to a higher position or rank at work.",
            levelWeight = "B1"
        ),
        PlacementQuestion(
            id = 16,
            section = "Vocabulary",
            questionText = "The software update introduced several new _____ that improve user privacy.",
            options = listOf("features", "hazards", "errors", "barriers"),
            correctOptionIndex = 0,
            explanation = "'Features' are distinctive attributes or capabilities of software.",
            levelWeight = "B1"
        ),
        PlacementQuestion(
            id = 17,
            section = "Vocabulary",
            questionText = "The negotiations reached a _____ when neither side was willing to compromise.",
            options = listOf("stalemate", "harmony", "summit", "breakthrough"),
            correctOptionIndex = 0,
            explanation = "A 'stalemate' is a deadlock situation where no progress or action can be made.",
            levelWeight = "B2"
        ),
        PlacementQuestion(
            id = 18,
            section = "Vocabulary",
            questionText = "Her explanation was remarkably _____, leaving no ambiguity whatsoever.",
            options = listOf("lucid", "opaque", "vague", "hesitant"),
            correctOptionIndex = 0,
            explanation = "'Lucid' means expressed clearly and easy to understand.",
            levelWeight = "C1"
        ),
        PlacementQuestion(
            id = 19,
            section = "Vocabulary",
            questionText = "He is renowned for his _____ memory; he can recall entire pages after reading them once.",
            options = listOf("fleeting", "eidetic", "turbulent", "sporadic"),
            correctOptionIndex = 1,
            explanation = "'Eidetic' describes the ability to recall images or texts with photographic vividness.",
            levelWeight = "C2"
        ),
        PlacementQuestion(
            id = 20,
            section = "Vocabulary",
            questionText = "The novel is filled with _____ humor that subtly critiques societal norms.",
            options = listOf("caustic", "trivial", "blatant", "monotonous"),
            correctOptionIndex = 0,
            explanation = "'Caustic' describes sharp, biting, sarcastic humor.",
            levelWeight = "C2"
        ),

        // ================= SENTENCE FORMATION (8 Questions) =================
        PlacementQuestion(
            id = 21,
            section = "Sentence Formation",
            questionText = "Which sentence has the correct word order?",
            options = listOf(
                "Always she arrives on time for class.",
                "She arrives always on time for class.",
                "She always arrives on time for class.",
                "She on time arrives always for class."
            ),
            correctOptionIndex = 2,
            explanation = "Adverbs of frequency (always) precede the main verb (arrives).",
            levelWeight = "A1"
        ),
        PlacementQuestion(
            id = 22,
            section = "Sentence Formation",
            questionText = "Choose the grammatically correct question:",
            options = listOf(
                "Where do you usually buy fresh groceries?",
                "Where you usually buy fresh groceries?",
                "Where does you usually buy fresh groceries?",
                "Where buy you usually fresh groceries?"
            ),
            correctOptionIndex = 0,
            explanation = "Question structure: Question word + auxiliary (do) + subject (you) + adverb + verb.",
            levelWeight = "A2"
        ),
        PlacementQuestion(
            id = 23,
            section = "Sentence Formation",
            questionText = "Select the sentence with correct adjective ordering:",
            options = listOf(
                "She wore a leather black comfortable jacket.",
                "She wore a comfortable black leather jacket.",
                "She wore a black comfortable leather jacket.",
                "She wore a leather comfortable black jacket."
            ),
            correctOptionIndex = 1,
            explanation = "Adjective order: Opinion (comfortable) -> Color (black) -> Material (leather).",
            levelWeight = "B1"
        ),
        PlacementQuestion(
            id = 24,
            section = "Sentence Formation",
            questionText = "Which sentence correctly links contrasting ideas?",
            options = listOf(
                "Although he was exhausted, but he completed the marathon.",
                "Despite he was exhausted, he completed the marathon.",
                "Although he was exhausted, he completed the marathon.",
                "In spite of he was exhausted, he completed the marathon."
            ),
            correctOptionIndex = 2,
            explanation = "'Although' connects clauses without adding 'but'; 'despite' and 'in spite of' require noun phrases or gerunds.",
            levelWeight = "B1"
        ),
        PlacementQuestion(
            id = 25,
            section = "Sentence Formation",
            questionText = "Which sentence uses the correct indirect question structure?",
            options = listOf(
                "Could you please tell me where is the nearest station?",
                "Could you please tell me where the nearest station is?",
                "Could you please tell me where does the nearest station be?",
                "Could you please tell me that where is the nearest station?"
            ),
            correctOptionIndex = 1,
            explanation = "In embedded indirect questions, the subject precedes the verb: 'where the nearest station is'.",
            levelWeight = "B2"
        ),
        PlacementQuestion(
            id = 26,
            section = "Sentence Formation",
            questionText = "Identify the properly constructed participle clause:",
            options = listOf(
                "Having finished all her assignments, the laptop was shut down by Maria.",
                "Having finished all her assignments, Maria shut down her laptop.",
                "Finishing all her assignments, the shutdown of the laptop occurred.",
                "Having her assignments finished, laptop was closed."
            ),
            correctOptionIndex = 1,
            explanation = "The subject of the main clause (Maria) must logically perform the action in the participle clause (having finished).",
            levelWeight = "B2"
        ),
        PlacementQuestion(
            id = 27,
            section = "Sentence Formation",
            questionText = "Select the sentence with proper inversion after 'Not only':",
            options = listOf(
                "Not only she achieved top honors, but she also founded a startup.",
                "Not only did she achieve top honors, but she also founded a startup.",
                "Not only she did achieve top honors, but also she founded a startup.",
                "Not only did she achieved top honors, she also founded a startup."
            ),
            correctOptionIndex = 1,
            explanation = "'Not only' at the start takes inverted auxiliary + subject + base verb: 'did she achieve'.",
            levelWeight = "C1"
        ),
        PlacementQuestion(
            id = 28,
            section = "Sentence Formation",
            questionText = "Which sentence correctly balances parallel rhetorical structures?",
            options = listOf(
                "The CEO spoke of fostering innovation, to expand globally, and financial stability.",
                "The CEO spoke of fostering innovation, expanding globally, and ensuring financial stability.",
                "The CEO spoke of innovation fostering, to expand globally, and ensure stability.",
                "The CEO spoke of foster innovation, expand globally, and ensure financial stability."
            ),
            correctOptionIndex = 1,
            explanation = "Parallel structure requires consistent gerund forms: fostering, expanding, and ensuring.",
            levelWeight = "C2"
        ),

        // ================= READING COMPREHENSION (6 Questions) =================
        PlacementQuestion(
            id = 29,
            section = "Reading",
            questionText = "[Passage: 'Liam moved to Seattle three months ago. He enjoys the rainy climate and visits local coffee shops every weekend to read architecture magazines.'] What does Liam do on weekends?",
            options = listOf(
                "He travels to other cities.",
                "He visits coffee shops and reads architecture magazines.",
                "He designs new office buildings.",
                "He avoids going outside in the rain."
            ),
            correctOptionIndex = 1,
            explanation = "The passage explicitly states he visits local coffee shops every weekend to read architecture magazines.",
            levelWeight = "A1"
        ),
        PlacementQuestion(
            id = 30,
            section = "Reading",
            questionText = "[Passage: 'Remote work has expanded rapidly worldwide. While employees value flexibility and zero commute time, managers often emphasize the challenges of maintaining team cohesion and spontaneous brainstorming.'] What is a primary concern for managers regarding remote work?",
            options = listOf(
                "Excessive commuting costs.",
                "High expenditure on software.",
                "Maintaining team cohesion and spontaneous brainstorming.",
                "Reduced working hours for staff."
            ),
            correctOptionIndex = 2,
            explanation = "Managers emphasize challenges with team cohesion and spontaneous brainstorming.",
            levelWeight = "A2"
        ),
        PlacementQuestion(
            id = 31,
            section = "Reading",
            questionText = "[Passage: 'Renewable energy adoption has accelerated due to declining photovoltaic manufacturing expenses. However, grid modernization and utility-scale energy storage remain critical bottlenecks for 24/7 reliability.'] According to the text, what currently restricts full renewable reliability?",
            options = listOf(
                "High manufacturing expenses of solar panels.",
                "Grid modernization and utility-scale energy storage.",
                "Lack of public awareness about green power.",
                "Strict governmental restrictions on solar farms."
            ),
            correctOptionIndex = 1,
            explanation = "The text identifies grid modernization and storage as critical bottlenecks.",
            levelWeight = "B1"
        ),
        PlacementQuestion(
            id = 32,
            section = "Reading",
            questionText = "[Passage: 'Cognitive scientists argue that bilingualism bolsters executive function, enabling individuals to switch tasks and filter extraneous auditory stimuli with greater efficiency.'] What cognitive benefit is attributed to bilingualism?",
            options = listOf(
                "Faster physical reflexes during sports.",
                "Enhanced ability to switch tasks and filter distractions.",
                "Complete immunity to cognitive decline.",
                "Superior mathematical computation speed."
            ),
            correctOptionIndex = 1,
            explanation = "The passage states it enables individuals to switch tasks and filter extraneous auditory stimuli.",
            levelWeight = "B2"
        ),
        PlacementQuestion(
            id = 33,
            section = "Reading",
            questionText = "[Passage: 'Urban planners advocating for 15-minute cities emphasize that decentralized amenities reduce vehicular reliance, though skeptics contend such initiatives inadvertently exacerbate socio-economic stratification unless coupled with equitable housing subsidies.'] What do skeptics caution about 15-minute cities?",
            options = listOf(
                "They increase greenhouse emissions substantially.",
                "They could worsen socio-economic divisions without housing subsidies.",
                "They make public transport obsolete.",
                "They reduce local pedestrian commerce."
            ),
            correctOptionIndex = 1,
            explanation = "Skeptics caution they may exacerbate stratification without equitable housing subsidies.",
            levelWeight = "C1"
        ),
        PlacementQuestion(
            id = 34,
            section = "Reading",
            questionText = "[Passage: 'The author's tone toward modern hyper-specialization in academia is best described as ambivalent; while applauding granular breakthroughs, they decry the concomitant erosion of interdisciplinary synthesis.'] The word 'concomitant' most nearly means:",
            options = listOf(
                "Deliberate",
                "Accompanying",
                "Superficial",
                "Inconsequential"
            ),
            correctOptionIndex = 1,
            explanation = "'Concomitant' means naturally accompanying or associated with.",
            levelWeight = "C2"
        ),

        // ================= EVERYDAY ENGLISH (6 Questions) =================
        PlacementQuestion(
            id = 35,
            section = "Everyday English",
            questionText = "Stranger: 'Excuse me, is this seat taken?' — You want to say they can sit there. What do you reply?",
            options = listOf(
                "No, go ahead. It's free.",
                "Yes, take it away please.",
                "No, I am taking it.",
                "You are welcome here."
            ),
            correctOptionIndex = 0,
            explanation = "'No, go ahead. It's free' politely informs them the seat is available.",
            levelWeight = "A1"
        ),
        PlacementQuestion(
            id = 36,
            section = "Everyday English",
            questionText = "Colleague: 'I won the regional sales award today!' — What is the most natural congratulatory response?",
            options = listOf(
                "Never mind, try harder.",
                "Congratulations! That is fantastic news!",
                "Please forgive me for that.",
                "It doesn't matter at all."
            ),
            correctOptionIndex = 1,
            explanation = "'Congratulations! That is fantastic news!' is warm and natural.",
            levelWeight = "A2"
        ),
        PlacementQuestion(
            id = 37,
            section = "Everyday English",
            questionText = "Waitperson: 'Would you care for any dessert or coffee?' — You are full and want the check. What do you say?",
            options = listOf(
                "We are stuffed, just the bill please.",
                "No, give me money now.",
                "Stop serving us food immediately.",
                "I hate desserts today."
            ),
            correctOptionIndex = 0,
            explanation = "'We are stuffed, just the bill please' is standard, polite everyday restaurant English.",
            levelWeight = "B1"
        ),
        PlacementQuestion(
            id = 38,
            section = "Everyday English",
            questionText = "Friend: 'I am so swamped with assignments this week.' — What does 'swamped' mean in this context?",
            options = listOf(
                "Extremely busy with lots of work.",
                "Stuck in muddy water.",
                "Bored with nothing to do.",
                "Angry with teammates."
            ),
            correctOptionIndex = 0,
            explanation = "To be 'swamped' is a common idiom meaning overwhelmed with work or commitments.",
            levelWeight = "B2"
        ),
        PlacementQuestion(
            id = 39,
            section = "Everyday English",
            questionText = "Manager: 'Could you give me a ballpark figure for the marketing budget?' — What is the manager asking for?",
            options = listOf(
                "A sports ticket price.",
                "A rough or approximate estimate.",
                "An exact penny-by-penny calculation.",
                "A formal legal contract."
            ),
            correctOptionIndex = 1,
            explanation = "A 'ballpark figure' is an idiomatic expression for a rough estimate.",
            levelWeight = "C1"
        ),
        PlacementQuestion(
            id = 40,
            section = "Everyday English",
            questionText = "During a business meeting, you need to politely interject without sounding disruptive. Which phrase is best?",
            options = listOf(
                "Be quiet everyone, I want to talk!",
                "If I may chime in here for a moment...",
                "Your point is invalid, listen to me.",
                "Shut up and let me explain."
            ),
            correctOptionIndex = 1,
            explanation = "'If I may chime in here for a moment...' is a professional, polite interjection.",
            levelWeight = "C2"
        )
    )

    fun calculateAssessment(answers: Map<Int, Int>): com.example.data.models.AssessmentResult {
        var grammarCorrect = 0
        var grammarTotal = 0
        var vocabCorrect = 0
        var vocabTotal = 0
        var sentenceCorrect = 0
        var sentenceTotal = 0
        var readingCorrect = 0
        var readingTotal = 0
        var everydayCorrect = 0
        var everydayTotal = 0

        var totalCorrect = 0

        for (q in questions) {
            val selected = answers[q.id]
            val isCorrect = selected == q.correctOptionIndex
            if (isCorrect) totalCorrect++

            when (q.section) {
                "Grammar" -> {
                    grammarTotal++
                    if (isCorrect) grammarCorrect++
                }
                "Vocabulary" -> {
                    vocabTotal++
                    if (isCorrect) vocabCorrect++
                }
                "Sentence Formation" -> {
                    sentenceTotal++
                    if (isCorrect) sentenceCorrect++
                }
                "Reading" -> {
                    readingTotal++
                    if (isCorrect) readingCorrect++
                }
                "Everyday English" -> {
                    everydayTotal++
                    if (isCorrect) everydayCorrect++
                }
            }
        }

        val overallPercentage = ((totalCorrect.toFloat() / questions.size) * 100).toInt()

        val grammarScore = if (grammarTotal > 0) ((grammarCorrect.toFloat() / grammarTotal) * 100).toInt() else 0
        val vocabScore = if (vocabTotal > 0) ((vocabCorrect.toFloat() / vocabTotal) * 100).toInt() else 0
        val sentenceScore = if (sentenceTotal > 0) ((sentenceCorrect.toFloat() / sentenceTotal) * 100).toInt() else 0
        val readingScore = if (readingTotal > 0) ((readingCorrect.toFloat() / readingTotal) * 100).toInt() else 0
        val everydayScore = if (everydayTotal > 0) ((everydayCorrect.toFloat() / everydayTotal) * 100).toInt() else 0

        val cefr = when {
            overallPercentage >= 88 -> "C2"
            overallPercentage >= 75 -> "C1"
            overallPercentage >= 60 -> "B2"
            overallPercentage >= 42 -> "B1"
            overallPercentage >= 25 -> "A2"
            else -> "A1"
        }

        val skills = listOf(
            "Grammar" to grammarScore,
            "Vocabulary" to vocabScore,
            "Sentence Formation" to sentenceScore,
            "Reading" to readingScore,
            "Everyday English" to everydayScore
        )

        val strongest = skills.maxByOrNull { it.second }?.first ?: "Grammar"
        val weakest = skills.minByOrNull { it.second }?.first ?: "Speaking"

        return com.example.data.models.AssessmentResult(
            overallScore = overallPercentage,
            estimatedCefrLevel = cefr,
            grammarScore = grammarScore,
            vocabularyScore = vocabScore,
            sentenceScore = sentenceScore,
            readingScore = readingScore,
            everydayScore = everydayScore,
            strongestSkill = strongest,
            weakestSkill = weakest
        )
    }
}
