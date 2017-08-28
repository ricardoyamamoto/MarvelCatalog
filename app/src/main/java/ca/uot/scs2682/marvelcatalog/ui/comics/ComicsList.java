package ca.uot.scs2682.marvelcatalog.ui.comics;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.inputmethod.InputMethodManager;
import android.widget.LinearLayout;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import ca.uot.scs2682.marvelcatalog.AppActivity;
import ca.uot.scs2682.marvelcatalog.R;
import ca.uot.scs2682.marvelcatalog.data.ComicBook;
import ca.uot.scs2682.marvelcatalog.data.Creator;
import ca.uot.scs2682.marvelcatalog.util.DateFormater;
import ca.uot.scs2682.marvelcatalog.util.json.DownloadJsonTask;
import ca.uot.scs2682.marvelcatalog.util.json.JsonConsumer;

/**
 * Created by ricardohidekiyamamoto on 2017-03-25.
 */

public class ComicsList extends LinearLayout implements OnComicsListClickListener, JsonConsumer{

    private AppActivity.Adapter adapter;

    private final List<ComicBook> comicBookList = new ArrayList<>();

    private ComicsListAdapter comicsListAdapter;

    private DownloadJsonTask downloadJsonTask;

    public ComicsList(final Context context){
        this(context,null,0);
    }

    public ComicsList(final Context context, final AttributeSet attrs){
        this(context,attrs,0);
    }

    public ComicsList(final Context context, final AttributeSet attrs, final int defStyleAttr){
        super(context,attrs,defStyleAttr);
        comicsListAdapter = new ComicsListAdapter(context, this);

    }

    public void searchComics(String comicsTitle){
        String url = "https://gateway.marvel.com:443/v1/public/comics?" +
                "titleStartsWith=" + comicsTitle + "&apikey=" + DownloadJsonTask.PUBLIC_KEY +
                "&ts=1&hash=" + DownloadJsonTask.MD5("1" + DownloadJsonTask.PRIVATE_KEY + DownloadJsonTask.PUBLIC_KEY);
        downloadJsonTask = new DownloadJsonTask(this);
        downloadJsonTask.execute(url);
    }

    @Override
    public void onComicBookClick(@NonNull ComicBook comicBook) {
        if (adapter != null ){
            adapter.onOpenComicBook(comicBook);
        }
    }


    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.comicsRecyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(comicsListAdapter);

    }

    public void setAdapter(AppActivity.Adapter adapter) {
        this.adapter = adapter;
    }



    @Override
    public void updateData(JSONArray jsonArray){
        // hide the keyboard
        InputMethodManager inputMethodManager
                =  (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(this.getWindowToken(), 0);
        comicsListAdapter.comicBookList.clear();
        comicsListAdapter.notifyDataSetChanged();
        if (jsonArray != null) {
            if (jsonArray.length() == 0 ){
                findViewById(R.id.noComicsFound).setVisibility(VISIBLE);
            } else {
                findViewById(R.id.noComicsFound).setVisibility(GONE);
            }
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.optJSONObject(i);
                int id = jsonObject.optInt("id");
                String title = jsonObject.optString("title");
                String description = jsonObject.optString("description");
                if (description == "null") {
                    description = "No description available.";
                }
                JSONArray dateArray = jsonObject.optJSONArray("dates");
                JSONObject dateObject = dateArray.optJSONObject(0);
                String releaseDate = "No release date available.";
                if (dateObject != null) {
                    releaseDate = DateFormater.formatDate(dateObject.optString("date"));
                }
                JSONArray priceArray = jsonObject.optJSONArray("prices");
                JSONObject priceObject = priceArray.optJSONObject(0);
                String price = "No price information available.";
                if (priceObject != null){
                    price = priceObject.optString("price");
                }
                JSONArray imageArray = jsonObject.optJSONArray("images");
                JSONObject imageObject = imageArray.optJSONObject(0);
                String imageUrl = "";
                if (imageObject != null) {
                    String thumbnail_path = imageObject.optString("path");
                    String thumbnail_extension = imageObject.optString("extension");
                    imageUrl = thumbnail_path + "." + thumbnail_extension;
                }
                ArrayList<Creator> creators = new ArrayList<Creator>();
                JSONObject creatorsObject = jsonObject.optJSONObject("creators");
                if (creatorsObject != null) {
                    JSONArray creatorsArray = creatorsObject.optJSONArray("items");
                    if (creatorsArray != null){
                        for (int j = 0; j < creatorsArray.length(); j++){
                            JSONObject creatorItemObject = creatorsArray.optJSONObject(j);
                            if (creatorItemObject != null) {
                                String name = creatorItemObject.optString("name");
                                String role = creatorItemObject.optString("role");
                                Creator creator = new Creator.Builder()
                                        .setName(name)
                                        .setRole(role)
                                        .build();
                                creators.add(creator);
                            }
                        }
                    }
                }
                String issueNumber = jsonObject.optString("issueNumber");
                if (issueNumber == null){
                    issueNumber = "No available information.";
                }
                String pageCount = jsonObject.optString("pageCount");
                if (pageCount == null){
                    pageCount = "No available information.";
                }
                JSONArray urlArray = jsonObject.optJSONArray("urls");
                String moreInfoUrl="";
                if (urlArray != null) {
                    JSONObject urlObject = urlArray.optJSONObject(0);
                    moreInfoUrl = urlObject.optString("url");
                }
                ComicBook comicBook = new ComicBook.Builder()
                                                    .setId(id)
                                                    .setTitle(title)
                                                    .setReleaseDate(releaseDate)
                                                    .setDescription(description)
                                                    .setImageUrl(imageUrl)
                                                    .setPrice(price)
                                                    .setCreators(creators)
                                                    .setPageCount(pageCount)
                                                    .setIssueNumber(issueNumber)
                                                    .setMoreInfo(moreInfoUrl)
                                                    .build();
                comicsListAdapter.comicBookList.add(comicBook);
                comicsListAdapter.notifyItemInserted(comicsListAdapter.comicBookList.size() - 1);

            }
        }
    }


}
