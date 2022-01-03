public class Address {
    private String cityName;
    private String streetName;

    public Address (String cityName, String streetName){
        this.cityName = cityName;
        this.streetName = streetName;
    }

    public String getCityName (){
        return this.cityName;
    }

    public String getStreetName (){
        return this.streetName;
    }

    public String toString (){
        return "Street: "+this.streetName+", City: "+this.cityName;
    }
}
