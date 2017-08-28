package ca.uot.scs2682.marvelcatalog.ui.characters;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import ca.uot.scs2682.marvelcatalog.AppActivity;
import ca.uot.scs2682.marvelcatalog.R;
import ca.uot.scs2682.marvelcatalog.data.Character;

/**
 * Created by ricardohidekiyamamoto on 2017-03-27.
 */

public class CharacterView  extends LinearLayout{

    private AppActivity.Adapter adapter;

    private Context context;

    private ImageView image;

    private TextView name;

    private TextView description;

    private int id;


    public CharacterView(Context context) {
        this(context, null, 0);
    }

    public CharacterView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CharacterView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
    }



    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

        image = (ImageView) findViewById(R.id.characterImage);
        name = (TextView) findViewById(R.id.characterName);
        description = (TextView) findViewById(R.id.characterDescription);
    }

    public void setAdapter(AppActivity.Adapter adapter) {
        this.adapter = adapter;
    }

    public void loadCharacter(Character character){
        // hide the keyboard
        InputMethodManager inputMethodManager
                =  (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(this.getWindowToken(), 0);

        if (character != null){
            name.setText(character.name);
            description.setText(character.description);
            Glide.with(context)
                    .load(character.imageUrl)
                    .override(300,400)
                    .into(image);
        }

    }

}
