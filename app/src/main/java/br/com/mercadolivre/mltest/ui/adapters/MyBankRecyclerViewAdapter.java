package br.com.mercadolivre.mltest.ui.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import androidx.recyclerview.widget.RecyclerView;
import br.com.mercadolivre.mltest.R;
import br.com.mercadolivre.mltest.domain.model.bank.Bank;
import br.com.mercadolivre.mltest.ui.fragments.BankFragment;


public class MyBankRecyclerViewAdapter extends RecyclerView.Adapter<MyBankRecyclerViewAdapter.ViewHolder> {

    private final List<Bank> mValues;
    private final BankFragment.OnListFragmentInteractionListener mListener;

    public MyBankRecyclerViewAdapter(List<Bank> items, BankFragment.OnListFragmentInteractionListener listener) {
        mValues = items;
        mListener = listener;
    }

    @Override
    public MyBankRecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_bank, parent, false);
        return new MyBankRecyclerViewAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final MyBankRecyclerViewAdapter.ViewHolder holder, int position) {
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
        public Bank mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mNameView = (TextView) view.findViewById(R.id.item_name_bank);
            mImageView = (ImageView) view.findViewById(R.id.item_image_bank);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mNameView.getText() + "'";
        }
    }


}
