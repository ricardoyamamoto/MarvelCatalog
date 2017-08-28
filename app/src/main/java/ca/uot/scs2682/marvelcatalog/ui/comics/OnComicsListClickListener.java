package ca.uot.scs2682.marvelcatalog.ui.comics;

import android.support.annotation.NonNull;

import ca.uot.scs2682.marvelcatalog.data.ComicBook;

/**
 * Created by ricardohidekiyamamoto on 2017-03-25.
 */

public interface OnComicsListClickListener {
    void onComicBookClick(@NonNull ComicBook comicBook);
}
