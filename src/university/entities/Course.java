package university.entities;

public class Course {
    private int id;
    private String title;
    private int credits;
    private Integer teacherId; // може бути null, якщо викладач ще не призначений

    public Course(int id, String title, int credits, Integer teacherId) {
        this.id = id;
        setTitle(title);
        setCredits(credits);
        this.teacherId = teacherId;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        if (title == null || title.trim().isEmpty()) {
            throw new IllegalArgumentException("Назва курсу не може бути порожньою");
        }
        this.title = title.trim();
    }

    public int getCredits() {
        return credits;
    }

    public void setCredits(int credits) {
        if (credits <= 0) {
            throw new IllegalArgumentException("Кількість кредитів має бути > 0");
        }
        this.credits = credits;
    }

    public Integer getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(Integer teacherId) {
        this.teacherId = teacherId;
    }

    @Override
    public String toString() {
        return "ID=" + id + ", курс='" + title + "', кредити=" + credits
                + ", викладач ID=" + (teacherId == null ? "не призначено" : teacherId);
    }
}
