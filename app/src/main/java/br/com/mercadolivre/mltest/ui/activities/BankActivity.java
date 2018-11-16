package br.com.mercadolivre.mltest.ui.activities;

import androidx.appcompat.app.AppCompatActivity;
import br.com.mercadolivre.mltest.R;
import br.com.mercadolivre.mltest.domain.model.bank.Bank;
import br.com.mercadolivre.mltest.domain.model.paymentmethod.PaymentMethod;
import br.com.mercadolivre.mltest.ui.fragments.BankFragment;
import br.com.mercadolivre.mltest.ui.fragments.PaymentMethodFragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

public class BankActivity extends AppCompatActivity implements
        BankFragment.OnListFragmentInteractionListener{


    private String montant;
    private String paymentMethodId;
    private Bank bank;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bank);
        getSupportActionBar().setTitle("Seleccionar Banco");

        Intent intent = getIntent();
        montant = intent.getStringExtra("montant");
        paymentMethodId = intent.getStringExtra("paymentMethodId");

        BankFragment bankFragment = new BankFragment();
        bankFragment.setPaymentMethodId(paymentMethodId);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, bankFragment).commit();

    }


    @Override
    public void onListFragmentInteraction(Bank item) {

        this.bank = item;
        Intent intent = new Intent(this, CotasActivity.class);
        intent.putExtra("montant",montant);
        intent.putExtra("paymentMethodId",paymentMethodId);
        intent.putExtra("bankId",item.getId());
        startActivityForResult(intent, MainActivity.PAYMENT_FLOW);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == MainActivity.PAYMENT_FLOW) {

            if (resultCode == RESULT_OK) {
                data.putExtra("bank",bank);
                setResult(RESULT_OK, data);
                finish();
            }
        }
    }
}
