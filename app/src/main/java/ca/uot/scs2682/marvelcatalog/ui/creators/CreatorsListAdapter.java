package ca.uot.scs2682.marvelcatalog.ui.creators;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import ca.uot.scs2682.marvelcatalog.R;
import ca.uot.scs2682.marvelcatalog.data.Creator;

/**
 * Created by ricardohidekiyamamoto on 2017-04-02.
 */

public class CreatorsListAdapter extends RecyclerView.Adapter<CreatorsListCellViewHolder> {


    private final LayoutInflater layoutInflater;

    public List<Creator> creatorsList = new ArrayList<>();

    public CreatorsListAdapter(Context context){
        layoutInflater = LayoutInflater.from(context);
        setHasStableIds(true);
    }


    @Override
    public CreatorsListCellViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.creators_list_cell, parent, false);
        return new CreatorsListCellViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CreatorsListCellViewHolder holder, int position) {
        Creator creator = creatorsList.get(position);
        holder.update(creator);
    }

    @Override
    public int getItemCount() {
        return creatorsList.size();
    }

    @Override
    public long getItemId(final int position) {
        return position;
    }
}
