package hibernatecrud;

import hibernatecrud.controllers.PhysicalPersonController;
import hibernatecrud.models.*;
import java.util.List;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Crud {

    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_BLUE = "\u001B[34m";

    public static void PrintMessage(int method, int result) {
        if (method == 1) {
            if (result == 1) {
                System.out.println(ANSI_GREEN + "\n--------------------------------------------|" + ANSI_RESET);
                System.out.println(ANSI_GREEN + "Successfully registered." + ANSI_RESET);
                System.out.println(ANSI_GREEN + "--------------------------------------------|\n" + ANSI_RESET);
            }

            if (result == -1) {
                System.out.println(ANSI_RED + "\n----------------------------------------------|" + ANSI_RESET);
                System.out.println(ANSI_RED + "Error registering." + ANSI_RESET);
                System.out.println(ANSI_RED + "--------------------------------------------|\n" + ANSI_RESET);
            }
        }

        if (method == 2) {
            if (result == 1) {
                System.out.println(ANSI_GREEN + "\n--------------------------------------------|" + ANSI_RESET);
                System.out.println(ANSI_GREEN + "Successfully edited." + ANSI_RESET);
                System.out.println(ANSI_GREEN + "--------------------------------------------|\n" + ANSI_RESET);
            }

            if (result == -1) {
                System.out.println(ANSI_RED + "\n----------------------------------------------|" + ANSI_RESET);
                System.out.println(ANSI_RED + "Error editing." + ANSI_RESET);
                System.out.println(ANSI_RED + "--------------------------------------------|\n" + ANSI_RESET);
            }

            if (result == 0) {
                System.out.println("\n--------------------------------------------|");
                System.out.println("No record found with this ID.");
                System.out.println("--------------------------------------------|\n");

            }
        }

        if (method == 3) {
            if (result == 1) {
                System.out.println(ANSI_GREEN + "\n--------------------------------------------|" + ANSI_RESET);
                System.out.println(ANSI_GREEN + "Successfully deleted." + ANSI_RESET);
                System.out.println(ANSI_GREEN + "--------------------------------------------|\n" + ANSI_RESET);
            }

            if (result == -1) {
                System.out.println(ANSI_RED + "\n----------------------------------------------|" + ANSI_RESET);
                System.out.println(ANSI_RED + "Error deleting." + ANSI_RESET);
                System.out.println(ANSI_RED + "--------------------------------------------|\n" + ANSI_RESET);
            }

            if (result == 0) {
                System.out.println("\n--------------------------------------------|");
                System.out.println("No record delete.");
                System.out.println("--------------------------------------------|\n");
            }
        }

        if (method == 5) {
            if (result == 0) {
                System.out.println("\n--------------------------------------------|");
                System.out.println("No record found with this ID.");
                System.out.println("--------------------------------------------|\n");
            }
        }
    }

    public static boolean ValidateData(String name, String email, String salary, String birthday) {
        boolean result = true;
        List<String> errors = new ArrayList<>();

        if (name.length() < 1) {
            errors.add("- Name is empty.");
            result = false;
        }

        if (email.length() > 1) {
            String regex = "^([_a-zA-Z0-9-]+(\\.[_a-zA-Z0-9-]+)*@[a-zA-Z0-9-]+(\\.[a-zA-Z0-9-]+)*(\\.[a-zA-Z]{1,6}))?$";
            Pattern pattern = Pattern.compile(regex);
            Matcher matcher = pattern.matcher(email);
            if (!matcher.matches()) {
                errors.add("- Invalid e-mail.");
                result = false;
            }

            if (PhysicalPersonController.CheckEmailRegistered(email)) {
                errors.add("- Email already exists.");
                result = false;
            }

        } else {
            errors.add("- Email is empty.");
            result = false;
        }

        if (salary.length() > 1) {
            try {
                if (Float.parseFloat(salary) < 0) {
                    errors.add("- Invalid salary.");
                    result = false;
                }
            } catch (NumberFormatException e) {
                errors.add("- Invalid salary.");
                result = false;
            }
        } else {
            errors.add("- Salary is empty.");
            result = false;
        }

        if (birthday.length() > 1) {
            try {
                Date temp_birthday = Date.valueOf(FormDateToDB(birthday));
            } catch (Exception e) {
                errors.add("- Invalid birthday.");
                result = false;
            }
        } else {
            errors.add("- Birthday is empty.");
            result = false;
        }

        /*
        if (gender.length() > 0) {
            if (!gender.equals("M")) {
                if (!gender.equals("F")) {
                    System.out.println(ANSI_RED + "- Invalid gender." + ANSI_RESET);
                    result = false;
                }
            }
        } else {
            System.out.println(ANSI_RED + "- Gender is empty." + ANSI_RESET);
            result = false;
        }
         */
        if (!errors.isEmpty()) {
            System.out.println(ANSI_RED + "--------------------------------------------|" + ANSI_RESET);
            System.out.println(ANSI_RED + "ERRORS:                                     |" + ANSI_RESET);
            errors.forEach((error) -> {
                System.out.println(ANSI_RED + error + ANSI_RESET);
            });
            System.out.println(ANSI_RED + "--------------------------------------------|\n\n" + ANSI_RESET);
        }
        return result;
    }

    private static String FormDateToDB(String birthday) {
        int day = Integer.parseInt(birthday.substring(0, 2));
        int month = Integer.parseInt(birthday.substring(3, 5));
        int year = Integer.parseInt(birthday.substring(6, 10));
        return year + "-" + month + "-" + day;
    }

    private static String FormDateToPrint(String birthday) {
        int day = Integer.parseInt(birthday.substring(8, 10));
        int month = Integer.parseInt(birthday.substring(5, 7));
        int year = Integer.parseInt(birthday.substring(0, 4));
        return day + "/" + month + "/" + year;
    }

    public static void InsertPhysicalPerson() {
        System.out.println(ANSI_BLUE + "\n\n--------------------------------------------|" + ANSI_RESET);
        System.out.println(ANSI_BLUE + "INSERT PHYSICAL PERSON                      |" + ANSI_RESET);
        System.out.println(ANSI_BLUE + "--------------------------------------------|" + ANSI_RESET);
        Scanner keyboard = new Scanner(System.in);
        System.out.print("Name: ");
        String name = keyboard.nextLine();

        System.out.print("Email: ");
        String email = keyboard.nextLine();

        System.out.print("Salary: ");
        String salary = keyboard.nextLine();

        System.out.print("Birthday (Ex: 01/01/1981): ");
        String birthday = keyboard.nextLine();

        System.out.print("Gender (M or F): ");
        String gender_tmp = keyboard.nextLine();

        Gender gender;
        if (gender_tmp.equals("M")) {
            gender = Gender.M;
        } else {
            gender = Gender.F;
        }

        if (ValidateData(name.trim(), email.trim(), salary.trim(), birthday.trim())) {
            PrintMessage(1, PhysicalPersonController.InsertPhysicalPerson(name, email, Float.parseFloat(salary), Date.valueOf(FormDateToDB(birthday)), gender));
        }
    }

    public static void EditPhysicalPerson() {
        System.out.println(ANSI_BLUE + "\n\n--------------------------------------------|" + ANSI_RESET);
        System.out.println(ANSI_BLUE + "EDIT PHYSICAL PERSON                        |" + ANSI_RESET);
        System.out.println(ANSI_BLUE + "--------------------------------------------|" + ANSI_RESET);

        try {
            Scanner keyboard = new Scanner(System.in);
            System.out.print("ID: ");
            String id = keyboard.nextLine();

            PhysicalPerson pp = PhysicalPersonController.GetPhysicalPersonByID(Integer.parseInt(id));
            if (pp != null) {
                System.out.println("NAME: " + pp.getName());
                System.out.println("EMAIL: " + pp.getEmail());
                System.out.println("SALARY: " + pp.getSalary());
                System.out.println("BIRTHDAY: " + FormDateToPrint(pp.getBirthday().toString()));
                System.out.println("GENDER: " + pp.getGender());
                System.out.println(ANSI_BLUE + "--------------------------------------------|" + ANSI_RESET);
                System.out.println(ANSI_BLUE + "NEW DATA                                    |" + ANSI_RESET);
                System.out.println(ANSI_BLUE + "--------------------------------------------|" + ANSI_RESET);

                System.out.print("Name: ");
                String name = keyboard.nextLine();

                System.out.print("Email: ");
                String email = keyboard.nextLine();

                System.out.print("Salary: ");
                String salary = keyboard.nextLine();

                System.out.print("Birthday (Ex: 01/01/1981): ");
                String birthday = keyboard.nextLine();

                System.out.print("Gender (M or F): ");
                String gender_tmp = keyboard.nextLine();

                Gender gender;
                if (gender_tmp.equals("M")) {
                    gender = Gender.M;
                } else {
                    gender = Gender.F;
                }

                if (ValidateData(name.trim(), email.trim(), salary.trim(), birthday.trim())) {
                    PrintMessage(2, PhysicalPersonController.EditPhysicalPerson(Integer.parseInt(id), name, email, Float.parseFloat(salary), Date.valueOf(FormDateToDB(birthday)), gender));
                }
            } else {
                PrintMessage(2, 0);
            }
        } catch (NumberFormatException e) {
            System.out.println(ANSI_RED + "\n\n--------------------------------------------|" + ANSI_RESET);
            System.out.println(ANSI_RED + "Invalid ID for edition." + ANSI_RESET);
            System.out.println(ANSI_RED + "--------------------------------------------|" + ANSI_RESET);
        }
    }

    public static void DeletePhysicalPerson() {
        System.out.println(ANSI_BLUE + "\n\n--------------------------------------------|" + ANSI_RESET);
        System.out.println(ANSI_BLUE + "DELETE PHYSICAL PERSON                      |" + ANSI_RESET);
        System.out.println(ANSI_BLUE + "--------------------------------------------|" + ANSI_RESET);
        try {
            Scanner keyboard = new Scanner(System.in);
            System.out.print("ID: ");
            String id_temp = keyboard.nextLine();
            int id = Integer.parseInt(id_temp);

            int result = PhysicalPersonController.DeletePhysicalPerson(id);
            PrintMessage(3, result);
        } catch (NumberFormatException e) {
            System.out.println(ANSI_RED + "\n\n--------------------------------------------|" + ANSI_RESET);
            System.out.println(ANSI_RED + "Invalid ID for deletion." + ANSI_RESET);
            System.out.println(ANSI_RED + "--------------------------------------------|" + ANSI_RESET);
        }
    }

    public static void GetPhysicalPersonByName() throws NoSuchFieldException, IllegalArgumentException, IllegalAccessException {
        System.out.println(ANSI_BLUE + "\n\n--------------------------------------------|" + ANSI_RESET);
        System.out.println(ANSI_BLUE + "PHYSICAL PERSON BY NAME                     |" + ANSI_RESET);
        System.out.println(ANSI_BLUE + "--------------------------------------------|" + ANSI_RESET);
        Scanner keyboard = new Scanner(System.in);
        String name = null;

        try {
            System.out.print("NAME: ");
            name = keyboard.nextLine();
            System.out.println(ANSI_BLUE + "\n--------------------------------------------|" + ANSI_RESET);
        } catch (Exception e) {
            name = "";
        }

        List<PhysicalPerson> list = PhysicalPersonController.GetPhysicalPersonByName(name);
        if (list != null) {
            list.stream().map((pp) -> {
                System.out.println("ID: " + pp.getId());
                return pp;
            }).map((pp) -> {
                System.out.println("NAME: " + pp.getName());
                return pp;
            }).map((pp) -> {
                System.out.println("EMAIL: " + pp.getEmail());
                return pp;
            }).map((pp) -> {
                System.out.println("SALARY: " + pp.getSalary());
                return pp;
            }).map((pp) -> {
                System.out.println("BIRTHDAY: " + FormDateToPrint(pp.getBirthday().toString()));
                return pp;
            }).map((pp) -> {
                System.out.println("GENDER: " + pp.getGender());
                return pp;
            }).forEachOrdered((_item) -> {
                System.out.println(ANSI_BLUE + "\n--------------------------------------------|" + ANSI_RESET);
            });
        } else {
            PrintMessage(5, 0);
        }
    }

    public static void GetPhysicalPersonByID() {
        System.out.println(ANSI_BLUE + "\n\n--------------------------------------------|" + ANSI_RESET);
        System.out.println(ANSI_BLUE + "PHYSICAL PERSON BY ID                       |" + ANSI_RESET);
        System.out.println(ANSI_BLUE + "--------------------------------------------|" + ANSI_RESET);
        try {
            Scanner keyboard = new Scanner(System.in);
            System.out.print("ID: ");
            String id = keyboard.nextLine();

            PhysicalPerson pp = PhysicalPersonController.GetPhysicalPersonByID(Integer.parseInt(id));
            if (pp != null) {
                System.out.println("NAME: " + pp.getName());
                System.out.println("EMAIL: " + pp.getEmail());
                System.out.println("SALARY: " + pp.getSalary());
                System.out.println("BIRTHDAY: " + FormDateToPrint(pp.getBirthday().toString()));
                System.out.println("GENDER: " + pp.getGender());
                System.out.println("\n\n");
            } else {
                PrintMessage(5, 0);
            }
        } catch (NumberFormatException e) {
            System.out.println(ANSI_RED + "Invalid ID for search by ID." + ANSI_RESET);
        }
    }

    public static void GetPhysicalPersonByBirthdayRange() {
        String birthday1_str = "";
        String birthday2_str = "";
        System.out.println(ANSI_BLUE + "\n\n--------------------------------------------|" + ANSI_RESET);
        System.out.println(ANSI_BLUE + "GET PHYSICAL PERSON BY BIRTHDAY RANGE       |" + ANSI_RESET);
        System.out.println(ANSI_BLUE + "--------------------------------------------|" + ANSI_RESET);
        Scanner keyboard = new Scanner(System.in);
        String name = null;

        try {
            System.out.print("BIRTHDAY 01 (Ex: 01/01/1981): ");
            birthday1_str = keyboard.nextLine();
        } catch (Exception e) {
            birthday1_str = "";
        }

        try {
            System.out.print("BIRTHDAY 02 (Ex: 01/01/1981): ");
            birthday2_str = keyboard.nextLine();
            System.out.println(ANSI_BLUE + "\n--------------------------------------------|" + ANSI_RESET);
        } catch (Exception e) {
            birthday2_str = "";
        }

        List<PhysicalPerson> list = PhysicalPersonController.GetPersonByBetweenBirthday(Date.valueOf(FormDateToDB(birthday1_str)), Date.valueOf(FormDateToDB(birthday2_str)));
        if (list != null) {
            list.stream().map((pp) -> {
                System.out.println("ID: " + pp.getId());
                return pp;
            }).map((pp) -> {
                System.out.println("NAME: " + pp.getName());
                return pp;
            }).map((pp) -> {
                System.out.println("EMAIL: " + pp.getEmail());
                return pp;
            }).map((pp) -> {
                System.out.println("SALARY: " + pp.getSalary());
                return pp;
            }).map((pp) -> {
                System.out.println("BIRTHDAY: " + FormDateToPrint(pp.getBirthday().toString()));
                return pp;
            }).map((pp) -> {
                System.out.println("GENDER: " + pp.getGender());
                return pp;
            }).forEachOrdered((_item) -> {
                System.out.println(ANSI_BLUE + "\n--------------------------------------------|" + ANSI_RESET);
            });
        } else {
            PrintMessage(5, 0);
        }
    }

    public static void GetStatisticsOfPhysicalPerson() {
        System.out.println(ANSI_BLUE + "\n\n--------------------------------------------|" + ANSI_RESET);
        System.out.println(ANSI_BLUE + "GET STATISTICS OF PHYSICAL PERSON           |" + ANSI_RESET);
        System.out.println(ANSI_BLUE + "--------------------------------------------|" + ANSI_RESET);
        try {
            //Older Physical Person

            PhysicalPerson pp1 = PhysicalPersonController.GetOlderPhysicalPerson();
            if (pp1 != null) {
                System.out.println("\nOlder Physical Person: ");
                System.out.println("\nID: " + pp1.getId());
                System.out.println("NAME: " + pp1.getName());
                System.out.println("EMAIL: " + pp1.getEmail());
                System.out.println("SALARY: " + pp1.getSalary());
                System.out.println("BIRTHDAY: " + FormDateToPrint(pp1.getBirthday().toString()));
                System.out.println("GENDER: " + pp1.getGender());
                System.out.println("\n\n");
            } else {
                PrintMessage(5, 0);
            }

            //Younger Physical Person
            PhysicalPerson pp2 = PhysicalPersonController.GetYoungerPhysicalPerson();
            if (pp2 != null) {
                System.out.println("\nYounger Physical Person: ");
                System.out.println("\nID: " + pp2.getId());
                System.out.println("NAME: " + pp2.getName());
                System.out.println("EMAIL: " + pp2.getEmail());
                System.out.println("SALARY: " + pp2.getSalary());
                System.out.println("BIRTHDAY: " + FormDateToPrint(pp2.getBirthday().toString()));
                System.out.println("GENDER: " + pp2.getGender());
                System.out.println("\n\n");
            } else {
                PrintMessage(5, 0);
            }
            System.out.println("\nThe average salary =  " + PhysicalPersonController.GetAVGPhysicalPersonSalary());

            System.out.println("\nNumber of registrations: " + PhysicalPersonController.CountPhysicalPerson());

            System.out.println(ANSI_BLUE + "--------------------------------------------|" + ANSI_RESET);
        } catch (NumberFormatException e) {
            System.out.println(ANSI_RED + "Invalid ID for search by ID." + ANSI_RESET);
        }
    }
}
