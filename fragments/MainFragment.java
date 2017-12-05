package ie.wit.explorewaterford.fragments;

import android.app.Fragment;
import android.content.res.Resources;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import ie.wit.explorewaterford.R;
import ie.wit.explorewaterford.adapters.TripAdapter;
import ie.wit.explorewaterford.models.Trips;

import static ie.wit.explorewaterford.seeds.ActivitySeeds.seedDatabase;


public class MainFragment extends Fragment implements AdapterView.OnItemSelectedListener {

    private RecyclerView recyclerView;
    private TripAdapter adapter;
    private List<Trips> tripList;

    static DatabaseReference myDatabase = FirebaseDatabase.getInstance().getReference();

    Spinner categorySpinner;
    String[] categoryArray;
    ArrayList<String> categoryArrayList;
    ArrayAdapter<String> categoryArrayAdapter;

    ImageView loadingImageView;
    public static Fragment mainFragment;

    public MainFragment() {
        //empty public constructor
    }

    public static MainFragment newInstance() {
        MainFragment fragment = new MainFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        seedDatabase(); //checking to see if Activities collection exists, adding seeds if not

        View v = null;
        v = inflater.inflate(R.layout.recycler_plan, container, false);

        loadingImageView = (ImageView) v.findViewById(R.id.activitiesLoadingImageView);
        Glide.with(getContext()).load(R.drawable.large_loading).into(loadingImageView);

        categorySpinner = (Spinner) v.findViewById(R.id.categorySpinner);
        recyclerView = (RecyclerView) v.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(false);

        tripList = new ArrayList<>();
        adapter = new TripAdapter(getContext(), tripList);

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

        categoryArray = getResources().getStringArray(R.array.categories_array); //passing values from array in strings file to this array
        categoryArrayList = new ArrayList<String>(Arrays.asList(categoryArray)); //passing the array to the arraylist
        categoryArrayAdapter = new ArrayAdapter<String>(getContext(),android.R.layout.simple_dropdown_item_1line, categoryArray); //defining the array adapters layout and contents
        categorySpinner.setAdapter(categoryArrayAdapter); //linking adapter to spinner
        categorySpinner.setOnItemSelectedListener(this);

        Query getAllTrips = myDatabase.child("activities");
        getAllTrips.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(!dataSnapshot.exists()){
                    seedDatabase(); //another check on database, add seeds if empty
                } else {
                    for(DataSnapshot category : dataSnapshot.getChildren()){ //get categories from activities collection
                        for(DataSnapshot trip : category.getChildren()){ //get trips from category
                            Trips thisTrip = trip.getValue(Trips.class);
                            tripList.add(thisTrip);
                            adapter.notifyDataSetChanged();
                        }
                    }
                    if(!tripList.isEmpty()){
                        loadingImageView.setVisibility(View.GONE);
                        recyclerView.setVisibility(View.VISIBLE);
                        categorySpinner.setVisibility(View.VISIBLE);
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        return v;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        TextView planTitle = (TextView) getActivity().findViewById(R.id.toolbar_title);
        planTitle.setText("Activities");

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        Query getHotels = myDatabase.child("activities").child("hotel");
        Query getBars = myDatabase.child("activities").child("pub");
        Query getRestaurants = myDatabase.child("activities").child("restaurant");
        Query getCulture = myDatabase.child("activities").child("culture");
        Query getCinemas = myDatabase.child("activities").child("cinema");
        Query getWalks = myDatabase.child("activities").child("walk");

        switch (parent.getSelectedItem().toString()){

            case "Family":
                tripList.clear();
                recyclerView.invalidate();
                getCinemas.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if(dataSnapshot.exists()){
                            for(DataSnapshot trip : dataSnapshot.getChildren()){
                                Trips thisTrip = trip.getValue(Trips.class);
                                tripList.add(thisTrip);
                                adapter.notifyDataSetChanged();
                            }
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
                getHotels.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if(dataSnapshot.exists()){
                            for(DataSnapshot trip : dataSnapshot.getChildren()){
                                Trips thisTrip = trip.getValue(Trips.class);
                                tripList.add(thisTrip);
                                adapter.notifyDataSetChanged();
                            }
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
                getRestaurants.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if(dataSnapshot.exists()){
                            for(DataSnapshot trip : dataSnapshot.getChildren()){
                                Trips thisTrip = trip.getValue(Trips.class);
                                tripList.add(thisTrip);
                                adapter.notifyDataSetChanged();
                            }
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
                getCulture.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if(dataSnapshot.exists()){
                            for(DataSnapshot trip : dataSnapshot.getChildren()){
                                Trips thisTrip = trip.getValue(Trips.class);
                                tripList.add(thisTrip);
                                adapter.notifyDataSetChanged();
                            }
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
                break;

            case "Social":
                tripList.clear();
                recyclerView.invalidate();
                getBars.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if(dataSnapshot.exists()){
                            for(DataSnapshot trip : dataSnapshot.getChildren()){
                                Trips thisTrip = trip.getValue(Trips.class);
                                tripList.add(thisTrip);
                                adapter.notifyDataSetChanged();
                            }
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
                getHotels.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if(dataSnapshot.exists()){
                            for(DataSnapshot trip : dataSnapshot.getChildren()){
                                Trips thisTrip = trip.getValue(Trips.class);
                                tripList.add(thisTrip);
                                adapter.notifyDataSetChanged();
                            }
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
                getRestaurants.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if(dataSnapshot.exists()){
                            for(DataSnapshot trip : dataSnapshot.getChildren()){
                                Trips thisTrip = trip.getValue(Trips.class);
                                tripList.add(thisTrip);
                                adapter.notifyDataSetChanged();
                            }
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
                getWalks.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if(dataSnapshot.exists()){
                            for(DataSnapshot trip : dataSnapshot.getChildren()){
                                Trips thisTrip = trip.getValue(Trips.class);
                                tripList.add(thisTrip);
                                adapter.notifyDataSetChanged();
                            }
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
                break;

            case "Romantic":
                tripList.clear();
                recyclerView.invalidate();
                getWalks.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if(dataSnapshot.exists()){
                            for(DataSnapshot trip : dataSnapshot.getChildren()){
                                Trips thisTrip = trip.getValue(Trips.class);
                                tripList.add(thisTrip);
                                adapter.notifyDataSetChanged();
                            }
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
                getRestaurants.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if(dataSnapshot.exists()){
                            for(DataSnapshot trip : dataSnapshot.getChildren()){
                                Trips thisTrip = trip.getValue(Trips.class);
                                tripList.add(thisTrip);
                                adapter.notifyDataSetChanged();
                            }
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
                getHotels.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if(dataSnapshot.exists()){
                            for(DataSnapshot trip : dataSnapshot.getChildren()){
                                Trips thisTrip = trip.getValue(Trips.class);
                                tripList.add(thisTrip);
                                adapter.notifyDataSetChanged();
                            }
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
                break;

            case "Hotels":
                tripList.clear();
                recyclerView.invalidate();
                getHotels.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if(dataSnapshot.exists()){
                            for(DataSnapshot trip : dataSnapshot.getChildren()){
                                Trips thisTrip = trip.getValue(Trips.class);
                                tripList.add(thisTrip);
                                adapter.notifyDataSetChanged();
                            }
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
                break;

            case "Bars":
                tripList.clear();
                recyclerView.invalidate();
                getBars.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if(dataSnapshot.exists()){
                            for(DataSnapshot trip : dataSnapshot.getChildren()){
                                Trips thisTrip = trip.getValue(Trips.class);
                                tripList.add(thisTrip);
                                adapter.notifyDataSetChanged();
                            }
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
                break;

            case "Restaurants":
                tripList.clear();
                recyclerView.invalidate();
                getRestaurants.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if(dataSnapshot.exists()){
                            for(DataSnapshot trip : dataSnapshot.getChildren()){
                                Trips thisTrip = trip.getValue(Trips.class);
                                tripList.add(thisTrip);
                                adapter.notifyDataSetChanged();
                            }
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
                break;

            case "Culture":
                tripList.clear();
                recyclerView.invalidate();
                getCulture.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if(dataSnapshot.exists()){
                            for(DataSnapshot trip : dataSnapshot.getChildren()){
                                Trips thisTrip = trip.getValue(Trips.class);
                                tripList.add(thisTrip);
                                adapter.notifyDataSetChanged();
                            }
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
                break;

            case "Cinemas":
                tripList.clear();
                recyclerView.invalidate();
                getCinemas.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if(dataSnapshot.exists()){
                            for(DataSnapshot trip : dataSnapshot.getChildren()){
                                Trips thisTrip = trip.getValue(Trips.class);
                                tripList.add(thisTrip);
                                adapter.notifyDataSetChanged();
                            }
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
                break;

            case "Walks":
                tripList.clear();
                recyclerView.invalidate();
                getWalks.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if(dataSnapshot.exists()){
                            for(DataSnapshot trip : dataSnapshot.getChildren()){
                                Trips thisTrip = trip.getValue(Trips.class);
                                tripList.add(thisTrip);
                                adapter.notifyDataSetChanged();
                            }
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
                break;

            default:
                tripList.clear();
                recyclerView.invalidate();
                Query getTrips = myDatabase.child("activities");
                getTrips.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if(!dataSnapshot.exists()){
                            seedDatabase(); //another check on database, add seeds if empty
                        } else {
                            for(DataSnapshot category : dataSnapshot.getChildren()){ //get categories from activities collection
                                for(DataSnapshot trip : category.getChildren()){ //get trips from category
                                    Trips thisTrip = trip.getValue(Trips.class);
                                    tripList.add(thisTrip);
                                    adapter.notifyDataSetChanged();
                                }
                            }
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    /**
     * Adding Plans as test

     private void prepareTrips() {
     int[] trips = new int[]{
     R.drawable.waterford_city,
     R.drawable.waterford_city,
     R.drawable.waterford_city,
     R.drawable.waterford_city};


     Trips t = new Trips("Hotel Trivago", "test text", trips[0]);
     tripList.add(t);

     t = new Trips("Hotel Trivago2", "test text", trips[1]);
     tripList.add(t);
     t = new Trips("Hotel Trivago3", "test text", trips[2]);
     tripList.add(t);
     t = new Trips("Hotel Trivago4", "test text", trips[3]);
     tripList.add(t);

     adapter.notifyDataSetChanged();

     }
     */

    /**
     * RecyclerView item decoration - give equal margin around grid item
     */
    public class GridSpacingItemDecoration extends RecyclerView.ItemDecoration {

        private int spanCount;
        private int spacing;
        private boolean includeEdge;

        public GridSpacingItemDecoration(int spanCount, int spacing, boolean includeEdge) {
            this.spanCount = spanCount;
            this.spacing = spacing;
            this.includeEdge = includeEdge;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            int position = parent.getChildAdapterPosition(view); // item position
            int column = position % spanCount; // item column

            if (includeEdge) {
                outRect.left = spacing - column * spacing / spanCount; // spacing - column * ((1f / spanCount) * spacing)
                outRect.right = (column + 1) * spacing / spanCount; // (column + 1) * ((1f / spanCount) * spacing)

                if (position < spanCount) { // top edge
                    outRect.top = spacing;
                }
                outRect.bottom = spacing; // item bottom
            } else {
                outRect.left = column * spacing / spanCount; // column * ((1f / spanCount) * spacing)
                outRect.right = spacing - (column + 1) * spacing / spanCount; // spacing - (column + 1) * ((1f /    spanCount) * spacing)
                if (position >= spanCount) {
                    outRect.top = spacing; // item top
                }
            }
        }
    }

    /**
     * Converting dp to pixel
     */
    private int dpToPx(int dp) {
        Resources r = getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));
    }


    @Override
    public void onResume() {
        super.onResume();
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
//Limit recycler to load only 15 items for presentation purposes
//    private void attachRecyclerViewAdapter() {
//        Query lastFifteen = ref.limitToLast(15);
//
//        mAdapter = new FirebaseRecyclerAdapter<Journal, JournalHolder>(
//                Journal.class, R.layout.item_journal, JournalHolder.class, lastFifteen) {
//            @Override
//            protected void populateViewHolder(final JournalHolder viewHolder, Journal model, final int position) {
//
//                viewHolder.setCreatedDate(model.getJdate());
//
//                viewHolder.setCreatedTime(model.getJtime());
//
//                viewHolder.setDesc(model.getdesc());
//
//                viewHolder.itemView.setOnLongClickListener(new View.OnLongClickListener()
//                                                           {
//                                                               @Override
//                                                               public boolean onLongClick(View v)
//                                                               {
//                                                                   // onJournalDelete (v.getTag().toString());
//
//                                                                   Log.w(TAG, "You clicked on "+position);
//                                                                   AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
//                                                                   builder.setMessage("Are you sure you want to delete this Journal entry " + "?");
//                                                                   builder.setCancelable(false);
//
//                                                                   builder.setPositiveButton("Yes", new DialogInterface.OnClickListener()
//                                                                   {
//                                                                       public void onClick(DialogInterface dialog, int id)
//                                                                       {
//                                                                           //app.databaseManager.deleteAJournal(key);
//                                                                           id = position;
//                                                                           mAdapter.getRef(id).removeValue();
//                                                                           Log.e(TAG, "Entry at position: " +id+ " has been deleted.");
//                                                                           Toast.makeText(viewHolder.itemView.getContext(), "Entry has been deleted", Toast.LENGTH_SHORT).show();
//                                                                           //app.databaseManager.deleteAJournal(key);
//                                                                       }
//                                                                   }).setNegativeButton("No", new DialogInterface.OnClickListener()
//                                                                   {
//                                                                       public void onClick(DialogInterface dialog, int id)
//                                                                       {
//                                                                           dialog.cancel();
//                                                                       }
//                                                                   });
//                                                                   AlertDialog alert = builder.create();
//                                                                   alert.show();
//                                                                   // without dialog
//                                                                   //mAdapter.getRef(position).removeValue();
//
//                                                                   return false;
//                                                               }
//                                                           }
//                );
//
//                viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//
//                        FragmentTransaction ft = getFragmentManager().beginTransaction();
//                        Fragment fragment;
//                        fragment = TimelineFragment.newInstance();
//                        ft.replace(R.id.fragment_container, fragment);
//                        ft.addToBackStack(null);
//                        ft.commit();
//
//                    }
//                });
//
//
//
//            }
//
//            @Override
//            protected void onDataChanged() {
//                // If there are no chat messages, show a view that invites the user to add a message.
//                mEmptyListMessage.setVisibility(mAdapter.getItemCount() == 0 ? View.VISIBLE : View.GONE);
//            }
//
//
//        };
//
//        // Scroll to bottom on new messages
//        mAdapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
//            @Override
//            public void onItemRangeInserted(int positionStart, int itemCount) {
//                recyclerView.smoothScrollToPosition(mAdapter.getItemCount());
//
//            }
//        });
//
//
//
//        recyclerView.setAdapter(mAdapter);
//    }
//}
