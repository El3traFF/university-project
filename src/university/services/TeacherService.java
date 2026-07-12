package university.services;

import university.entities.Teacher;
import university.enums.TeacherPosition;

import java.util.Arrays;

public class TeacherService {
    private Teacher[] teachers = new Teacher[4];
    private int count = 0;
    private int nextId = 1;

    private void ensureCapacity() {
        if (count == teachers.length) {
            teachers = Arrays.copyOf(teachers, teachers.length * 2);
        }
    }

    public Teacher add(String fullName, String email, TeacherPosition position) {
        Teacher t = new Teacher(nextId, fullName, email, position);
        ensureCapacity();
        teachers[count++] = t;
        nextId++;
        return t;
    }

    public Teacher[] getAll() {
        return Arrays.copyOf(teachers, count);
    }

    public Teacher findById(int id) {
        for (int i = 0; i < count; i++) {
            if (teachers[i].getId() == id) {
                return teachers[i];
            }
        }
        return null;
    }

    public void update(int id, String fullName, String email, TeacherPosition position) {
        Teacher t = findById(id);
        if (t == null) {
            throw new IllegalArgumentException("Викладача з ID=" + id + " не знайдено");
        }
        t.setFullName(fullName);
        t.setEmail(email);
        t.setPosition(position);
    }

    public boolean delete(int id) {
        for (int i = 0; i < count; i++) {
            if (teachers[i].getId() == id) {
                System.arraycopy(teachers, i + 1, teachers, i, count - i - 1);
                teachers[count - 1] = null;
                count--;
                return true;
            }
        }
        return false;
    }

    public Teacher[] filterByPosition(TeacherPosition position) {
        Teacher[] result = new Teacher[count];
        int n = 0;
        for (int i = 0; i < count; i++) {
            if (teachers[i].getPosition() == position) {
                result[n++] = teachers[i];
            }
        }
        return Arrays.copyOf(result, n);
    }
}
