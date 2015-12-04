package com.epam.cdp.spring.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(Parameterized.class)
public class EmailValidatorTest {
    private final String testData;
    private final boolean expected;

    private final EmailValidator emailValidator = new EmailValidator();

    public EmailValidatorTest(String testData, boolean expected) {
        this.testData = testData;
        this.expected = expected;
    }

    @Test
    public void testValidate() throws Exception {
        final boolean actual = emailValidator.validate(testData);

        assertEquals(expected, actual);
    }

    @Parameterized.Parameters
    public static List<Object[]> testData() {
        return Arrays.asList(new Object[][] {
                { null, false },
                { "", false },
                { " ", false },
                { "some string", false },
                { "some@email.valid", true},
                { "some+123@email.valid", true},
                { "some@email-epam.valid", true},
                { "some@email-epam", false},
                { "some-email@epam@google.com", false},
        });
    }
}