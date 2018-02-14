package com.copypastapublishing.bookarchive;


        import android.app.DownloadManager;
        import android.content.Context;
        import android.content.Intent;
        import android.media.Image;
        import android.net.Uri;
        import android.os.Environment;
        import android.support.v7.widget.RecyclerView;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.Button;
        import android.widget.ImageButton;
        import android.widget.ImageView;
        import android.widget.LinearLayout;
        import android.widget.TextView;
        import android.widget.Toast;


        import java.util.List;

/**
 * Created by EKENE on 7/23/2017.
 */

public class DevelopersAdapter extends RecyclerView.Adapter<DevelopersAdapter.ViewHolder> {
    private DownloadManager downloadManager;


    public static final String KEY_TITLE = "title";
    public static final String KEY_TEXT = "text";
    public static final String KEY_EPUB = "epub";
    public static final String KEY_ID = "id";
    public static final String KEY_AUTHOR = "author";

    // we define a list from the DevelopersList java class

    private List<DevelopersList> developersLists;
    private Context context;

    public DevelopersAdapter(List<DevelopersList> developersLists, Context context) {

        // generate constructors to initialise the List and Context objects

        this.developersLists = developersLists;
        this.context = context;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // this method will be called whenever our ViewHolder is created
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.developers_list, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {

        // this method will bind the data to the ViewHolder from whence it'll be shown to other Views

        final DevelopersList developersList = developersLists.get(position);
        holder.title.setText(developersList.getTitle());
        holder.author.setText(developersList.getAuthor());
        holder.id.setText(developersList.getId());
        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DevelopersList developersList1 = developersLists.get(position);
                Intent skipIntent = new Intent(v.getContext(), ProfileActivity.class);
                skipIntent.putExtra(KEY_TITLE, developersList1.getTitle());
                skipIntent.putExtra(KEY_TEXT, developersList1.getText());
                skipIntent.putExtra(KEY_EPUB, developersList1.getEpub());
                skipIntent.putExtra(KEY_ID, developersList1.getId());
                skipIntent.putExtra(KEY_AUTHOR, developersList1.getAuthor());

                v.getContext().startActivity(skipIntent);
            }
        });


    }

    @Override

    //return the size of the listItems (developersList)

    public int getItemCount() {
        return developersLists.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder  {

        // define the View objects

        public TextView title;
        public TextView author;
        public TextView id;

        public LinearLayout linearLayout;


        public ViewHolder(View itemView) {
            super(itemView);

            // initialize the View objects
            author= (TextView) itemView.findViewById(R.id.author);
            title = (TextView) itemView.findViewById(R.id.title);
            id = (TextView) itemView.findViewById(R.id.id);

            linearLayout = (LinearLayout) itemView.findViewById(R.id.linearLayout);
        }

    }
}
