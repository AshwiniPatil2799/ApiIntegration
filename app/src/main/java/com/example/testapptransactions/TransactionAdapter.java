package com.example.testapptransactions;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.testapptransactions.Data.TransactionEntity;

import java.util.ArrayList;
import java.util.List;

public class TransactionAdapter extends RecyclerView.Adapter<TransactionAdapter.TransactionViewHolder> {
    private List<TransactionEntity> transactionList;

    public TransactionAdapter(List<TransactionEntity> transactionList) {
        this.transactionList = transactionList;
    }

    @NonNull
    @Override
    public TransactionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_transaction, parent, false);
        return new TransactionViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TransactionViewHolder holder, int position) {
        TransactionEntity transaction = transactionList.get(position);

        holder.dateTextView.setText(transaction.getDate());
        holder.amountTextView.setText(String.valueOf(transaction.getAmount()));
        holder.descriptionTextView.setText(transaction.getDescription());
        holder.categoryTextView.setText(transaction.getCategory());
    }

    @Override
    public int getItemCount() {
        return transactionList.size(); // Use filteredList size
    }


    public void filterList(ArrayList<TransactionEntity> filterlist) {
        transactionList = filterlist;
        // below line is to notify our adapter
        notifyDataSetChanged();
    }

    public static class TransactionViewHolder extends RecyclerView.ViewHolder {
        TextView dateTextView, amountTextView, descriptionTextView,categoryTextView;

        public TransactionViewHolder(View itemView) {
            super(itemView);
            dateTextView = itemView.findViewById(R.id.dateTextView);
            amountTextView = itemView.findViewById(R.id.amountTextView);
            descriptionTextView = itemView.findViewById(R.id.descriptionTextView);
            categoryTextView=itemView.findViewById(R.id.categoryTextView);
        }
    }
}

