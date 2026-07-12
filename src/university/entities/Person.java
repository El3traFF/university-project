package university.entities;

/**
 * Базовий клас для Student та Teacher. Містить спільні поля: id, ПІБ, email.
 */
public abstract class Person {
    protected int id;
    protected String fullName;
    protected String email;

    public Person(int id, String fullName, String email) {
        this.id = id;
        setFullName(fullName);
        setEmail(email);
    }

    public int getId() {
        return id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        if (fullName == null || fullName.trim().isEmpty()) {
            throw new IllegalArgumentException("ПІБ не може бути порожнім");
        }
        this.fullName = fullName.trim();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        if (email == null || !email.matches("^[\\w.+-]+@[\\w-]+\\.[a-zA-Z]{2,}$")) {
            throw new IllegalArgumentException("Неправильний формат email");
        }
        this.email = email;
    }

    public abstract String getRoleName();

    @Override
    public String toString() {
        return "ID=" + id + ", ПІБ=" + fullName + ", email=" + email;
    }
}
