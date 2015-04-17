package net.talayhan.android.vibeproject.Util;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import net.talayhan.android.vibeproject.Controller.ChartActivity;
import net.talayhan.android.vibeproject.R;

/**
 * Created by talayhan on 06/04/15.
 */
public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {
    private boolean isStaggered;
    private int itemLayoutRes;
    private String[] mDataset;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener  {
        // each data item is just a string in this case
        public TextView mTextView;
        public ViewHolder(View itemView, TextView textView) {
            super(itemView);
            itemView.setOnClickListener(this);
            mTextView = textView;
        }

        @Override
        public void onClick(View v) {
            Toast.makeText(v.getContext(), "ViewHolder Clicked, Position = " + getPosition(), Toast.LENGTH_SHORT).show();
            Intent i = new Intent(v.getContext(), ChartActivity.class);

        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public MyAdapter(String[] myDataset) {
        mDataset = myDataset;
    }

    public MyAdapter(String[] myDataset, int itemLayoutRes, boolean isStaggered) {
        this.mDataset = myDataset;
        this.itemLayoutRes = itemLayoutRes;
        this.isStaggered = isStaggered;

    }

    // Create new views (invoked by the layout manager)
    @Override
    public MyAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                   int viewType) {
        /* create a new view */
        View v = LayoutInflater.from(parent.getContext())
                .inflate(android.R.layout.simple_list_item_1, parent, false);
        TextView textView = (TextView) v.findViewById(android.R.id.text1);
        return new ViewHolder(v, textView);

        // set the view's size, margins, paddings and layout parameters

        //ViewHolder vh = new ViewHolder((TextView) v);
        //return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        holder.mTextView.setBackgroundColor(backgroundColor(position, holder));
        holder.mTextView.setText(mDataset[position]);

    }

    private int backgroundColor(int position, ViewHolder holder) {
        if (isStaggered) {
            int val = (int) (Math.random() * 55 + 200);
            return Color.rgb(val, val, val);
        } else {
            return holder.itemView.getResources().getColor(getBackgroundColorRes(position, itemLayoutRes));
        }
    }

    public static int getBackgroundColorRes(int position, int itemLayoutRes) {
        if (itemLayoutRes == R.layout.item) {
            return position % 2 == 0 ? R.color.even : R.color.odd;
        } else {
            return (position % 4 == 0 || position % 4 == 3) ? R.color.even : R.color.odd;
        }
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset.length;
    }
}
