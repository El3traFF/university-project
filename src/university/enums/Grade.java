package university.enums;

/**
 * Оцінка студента за курс. NA означає, що оцінка ще не виставлена.
 */
public enum Grade {
    A(5.0),
    B(4.0),
    C(3.0),
    D(2.0),
    F(1.0),
    NA(0.0);

    private final double points;

    Grade(double points) {
        this.points = points;
    }

    public double getPoints() {
        return points;
    }
}
