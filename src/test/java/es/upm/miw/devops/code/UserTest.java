package es.upm.miw.devops.code;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class UserTest {

    private User user;

    @BeforeEach
    void before() {
        user = new User("id-1", "Paula", "Torres",
                new ArrayList<>(List.of(new Fraction(1, 2), new Fraction(2, 3))));
    }

    @Test
    void testUserDefaultConstructor() {
        User u = new User();
        assertThat(u.getFractions()).isNotNull();
        assertThat(u.getFractions()).isEmpty();
        // name/familyName son null por defecto hasta que se establezcan
        u.setName("Ana");
        u.setFamilyName("López");
        assertThat(u.getName()).isEqualTo("Ana");
        assertThat(u.getFamilyName()).isEqualTo("López");
    }

    @Test
    void testUserConstructorFull() {
        assertThat(user.getId()).isEqualTo("id-1");
        assertThat(user.getName()).isEqualTo("Paula");
        assertThat(user.getFamilyName()).isEqualTo("Torres");
        assertThat(user.getFractions()).hasSize(2);
    }

    @Test
    void testSetters() {
        user.setName("Ana");
        user.setFamilyName("López");
        user.setFractions(new ArrayList<>());
        assertThat(user.getName()).isEqualTo("Ana");
        assertThat(user.getFamilyName()).isEqualTo("López");
        assertThat(user.getFractions()).isEmpty();
    }

    @Test
    void testAddFraction() {
        int previous = user.getFractions().size();
        user.addFraction(new Fraction(5, 6));
        assertThat(user.getFractions()).hasSize(previous + 1);
        assertThat(user.getFractions().get(user.getFractions().size() - 1).getNumerator()).isEqualTo(5);
        assertThat(user.getFractions().get(user.getFractions().size() - 1).getDenominator()).isEqualTo(6);
    }

    @Test
    void testFullName() {
        assertThat(user.fullName()).isEqualTo("Paula Torres");
    }

    @Test
    void testInitials() {
        // initials usa el primer carácter del nombre y añade "."
        assertThat(user.initials()).isEqualTo("P.");
    }
}
