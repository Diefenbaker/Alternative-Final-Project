package ie.wit.explorewaterford.models;


public class Trips {

    private String category;
    private String name;
    private String briefDescription;
    private String detailedDescription;
    private String phoneNumber;
    private String email;
    private String address;
    private String website;
    private int rating;
    private int distanceFromCityCentre;
    private int cost;
    private String thumbnail;
    private String latitude;
    private String longitude;


    public Trips() {
    }

    public Trips(String category, String name, String briefDescription, String detailedDescription,
                 String phoneNumber, String email, String address, String website, int rating, int distanceFromCityCentre,
                 int cost, String thumbnail, String latitude, String longitude) {

        this.category = category;
        this.name = name;
        this.briefDescription = briefDescription;
        this.detailedDescription = detailedDescription;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.address = address;
        this.website = website;
        this.rating = rating;
        this.distanceFromCityCentre = distanceFromCityCentre;
        this.cost = cost;
        this.thumbnail = thumbnail;
        this.longitude = longitude;
        this.latitude = latitude;
    }

    public String getName() {
        return name;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longtitude) {
        this.longitude = longtitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }



    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getBriefDescription() {
        return briefDescription;
    }

    public void setBriefDescription(String briefDescription) {
        this.briefDescription = briefDescription;
    }

    public String getDetailedDescription() {
        return detailedDescription;
    }

    public void setDetailedDescription(String detailedDescription) {
        this.detailedDescription = detailedDescription;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public int getDistanceFromCityCentre() {
        return distanceFromCityCentre;
    }

    public void setDistanceFromCityCentre(int distanceFromCityCentre) {
        this.distanceFromCityCentre = distanceFromCityCentre;
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}