package br.com.mercadolivre.mltest.domain.model.paymentmethod;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class CardNumber implements Serializable {

    @SerializedName("length")
    @Expose
    private Integer length;
    @SerializedName("validation")
    @Expose
    private String validation;

    public Integer getLength() {
        return length;
    }

    public void setLength(Integer length) {
        this.length = length;
    }

    public String getValidation() {
        return validation;
    }

    public void setValidation(String validation) {
        this.validation = validation;
    }

}