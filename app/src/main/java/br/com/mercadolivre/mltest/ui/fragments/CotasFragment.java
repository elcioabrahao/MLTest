package br.com.mercadolivre.mltest.ui.fragments;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import br.com.mercadolivre.mltest.R;
import br.com.mercadolivre.mltest.domain.api.MercadolivreAPI;
import br.com.mercadolivre.mltest.domain.api.MercadolivreAPIService;
import br.com.mercadolivre.mltest.domain.model.bank.Bank;
import br.com.mercadolivre.mltest.domain.model.cotas.Cotas;
import br.com.mercadolivre.mltest.domain.model.cotas.PayerCost;
import br.com.mercadolivre.mltest.ui.adapters.MyBankRecyclerViewAdapter;
import br.com.mercadolivre.mltest.ui.adapters.MyCotasRecyclerViewAdapter;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;


public class CotasFragment extends Fragment {

    private static final String ARG_COLUMN_COUNT = "column-count";
    private int mColumnCount = 1;
    private CotasFragment.OnListFragmentInteractionListener mListener;

    private List<PayerCost> allCotasList;

    private MyCotasRecyclerViewAdapter adapter;

    private String paymentMethodId;
    private String montant;
    private String bankId;

    AlertDialog dialog;

    public void setPaymentMethodId(String paymentMethodId) {
        this.paymentMethodId = paymentMethodId;
    }

    public void setMontant(String montant) {
        this.montant = montant;
    }

    public void setBankId(String bankId) {
        this.bankId = bankId;
    }

    public CotasFragment() {
    }

    public static CotasFragment newInstance(int columnCount) {
        CotasFragment fragment = new CotasFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cotas_list, container, false);

        allCotasList = new ArrayList<>();

        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            RecyclerView recyclerView = (RecyclerView) view;
            if (mColumnCount <= 1) {
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
            } else {
                recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
            }
            adapter = new MyCotasRecyclerViewAdapter(allCotasList, mListener);
            recyclerView.setAdapter(adapter);
        }

        MercadolivreAPIService service = MercadolivreAPI.getClient(getContext()).create(MercadolivreAPIService.class);

        service.getCotas(MercadolivreAPI.publicKey, paymentMethodId,montant,bankId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<Cotas>>() {
                    @Override

                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(List<Cotas> cotasList) {
                        allCotasList.clear();
                        allCotasList.addAll(cotasList.get(0).getPayerCosts());
                    }

                    @Override
                    public void onError(Throwable e) {
                        dialog.show();
                        Log.e("ERROR:", "" + e.getMessage());
                    }

                    @Override
                    public void onComplete() {
                        if(allCotasList.size()==0){
                            dialog.show();
                        }else {
                            adapter.notifyDataSetChanged();
                        }
                    }
                });

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage("Las cotas no están disponibles")
                .setTitle("¡Atención!");
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                finishActivity();
            }
        });
        dialog = builder.create();

        return view;
    }

    private void finishActivity(){
        if(getActivity()!=null){
            getActivity().finish();
        }
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof CotasFragment.OnListFragmentInteractionListener) {
            mListener = (CotasFragment.OnListFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnListFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnListFragmentInteractionListener {
        void onListFragmentInteraction(PayerCost item);
    }
}