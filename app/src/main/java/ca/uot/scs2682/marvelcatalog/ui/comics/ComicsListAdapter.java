package ca.uot.scs2682.marvelcatalog.ui.comics;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import ca.uot.scs2682.marvelcatalog.R;
import ca.uot.scs2682.marvelcatalog.data.ComicBook;

/**
 * Created by ricardohidekiyamamoto on 2017-03-25.
 */

public class ComicsListAdapter extends RecyclerView.Adapter<ComicsListCellViewHolder> {

    final List<ComicBook> comicBookList = new ArrayList<>();

    private final LayoutInflater layoutInflater;

    private final OnComicsListClickListener onComicsListClickListener;

    private Context context;

    ComicsListAdapter(Context context, OnComicsListClickListener onComicsListClickListener){
        layoutInflater = LayoutInflater.from(context);
        this.onComicsListClickListener = onComicsListClickListener;
        this.context = context;
        setHasStableIds(true);
    }


    @Override
    public ComicsListCellViewHolder onCreateViewHolder(final ViewGroup parent, final int viewType) {
        View view = layoutInflater.inflate(R.layout.comics_list_cell, parent, false);
        return new ComicsListCellViewHolder(view, onComicsListClickListener);
    }

    @Override
    public void onBindViewHolder(final ComicsListCellViewHolder holder, final int position) {
        ComicBook comicBook = comicBookList.get(position);
        holder.update(comicBook, context);
    }

    @Override
    public int getItemCount() {
        return comicBookList.size();
    }

    @Override
    public long getItemId(final int position) {
        return position;
    }
}
