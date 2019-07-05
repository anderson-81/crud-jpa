package emcrud;

import java.util.Scanner;

public class Main {

    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_BLUE = "\u001B[34m";

    public static void main(String[] args) {

        int opc = 1;
        while (opc != 0) {

            System.out.println(ANSI_BLUE + "-----------------------------------------------|" + ANSI_RESET);
            System.out.println(ANSI_BLUE + "REGISTRATION                                   |" + ANSI_RESET);
            System.out.println(ANSI_BLUE + "-----------------------------------------------|" + ANSI_RESET);
            System.out.println(ANSI_BLUE + " 1 - INSERT PHYSICAL PHYSICAL PERSON           |" + ANSI_RESET);
            System.out.println(ANSI_BLUE + " 2 - EDIT PHYSICAL PHYSICAL PERSON             |" + ANSI_RESET);
            System.out.println(ANSI_BLUE + " 3 - DELETE PHYSICAL PHYSICAL PERSON           |" + ANSI_RESET);
            System.out.println(ANSI_BLUE + " 4 - GET PHYSICAL PHYSICAL PERSON BY NAME      |" + ANSI_RESET);
            System.out.println(ANSI_BLUE + " 5 - GET PHYSICAL PHYSICAL PERSON BY ID        |" + ANSI_RESET);
            System.out.println(ANSI_BLUE + " 6 - GET PHYSICAL PERSON BY BY BIRTHDAY RANGE  |" + ANSI_RESET);
            System.out.println(ANSI_BLUE + " 7 - GET PHYSICAL PERSON'S STATISTICS          |" + ANSI_RESET);
            System.out.println(ANSI_BLUE + "-----------------------------------------------|" + ANSI_RESET);

            try {
                Scanner keyboard = new Scanner(System.in);
                System.out.print("Enter option: ");
                opc = keyboard.nextInt();

                switch (opc) {
                    case 1:
                        Crud.InsertPhysicalPerson();
                        break;
                    case 2:
                        Crud.EditPhysicalPerson();
                        break;
                    case 3:
                        Crud.DeletePhysicalPerson();
                        break;
                    case 4:
                        Crud.GetPhysicalPersonByName();
                        break;
                    case 5:
                        Crud.GetPhysicalPersonByID();
                        break;
                    case 6:
                        Crud.GetPhysicalPersonByBirthdayRange();
                        break;
                    case 7:
                        Crud.GetStatisticsOfPhysicalPerson();
                        break;
                    default:
                        System.out.println("FINISH.");
                        break;
                }
            } catch (IllegalAccessException | IllegalArgumentException | NoSuchFieldException e) {
                System.out.println("FINISH.");
                opc = 0;
            }
        }
    }
}
