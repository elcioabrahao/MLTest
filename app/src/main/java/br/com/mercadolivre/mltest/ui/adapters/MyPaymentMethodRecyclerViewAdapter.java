package br.com.mercadolivre.mltest.ui.adapters;

import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import br.com.mercadolivre.mltest.R;
import br.com.mercadolivre.mltest.domain.model.paymentmethod.PaymentMethod;
import br.com.mercadolivre.mltest.ui.fragments.PaymentMethodFragment.OnListFragmentInteractionListener;

import java.util.List;

public class MyPaymentMethodRecyclerViewAdapter extends RecyclerView.Adapter<MyPaymentMethodRecyclerViewAdapter.ViewHolder> {

    private final List<PaymentMethod> mValues;
    private final OnListFragmentInteractionListener mListener;

    public MyPaymentMethodRecyclerViewAdapter(List<PaymentMethod> items, OnListFragmentInteractionListener listener) {
        mValues = items;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_paymentmethod, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        holder.mNameView.setText(mValues.get(position).getName());
        Picasso.get().load(mValues.get(position).getSecureThumbnail()).into(holder.mImageView) ;

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    mListener.onListFragmentInteraction(holder.mItem);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mNameView;
        public final ImageView mImageView;
        public PaymentMethod mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mNameView = (TextView) view.findViewById(R.id.item_name);
            mImageView = (ImageView) view.findViewById(R.id.item_image);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mNameView.getText() + "'";
        }
    }


}
