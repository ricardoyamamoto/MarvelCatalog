package ca.uot.scs2682.marvelcatalog.ui.search;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;

import ca.uot.scs2682.marvelcatalog.AppActivity;
import ca.uot.scs2682.marvelcatalog.R;

/**
 * Created by ricardohidekiyamamoto on 2017-03-27.
 */

public class Search extends LinearLayout{

    private ImageView marvelLogo;

    private EditText searchTerm;

    private ImageView marvelBanner;

    private Button searchButton;

    private AppActivity.Adapter adapter;

    public Search(Context context) {
        this(context, null, 0);
    }

    public Search(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public Search(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

        marvelLogo = (ImageView) findViewById(R.id.marvelLogo);
        searchTerm = (EditText) findViewById(R.id.searchTerm);
        marvelBanner = (ImageView) findViewById(R.id.marvelBanner);
        findViewById(R.id.searchButton).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                adapter.onSearch(searchTerm.getText().toString());
            }
        });
        Glide.with(getContext())
                .load("https://a.dilcdn.com/bl/wp-content/uploads/sites/28/2016/10/57e190455b4bc.jpg")
                .into(marvelBanner);
        Glide.with(getContext())
                .load("http://vignette1.wikia.nocookie.net/logopedia/images/7/72/Marvel-logo.jpg/revision/latest?cb=20110818023639")
                .into(marvelLogo);
        // show the keyboard
        InputMethodManager inputMethodManager
                =  (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY,0);

    }

    public void setAdapter(AppActivity.Adapter adapter) {
        this.adapter = adapter;
    }
}
