package university.entities;

import university.enums.Grade;
import university.interfaces.Payable;

/**
 * Зарахування студента на курс у конкретному семестрі.
 */
public class Enrollment implements Payable {
    private int id;
    private int studentId;
    private int courseId;
    private String semester;
    private Grade grade;
    private boolean paid;

    public Enrollment(int id, int studentId, int courseId, String semester) {
        this.id = id;
        this.studentId = studentId;
        this.courseId = courseId;
        setSemester(semester);
        this.grade = Grade.NA;
        this.paid = false;
    }

    public int getId() {
        return id;
    }

    public int getStudentId() {
        return studentId;
    }

    public int getCourseId() {
        return courseId;
    }

    public String getSemester() {
        return semester;
    }

    public void setSemester(String semester) {
        if (semester == null || semester.trim().isEmpty()) {
            throw new IllegalArgumentException("Семестр не може бути порожнім");
        }
        this.semester = semester.trim();
    }

    public Grade getGrade() {
        return grade;
    }

    public void setGrade(Grade grade) {
        if (grade == null) {
            throw new IllegalArgumentException("Оцінка не може бути порожньою");
        }
        this.grade = grade;
    }

    @Override
    public boolean isPaid() {
        return paid;
    }

    @Override
    public void markAsPaid() {
        this.paid = true;
    }

    @Override
    public String toString() {
        return "Enrollment ID=" + id + ", studentId=" + studentId + ", courseId=" + courseId
                + ", семестр=" + semester + ", оцінка=" + grade
                + ", оплачено=" + (paid ? "так" : "ні");
    }
}
