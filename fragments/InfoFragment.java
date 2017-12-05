package ie.wit.explorewaterford.fragments;

import android.app.Fragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import ie.wit.explorewaterford.R;
import ie.wit.explorewaterford.adapters.SavedTripAdapter;
import ie.wit.explorewaterford.adapters.TripAdapter;
import ie.wit.explorewaterford.firebase.auth.FirebaseImageLoader;
import ie.wit.explorewaterford.models.Trips;

import static ie.wit.explorewaterford.seeds.ActivitySeeds.seedDatabase;
import static java.net.Proxy.Type.HTTP;


public class InfoFragment extends Fragment implements View.OnClickListener {

    final FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
    final DatabaseReference myDatabase = FirebaseDatabase.getInstance().getReference();

    public static RecyclerView recyclerView;
    public static SavedTripAdapter adapter;
    private List<Trips> savedTripsList;

    public static FloatingActionButton emailFAB;
    public static ImageView itineraryLoadingImageView;
    public static ImageView emptyListImageView;
    public static TextView emptyListTextView;


    public InfoFragment() {
        //Required empty public constructor.
    }

    public static InfoFragment newInstance() {
        InfoFragment fragment = new InfoFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // super.onCreate(savedInstanceState);

        View view = inflater.inflate(R.layout.activity_info_fragment, container, false);

        recyclerView = (RecyclerView) view.findViewById(R.id.savedTripsRecyclerView);
        recyclerView.setHasFixedSize(false);

        emailFAB = (FloatingActionButton) view.findViewById(R.id.emailFAB);
        //setting colour of email FAB to match title bar
        emailFAB.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#00aad2")));
        emailFAB.setOnClickListener(this);

        itineraryLoadingImageView = (ImageView) view.findViewById(R.id.itineraryLoadingImageView);
        emptyListImageView = (ImageView) view.findViewById(R.id.emptyListImageView);
        emptyListTextView = (TextView) view.findViewById(R.id.emptyListTextView);

        Glide.with(getContext()).load(R.drawable.large_loading).into(itineraryLoadingImageView);
        Glide.with(getContext()).load(R.drawable.no_itinerary).into(emptyListImageView);

        savedTripsList = new ArrayList<>();
        adapter = new SavedTripAdapter(getContext(), savedTripsList);

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        final int offset = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 16, getResources().getDisplayMetrics());
        recyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                if (parent.getChildAdapterPosition(view) != parent.getAdapter().getItemCount() - 1) {
                    outRect.bottom = offset;
                }
            }
        });

        recyclerView.setAdapter(adapter);

        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState){
        TextView planTitle = (TextView) getActivity().findViewById(R.id.toolbar_title);
        planTitle.setText("Itinerary");

        Query getTrips = myDatabase.child("users").child(firebaseUser.getUid()).child("saved activities");
        getTrips.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    for(DataSnapshot savedTrip : dataSnapshot.getChildren()){ //get saved trips from collection
                        Trips thisTrip = savedTrip.getValue(Trips.class);
                        savedTripsList.add(thisTrip);
                        adapter.notifyDataSetChanged();
                    }
                }
                if(!savedTripsList.isEmpty()){
                    itineraryLoadingImageView.setVisibility(View.GONE);
                    recyclerView.setVisibility(View.VISIBLE);
                    emailFAB.setVisibility(View.VISIBLE);
                }else {
                    itineraryLoadingImageView.setVisibility(View.GONE);
                    emptyListImageView.setVisibility(View.VISIBLE);
                    emptyListTextView.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.emailFAB:
                launchEmailProcess();
                break;
        }
    }

    private void launchEmailProcess(){
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getContext());
        dialogBuilder.setTitle("Send Email");
        dialogBuilder.setMessage("Send this itinerary by email?\n" +
                "(Delete removes itinerary!)");

        dialogBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        dialogBuilder.setPositiveButton("Send", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                //Building a String to pass data to Gmail
                String emailMessage = "Hi,\n\n" +
                        "I'm sharing this itinerary with you using the Explore Waterford app.";

                for(int i = 0; i<savedTripsList.size();i++){
                    Trips thisTrip = savedTripsList.get(i);
                    int activityNumber = i+1;
                    emailMessage = emailMessage + "\n\n\n\nActivity " +activityNumber;
                    emailMessage = emailMessage + "\n\n"+thisTrip.getName();
                    emailMessage = emailMessage + "\n\n"+thisTrip.getDetailedDescription();

                    String pricing;
                    switch (thisTrip.getCategory()){
                        case "Cinema":
                            pricing = "Standard movie ticket costs an average of €"+thisTrip.getCost();
                            break;

                        case "Culture":
                            pricing = "Entry tickets cost an average of €"+thisTrip.getCost();
                            break;

                        case "Hotel":
                            pricing = "One nights B&B costs an average of €"+thisTrip.getCost()+"p.p. sharing";
                            break;

                        case "Pub":
                            pricing = "The average price of a beverage here is €"+thisTrip.getCost();
                            break;

                        case "Restaurant":
                            pricing = "A two course meal here will cost an average of €"+thisTrip.getCost();
                            break;

                        case "Walk":
                            pricing = "Free";
                            break;

                        default:
                            pricing = "No information present";
                    }
                    emailMessage = emailMessage + "\n\nPricing: "+pricing;

                    emailMessage = emailMessage +"\nRating: "+thisTrip.getRating()+"/5";
                    emailMessage = emailMessage + "\nDistance: Approx. "+thisTrip.getDistanceFromCityCentre()+" min walk from city centre";
                    emailMessage = emailMessage + "\nWebsite: "+thisTrip.getWebsite();
                    emailMessage = emailMessage + "\nPhone: "+thisTrip.getPhoneNumber();
                    emailMessage = emailMessage + "\nEmail: "+thisTrip.getEmail();
                    emailMessage = emailMessage + "\nAddress: "+thisTrip.getAddress();
                }

                emailMessage = emailMessage + "\n\n\nTo see the full range of activities available take a look at the Explore Waterford app." +
                        "\n\nSee you in Waterford!\n" +firebaseUser.getDisplayName();

                Intent emailIntent = new Intent(Intent.ACTION_SEND);
                emailIntent.setType("message/rfc822");
                emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[] {""}); // recipients
                emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Explore Waterford: Itinerary");
                emailIntent.putExtra(Intent.EXTRA_TEXT, emailMessage);
                emailIntent.setPackage("com.google.android.gm");
                startActivity(emailIntent);
            }
        });
        dialogBuilder.setNeutralButton("Delete", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                myDatabase.child("users").child(firebaseUser.getUid()).child("saved activities").removeValue();
                savedTripsList.clear();
                adapter.notifyDataSetChanged();
                emailFAB.setVisibility(View.GONE);
                recyclerView.setVisibility(View.GONE);
                emptyListTextView.setVisibility(View.VISIBLE);
                emptyListImageView.setVisibility(View.VISIBLE);
                dialog.dismiss();
                Toast.makeText(getContext(), "Itinerary deleted",Toast.LENGTH_SHORT).show();
            }
        });

        AlertDialog dialog = dialogBuilder.create();
        dialog.show();
    }

    @Override
    public void onStop() {
        Log.d(getClass().getSimpleName(), "onStop()");
        super.onStop();
    }

    @Override
    public void onDestroyView() {
        Log.d(getClass().getSimpleName(), "onDestroyView()");
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        Log.d(getClass().getSimpleName(), "onDestroy()");
        super.onDestroy();
    }

}
