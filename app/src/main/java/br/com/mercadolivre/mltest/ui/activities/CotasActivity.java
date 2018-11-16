package br.com.mercadolivre.mltest.ui.activities;

import androidx.appcompat.app.AppCompatActivity;
import br.com.mercadolivre.mltest.R;
import br.com.mercadolivre.mltest.domain.model.cotas.Cotas;
import br.com.mercadolivre.mltest.domain.model.cotas.PayerCost;
import br.com.mercadolivre.mltest.ui.fragments.BankFragment;
import br.com.mercadolivre.mltest.ui.fragments.CotasFragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

public class CotasActivity extends AppCompatActivity  implements
    CotasFragment.OnListFragmentInteractionListener{


        private String montant;
        private String paymentMethodId;
        private String bankId;


    @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_bank);
            getSupportActionBar().setTitle("Seleccionar número de cuotas");

            Intent intent = getIntent();
            montant = intent.getStringExtra("montant");

            paymentMethodId = intent.getStringExtra("paymentMethodId");
            bankId = intent.getStringExtra("bankId");

            CotasFragment cotasFragment = new CotasFragment();
            cotasFragment.setPaymentMethodId(paymentMethodId);
            cotasFragment.setMontant(montant);
            cotasFragment.setBankId(bankId);

            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, cotasFragment).commit();

        }

    @Override
    public void onListFragmentInteraction(PayerCost item) {
        Intent intent = new Intent();
        intent.putExtra("payerCost",item);
        setResult(RESULT_OK, intent);
        finish();
    }
}
