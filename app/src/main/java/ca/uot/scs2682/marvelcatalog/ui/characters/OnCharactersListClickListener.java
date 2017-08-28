package ca.uot.scs2682.marvelcatalog.ui.characters;

import android.support.annotation.NonNull;

import ca.uot.scs2682.marvelcatalog.data.Character;

/**
 * Created by ricardohidekiyamamoto on 2017-03-27.
 */

public interface OnCharactersListClickListener {

    void onCharacterClick(@NonNull Character character);
}
