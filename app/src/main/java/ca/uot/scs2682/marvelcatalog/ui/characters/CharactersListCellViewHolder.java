package ca.uot.scs2682.marvelcatalog.ui.characters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import ca.uot.scs2682.marvelcatalog.R;
import ca.uot.scs2682.marvelcatalog.data.Character;

/**
 * Created by ricardohidekiyamamoto on 2017-03-27.
 */

public class CharactersListCellViewHolder  extends RecyclerView.ViewHolder{

    private final ImageView image;

    private final TextView name;

    private final TextView description;

    private Character character;



    public CharactersListCellViewHolder(View itemView, final OnCharactersListClickListener onCharactersListClickListener) {
        super(itemView);
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onCharactersListClickListener != null) {
                    onCharactersListClickListener.onCharacterClick(character);
                }
            }
        });
        image = (ImageView) itemView.findViewById(R.id.charactersCellImage);
        name = (TextView) itemView.findViewById(R.id.charactersCellName);
        description = (TextView) itemView.findViewById(R.id.charactersCellDescription);

    }

    public void update(Character character, Context context){

        this.character = character;

        Glide.with(context)
                .load(character.imageUrl)
                .override(40,100)
                .into(image);
        name.setText(character.name);
        description.setText(character.description);


    }

}
