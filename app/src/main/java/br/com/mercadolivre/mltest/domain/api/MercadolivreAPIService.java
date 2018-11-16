package br.com.mercadolivre.mltest.domain.api;

import java.util.List;

import br.com.mercadolivre.mltest.domain.model.bank.Bank;
import br.com.mercadolivre.mltest.domain.model.cotas.Cotas;
import br.com.mercadolivre.mltest.domain.model.paymentmethod.PaymentMethod;
import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;


public interface MercadolivreAPIService {

    @GET("v1/payment_methods")
    Observable<List<PaymentMethod>> getPaymentMethods(@Query("public_key") String publicKey);

    @GET("v1/payment_methods/card_issuers")
    Observable<List<Bank>> getBanks(@Query("public_key") String publicKey, @Query("payment_method_id") String paymentMethodId);

    @GET("v1/payment_methods/installments")
    Observable<List<Cotas>> getCotas(@Query("public_key") String publicKey, @Query("payment_method_id") String paymentMethodId, @Query("amount") String amount, @Query("issuer.id") String issuerId);


}
