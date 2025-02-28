import java.util.ArrayList;
import java.util.List;

public class Student {
    private Long id;
    private String firstName;
    private int number;
    private int goodQuestion;
    private List<String> questions;
    private List<String> answers;

    public void reset() {
        this.number = 0;
        this.goodQuestion = 0;
        this.questions.clear();
        this.answers.clear();
    }

    public Long getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public int getGoodQuestion() {
        return goodQuestion;
    }

    public int getNumber() {
        return number;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public void setGoodQuestion(int goodQuestion) {
        this.goodQuestion = goodQuestion;
    }


    public void veryGoodQuestion() {
        this.goodQuestion++;
    }


    public void vetynumber() {
        this.number++;
    }


    public Student(Long id, String firstName) {
        this.id = id;
        this.firstName = firstName;
        this.number = 0;
        this.goodQuestion = 0;
        this.questions = new ArrayList<>();
        this.answers = new ArrayList<>();
    }


    public void addQuestion(String question) {
        questions.add(question);
    }


    public void addAnswer(String answer) {
        answers.add(answer);
    }


    public List<String> getQuestions() {
        return questions;
    }


    public List<String> getAnswers() {
        return answers;
    }
    }

