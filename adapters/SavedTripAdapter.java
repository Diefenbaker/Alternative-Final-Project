package ie.wit.explorewaterford.adapters;

import android.app.Dialog;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.util.List;

import ie.wit.explorewaterford.R;
import ie.wit.explorewaterford.firebase.auth.FirebaseImageLoader;
import ie.wit.explorewaterford.models.Trips;

import static ie.wit.explorewaterford.fragments.InfoFragment.adapter;
import static ie.wit.explorewaterford.fragments.InfoFragment.emailFAB;
import static ie.wit.explorewaterford.fragments.InfoFragment.emptyListImageView;
import static ie.wit.explorewaterford.fragments.InfoFragment.emptyListTextView;
import static ie.wit.explorewaterford.fragments.InfoFragment.recyclerView;
import static ie.wit.explorewaterford.main.MyTrip.bottomBar;
import static ie.wit.explorewaterford.main.MyTrip.showOnMapString;


public class SavedTripAdapter extends RecyclerView.Adapter<SavedTripAdapter.MyViewHolder> {
    private Context mContext;
    private List<Trips> tripList;


    private StorageReference mStorageRef = FirebaseStorage.getInstance().getReference();

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title, description, phoneTextView;
        public RatingBar ratingBar;
        public ImageView thumbnail, overflow;


