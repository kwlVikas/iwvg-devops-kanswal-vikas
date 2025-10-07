package es.upm.miw.devops.code;

import java.util.List;

import org.junit.jupiter.api.Test;

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

    void testFindUserIdByAnyProperFraction() {
    }

    void testFindUserNameByAnyImproperFraction() {
    }

    void testFindUserFamilyNameByAllSignFractionDistinct() {
    }

    void testFindDecimalFractionByUserName() {
    }

    void testFindDecimalFractionBySignFraction() {
    }
    @Test
    void testFindDecimalFractionByNegativeSignFraction() {
        // Esperado: construirlo a partir del dataset real
        List<Double> obtained = new Searches().findDecimalFractionByNegativeSignFraction().toList();
        List<Double> expected = new UsersDatabase().findAll()
                .flatMap(user -> user.getFractions().stream())
                .filter(Objects::nonNull)
                .filter(f -> (long) f.getNumerator() * (long) f.getDenominator() < 0)
                .map(Fraction::decimal)
                .toList();

        assertThat(obtained).containsExactlyElementsOf(expected);


        // Propiedad esencial: todos los valores deben ser negativos
        assertThat(obtained).allMatch(d -> d < 0.0d);
    }

    @Test
    void testFindFractionAdditionByUserId() {
        // Elegimos un usuario real de la base con >= 1 fracción no nula
        User target = new UsersDatabase().findAll()
                .filter(u -> u.getFractions() != null && u.getFractions().stream().anyMatch(Objects::nonNull))
                .findFirst()
                .orElse(null);

        if (target == null) {
            // Si no hay ninguno en el dataset, el contrato es devolver null
            assertThat(new Searches().findFractionAdditionByUserId("no-exists")).isNull();
            return;
        }

        // Calculamos el esperado con la misma regla matemática (sin simplificar)
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

    void testFindFractionSubtractionByUserName() {
    }

    void testFindFractionMultiplicationByUserFamilyName() {
    }
    @Test
    void testFindUserIdBySomeProperFraction() {
        // Construimos el esperado directamente desde la base de datos
        List<String> expected = new UsersDatabase().findAll()
                .filter(user -> user.getFractions().stream()
                        .filter(Objects::nonNull)
                        .anyMatch(f -> f.getNumerator() < f.getDenominator()))
                .map(User::getId)
                .toList();

        List<String> obtained = new Searches().findUserIdBySomeProperFraction().toList();

        // Mismo orden y contenido exacto
        assertThat(obtained).containsExactlyElementsOf(expected);
    }

}