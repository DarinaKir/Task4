public class Property {
    private Address address;
    private int roomNumbers;
    private int price;
    private String type;
    private boolean forRent;
    private int houseNumber;
    private int floor;
    private User user;

    public Property (Address address, int roomNumbers, int price, String type, boolean forRent, int houseNumber, int floor, User user){
        this.address = address;
        this.roomNumbers = roomNumbers;
        this.price = price;
        this.type = type;
        this.forRent = forRent;
        this.houseNumber = houseNumber;
        this.floor = floor;
        this.user = user;
    }

    public User getUser() {
        return user;
    }

    public boolean getForRent (){
        return forRent;
    }

    public String getType (){
        return type;
    }

    public int getRoomNumbers() {
        return roomNumbers;
    }

    public double getPrice() {
        return price;
    }

    public String toString (){
        return this.type+" - " + (forRent ? "for rent" : "for sale") +": " + this.roomNumbers + " rooms" + ((!this.type.equals("Private home") ? ", floor "+this.floor : ""))+
                ".\n" + "Price : " + this.price + " $\n" + "Contact info: " + this.user + "\n"+
                "(Address : "+this.address+")";
    }
}
