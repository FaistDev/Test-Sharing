package faistdev.at.test_sharingkaindorf;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

public class RegisterActivityTest {

    /**
     * Tests if Register fields are not empty
     */
    @Test
    public void fieldsFilledOut() {
        RegisterActivity instance = new RegisterActivity();
        assertEquals(instance.fieldsFilledOut("","1234","test@test.at"),false);
    }
}
