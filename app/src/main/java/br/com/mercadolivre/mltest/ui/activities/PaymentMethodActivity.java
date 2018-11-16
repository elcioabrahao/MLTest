package br.com.mercadolivre.mltest.ui.activities;

import androidx.appcompat.app.AppCompatActivity;
import br.com.mercadolivre.mltest.R;
import br.com.mercadolivre.mltest.domain.model.paymentmethod.PaymentMethod;
import br.com.mercadolivre.mltest.ui.fragments.PaymentMethodFragment;

import android.content.Intent;
import android.os.Bundle;

public class PaymentMethodActivity extends AppCompatActivity implements
        PaymentMethodFragment.OnListFragmentInteractionListener{

    private String montant;
    private PaymentMethod paymentMethod;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_method);
        getSupportActionBar().setTitle("Seleccionar Medio de Pago");

        Intent intent = getIntent();

        montant = intent.getStringExtra("montant");

        PaymentMethodFragment paymentMethodFragment = new PaymentMethodFragment();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, paymentMethodFragment).commit();
    }

    @Override
    public void onListFragmentInteraction(PaymentMethod item) {
        this.paymentMethod = item;
        Intent intent = new Intent(this,BankActivity.class);
        intent.putExtra("montant",montant);
        intent.putExtra("paymentMethodId",item.getId());
        startActivityForResult(intent, MainActivity.PAYMENT_FLOW);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == MainActivity.PAYMENT_FLOW) {
            if (resultCode == RESULT_OK) {
                data.putExtra("paymentMethod",paymentMethod);
                setResult(RESULT_OK, data);
                finish();
            }
        }
    }
}
