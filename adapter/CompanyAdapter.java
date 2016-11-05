package kz.eugales.re4.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import kz.eugales.re4.R;
import kz.eugales.re4.model.Phone;
import kz.eugales.re4.CompanyFragment.OnListFragmentInteractionListener;
import kz.eugales.re4.model.Company;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link Company} and makes a call to the
 * specified {@link OnListFragmentInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class CompanyAdapter extends RecyclerView.Adapter<CompanyAdapter.ViewHolder> {

    public ArrayList<Company> COMPANIES;
    public HashMap<Integer,Phone> PHONES;
    private final OnListFragmentInteractionListener mListener;

    public CompanyAdapter(OnListFragmentInteractionListener listener) {

        COMPANIES = new ArrayList<>();
        PHONES = new HashMap<>();
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_company, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mCompany = COMPANIES.get(position);
        //holder.mImageView.setImageBitmap(holder.mCompany.getPhotoBitmap());
        holder.mView.setBackgroundResource(R.drawable.ripple_effect);
        holder.mSubjectView.setText(holder.mCompany.getCompanySubject());
        holder.mNameView.setText(holder.mCompany.getCompanyName());


        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mListener.onListFragmentInteraction(holder.mCompany);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return COMPANIES.size();
    }

    public void addCompanies(List<Company> companies){
        COMPANIES.addAll(companies);
        notifyDataSetChanged();
    }

    public void addPhone(Phone phone){
        PHONES.put(phone.getCompanyId(), phone);
        //notifyDataSetChanged();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final ImageView mImageView;
        public final TextView mSubjectView;
        public final TextView mNameView;
        public Company mCompany;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mImageView = (ImageView) view.findViewById(R.id.cImage);
            mSubjectView = (TextView) view.findViewById(R.id.cSubject);
            mNameView = (TextView) view.findViewById(R.id.cName);
        }
    }


}
