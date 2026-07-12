package university;

import university.entities.*;
import university.enums.*;
import university.services.*;
import university.util.GPAUtils;

import java.util.Scanner;

public class Main {
    private static final Scanner scanner = new Scanner(System.in);
    private static final StudentService studentService = new StudentService();
    private static final TeacherService teacherService = new TeacherService();
    private static final CourseService courseService = new CourseService();
    private static final EnrollmentService enrollmentService = new EnrollmentService();

    public static void main(String[] args) {
        seedData();
        mainMenu();
    }

    // ==================== ТЕСТОВІ ДАНІ ====================
    private static void seedData() {
        try {
            studentService.add("Іван Петренко", "ivan.petrenko@example.com", 1);
            studentService.add("Олена Коваль", "olena.koval@example.com", 2);
            studentService.add("Andriy Shevchenko", "andriy.shevchenko@example.com", 3);

            teacherService.add("Марія Гончар", "maria.honchar@example.com", TeacherPosition.PROFESSOR);
            teacherService.add("Сергій Литвин", "serhii.lytvyn@example.com", TeacherPosition.LECTURER);

            courseService.add("Основи програмування", 5, 1);
            courseService.add("Бази даних", 4, 2);

            Enrollment e1 = enrollmentService.add(1, 1, "2025-Fall");
            enrollmentService.setGrade(e1.getId(), Grade.A);
            enrollmentService.markPaid(e1.getId());

            Enrollment e2 = enrollmentService.add(1, 2, "2025-Fall");
            enrollmentService.setGrade(e2.getId(), Grade.B);

            Enrollment e3 = enrollmentService.add(2, 1, "2025-Fall");
            enrollmentService.setGrade(e3.getId(), Grade.C);
            enrollmentService.markPaid(e3.getId());

            enrollmentService.add(3, 2, "2025-Fall");
        } catch (IllegalArgumentException ex) {
            System.out.println("Помилка при заповненні тестових даних: " + ex.getMessage());
        }
    }

    // ==================== ГОЛОВНЕ МЕНЮ ====================
    private static void mainMenu() {
        boolean running = true;
        while (running) {
            System.out.println();
            System.out.println("===== ГОЛОВНЕ МЕНЮ =====");
            System.out.println("1. Студенти");
            System.out.println("2. Викладачі");
            System.out.println("3. Курси");
            System.out.println("4. Зарахування");
            System.out.println("5. Звіти / Пошук");
            System.out.println("0. Вихід");
            System.out.print("Обери пункт: ");
            String choice = scanner.nextLine().trim();
            try {
                switch (choice) {
                    case "1": studentsMenu(); break;
                    case "2": teachersMenu(); break;
                    case "3": coursesMenu(); break;
                    case "4": enrollmentsMenu(); break;
                    case "5": reportsMenu(); break;
                    case "0":
                        running = false;
                        System.out.println("До побачення!");
                        break;
                    default:
                        System.out.println("Невірний пункт меню.");
                }
            } catch (IllegalArgumentException ex) {
                System.out.println("Помилка: " + ex.getMessage());
            } catch (Exception ex) {
                System.out.println("Несподівана помилка: " + ex.getMessage());
            }
        }
    }

    // ==================== СТУДЕНТИ ====================
    private static void studentsMenu() {
        boolean back = false;
        while (!back) {
            System.out.println();
            System.out.println("--- Студенти ---");
            System.out.println("1. Додати студента");
            System.out.println("2. Показати всіх студентів");
            System.out.println("3. Оновити студента");
            System.out.println("4. Видалити студента");
            System.out.println("5. Змінити статус студента");
            System.out.println("6. Фільтр за статусом");
            System.out.println("7. Фільтр за роком навчання");
            System.out.println("8. Сортувати за ПІБ (bubble sort)");
            System.out.println("0. Назад");
            System.out.print("Обери пункт: ");
            String choice = scanner.nextLine().trim();
            try {
                switch (choice) {
                    case "1": addStudent(); break;
                    case "2": printStudents(studentService.getAll()); break;
                    case "3": updateStudent(); break;
                    case "4": deleteStudent(); break;
                    case "5": changeStudentStatus(); break;
                    case "6": filterStudentsByStatus(); break;
                    case "7": filterStudentsByYear(); break;
                    case "8": printStudents(studentService.sortedByNameBubble()); break;
                    case "0": back = true; break;
                    default: System.out.println("Невірний пункт меню.");
                }
            } catch (IllegalArgumentException ex) {
                System.out.println("Помилка: " + ex.getMessage());
            }
        }
    }

