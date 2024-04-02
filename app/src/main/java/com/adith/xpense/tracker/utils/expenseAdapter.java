package com.adith.xpense.tracker.utils;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.adith.xpense.tracker.R;
import com.adith.xpense.tracker.models.ExpensesRecyler;

import java.util.List;


public class expenseAdapter extends RecyclerView.Adapter<expenseAdapter.ViewHolder> {
    private final List<ExpensesRecyler> mExpenses;

    public expenseAdapter(List<ExpensesRecyler> expenses) {
        mExpenses = expenses;
    }

    @Override
    public expenseAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View expenseView = inflater.inflate(R.layout.expense_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(expenseView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(expenseAdapter.ViewHolder holder, int position) {
        ExpensesRecyler expense = mExpenses.get(position);

        TextView name = holder.name;
        TextView amount = holder.amount;
        TextView date = holder.date;
        ImageView image = holder.image;

        name.setText(expense.name);
        amount.setText(String.valueOf(expense.amount));
        date.setText(expense.date);
        image.setImageResource(expense.image);
    }

    @Override
    public int getItemCount() {
        return mExpenses.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView name, amount, date;
        public ImageView image;

        public ViewHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
            amount = itemView.findViewById(R.id.amount);
            date = itemView.findViewById(R.id.date);
            image = itemView.findViewById(R.id.image);
        }
    }
}