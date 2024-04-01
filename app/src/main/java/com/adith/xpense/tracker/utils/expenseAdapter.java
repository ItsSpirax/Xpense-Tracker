package com.adith.xpense.tracker.utils;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.adith.xpense.tracker.*;

import androidx.recyclerview.widget.RecyclerView;
import com.adith.xpense.tracker.models.ExpensesRecyler;
import java.util.List;


public class expenseAdapter extends RecyclerView.Adapter<expenseAdapter.ViewHolder> {
    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView name;
        public TextView amount;
        public TextView date;
        public ImageView image;

        public ViewHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
            amount = itemView.findViewById(R.id.amount);
            date = itemView.findViewById(R.id.date);
            image = itemView.findViewById(R.id.image);
        }
    }
    private List<ExpensesRecyler> mExpenses;

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
        name.setText(expense.name);
        TextView amount = holder.amount;
        amount.setText(String.valueOf(expense.amount));
        TextView date = holder.date;
        date.setText(expense.date);
        ImageView image = holder.image;
        image.setImageResource(expense.image);
    }

    @Override
    public int getItemCount() {
        return mExpenses.size();
    }
}