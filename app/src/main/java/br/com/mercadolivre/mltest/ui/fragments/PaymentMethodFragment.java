package br.com.mercadolivre.mltest.ui.fragments;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import br.com.mercadolivre.mltest.R;
import br.com.mercadolivre.mltest.domain.api.MercadolivreAPI;
import br.com.mercadolivre.mltest.domain.api.MercadolivreAPIService;
import br.com.mercadolivre.mltest.domain.model.paymentmethod.PaymentMethod;
import br.com.mercadolivre.mltest.ui.activities.PaymentMethodActivity;
import br.com.mercadolivre.mltest.ui.adapters.MyPaymentMethodRecyclerViewAdapter;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;


public class PaymentMethodFragment extends Fragment {


    private static final String ARG_COLUMN_COUNT = "column-count";
    private int mColumnCount = 1;
    private OnListFragmentInteractionListener mListener;

    private List<PaymentMethod> allPaymentMethodList;

    private MyPaymentMethodRecyclerViewAdapter adapter;

    AlertDialog dialog;


    public PaymentMethodFragment() {
    }

    public static PaymentMethodFragment newInstance(int columnCount) {
        PaymentMethodFragment fragment = new PaymentMethodFragment();
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
        View view = inflater.inflate(R.layout.fragment_paymentmethod_list, container, false);

        allPaymentMethodList =  new ArrayList<>();

        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            RecyclerView recyclerView = (RecyclerView) view;
            if (mColumnCount <= 1) {
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
            } else {
                recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
            }
            adapter = new MyPaymentMethodRecyclerViewAdapter(allPaymentMethodList, mListener);
            recyclerView.setAdapter(adapter);
        }


        MercadolivreAPIService service = MercadolivreAPI.getClient(getContext()).create(MercadolivreAPIService.class);

        service.getPaymentMethods(MercadolivreAPI.publicKey)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<PaymentMethod>>() {
                    @Override

                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(List<PaymentMethod> paymentMethodList) {
                        allPaymentMethodList.clear();
                        allPaymentMethodList.addAll(paymentMethodList);
                    }

                    @Override
                    public void onError(Throwable e) {
                        dialog.show();
                        Log.e("ERROR:",""+e.getMessage());
                    }

                    @Override
                    public void onComplete() {

                        if(allPaymentMethodList.size()==0){
                            dialog.show();
                        }else {
                            adapter.notifyDataSetChanged();
                        }
                    }
                });

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage("Los medios de pago no están disponibles")
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
        if (context instanceof OnListFragmentInteractionListener) {
            mListener = (OnListFragmentInteractionListener) context;
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
        void onListFragmentInteraction(PaymentMethod item);
    }
}
