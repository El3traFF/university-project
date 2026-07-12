package university.interfaces;

/**
 * Позначає сутність, яка має статус оплати (наприклад, зарахування на курс).
 */
public interface Payable {
    boolean isPaid();
    void markAsPaid();
}
