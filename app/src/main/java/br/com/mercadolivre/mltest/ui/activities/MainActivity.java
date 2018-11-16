package br.com.mercadolivre.mltest.ui.activities;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import br.com.mercadolivre.mltest.R;
import br.com.mercadolivre.mltest.domain.model.bank.Bank;
import br.com.mercadolivre.mltest.domain.model.cotas.PayerCost;
import br.com.mercadolivre.mltest.domain.model.paymentmethod.PaymentMethod;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import in.shadowfax.proswipebutton.ProSwipeButton;

import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import com.shashank.sony.fancygifdialoglib.FancyGifDialog;
import com.shashank.sony.fancygifdialoglib.FancyGifDialogListener;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.editTextMontant)
    EditText editTextMontant;

    @BindView(R.id.buttonSend)
    ProSwipeButton proSwipeBtn;


    static final int PAYMENT_FLOW = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Pago de las compras");
        ButterKnife.bind(this);

        proSwipeBtn.setOnSwipeListener(new ProSwipeButton.OnSwipeListener() {
            @Override
            public void onSwipeConfirm() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        proSwipeBtn.showResultIcon(true,true);
                        Intent intent = new Intent(MainActivity.this, PaymentMethodActivity.class);
                        if(editTextMontant.getText().toString().length()>0){
                            intent.putExtra("montant",editTextMontant.getText().toString());
                            startActivityForResult(intent,PAYMENT_FLOW);
                        }
                    }
                }, 2000);
            }


        });



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {

            new FancyGifDialog.Builder(this)
                    .setTitle("Elcio Abrahão, Novembro/2018, Mercadolivre")
                    .setMessage("Este app é um teste solicitado pelo Mercadolivre.")
                    .setPositiveBtnBackground("#FF4081")
                    .setPositiveBtnText("Ok")
                    .setNegativeBtnText("Cancelar")
                    .setGifResource(R.drawable.dev)
                    .isCancellable(true)
                    .OnPositiveClicked(new FancyGifDialogListener() {
                        @Override
                        public void OnClick() {

                        }
                    })
                    .OnNegativeClicked(new FancyGifDialogListener() {
                        @Override
                        public void OnClick() {

                        }
                    })
                    .build();

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PAYMENT_FLOW) {
            if (resultCode == RESULT_OK) {


                Bank bank = (Bank)data.getSerializableExtra("bank");
                PayerCost pePayerCost = (PayerCost)data.getSerializableExtra("payerCost");
                PaymentMethod paymentMethod = (PaymentMethod)data.getSerializableExtra("paymentMethod");

                new FancyGifDialog.Builder(this)
                        .setTitle("¡Su pago fue realizado con éxito!")
                        .setMessage("Método de pago: "+paymentMethod.getName()+ "\nBanco: "+bank.getName()+"\nPagando en "+pePayerCost.getRecommendedMessage()+".")
                        .setNegativeBtnText("Cerrar")
                        .setPositiveBtnBackground("#FF4081")
                        .setPositiveBtnText("Ok")
                        .setNegativeBtnBackground("#FFA9A7A8")
                        .setGifResource(R.drawable.pagamento)
                        .isCancellable(true)
                        .OnPositiveClicked(new FancyGifDialogListener() {
                            @Override
                            public void OnClick() {
                                editTextMontant.setText("");
                                Toast.makeText(MainActivity.this,"Obrigado!", Toast.LENGTH_LONG).show();
                            }
                        })
                        .OnNegativeClicked(new FancyGifDialogListener() {
                            @Override
                            public void OnClick() {
                                editTextMontant.setText("");
                                Toast.makeText(MainActivity.this,"Obrigado!",Toast.LENGTH_LONG).show();
                            }
                        })
                        .build();


            }
        }
    }


}
