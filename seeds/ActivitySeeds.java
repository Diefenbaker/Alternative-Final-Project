package ie.wit.explorewaterford.seeds;


import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import ie.wit.explorewaterford.models.Trips;

public class ActivitySeeds {

    static DatabaseReference myDatabase = FirebaseDatabase.getInstance().getReference();

    public static void seedDatabase(){
        //checking to see if the Activities collection exists in database, if not push seeds to db
        Query dataCheck = myDatabase.child("activities");
        dataCheck.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(!dataSnapshot.exists()){
                    Trips trip1 = new Trips("Hotel","Dooley's Hotel", //category and name
                            "3* Quay Front Hotel, City Centre", //brief description
                            //detailed description
                            "In the center of Waterford, Dooley’s is a family-run hotel offering spacious rooms and full Irish breakfasts. Guests can enjoy weekend live music, and Christchurch Cathedral is just a 10-minute walk away.\n" +
                                    "\n" +
                                    "Rooms at Dooley’s Hotel are brightly furnished and all feature a private bathroom with a hairdryer. Each room provides a TV, free tea and coffee, and ironing facilities.\n" +
                                    "\n" +
                                    "The New Ship Restaurant serves creative Irish cuisine and desserts. The bar offers a range of dishes, including light snacks, cooked breakfasts, and lunch, afternoon, and evening menus.\n" +
                                    "\n" +
                                    "Waterford Crystal Visitor Center is a 5-minute drive from Dooley’s, and Waterford Museum of Treasures is a 10-minute walk away.\n" +
                                    "\n" +
                                    "Couples in particular like the location – they rated it 9.2 for a two-person trip.\n" +
                                    "\n" +
                                    "If you want to request something specific, you can do that in the next step before you book. After you book, we'll provide details so you can contact the property directly.\n" +
                                    "\n" +
                                    "We speak 10 languages, including yours!",
                            "+353 (0)51 873 531", //phone
                            "hotel@dooleys-hotel.ie", //email
                            "The Quay, Waterford City", //address
                            "http://www.dooleys-hotel.ie/index.html", //website
                            4, //rating
                            5, //minutes from city centre
                            104, //cost
                            "dooleys_hotel.jpg", //image name
                            "52.262848", //lat
                            "-7.115533"); //long
                    Trips trip2 = new Trips("Hotel", "Waterford Marina Hotel",
                            "3* Riverside Hotel, City Centre",
                            "Sitting on the banks of the River Suir, the Waterford Marina Hotel is 293 m from Waterford Crystal. It has a riverside restaurant, rooms with free Wi-Fi and free on-site parking.\n" +
                                    "\n" +
                                    "Decorated in light colors, the rooms at the Waterford Marina feature satellite TVs and work desks. They include bathrooms with showers and hairdryers.\n" +
                                    "\n" +
                                    "The Waterfront Restaurant features fine cuisine, either from the menu or as meals with fixed courses. The spacious Waterfront Bar features a lovely riverside terrace and live entertainment during the weekend. In the comfortable lounge, guests have access to internet facilities.\n" +
                                    "\n" +
                                    "The hotel is close to all Waterford’s fantastic shops, bars and restaurants. The Viking Triangle is a 5-minute walk from the hotel, along with Bishops Palace, Reginald's Tower, Waterford's Medieval Museum, and Christ Church Cathedral. Golfing can be enjoyed at the nearby Waterford Castle, Tramore, and Faithlegg Golf Courses.\n" +
                                    "\n" +
                                    "Waterford Airport is a 10-minute drive away, and Cork and Dublin Airports are each a 1 hour and 45 minute-drive from the hotel.\n" +
                                    "\n" +
                                    "Couples in particular like the location – they rated it 8.9 for a two-person trip.\n" +
                                    "\n" +
                                    "This property is also rated for the best value in Waterford! Guests are getting more for their money when compared to other properties in this city.",
                            "+353 51 856600",
                            "info@waterfordmarinahotel.ie",
                            "Canada Street, Waterford City",
                            "http://www.waterfordmarinahotel.com/home.htm",
                            4,
                            8,
                            69,
                            "marina_hotel.jpg",
                            "52.258670",
                            "-7.101763");
                    Trips trip3 = new Trips("Hotel","Treacy's Hotel & Spa",
                            "3* Quay Front Hotel, City Centre",
                            "Treacy’s Hotel is on Waterford’s Quays, overlooking the Suir River. It has delicious food, modern bedrooms, free parking and fantastic recreational facilities including a 21 m pool, sauna and hot tub.\n" +
                                    "\n" +
                                    "Rooms at Treacy’s Hotel Spa & Leisure Club Waterford are decorated in warm colors, feature satellite TVs and free Wi-Fi. Rooms have laptop safes and Hypnos beds covered with crisp white sheets.\n" +
                                    "\n" +
                                    "The 5-star Spirit Health and Beauty Centre offers a range of indulgent treatments including massage, thermal suites, mud chambers and holistic therapies. Guests can also relax in the steam room and workout in the gym.\n" +
                                    "\n" +
                                    "The elegant Crokers Restaurant serves fine Irish and European cuisine, using ingredients sourced from the Waterford area. Timbertoes Bar offers its own extensive bar food menu and weekly entertainment.\n" +
                                    "\n" +
                                    "The hotel is just a 5 minute walk from both the bus and train station. Waterford Crystal, Reginald’s Tower and Waterford Golf Club are all within a 10 minute walk. ",
                            "+353 (0)51 877 222",
                            "res@thwaterford.com",
                            "1 Merchants Quay, Waterford City",
                            "http://www.treacyshotelwaterford.com/index.html",
                            4,
                            3,
                            91,
                            "treacys_hotel.jpg",
                            "52.263860",
                            "-7.118677");
                    myDatabase.child("activities").child("hotel").child(trip1.getName()).setValue(trip1);
                    myDatabase.child("activities").child("hotel").child(trip2.getName()).setValue(trip2);
                    myDatabase.child("activities").child("hotel").child(trip3.getName()).setValue(trip3);

                    Trips trip4 = new Trips("Restaurant", "The Granary",
                            "Award Winning City Centre Café",
                            "In the heart of Waterford City, The Granary Cafe provides excellent dining in a light and relaxing environment, housed in a beautiful and charming quay-side granary built in 1870.\n" +
                                    "\n" +
                                    "We only use the best and the freshest ingredients in all of our dishes. Every day we prepare a tempting range of delicious salads, main courses and desserts.\n" +
                                    "\n" +
                                    "We are passionate about providing a warm welcome and a good food experience every time.",
                            "(051) 854 428",
                            "n/a",
                            "Hannover Street, Waterford",
                            "http://granarycafe.ie/home",
                            5,
                            4,
                            8,
                            "granary_restaurant.jpg",
                            "52.262659",
                            "-7.115193");
                    Trips trip5 = new Trips("Restaurant", "Sheehan's",
                            "European & Sea Food Restaurant",
                            "Multi-cuisine restaurant serving breakfast, lunch and dinner. \n" +
                                    "On street and car lot parking available. Walk-ins and reservations welcome. \n" +
                                    "Outdoor seating area with full waiter service. Take-out available.",
                            "(051) 850 500",
                            "n/a",
                            "40 Merchants Quay, Waterford",
                            "https://www.facebook.com/pg/SheehansRestaurant/about",
                            2,
                            5,
                            23,
                            "sheehans_restaurant.jpg",
                            "52.262797",
                            "-7.114510");
                    Trips trip6 = new Trips("Restaurant", "Emiliano's",
                            "Ristorante Italiano, City Centre",
                            "We are open 17 years since year 2000. Emiliano's is a traditional Italian restaurant serving the most fresh local " +
                                    "ingredients to create tasty and honest dishes - letting the ingredients speak for themselves. \n" +
                                    "\n" +
                                    "The food is created with passion, professionalism and care from the very experienced Italian chefs. " +
                                    "Great staff, fantastic friendly service, attentive to every need from the customers. \n" +
                                    "\n" +
                                    "We cater for any kind of occasions including hen, stag, party, communion, birthdays, business meetings etc." +
                                    "Private dining room available. We create menus for any occasion with a great choice of Italian wines from the best regions of Italy.",
                            "(051) 820 333",
                            "n/a",
                            "21 High Street, Waterford",
                            "https://www.facebook.com/Emilianos-ristorante-italiano-167317383311641/",
                            3,
                            5,
                            28,
                            "emilianos_restaurant.jpg",
                            "52.260416",
                            "-7.108442");
                    myDatabase.child("activities").child("restaurant").child(trip4.getName()).setValue(trip4);
                    myDatabase.child("activities").child("restaurant").child(trip5.getName()).setValue(trip5);
                    myDatabase.child("activities").child("restaurant").child(trip6.getName()).setValue(trip6);

                    Trips trip7 = new Trips("Pub", "Munster Bar",
                            "Family Run Pub, City Centre",
                            "The Munster Bar has been run by the Fitzgerald Family for the past 60 years and is one of the few remaining family-run pubs in the city. We have always prided ourselves on the quality of our service and the standard of our food, so whether you call into the Munster for a quick coffee, a relaxing pint or a full dinner there will always be one of the Fitzgerald Family on hand to ensure your visit is enjoyable.\n" +
                                    "\n" +
                                    "We specialise in steak and seafood and offer an affordable early bird and pre-theatre menu. We also offer a carvery lunch menu and cater for private functions. ",
                            "(051) 874 656",
                            "info@themunsterbar.com",
                            "The Mall, Bailey's New St., Waterford",
                            "http://themunsterbar.com/index.html",
                            3,
                            4,
                            5,
                            "munster_bar_pub.jpg",
                            "52.260033",
                            "-7.105932");
                    Trips trip8 = new Trips("Pub", "The Three Shippes",
                            "City Centre Gastropub",
                            "Gatropub serving Irish cuisine with full bar available.\n" +
                                    "Child friendly atmosphere, highchairs available. Serving late breakfast, lunch and dinner.\n" +
                                    "Large screens showing major sports events, Sky Sports, BT Sports and Eir Sports available.",
                            "(051) 843 178",
                            "n/a",
                            "18 William Street, Waterford",
                            "https://www.facebook.com/threeshippes/",
                            3,
                            7,
                            4,
                            "three_shippes_pub.jpg",
                            "52.258018",
                            "-7.103265");
                    Trips trip9 = new Trips("Pub", "The Gingerman",
                            "Revived Historic Tavern, City Centre",
                            "Your stay in Waterford is not complete without a visit to the historic Gingerman Tavern. \n" +
                                    "This revived old pub is located in a newly pedestrianised lane just off Broad Street in the historic heart of the old norman city which rises steeply above the suir waterfront.",
                            "(051) 879 522",
                            "n/a",
                            "6 Arundel Lane, Waterford",
                            "https://www.tripadvisor.ie/Restaurant_Review-g186638-d3932134-Reviews-The_Gingerman-Waterford_County_Waterford.html",
                            4,
                            3,
                            5,
                            "gingerman_pub.jpg",
                            "52.260612",
                            "-7.111351");
                    myDatabase.child("activities").child("pub").child(trip7.getName()).setValue(trip7);
                    myDatabase.child("activities").child("pub").child(trip8.getName()).setValue(trip8);
                    myDatabase.child("activities").child("pub").child(trip9.getName()).setValue(trip9);

                    Trips trip10 = new Trips("Walk", "Waterford Waterfront Slí",
                            "Waterfront Walk, City Centre",
                            "This walk takes you along the waterfront, through the People's Park and on the streets of " +
                                    "Waterford City, passing by a large number of places of interest, a few of which are " +
                                    "Reginald's Tower, the Theatre Royal, Blackfriars Abbey and the Apple Market.",
                            "n/a",
                            "n/a",
                            "The Mall, Waterford City",
                            "http://www.irishtrails.ie/Trail/Waterford-Waterfront-Sli/1052/",
                            5,
                            4,
                            0,
                            "walks.JPG",
                            "52.260045",
                            "-7.105803");
                    Trips trip11 = new Trips("Walk", "Waterford City Slí",
                            "City Walk, City Centre & Surrounding Areas",
                            "This walk takes you around the roads of Waterford City towards the southern side, passing " +
                                    "through the People's Park and by two churches, the Court House, Railway Square, " +
                                    "the Theatre Royal and City Hall.",
                            "n/a",
                            "n/a",
                            "The Mall, Waterford City",
                            "http://www.irishtrails.ie/Trail/Waterford-City-Sli/1053/",
                            3,
                            4,
                            0,
                            "walks.JPG",
                            "52.260248",
                            "-7.105266");
                    myDatabase.child("activities").child("walk").child(trip10.getName()).setValue(trip10);
                    myDatabase.child("activities").child("walk").child(trip11.getName()).setValue(trip11);

                    Trips trip12 = new Trips("Cinema","Waterford Omniplex",
                            "5 Cinema Screens, 1 MAXX Screen with Barco Auro Sound",
                            "A modern cinema located on the busy thoroughfare of Patrick Street, County Waterford, showing all the latest movie releases. Join MyOmniplex and get 10% off all cinema bookings. Book tickets online, check cinema listings, MAXX screen listings and more.\n" +
                                    "\n" +
                                    "Features of Waterford Omniplex include: 5 Cinema Screens, OmniplexMAXX screen with Barco Auro sound, Candy King Pic’n’mix, Ben & Jerry’s Cafe, Assigned seating, Automated ticket purchase and collection points, 3D performances and Disabled seating locations.",
                            "(051) 855 685",
                            "waterford@omniplex.ie",
                            "Patrick St., Waterford",
                            "https://www.omniplex.ie/cinemas/cinema/waterford",
                            4,
                            10,
                            7,
                            "omniplex_cinema.jpg",
                            "52.260268",
                            "-7.112573");
                    Trips trip13 = new Trips("Cinema","Odeon Cinema",
                            "8 Cinema Screens, Premier Seats",
                            "We’ve got 8 screens of film magic all showing stunning RealD 3D, conveniently located near the city centre. Relax in our Premier Seats, join our cafe culture at Costa, and satisfy your sweet tooth with a scoop of Ben & Jerry's.\n" +
                                    "\n" +
                                    "Enjoy super speedy ticket pick up at 3 FTMs, located in the main foyer and lobby in the main foyer! ",
                            "(083) 834 5422",
                            "n/a",
                            "Railway Sq. Development, Waterford",
                            "http://www.odeoncinemas.ie/cinemas/waterford/205/",
                            4,
                            6,
                            8,
                            "odeon_cinema.jpg",
                            "52.254672",
                            "-7.111028");
                    myDatabase.child("activities").child("cinema").child(trip12.getName()).setValue(trip12);
                    myDatabase.child("activities").child("cinema").child(trip13.getName()).setValue(trip13);

                    Trips trip14 = new Trips("Culture", "Garter Lane Arts Centre",
                            "Theatre, Dance, Comedy, Music, Film, Literature",
                            "Exhibitions by contemporary and local artists, plus theatre, music, film, comedy and dance events.\n" +
                                    "Your home for the arts in Waterford, supported by the Arts Council, Department of Social Protection & Waterford City Council.",
                            "(051) 855 038",
                            "admin@garterlane.ie",
                            "O'Connell St., Waterford City",
                            "http://www.garterlane.ie/index.php",
                            3,
                            10,
                            5,
                            "garter_lane_culture.JPG",
                            "52.261949",
                            "-7.114761");
                    Trips trip15 = new Trips("Culture","Waterford Museum of Treasures",
                            "3 Museums within the Viking Triangle",
                            "Three very different museums all within a few paces of each other tell the 1100 year old story of Waterford, from its foundation in 914 by Viking sea pirates, to the late 20th century.\n" +
                                    "\n" +
                                    "The massive stone fortress that is Reginald’s Tower houses the Treasures of Viking Waterford. \n" +
                                    "\n" +
                                    "Ireland’s only Medieval Museum showcases the Treasures of Medieval Waterford.\n" +
                                    "\n" +
                                    "The elegant Neo-Classical Bishop’s Palace built in 1743 is the home of the Treasures of 18th, 19th and 20th centuries.",
                            "+353 (0)761 102501",
                            "booking@waterfordcouncil.ie",
                            "The Mall, Waterford City",
                            "http://www.waterfordtreasures.com/",
                            5,
                            5,
                            10,
                            "museum_treasures_culture.jpg",
                            "52.259679",
                            "-7.107674");
                    Trips trip16 = new Trips("Culture", "Waterford Crystal Visitor Centre",
                            "House of Waterford Crystal",
                            "Located on the Mall in the heart of the Viking Triangle in Waterford City, the House of Waterford Crystal brings a visit to Waterford to a whole new level. Our staff will be pleased to welcome you on arrival and take you on a journey to see exquisite pieces of crystal created before your very eyes.",
                            "+353 51 317000",
                            "houseofwaterfordcrystal@fiskars.com",
                            "The Mall, Waterford City",
                            "https://www.waterfordvisitorcentre.com/",
                            5,
                            5,
                            12,
                            "waterford_crystal_culture.jpg",
                            "52.259314",
                            "-7.106776");
                    myDatabase.child("activities").child("culture").child(trip14.getName()).setValue(trip14);
                    myDatabase.child("activities").child("culture").child(trip15.getName()).setValue(trip15);
                    myDatabase.child("activities").child("culture").child(trip16.getName()).setValue(trip16);


                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }



}
