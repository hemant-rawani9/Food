package com.example.foodapp;

import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.HistoryViewHolder> {

    private Cursor cursor;

    public HistoryAdapter(Cursor cursor) {
        this.cursor = cursor;
    }

    @NonNull
    @Override
    public HistoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.history_item, parent, false);
        return new HistoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HistoryViewHolder holder, int position) {
        if (cursor.moveToPosition(position)) {
            String date = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COL_ORDER_DATE));
            String total = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COL_ORDER_TOTAL));
            String items = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COL_ORDER_ITEMS));

            holder.tvDate.setText(date);
            holder.tvTotal.setText(total);
            holder.tvItems.setText(items);
        }
    }

    @Override
    public int getItemCount() {
        return cursor != null ? cursor.getCount() : 0;
    }

    public void swapCursor(Cursor newCursor) {
        if (cursor != null) {
            cursor.close();
        }
        cursor = newCursor;
        notifyDataSetChanged();
    }

    public static class HistoryViewHolder extends RecyclerView.ViewHolder {
        TextView tvDate, tvTotal, tvItems;

        public HistoryViewHolder(@NonNull View itemView) {
            super(itemView);
            tvDate = itemView.findViewById(R.id.tv_history_date);
            tvTotal = itemView.findViewById(R.id.tv_history_total);
            tvItems = itemView.findViewById(R.id.tv_history_items);
        }
    }
}
