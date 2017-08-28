package ca.uot.scs2682.marvelcatalog.ui.comics;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import ca.uot.scs2682.marvelcatalog.R;
import ca.uot.scs2682.marvelcatalog.data.ComicBook;

/**
 * Created by ricardohidekiyamamoto on 2017-03-25.
 */

class ComicsListCellViewHolder extends RecyclerView.ViewHolder {



    private final ImageView image;

    private final TextView title;

    private final TextView description;

    private final TextView releaseDate;

    private ComicBook comicBook;


    public ComicsListCellViewHolder(View itemView, final OnComicsListClickListener onComicsListClickListener) {
        super(itemView);
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onComicsListClickListener != null) {
                    onComicsListClickListener.onComicBookClick(comicBook);
                }
            }
        });
        image = (ImageView) itemView.findViewById(R.id.comicsCellImage);
        releaseDate = (TextView) itemView.findViewById(R.id.comicsCellDate);
        title = (TextView) itemView.findViewById(R.id.comicsCellTitle);
        description = (TextView) itemView.findViewById(R.id.comicsCellDescription);

    }

    public void update(ComicBook comicBook, Context context){
        this.comicBook = comicBook;

        Glide.with(context)
                .load(comicBook.imageUrl)
                .override(40,100)
                .into(image);
        title.setText(comicBook.title);
        releaseDate.setText(comicBook.releaseDate);
        description.setText(comicBook.description);


    }



}
