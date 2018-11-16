package br.com.mercadolivre.mltest.domain.model.paymentmethod;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class SecurityCode implements Serializable {

    @SerializedName("mode")
    @Expose
    private String mode;
    @SerializedName("card_location")
    @Expose
    private String cardLocation;
    @SerializedName("length")
    @Expose
    private Integer length;

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public String getCardLocation() {
        return cardLocation;
    }

    public void setCardLocation(String cardLocation) {
        this.cardLocation = cardLocation;
    }

    public Integer getLength() {
        return length;
    }

    public void setLength(Integer length) {
        this.length = length;
    }

}