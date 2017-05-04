package com.robertkiszelirk.placesiwere;

class Place {

    private String placeName;
    private String pictureName;
    private double latitude;
    private double longitude;
    private String link;
    private String email;
    private String phoneNumber;

    Place(String placeName,
          String pictureName,
          double latitude,
          double longitude,
          String link,
          String email,
          String phoneNumber){
        this.placeName = placeName;
        this.pictureName = pictureName;
        this.latitude = latitude;
        this.longitude = longitude;
        this.link = link;
        this.email = email;
        this.phoneNumber = phoneNumber;
    }

    String getPlaceName() {
        return placeName;
    }

    String getPictureName() {
        return pictureName;
    }

    double getLatitude() {
        return latitude;
    }

    double getLongitude() {
        return longitude;
    }

    String getLink() {
        return link;
    }

    String getEmail() {
        return email;
    }

    String getPhoneNumber() {
        return phoneNumber;
    }
}
