import java.util.Scanner;
public class RealEstate {
    private User[] users;
    private Property[] properties;
    private Address[] addresses;
    private String[] propertiesTypes;

    final String stringTermSearch = "or type -999 if you do not want to filter it";
    final int intTermSearch = -999;

    public RealEstate() {
        this.users = new User[0];
        this.properties = new Property[0];
        this.addresses = new Address[]{new Address("Ashkelon", "Maale Hagat"), new Address("Ashkelon", "Herzl"), new Address("Tel Aviv", "Herzl"), new Address("Tel Aviv", "Ben-Gurion"), new Address("Ashkelon", "Ben-Gurion"), new Address("Jerusalem", "Ben-Gurion"), new Address("Jerusalem", "Eli Cohen"), new Address("Ashkelon", "Eli Cohen"), new Address("Tel Aviv", "Eli Cohen"), new Address("Ashdod", "Arlozorov")};
        this.propertiesTypes = new String[]{"Standard apartment", "Penthouse apartment", "Private home"};
    }

    private int userNameIndex(String userName) {
        int index = -1;
        for (int i = 0; i < this.users.length; i++) {
            if (userName.equals(this.users[i].getName())) {
                index = i;
                break;
            }
        }
        return index;
    }

    private boolean isPasswordStrong(String password) {
        boolean hasNumber = false;
        boolean hasStrongChar = false;
        for (int i = 0; i < password.length() && !(hasStrongChar && hasNumber); i++) {
            hasStrongChar = hasStrongChar || (password.charAt(i) == '$' || password.charAt(i) == '%' || password.charAt(i) == '_');
            hasNumber = hasNumber || (Character.isDigit(password.charAt(i)));
        }
        return hasStrongChar && hasNumber;
    }

    private boolean isPhoneNumberCorrect(String phoneNumber) {
        boolean isCorrect = false;
        for (int i = 0; i < phoneNumber.length(); i++) {
            isCorrect = (Character.isDigit(phoneNumber.charAt(i)));
            if (!isCorrect) {
                System.out.println("A phone number consists only of numbers!");
                break;
            }
        }
        if (isCorrect) {
            isCorrect = false;
            if (phoneNumber.length() != 10) {
                System.out.println("The phone number displayed is too short.");
            } else if (phoneNumber.charAt(0) != '0' || phoneNumber.charAt(1) != '5') {
                System.out.println("The prefix of the phone number does not match.");
            } else isCorrect = true;
        }
        return isCorrect;
    }

    private void addUser(User user) {
        User[] updatedUsersList = new User[this.users.length + 1];
        for (int i = 0; i < this.users.length; i++) {
            updatedUsersList[i] = this.users[i];
        }
        updatedUsersList[this.users.length] = user;
        this.users = updatedUsersList;
    }

