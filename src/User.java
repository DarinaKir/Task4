public class User {
    private String name;
    private String password;
    private String phoneNumber;
    private boolean isMediator;

    public User (String name, String password, String phoneNumber, boolean isMediator){
        this.name = name;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.isMediator = isMediator;
    }

    public String getName (){
        return this.name;
    }

    public String getPassword () {
        return  this.password;
    }

    public boolean getIsMediator (){
        return this.isMediator;
    }

    public String toString () {
        return this.name+" "+this.phoneNumber+(isMediator ? " (real estate broker)" : " (regular user)");
    }
}
