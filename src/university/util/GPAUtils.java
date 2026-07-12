package university.util;

import university.entities.Enrollment;
import university.entities.Student;
import university.enums.Grade;

/**
 * Допоміжні методи: обчислення GPA та сортування.
 */
public class GPAUtils {

    /** Середній бал за масивом зарахувань. Зарахування з оцінкою NA не враховуються. */
    public static double calculateGPA(Enrollment[] enrollments) {
        double sum = 0;
        int n = 0;
        for (Enrollment e : enrollments) {
            if (e.getGrade() != Grade.NA) {
                sum += e.getGrade().getPoints();
                n++;
            }
        }
        return n == 0 ? 0.0 : sum / n;
    }

    /**
     * Сортує паралельні масиви students/values за спаданням значення value (selection sort).
     * Використовується для звіту "Топ-N студентів за GPA".
     */
    public static void sortDescByValue(Student[] students, double[] values) {
        for (int i = 0; i < values.length - 1; i++) {
            int maxIdx = i;
            for (int j = i + 1; j < values.length; j++) {
                if (values[j] > values[maxIdx]) {
                    maxIdx = j;
                }
            }
            if (maxIdx != i) {
                double tv = values[i];
                values[i] = values[maxIdx];
                values[maxIdx] = tv;

                Student ts = students[i];
                students[i] = students[maxIdx];
                students[maxIdx] = ts;
            }
        }
    }
}
