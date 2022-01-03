import java.util.Scanner;
public class Main {
    final static int creatAnAccount =1;
    final static int loginToExistingAccount =2;
    final static int finishTheProgram =3;
    final static int postANewProperty =1;
    final static int removeAdvertisingOnAProperty =2;
    final static int viewAllPropertiesInTheSystem =3;
    final static int viewAllPropertiesPostedByTheUser =4;
    final static int searchForPropertyByParameters =5;
    final static int disconnectAndReturnToTheMainMenu =6;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        RealEstate realEstate = new RealEstate();
        int userAnswer = 0;
        while (userAnswer!=finishTheProgram){
            System.out.println("Hello, what are you interested in:\n" +
                    "1 - Create an account\n" +
                    "2 - Log in to an existing account\n" +
                    "3 - Finish the program");
            userAnswer = scanner.nextInt();
            while (userAnswer<1 && userAnswer>3){
                System.out.println("(Type 1, 2 or 3)");
            }
            switch (userAnswer){
                case creatAnAccount :
                    realEstate.createUser();
                    break;
                case loginToExistingAccount:
                    User user = realEstate.login();
                    if (user != null){
                        while (userAnswer!=disconnectAndReturnToTheMainMenu){
                            System.out.println("What are you interested in:\n" +
                                    "1 - Post a new property\n" +
                                    "2 - Remove advertising on a property\n" +
                                    "3 - View all properties in the system\n" +
                                    "4 - View all properties posted by the user\n" +
                                    "5 - Search for property by parameters\n" +
                                    "6 - Disconnect and return to the main menu");
                            userAnswer=scanner.nextInt();
                            switch (userAnswer){
                                case postANewProperty:
                                    System.out.println((realEstate.postNewProperty(user)) ? "Property successfully added!" : "The property was not added.");
                                    break;
                                case removeAdvertisingOnAProperty:
                                    realEstate.removeProperty(user);
                                    break;
                                case viewAllPropertiesInTheSystem:
                                    realEstate.printAllProperties();
                                    break;
                                case viewAllPropertiesPostedByTheUser:
                                    realEstate.printUserProperties(user);
                                    break;
                                case searchForPropertyByParameters:
                                    Property[] searchableProperties = realEstate.search();
                                    System.out.println((searchableProperties.length > 0) ? "The properties found that match your requests are:" : "No properties found ...");
                                    for (int i = 0; i < searchableProperties.length; i++) {
                                        System.out.println("( " + (i + 1) + " )\n" + searchableProperties[i] + "\n");
                                    }
                                    break;
                                case disconnectAndReturnToTheMainMenu:
                                    System.out.println("You're back to the main menu:");
                                    break;
                                default:
                                    System.out.println("Type the desired operation number (1-6):");
                                    break;
                            }
                        }
                    }
                    break;
                case finishTheProgram:
                    System.out.println("You left the program");
                    break;
                default:
                    System.out.println("(Type 1, 2 or 3)");
                    break;
            }
        }
    }
}
