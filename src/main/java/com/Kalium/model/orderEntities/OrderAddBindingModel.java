package com.Kalium.model.orderEntities;

import com.Kalium.model.userEntities.User;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.hibernate.validator.constraints.CreditCardNumber;

public class OrderAddBindingModel {
    @Size(min = 3, max = 20, message = "First name must be between 3 and 20 characters!")
    private String userFirstName;
    @Size(min = 3, max = 20, message = "Last name must be between 3 and 20 characters!")
    private String userLastName;
    @Email
    @NotNull(message = "Email must not be empty!")
    private String email;

    @Size(min = 3, max = 50, message = "Shipping address must be between 3 and 50 characters!")
    private String shippingAddress;

    @NotNull(message = "Country must not be empty!")
    private String country;

    @NotNull(message = "State must not be empty!")
    private String state;

    @NotNull(message = "Zip code must not be null!")
    private String zipCode;

    @Size(min = 3, max = 20, message = "Card holder name must be between 3 and 20 characters!")
    private String cardName;

    @NotNull(message = "Card number must not be null!")
    private String cardNumber;

    @NotNull(message = "Card expiration date must not be null!")
    private String cardExpiration;

    @Size(min = 3, max = 3, message = "Card CVV must be 3 characters long")
    private String cardCVV;

    @Size(max = 200, message = "Additional details must be maximum 200 characters long!")
    private String additionalDetails;

    public String getUserFirstName() {
        return userFirstName;
    }

    public void setUserFirstName(String userFirstName) {
        this.userFirstName = userFirstName;
    }

    public String getUserLastName() {
        return userLastName;
    }

    public void setUserLastName(String userLastName) {
        this.userLastName = userLastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getShippingAddress() {
        return shippingAddress;
    }

    public void setShippingAddress(String shippingAddress) {
        this.shippingAddress = shippingAddress;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getCardName() {
        return cardName;
    }

    public void setCardName(String cardName) {
        this.cardName = cardName;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getCardExpiration() {
        return cardExpiration;
    }

    public void setCardExpiration(String cardExpiration) {
        this.cardExpiration = cardExpiration;
    }

    public String getCardCVV() {
        return cardCVV;
    }

    public void setCardCVV(String cardCVV) {
        this.cardCVV = cardCVV;
    }

    public String getAdditionalDetails() {
        return additionalDetails;
    }

    public void setAdditionalDetails(String additionalDetails) {
        this.additionalDetails = additionalDetails;
    }

}