        public MyViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.title);
            description = (TextView) view.findViewById(R.id.description);
            thumbnail = (ImageView) view.findViewById(R.id.thumbnail);
            overflow = (ImageView) view.findViewById(R.id.overflow);
            phoneTextView = (TextView) view.findViewById(R.id.phoneTextView);
            ratingBar = (RatingBar) view.findViewById(R.id.ratingBar);
        }
    }


    public SavedTripAdapter(Context mContext, List<Trips> tripList) {
        this.mContext = mContext;
        this.tripList = tripList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_plan, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {

        final FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        final DatabaseReference myDatabase = FirebaseDatabase.getInstance().getReference();
        final Trips trips = tripList.get(position);
        holder.title.setText(trips.getName());
        holder.description.setText(trips.getBriefDescription());
        holder.phoneTextView.setText("Phone: "+trips.getPhoneNumber());

        //Setting color of stars in RatingBar
        Drawable drawable = holder.ratingBar.getProgressDrawable();
        DrawableCompat.setTint(drawable, Color.BLUE);
        //Setting number of stars to display, as per rating from database
        holder.ratingBar.setNumStars(trips.getRating());


        if(trips.getThumbnail() != null){
            StorageReference path = mStorageRef.child(trips.getThumbnail());
            // loading album cover using Glide library
            Glide.with(mContext).using(new FirebaseImageLoader()).load(path).into(holder.thumbnail);
        }



        holder.overflow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                PopupMenu popup = new PopupMenu(mContext, view);
                MenuInflater inflater = popup.getMenuInflater();
                inflater.inflate(R.menu.menu_saved_trip, popup.getMenu());
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {

                            case R.id.action_remove_trip:
                                myDatabase.child("users").child(firebaseUser.getUid()).child("saved activities").child(trips.getName()).removeValue();
                                Toast.makeText(mContext, "Removed from Itinerary", Toast.LENGTH_SHORT).show();
                                tripList.remove(position);
                                adapter.notifyDataSetChanged();

                                if(tripList.isEmpty()){
                                    emailFAB.setVisibility(View.GONE);
                                    recyclerView.setVisibility(View.GONE);
                                    emptyListTextView.setVisibility(View.VISIBLE);
                                    emptyListImageView.setVisibility(View.VISIBLE);
                                }
                                return true;

                            case R.id.action_add_description:
                                final Dialog dialog = new Dialog(mContext);
                                dialog.setContentView(R.layout.more_info_dialog);

                                ImageView headerImage = (ImageView) dialog.findViewById(R.id.headerImage);
                                TextView title = (TextView) dialog.findViewById(R.id.dialogTitle);
                                RatingBar ratingBar = (RatingBar) dialog.findViewById(R.id.dialogRatingBar);
                                TextView distance = (TextView) dialog.findViewById(R.id.distance);
                                TextView price = (TextView) dialog.findViewById(R.id.price);
                                TextView address = (TextView) dialog.findViewById(R.id.address);
                                TextView website = (TextView) dialog.findViewById(R.id.website);
                                TextView phone = (TextView) dialog.findViewById(R.id.phone);
                                TextView email = (TextView) dialog.findViewById(R.id.email);
                                TextView info = (TextView) dialog.findViewById(R.id.info);
                                Button addButton = (Button) dialog.findViewById(R.id.addButton);
                                addButton.setText("Remove"); //Changing the text to reflect the action this button will now launch
                                Button mapButton = (Button) dialog.findViewById(R.id.mapButton);
                                Button closeButton = (Button) dialog.findViewById(R.id.closeButton);

                                if(trips.getThumbnail() != null){
                                    StorageReference path = mStorageRef.child(trips.getThumbnail());
                                    Log.v("TripAdapter","Path: "+path);
                                    // loading album cover using Glide library
                                    Glide.with(mContext).using(new FirebaseImageLoader()).load(path).into(headerImage);
                                }

                                title.setText(trips.getName());

                                //Setting color of stars in RatingBar
                                Drawable drawable = ratingBar.getProgressDrawable();
                                DrawableCompat.setTint(drawable, Color.BLUE);
                                //Setting number of stars to display, as per rating from database
                                ratingBar.setNumStars(trips.getRating());

                                distance.setText(trips.getDistanceFromCityCentre()+" min walk from city centre");

                                //switch statement determines what the value stored in 'cost' is associated with,
                                //hotels will be for bed & breakfast, cinemas will be about tickets etc
                                switch (trips.getCategory()){
                                    case "Cinema":
                                        price.setText("Standard movie ticket costs an average of €"+trips.getCost());
                                        break;

                                    case "Culture":
                                        price.setText("Entry tickets cost an average of €"+trips.getCost());
                                        break;

                                    case "Hotel":
                                        price.setText("One nights B&B costs an average of €"+trips.getCost()+"p.p. sharing");
                                        break;

                                    case "Pub":
                                        price.setText("The average price of a beverage here is €"+trips.getCost());
                                        break;

                                    case "Restaurant":
                                        price.setText("A two course meal here will cost an average of €"+trips.getCost());
                                        break;

                                    case "Walk":
                                        price.setText("Free");
                                        break;

                                    default:
                                        price.setText("No information present");
                                }

                                //setting remainder of Textviews in dialog
                                address.setText(trips.getAddress());
                                website.setText(trips.getWebsite());
                                phone.setText(trips.getPhoneNumber());
                                email.setText(trips.getEmail());
                                info.setText(trips.getDetailedDescription());

                                //adding listeners and actions for dialog buttons
                                addButton.setOnClickListener(new View.OnClickListener() { //This is now the REMOVE button in Itinerary dialog
                                    @Override
                                    public void onClick(View v) {
                                        myDatabase.child("users").child(firebaseUser.getUid()).child("saved activities").child(trips.getName()).removeValue();
                                        Toast.makeText(mContext, "Removed from Itinerary", Toast.LENGTH_SHORT).show();
                                        dialog.dismiss();
                                        tripList.remove(position);
                                        adapter.notifyDataSetChanged();

                                        if(tripList.isEmpty()){
                                            emailFAB.setVisibility(View.GONE);
                                            recyclerView.setVisibility(View.GONE);
                                            emptyListTextView.setVisibility(View.VISIBLE);
                                            emptyListImageView.setVisibility(View.VISIBLE);
                                        }
                                    }
                                });

                                mapButton.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        dialog.dismiss();
                                        showOnMapString = trips.getName(); //passing name from trip to a String in MyTrip class, used in onMapReady method in MapsFragment
                                        bottomBar.getTabWithId(R.id.tab_maps).performClick(); //getting the Maps tab on bottombar and simulating click to change screen
                                    }
                                });

                                closeButton.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        dialog.dismiss();
                                    }
                                });


                                dialog.show();

                                return true;

                            case R.id.action_show_trip_on_map:
                                showOnMapString = trips.getName(); //passing name from trip to a String in MyTrip class, used in onMapReady method in MapsFragment
                                bottomBar.getTabWithId(R.id.tab_maps).performClick(); //getting the Maps tab on bottombar and simulating click to change screen
                                return true;

                            default:

                                break;
                        }
                        return false;
                    }
                });
                //popup.setOnMenuItemClickListener(new MyMenuItemClickListener());
                popup.show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return tripList.size();
    }

    private void showInfoDialog(){

    }

}
