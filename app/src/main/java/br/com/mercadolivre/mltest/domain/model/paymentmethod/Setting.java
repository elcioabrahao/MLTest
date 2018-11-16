package br.com.mercadolivre.mltest.domain.model.paymentmethod;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Setting implements Serializable {

    @SerializedName("security_code")
    @Expose
    private SecurityCode securityCode;
    @SerializedName("card_number")
    @Expose
    private CardNumber cardNumber;
    @SerializedName("bin")
    @Expose
    private Bin bin;

    public SecurityCode getSecurityCode() {
        return securityCode;
    }

    public void setSecurityCode(SecurityCode securityCode) {
        this.securityCode = securityCode;
    }

    public CardNumber getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(CardNumber cardNumber) {
        this.cardNumber = cardNumber;
    }

    public Bin getBin() {
        return bin;
    }

    public void setBin(Bin bin) {
        this.bin = bin;
    }

}