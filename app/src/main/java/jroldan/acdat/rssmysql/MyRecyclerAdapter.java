package jroldan.acdat.rssmysql;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class
MyRecyclerAdapter extends RecyclerView.Adapter<MyRecyclerAdapter.MyViewHolder> {
    private List<Site> sitesList;
    private LayoutInflater inflater;
    public MyRecyclerAdapter(Context context) {
        this.sitesList = new ArrayList<Site>();
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View rootView = inflater.inflate(R.layout.singleitem, parent, false);
        return new MyViewHolder(rootView);
    }
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Site feed = sitesList.get(position);
        //Pass the values of feeds object to Views
        holder.name.setText(feed.getName());
        holder.link.setText(feed.getLink());
    }
    @Override
    public int getItemCount() {
        return sitesList.size();
    }
    public class MyViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.name_view) TextView name;
        @BindView(R.id.link_view) TextView link;
        //private TextView name, link;
        public MyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            //name = (TextView) itemView.findViewById(R.id.name_view);
            //link = (TextView) itemView.findViewById(R.id.link_view);
        }
    }
    public void set(ArrayList<Site> sitesList) {
        //set all the sites
        this.sitesList = sitesList;
        notifyItemRangeChanged(0, sitesList.size() - 1);
    }
    public Site getAt(int position) {
        //get the site in the position
        return this.sitesList.get(position);
    }
    public void add(Site site) {
        //add a site
        this.sitesList.add(site);
        notifyItemChanged(sitesList.size() - 1);
        notifyItemRangeChanged(0, sitesList.size() - 1);
    }
    public void modifyAt(Site site, int position) {
        //modify a site in the position
        this.sitesList.set(position, site);
        notifyItemChanged(position);
    }
    public void removeAt(int position) {
        //delete a site in the position
        this.sitesList.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(0, sitesList.size() - 1);
    }
}