    private static void addStudent() {
        System.out.print("ПІБ: ");
        String name = scanner.nextLine();
        System.out.print("Email: ");
        String email = scanner.nextLine();
        System.out.print("Рік навчання: ");
        int year = readInt();
        Student s = studentService.add(name, email, year);
        System.out.println("Додано: " + s);
    }

    private static void printStudents(Student[] arr) {
        if (arr.length == 0) {
            System.out.println("Записів не знайдено.");
            return;
        }
        for (Student s : arr) {
            System.out.println(s);
        }
    }

    private static void updateStudent() {
        System.out.print("ID студента: ");
        int id = readInt();
        System.out.print("Нове ПІБ: ");
        String name = scanner.nextLine();
        System.out.print("Новий email: ");
        String email = scanner.nextLine();
        System.out.print("Новий рік навчання: ");
        int year = readInt();
        studentService.update(id, name, email, year);
        System.out.println("Оновлено.");
    }

    private static void deleteStudent() {
        System.out.print("ID студента: ");
        int id = readInt();
        boolean ok = studentService.delete(id);
        System.out.println(ok ? "Видалено." : "Студента не знайдено.");
    }

    private static void changeStudentStatus() {
        System.out.print("ID студента: ");
        int id = readInt();
        System.out.println("Статуси: ACTIVE, ON_LEAVE, EXPELLED, GRADUATED");
        System.out.print("Новий статус: ");
        StudentStatus status = parseEnum(StudentStatus.class, scanner.nextLine());
        studentService.changeStatus(id, status);
        System.out.println("Статус оновлено.");
    }

    private static void filterStudentsByStatus() {
        System.out.println("Статуси: ACTIVE, ON_LEAVE, EXPELLED, GRADUATED");
        System.out.print("Статус: ");
        StudentStatus status = parseEnum(StudentStatus.class, scanner.nextLine());
        printStudents(studentService.filterByStatus(status));
    }

    private static void filterStudentsByYear() {
        System.out.print("Рік навчання: ");
        int year = readInt();
        printStudents(studentService.filterByYear(year));
    }

    // ==================== ВИКЛАДАЧІ ====================
    private static void teachersMenu() {
        boolean back = false;
        while (!back) {
            System.out.println();
            System.out.println("--- Викладачі ---");
            System.out.println("1. Додати викладача");
            System.out.println("2. Показати всіх викладачів");
            System.out.println("3. Оновити викладача");
            System.out.println("4. Видалити викладача");
            System.out.println("5. Фільтр за посадою");
            System.out.println("0. Назад");
            System.out.print("Обери пункт: ");
            String choice = scanner.nextLine().trim();
            try {
                switch (choice) {
                    case "1": addTeacher(); break;
                    case "2": printTeachers(teacherService.getAll()); break;
                    case "3": updateTeacher(); break;
                    case "4": deleteTeacher(); break;
                    case "5": filterTeachersByPosition(); break;
                    case "0": back = true; break;
                    default: System.out.println("Невірний пункт меню.");
                }
            } catch (IllegalArgumentException ex) {
                System.out.println("Помилка: " + ex.getMessage());
            }
        }
    }

    private static void addTeacher() {
        System.out.print("ПІБ: ");
        String name = scanner.nextLine();
        System.out.print("Email: ");
        String email = scanner.nextLine();
        System.out.println("Посади: ASSISTANT, LECTURER, PROFESSOR");
        System.out.print("Посада: ");
        TeacherPosition position = parseEnum(TeacherPosition.class, scanner.nextLine());
        Teacher t = teacherService.add(name, email, position);
        System.out.println("Додано: " + t);
    }

    private static void printTeachers(Teacher[] arr) {
        if (arr.length == 0) {
            System.out.println("Записів не знайдено.");
            return;
        }
        for (Teacher t : arr) {
            System.out.println(t);
        }
    }

