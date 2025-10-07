package es.upm.miw.devops.code;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.within;

class FractionTest {

    private Fraction fraction;

    @BeforeEach
    void before() {
        fraction = new Fraction(3, 4);
    }

    @Test
    void testFractionIntInt() {
        assertThat(fraction.getNumerator()).isEqualTo(3);
        assertThat(fraction.getDenominator()).isEqualTo(4);
    }

    @Test
    void testFraction() {
        fraction = new Fraction();
        assertThat(fraction.getNumerator()).isEqualTo(1);
        assertThat(fraction.getDenominator()).isEqualTo(1);
    }

    @Test
    void testGettersAndSetters() {
        fraction.setNumerator(5);
        fraction.setDenominator(2);
        assertThat(fraction.getNumerator()).isEqualTo(5);
        assertThat(fraction.getDenominator()).isEqualTo(2);
    }

    @Test
    void testDecimal() {
        assertThat(fraction.decimal()).isCloseTo(0.75, within(1e-5));
        fraction.setNumerator(-3);
        fraction.setDenominator(2);
        assertThat(fraction.decimal()).isCloseTo(-1.5, within(1e-5));
    }
}
