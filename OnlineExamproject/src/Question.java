/**
 * Question.java
 * Represents one MCQ question with 4 options and a correct answer index.
 */
public class Question {

    private String questionText;          // The question itself
    private String[] options;             // Always 4 options
    private int correctOptionIndex;       // 0-based index of the correct option

    public Question(String questionText, String[] options, int correctOptionIndex) {
        this.questionText      = questionText;
        this.options           = options;
        this.correctOptionIndex = correctOptionIndex;
    }

    // ── Getters ──────────────────────────────────────────────
    public String   getQuestionText()      { return questionText;       }
    public String[] getOptions()           { return options;            }
    public int      getCorrectOptionIndex(){ return correctOptionIndex; }

    /** Returns true if the given 0-based answer index is correct. */
    public boolean isCorrect(int answerIndex) {
        return answerIndex == correctOptionIndex;
    }
}