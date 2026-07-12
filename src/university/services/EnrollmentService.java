package university.services;

import university.entities.Enrollment;
import university.enums.Grade;

import java.util.Arrays;

public class EnrollmentService {
    private Enrollment[] enrollments = new Enrollment[4];
    private int count = 0;
    private int nextId = 1;

    private void ensureCapacity() {
        if (count == enrollments.length) {
            enrollments = Arrays.copyOf(enrollments, enrollments.length * 2);
        }
    }

    public Enrollment add(int studentId, int courseId, String semester) {
        Enrollment e = new Enrollment(nextId, studentId, courseId, semester);
        ensureCapacity();
        enrollments[count++] = e;
        nextId++;
        return e;
    }

    public Enrollment[] getAll() {
        return Arrays.copyOf(enrollments, count);
    }

    public Enrollment findById(int id) {
        for (int i = 0; i < count; i++) {
            if (enrollments[i].getId() == id) {
                return enrollments[i];
            }
        }
        return null;
    }

    public void setGrade(int id, Grade grade) {
        Enrollment e = findById(id);
        if (e == null) {
            throw new IllegalArgumentException("Зарахування з ID=" + id + " не знайдено");
        }
        e.setGrade(grade);
    }

    public void markPaid(int id) {
        Enrollment e = findById(id);
        if (e == null) {
            throw new IllegalArgumentException("Зарахування з ID=" + id + " не знайдено");
        }
        e.markAsPaid();
    }

    public Enrollment[] getByStudent(int studentId) {
        Enrollment[] result = new Enrollment[count];
        int n = 0;
        for (int i = 0; i < count; i++) {
            if (enrollments[i].getStudentId() == studentId) {
                result[n++] = enrollments[i];
            }
        }
        return Arrays.copyOf(result, n);
    }

    public Enrollment[] getByCourseAndSemester(int courseId, String semester) {
        Enrollment[] result = new Enrollment[count];
        int n = 0;
        for (int i = 0; i < count; i++) {
            if (enrollments[i].getCourseId() == courseId
                    && enrollments[i].getSemester().equalsIgnoreCase(semester)) {
                result[n++] = enrollments[i];
            }
        }
        return Arrays.copyOf(result, n);
    }

    public Enrollment[] unpaid() {
        Enrollment[] result = new Enrollment[count];
        int n = 0;
        for (int i = 0; i < count; i++) {
            if (!enrollments[i].isPaid()) {
                result[n++] = enrollments[i];
            }
        }
        return Arrays.copyOf(result, n);
    }
}
