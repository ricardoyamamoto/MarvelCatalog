package ca.uot.scs2682.marvelcatalog;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import ca.uot.scs2682.marvelcatalog.data.Character;
import ca.uot.scs2682.marvelcatalog.data.ComicBook;
import ca.uot.scs2682.marvelcatalog.ui.characters.CharacterView;
import ca.uot.scs2682.marvelcatalog.ui.characters.CharactersList;
import ca.uot.scs2682.marvelcatalog.ui.comics.ComicBookView;
import ca.uot.scs2682.marvelcatalog.ui.comics.ComicsList;
import ca.uot.scs2682.marvelcatalog.ui.search.Search;

public class AppActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.appactivity);
        ViewPager viewPager = (ViewPager) findViewById(R.id.viewPager);
        viewPager.setAdapter(new Adapter(viewPager));
        viewPager.setOffscreenPageLimit(10);

    }

    private static final class Page{
        private final int layoutId;

        @NonNull
        private final String title;

        public Page(final int layoutId, @NonNull String title){
            this.layoutId = layoutId;
            this.title = title;
        }
    }


    public static final class Adapter extends PagerAdapter{


        private static final int POSITION_SEARCH = 0;
        private static final int POSITION_COMICS_LIST = 1;
        private static final int POSITION_COMIC_BOOK = 2;
        private static final int POSITION_CHARACTER = 3;



        private final List<Page> pages;

        private final LayoutInflater layoutInflater;

        private final ViewPager viewPager;

        private ComicsList comicsList;

        private CharactersList charactersList;

        private ComicBookView comicBookView;

        private CharacterView characterView;

        private Search search;


        private Adapter(ViewPager viewPager){
            Context context = viewPager.getContext();
            this.viewPager = viewPager;
            layoutInflater = LayoutInflater.from(context);
            pages = new ArrayList<>(5);
            pages.add(POSITION_SEARCH, new Page(R.layout.search, context.getString(R.string.search)));
            pages.add(POSITION_COMICS_LIST, new Page(R.layout.comics_list,context.getString(R.string.comic_book_list)));
            pages.add(POSITION_COMIC_BOOK, new Page(R.layout.comic_book, context.getString(R.string.comic_book)));
            pages.add(POSITION_CHARACTER, new Page(R.layout.character, context.getString(R.string.character)));

        }

        @Override
        public Object instantiateItem(ViewGroup container, final int position) {
            Page page = pages.get(position);
            View view = layoutInflater.inflate(page.layoutId, container, false);
            container.addView(view);

            if (view instanceof Search) {
                search = (Search) view;
                search.setAdapter(this);
            } else if(view instanceof CharacterView) {
                characterView = (CharacterView) view;
                characterView.setAdapter(this);
            } else if (view instanceof ComicsList) {
                comicsList = (ComicsList) view;
                comicsList.setAdapter(this);
            } else if (view instanceof ComicBookView) {
                comicBookView = (ComicBookView) view;
                comicBookView.setAdapter(this);
            }
            return view;
        }

        public void onOpenComicBook(ComicBook comicBook) {
            viewPager.setCurrentItem(POSITION_COMIC_BOOK);

            comicBookView.loadComicBook(comicBook);
        }

        public void onOpenCharacter(Character character) {
            viewPager.setCurrentItem(POSITION_CHARACTER);

            characterView.loadCharacter(character);
        }


        @Override
        public CharSequence getPageTitle(final int position) {
            return pages.get(position).title;
        }


        @Override
        public int getCount() {
            return pages.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public void destroyItem(final ViewGroup container, final int position,
                                final Object object) {
            container.removeView((View) object);
        }

        public void onSearch(String comicsTitle) {
            if (comicsTitle!= null) {
                viewPager.setCurrentItem(POSITION_COMICS_LIST);
                comicsList.searchComics(comicsTitle);
            }
        }
    }

}
