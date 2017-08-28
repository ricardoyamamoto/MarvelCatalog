package ca.uot.scs2682.marvelcatalog.data;

import android.support.annotation.NonNull;

/**
 * Created by ricardohidekiyamamoto on 2017-04-02.
 */

public class Creator {


    public static class Builder {
        @NonNull
        private String name;
        @NonNull
        private String role;

        public Builder(){
            this.name = "";
            this.role = "";
        }

        public Builder setName(String name){
            this.name = name;
            return this;
        }

        public Builder setRole(String role){
            this.role = role;
            return this;
        }

        public Creator build(){
            return new Creator(this);
        }

    }

    @NonNull
    public final String name;

    @NonNull
    public final String role;

    public Creator(@NonNull Builder builder){
        this.name = builder.name;
        this.role = builder.role;
    }


}
