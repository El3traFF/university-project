package university.services;

import university.entities.Student;
import university.enums.StudentStatus;

import java.util.Arrays;

/**
 * Керує колекцією студентів. Дані зберігаються у масиві, який росте за потреби.
 */
public class StudentService {
    private Student[] students = new Student[4];
    private int count = 0;
    private int nextId = 1;

    private void ensureCapacity() {
        if (count == students.length) {
            students = Arrays.copyOf(students, students.length * 2);
        }
    }

    public Student add(String fullName, String email, int year) {
        Student s = new Student(nextId, fullName, email, year, StudentStatus.ACTIVE);
        ensureCapacity();
        students[count++] = s;
        nextId++;
        return s;
    }

    public Student[] getAll() {
        return Arrays.copyOf(students, count);
    }

    public Student findById(int id) {
        for (int i = 0; i < count; i++) {
            if (students[i].getId() == id) {
                return students[i];
            }
        }
        return null;
    }

    public void update(int id, String fullName, String email, int year) {
        Student s = findById(id);
        if (s == null) {
            throw new IllegalArgumentException("Студента з ID=" + id + " не знайдено");
        }
        s.setFullName(fullName);
        s.setEmail(email);
        s.setYear(year);
    }

    public boolean delete(int id) {
        for (int i = 0; i < count; i++) {
            if (students[i].getId() == id) {
                System.arraycopy(students, i + 1, students, i, count - i - 1);
                students[count - 1] = null;
                count--;
                return true;
            }
        }
        return false;
    }

    public void changeStatus(int id, StudentStatus status) {
        Student s = findById(id);
        if (s == null) {
            throw new IllegalArgumentException("Студента з ID=" + id + " не знайдено");
        }
        s.setStatus(status);
    }

    public Student[] filterByStatus(StudentStatus status) {
        Student[] result = new Student[count];
        int n = 0;
        for (int i = 0; i < count; i++) {
            if (students[i].getStatus() == status) {
                result[n++] = students[i];
            }
        }
        return Arrays.copyOf(result, n);
    }

    public Student[] filterByYear(int year) {
        Student[] result = new Student[count];
        int n = 0;
        for (int i = 0; i < count; i++) {
            if (students[i].getYear() == year) {
                result[n++] = students[i];
            }
        }
        return Arrays.copyOf(result, n);
    }

    /** Простий bubble sort за ПІБ (як вимагає завдання). */
    public Student[] sortedByNameBubble() {
        Student[] arr = getAll();
        for (int i = 0; i < arr.length - 1; i++) {
            for (int j = 0; j < arr.length - 1 - i; j++) {
                if (arr[j].getFullName().compareToIgnoreCase(arr[j + 1].getFullName()) > 0) {
                    Student tmp = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = tmp;
                }
            }
        }
        return arr;
    }

    public Student[] search(String query) {
        String q = query.toLowerCase();
        Student[] result = new Student[count];
        int n = 0;
        for (int i = 0; i < count; i++) {
            if (students[i].getFullName().toLowerCase().contains(q)
                    || students[i].getEmail().toLowerCase().contains(q)) {
                result[n++] = students[i];
            }
        }
        return Arrays.copyOf(result, n);
    }
}