    private static void updateTeacher() {
        System.out.print("ID викладача: ");
        int id = readInt();
        System.out.print("Нове ПІБ: ");
        String name = scanner.nextLine();
        System.out.print("Новий email: ");
        String email = scanner.nextLine();
        System.out.println("Посади: ASSISTANT, LECTURER, PROFESSOR");
        System.out.print("Нова посада: ");
        TeacherPosition position = parseEnum(TeacherPosition.class, scanner.nextLine());
        teacherService.update(id, name, email, position);
        System.out.println("Оновлено.");
    }

    private static void deleteTeacher() {
        System.out.print("ID викладача: ");
        int id = readInt();
        boolean ok = teacherService.delete(id);
        System.out.println(ok ? "Видалено." : "Викладача не знайдено.");
    }

    private static void filterTeachersByPosition() {
        System.out.println("Посади: ASSISTANT, LECTURER, PROFESSOR");
        System.out.print("Посада: ");
        TeacherPosition position = parseEnum(TeacherPosition.class, scanner.nextLine());
        printTeachers(teacherService.filterByPosition(position));
    }

    // ==================== КУРСИ ====================
    private static void coursesMenu() {
        boolean back = false;
        while (!back) {
            System.out.println();
            System.out.println("--- Курси ---");
            System.out.println("1. Додати курс");
            System.out.println("2. Показати всі курси");
            System.out.println("3. Оновити курс");
            System.out.println("4. Видалити курс");
            System.out.println("5. Фільтр за викладачем");
            System.out.println("6. Фільтр за кількістю кредитів");
            System.out.println("0. Назад");
            System.out.print("Обери пункт: ");
            String choice = scanner.nextLine().trim();
            try {
                switch (choice) {
                    case "1": addCourse(); break;
                    case "2": printCourses(courseService.getAll()); break;
                    case "3": updateCourse(); break;
                    case "4": deleteCourse(); break;
                    case "5": filterCoursesByTeacher(); break;
                    case "6": filterCoursesByCredits(); break;
                    case "0": back = true; break;
                    default: System.out.println("Невірний пункт меню.");
                }
            } catch (IllegalArgumentException ex) {
                System.out.println("Помилка: " + ex.getMessage());
            }
        }
    }

    private static void addCourse() {
        System.out.print("Назва курсу: ");
        String title = scanner.nextLine();
        System.out.print("Кількість кредитів: ");
        int credits = readInt();
        System.out.print("ID викладача (0, якщо не призначено): ");
        int teacherId = readInt();
        Integer teacherIdObj = null;
        if (teacherId != 0) {
            if (teacherService.findById(teacherId) == null) {
                throw new IllegalArgumentException("Викладача з ID=" + teacherId + " не знайдено");
            }
            teacherIdObj = teacherId;
        }
        Course c = courseService.add(title, credits, teacherIdObj);
        System.out.println("Додано: " + c);
    }

    private static void printCourses(Course[] arr) {
        if (arr.length == 0) {
            System.out.println("Записів не знайдено.");
            return;
        }
        for (Course c : arr) {
            System.out.println(c);
        }
    }

    private static void updateCourse() {
        System.out.print("ID курсу: ");
        int id = readInt();
        System.out.print("Нова назва: ");
        String title = scanner.nextLine();
        System.out.print("Нова кількість кредитів: ");
        int credits = readInt();
        System.out.print("Новий ID викладача (0, якщо не призначено): ");
        int teacherId = readInt();
        Integer teacherIdObj = null;
        if (teacherId != 0) {
            if (teacherService.findById(teacherId) == null) {
                throw new IllegalArgumentException("Викладача з ID=" + teacherId + " не знайдено");
            }
            teacherIdObj = teacherId;
        }
        courseService.update(id, title, credits, teacherIdObj);
        System.out.println("Оновлено.");
    }

    private static void deleteCourse() {
        System.out.print("ID курсу: ");
        int id = readInt();
        boolean ok = courseService.delete(id);
        System.out.println(ok ? "Видалено." : "Курс не знайдено.");
    }

    private static void filterCoursesByTeacher() {
        System.out.print("ID викладача: ");
        int teacherId = readInt();
        printCourses(courseService.filterByTeacher(teacherId));
    }

