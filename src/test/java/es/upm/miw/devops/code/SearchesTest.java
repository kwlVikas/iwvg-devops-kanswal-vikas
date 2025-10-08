package es.upm.miw.devops.code;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Objects;

import static org.assertj.core.api.Assertions.assertThat;

class SearchesTest {

    @Test
    void testFindUserFamilyNameByUserNameDistinct() {
        assertThat(new Searches().findUserFamilyNameByUserNameDistinct("Paula").toList())
                .containsExactly("Torres");
    }

    @Test
    void testFindUserFractionNumeratorByFamilyName() {
        assertThat(new Searches().findFractionNumeratorByUserFamilyName("Torres").toList())
                .containsExactly(2, 4, 0, 1, 1);
    }

    @Test
    void testFindFamilyNameByFractionDenominator() {
        assertThat(new Searches().findUserFamilyNameByFractionDenominator(2).toList())
                .containsExactly("López", "Torres");
    }

    // ====== NUEVOS TESTS PARA STUBS (suben cobertura de New Code) ======

    @Test
    void testFindFirstDecimalFractionByUserName_isNull() {
        assertThat(new Searches().findFirstDecimalFractionByUserName("any")).isNull();
    }

    @Test
    void testFindUserIdByAllProperFraction_isEmpty() {
        assertThat(new Searches().findUserIdByAllProperFraction().toList()).isEmpty();
    }

    @Test
    void testFindDecimalImproperFractionByUserName_isEmpty() {
        assertThat(new Searches().findDecimalImproperFractionByUserName("any").toList()).isEmpty();
    }

    @Test
    void testFindFirstProperFractionByUserId_isNull() {
        assertThat(new Searches().findFirstProperFractionByUserId("any")).isNull();
    }

    @Test
    void testFindUserFamilyNameByImproperFraction_isEmpty() {
        assertThat(new Searches().findUserFamilyNameByImproperFraction().toList()).isEmpty();
    }

    @Test
    void testFindHighestFraction_isNull() {
        assertThat(new Searches().findHighestFraction()).isNull();
    }

    @Test
    void testFindUserNameByAnyImproperFraction_isEmpty() {
        assertThat(new Searches().findUserNameByAnyImproperFraction().toList()).isEmpty();
    }

    @Test
    void testFindUserFamilyNameByAllNegativeSignFractionDistinct_isEmpty() {
        assertThat(new Searches().findUserFamilyNameByAllNegativeSignFractionDistinct().toList()).isEmpty();
    }

    @Test
    void testFindDecimalFractionByUserName_isEmpty() {
        assertThat(new Searches().findDecimalFractionByUserName("any").toList()).isEmpty();
    }

    // ====== TUS TESTS IMPLEMENTADOS (d, e, 1, 3) ======

    @Test
    void testFindDecimalFractionByNegativeSignFraction() {
        List<Double> obtained = new Searches().findDecimalFractionByNegativeSignFraction().toList();
        List<Double> expected = new UsersDatabase().findAll()
                .flatMap(user -> user.getFractions().stream())
                .filter(Objects::nonNull)
                .filter(f -> (long) f.getNumerator() * (long) f.getDenominator() < 0)
                .map(Fraction::decimal)
                .toList();

        assertThat(obtained).containsExactlyElementsOf(expected);
        assertThat(obtained).allMatch(d -> d < 0.0d);
    }

    @Test
    void testFindFractionAdditionByUserId() {
        User target = new UsersDatabase().findAll()
                .filter(u -> u.getFractions() != null && u.getFractions().stream().anyMatch(Objects::nonNull))
                .findFirst()
                .orElse(null);

        if (target == null) {
            assertThat(new Searches().findFractionAdditionByUserId("no-exists")).isNull();
            return;
        }

        int num = 0;
        int den = 1;
        for (Fraction f : target.getFractions()) {
            if (f == null) continue;
            num = num * f.getDenominator() + f.getNumerator() * den;
            den = den * f.getDenominator();
        }

        Fraction obtained = new Searches().findFractionAdditionByUserId(target.getId());
        assertThat(obtained).isNotNull();
        assertThat(obtained.getNumerator()).isEqualTo(num);
        assertThat(obtained.getDenominator()).isEqualTo(den);
    }

    @Test
    void testFindUserIdBySomeProperFraction() {
        List<String> expected = new UsersDatabase().findAll()
                .filter(user -> user.getFractions().stream()
                        .filter(Objects::nonNull)
                        .anyMatch(f -> f.getNumerator() < f.getDenominator()))
                .map(User::getId)
                .toList();

        List<String> obtained = new Searches().findUserIdBySomeProperFraction().toList();

        assertThat(obtained).containsExactlyElementsOf(expected);
    }

    @Test
    void testFindFirstFractionDivisionByUserId() {
        User target = new UsersDatabase().findAll()
                .filter(u -> u.getFractions() != null
                        && u.getFractions().stream().filter(Objects::nonNull).limit(2).count() >= 2)
                .findFirst()
                .orElse(null);

        if (target == null) {
            assertThat(new Searches().findFirstFractionDivisionByUserId("no-exists")).isNull();
            return;
        }

        Fraction f1 = target.getFractions().stream().filter(Objects::nonNull).findFirst().orElse(null);
        Fraction f2 = target.getFractions().stream().filter(Objects::nonNull).skip(1).findFirst().orElse(null);

        Fraction expected = new Fraction(
                f1.getNumerator() * f2.getDenominator(),
                f1.getDenominator() * f2.getNumerator()
        );

        Fraction obtained = new Searches().findFirstFractionDivisionByUserId(target.getId());

        assertThat(obtained).isNotNull();
        assertThat(obtained.getNumerator()).isEqualTo(expected.getNumerator());
        assertThat(obtained.getDenominator()).isEqualTo(expected.getDenominator());
    }
}
