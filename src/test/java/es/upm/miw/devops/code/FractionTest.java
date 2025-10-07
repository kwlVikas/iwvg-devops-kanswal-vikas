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
    void testDecimal() {
        assertThat(fraction.decimal()).isCloseTo(0.75, within(1e-5));
    }

    @Test
    void testIsProper() {
        assertThat(new Fraction(1, 2).isProper()).isTrue();
        assertThat(new Fraction(3, 2).isProper()).isFalse();
    }

    @Test
    void testIsImproper() {
        assertThat(new Fraction(5, 3).isImproper()).isTrue();
        assertThat(new Fraction(1, 3).isImproper()).isFalse();
    }

    @Test
    void testIsEquivalent() {
        Fraction f1 = new Fraction(1, 2);
        Fraction f2 = new Fraction(2, 4);
        Fraction f3 = new Fraction(3, 4);
        assertThat(f1.isEquivalent(f2)).isTrue();
        assertThat(f1.isEquivalent(f3)).isFalse();
    }

    @Test
    void testAdd() {
        Fraction f1 = new Fraction(1, 2);
        Fraction f2 = new Fraction(1, 3);
        Fraction result = f1.add(f2);
        assertThat(result.getNumerator()).isEqualTo(5);
        assertThat(result.getDenominator()).isEqualTo(6);
    }

    @Test
    void testMultiply() {
        Fraction f1 = new Fraction(2, 3);
        Fraction f2 = new Fraction(3, 4);
        Fraction result = f1.multiply(f2);
        assertThat(result.getNumerator()).isEqualTo(6);
        assertThat(result.getDenominator()).isEqualTo(12);
    }

    @Test
    void testDivide() {
        Fraction f1 = new Fraction(1, 2);
        Fraction f2 = new Fraction(3, 4);
        Fraction result = f1.divide(f2);
        assertThat(result.getNumerator()).isEqualTo(4);
        assertThat(result.getDenominator()).isEqualTo(6);
    }
}