    private static void filterCoursesByCredits() {
        System.out.print("Кількість кредитів: ");
        int credits = readInt();
        printCourses(courseService.filterByCredits(credits));
    }

    // ==================== ЗАРАХУВАННЯ ====================
    private static void enrollmentsMenu() {
        boolean back = false;
        while (!back) {
            System.out.println();
            System.out.println("--- Зарахування ---");
            System.out.println("1. Зарахувати студента на курс");
            System.out.println("2. Поставити оцінку");
            System.out.println("3. Позначити оплату");
            System.out.println("4. Зарахування студента + GPA");
            System.out.println("5. Транскрипт студента");
            System.out.println("0. Назад");
            System.out.print("Обери пункт: ");
            String choice = scanner.nextLine().trim();
            try {
                switch (choice) {
                    case "1": createEnrollment(); break;
                    case "2": setEnrollmentGrade(); break;
                    case "3": markEnrollmentPaid(); break;
                    case "4": viewStudentEnrollments(); break;
                    case "5": printTranscript(); break;
                    case "0": back = true; break;
                    default: System.out.println("Невірний пункт меню.");
                }
            } catch (IllegalArgumentException ex) {
                System.out.println("Помилка: " + ex.getMessage());
            }
        }
    }

    private static void createEnrollment() {
        System.out.print("ID студента: ");
        int studentId = readInt();
        if (studentService.findById(studentId) == null) {
            throw new IllegalArgumentException("Студента з ID=" + studentId + " не знайдено");
        }
        System.out.print("ID курсу: ");
        int courseId = readInt();
        if (courseService.findById(courseId) == null) {
            throw new IllegalArgumentException("Курс з ID=" + courseId + " не знайдено");
        }
        System.out.print("Семестр (наприклад, 2025-Fall): ");
        String semester = scanner.nextLine();
        Enrollment e = enrollmentService.add(studentId, courseId, semester);
        System.out.println("Створено: " + e);
    }

    private static void setEnrollmentGrade() {
        System.out.print("ID зарахування: ");
        int id = readInt();
        System.out.println("Оцінки: A, B, C, D, F, NA");
        System.out.print("Оцінка: ");
        Grade grade = parseEnum(Grade.class, scanner.nextLine());
        enrollmentService.setGrade(id, grade);
        System.out.println("Оцінку виставлено.");
    }

    private static void markEnrollmentPaid() {
        System.out.print("ID зарахування: ");
        int id = readInt();
        enrollmentService.markPaid(id);
        System.out.println("Оплату позначено.");
    }

    private static void viewStudentEnrollments() {
        System.out.print("ID студента: ");
        int studentId = readInt();
        Student student = studentService.findById(studentId);
        if (student == null) {
            throw new IllegalArgumentException("Студента з ID=" + studentId + " не знайдено");
        }
        Enrollment[] list = enrollmentService.getByStudent(studentId);
        if (list.length == 0) {
            System.out.println("У студента немає зарахувань.");
            return;
        }
        for (Enrollment e : list) {
            Course c = courseService.findById(e.getCourseId());
            System.out.println(e + (c != null ? ", курс='" + c.getTitle() + "'" : ""));
        }
        double gpa = GPAUtils.calculateGPA(list);
        System.out.printf("GPA студента %s: %.2f%n", student.getFullName(), gpa);
    }

    private static void printTranscript() {
        System.out.print("ID студента: ");
        int studentId = readInt();
        Student student = studentService.findById(studentId);
        if (student == null) {
            throw new IllegalArgumentException("Студента з ID=" + studentId + " не знайдено");
        }
        Enrollment[] list = enrollmentService.getByStudent(studentId);
        System.out.println("===== ТРАНСКРИПТ =====");
        System.out.println(student);
        if (list.length == 0) {
            System.out.println("Немає зарахувань.");
            return;
        }
        for (Enrollment e : list) {
            Course c = courseService.findById(e.getCourseId());
            String courseTitle = (c != null) ? c.getTitle() : "невідомий курс";
            System.out.printf("  Курс: %-25s Семестр: %-10s Оцінка: %-3s Оплачено: %s%n",
                    courseTitle, e.getSemester(), e.getGrade(), e.isPaid() ? "так" : "ні");
        }
        System.out.printf("GPA: %.2f%n", GPAUtils.calculateGPA(list));
    }

