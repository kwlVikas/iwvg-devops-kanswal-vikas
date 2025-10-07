package es.upm.miw.devops.code;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

public class Searches {

    public Stream<String> findUserFamilyNameByUserNameDistinct(String userName) {
        return new UsersDatabase().findAll()
                .filter(user -> userName.equals(user.getName()))
                .map(User::getFamilyName)
                .distinct();
    }

    public Stream<Integer> findFractionNumeratorByUserFamilyName(String userFamilyName) {
        return new UsersDatabase().findAll()
                .filter(user -> userFamilyName.equals(user.getFamilyName()))
                .flatMap(user -> user.getFractions().stream()
                        .filter(Objects::nonNull)
                )
                .map(Fraction::getNumerator);
    }

    public Stream<String> findUserFamilyNameByFractionDenominator(int fractionDenominator) {
        return new UsersDatabase().findAll()
                .filter(user -> user.getFractions().stream()
                        .anyMatch(fraction -> fractionDenominator == fraction.getDenominator()))
                .map(User::getFamilyName);
    }

    public Stream<String> findUserFamilyNameInitialByAnyProperFraction() {
        return Stream.empty();
    }

    public Stream<String> findUserIdByAnyProperFraction() {
        return Stream.empty();
    }

    public Stream<String> findUserIdBySomeProperFraction() {
        return new UsersDatabase().findAll()
                // Nos quedamos solo con los usuarios que tengan al menos UNA fracción propia
                .filter(user -> user.getFractions().stream()
                        .filter(Objects::nonNull)
                        .anyMatch(f -> f.getNumerator() < f.getDenominator()))
                // Devolvemos su id
                .map(User::getId);
    }

    public Fraction findFractionMultiplicationByUserFamilyName(String familyName) {
        return null;
    }

    public Fraction findFirstFractionDivisionByUserId(String id) {
        // Tomamos las dos primeras fracciones NO nulas del usuario con ese id
        List<Fraction> fractions = new UsersDatabase().findAll()
                .filter(u -> id.equals(u.getId()))
                .findFirst()
                .map(User::getFractions)
                .orElse(List.of());

        Fraction f1 = fractions.stream().filter(Objects::nonNull).findFirst().orElse(null);
        Fraction f2 = fractions.stream().filter(Objects::nonNull).skip(1).findFirst().orElse(null);

        if (f1 == null || f2 == null) {
            return null; // no hay suficientes fracciones para dividir
        }

        // (a/b) ÷ (c/d) = (a*d) / (b*c)  (sin simplificar)
        return new Fraction(
                f1.getNumerator() * f2.getDenominator(),
                f1.getDenominator() * f2.getNumerator()
        );
    }


    public Double findFirstDecimalFractionByUserName(String name) {
        return null;
    }

    public Stream<String> findUserIdByAllProperFraction() {
        return Stream.empty();
    }

    public Stream<Double> findDecimalImproperFractionByUserName(String name) {
        return Stream.empty();
    }

    public Fraction findFirstProperFractionByUserId(String id) {
        return null;
    }

    public Stream<String> findUserFamilyNameByImproperFraction() {
        return Stream.empty();
    }

    public Fraction findHighestFraction() {
        return null;
    }

    public Stream<String> findUserNameByAnyImproperFraction() {
        return Stream.empty();
    }

    public Stream<String> findUserFamilyNameByAllNegativeSignFractionDistinct() {
        return Stream.empty();
    }

    public Stream<Double> findDecimalFractionByUserName(String name) {
        return Stream.empty();
    }

    public Stream<Double> findDecimalFractionByNegativeSignFraction() {
        return new UsersDatabase().findAll()
                .flatMap(u -> u.getFractions().stream())
                .filter(Objects::nonNull)
                .filter(f -> (long) f.getNumerator() * (long) f.getDenominator() < 0)
                .map(Fraction::decimal);
    }

    public Fraction findFractionAdditionByUserId(String id) {
        List<Fraction> fractions = new UsersDatabase().findAll()
                .filter(u -> id.equals(u.getId()))
                .findFirst()
                .map(User::getFractions)
                .orElse(List.of());

        if (fractions.isEmpty()) {
            return null;
        }

        int num = 0;
        int den = 1;

        for (Fraction f : fractions) {
            if (f == null || f.getDenominator() == 0) {
                continue; // ❗ Nueva mejora: ignorar fracciones inválidas
            }
            num = num * f.getDenominator() + f.getNumerator() * den;
            den = den * f.getDenominator();
        }

        // Si después de filtrar no se sumó nada, devolvemos null
        if (num == 0 && den == 1) {
            return null;
        }

        return new Fraction(num, den);
    }


    public Fraction findFirstFractionSubtractionByUserName(String name) {
        return null;
    }

}