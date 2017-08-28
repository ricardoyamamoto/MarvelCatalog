package ca.uot.scs2682.marvelcatalog.ui.creators;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import ca.uot.scs2682.marvelcatalog.R;
import ca.uot.scs2682.marvelcatalog.data.Creator;

/**
 * Created by ricardohidekiyamamoto on 2017-04-02.
 */

public class CreatorsListCellViewHolder extends RecyclerView.ViewHolder {

    private final TextView creatorName;

    private final TextView creatorRole;


    public CreatorsListCellViewHolder(View itemView){
        super(itemView);
        this.creatorName = (TextView) itemView.findViewById(R.id.creatorName);
        this.creatorRole = (TextView) itemView.findViewById(R.id.creatorRole);
    }


    public void update(Creator creator){
        creatorName.setText(creator.name);
        creatorRole.setText(creator.role);
    }


}
