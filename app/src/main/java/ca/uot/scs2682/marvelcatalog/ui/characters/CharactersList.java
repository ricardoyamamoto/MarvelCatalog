package ca.uot.scs2682.marvelcatalog.ui.characters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.inputmethod.InputMethodManager;
import android.widget.LinearLayout;

import org.json.JSONArray;
import org.json.JSONObject;

import ca.uot.scs2682.marvelcatalog.AppActivity;
import ca.uot.scs2682.marvelcatalog.R;
import ca.uot.scs2682.marvelcatalog.data.Character;
import ca.uot.scs2682.marvelcatalog.util.json.DownloadJsonTask;
import ca.uot.scs2682.marvelcatalog.util.json.JsonConsumer;

/**
 * Created by ricardohidekiyamamoto on 2017-03-25.
 */

public class CharactersList extends LinearLayout implements OnCharactersListClickListener, JsonConsumer{

    private final CharactersListAdapter charactersListAdapter;
    private DownloadJsonTask downloadJsonTask;
    private AppActivity.Adapter adapter;

    public CharactersList(Context context) {
        this(context,null,0);
    }

    public CharactersList(Context context, @Nullable AttributeSet attrs) {
        this(context,attrs,0);
    }

    public CharactersList(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        charactersListAdapter = new CharactersListAdapter(context, this);

    }

    public void searchCharactersByComic(int comicBookId){
        downloadJsonTask = new DownloadJsonTask(this);
        String url = "https://gateway.marvel.com:443/v1/public/" + comicBookId +
                "/characters?" +
                "&apikey=" + DownloadJsonTask.PUBLIC_KEY +
                "&ts=1&hash=" + DownloadJsonTask.MD5("1" + DownloadJsonTask.PRIVATE_KEY + DownloadJsonTask.PUBLIC_KEY);
        downloadJsonTask.execute(url);
    }

    public void setAdapter(AppActivity.Adapter adapter) {
        this.adapter = adapter;
    }

    @Override
    public void updateData(JSONArray jsonArray) {
        // hide the keyboard
        InputMethodManager inputMethodManager
                =  (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        //inputMethodManager.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY,0);
        inputMethodManager.hideSoftInputFromWindow(this.getWindowToken(), 0);
        charactersListAdapter.characters.clear();
        charactersListAdapter.notifyDataSetChanged();
        if (jsonArray != null) {


            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.optJSONObject(i);
                int id = jsonObject.optInt("id");
                String name = jsonObject.optString("name");
                String description = jsonObject.optString("description");
                if (description == "null") {
                    description = "";
                }
                JSONObject image = jsonObject.optJSONObject("thumbnail");
                String imageUrl = "";
                if (image != null) {
                    String thumbnail_path = image.optString("path");
                    String thumbnail_extension = image.optString("extension");
                    imageUrl = thumbnail_path + "." + thumbnail_extension;
                }
                Character character = new Character.Builder()
                                                    .setId(id)
                                                    .setName(name)
                                                    .setDescription(description)
                                                    .setImageUrl(imageUrl)
                                                    .build();
                charactersListAdapter.characters.add(character);
                charactersListAdapter.notifyItemInserted(charactersListAdapter.characters.size() - 1);


            }
        }
    }

    @Override
    public void onCharacterClick(@NonNull Character character) {
        if (adapter != null ){
            adapter.onOpenCharacter(character);
        }
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.charactersRecyclerView);
        if (recyclerView != null ) {
            recyclerView.setHasFixedSize(true);
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
            recyclerView.setItemAnimator(new DefaultItemAnimator());
            recyclerView.setAdapter(charactersListAdapter);
        }
    }

}
