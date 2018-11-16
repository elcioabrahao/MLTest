package br.com.mercadolivre.mltest.ui.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import androidx.recyclerview.widget.RecyclerView;
import br.com.mercadolivre.mltest.R;


import br.com.mercadolivre.mltest.domain.model.cotas.PayerCost;
import br.com.mercadolivre.mltest.ui.fragments.CotasFragment;

public class MyCotasRecyclerViewAdapter  extends RecyclerView.Adapter<MyCotasRecyclerViewAdapter.ViewHolder> {

    private final List<PayerCost> mValues;
    private final CotasFragment.OnListFragmentInteractionListener mListener;

    public MyCotasRecyclerViewAdapter(List<PayerCost> items, CotasFragment.OnListFragmentInteractionListener listener) {
        mValues = items;
        mListener = listener;
    }

    @Override
    public MyCotasRecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_cotas, parent, false);
        return new MyCotasRecyclerViewAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final MyCotasRecyclerViewAdapter.ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        holder.mMessageView.setText(mValues.get(position).getRecommendedMessage());

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
        public final TextView mMessageView;
        public PayerCost mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mMessageView = (TextView) view.findViewById(R.id.item_recommended_message);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mMessageView.getText() + "'";
        }
    }


}