    public void createUser() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Type a user name :");
        String userName = scanner.nextLine();
        while (userNameIndex(userName) != -1) {
            System.out.println("Such a username already exists in the system.\n" +
                    "Choose another username.");
            userName = scanner.nextLine();
        }
        System.out.println("Compose a password :");
        String password = scanner.nextLine();
        while (!isPasswordStrong(password)) {
            System.out.println("Your password is not strong enough.\n" +
                    "It must contain at least one digit and at least one of the following characters: %  $  _\n" +
                    "Compose a password again :");
            password = scanner.nextLine();
        }
        System.out.println("Type in a phone number :");
        String phoneNumber = scanner.nextLine();
        while (!isPhoneNumberCorrect(phoneNumber)) {
            System.out.println("Enter a phone number correctly again:");
            phoneNumber = scanner.nextLine();
        }
        System.out.println("You are (Type the appropriate number) :\n" +
                "1 - Mediator\n" +
                "2 - Regular user");
        String mediatorOrRegular = scanner.nextLine();
        while (!mediatorOrRegular.equals("1") && !mediatorOrRegular.equals("2")) {
            System.out.println("Error.\n" +
                    "Type the number of one of the options, you:\n" +
                    "1 - Mediator\n" +
                    "2 - Regular user");
            mediatorOrRegular = scanner.nextLine();
        }
        User newUser = new User(userName, password, phoneNumber, (mediatorOrRegular.equals("1")));
        addUser(newUser);
        System.out.println("User created successfully!");
    }

    public User login() {
        Scanner scanner = new Scanner(System.in);
        User user = null;
        System.out.println("Enter your username :");
        String userName = scanner.nextLine();
        int userIndex = userNameIndex(userName);
        if (userIndex == -1) {
            System.out.println("There is no user with such a name.");
        } else {
            System.out.println("Enter your password :");
            String password = scanner.nextLine();
            if (!this.users[userIndex].getPassword().equals(password)) {
                System.out.println("The password does not match this username.");
            } else {
                user = this.users[userIndex];
            }
        }
        return user;
    }

    private int numberOfUserProperties(User user) {
        int userProperties = 0;
        for (int i = 0; i < this.properties.length; i++) {
            if (this.properties[i].getUser().getName().equals(user.getName()) && this.properties[i].getUser().getPassword().equals(user.getPassword())) {
                userProperties++;
            }
        }
        return userProperties;
    }

    private boolean isCityAppeared(String cityName, int startIndex) {
        boolean isAppeared = false;
        for (int j = startIndex; j >= 0 && !isAppeared; j--) {
            isAppeared = (cityName.equalsIgnoreCase(this.addresses[j].getCityName()));
        }
        return isAppeared;
    }

    private int receiveOfPositiveNumbersAndForFiltering(boolean isForSearch) {
        Scanner scanner = new Scanner(System.in);
        int number = 0;
        do {
            System.out.println("Enter a positive number greater than zero " + ((isForSearch) ? stringTermSearch : ""));
            number = scanner.nextInt();
        } while (number <= 0 && (isForSearch && number != intTermSearch));
        return number;
    }

    private void addProperty(Property property) {
        Property[] properties = new Property[this.properties.length + 1];
        for (int i = 0; i < this.properties.length; i++) {
            properties[i] = this.properties[i];
        }
        properties[this.properties.length] = property;
        this.properties = properties;
    }

    public boolean postNewProperty(User user) {
        int userProperties = numberOfUserProperties(user);
        boolean canPost = ((user.getIsMediator() && userProperties < 10) || (!user.getIsMediator() && userProperties < 3));
        if (canPost) {
            Scanner scanner = new Scanner(System.in);
            System.out.println("Please select a city from the following list (type it):");
            for (int i = 0; i < this.addresses.length; i++) {
                if (!isCityAppeared(this.addresses[i].getCityName(), i-1)) {
                    System.out.println(this.addresses[i].getCityName());
                }
            }
            String chosenCity = scanner.nextLine();
            if (!isCityAppeared(chosenCity, this.addresses.length - 1)) {
                System.out.println("There is no such city on the list.");
                canPost = false;
            } else {
                System.out.println("Please select from the following streets (type it):");
                for (int i = 0; i < this.addresses.length; i++) {
                    if (this.addresses[i].getCityName().equalsIgnoreCase(chosenCity)) {
                        System.out.println(this.addresses[i].getStreetName());
                    }
                }
                String chosenStreet = scanner.nextLine();
                int addressIndex = -1;
                for (int i = 0; i < this.addresses.length; i++) {
                    if ((chosenStreet.equalsIgnoreCase(this.addresses[i].getStreetName()) && chosenCity.equalsIgnoreCase(this.addresses[i].getCityName()))) {
                        addressIndex = i;
                        break;
                    }
                }
                canPost = (addressIndex > -1);
                if (canPost) {
                    String type = typePropertyAbsorption(false);
                    int floor = 0;
                    if (!type.equals(propertiesTypes[2])) {
                        System.out.println("Enter a floor number:");
                        floor = scanner.nextInt();
                    }
                    System.out.println("How many rooms are there in the property ?");
                    int roomNumbers = receiveOfPositiveNumbersAndForFiltering(false);
                    System.out.println("What is the property number ?");
                    int propertyNumber = receiveOfPositiveNumbersAndForFiltering(false);
                    String rentOrSale = isForRentPropertyAbsorption(false);
                    System.out.println("What is the required price for the property?");
                    int price = receiveOfPositiveNumbersAndForFiltering(false);
                    Property newProperty = new Property(addresses[addressIndex], roomNumbers, price, type, rentOrSale.equalsIgnoreCase("rent"), propertyNumber, floor, user);
                    addProperty(newProperty);
                } else {
                    System.out.println("There is no such street in the city you have chosen.");
                }
            }
        } else {
            System.out.println("It is not possible to post another property. Your user has reached the limit.");
        }
        return canPost;
    }

    public void removeProperty(User user) {
        int propertyAmount = numberOfUserProperties(user);
        if (propertyAmount > 0) {
            Scanner scanner = new Scanner(System.in);
            System.out.println("Select which property you want to remove :");
            printUserProperties(user);
            int answer = 0;
            do {
                System.out.println("(Type the appropriate property number)");
                answer = scanner.nextInt();
            } while (answer < 1 || answer > propertyAmount);
            Property[] properties = new Property[this.properties.length - 1];
            int indexCounter = 0;
            for (int i = 0; i <this.properties.length; i++) {
                if (this.properties[i].getUser().getName().equals(user.getName()) && indexCounter != answer) {
                    indexCounter++;
                    if (indexCounter == answer) {
                        continue;
                    }
                }
                properties[i - (indexCounter / answer)] = this.properties[i];
            }
            this.properties = properties;
            System.out.println("The property was successfully removed !");
        } else {
            System.out.println("You have no properties to remove.");
        }
    }

    public void printAllProperties() {
        if (this.properties.length>0){
            for (int i = 0; i < this.properties.length; i++) {
                System.out.println("( " + (i + 1) + " )\n" + this.properties[i] + "\n");
            }
        }else {
            System.out.println("There is no properties.");
        }
    }

    public void printUserProperties(User user) {
        int counter = 0;
        for (int i = 0; i < this.properties.length; i++) {
            if (this.properties[i].getUser().getName().equals(user.getName()) && this.properties[i].getUser().getPassword().equals(user.getPassword())) {
                counter++;
                System.out.println("( " + counter + " )\n" + this.properties[i] + "\n");
            }
        }
        if (counter == 0){
            System.out.println("You don't have any properties.");
        }
    }

    public Property[] search() {
        Property [] searchableProperties = new Property[0];
        if (this.properties.length>0){
            Scanner scanner = new Scanner(System.in);
            String rentOrSale = isForRentPropertyAbsorption(true);
            String type = typePropertyAbsorption(true);
            System.out.println("What is the desired number of rooms ?");
            int roomNumbers = receiveOfPositiveNumbersAndForFiltering(true);
            System.out.println("What is the desired price range ?");
            double minPrice = 0;
            double maxPrice = 0;
            do {
                System.out.println("(The minimum price should be less than the maximum)");
                System.out.println("What is the minimum price ?");
                minPrice = (double) receiveOfPositiveNumbersAndForFiltering(true);
                System.out.println("What is the maximum price ?");
                maxPrice = (double) receiveOfPositiveNumbersAndForFiltering(true);
            }while (maxPrice!= intTermSearch && maxPrice<=minPrice);
            boolean checker = false;
            for (int i = 0; i < this.properties.length; i++) {
                checker = (rentOrSale.equals(intTermSearch +"") || (this.properties[i].getForRent() == rentOrSale.equalsIgnoreCase("rent")));
                checker = checker && (type.equals(intTermSearch +"") || type.equalsIgnoreCase(this.properties[i].getType()));
                checker = checker && (roomNumbers == intTermSearch || roomNumbers == this.properties[i].getRoomNumbers());
                checker = checker && ((maxPrice== intTermSearch || maxPrice>=this.properties[i].getPrice()) && (minPrice== intTermSearch || maxPrice<=this.properties[i].getPrice()));
                if (checker){
                    searchableProperties = addPropertyForSearch(this.properties[i], searchableProperties);
                }
            }
        }
        return searchableProperties;
    }

    private String typePropertyAbsorption(boolean isForSearch) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Type the property type: \n" +
                "1 - For a standard apartment in an apartment building\n" +
                "2 - For a penthouse apartment in an apartment building\n" +
                "3 - For a private home");
        int chosenTypeNumber = 0;
        do {
            System.out.println("Type the selection number (1 ,2 or 3) " + ((isForSearch) ? stringTermSearch : ""));
            chosenTypeNumber = scanner.nextInt();
        } while ((chosenTypeNumber != 1 && chosenTypeNumber != 2 && chosenTypeNumber != 3) && (isForSearch && chosenTypeNumber != intTermSearch));
        String type = (chosenTypeNumber != intTermSearch) ? propertiesTypes[chosenTypeNumber - 1] : (chosenTypeNumber + "");
        return type;
    }

    private String isForRentPropertyAbsorption(boolean isForSearch) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Is it for rent or for sale ?");
        String rentOrSale = "---";
        do {
            System.out.println("Type rent or sale " + ((isForSearch) ? stringTermSearch : ""));
            rentOrSale = scanner.nextLine();
        } while ((!rentOrSale.equalsIgnoreCase("sale") && !rentOrSale.equalsIgnoreCase("rent")) && !(isForSearch && rentOrSale.equalsIgnoreCase(intTermSearch + "")));
        return rentOrSale;
    }

    private Property[] addPropertyForSearch(Property newProperty, Property[] propertiesArray) {
        Property[] newPropertiesArray = new Property[propertiesArray.length+1];
        for (int i = 0; i < propertiesArray.length; i++) {
            newPropertiesArray[i] = propertiesArray[i];
        }
        newPropertiesArray[propertiesArray.length]=newProperty;
        return newPropertiesArray;
    }
}