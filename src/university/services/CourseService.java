package university.services;

import university.entities.Course;

import java.util.Arrays;

public class CourseService {
    private Course[] courses = new Course[4];
    private int count = 0;
    private int nextId = 1;

    private void ensureCapacity() {
        if (count == courses.length) {
            courses = Arrays.copyOf(courses, courses.length * 2);
        }
    }

    public Course add(String title, int credits, Integer teacherId) {
        Course c = new Course(nextId, title, credits, teacherId);
        ensureCapacity();
        courses[count++] = c;
        nextId++;
        return c;
    }

    public Course[] getAll() {
        return Arrays.copyOf(courses, count);
    }

    public Course findById(int id) {
        for (int i = 0; i < count; i++) {
            if (courses[i].getId() == id) {
                return courses[i];
            }
        }
        return null;
    }

    public void update(int id, String title, int credits, Integer teacherId) {
        Course c = findById(id);
        if (c == null) {
            throw new IllegalArgumentException("Курс з ID=" + id + " не знайдено");
        }
        c.setTitle(title);
        c.setCredits(credits);
        c.setTeacherId(teacherId);
    }

    public boolean delete(int id) {
        for (int i = 0; i < count; i++) {
            if (courses[i].getId() == id) {
                System.arraycopy(courses, i + 1, courses, i, count - i - 1);
                courses[count - 1] = null;
                count--;
                return true;
            }
        }
        return false;
    }

    public Course[] filterByTeacher(int teacherId) {
        Course[] result = new Course[count];
        int n = 0;
        for (int i = 0; i < count; i++) {
            if (courses[i].getTeacherId() != null && courses[i].getTeacherId() == teacherId) {
                result[n++] = courses[i];
            }
        }
        return Arrays.copyOf(result, n);
    }

    public Course[] filterByCredits(int credits) {
        Course[] result = new Course[count];
        int n = 0;
        for (int i = 0; i < count; i++) {
            if (courses[i].getCredits() == credits) {
                result[n++] = courses[i];
            }
        }
        return Arrays.copyOf(result, n);
    }
}
