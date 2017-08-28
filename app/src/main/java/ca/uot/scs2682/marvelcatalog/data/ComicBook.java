package ca.uot.scs2682.marvelcatalog.data;

import android.support.annotation.NonNull;

import java.util.ArrayList;

/**
 * Created by ricardohidekiyamamoto on 2017-03-25.
 */

public class ComicBook {

    public static class Builder{

        @NonNull
        public int id;

        @NonNull
        public String title;

        @NonNull
        public String releaseDate;

        @NonNull
        public String description;

        @NonNull
        public String imageUrl;

        public String price;

        public String moreInfo;

        public String issueNumber;

        public String pageCount;

        public ArrayList<Creator> creators;

        public Builder(){
            this.id = 0;
            this.title = "";
            this.releaseDate = "";
            this.description = "";
            this.imageUrl = "";
            this.price = "";
            this.moreInfo = "";
            this.issueNumber = "";
            this.pageCount = "";
            this.creators = new ArrayList<Creator>();
        }

        public Builder setId(int id){
            this.id = id;
            return this;
        }

        public Builder setTitle(String title){
            this.title = title;
            return this;
        }

        public Builder setReleaseDate(String releaseDate){
            this.releaseDate = releaseDate;
            return this;
        }

        public Builder setDescription(String description){
            this.description = description;
            return this;
        }

        public Builder setImageUrl(String imageUrl){
            this.imageUrl = imageUrl;
            return this;
        }

        public Builder setMoreInfo(String moreInfo){
            this.moreInfo = moreInfo;
            return this;
        }

        public Builder setPrice(String price){
            this.price = price;
            return this;
        }

        public Builder setCreators(ArrayList<Creator> creators){
            this.creators = creators;
            return this;
        }

        public Builder setIssueNumber(String issueNumber){
            this.issueNumber = issueNumber;
            return this;
        }

        public Builder setPageCount(String pageCount){
            this.pageCount = pageCount;
            return this;
        }


        public ComicBook build() {
            return new ComicBook(this);
        }

    }

    @NonNull
    public final int id;

    @NonNull
    public final String title;

    @NonNull
    public final String releaseDate;

    @NonNull
    public final String description;

    @NonNull
    public final String imageUrl;

    @NonNull
    public final String price;

    @NonNull
    public final String moreInfo;

    @NonNull
    public final String issueNumber;

    @NonNull
    public final String pageCount;

    @NonNull
    public final ArrayList<Creator> creators;

    public ComicBook(@NonNull Builder builder){
        this.id = builder.id;
        this.title = builder.title;
        this.releaseDate = builder.releaseDate;
        this.description = builder.description;
        this.imageUrl = builder.imageUrl;
        this.price = builder.price;
        this.creators = builder.creators;
        this.moreInfo = builder.moreInfo;
        this.issueNumber = builder.issueNumber;
        this.pageCount = builder.pageCount;

    }


}
