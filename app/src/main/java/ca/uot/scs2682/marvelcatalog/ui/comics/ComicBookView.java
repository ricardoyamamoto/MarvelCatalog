package ca.uot.scs2682.marvelcatalog.ui.comics;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import org.json.JSONArray;
import org.json.JSONObject;

import ca.uot.scs2682.marvelcatalog.AppActivity;
import ca.uot.scs2682.marvelcatalog.R;
import ca.uot.scs2682.marvelcatalog.data.Character;
import ca.uot.scs2682.marvelcatalog.data.ComicBook;
import ca.uot.scs2682.marvelcatalog.ui.characters.CharactersListAdapter;
import ca.uot.scs2682.marvelcatalog.ui.characters.OnCharactersListClickListener;
import ca.uot.scs2682.marvelcatalog.ui.creators.CreatorsListAdapter;
import ca.uot.scs2682.marvelcatalog.util.json.DownloadJsonTask;
import ca.uot.scs2682.marvelcatalog.util.json.JsonConsumer;

/**
 * Created by ricardohidekiyamamoto on 2017-03-26.
 */

public class ComicBookView extends LinearLayout implements OnCharactersListClickListener, JsonConsumer {

    AppActivity.Adapter adapter;

    private TextView title;
    private TextView releaseDate;
    private TextView description;
    private Button moreInfo;
    private TextView price;
    private ImageView image;
    private TextView issueNumber;
    private TextView pageCount;

    private Context context;

    private final CharactersListAdapter charactersListAdapter;
    private DownloadJsonTask downloadJsonTask;

    private final CreatorsListAdapter creatorsListAdapter;

    public ComicBookView(final Context context) {
        this(context, null, 0);
    }

    public ComicBookView(final Context context, final AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ComicBookView(final Context context, final AttributeSet attrs, final int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        charactersListAdapter = new CharactersListAdapter(context, this);
        creatorsListAdapter = new CreatorsListAdapter(context);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        title = (TextView) findViewById(R.id.comicBookTitle);
        releaseDate = (TextView) findViewById(R.id.comicBookReleaseDate);
        description = (TextView) findViewById(R.id.comicBookDescription);
        image = (ImageView) findViewById(R.id.comicBookImage);
        price = (TextView) findViewById(R.id.price);
        moreInfo = (Button) findViewById(R.id.moreInfo);
        issueNumber = (TextView) findViewById(R.id.issueNumber);
        pageCount = (TextView) findViewById(R.id.pageCount);
        RecyclerView charactersView = (RecyclerView) findViewById(R.id.charactersList);
        charactersView.setHasFixedSize(true);
        charactersView.setLayoutManager(new LinearLayoutManager(getContext()));
        charactersView.setItemAnimator(new DefaultItemAnimator());
        charactersView.setAdapter(charactersListAdapter);

        RecyclerView creatorsView = (RecyclerView) findViewById(R.id.creatorsList);
        creatorsView.setLayoutManager(new LinearLayoutManager(getContext()));
        creatorsView.setItemAnimator(new DefaultItemAnimator());
        creatorsView.setHasFixedSize(true);
        creatorsView.setAdapter(creatorsListAdapter);

    }

    public void setAdapter(AppActivity.Adapter adapter) {
        this.adapter = adapter;
    }

    public void loadComicBook(final ComicBook comicBook){
        // hide the keyboard
        InputMethodManager inputMethodManager
                =  (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(this.getWindowToken(), 0);

        if (comicBook != null){
            title.setText(comicBook.title);
            releaseDate.setText(comicBook.releaseDate);
            description.setText(comicBook.description);
            price.setText(comicBook.price);
            issueNumber.setText(comicBook.issueNumber);
            pageCount.setText(comicBook.pageCount);
            Glide.with(context)
                    .load(comicBook.imageUrl)
                    .override(300,400)
                    .into(image);
            creatorsListAdapter.creatorsList = comicBook.creators;
            creatorsListAdapter.notifyDataSetChanged();
            if (comicBook.creators != null && comicBook.creators.size() > 0){
                findViewById(R.id.noCreatorsFound).setVisibility(GONE);
            } else {
                findViewById(R.id.noCreatorsFound).setVisibility(VISIBLE);
            }

            moreInfo.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(comicBook.moreInfo));
                    context.startActivity(intent);
                }
            });
            if (comicBook.moreInfo == null || comicBook.moreInfo.equals("")){
                moreInfo.setEnabled(false);
            } else {
                moreInfo.setEnabled(true);
            }
            ((ScrollView)findViewById(R.id.comicBookScroll)).fullScroll(ScrollView.FOCUS_UP);
            searchCharactersByComic(comicBook.id);
        }
    }

    @Override
    public void updateData(JSONArray jsonArray) {
        // hide the keyboard
        InputMethodManager inputMethodManager
                =  (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(this.getWindowToken(), 0);

        if (jsonArray != null) {
            if (jsonArray.length() > 0){
                findViewById(R.id.noCharactersFound).setVisibility(GONE);
            } else {
                findViewById(R.id.noCharactersFound).setVisibility(VISIBLE);
            }
            charactersListAdapter.characters.clear();
            charactersListAdapter.notifyDataSetChanged();
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.optJSONObject(i);
                int id = jsonObject.optInt("id");
                String name = jsonObject.optString("name");
                String description = jsonObject.optString("description");
                if (description.equals("null") || description.equals("")) {
                    description = "No description available";
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


    public void searchCharactersByComic(int comicBookId){
        downloadJsonTask = new DownloadJsonTask(this);
        String url = "https://gateway.marvel.com:443/v1/public/comics/" + comicBookId +
                "/characters?" +
                "&apikey=" + DownloadJsonTask.PUBLIC_KEY +
                "&ts=1&hash=" + DownloadJsonTask.MD5("1" + DownloadJsonTask.PRIVATE_KEY + DownloadJsonTask.PUBLIC_KEY);
        downloadJsonTask.execute(url);
    }

}
