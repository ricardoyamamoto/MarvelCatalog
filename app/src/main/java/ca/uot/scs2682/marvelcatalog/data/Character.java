package ca.uot.scs2682.marvelcatalog.data;

import android.support.annotation.NonNull;

/**
 * Created by ricardohidekiyamamoto on 2017-03-27.
 */

public class Character {

    public static class Builder{
        @NonNull
        private int id;

        @NonNull
        private String name;

        @NonNull
        private String description;

        @NonNull
        private String imageUrl;


        public Builder(){
            this.id = 0;
            this.name = "";
            this.description = "";
            this.imageUrl = "";
        }

        public Builder setId(int id){
            this.id = id;
            return this;
        }

        public Builder setName(String name){
            this.name = name;
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

        public Character build(){
            return new Character(this);
        }


    }


    @NonNull
    public final int id;

    @NonNull
    public final String name;

    @NonNull
    public final String description;

    @NonNull
    public final String imageUrl;

    public Character(@NonNull Builder builder){
        this.id = builder.id;
        this.name = builder.name;
        this.description = builder.description;
        this.imageUrl = builder.imageUrl;
    }

}
