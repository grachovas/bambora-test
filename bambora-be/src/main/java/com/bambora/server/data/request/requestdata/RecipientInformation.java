package com.bambora.server.data.request.requestdata;

import com.bambora.server.data.request.AttributeData;
import com.google.gson.annotations.SerializedName;

public class RecipientInformation extends AttributeData {
    @SerializedName("Partytype")
    private String partyType;
    @SerializedName("Firstname")
    private String firstName;
    @SerializedName("Lastname")
    private String lastName;
    @SerializedName("CountryCode")
    private String countryCode;
    @SerializedName("CustomerID")
    private String customerId;
    @SerializedName("Address")
    private String address;
    @SerializedName("DateOfBirth")
    private String dateOfBirth;

    public RecipientInformation(final String partyType, final String firstName, final String lastName, final String countryCode) {
        this.partyType = partyType;
        this.firstName = firstName;
        this.lastName = lastName;
        this.countryCode = countryCode;
    }

    public RecipientInformation setCustomerId(final String customerId) {
        this.customerId = customerId;
        return this;
    }

    public RecipientInformation setAddress(final String address) {
        this.address = address;
        return this;
    }

    public RecipientInformation setDateOfBirth(final String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
        return this;
    }
}
