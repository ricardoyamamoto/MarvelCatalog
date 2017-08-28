package ca.uot.scs2682.marvelcatalog.ui.characters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import ca.uot.scs2682.marvelcatalog.R;
import ca.uot.scs2682.marvelcatalog.data.Character;

/**
 * Created by ricardohidekiyamamoto on 2017-03-27.
 */

public class CharactersListAdapter extends RecyclerView.Adapter<CharactersListCellViewHolder> {

    public final List<Character> characters = new ArrayList<>();

    private final LayoutInflater layoutInflater;

    private final OnCharactersListClickListener onCharactersListClickListener;

    private Context context;

    public CharactersListAdapter(Context context, OnCharactersListClickListener onCharactersListClickListener){
        layoutInflater = LayoutInflater.from(context);
        this.onCharactersListClickListener = onCharactersListClickListener;
        this.context = context;
        setHasStableIds(true);
    }

    @Override
    public CharactersListCellViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.characters_list_cell, parent, false);
        return new CharactersListCellViewHolder(view, onCharactersListClickListener);
    }

    @Override
    public void onBindViewHolder(CharactersListCellViewHolder holder, int position) {
        Character character = characters.get(position);
        holder.update(character, context);
    }

    @Override
    public int getItemCount() {
        return characters.size();
    }

    @Override
    public long getItemId(final int position) {
        return position;
    }


}
