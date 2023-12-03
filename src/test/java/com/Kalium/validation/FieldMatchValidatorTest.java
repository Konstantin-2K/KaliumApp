package com.Kalium.validation;

import org.junit.jupiter.api.Test;

import java.lang.annotation.Annotation;

import static org.junit.jupiter.api.Assertions.*;

class FieldMatchValidatorTest {

    @Test
    void isValid_WhenFieldsMatch_ShouldReturnTrue() {
        // Arrange
        FieldMatchValidator validator = new FieldMatchValidator();
        validator.initialize(createFieldMatch("password", "confirmPassword", "Passwords do not match"));

        // Act
        TestObject testObject = new TestObject();
        testObject.setPassword("password123");
        testObject.setConfirmPassword("password123");

        boolean isValid = validator.isValid(testObject, null);

        // Assert
        assertTrue(isValid);
    }


    private FieldMatch createFieldMatch(String first, String second, String message) {
        return new FieldMatch() {
            @Override
            public Class<? extends Annotation> annotationType() {
                return null;
            }

            @Override
            public Class<?>[] groups() {
                return new Class[0];
            }

            @Override
            public Class[] payload() {
                return new Class[0];
            }

            @Override
            public String first() {
                return first;
            }

            @Override
            public String second() {
                return second;
            }

            @Override
            public String message() {
                return message;
            }
        };
    }

    static class TestObject {
        private String password;
        private String confirmPassword;

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public String getConfirmPassword() {
            return confirmPassword;
        }

        public void setConfirmPassword(String confirmPassword) {
            this.confirmPassword = confirmPassword;
        }
    }
}
