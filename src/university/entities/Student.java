package university.entities;

import university.enums.StudentStatus;

public class Student extends Person {
    private StudentStatus status;
    private int year;

    public Student(int id, String fullName, String email, int year, StudentStatus status) {
        super(id, fullName, email);
        setYear(year);
        this.status = (status == null) ? StudentStatus.ACTIVE : status;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        if (year < 1) {
            throw new IllegalArgumentException("Рік навчання має бути >= 1");
        }
        this.year = year;
    }

    public StudentStatus getStatus() {
        return status;
    }

    public void setStatus(StudentStatus status) {
        if (status == null) {
            throw new IllegalArgumentException("Статус не може бути порожнім");
        }
        this.status = status;
    }

    @Override
    public String getRoleName() {
        return "Студент";
    }

    @Override
    public String toString() {
        return super.toString() + ", рік=" + year + ", статус=" + status;
    }
}