    // ==================== ЗВІТИ / ПОШУК ====================
    private static void reportsMenu() {
        boolean back = false;
        while (!back) {
            System.out.println();
            System.out.println("--- Звіти / Пошук ---");
            System.out.println("1. Пошук студента за ПІБ або email");
            System.out.println("2. Студенти з неоплаченими курсами");
            System.out.println("3. Середній GPA по курсу/семестру");
            System.out.println("4. Топ-N студентів за GPA");
            System.out.println("0. Назад");
            System.out.print("Обери пункт: ");
            String choice = scanner.nextLine().trim();
            try {
                switch (choice) {
                    case "1": searchStudents(); break;
                    case "2": unpaidStudentsReport(); break;
                    case "3": averageGpaByCourseSemester(); break;
                    case "4": topNByGpa(); break;
                    case "0": back = true; break;
                    default: System.out.println("Невірний пункт меню.");
                }
            } catch (IllegalArgumentException ex) {
                System.out.println("Помилка: " + ex.getMessage());
            }
        }
    }

    private static void searchStudents() {
        System.out.print("Частина ПІБ або email: ");
        String query = scanner.nextLine();
        printStudents(studentService.search(query));
    }

    private static void unpaidStudentsReport() {
        Enrollment[] unpaid = enrollmentService.unpaid();
        if (unpaid.length == 0) {
            System.out.println("Усі курси оплачено.");
            return;
        }
        System.out.println("Студенти з неоплаченими курсами:");
        for (Enrollment e : unpaid) {
            Student s = studentService.findById(e.getStudentId());
            Course c = courseService.findById(e.getCourseId());
            System.out.println("  " + (s != null ? s.getFullName() : "ID=" + e.getStudentId())
                    + " -> курс: " + (c != null ? c.getTitle() : "ID=" + e.getCourseId())
                    + " (" + e.getSemester() + ")");
        }
    }

    private static void averageGpaByCourseSemester() {
        System.out.print("ID курсу: ");
        int courseId = readInt();
        if (courseService.findById(courseId) == null) {
            throw new IllegalArgumentException("Курс з ID=" + courseId + " не знайдено");
        }
        System.out.print("Семестр: ");
        String semester = scanner.nextLine();
        Enrollment[] list = enrollmentService.getByCourseAndSemester(courseId, semester);
        if (list.length == 0) {
            System.out.println("Немає зарахувань для цього курсу та семестру.");
            return;
        }
        double avg = GPAUtils.calculateGPA(list);
        System.out.printf("Середній GPA: %.2f (на основі %d зарахувань)%n", avg, list.length);
    }

    private static void topNByGpa() {
        System.out.print("Кількість студентів N: ");
        int n = readInt();
        if (n <= 0) {
            throw new IllegalArgumentException("N має бути > 0");
        }
        Student[] allStudents = studentService.getAll();
        if (allStudents.length == 0) {
            System.out.println("Немає студентів.");
            return;
        }
        double[] gpas = new double[allStudents.length];
        for (int i = 0; i < allStudents.length; i++) {
            Enrollment[] list = enrollmentService.getByStudent(allStudents[i].getId());
            gpas[i] = GPAUtils.calculateGPA(list);
        }
        GPAUtils.sortDescByValue(allStudents, gpas);
        int limit = Math.min(n, allStudents.length);
        System.out.println("Топ-" + limit + " студентів за GPA:");
        for (int i = 0; i < limit; i++) {
            System.out.printf("  %d. %s - GPA: %.2f%n", i + 1, allStudents[i].getFullName(), gpas[i]);
        }
    }

    // ==================== ДОПОМІЖНІ МЕТОДИ ====================
    private static int readInt() {
        String line = scanner.nextLine().trim();
        try {
            return Integer.parseInt(line);
        } catch (NumberFormatException ex) {
            throw new IllegalArgumentException("Очікувалось ціле число, отримано: '" + line + "'");
        }
    }

    private static <T extends Enum<T>> T parseEnum(Class<T> enumClass, String value) {
        try {
            return Enum.valueOf(enumClass, value.trim().toUpperCase());
        } catch (IllegalArgumentException ex) {
            throw new IllegalArgumentException("Невірне значення: '" + value + "'");
        }
    }
}
