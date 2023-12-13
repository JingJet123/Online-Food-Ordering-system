import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import static java.lang.System.exit;

public class HostSystem {
    public static void main(String[] args) throws IOException, ParseException {
        char relogin;
        do {
            boolean end = true;
            ArrayList<Customer> custDetails = getCustDetails();
            Customer userLogin = login(custDetails);
            //File Details - We will use these variables (objects) to pass through whole program : To Keep All Values Run Through - To Keep All Details From FIle Updated If Changes
            ArrayList<Reservation> allReservationDetails = getAllReserveDetails(userLogin);
            ArrayList<Payment> allPaymentDetails = getAllPaymentDetails(allReservationDetails);
            ArrayList<Table> tableDetails = getTableDetails();
            //Current Details - We will use these variables (objects) to pass through whole program : To Keep All Values Run Through In Run Time
            ArrayList<OrderDetails> orderDetails = new ArrayList<>();
            Reservation reservation = new Reservation();
            ArrayList<Table> tables = new ArrayList<>();
            Order order = new Order();
            Payment payDetails = new Payment();
            do {
                switch (mainMenu()) {
                    case 1 -> printAllProdDetails();
                    case 2 -> custProfile(userLogin, custDetails);
                    case 3 -> reservation = reservationMenu(userLogin, tableDetails, orderDetails, tables, order, reservation, allReservationDetails, allPaymentDetails);
                    case 4 -> paymentMenu(payDetails, reservation, allPaymentDetails);
                    case 5, 6 ->  end = logoutConfirmation();      //Both Re-Build Reservation & Log-Out Require Confirming and Re-Login
                    case 7 -> {
                        HostSystem.logoDisplay(17);
                        exit(-1);
                    }
                    default -> {
                        System.out.print(PrintColor.RED_BOLD);
                        System.out.printf("\n%68s===========================================\n", "");
                        System.out.printf("%86s|||||||\n", "");
                        System.out.printf("%86s|||||||\n", "");
                        System.out.printf("%60s##~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~##\n", "");
                        System.out.printf("%60s||        Invalid SELECTION!! Please TRY again...        ||\n", "");
                        System.out.printf("%60s||         Input should be in numeric form (1-5)         ||\n", "");
                        System.out.printf("%60s##~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~##\n\n", "");
                    }
                }
            } while(end);
            HostSystem.logoDisplay(24);
            System.out.print(PrintColor.CYAN);
            System.out.printf("%49s[-------------------------------------------------------------------------------]\n", "");
            System.out.printf("%49s[                          !!!   Kindly Reminder   !!!                          ]\n", "");
            System.out.printf("%49s[            You Can Opt to Re-Login If Desire Continue Using Program           ]\n", "");
            System.out.printf("%49s[                     ---> All Information Will Be Refreshed                    ]\n", "");
            System.out.printf("%49s[                     ---> Otherwise, Exit Program Promptly                     ]\n", "");
            System.out.printf("%49s[-------------------------------------------------------------------------------]\n", "");
            System.out.print(PrintColor.RESET);
            relogin = HostSystem.charValidation("%63sDo You Want To Re-Login System (Y/y = yes) >>>> ", 1);
            System.out.println("\n\n\n");
        }while(Character.toUpperCase(relogin) == 'Y');
        HostSystem.logoDisplay(17);
        exit(-1);
    }

    public static int mainMenu() {
        HostSystem.logoDisplay(4);
        System.out.print(PrintColor.CYAN_BOLD);
        System.out.printf("%63s##=================##==============================##\n", "");
        System.out.printf("%63s||             Enjoy & Start Your Day              ||\n", "");
        System.out.printf("%63s##-----------------##------------------------------##\n", "");
        System.out.printf("%63s||    Selection    ||          Procedures          ||\n", "");
        System.out.printf("%63s##-----------------##------------------------------##\n", "");
        System.out.printf("%63s||        1.       ||     Restaurant Menu          ||\n", "");
        System.out.printf("%63s||        2.       ||     My Profile               ||\n", "");
        System.out.printf("%63s||        3.       ||     My Reservation           ||\n", "");
        System.out.printf("%63s||        4.       ||     My Payment               ||\n", "");
        System.out.printf("%63s##=================##==============================##\n", "");
        System.out.printf("%63s||       Both Re-Build Reservation OR Logout       ||\n", "");
        System.out.printf("%63s||                Require Re-Login                 ||\n", "");
        System.out.printf("%63s||****                                          ***||\n", "");
        System.out.printf("%63s||         [ 5 ]    Make New Reservation           ||\n", "");
        System.out.printf("%63s||         [ 6 ]    Logout Current Account         ||\n", "");
        System.out.printf("%63s||         [ 7 ]    Exit Program                   ||\n", "");
        System.out.printf("%63s||****                                          ***||\n", "");
        System.out.printf("%63s##=================##==============================##\n", "");
        System.out.print(PrintColor.RESET);
        System.out.print(PrintColor.CYAN_BRIGHT);
        System.out.printf("%66s~~~  What Selection Would You Want To Go ?  ~~~\n", "");
        System.out.print(PrintColor.RESET);
        return HostSystem.intValidation("%69sPlease Enter Selection (in number) ---> ");
    }


    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //Customer
    public static ArrayList<Customer> getCustDetails() {
        ArrayList<Customer> custDetails = new ArrayList<>();
        int index = 0;
        try {
            Scanner fileScan = new Scanner(new File("Customer.txt"));
            while(fileScan.hasNextLine()) {
                Customer getCust = new Customer();
                Scanner lineScan = new Scanner(fileScan.nextLine()).useDelimiter("/");
                while(lineScan.hasNext()) {
                    String data = lineScan.next();
                    switch(index) {
                        case 0 -> getCust.setUserID(data);
                        case 1 -> getCust.setPassword(data);
                        case 2 -> getCust.setCustName(data);
                        case 3 -> getCust.setGender((data).charAt(0));
                        case 4 -> getCust.setCustContact(data);
                        case 5 -> getCust.setCustEmail(data);
                        case 6 -> getCust.setMemberType(data);
                    }
                    index++;
                }
                index = 0;
                custDetails.add(getCust);
                lineScan.close();
            }
            fileScan.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return custDetails;
    }

    public static void custProfile(Customer userLogin, ArrayList<Customer> custDetails) throws FileNotFoundException, ParseException {
        boolean end = true;
        do {
            HostSystem.logoDisplay(20);
            System.out.print(PrintColor.CYAN_BOLD);
            System.out.printf("%60s##=============##=======================================##\n", "");
            System.out.printf("%60s||  Selection  ||     Procedures                        ||\n", "");
            System.out.printf("%60s##-------------##---------------------------------------##\n", "");
            System.out.printf("%60s||     1.      ||     Sign-Up Another New Account       ||\n", "");
            System.out.printf("%60s||     2.      ||     View Personal Details             ||\n", "");
            System.out.printf("%60s||     3.      ||     Edit Personal Details             ||\n", "");
            System.out.printf("%60s||     4.      ||     Show My History                   ||\n", "");
            System.out.printf("%60s##-------------##---------------------------------------##\n", "");
            System.out.printf("%60s||     5.      ||     Back To The Previous Menu         ||\n", "");
            System.out.printf("%60s##=============##=======================================##\n", "");
            System.out.print(PrintColor.RESET);
            switch(HostSystem.intValidation("%69sPlease Enter Selection (in number) ---> ")) {
                case 1 -> signUpCust(custDetails);
                case 2 -> printProfileDetails(userLogin);
                case 3 -> userLogin = editProfile(custDetails, userLogin);
                case 4 -> historyMenu(userLogin);
                case 5 -> end = false;
                default -> {
                    System.out.print(PrintColor.RED_BOLD);
                    System.out.printf("\n%68s===========================================\n", "");
                    System.out.printf("%86s|||||||\n", "");
                    System.out.printf("%86s|||||||\n", "");
                    System.out.printf("%60s##~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~##\n", "");
                    System.out.printf("%60s||        Invalid SELECTION!! Please TRY again...        ||\n", "");
                    System.out.printf("%60s||         Input should be in numeric form (1-4)         ||\n", "");
                    System.out.printf("%60s##~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~##\n", "");
                    System.out.print(PrintColor.RESET);
                }
            }
        }while(end);
    }

    public static void signUpCust(ArrayList<Customer> custDetails) {
        Customer custSignUp = new Customer();
        int end = 0;
        do {
            HostSystem.logoDisplay(18);
            System.out.print(PrintColor.CYAN_BRIGHT);
            System.out.printf("%69sHere To Get A New Account: \n", "");
            System.out.printf("%69s------------------------------\n", "");
            System.out.print(PrintColor.RESET);
            custSignUp.setUserID(HostSystem.strValidation("%69sUserID: ", 6));
            custSignUp.setPassword(HostSystem.strValidation("%69sPassword: ", 2));
            custSignUp.setCustName(HostSystem.strValidation("%69sFull Name: ", 1));
            custSignUp.setGender(HostSystem.charValidation("%69sGender (M/F): ", 2));
            custSignUp.setCustContact(HostSystem.strValidation("%69sContact Number: ", 4));
            custSignUp.setCustEmail(HostSystem.strValidation("%69sEmail Address: ", 5));
            custSignUp.setMemberType("Member");
            for (Customer c : custDetails) {
                if (c.equals(custSignUp)) {
                    System.out.print(PrintColor.BLUE);
                    System.out.printf("\n%68s===========================================\n", "");
                    System.out.printf("%86s|||||||\n", "");
                    System.out.printf("%86s|||||||\n", "");
                    System.out.printf("%60s##~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~##\n", "");
                    System.out.printf("%60s||     UserID & Password Had Been Registered By Others     ||\n", "");
                    System.out.printf("%60s||          Please Sign-Up New Account If Needs...         ||\n", "");
                    System.out.printf("%60s##~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~##\n\n", "");
                    System.out.print(PrintColor.RESET);
                    end++;
                }
            }
        }while(end != 0);
        System.out.print(PrintColor.BLUE);
        System.out.printf("\n%68s===========================================\n", "");
        System.out.printf("%86s|||||||\n", "");
        System.out.printf("%86s|||||||\n", "");
        System.out.printf("%60s##~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~##\n", "");
        System.out.printf("%60s||     You have successfully SIGN-UP a new account !!!     ||\n", "");
        System.out.printf("%60s||           Hope you glad and enjoy using here.           ||\n", "");
        System.out.printf("%60s##~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~##\n\n", "");
        custDetails.add(custSignUp);
        custWriteToFile(custDetails);
    }

    public static void printProfileDetails(Customer cust) {
        System.out.print(PrintColor.BLUE_BRIGHT);
        System.out.printf("\n\n%65s <<<<<:::       Profile Details       :::>>>>>\n", "");
        System.out.printf("%25s+===============================================================================================================================+\n", "");
        System.out.printf("%25s| %-14s | %-14s | %-22s | %-6s | %-15s | %-23s | %-13s |\n", "", "User ID", "Password", "Full Name", "Gender", "Contact No", "Email Address", "Member Type");
        System.out.printf("%25s+===============================================================================================================================+\n", "");
        System.out.printf("%25s| %-14s | %-14s | %-22s | %-6s | %-15s | %-23s |    %-10s |\n", "", "", "", "", "", "", "", "");
        System.out.print(cust.toString());
        System.out.printf("%25s| %-14s | %-14s | %-22s | %-6s | %-15s | %-23s |    %-10s |\n", "", "", "", "", "", "", "", "");
        System.out.printf("%25s+===============================================================================================================================+\n", "");
        System.out.print(PrintColor.RESET);
    }

    public static Customer editProfile(ArrayList<Customer> custDetails, Customer userLogin) {
        for (Customer c : custDetails) {
            if (Objects.equals(c.getUserID(), userLogin.getUserID())) {
                char nextEdit;
                do {
                    printProfileDetails(c);
                    HostSystem.logoDisplay(19);
                    System.out.print(PrintColor.CYAN_BOLD);
                    System.out.printf("%63s##===============##===============================##\n", "");
                    System.out.printf("%63s||   Selection   ||     Fields To Edit            ||\n", "");
                    System.out.printf("%63s##---------------##-------------------------------##\n", "");
                    System.out.printf("%63s||       1.      ||     User ID                   ||\n", "");
                    System.out.printf("%63s||       2.      ||     Password                  ||\n", "");
                    System.out.printf("%63s||       3.      ||     Name                      ||\n", "");
                    System.out.printf("%63s||       4.      ||     Gender                    ||\n", "");
                    System.out.printf("%63s||       5.      ||     Contact Number            ||\n", "");
                    System.out.printf("%63s||       6.      ||     Email Address             ||\n", "");
                    System.out.printf("%63s##---------------##-------------------------------##\n", "");
                    System.out.printf("%63s||       7.      ||     Back To Previous          ||\n", "");
                    System.out.printf("%63s##===============##===============================##\n", "");
                    System.out.print(PrintColor.RESET);
                    System.out.print(PrintColor.CYAN_BRIGHT);
                    System.out.printf("%66s~~~   What Field Would You Want To Edit ?   ~~~\n", "");
                    System.out.print(PrintColor.RESET);
                    switch (HostSystem.intValidation("%69sPlease Enter Selection (in number) ---> ")) {
                        case 1 -> {
                            System.out.print(PrintColor.CYAN_BRIGHT);
                            System.out.printf("\n\n%60sEdit Specific Data\n", "");
                            System.out.printf("%60sCurrent Record: %s\n", "", c.getUserID());
                            System.out.printf("%60s==========================================================\n", "");
                            System.out.print(PrintColor.RESET);
                            String newUserID = HostSystem.strValidation("%60sEnter NEW User ID : ", 6);
                            if(editConfirmation(c, newUserID)) { c.setCustName(newUserID); userLogin = c; }
                        }
                        case 2 -> {
                            System.out.print(PrintColor.CYAN_BRIGHT);
                            System.out.printf("\n\n%60sEdit Specific Data\n", "");
                            System.out.printf("%60sCurrent Record: %s\n", "", c.getPassword());
                            System.out.printf("%60s==========================================================\n", "");
                            System.out.print(PrintColor.RESET);
                            String newPassword = HostSystem.strValidation("%60sEnter NEW Password : ", 2);
                            if (editConfirmation(c, newPassword)) { c.setPassword(newPassword); userLogin = c;}
                        }
                        case 3 -> {
                            System.out.print(PrintColor.CYAN_BRIGHT);
                            System.out.printf("\n\n%60sEdit Specific Data\n", "");
                            System.out.printf("%60sCurrent Record: %s\n", "", c.getCustName());
                            System.out.printf("%60s==========================================================\n", "");
                            System.out.print(PrintColor.RESET);
                            String newName = HostSystem.strValidation("%60sEnter NEW Name : ", 1);
                            if(editConfirmation(c, "")) { c.setCustName(newName); userLogin = c;}
                        }
                        case 4 -> {
                            System.out.print(PrintColor.CYAN_BRIGHT);
                            System.out.printf("\n\n%60sEdit Specific Data\n", "");
                            System.out.printf("%60sCurrent Record: %s\n", "", c.getGender());
                            System.out.printf("%60s==========================================================\n", "");
                            System.out.print(PrintColor.RESET);
                            char newGender = HostSystem.charValidation("%60sEnter NEW Gender : ", 2);
                            if(editConfirmation(c, "")) { c.setGender(newGender); userLogin = c;}
                        }
                        case 5 -> {
                            System.out.print(PrintColor.CYAN_BRIGHT);
                            System.out.printf("\n\n%60sEdit Specific Data\n", "");
                            System.out.printf("%60sCurrent Record: %s\n", "", c.getCustContact());
                            System.out.printf("%60s==========================================================\n", "");
                            System.out.print(PrintColor.RESET);
                            String newContactNo = HostSystem.strValidation("%60sEnter NEW Contact No : ", 4);
                            if(editConfirmation(c, "")) { c.setCustContact(newContactNo); userLogin = c;}
                        }
                        case 6 -> {
                            System.out.print(PrintColor.CYAN_BRIGHT);
                            System.out.printf("\n\n%60sEdit Specific Data\n", "");
                            System.out.printf("%60sCurrent Record: %s\n", "", c.getCustEmail());
                            System.out.printf("%60s==========================================================\n", "");
                            System.out.print(PrintColor.RESET);
                            String newEmail = HostSystem.strValidation("%60sEnter NEW Email Address : ", 5);
                            if(editConfirmation(c, "")) {  c.setCustEmail(newEmail); userLogin = c;}
                        }
                        case 7 -> { return userLogin; }
                        default -> {
                            System.out.print(PrintColor.RED_BOLD);
                            System.out.printf("\n%68s===========================================\n", "");
                            System.out.printf("%86s|||||||\n", "");
                            System.out.printf("%86s|||||||\n", "");
                            System.out.printf("%60s##~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~##\n", "");
                            System.out.printf("%60s||        Invalid SELECTION!! Please TRY again...        ||\n", "");
                            System.out.printf("%60s||         Input should be in numeric form (1-7)         ||\n", "");
                            System.out.printf("%60s##~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~##\n", "");
                            System.out.print(PrintColor.RESET);
                        }
                    }
                    System.out.println(PrintColor.CYAN_BRIGHT);
                    System.out.printf("\n%47s   ++++++[    Should Continue To Edit Details, Type 'Y' or 'y' !!!    ]++++++\n", "");
                    System.out.printf("%45s----------------------------------------------------------------------------------------\n", "");
                    nextEdit = HostSystem.charValidation("%55sContinue To Edit Other Data For Your Profile Details (Y/y = yes) >>> ", 1);
                    System.out.println(PrintColor.RESET);
                } while (Character.toUpperCase(nextEdit) == 'Y');
            }
        }
        custWriteToFile(custDetails);
        System.out.print(PrintColor.BLUE);
        System.out.printf("\n%68s===========================================\n", "");
        System.out.printf("%86s|||||||\n", "");
        System.out.printf("%86s|||||||\n", "");
        System.out.printf("%60s##~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~##\n", "");
        System.out.printf("%60s||     You Have Successfully Edited Personal Details     ||\n", "");
        System.out.printf("%60s||             Please TRY Again If Any Needs             ||\n", "");
        System.out.printf("%60s##~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~##\n\n", "");
        System.out.print(PrintColor.RESET);
        return userLogin;
    }

    public static boolean editConfirmation(Customer allCustDetails, String newDetails) {
        if(Objects.equals(allCustDetails.getUserID(), newDetails) || Objects.equals(allCustDetails.getPassword(), newDetails)) {
            System.out.print(PrintColor.BLUE);
            System.out.printf("\n%68s===========================================\n", "");
            System.out.printf("%86s|||||||\n", "");
            System.out.printf("%86s|||||||\n", "");
            System.out.printf("%60s##~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~##\n", "");
            System.out.printf("%60s||     UserID & Password Had Been Registered By Others     ||\n", "");
            System.out.printf("%60s||          Please Sign-Up New Account If Needs...         ||\n", "");
            System.out.printf("%60s##~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~##\n\n", "");
            System.out.print(PrintColor.RESET);
            return false;
        }
        System.out.print(PrintColor.CYAN_BRIGHT);
        System.out.printf("\n%49s   ++++++[    Required To Confirm EDITION By Typing 'Y' or 'y' !!!    ]++++++\n", "");
        System.out.printf("%45s----------------------------------------------------------------------------------------\n", "");
        System.out.print(PrintColor.RESET);
        char confirmation = HostSystem.charValidation("%65sAre You Confirm To Edit This Data (Y/y = Yes): ", 1);
        System.out.print(PrintColor.BLUE);
        if (Customer.checkConfirmation(confirmation)) {
            System.out.printf("\n%68s===========================================\n", "");
            System.out.printf("%86s|||||||\n", "");
            System.out.printf("%86s|||||||\n", "");
            System.out.printf("%60s##~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~##\n", "");
            System.out.printf("%60s||              You Have Confirmed Edition               ||\n", "");
            System.out.printf("%60s||        No Operation Will Be Made On This Record       ||\n", "");
            System.out.printf("%60s##~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~##\n", "");
            System.out.print(PrintColor.RESET);
            return true;
        } else {
            System.out.printf("\n%68s===========================================\n", "");
            System.out.printf("%86s|||||||\n", "");
            System.out.printf("%86s|||||||\n", "");
            System.out.printf("%60s##~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~##\n", "");
            System.out.printf("%60s||             You Haven't Confirmed Edition             ||\n", "");
            System.out.printf("%60s||        No Operation Will Be Made On This Record       ||\n", "");
            System.out.printf("%60s##~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~##\n", "");
            System.out.print(PrintColor.RESET);
            return false;
        }
    }

    public static void custWriteToFile(ArrayList<Customer> custDetails) {
        try {
            FileWriter writer = new FileWriter("Customer.txt");
            int size = custDetails.size();
            for (int i = 0; i < size; i++) {
                String str = custDetails.get(i).contentWriteToFile();
                writer.write(str);
                if (i < size - 1)
                    writer.write("\n");
            }
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void historyMenu(Customer userLogin) {
        ArrayList<Reservation> allReservationDetails = getAllReserveDetails(userLogin);
        ArrayList<Payment> allPaymentDetails = getAllPaymentDetails(allReservationDetails);
        boolean end = true;
        do {
            logoDisplay(29);
            System.out.print(PrintColor.CYAN_BOLD);
            System.out.printf("%66s##=============++===========================##\n", " ");
            System.out.printf("%66s||  Selection  ||       History Types       ##\n", " ");
            System.out.printf("%66s##-------------++---------------------------##\n", " ");
            System.out.printf("%66s||      1      ||      Reservation          ##\n", " ");
            System.out.printf("%66s||      2      ||      Table                ##\n", " ");
            System.out.printf("%66s||      3      ||      Order Details        ##\n", " ");
            System.out.printf("%66s||      4      ||      Payment              ##\n", " ");
            System.out.printf("%66s##-------------++---------------------------##\n", " ");
            System.out.printf("%66s||      5      ||      Back To Previous     ##\n", " ");
            System.out.printf("%66s##=============++===========================##\n", " ");
            System.out.print(PrintColor.RESET);
            switch (HostSystem.intValidation("%68sPlease Enter Selection (in number) ---> ")) {
                case 1 -> reservationHistory(allReservationDetails, userLogin);
                case 2 -> tableHistory(userLogin, allReservationDetails);
                case 3 -> orderHistory(userLogin, allReservationDetails);
                case 4 -> paymentHistory(userLogin, allReservationDetails, allPaymentDetails);
                case 5 -> end = false;
                default -> {
                    System.out.print(PrintColor.RED_BOLD);
                    System.out.printf("\n%68s===========================================\n", "");
                    System.out.printf("%86s|||||||\n", "");
                    System.out.printf("%86s|||||||\n", "");
                    System.out.printf("%60s##~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~##\n", "");
                    System.out.printf("%60s||        Invalid SELECTION!! Please TRY again...        ||\n", "");
                    System.out.printf("%60s||         Input should be in numeric form (1-5)         ||\n", "");
                    System.out.printf("%60s##~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~##\n", "");
                    System.out.print(PrintColor.RESET);
                }
            }
        }while(end);
    }

    public static void reservationHistory(ArrayList<Reservation>  allReservationDetails, Customer userLogin){
        int recordReserve = 0;
        logoDisplay(25);
        System.out.print(PrintColor.PURPLE_BOLD);
        System.out.printf("\n%66s[[~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~]]\n", "");
        System.out.printf("%66s[[    User ID      :  %-20s  ]]\n", "", userLogin.getUserID());
        System.out.printf("%66s[[    Reserved By  :  %-20s  ]]\n", "", userLogin.getCustName());
        System.out.printf("%66s[[    Contact No   :  %-20s  ]]\n", "", userLogin.getCustContact());
        System.out.printf("%66s[[~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~]]\n", "");
        System.out.printf("%86s||||||\n", "");
        System.out.printf("%86s||||||\n", "");
        System.out.printf("%37s++====================++======================++======================++============++================++\n", "");
        System.out.printf("%37s||   %-16s ||   %-17s  ||   %-18s ||   %-8s ||    %-11s ||\n", "", "Reservation ID", "Reservation Date ", "Reservation Time", "Person","Order ID");
        System.out.printf("%37s++====================++======================++======================++============++================++\n", "");
        for(Reservation r : allReservationDetails) {
            if(r.getCust().getUserID().equals(userLogin.getUserID())) {
                System.out.printf("%28s [ %2d ]  ||   %11s%4s  ||   %13s%4s  ||     %s - %s%3s || %6d%4s || %11s%3s ||\n" , " ", ++recordReserve, r.getReserveID(), " ",
                        new SimpleDateFormat("dd-MM-yyyy").format(r.getReserveDate()), " ", new SimpleDateFormat("HH:mm").format(r.getStartTime()),
                        new SimpleDateFormat("HH:mm").format(r.getEndTime()), " ", r.getNoOfPerson(), " ", r.getOrder().getOrderID(), " ");
                System.out.printf("%37s++--------------------++----------------------++----------------------++------------++----------------++\n", "");
            }
        }
        if(recordReserve>0) {
            System.out.printf("\n%30s>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>        THERE ARE %3d RESERVATION HISTORY RECORD.         <<<<<<<<<<<<<<<<<<<<<<<<<<<<<\n"," ",recordReserve);
        } else if(recordReserve == 0){
            System.out.println(PrintColor.BLUE);
            System.out.printf("\n%68s===========================================\n", "");
            System.out.printf("%86s|||||||\n", "");
            System.out.printf("%86s|||||||\n", "");
            System.out.printf("%60s##~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~##\n", "");
            System.out.printf("%60s||                Hello Dear Customer !!!                ||\n", "");
            System.out.printf("%60s||       There's No Reservation Records For You...       ||\n", "");
            System.out.printf("%60s##~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~##\n", "");
        }
        System.out.print(PrintColor.RESET);
    }

    public static void tableHistory(Customer userLogin, ArrayList<Reservation> allReservationDetails) {
        int recordTable = 0;
        logoDisplay(27);
        System.out.print(PrintColor.PURPLE_BOLD);
        System.out.printf("\n%66s[[~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~]]\n", "");
        System.out.printf("%66s[[    User ID      :  %-20s  ]]\n", "", userLogin.getUserID());
        System.out.printf("%66s[[    Reserved By  :  %-20s  ]]\n", "", userLogin.getCustName());
        System.out.printf("%66s[[    Contact No   :  %-20s  ]]\n", "", userLogin.getCustContact());
        System.out.printf("%66s[[~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~]]\n", "");
        System.out.printf("%86s||||||\n", "");
        System.out.printf("%86s||||||\n", "");
        System.out.printf("%35s++==============++============++=======================++===============++===============================++\n", "");
        System.out.printf("%35s||   %-10s ||  %-8s  ||   %-19s ||   %s  ||    %-25s  ||\n", "", "Table ID", "Max Seat", "Table Description","Charges(%)","Reservation Details");
        System.out.printf("%35s++================++==========++=======================++===============++===============================++\n", "");
        for (Reservation r : allReservationDetails) {
            for(Reservation r1 : allReservationDetails) {
                for (Table table : r.getTables()) {
                    if (r.getCust().getUserID().equals(userLogin.getUserID()) && r.getReserveDate().equals(r1.getReserveDate())) {
                        System.out.printf("%26s [ %2d ]  ||  %7s%4s || %6d%4s ||   %-17s   || %9.2f%4s ||    %s  ~  %-13s ||\n", " ", ++recordTable, table.getTableID(), " ", table.getMaxSeat(),
                                " ", table.getTableDesc(), table.getTableCharges(), " ", r.getReserveID(), new SimpleDateFormat("dd-MM-yyyy").format(r.getReserveDate()));
                    }
                }
            }
            if(r.getCust().getUserID().equals(userLogin.getUserID())) {
                System.out.printf("%35s++----------------++----------++-----------------------++---------------++-------------------------------++\n", "");
            }
        }
        if(recordTable > 0) {
            System.out.printf("\n%33s>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>       THERE ARE %3d TABLE RESERVED RECORDS         <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<\n", " ", recordTable);
        } else if(recordTable == 0){
            System.out.println(PrintColor.BLUE);
            System.out.printf("\n%68s===========================================\n", "");
            System.out.printf("%86s|||||||\n", "");
            System.out.printf("%86s|||||||\n", "");
            System.out.printf("%60s##~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~##\n", "");
            System.out.printf("%60s||                Hello Dear Customer !!!                ||\n", "");
            System.out.printf("%60s||      There's No Table Reserved Records For You...     ||\n", "");
            System.out.printf("%60s##~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~##\n", "");
        }
        System.out.print(PrintColor.RESET);
    }

    public static void orderHistory(Customer userLogin,ArrayList<Reservation> allReservationDetails){
        int recordOrder = 0;
        logoDisplay(26);
        System.out.print(PrintColor.PURPLE_BOLD);
        System.out.printf("\n%66s[[~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~]]\n", "");
        System.out.printf("%66s[[    User ID      :  %-20s  ]]\n", "", userLogin.getUserID());
        System.out.printf("%66s[[    Ordered By   :  %-20s  ]]\n", "", userLogin.getCustName());
        System.out.printf("%66s[[    Contact No   :  %-20s  ]]\n", "", userLogin.getCustContact());
        System.out.printf("%66s[[~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~]]\n", "");
        System.out.printf("%86s||||||\n", "");
        System.out.printf("%86s||||||\n", "");
        System.out.printf("%18s++==============++==============++=============================++============++==================++===================++=====================++\n", "");
        System.out.printf("%18s||   %-10s ||  %-11s ||   %-25s ||  %-9s ||  %-15s ||  %-16s ||   %-16s  ||\n", "", "Order ID", "Product ID ", "Product Name", "Quantity", "Unit Price(RM)", "Total Price(RM)","Description");
        System.out.printf("%18s++==============++==============++=============================++============++==================++===================++=====================++\n", "");
        for (Reservation r : allReservationDetails) {
            for (Reservation r1 : allReservationDetails) {
                for (OrderDetails o : r.getOrder().getOrderDetails()) {
                    if (r.getCust().getUserID().equals(userLogin.getUserID()) && r1.getOrder().getOrderID().equals(r.getOrder().getOrderID())) {
                        System.out.printf("%8s [ %3d ]  ||   %-10s ||  %7s%4s ||   %-23s%-2s ||  %5d%4s ||  %10.2f%5s ||  %11.2f%5s ||   %-18s||\n", " ", ++recordOrder,
                                r.getOrder().getOrderID(), o.getProd().prodID, " ", o.getProd().prodName, " ", o.getProdQty(), " ", o.getProd().prodPrice, "", o.getProdQty() * o.getProd().prodPrice,
                                " ", o.getProdDesc());
                    }
                }
            }
            if (r.getCust().getUserID().equals(userLogin.getUserID())) {
                System.out.printf("%18s++--------------++--------------++-----------------------------++------------++------------------++-------------------++---------------------++\n", "");
            }
        }
        if(recordOrder > 0) {
            System.out.printf("\n%34s>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>       THERE ARE %3d ORDER HISTORY RECORDS         <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<\n", " ", recordOrder);
        } else if(recordOrder == 0){
            System.out.println(PrintColor.BLUE);
            System.out.printf("\n%68s===========================================\n", "");
            System.out.printf("%86s|||||||\n", "");
            System.out.printf("%86s|||||||\n", "");
            System.out.printf("%60s##~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~##\n", "");
            System.out.printf("%60s||                Hello Dear Customer !!!                ||\n", "");
            System.out.printf("%60s||          There's No Order Records For You...          ||\n", "");
            System.out.printf("%60s##~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~##\n", "");
        }
        System.out.print(PrintColor.RESET);
    }

    public static void paymentHistory(Customer userLogin, ArrayList<Reservation> allReservationDetails, ArrayList<Payment> allPaymentDetails){
        String complete;
        int recordPayment = 0;
        logoDisplay(28);
        System.out.print(PrintColor.PURPLE_BOLD);
        System.out.print(PrintColor.PURPLE_BOLD);
        System.out.printf("\n%66s[[~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~]]\n", "");
        System.out.printf("%66s[[    User ID      :  %-20s  ]]\n", "", userLogin.getUserID());
        System.out.printf("%66s[[    Checked By   :  %-20s  ]]\n", "", userLogin.getCustName());
        System.out.printf("%66s[[    Contact No   :  %-20s  ]]\n", "", userLogin.getCustContact());
        System.out.printf("%66s[[~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~]]\n", "");
        System.out.printf("%86s||||||\n", "");
        System.out.printf("%86s||||||\n", "");
        System.out.printf("%26s++====================++================++=================++====================++=====================++==================++\n", "");
        System.out.printf("%26s||   %-15s  ||   %-13s||   %-14s||   %-16s ||   %-17s ||  %-16s||\n", "", "Reservation ID", "Payment ID ", "Payment Date", "Payment Method", "Grand Total(RM)","Payment Status");
        System.out.printf("%26s++====================++================++=================++====================++=====================++==================++\n", "");
        for(Reservation r : allReservationDetails) {
            for(Payment p:allPaymentDetails){
                if (r.getCust().getUserID().equals(userLogin.getUserID())) {
                    if (r.getReserveID().equals(p.getReservation().getReserveID())) {
                        if (p.isPayComplete()) {
                            complete = "Complete";
                        } else complete = "Incomplete";
                        System.out.printf("%17s [ %2d ]  ||  %12s%5s || %12s%2s ||  %11s%3s ||   %-16s ||  %12.2f%6s ||     %-12s ||\n", "", ++recordPayment,
                                p.getReservation().getReserveID(), " ", p.getPayID(), " ", new SimpleDateFormat("dd-MM-yyyy").format(p.getPayDate()), " ", p.getPayMethod(), p.getPayAmount(), " ", complete);
                        System.out.printf("%26s++--------------------++----------------++-----------------++--------------------++---------------------++------------------++\n", "");
                    }
                }
            }
        }
        if(recordPayment > 0) {
            System.out.printf("\n%33s>>>>>>>>>>>>>>>>>>>>>>>>>>>>>       THERE ARE %3d PAYMENT HISTORY RECORDS         <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<\n", " ", recordPayment);
        } else if(recordPayment == 0){
            System.out.println(PrintColor.BLUE);
            System.out.printf("\n%68s===========================================\n", "");
            System.out.printf("%86s|||||||\n", "");
            System.out.printf("%86s|||||||\n", "");
            System.out.printf("%60s##~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~##\n", "");
            System.out.printf("%60s||                Hello Dear Customer !!!                ||\n", "");
            System.out.printf("%60s||          There's No Order Records For You...          ||\n", "");
            System.out.printf("%60s##~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~##\n", "");
        }
        System.out.print(PrintColor.RESET);
    }

    public static Customer login(ArrayList<Customer> custDetails) {
        Scanner input = new Scanner(System.in);
        Customer cust = new Customer();
        HostSystem.logoDisplay(1);
        HostSystem.logoDisplay(2);
        do {
            System.out.print(PrintColor.CYAN_BOLD);
            System.out.printf("%60s##=======================================================##\n", "");
            System.out.printf("%60s||           You Are Required To Login/Sign Up           ||\n", "");
            System.out.printf("%60s||                To Make Any Reservation                ||\n", "");
            System.out.printf("%60s||              Which Describe Your Details              ||\n", "");
            System.out.printf("%60s||           ~~~   START YOUR APPLICATION   ~~~          ||\n", "");
            System.out.printf("%60s||*****                                             *****||\n", "");
            System.out.printf("%60s||        [ 1 ]----Login         [ 2 ]----Sign Up        ||\n", "");
            System.out.printf("%60s||*****                                             *****||\n", "");
            System.out.printf("%60s##=======================================================##\n", "");
            System.out.print(PrintColor.RESET);
            switch (HostSystem.intValidation("%69sPlease Enter Selection (in number) ---> ")) {
                case 1 -> {
                    HostSystem.logoDisplay(3);
                    System.out.print(PrintColor.CYAN_BRIGHT);
                    System.out.printf("\n%72sPlease Enter Login Details Below :-\n", "");
                    System.out.printf("%72s===================================\n", "");
                    System.out.printf("%72sUserID: ", "");
                    cust.setUserID(input.next());
                    System.out.printf("%72sPassword: ", "");
                    cust.setPassword(input.next());
                    System.out.print(PrintColor.RESET);
                    for (Customer c : custDetails) {
                        if (cust.equals(c)) {
                            cust = c;
                            System.out.print(PrintColor.CYAN_BOLD);
                            System.out.printf("\n\n%60s##~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~##\n", "");
                            System.out.printf("%60s||              LOGIN SYSTEM SUCCESSFULLY !!             ||\n", "");
                            System.out.printf("%60s||          .....   START YOUR DAY HERE   .....          ||\n", "");
                            System.out.printf("%60s||_____                                             _____||\n", "");
                            System.out.printf("%60s||                 Welcome %-30s||\n", "", cust.getCustName());
                            System.out.printf("%60s||                 --> Our Respect %-22s||\n", "", cust.getMemberType());
                            System.out.printf("%60s##~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~##\n", "");
                            System.out.print(PrintColor.RESET);
                            return cust;
                        }
                    }
                    System.out.print(PrintColor.RED_BOLD);
                    System.out.printf("\n%68s===========================================\n", "");
                    System.out.printf("%86s|||||||\n", "");
                    System.out.printf("%86s|||||||\n", "");
                    System.out.printf("%60s##~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~##\n", "");
                    System.out.printf("%60s||        Mismatched Username & Password Login!!!        ||\n", "");
                    System.out.printf("%60s||   ---> Register In System Before Making Reservation   ||\n", "");
                    System.out.printf("%60s||                   Please Try Again...                 ||\n", "");
                    System.out.printf("%60s##~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~##\n\n\n", "");
                    System.out.print(PrintColor.RESET);
                }
                case 2 -> signUpCust(custDetails);
                default -> {
                    System.out.print(PrintColor.RED_BOLD);
                    System.out.printf("\n%68s===========================================\n", "");
                    System.out.printf("%86s|||||||\n", "");
                    System.out.printf("%86s|||||||\n", "");
                    System.out.printf("%60s##~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~##\n", "");
                    System.out.printf("%60s||        Invalid SELECTION!! Please TRY again...        ||\n", "");
                    System.out.printf("%60s||         Input should be in numeric form (1-2)         ||\n", "");
                    System.out.printf("%60s##~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~##\n\n\n", "");
                    System.out.print(PrintColor.RESET);
                }
            }
        }while(true);
    }

    public static boolean logoutConfirmation() {
        HostSystem.logoDisplay(23);
        System.out.print(PrintColor.CYAN);
        System.out.printf("%49s[-------------------------------------------------------------------------------]\n", "");
        System.out.printf("%49s[                           !!   Please Be Aware   !!                           ]\n", "");
        System.out.printf("%49s[                      !!!!!!!   Please Be Aware   !!!!!!!                      ]\n", "");
        System.out.printf("%49s[                  !!!!!!!!!!!   Please Be Aware   !!!!!!!!!!!                  ]\n", "");
        System.out.printf("%49s[     Should You Want to Re-Build New Reservation OR Logout Current Account     ]\n", "");
        System.out.printf("%49s[      Both Selection Require To Re-Login If Desire Continue Using Program      ]\n", "");
        System.out.printf("%49s[                     ---> All Information Will Be Refreshed                    ]\n", "");
        System.out.printf("%49s[                     ---> Otherwise, Exit Program Promptly                     ]\n", "");
        System.out.printf("%49s[-------------------------------------------------------------------------------]\n", "");
        System.out.print(PrintColor.RESET);
        char confirmation = HostSystem.charValidation("%58sAre You Confirm Re-Build Reservation | Logout (Y/y = yes) >>> ", 1);
        System.out.print(PrintColor.CYAN_BRIGHT);
        System.out.printf("%58s===============================================================\n", "");
        System.out.printf("%86s||||||\n", "");
        System.out.printf("%86s||||||\n", "");
        if (Customer.checkConfirmation(confirmation)) {
            System.out.printf("%60s##~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~##\n", "");
            System.out.printf("%60s||         Automatically Logout Current Account          ||\n", "");
            System.out.printf("%60s||          Require To Confirm Re-Login Message          ||\n", "");
        }else {
            System.out.printf("%60s##~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~##\n", "");
            System.out.printf("%60s||              Remain Current Account Status            ||\n", "");
            System.out.printf("%60s||          Please Do Next Other Menu Selection          ||\n", "");
        }
        System.out.printf("%60s##~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~##\n\n", "");
        System.out.print(PrintColor.RESET);
        return Character.toUpperCase(confirmation) != 'Y';
    }


    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //Reservation
    public static ArrayList<Reservation> getAllReserveDetails(Customer userLogin) {
        ArrayList<Reservation> reserveDetails = new ArrayList<>();
        int index = 0;
        try {
            Scanner fileScan = new Scanner(new File("Reservation.txt"));
            while(fileScan.hasNextLine()) {
                Reservation getReserve = new Reservation();
                Scanner lineScan = new Scanner(fileScan.nextLine()).useDelimiter("/");
                while(lineScan.hasNext()) {
                    String data = lineScan.next();
                    switch(index) {
                        case 0 -> getReserve.setReserveID(data);
                        case 1 -> getReserve.setReserveDate(new SimpleDateFormat("dd-MM-yyyy").parse(data));
                        case 2 -> getReserve.setStartTime(new SimpleDateFormat("HH:mm").parse(data));
                        case 3 -> getReserve.setEndTime(new SimpleDateFormat("HH:mm").parse(data));
                        case 4 -> getReserve.setNoOfPerson(Integer.parseInt(data));
                        case 5 -> getReserve.setTotalOfTableCharges(Double.parseDouble(data));
                        case 6 -> {
                            if(Objects.equals(userLogin.getUserID(), data)){
                                getReserve.setCust(userLogin);
                            }
                        }
                        case 7 -> getReserve.setTables(getAllTableReservedDetails(getReserve.getReserveID(), new SimpleDateFormat("dd-MM-yyyy").parse(data)));
                        case 8 -> getReserve.setOrder(new Order(data, getAllOrderDetails(data)));
                    }
                    index++;
                }
                index = 0;
                Reservation.setLastReserveID(Integer.parseInt(getReserve.getReserveID().replaceAll("[a-zA-Z]", "")));
                reserveDetails.add(getReserve);
                lineScan.close();
            }
            fileScan.close();
        } catch (FileNotFoundException | ParseException e) {
            e.printStackTrace();
        }
        return reserveDetails;
    }

    public static Reservation reservationMenu(Customer cust, ArrayList<Table> tableDetails, ArrayList<OrderDetails> orderDetails, ArrayList<Table> tables, Order order, Reservation reservations, ArrayList<Reservation> allReservationDetails, ArrayList<Payment> allPaymentDetails) throws ParseException, FileNotFoundException {
        boolean end = true;
        do {
            HostSystem.logoDisplay(5);
            System.out.print(PrintColor.CYAN_BOLD);
            System.out.printf("%60s##=============##========================================##\n", "");
            System.out.printf("%60s||  Selection  ||   Reservation Details                  ||\n", "");
            System.out.printf("%60s##-------------##----------------------------------------##\n", "");
            System.out.printf("%60s||      1.     ||   Reserve Date & Time & No.Of.Person   ||\n", "");
            System.out.printf("%60s||      2.     ||   Reserve Table                        ||\n", "");
            System.out.printf("%60s||      3.     ||   Pre-Order Meals                      ||\n", "");
            System.out.printf("%60s||      4.     ||   Confirm Reservation Details          ||\n", "");
            System.out.printf("%60s||      5.     ||   View Current Reservation             ||\n", "");
            System.out.printf("%60s||      6.     ||   Cancel Current Reservation           ||\n", "");
            System.out.printf("%60s##-------------##----------------------------------------##\n", "");
            System.out.printf("%60s||      7.     ||   Back To Previous Menu                ||\n", "");
            System.out.printf("%60s##=============##========================================##\n", "");
            System.out.print(PrintColor.RESET);
            switch(HostSystem.intValidation("%69sPlease Enter Selection (in number) ---> ")) {
                case 1 -> selectReserveDateTime(reservations);
                case 2 -> {
                    tables.add(tableSelect(tableDetails));
                     for(Table tableAdded : tables) {
                        for(Table tableInFile : tableDetails) {
                            if(tableAdded.equals(tableInFile)) {
                                tableInFile.setAvailability(false);
                            }
                        }
                    }
                    Collections.sort(tables);
                }
                case 3 -> order = Order.addOrder(orderDetails);
                case 4 -> reservations = reserveConfirmation(cust, tables, order, reservations, allReservationDetails, allPaymentDetails);
                case 5 -> printReservationDetail(reservations);
                case 6 -> {
                    reservations = removeReservation(reservations, allReservationDetails);
                    if(reservations.getReserveID().equals("")) {
                        tableDetails = getTableDetails();
                        tables = new ArrayList<>();
                        order = new Order();
                        orderDetails = new ArrayList<>();
                        allPaymentDetails.remove(allPaymentDetails.size() - 1);
                        paymentWriteToFile(allPaymentDetails);
                    }
                }
                case 7 -> end = false;
                default -> {
                    System.out.print(PrintColor.RED_BOLD);
                    System.out.printf("\n%68s===========================================\n", "");
                    System.out.printf("%86s|||||||\n", "");
                    System.out.printf("%86s|||||||\n", "");
                    System.out.printf("%60s##~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~##\n", "");
                    System.out.printf("%60s||        Invalid SELECTION!! Please TRY again...        ||\n", "");
                    System.out.printf("%60s||         Input Should Be In Numeric Form (1-5)         ||\n", "");
                    System.out.printf("%60s##~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~##\n", "");
                    System.out.print(PrintColor.RESET);
                }
            }
        }while(end);
        return reservations;
    }

    public static void selectReserveDateTime(Reservation reserve) throws ParseException {
        System.out.print(PrintColor.CYAN);
        System.out.printf("\n\n%60s##=======================================================##\n", "");
        System.out.printf("%60s||                       Guidances                       ||\n", "");
        System.out.printf("%60s##-------------------------------------------------------##\n", "");
        System.out.printf("%60s||      Please Provide Information As Stated Below:      ||\n", "");
        System.out.printf("%60s##-------------------------------------------------------##\n", "");
        System.out.printf("%60s||***                                                 ***||\n", "");
        System.out.printf("%60s||   --> Input Date To Reserve  (eg. 31-10-2021)         ||\n", "");
        System.out.printf("%60s||   --> Input Start & End Time (eg. 12:00 & 14:00)      ||\n", "");
        System.out.printf("%60s||   --> Input Number Of Person (eg. 6)                  ||\n", "");
        System.out.printf("%60s||***                                                 ***||\n", "");
        System.out.printf("%60s##=======================================================##\n", "");
        System.out.print(PrintColor.RESET);
        reserve.setReserveDate(HostSystem.dateTimeValidation("%60sEnter Reservation Date (dd-MM-yyyy): ", 1));
        checkDate(reserve);
        reserve.setStartTime(HostSystem.dateTimeValidation("%60sEnter Reservation Start-Time (HH:MM): ", 2));
        reserve.setEndTime(HostSystem.dateTimeValidation("%60sEnter Reservation End-Time (HH:MM): ",2));
        checkStartEndTime(reserve);
        reserve.setNoOfPerson(HostSystem.intValidation("%60sEnter Number Of Person To Dine-In (in number): "));
    }

    public static Reservation reserveConfirmation(Customer cust, ArrayList<Table> tables, Order order, Reservation reservations, ArrayList<Reservation> allReservationDetails, ArrayList<Payment> allPaymentDetails) {
        if(!tables.isEmpty() && !order.getOrderDetails().isEmpty() && reservations.getReserveDate().after(new Date())) {
            if(!Reservation.isReserveConfirm()) {
                System.out.print(PrintColor.CYAN_BRIGHT);
                System.out.printf("\n%45s   ++++++[    Required To Confirm RESERVATION By Typing 'Y' or 'y' !!!    ]++++++\n", "");
                System.out.printf("%45s----------------------------------------------------------------------------------------\n", "");
                System.out.print(PrintColor.RESET);
                char confirmation = HostSystem.charValidation("%68sAre You Confirm The Details (Y/y = yes): ", 1);
                System.out.print(PrintColor.CYAN_BRIGHT);
                System.out.printf("%68s==========================================\n", "");
                System.out.printf("%86s||||||\n", "");
                System.out.printf("%86s||||||\n", "");
                if (reservations.checkConfirmation(confirmation)) {
                    for (Table table : tables) {reservations.addTableCharges(table.getTableCharges());}
                    int lastOrderID = Order.getLastOrderID();
                    int lastReserveID = Reservation.getLastReserveID();
                    ++lastReserveID;
                    ++lastOrderID;
                    order.setOrderID("FDD" + lastOrderID);

                    reservations = new Reservation("RES" + lastReserveID, reservations.getReserveDate(), reservations.getStartTime(), reservations.getEndTime(), reservations.getNoOfPerson(), reservations.getTotalOfTableCharges(), cust, tables, order);
                    allReservationDetails.add(reservations);
                    reserveWriteToFile(allReservationDetails);
                    Reservation.setReserveConfirm(true);

                    allPaymentDetails.add(new Payment(reservations, "PAY", 0.0, 0.0, new Date(), false, 0.0, 0.0, 0.0, "Unknown"));
                    paymentWriteToFile(allPaymentDetails);
                    System.out.printf("%60s##~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~##\n", "");
                    System.out.printf("%60s||       You Have Confirmed The Reservation Details      ||\n", "");
                    System.out.printf("%60s||        Below Are The Information For References       ||\n", "");
                } else {
                    System.out.printf("%60s##~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~##\n", "");
                    System.out.printf("%60s||     You Haven't Confirmed The Reservation Details     ||\n", "");
                    System.out.printf("%60s||     Please Try Again OR Do Next Reservation Input     ||\n", "");
                }
                System.out.printf("%60s##~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~##\n\n", "");
            } else {
                System.out.print(PrintColor.BLUE);
                System.out.printf("%68s==========================================\n", "");
                System.out.printf("%86s||||||\n", "");
                System.out.printf("%86s||||||\n", "");
                System.out.printf("%60s##~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~##\n", "");
                System.out.printf("%60s||    There's No & Null Reservation To Be Confirmed...   ||\n", "");
                System.out.printf("%60s||                          OR                           ||\n", "");
                System.out.printf("%60s||             Meal Orders Haven't Selected              ||\n", "");
                System.out.printf("%60s||                          OR                           ||\n", "");
                System.out.printf("%60s||       Reservation Date & Table Haven't Selected       ||\n", "");
                System.out.printf("%60s##~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~##\n", "");
            }
        } else {
            System.out.print(PrintColor.BLUE);
            System.out.printf("%68s==========================================\n", "");
            System.out.printf("%86s||||||\n", "");
            System.out.printf("%86s||||||\n", "");
            System.out.printf("%60s##~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~##\n", "");
            System.out.printf("%60s||    There's No & Null Reservation To Be Confirmed...   ||\n", "");
            System.out.printf("%60s||                          OR                           ||\n", "");
            System.out.printf("%60s||             Meal Orders Haven't Selected              ||\n", "");
            System.out.printf("%60s||                          OR                           ||\n", "");
            System.out.printf("%60s||       Reservation Date & Table Haven't Selected       ||\n", "");
            System.out.printf("%60s##~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~##\n", "");
        }
        System.out.print(PrintColor.RESET);
        return reservations;
    }

    public static void printReservationDetail(Reservation reserve) {
        if(!reserve.getReserveID().equals("")) {
            System.out.print(PrintColor.BLUE_BRIGHT);
            System.out.printf("\n\n%61s <<<<<:::      Current Reservation Details      :::>>>>>\n", "");
            System.out.printf("%61s#=======================================================#\n", "");
            System.out.printf("%61s|           Preview Table Reservation Details           |\n", "");
            System.out.printf("%61s#=======================================================#\n", "");
            System.out.printf("%61s|       Reservation ID     :     %-21s  |\n", "", reserve.getReserveID());
            System.out.printf("%61s|       Order ID           :     %-21s  |\n", "", reserve.getOrder().getOrderID());
            System.out.printf("%61s|-------------------------------------------------------|\n", "");
            System.out.printf("%61s|%55s|\n", "", "");
            System.out.printf("%61s|       Customer Name      :     %-21s  |\n", "", reserve.getCust().getCustName());
            System.out.printf("%61s|       Contact Number     :     %-21s  |\n", "", reserve.getCust().getCustContact());
            System.out.printf("%61s|       Reservation Date   :     %-21s  |\n", "", new SimpleDateFormat("dd-MM-yyyy").format(reserve.getReserveDate()));
            System.out.printf("%61s|       Reservation Time   :     %-6s- %-13s  |\n", "", new SimpleDateFormat("HH:mm").format(reserve.getStartTime()), new SimpleDateFormat("HH:mm").format(reserve.getEndTime()));
            System.out.printf("%61s|       No.Of Person       :     %-2d%-19s  |\n", "", reserve.getNoOfPerson(), " Person");
            System.out.printf("%61s|       No.Of Meal Ordered :     %-2d%-19s  |\n", "", OrderDetails.getNoOfOrder(), "");
            System.out.printf("%61s|%55s|\n", "", "");
            System.out.printf("%61s|-------------------------------------------------------|\n", "");
            System.out.printf("%61s|*****%45s*****|\n", "", "");
            System.out.printf("%61s|*%18s TABLES SELECTED %18s*|\n", "", "", "");
            System.out.printf("%61s|*%18s --------------- %18s*|\n", "", "", "");
            for (Table table : reserve.getTables()) {
                System.out.printf("%61s|* %10s%s - %-15s    %5.2f%% %7s   *|\n", "", "", table.getTableID(), table.getTableDesc(), table.getTableCharges(), "");
            }
            System.out.printf("%61s|*  %32s--------%10s *|\n", "", "", "");
            System.out.printf("%61s|*  %9sTOTAL Charges :  %13.2f%% %7s   *|\n", "", "", reserve.getTotalOfTableCharges(), "");
            System.out.printf("%61s|*  %32s--------%10s *|\n", "", "", "");
            System.out.printf("%61s|*****%45s*****|\n", "", "");
            System.out.printf("%61s#=======================================================#\n\n", "");
            System.out.print(PrintColor.PURPLE_BOLD);
        } else {
            System.out.print(PrintColor.BLUE);
            System.out.printf("\n%68s===========================================\n", "");
            System.out.printf("%86s|||||||\n", "");
            System.out.printf("%86s|||||||\n", "");
            System.out.printf("%60s##~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~##\n", "");
            System.out.printf("%60s||         There's No Reservation To Be Viewed...        ||\n", "");
            System.out.printf("%60s||                          OR                           ||\n", "");
            System.out.printf("%60s||          You Haven't Confirmed Reservation...         ||\n", "");
            System.out.printf("%60s##~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~##\n", "");
            System.out.print(PrintColor.RESET);
        }
    }

    public static Reservation removeReservation(Reservation reservation, ArrayList<Reservation> allReservationDetails) throws FileNotFoundException, ParseException {
        if(!reservation.getReserveID().equals("")) {
            HostSystem.logoDisplay(22);
            printReservationDetail(reservation);
            System.out.print(PrintColor.CYAN_BRIGHT);
            System.out.printf("\n%49s   ++++++[    Required To Confirm REMOVAL By Typing 'Y' or 'y' !!!    ]++++++\n", "");
            System.out.printf("%45s----------------------------------------------------------------------------------------\n", "");
            System.out.print(PrintColor.RESET);
            char confirmation = HostSystem.charValidation("%64sAre You Confirm Removing This Record (Y/y = yes): ", 1);
            System.out.print(PrintColor.CYAN_BRIGHT);
            System.out.printf("\n%68s===========================================\n", "");
            System.out.printf("%86s|||||||\n", "");
            System.out.printf("%86s|||||||\n", "");
            if (reservation.checkConfirmation(confirmation)) {
                reservation = new Reservation();
                allReservationDetails.remove(allReservationDetails.size() - 1);
                reserveWriteToFile(allReservationDetails);
                Reservation.setReserveConfirm(false);
                System.out.printf("%60s##~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~##\n", "");
                System.out.printf("%60s||     You Have Successfully Removed This Record !!!     ||\n", "");
                System.out.printf("%60s||   ---> This Record Will Not Appear In Reservation...  ||\n", "");
            } else {
                System.out.printf("%60s##~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~##\n", "");
                System.out.printf("%60s||           There's no REMOVAL Being Made !!!           ||\n", "");
                System.out.printf("%60s||             Please Try Again If Any Needs             ||\n", "");
            }
            System.out.printf("%60s##~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~##\n\n", "");
        } else {
            System.out.print(PrintColor.BLUE);
            System.out.printf("\n%68s===========================================\n", "");
            System.out.printf("%86s|||||||\n", "");
            System.out.printf("%86s|||||||\n", "");
            System.out.printf("%60s##~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~##\n", "");
            System.out.printf("%60s||        There's No Reservation To Be Viewed...         ||\n", "");
            System.out.printf("%60s||                          OR                           ||\n", "");
            System.out.printf("%60s||          You Haven't Confirmed Reservation...         ||\n", "");
            System.out.printf("%60s##~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~##\n", "");
        }
        System.out.print(PrintColor.RESET);
        return reservation;
    }

    public static void reserveWriteToFile(ArrayList<Reservation> reservationDetails) {
        try {
            FileWriter writer = new FileWriter("Reservation.txt");
            FileWriter writer2 = new FileWriter("OrderDetails.txt");
            FileWriter writer3 = new FileWriter("TableReservedDetails.txt");
            int size = reservationDetails.size();
            for (int i = 0; i < size; i++) {
                String str = reservationDetails.get(i).contentWriteToFile();
                writer.write(str);
                if (i < size - 1) { writer.write("\n"); }

                //OrderDetails
                int size2 = reservationDetails.get(i).getOrder().getOrderDetails().size();
                for (int j = 0; j < size2; j++) {
                    String str2 = reservationDetails.get(i).getOrder().getOrderID() + "/" + reservationDetails.get(i).getOrder().getOrderDetails().get(j).contentWriteToFile();
                    writer2.write(str2);
                }

                //Table
                int size3 = reservationDetails.get(i).getTables().size();
                for (int k = 0; k < size3; k++) {
                    String str3 = reservationDetails.get(i).getReserveID() + "/" + new SimpleDateFormat("dd-MM-yyyy").format(reservationDetails.get(i).getReserveDate())
                            + "/" + reservationDetails.get(i).getTables().get(k).contentWriteToFile();
                    writer3.write(str3);
                }
            }
            writer.close();
            writer2.close();
            writer3.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void checkStartEndTime(Reservation reserve) throws ParseException {
        while(true) {
            if(reserve.checkReserveTime(reserve.getStartTime(), reserve.getEndTime())) {return;}
            System.out.print(PrintColor.RED_BOLD);
            System.out.printf("\n%60s##~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~##\n", "");
            System.out.printf("%60s||  It's Not Possible End-Time Is Before Start-Time !!!  ||\n", "");
            System.out.printf("%60s||        ---> Only Allow To Reserve Within 3 Hours      ||\n", "");
            System.out.printf("%60s||        ---> End-Time Must Exceed Start-Time...        ||\n", "");
            System.out.printf("%60s||                  Please Try Again...                  ||\n", "");
            System.out.printf("%60s##~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~##\n", "");
            System.out.print(PrintColor.RED_BOLD);
            reserve.setStartTime(HostSystem.dateTimeValidation("%60sRe-Enter Reservation Start-Time (HH:MM): ", 2));
            reserve.setEndTime(HostSystem.dateTimeValidation("%60sRe-Enter Reservation End-Time (HH:MM): ",2));
        }
    }

    public static void checkDate(Reservation reserve) throws ParseException {
        while(true) {
            if(reserve.checkMaxDayReserve(reserve.getReserveDate(), new Date())) { return; }
            System.out.print(PrintColor.RED_BOLD);
            System.out.printf("\n%60s##~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~##\n", "");
            System.out.printf("%60s||      Reserve Date Not Allow More Than Two Weeks       ||\n", "");
            System.out.printf("%60s||       --->  Only Permit Reserve Within 14 Days        ||\n", "");
            System.out.printf("%60s||                  Please Try Again...                  ||\n", "");
            System.out.printf("%60s##~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~##\n", "");
            System.out.print(PrintColor.RED_BOLD);
            reserve.setReserveDate(HostSystem.dateTimeValidation("%60sRe-Enter Reservation Date (dd-MM-yyyy): ", 1));
        }
    }


    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //Table
    public static ArrayList<Table> getAllTableReservedDetails(String reserveID, Date reserveDate) throws FileNotFoundException {
        ArrayList<Table> tableDetails = new ArrayList<>();
        try {
            String line;
            BufferedReader readFile = new BufferedReader(new FileReader("TableReservedDetails.txt"));
            while((line = readFile.readLine()) != null) {
                String[] data = line.split("/");
                if (Objects.equals(reserveID, data[0]) && Objects.equals(reserveDate, new SimpleDateFormat("dd-MM-yyyy").parse(data[1]))) {
                    tableDetails.add(new Table(data[2], Integer.parseInt(data[3]), data[4], Double.parseDouble(data[5]), Boolean.parseBoolean(data[6])));
                }
            }
            readFile.close();
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
        return tableDetails;
    }

    public static ArrayList<Table> getTableDetails() {
        ArrayList<Table> tableDetails = new ArrayList<>();
        int index = 0;
        try {
            Scanner fileScan = new Scanner(new File("Table.txt"));
            while(fileScan.hasNextLine()) {   //C:\Users\HP\IdeaProjects\Assignment_Diploma\src\Table.txt
                Table getTable = new Table();
                Scanner lineScan = new Scanner(fileScan.nextLine()).useDelimiter("/");
                while(lineScan.hasNext()) {
                    String data = lineScan.next();
                    switch(index) {
                        case 0-> getTable.setTableID(data);
                        case 1-> getTable.setMaxSeat(Integer.parseInt(data));
                        case 2-> getTable.setTableDesc(data);
                        case 3-> getTable.setTableCharges(Double.parseDouble(data));
                        case 4-> getTable.setAvailability(Boolean.parseBoolean(data));
                    }
                    index++;
                }
                index = 0;
                tableDetails.add(getTable);
                lineScan.close();
            }
            fileScan.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return tableDetails;
    }

    public static Table tableSelect(ArrayList<Table> tableDetails) {
        Table table =  new Table();
        do {
            printTableDetails(tableDetails);
            int selection = HostSystem.intValidation("%69sPlease Enter Selection (in number) ---> ");
            if(selection >= 1 && selection <= 12) {
                for (int j = 0; j < tableDetails.size(); j++) {
                    if (j == selection - 1) {
                        table = tableDetails.get(j);
                    }
                }
                System.out.print(PrintColor.BLUE);
                System.out.printf("\n%68s===========================================\n", "");
                System.out.printf("%86s|||||||\n", "");
                System.out.printf("%86s|||||||\n", "");
                if (Objects.equals(table.isAvailability(), true)) {
                    System.out.printf("%60s##~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~##\n", "");
                    System.out.printf("%60s||     You Have Successfully ADDED A Table Reserved!!!   ||\n", "");
                    System.out.printf("%60s||        Please Choose Again If Needs More Tables       ||\n", "");
                    System.out.printf("%60s##~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~##\n", "");
                    return table;
                } else {
                    System.out.printf("%60s##~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~##\n", "");
                    System.out.printf("%60s||              TABLE Has Been Reserved !!!              ||\n", "");
                    System.out.printf("%60s||                --> Please Try Again...                ||\n", "");
                    System.out.printf("%60s##~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~##\n", "");
                }
            } else {
                System.out.print(PrintColor.RED_BOLD);
                System.out.printf("\n%68s===========================================\n", "");
                System.out.printf("%86s|||||||\n", "");
                System.out.printf("%86s|||||||\n", "");
                System.out.printf("%60s##~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~##\n", "");
                System.out.printf("%60s||        Invalid SELECTION!! Please TRY again...        ||\n", "");
                System.out.printf("%60s||         Input should be in numeric form (1-12)        ||\n", "");
                System.out.printf("%60s##~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~##\n", "");
            }
            System.out.print(PrintColor.RESET);
        }while(true);
    }

    public static void printTableDetails(ArrayList<Table> tableDetails) {
        int i = 1;
        String available = "";
        HostSystem.logoDisplay(7);
        System.out.print(PrintColor.BLUE_BRIGHT);
        System.out.printf("%49s+===============================================================================+\n", "");
        System.out.printf("%49s| %-9s |   %-10s |  %-18s | %-10s |  %-13s  |\n", "", "Table ID", "Max Seat", "Table Description", "Charges(%)", "Availability");
        System.out.printf("%49s+===============================================================================+\n", "");
        for (Table t : tableDetails) {
            if (Objects.equals(String.valueOf(t.isAvailability()), "true")) { available = "Available";}
            else if (Objects.equals(String.valueOf(t.isAvailability()), "false")) {available = "Reserved";}
            System.out.printf("%42sNo.%2d", "", i++);
            System.out.print(t);
            System.out.printf("  %-13s  |\n", available);
        }
        System.out.printf("%49s+===============================================================================+\n", "");
        System.out.print(PrintColor.PURPLE_BOLD);
    }


    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //OrderDetails
    public static ArrayList<OrderDetails> getAllOrderDetails(String orderID) throws ParseException, FileNotFoundException {
        ArrayList<OrderDetails> orderDetails = new ArrayList<>();
        try {
            String line;
            BufferedReader readFile = new BufferedReader(new FileReader("OrderDetails.txt"));
            while((line = readFile.readLine()) != null) {
                String[] data = line.split("/");
                if (Objects.equals(orderID, data[0])) {
                    orderDetails.add(new OrderDetails(Integer.parseInt(data[1]), data[2], Double.parseDouble(data[3]), new Food(data[4], data[5], Double.parseDouble(data[6]))));
                }
                Order.setLastOrderID(Integer.parseInt(orderID.replaceAll("[a-zA-Z]", "")));
            }
            readFile.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return orderDetails;
    }

    public static ArrayList<OrderDetails> orderMenu(ArrayList<OrderDetails> orders) throws ParseException, FileNotFoundException {
        boolean end = true;
        do {
            HostSystem.logoDisplay(6);
            System.out.print(PrintColor.CYAN_BOLD);
            System.out.printf("%60s##=============##========================================##\n", "");
            System.out.printf("%60s||  Selection  ||    Order Details                       ||\n", "");
            System.out.printf("%60s||-------------------------------------------------------||\n", "");
            System.out.printf("%60s||      1.     ||    Food Order                          ||\n", "");
            System.out.printf("%60s||      2.     ||    Drink Order                         ||\n", "");
            System.out.printf("%60s||      3.     ||    Dessert Order                       ||\n", "");
            System.out.printf("%60s||      4.     ||    View Current Order                  ||\n", "");
            System.out.printf("%60s||      5.     ||    Remove An Item From Order List      ||\n", "");
            System.out.printf("%60s##-------------##----------------------------------------##\n", "");
            System.out.printf("%60s||      6.     ||    Back To Previous                    ||\n", "");
            System.out.printf("%60s##=============##========================================##\n", "");
            System.out.print(PrintColor.RESET);
            switch(HostSystem.intValidation("%69sPlease Enter Selection (in number) ---> ")) {
                case 1 ->  orders.add(takeFoodOrder(foodSelect()));
                case 2 ->  orders.add(takeDrinkOrder(drinkSelect()));
                case 3 ->  orders.add(takeDessertOrder(dessertSelect()));
                case 4 ->  printOrderDetail(orders);
                case 5 ->  removeOrderList(orders);
                case 6 ->  end = false;
                default -> {
                    System.out.print(PrintColor.RED_BOLD);
                    System.out.printf("\n%68s===========================================\n", "");
                    System.out.printf("%86s|||||||\n", "");
                    System.out.printf("%86s|||||||\n", "");
                    System.out.printf("%60s##~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~##\n", "");
                    System.out.printf("%60s||        Invalid SELECTION!! Please TRY again...        ||\n", "");
                    System.out.printf("%60s||         Input should be in numeric form (1-5)         ||\n", "");
                    System.out.printf("%60s##~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~##\n", "");
                    System.out.print(PrintColor.RESET);
                }
            }
            orders.removeIf(o -> o.getProd() == null);
            Collections.sort(orders);
        }while(end);
        return orders;
    }

    public static OrderDetails takeFoodOrder(Product foods) {
        OrderDetails orderDetails = new OrderDetails();
        boolean end = true;
        int qty;
        String foodDesc = "";
        System.out.print(PrintColor.CYAN);
        System.out.printf("\n\n%60s##=======================================================##\n", "");
        System.out.printf("%60s||                       Guidances                       ||\n", "");
        System.out.printf("%60s##-------------------------------------------------------##\n", "");
        System.out.printf("%60s||  --> Input Food's Quantity Desired                    ||\n", "");
        System.out.printf("%60s||  --> Select When Food To Be ON-TIME (SERVE | PREPARE) ||\n", "");
        System.out.printf("%60s##-------------------------------------------------------##\n", "");
        System.out.printf("%60s||***                                                 ***||\n", "");
        System.out.printf("%60s||    Remember To Type 'Y' / 'y' To Confirm This Order   ||\n", "");
        System.out.printf("%60s||***                                                 ***||\n", "");
        System.out.printf("%60s##=======================================================##\n", "");
        System.out.print(PrintColor.RESET);
        qty = HostSystem.intValidation("%73sPlease Key In Quantity Desired: ");
        do {
            System.out.print(PrintColor.CYAN);
            System.out.printf("\n%49s[-------------------------------------------------------------------------------]\n", "");
            System.out.printf("%49s[                              How To Serve Food ?                              ]\n", "");
            System.out.printf("%49s[            [ 1 ]---ON-TIME SERVE           [ 2 ]---CALL TO PREPARE            ]\n", "");
            System.out.printf("%49s[-------------------------------------------------------------------------------]\n", "");
            System.out.print(PrintColor.RESET);
            switch (HostSystem.intValidation("%64sPlease Select Time Food To Be Served (in number): ")) {
                case 1 -> { foodDesc = "ON-TIME SERVE"; end = false; }
                case 2 -> { foodDesc = "CALL TO PREPARE"; end = false; }
                default -> {
                    System.out.print(PrintColor.RED_BOLD);
                    System.out.printf("\n%68s===========================================\n", "");
                    System.out.printf("%86s|||||||\n", "");
                    System.out.printf("%86s|||||||\n", "");
                    System.out.printf("\n%60s##~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~##\n", "");
                    System.out.printf("%60s||        Invalid SELECTION!! Please TRY again...        ||\n", "");
                    System.out.printf("%60s||         Input should be in numeric form (1-2)         ||\n", "");
                    System.out.printf("%60s##~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~##\n", "");
                    System.out.print(PrintColor.RESET);
                }
            }
        }while(end);
        return orderConfirmation(orderDetails, foods, qty, foodDesc);
    }

    public static OrderDetails takeDrinkOrder(Product drinks) {
        OrderDetails orderDetails = new OrderDetails();
        ArrayList<Drink> drinkType = getDrinkDetails();
        boolean end = true;
        int qty;
        String drinkDesc = "";
        System.out.print(PrintColor.CYAN);
        System.out.printf("\n\n%60s##=======================================================##\n", "");
        System.out.printf("%60s||                       Guidances                       ||\n", "");
        System.out.printf("%60s##-------------------------------------------------------##\n", "");
        System.out.printf("%60s||  --> Input Drink's Quantity Desired                   ||\n", "");
        System.out.printf("%60s||  --> Select Drink Type (HOT) | (COLD : SMALL/LARGE)   ||\n", "");
        System.out.printf("%60s##-------------------------------------------------------##\n", "");
        System.out.printf("%60s||***                                                 ***||\n", "");
        System.out.printf("%60s||    Remember To Type 'Y' / 'y' To Confirm This Order   ||\n", "");
        System.out.printf("%60s||***                                                 ***||\n", "");
        System.out.printf("%60s##=======================================================##\n", "");
        System.out.print(PrintColor.RESET);
        qty = HostSystem.intValidation("%73sPlease Key In Quantity Desired: ");
        do {
            System.out.print(PrintColor.CYAN);
            System.out.printf("\n%49s[-------------------------------------------------------------------------------]\n", "");
            System.out.printf("%49s[                           Which Drink Type Desired ?                          ]\n", "");
            System.out.printf("%49s[      [ 1 ]---HOT          [ 2 ]---COLD SMALL          [ 3 ]---COLD LARGE      ]\n", "");
            System.out.printf("%49s[-------------------------------------------------------------------------------]\n", "");
            System.out.print(PrintColor.RESET);
            switch(HostSystem.intValidation("%66sPlease Select Drink Type Desired (in number): ")) {
                case 1 -> {
                    for(Drink d : drinkType) {
                        if (Objects.equals(drinks.prodID, d.prodID)) {
                            drinks.prodPrice = d.getHotPrice();
                            drinkDesc = "HOT";
                        }
                    } end = false;
                }
                case 2 -> {
                    for(Drink d : drinkType) {
                        if (Objects.equals(drinks.prodID, d.prodID)) {
                            drinks.prodPrice = d.getSmallCold();
                            drinkDesc = "COLD-SMALL";
                        }
                    }end = false;
                }
                case 3 -> {
                    for(Drink d : drinkType) {
                        if (Objects.equals(drinks.prodID, d.prodID)) {
                            drinks.prodPrice = d.getLargeCold();
                            drinkDesc = "COLD-LARGE";
                        }
                    }end = false;
                }
                default -> {
                    System.out.print(PrintColor.RED_BOLD);
                    System.out.printf("\n%68s===========================================\n", "");
                    System.out.printf("%86s|||||||\n", "");
                    System.out.printf("%86s|||||||\n", "");
                    System.out.printf("%60s##~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~##\n", "");
                    System.out.printf("%60s||        Invalid SELECTION!! Please TRY again...        ||\n", "");
                    System.out.printf("%60s||         Input should be in numeric form (1-3)         ||\n", "");
                    System.out.printf("%60s##~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~##\n", "");
                    System.out.print(PrintColor.RESET);
                }
            }
        }while(end);
        return orderConfirmation(orderDetails, drinks, qty, drinkDesc);
    }

    public static OrderDetails takeDessertOrder(Product desserts) {
        OrderDetails orderDetails = new OrderDetails();
        boolean end = true;
        int qty;
        String dessertDesc = "";
        System.out.print(PrintColor.CYAN);
        System.out.printf("\n\n%60s##=======================================================##\n", "");
        System.out.printf("%60s||                       Guidances                       ||\n", "");
        System.out.printf("%60s##-------------------------------------------------------##\n", "");
        System.out.printf("%60s||  --> Input Dessert's Quantity Desired                 ||\n", "");
        System.out.printf("%60s||  --> Select When DESSERT To Be Served(BEFORE | AFTER) ||\n", "");
        System.out.printf("%60s##-------------------------------------------------------##\n", "");
        System.out.printf("%60s||***                                                 ***||\n", "");
        System.out.printf("%60s||    Remember To Type 'Y' / 'y' To Confirm This Order   ||\n", "");
        System.out.printf("%60s||***                                                 ***||\n", "");
        System.out.printf("%60s##=======================================================##\n", "");
        System.out.print(PrintColor.RESET);
        qty = HostSystem.intValidation("%73sPlease Key In Quantity Desired: ");
        do {
            System.out.print(PrintColor.CYAN);
            System.out.printf("\n%49s[-------------------------------------------------------------------------------]\n", "");
            System.out.printf("%49s[                             When To Serve Dessert ?                           ]\n", "");
            System.out.printf("%49s[              [ 1 ]---BEFORE MEAL               [ 2 ]---AFTER MEAL             ]\n", "");
            System.out.printf("%49s[-------------------------------------------------------------------------------]\n", "");
            System.out.print(PrintColor.RESET);
            switch (HostSystem.intValidation("%61sPlease Select Time Dessert To Be Served (in number): ")) {
                case 1 -> { dessertDesc = "BEFORE MEAL"; end = false; }
                case 2 -> { dessertDesc = "AFTER MEAL"; end = false; }
                default -> {
                    System.out.print(PrintColor.RED_BOLD);
                    System.out.printf("\n%68s===========================================\n", "");
                    System.out.printf("%86s|||||||\n", "");
                    System.out.printf("%86s|||||||\n", "");
                    System.out.printf("%60s##~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~##\n", "");
                    System.out.printf("%60s||        Invalid SELECTION!! Please TRY again...        ||\n", "");
                    System.out.printf("%60s||         Input should be in numeric form (1-2)         ||\n", "");
                    System.out.printf("%60s##~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~##\n", "");
                    System.out.print(PrintColor.RESET);
                }
            }
        }while(end);
        return orderConfirmation(orderDetails, desserts, qty, dessertDesc);
    }

    public static OrderDetails orderConfirmation(OrderDetails orderDetails, Product prod, int qty, String prodDesc) {
        int noOfOrder = OrderDetails.getNoOfOrder();
        System.out.print(PrintColor.CYAN_BRIGHT);
        System.out.printf("\n%50s   ++++++[    Required To Confirm ORDERS By Typing 'Y' or 'y' !!!    ]++++++\n", "");
        System.out.printf("%45s-----------------------------------------------------------------------------------------\n", "");
        System.out.print(PrintColor.RESET);
        char confirmation = HostSystem.charValidation("%68sAre You Confirm The Details (Y/y = yes): ", 1);
        System.out.print(PrintColor.CYAN_BRIGHT);
        System.out.printf("%68s==========================================\n", "");
        System.out.printf("%86s||||||\n", "");
        System.out.printf("%86s||||||\n", "");
        if (OrderDetails.checkConfirmation(confirmation)) {
            noOfOrder++;
            OrderDetails.setNoOfOrder(noOfOrder);
            orderDetails = new OrderDetails(qty, prodDesc, orderDetails.calcSubTotalPerOrder(qty, prod.prodPrice), new Food(prod.prodID, prod.prodName, prod.prodPrice));
            System.out.printf("%60s##~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~##\n", "");
            System.out.printf("%60s||          You Have Confirmed The Order Detail          ||\n", "");
            System.out.printf("%60s||        Please Choose Again If Needs Other Food        ||\n", "");
        } else {
            System.out.printf("%60s##~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~##\n", "");
            System.out.printf("%60s||        You Haven't Confirmed The Order Details        ||\n", "");
            System.out.printf("%60s||        Please Try Again OR Do Next Order Input        ||\n", "");
        }
        System.out.printf("%60s##~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~##\n\n", "");
        System.out.print(PrintColor.RESET);
        return orderDetails;
    }

    public static void removeOrderList(ArrayList<OrderDetails> orders) {
        char nextDelete;
        int noOfOrder = OrderDetails.getNoOfOrder();
        if(noOfOrder > 0) {
            do {
                HostSystem.logoDisplay(21);
                printOrderDetail(orders);
                System.out.print(PrintColor.CYAN_BRIGHT);
                System.out.printf("\n\n%53sRemoving An Order List REQUIRES To Make A Selection To Complete Program :\n", "");
                System.out.printf("%53s=========================================================================\n", "");
                System.out.print(PrintColor.RESET);
                int selection = HostSystem.intValidation("%69sPlease Enter Selection (in number) ---> ");
                if(selection >= 1 && selection <= noOfOrder) {
                    System.out.print(PrintColor.CYAN_BRIGHT);
                    System.out.printf("\n%49s   ++++++[    Required To Confirm REMOVAL By Typing 'Y' or 'y' !!!    ]++++++\n", "");
                    System.out.printf("%45s----------------------------------------------------------------------------------------\n", "");
                    System.out.print(PrintColor.RESET);
                    char confirmation = HostSystem.charValidation("%64sAre You Confirm Removing This Record (Y/y = yes): ", 1);
                    System.out.print(PrintColor.BLUE);
                    System.out.printf("\n%68s===========================================\n", "");
                    System.out.printf("%86s|||||||\n", "");
                    System.out.printf("%86s|||||||\n", "");
                    if (OrderDetails.checkConfirmation(confirmation)) {
                        orders.remove(selection - 1);
                        noOfOrder--;
                        OrderDetails.setNoOfOrder(noOfOrder);
                        System.out.printf("%60s##~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~##\n", "");
                        System.out.printf("%60s||     You Have Successfully Removed This Record !!!     ||\n", "");
                        System.out.printf("%60s||        --> It Will Not Appear In Order List...        ||\n", "");
                    } else {
                        System.out.printf("%60s##~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~##\n", "");
                        System.out.printf("%60s||           There's no REMOVAL Being Made !!!           ||\n", "");
                        System.out.printf("%60s||         Please Try Again OR Do Next Removal...        ||\n", "");
                    }
                    System.out.printf("%60s##~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~##\n\n", "");
                } else {
                    System.out.print(PrintColor.RED_BOLD);
                    System.out.printf("\n%68s===========================================\n", "");
                    System.out.printf("%86s|||||||\n", "");
                    System.out.printf("%86s|||||||\n", "");
                    System.out.printf("%60s##~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~##\n", "");
                    System.out.printf("%60s||        Invalid SELECTION!! Please TRY again...        ||\n", "");
                    System.out.printf("%60s||         Input should be in numeric form ( 1 -%2d )     ||\n", "", noOfOrder);
                    System.out.printf("%60s##~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~##\n\n", "");
                    System.out.print(PrintColor.RESET);
                }
                System.out.print(PrintColor.CYAN_BRIGHT);
                System.out.printf("\n%52s   ++++++[    Should Continue to REMOVE, Type 'Y' or 'y' !!!    ]++++++\n", "");
                System.out.printf("%45s----------------------------------------------------------------------------------------\n", "");
                System.out.print(PrintColor.RESET);
                nextDelete = HostSystem.charValidation("%66sContinue To Remove Other Record (Y/y = yes): ", 1);
            } while (OrderDetails.checkConfirmation(nextDelete));
        } else {
            System.out.print(PrintColor.BLUE);
            System.out.printf("\n%68s===========================================\n", "");
            System.out.printf("%86s|||||||\n", "");
            System.out.printf("%86s|||||||\n", "");
            System.out.printf("%60s##~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~##\n", "");
            System.out.printf("%60s||           There's No Order To Be Viewed...            ||\n", "");
            System.out.printf("%60s||                          OR                           ||\n", "");
            System.out.printf("%60s||            You Haven't Added Any Order...             ||\n", "");
            System.out.printf("%60s##~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~##\n", "");
            System.out.print(PrintColor.RESET);
        }
    }

    public static void printOrderDetail(ArrayList<OrderDetails> orderDetails) {
        if(!orderDetails.isEmpty()) {
            int i = 1;
            double total = 0;
            System.out.print(PrintColor.BLUE_BRIGHT);
            System.out.printf("\n\n%64s <<<<<:::      Current Order Details      :::>>>>>\n", "");
            System.out.printf("%26s++=============++==================================================++==============++====================++===================++\n", "");
            System.out.printf("%26s||  %-8s   ||   %-46s ||   %-10s ||   %-16s ||   %-15s ||\n", "", "Item ID", "Item Name & Description", "Quantity", "Unit Price(RM)", "Sub_Total(RM)");
            System.out.printf("%26s++=============++==================================================++==============++====================++===================++\n", "");
            for (OrderDetails o : orderDetails) {
                total = OrderDetails.calcTotalOfOrder(total, o.getSubTotalPerOrder());
                System.out.printf("%19sNo.%2d", "", i++);
                System.out.println(o);
                o.setTotalOfOrder(total);
            }
            System.out.printf("%26s++-------------++--------------------------------------------------++--------------++--------------------++-------------------++\n", "");
            System.out.printf("%26s~~~~~>  Number Of Order  =  %-3d %51s||   Total(RM):    %17.2f%6s ||\n", "", OrderDetails.getNoOfOrder(),  "", total, "");
            System.out.printf("%109s++=========================================++\n", "");
            System.out.print(PrintColor.PURPLE_BOLD);
        } else {
            System.out.print(PrintColor.BLUE);
            System.out.printf("\n%68s===========================================\n", "");
            System.out.printf("%86s|||||||\n", "");
            System.out.printf("%86s|||||||\n", "");
            System.out.printf("%60s##~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~##\n", "");
            System.out.printf("%60s||           There's No Order To Be Viewed...            ||\n", "");
            System.out.printf("%60s||   ---> Please Go On Adding Order For Reservation...   ||\n", "");
            System.out.printf("%60s##~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~##\n", "");
            System.out.print(PrintColor.RESET);
        }
    }


    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //Product
    public static void printAllProdDetails() {
        ArrayList<Food> foodDetails = getFoodDetails();
        ArrayList<Drink> drinkDetails = getDrinkDetails();
        ArrayList<Dessert> dessertDetails = getDessertDetails();
        HostSystem.logoDisplay(8);
        System.out.print(PrintColor.BLUE_BRIGHT);
        System.out.printf("%58s+=============================================================+\n", "");
        System.out.printf("%58s|  %-8s |  %-28s | %-15s |\n", "", "Food ID", "Food Name", "Food Price(RM)");
        System.out.printf("%58s+=============================================================+\n", "");
        for(Food f : foodDetails) {
            System.out.printf("%56s", "");
            System.out.print(f.toString());
        }
        System.out.printf("%58s+=============================================================+\n", "");
        System.out.print(PrintColor.RESET);

        HostSystem.logoDisplay(9);
        System.out.print(PrintColor.BLUE_BRIGHT);
        System.out.printf("%45s+=======================================================================================+\n","");
        System.out.printf("%45s|  %-8s |  %-16s |  %-14s |  %-15s |  %-15s |\n", "", "Drink ID", "Drink Name", "Hot Price(RM)", "Small Cold(RM)", "Large Cold(RM)");
        System.out.printf("%45s+=======================================================================================+\n","");
        for(Drink d : drinkDetails) {
            System.out.printf("%43s", "");
            System.out.print(d.toString());
        }
        System.out.printf("%45s+=======================================================================================+\n","");
        System.out.print(PrintColor.RESET);

        HostSystem.logoDisplay(10);
        System.out.print(PrintColor.BLUE_BRIGHT);
        System.out.printf("%59s+===========================================================+\n", "");
        System.out.printf("%59s|  %-11s |  %-19s |  %-17s  |\n", "", "Dessert ID", "Dessert Name", "Dessert Price(RM)");
        System.out.printf("%59s+===========================================================+\n", "");
        for(Dessert d : dessertDetails) {
            System.out.printf("%57s", "");
            System.out.print(d.toString());
        }
        System.out.printf("%59s+===========================================================+\n", "");
        System.out.print(PrintColor.RESET);
    }

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //Food
    public static ArrayList<Food> getFoodDetails() {
        ArrayList<Food> foodDetails = new ArrayList<>();
        int index = 0;
        try {
            Scanner fileScan = new Scanner(new File("Food.txt"));
            while(fileScan.hasNextLine()) {
                Food getFood = new Food();
                Scanner lineScan = new Scanner(fileScan.nextLine()).useDelimiter("/");
                while(lineScan.hasNext()) {
                    String data = lineScan.next();
                    switch(index) {
                        case 0 -> getFood.prodID = data;
                        case 1 -> getFood.prodName = data;
                        case 2 -> getFood.prodPrice = Double.parseDouble(data);
                    }
                    index++;
                }
                index = 0;
                foodDetails.add(getFood);
                lineScan.close();
            }
            fileScan.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return foodDetails;
    }

    public static Product foodSelect() throws ParseException, FileNotFoundException {
        ArrayList<Food> foodDetails = HostSystem.getFoodDetails();
        Product food = new Food();
        do {
            printFoodDetails(foodDetails);
            int selection = HostSystem.intValidation("%69sPlease Enter Selection (in number) ---> ");
            if(selection >= 1 && selection <= 27) {
                return food.getProdSelect(selection);
            } else {
                System.out.print(PrintColor.RED_BOLD);
                System.out.printf("\n%68s===========================================\n", "");
                System.out.printf("%86s|||||||\n", "");
                System.out.printf("%86s|||||||\n", "");
                System.out.printf("%60s##~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~##\n", "");
                System.out.printf("%60s||        Invalid SELECTION!! Please TRY again...        ||\n", "");
                System.out.printf("%60s||         Input should be in numeric form (1-27)        ||\n", "");
                System.out.printf("%60s##~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~##\n", "");
                System.out.print(PrintColor.RESET);
            }
        }while(true);
    }

    public static void printFoodDetails(ArrayList<Food> foodDetails) {
        int i = 1;
        HostSystem.logoDisplay(8);
        System.out.print(PrintColor.BLUE_BRIGHT);
        System.out.printf("%58s+=============================================================+\n", "");
        System.out.printf("%58s|  %-8s |  %-28s | %-15s |\n", "", "Food ID", "Food Name", "Food Price(RM)");
        System.out.printf("%58s+=============================================================+\n", "");
        for (Food f : foodDetails) {
            System.out.printf("%51sNo.%2d", "", i++);
            System.out.print(f.toString());
        }
        System.out.printf("%58s+=============================================================+\n", "");
        System.out.print(PrintColor.RESET);
    }


    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //Drink
    public static ArrayList<Drink> getDrinkDetails() {
        ArrayList<Drink> drinkDetails = new ArrayList<>();
        int index = 0;
        try {
            Scanner fileScan = new Scanner(new File("Drink.txt"));
            while(fileScan.hasNextLine()) {
                Drink getDrink = new Drink();
                Scanner lineScan = new Scanner(fileScan.nextLine()).useDelimiter("/");
                while(lineScan.hasNext()) {
                    String data = lineScan.next();
                    switch(index) {
                        case 0 -> getDrink.prodID = data;
                        case 1 -> getDrink.prodName = data;
                        case 2 -> getDrink.setHotPrice(Double.parseDouble(data));
                        case 3 -> getDrink.setSmallCold(Double.parseDouble(data));
                        case 4 -> getDrink.setLargeCold(Double.parseDouble(data));
                    }
                    index++;
                }
                index = 0;
                drinkDetails.add(getDrink);
                lineScan.close();
            }
            fileScan.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return drinkDetails;
    }

    public static Product drinkSelect() throws ParseException, FileNotFoundException {
        ArrayList<Drink> drinkDetails = HostSystem.getDrinkDetails();
        Product drink = new Drink();
        do {
            printDrinkDetails(drinkDetails);
            int selection = HostSystem.intValidation("%69sPlease Enter Selection (in number) ---> ");
            if(selection >= 1 && selection <= 27) {
                return drink.getProdSelect(selection);
            } else {
                System.out.print(PrintColor.RED_BOLD);
                System.out.printf("\n%68s===========================================\n", "");
                System.out.printf("%86s|||||||\n", "");
                System.out.printf("%86s|||||||\n", "");
                System.out.printf("%60s##~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~##\n", "");
                System.out.printf("%60s||        Invalid SELECTION!! Please TRY again...        ||\n", "");
                System.out.printf("%60s||         Input should be in numeric form (1-27)        ||\n", "");
                System.out.printf("%60s##~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~##\n", "");
                System.out.print(PrintColor.RESET);
            }
        }while(true);
    }

    public static void printDrinkDetails(ArrayList<Drink> drinkDetails) {
        int i = 1;
        HostSystem.logoDisplay(9);
        System.out.print(PrintColor.BLUE_BRIGHT);
        System.out.printf("%45s+=======================================================================================+\n","");
        System.out.printf("%45s|  %-8s |  %-16s |  %-14s |  %-15s |  %-15s |\n", "", "Drink ID", "Drink Name", "Hot Price(RM)", "Small Cold(RM)", "Large Cold(RM)");
        System.out.printf("%45s+=======================================================================================+\n","");
        for(Drink d : drinkDetails) {
            System.out.printf("%38sNo.%2d", "", i++);
            System.out.print(d.toString());
        }
        System.out.printf("%45s+=======================================================================================+\n","");
        System.out.print(PrintColor.RESET);
    }


    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //Dessert
    public static ArrayList<Dessert> getDessertDetails() {
        ArrayList<Dessert> dessertDetails = new ArrayList<>();
        int index = 0;
        try {
            Scanner fileScan = new Scanner(new File("Dessert.txt"));
            while(fileScan.hasNextLine()) {
                Dessert getDessert = new Dessert();
                Scanner lineScan = new Scanner(fileScan.nextLine()).useDelimiter("/");
                while(lineScan.hasNext()) {
                    String data = lineScan.next();
                    switch(index) {
                        case 0 -> getDessert.prodID = data;
                        case 1 -> getDessert.prodName = data;
                        case 2 -> getDessert.prodPrice = Double.parseDouble(data);
                    }
                    index++;
                }
                index = 0;
                dessertDetails.add(getDessert);
                lineScan.close();
            }
            fileScan.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return dessertDetails;
    }

    public static Product dessertSelect() throws ParseException, FileNotFoundException {
        ArrayList<Dessert> dessertDetails = HostSystem.getDessertDetails();
        Product dessert = new Dessert();
        do {
            printDessertDetails(dessertDetails);
            int selection = HostSystem.intValidation("%69sPlease Enter Selection (in number) ---> ");
            if(selection >= 1 && selection <= 27) {
                return dessert.getProdSelect(selection);
            } else {
                System.out.print(PrintColor.RED_BOLD);
                System.out.printf("\n%68s===========================================\n", "");
                System.out.printf("%86s|||||||\n", "");
                System.out.printf("%86s|||||||\n", "");
                System.out.printf("%60s##~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~##\n", "");
                System.out.printf("%60s||        Invalid SELECTION!! Please TRY again...        ||\n", "");
                System.out.printf("%60s||         Input should be in numeric form (1-27)        ||\n", "");
                System.out.printf("%60s##~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~##\n", "");
                System.out.print(PrintColor.RESET);
            }
        }while(true);
    }

    public static void printDessertDetails(ArrayList<Dessert> dessertDetails) {
        int i = 1;
        HostSystem.logoDisplay(10);
        System.out.print(PrintColor.BLUE_BRIGHT);
        System.out.printf("%59s+===========================================================+\n", "");
        System.out.printf("%59s|  %-11s |  %-19s |  %-17s  |\n", "", "Dessert ID", "Dessert Name", "Dessert Price(RM)");
        System.out.printf("%59s+===========================================================+\n", "");
        for (Dessert d : dessertDetails) {
            System.out.printf("%52sNo.%2d", "", i++);
            System.out.print(d.toString());
        }
        System.out.printf("%59s+===========================================================+\n", "");
        System.out.print(PrintColor.RESET);
    }


    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //Payment
    public static ArrayList<Payment> getAllPaymentDetails(ArrayList<Reservation> allReservationDetails)  {
        ArrayList<Payment> paymentDetails = new ArrayList<>();
        try {
            String line;
            BufferedReader readFile = new BufferedReader(new FileReader("Payment.txt"));
            while((line = readFile.readLine()) != null) {
                String[] data = line.split("/");
                for(Reservation r : allReservationDetails) {
                    if (Objects.equals(r.getReserveID(), data[0])) {
                        paymentDetails.add(new Payment(r, data[1], Double.parseDouble(data[2]), Double.parseDouble(data[3]), new SimpleDateFormat("dd-MM-yyyy").parse(data[4]),
                                Boolean.parseBoolean(data[5]), Double.parseDouble(data[6]), Double.parseDouble(data[7]), Double.parseDouble(data[8]), data[9]));
                    }
                }
            }
            readFile.close();
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
        return paymentDetails;
    }

    public static void paymentMenu(Payment payDetails, Reservation reservation, ArrayList<Payment> allPaymentDetails) throws ParseException {
        boolean end = true;
        payDetails = getIntoPayment(payDetails, reservation, allPaymentDetails);
        paymentWriteToFile(allPaymentDetails);
        if(payDetails.getPayID().equals("")) {
            System.out.print(PrintColor.BLUE);
            System.out.printf("\n%68s===========================================\n", "");
            System.out.printf("%86s|||||||\n", "");
            System.out.printf("%86s|||||||\n", "");
            System.out.printf("%60s##~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~##\n", "");
            System.out.printf("%60s||            There's No Payment To Be Made...           ||\n", "");
            System.out.printf("%60s||       Please Check All Details Whether Completed      ||\n", "");
            System.out.printf("%60s||     To Make Payment --> Please Confirm All Details    ||\n", "");
            System.out.printf("%60s##~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~##\n", "");
            System.out.print(PrintColor.RESET);
            return;
        }
        do {
            HostSystem.logoDisplay(11);
            System.out.print(PrintColor.CYAN_BOLD);
            System.out.printf("%60s##=============##========================================##\n", "");
            System.out.printf("%60s||  Selection  ||      Payment Information               ||\n", "");
            System.out.printf("%60s##-------------##----------------------------------------##\n", "");
            System.out.printf("%60s||      1.     ||      View Payment Details              ||\n", "");
            System.out.printf("%60s||      2.     ||      Proceed To Payment                ||\n", "");
            System.out.printf("%60s||      3.     ||      View Receipt                      ||\n", "");
            System.out.printf("%60s##-------------##----------------------------------------##\n", "");
            System.out.printf("%60s||      4.     ||      Back To Previous Menu             ||\n", "");
            System.out.printf("%60s##=============##========================================##\n", "");
            System.out.print(PrintColor.RESET);
            switch(HostSystem.intValidation("%69sPlease Enter Selection (in number) ---> ")) {
                case 1 -> printPaymentDetails(payDetails);
                case 2 -> {
                    boolean end2 = true;
                    do{
                        if(payDetails.getPayAmount() > 0 && !payDetails.isPayComplete()) {
                            HostSystem.logoDisplay(12);
                            System.out.print(PrintColor.CYAN_BOLD);
                            System.out.printf("%60s##=============##=========================================##\n", "");
                            System.out.printf("%60s||  Selection  ||      Payment Type                       ||\n", "");
                            System.out.printf("%60s##-------------##-----------------------------------------##\n", "");
                            System.out.printf("%60s||      1.     ||      Credit Card                        ||\n", "");
                            System.out.printf("%60s||      2.     ||      Online Banking                     ||\n", "");
                            System.out.printf("%60s##-------------##-----------------------------------------##\n", "");
                            System.out.printf("%60s||      3.     ||      Back To Previous Menu              ||\n", "");
                            System.out.printf("%60s##=============##=========================================##\n", "");
                            System.out.print(PrintColor.RESET);
                            switch (HostSystem.intValidation("%69sPlease Enter Selection (in number) ---> ")) {
                                case 1 -> { cardPay(payDetails, allPaymentDetails); end2 = false;}
                                case 2 -> { onlinePay(payDetails, allPaymentDetails); end2 = false;}
                                case 3 -> end2 = false;
                                default -> {
                                    System.out.print(PrintColor.RED_BOLD);
                                    System.out.printf("\n%68s===========================================\n", "");
                                    System.out.printf("%86s|||||||\n", "");
                                    System.out.printf("%86s|||||||\n", "");
                                    System.out.printf("%60s##~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~##\n", "");
                                    System.out.printf("%60s||        Invalid SELECTION!! Please TRY again...        ||\n", "");
                                    System.out.printf("%60s||         Input should be in numeric form (1-3)         ||\n", "");
                                    System.out.printf("%60s##~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~##\n", "");
                                    System.out.print(PrintColor.RESET);
                                }
                            }
                        } else {
                            System.out.print(PrintColor.BLUE);
                            System.out.printf("\n%68s===========================================\n", "");
                            System.out.printf("%86s|||||||\n", "");
                            System.out.printf("%86s|||||||\n", "");
                            System.out.printf("%60s##~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~##\n", "");
                            System.out.printf("%60s||            There's No Payment To Be Made...           ||\n", "");
                            System.out.printf("%60s||                          OR                           ||\n", "");
                            System.out.printf("%60s||             Payment Had Been Completed...             ||\n", "");
                            System.out.printf("%60s##~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~##\n", "");
                            System.out.print(PrintColor.RESET);
                            end2 = false;
                        }
                    }while(end2);
                }
                case 3 -> printReceipt(payDetails);
                case 4 -> end = false;
                default -> {
                    System.out.print(PrintColor.RED_BOLD);
                    System.out.printf("\n%68s===========================================\n", "");
                    System.out.printf("%86s|||||||\n", "");
                    System.out.printf("%86s|||||||\n", "");
                    System.out.printf("%60s##~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~##\n", "");
                    System.out.printf("%60s||        Invalid SELECTION!! Please TRY again...        ||\n", "");
                    System.out.printf("%60s||         Input should be in numeric form (1-5)         ||\n", "");
                    System.out.printf("%60s##~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~##\n", "");
                    System.out.print(PrintColor.RESET);
                }
            }
        }while(end);
    }

    public static Payment getIntoPayment(Payment payDetails, Reservation reservation, ArrayList<Payment> allPaymentDetails) {
        Payment pay = new Payment();
        if(payDetails.isPayComplete() || Payment.isConvertPay()) { return payDetails;}
        printReservationDetail(reservation);
        printOrderDetail(reservation.getOrder().getOrderDetails());
        System.out.print(PrintColor.CYAN_BRIGHT);
        System.out.printf("\n%40s   ++++++[    Required To Add Reservation Details To Payment By Typing 'Y' or 'y' !!!    ]++++++\n", "");
        System.out.printf("%40s---------------------------------------------------------------------------------------------------\n", "");
        System.out.print(PrintColor.RESET);
        char confirmation = HostSystem.charValidation("%68sAre You Confirm The Details (Y/y = yes): ", 1);
        System.out.print(PrintColor.CYAN_BRIGHT);
        System.out.printf("%68s==========================================\n", "");
        System.out.printf("%86s||||||\n", "");
        System.out.printf("%86s||||||\n", "");
        if (payDetails.checkConfirmation(confirmation)) {
            System.out.printf("%60s##~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~##\n", "");
            System.out.printf("%60s||     You Have Added Reservation Details To Payment     ||\n", "");
            System.out.printf("%60s||      Please Go On A Payment View Or Make Payment      ||\n", "");
            pay = Payment.buildPayment(payDetails, reservation);
            int size = allPaymentDetails.size()-1;
            for (int i = 0; i < allPaymentDetails.size(); i++) {
                if(size == i) {
                    allPaymentDetails.set(i, pay);
                }
            }
            paymentWriteToFile(allPaymentDetails);
            Payment.setConvertPay(true);
        } else {
            System.out.printf("%60s##~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~##\n", "");
            System.out.printf("%60s||      Failed To Add Reservation Details To Payment     ||\n", "");
            System.out.printf("%60s||                --->  Back To Main Menu                ||\n", "");
            System.out.printf("%60s||       Please Do Next Payment Adding If Any Needs      ||\n", "");
        }
        System.out.printf("%60s##~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~##\n\n", "");
        return pay;
    }

    public static void printPaymentDetails(Payment payDetails) {
        System.out.print(PrintColor.BLUE_BRIGHT);
        System.out.printf("\n\n%65s         ##===========================##\n", "");
        System.out.printf("%65s         ##      Payment Details      ##\n", "");
        System.out.printf("%65s         ##===========================##\n", "");
        System.out.printf("%65s                 |||         |||\n", "");
        System.out.printf("%65s                 |||         |||\n", "");
        System.out.printf("%65s #=============================================#\n", "");
        System.out.printf("%65s #    Checked By   :  %-25s#\n", "", payDetails.getReservation().getCust().getCustName());
        System.out.printf("%65s #    Payment Date :  %-25s#\n", "", new SimpleDateFormat("dd-MM-yyyy").format(payDetails.getPayDate()));
        System.out.printf("%65s #    Payment ID   :  %-25s#\n", "", payDetails.getPayID());
        System.out.printf("%65s #---------------------------------------------#\n", "");
        System.out.printf("%65s # ***          Sub_Total Includes         *** #\n", "");
        System.out.printf("%65s # ***     Table Charges & Meal Ordered    *** #\n", "");
        System.out.printf("%65s #---------------------------------------------#\n", "");
        System.out.printf("%65s #    Sub_Total              :  RM  %9.2f  #\n", "", payDetails.getSubTotal());
        System.out.printf("%65s #    Service Charges (S/C)  :  RM  %9.2f  #\n", "", payDetails.getScTotal());
        System.out.printf("%65s #    Service Tax (SST)      :  RM  %9.2f  #\n", "", payDetails.getSstTotal());
        System.out.printf("%65s #    Discount (-)           :  RM  %9.2f  #\n", "", payDetails.getDiscTotal());
        System.out.printf("%65s #                              -------------  #\n", "");
        System.out.printf("%65s #    Total                  :  RM  %9.2f  #\n", "", payDetails.getPayAmount());
        System.out.printf("%65s #                              -------------  #\n", "");
        System.out.printf("%65s #                                             #\n", "");
        System.out.printf("%65s #           ---  S/C    &    SST  ---         #\n", "");
        System.out.printf("%65s #           ---  10%%    &     6%%  ---         #\n", "");
        System.out.printf("%65s #=============================================#\n", "");
        if (payDetails.isPayComplete()) {
            System.out.printf("%65s                 |||         |||\n", "");
            System.out.printf("%65s                 |||         |||\n", "");
            System.out.printf("%55s ##+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++##\n", "");
            System.out.printf("%55s ##                                                               ##\n", "");
            System.out.printf("%55s ##     +~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~+     ##\n", "");
            System.out.printf("%55s ##     +                Payment Completed...               +     ##\n", "");
            System.out.printf("%55s ##     +     Please Contact Us When There Is Any Doubt     +     ##\n", "");
            System.out.printf("%55s ##     +              Our Pleasure To Meet You             +     ##\n", "");
            System.out.printf("%55s ##     +                    Thank You !!!                  +     ##\n", "");
            System.out.printf("%55s ##     +~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~+     ##\n", "");
            System.out.printf("%55s ##                                                               ##\n", "");
            System.out.printf("%55s ##+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++##\n", "");
        }
        System.out.print(PrintColor.RESET);
    }

    public static void printReceipt(Payment payDetails) {
        if(payDetails.isPayComplete()) {
            System.out.print(PrintColor.BLUE_BRIGHT);
            System.out.printf("\n\n\n%29s#------------------------------------------------------------------------------------------------------------------------#\n", "");
            System.out.printf("%29s|%120s|\n", "", "");
            System.out.print(PrintColor.RESET);
            HostSystem.logoDisplay(15);
            System.out.print(PrintColor.BLUE_BRIGHT);
            System.out.printf("%29s|%120s|\n", "", "");
            System.out.printf("%29s|     Murni-Bless Discovery%94s|\n", "", "");
            System.out.printf("%29s|     No. 41, Jalan 16/155C%94s|\n", "", "");
            System.out.printf("%29s|     Bandar Bukit Jalil, Bukit Jalil%84s|\n", "", "");
            System.out.printf("%29s|     57000 Kuala Lumpur    %59s Payment ID   :  %-15s%2s|\n", "", "", payDetails.getPayID(), "");
            System.out.printf("%29s|     Phone: (03) 7729-2234 %59s Receipt Date :  %-15s%2s|\n", "", "", new SimpleDateFormat("dd-MM-yyyy").format(payDetails.getPayDate()), "");
            System.out.printf("%29s|     Fax  : (03) 7729-2235 %59s Receipt Time :  %-15s%2s|\n", "", "", new SimpleDateFormat("HH:mm").format(payDetails.getPayDate()), "");
            System.out.printf("%29s|     --------------------------------------------------------------------------------------------------------------%5s|\n", "", "");
            System.out.printf("%29s|     BILL TO CUSTOMER:%98s|\n","", "");
            System.out.printf("%29s|     Full Name     : %-65s Customer ID  : %-15s%3s|\n", "", payDetails.getReservation().getCust().getCustName(), payDetails.getReservation().getCust().getUserID(), "");
            System.out.printf("%29s|     Contact No    : %-65s Reserve ID   : %-15s%3s|\n", "", payDetails.getReservation().getCust().getCustContact(), payDetails.getReservation().getReserveID(), "");
            System.out.printf("%29s|     Email Address : %-65s Reserve Date : %-15s%3s|\n", "", payDetails.getReservation().getCust().getCustEmail(), new SimpleDateFormat("dd-MM-yyyy").format(payDetails.getReservation().getReserveDate()), "");
            System.out.printf("%29s|     No.Of Persons : %-65d Reserve Time : %s - %-10s|\n", "", payDetails.getReservation().getNoOfPerson(), new SimpleDateFormat("HH:mm").format(payDetails.getReservation().getStartTime()), new SimpleDateFormat("HH:mm").format(payDetails.getReservation().getEndTime()));
            System.out.printf("%29s|%120s|\n", "", "");
            System.out.printf("%29s|     %32s#============================================#%37s|\n", "", "", "");
            System.out.printf("%29s|     %32s|           Table Reserved For You           |%37s|\n", "", "", "");
            System.out.printf("%29s|     %32s#============================================#%37s|\n", "", "", "");
            for (Table table : payDetails.getReservation().getTables()) {
                System.out.printf("%29s|     %32s|       %s - %-15s    %5.2f%% %2s   |%37s|\n", "", "", table.getTableID(), table.getTableDesc(), table.getTableCharges(), "", "");
            }
            System.out.printf("%29s|     %32s|                              --------%5s |%37s|\n", "", "", "", "");
            System.out.printf("%29s|     %32s|       TOTAL Charges :  %13.2f%% %2s   |%37s|\n", "", "", payDetails.getReservation().getTotalOfTableCharges(), "", "");
            System.out.printf("%29s|     %32s#============================================#%37s|\n", "", "", "");
            System.out.printf("%29s|%120s|\n", "", "");
            System.out.printf("%29s|     %35s Welcome to [  Murni-Bless Discovery  ]%41s|\n", "", "", "");
            System.out.printf("%29s|     #============================================================================================================#%5s|\n", "", "");
            System.out.printf("%29s|     |%13s%s%13s|     %-11s     |  %-4s  |   %-15s   |   %-11s  |%5s|\n", "", "", "Item Details", "", "Description", "Qty", "Unit Price (RM)", "Amount (RM)", "");
            System.out.printf("%29s|     #============================================================================================================#%5s|\n", "", "");
            for (OrderDetails order : payDetails.getReservation().getOrder().getOrderDetails()) {
                String desc = order.getProdDesc();
                System.out.printf("%29s|     |  %5s - %-28s|  %-19s|   %2d   |  %11.2f%6s  |  %12.2f  |%5s|\n", "", order.getProd().prodID, order.getProd().prodName, desc, order.getProdQty(), order.getProd().prodPrice, "", order.getSubTotalPerOrder(), "");
            }
            System.out.printf("%29s|     #============================================================================================================#%5s|\n", "", "");
            System.out.printf("%29s|     %68s         Table Charges  |  RM  %8.2f  |%5s|\n", "", "", Payment.getTotalOfTableCost(), "");
            System.out.printf("%29s|     %68s             Sub_Total  |  RM  %8.2f  |%5s|\n", "", "", payDetails.getSubTotal(), "");
            System.out.printf("%29s|     %68s  Service Charges(S/C)  |  RM  %8.2f  |%5s|\n", "", "", payDetails.getScTotal(), "");
            System.out.printf("%29s|     %68s      Service Tax(SST)  |  RM  %8.2f  |%5s|\n", "", "", payDetails.getSstTotal(), "");
            System.out.printf("%29s|     %68s           Discount(-)  |  RM  %8.2f  |%5s|\n", "", "", payDetails.getDiscTotal(), "");
            System.out.printf("%29s|     %68s                        #----------------#%5s|\n", "", "", "");
            System.out.printf("%29s|     %68s                 Total  |  RM  %8.2f  |%5s|\n", "", "", payDetails.getPayAmount(), "");
            System.out.printf("%29s|     %68s                        #================#%5s|\n", "", "", "");
            System.out.printf("%29s|     %68s                            #=========#%8s|\n", "", "", "");
            System.out.printf("%29s|%120s|\n", "", "");
            System.out.printf("%29s|     Payment Has Been Done !!                 %74s|\n", "", "");
            System.out.printf("%29s|     Show This Receipt To Staff When Reach    %74s|\n", "", "");
            System.out.printf("%29s|     Please Contact Us If There's Any Doubt...%74s|\n", "", "");
            System.out.printf("%29s|%120s|\n", "", "");
            System.out.printf("%29s|%46sTHANK YOU FOR YOUR BUSINESS%47s|\n", "", "", "");
            System.out.printf("%29s|%46s     Please Come Again     %47s|\n", "", "", "");
            System.out.print(PrintColor.RESET);
            HostSystem.logoDisplay(16);
            System.out.print(PrintColor.BLUE_BRIGHT);
            System.out.printf("%29s|%120s|\n", "", "");
            System.out.printf("%29s#------------------------------------------------------------------------------------------------------------------------#\n", "");
        } else {
            System.out.print(PrintColor.BLUE);
            System.out.printf("\n%68s===========================================\n", "");
            System.out.printf("%86s|||||||\n", "");
            System.out.printf("%86s|||||||\n", "");
            System.out.printf("%60s##~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~##\n", "");
            System.out.printf("%60s||          There Is No Receipt To Be Viewed...          ||\n", "");
            System.out.printf("%60s||                          OR                           ||\n", "");
            System.out.printf("%60s||             Payment Haven't Completed...              ||\n", "");
            System.out.printf("%60s##~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~##\n", "");
        }
        System.out.print(PrintColor.RESET);
    }

    public static void viewAmountWhenPay(Payment payDetails) {
        System.out.print(PrintColor.BLUE_BRIGHT);
        System.out.printf("\n\n%65s         ##===========================##\n", "");
        System.out.printf("%65s         ##      Payment Details      ##\n", "");
        System.out.printf("%65s         ##===========================##\n", "");
        System.out.printf("%65s                 |||         |||\n", "");
        System.out.printf("%65s                 |||         |||\n", "");
        System.out.printf("%65s #=============================================#\n", "");
        System.out.printf("%65s #    Sub_Total              :  RM  %9.2f  #\n", "", payDetails.getSubTotal());
        System.out.printf("%65s #    Service Charges (S/C)  :  RM  %9.2f  #\n", "", payDetails.getScTotal());
        System.out.printf("%65s #    Service Tax (SST)      :  RM  %9.2f  #\n", "", payDetails.getSstTotal());
        System.out.printf("%65s #    Discount (-)           :  RM  %9.2f  #\n", "", payDetails.getDiscTotal());
        System.out.printf("%65s #                              -------------  #\n", "");
        System.out.printf("%65s #    Total                  :  RM  %9.2f  #\n", "", payDetails.getPayAmount());
        System.out.printf("%65s #                              -------------  #\n", "");
        System.out.printf("%65s #=============================================#\n", "");
        System.out.print(PrintColor.RESET);
    }

    public static void paymentWriteToFile(ArrayList<Payment> paymentDetails) {
        try {
            FileWriter writer = new FileWriter("Payment.txt");
            for (Payment paymentDetail : paymentDetails) {
                String str = paymentDetail.contentWriteToFile() + "\n";
                writer.write(str);
            }
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //CreditCard
    public static ArrayList<CreditCard> getCardDetails()  {
        ArrayList<CreditCard> cardDetails = new ArrayList<>();
        int index = 0;
        try {
            Scanner fileScan = new Scanner(new File("CreditCard.txt"));
            while(fileScan.hasNextLine()) {
                CreditCard getCard = new CreditCard();
                Scanner lineScan = new Scanner(fileScan.nextLine()).useDelimiter("-");
                while(lineScan.hasNext()) {
                    String data = lineScan.next();
                    switch(index) {
                        case 0 -> getCard.setCredCardNo(Long.parseLong(data));
                        case 1 -> getCard.setCardName(data);
                        case 2 -> getCard.setCardExpDate(new SimpleDateFormat("MM/yy").parse(data));
                        case 3 -> getCard.setCvvNo(Integer.parseInt(data));
                        case 4 -> getCard.setCardPinNo(Integer.parseInt(data));
                    }
                    index++;
                }
                index = 0;
                cardDetails.add(getCard);
                lineScan.close();
            }
            fileScan.close();
        } catch (FileNotFoundException | ParseException e) {
            e.printStackTrace();
        }
        return cardDetails;
    }

    public static void cardPay(Payment payDetails, ArrayList<Payment> allPaymentDetails) throws ParseException {
        ArrayList<CreditCard> cardDetails = getCardDetails();
        CreditCard card = new CreditCard(payDetails.getPayID(), payDetails.getPayAmount(), payDetails.getPayDate(), payDetails.isPayComplete(), "Credit Card", 0, "", new Date(), 0, 0);
        viewAmountWhenPay(payDetails);
        if(cardVerification(card, cardDetails)) {
            payDetails.setPayComplete(true);
            System.out.print(PrintColor.BLUE);
            System.out.printf("%68s===========================================\n", "");
            System.out.printf("%86s|||||||\n", "");
            System.out.printf("%86s|||||||\n", "");
            System.out.printf("%60s##~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~##\n", "");
            System.out.printf("%60s||          Congratulations, Payment SUCCESSFUL          ||\n", "");
            System.out.printf("%60s||           Thank You For Using Our Services!           ||\n", "");
            System.out.printf("%60s||         Hope To See You Soon & Have A Nice Day        ||\n", "");
            System.out.printf("%60s##~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~##\n", "");
            System.out.print(PrintColor.RESET);
            for(Payment p : allPaymentDetails) {
                if(Objects.equals(p.getPayID(), card.getPayID())) {
                    p.setPayComplete(true);
                    p.setPayMethod(card.getPayMethod());
                }
            }
            paymentWriteToFile(allPaymentDetails);
            printReceipt(payDetails);
        } else {
            System.out.print(PrintColor.BLUE);
            System.out.printf("\n\n%68s===========================================\n", "");
            System.out.printf("%86s|||||||\n", "");
            System.out.printf("%86s|||||||\n", "");
            System.out.printf("%60s##~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~##\n", "");
            System.out.printf("%60s||              Sorry, Payment UNSUCCESSFUL              ||\n", "");
            System.out.printf("%60s||    Please Do Payment Again If Confirm All Details     ||\n", "");
            System.out.printf("%60s||                     Thank You !!!                     ||\n", "");
            System.out.printf("%60s##~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~##\n", "");
            System.out.print(PrintColor.RESET);
        }
    }

    public static boolean cardVerification(CreditCard card, ArrayList<CreditCard> cardDetails) throws ParseException {
        int attempt = 0;
        do{
            HostSystem.logoDisplay(13);
            System.out.print(PrintColor.CYAN);
            System.out.printf("%60s##~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~##\n", "");
            System.out.printf("%60s||          Digital Card Is Used To Make Payment         ||\n", "");
            System.out.printf("%60s||  Please Key In Details Below To Complete Reservation  ||\n", "");
            System.out.printf("%60s##~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~##\n", "");
            System.out.print(PrintColor.RESET);
            card.setCredCardNo(cardNumValidation("%60sEnter Card Number: "));
            card.setCardName(HostSystem.strValidation("%60sEnter Name On Card: ", 1));
            card.setCardExpDate(cardDateValidation("%60sEnter Card Expiry Date: "));
            card.setCvvNo(cardCVVValidation("%60sEnter Card Verification Value (CVV - 3 digits): "));
            card.setCardPinNo(cardPinValidation("%60sEnter Card Pin Number (6-digits): "));
            for (CreditCard cardLists : cardDetails) {
                if (card.equals(cardLists)) {
                    viewAmountWhenPay(card);
                    System.out.print(PrintColor.CYAN_BRIGHT);
                    System.out.printf("\n%49s   ++++++[     Required To Confirm PAYMENT By Typing 'Y' or 'y' !!     ]++++++\n", "");
                    System.out.printf("%45s-----------------------------------------------------------------------------------------\n", "");
                    System.out.print(PrintColor.RESET);
                    char confirmation = HostSystem.charValidation("%68sAre You Confirm The Details (Y/y = yes): ", 1);
                    if (card.checkConfirmation(confirmation)) {
                        card.setPayComplete(true);
                        return true;
                    }
                    else if(Character.toUpperCase(confirmation) == 'N') {
                        card.setPayComplete(false);
                    }
                }
            }
            System.out.print(PrintColor.BLUE);
            System.out.printf("\n%68s===========================================\n", "");
            System.out.printf("%86s|||||||\n", "");
            System.out.printf("%86s|||||||\n", "");
            System.out.printf("%60s##~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~##\n", "");
            System.out.printf("%60s||       There Are Some Mismatched Information !!!       ||\n", "");
            System.out.printf("%60s||                          OR                           ||\n", "");
            System.out.printf("%60s||              Does Not Confirm Payment...              ||\n", "");
            System.out.printf("%60s||                  Please Try Again...                  ||\n", "");
            System.out.printf("%60s##~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~##\n", "");
            System.out.print(PrintColor.RESET);
            System.out.print(PrintColor.CYAN_BRIGHT);
            System.out.printf("\n%49s   ++++++[    Should Try To Enter Details Again, Type 'Y' or 'y' !!!    ]++++++\n", "");
            System.out.printf("%46s----------------------------------------------------------------------------------------\n", "");
            System.out.print(PrintColor.RESET);
            attempt++;
            char nextAttempt = HostSystem.charValidation("%62sRe-Try Card Details Entry For Payment (Y/y = yes) >>> ", 1);
            if(!card.checkConfirmation(nextAttempt)) { return false; }
        } while (attempt < Payment.MAX_ATTEMPT);
        return true;
    }

    public static long cardNumValidation(String prompt) {
        long cardNo = HostSystem.longValidation(prompt);
        while(true) {
            if(CreditCard.checkCardNum(cardNo)) { return cardNo; }
            System.out.print(PrintColor.RED_BOLD);
            System.out.printf("\n%68s===========================================\n", "");
            System.out.printf("%86s|||||||\n", "");
            System.out.printf("%86s|||||||\n", "");
            System.out.printf("%60s##~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~##\n", "");
            System.out.printf("%60s||      A Credit Card Number Must Have 16 Digits !!      ||\n", "");
            System.out.printf("%60s||                          OR                           ||\n", "");
            System.out.printf("%60s||       The Length Of Card Number Is Incorrect !!       ||\n", "");
            System.out.printf("%60s||                  Please Try Again...                  ||\n", "");
            System.out.printf("%60s##~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~##\n", "");
            System.out.print(PrintColor.RESET);
            cardNo = HostSystem.longValidation("%60sPlease Re-Enter Card Number: ");
        }
    }

    public static Date cardDateValidation(String prompt) throws ParseException {
        Date expDate = HostSystem.dateTimeValidation(prompt, 3);
        while(true) {
            if(CreditCard.checkExpDate(expDate)) { return expDate; }
            System.out.print(PrintColor.RED_BOLD);
            System.out.printf("\n%68s===========================================\n", "");
            System.out.printf("%86s|||||||\n", "");
            System.out.printf("%86s|||||||\n", "");
            System.out.printf("%60s##~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~##\n", "");
            System.out.printf("%60s||                The Card Is Expired !!!                ||\n", "");
            System.out.printf("%60s||      --> Expiry Date Must Exceed Current Date...      ||\n", "");
            System.out.printf("%60s||                  Please Try Again...                  ||\n", "");
            System.out.printf("%60s##~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~##\n", "");
            System.out.print(PrintColor.RESET);
            expDate = HostSystem.dateTimeValidation("%60sPlease Re-Enter Card Expiry Date: ", 3);
        }
    }

    public static int cardCVVValidation(String prompt) {
        int cvv = HostSystem.intValidation(prompt);
        while(true) {
            if(CreditCard.checkCvvNum(cvv)) { return cvv; }
            System.out.print(PrintColor.RED_BOLD);
            System.out.printf("\n%68s===========================================\n", "");
            System.out.printf("%86s|||||||\n", "");
            System.out.printf("%86s|||||||\n", "");
            System.out.printf("%60s##~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~##\n", "");
            System.out.printf("%60s||               CVV Number Is Invalid !!!               ||\n", "");
            System.out.printf("%60s||           ---> It Should Have 3 Digits Only           ||\n", "");
            System.out.printf("%60s||     Without Any Alphabetic & Special Characters...    ||\n", "");
            System.out.printf("%60s||                  Please Try Again...                  ||\n", "");
            System.out.printf("%60s##~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~##\n", "");
            System.out.print(PrintColor.RESET);
            cvv = HostSystem.intValidation("%60sPlease Re-Enter Card Verification Value (CVV - 3 digits): ");
        }
    }

    public static int cardPinValidation(String prompt) {
        int cardPin = HostSystem.intValidation(prompt);
        while(true) {
            if(CreditCard.checkPinNum(cardPin)) { return cardPin; }
            System.out.print(PrintColor.RED_BOLD);
            System.out.printf("\n%68s===========================================\n", "");
            System.out.printf("%86s|||||||\n", "");
            System.out.printf("%86s|||||||\n", "");
            System.out.printf("%60s##~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~##\n", "");
            System.out.printf("%60s||               PIN Number Is Invalid !!!               ||\n", "");
            System.out.printf("%60s||           ---> It Should Have 6 Digits Only           ||\n", "");
            System.out.printf("%60s||     Without Any Alphabetic & Special Characters...    ||\n", "");
            System.out.printf("%60s||                  Please Try Again...                  ||\n", "");
            System.out.printf("%60s##~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~##\n", "");
            System.out.print(PrintColor.RESET);
            cardPin = HostSystem.intValidation("%60sPlease Re-Enter Pin Number (6 digits): ");
        }
    }

    public static void viewAmountWhenPay(CreditCard card) {
        System.out.print(PrintColor.BLUE_BRIGHT);
        System.out.printf("\n\n%60s              ##===========================##\n", "");
        System.out.printf("%60s              ##        Credit Card        ##\n", "");
        System.out.printf("%60s              ##===========================##\n", "");
        System.out.printf("%60s                      |||         |||\n", "");
        System.out.printf("%60s                      |||         |||\n", "");
        System.out.printf("%60s #=======================================================#\n", "");
        System.out.printf("%60s #      Pay To             :    %-18s    #\n", "", "Murni Bless Discovery");
        System.out.printf("%60s #      PaymentID          :    %-18s       #\n", "", card.getPayID());
        System.out.printf("%60s #      Date & Time        :    %s  %-10s   #\n", "", new SimpleDateFormat("dd/MM/yyyy").format(card.getPayDate()), new SimpleDateFormat("HH:mm:ss").format(card.getPayDate()));
        System.out.printf("%60s #      Name on Card       :    %-18s       #\n", "", card.getCardName());
        System.out.printf("%60s #      Card Number        :    %-18s       #\n", "", card.getCredCardNo());
        System.out.printf("%60s #      Exp Date - CVV     :    %s   -   %-7d      #\n", "", new SimpleDateFormat("MM/yy").format(card.getCardExpDate()), card.getCvvNo());
        System.out.printf("%60s #      Amount             :    RM  %-16.2f     #\n", "", card.getPayAmount());
        System.out.printf("%60s #=======================================================#\n", "");
        System.out.print(PrintColor.RESET);
    }


    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //OnlineBanking
    public static ArrayList<OnlineBanking> getOnlineBankDetails() {
        ArrayList<OnlineBanking> onlineBankDetails = new ArrayList<>();
        int index = 0;
        try {
            Scanner fileScan = new Scanner(new File("OnlineBanking.txt"));
            while(fileScan.hasNextLine()) {
                OnlineBanking getOnlineBank = new OnlineBanking();
                Scanner lineScan = new Scanner(fileScan.nextLine()).useDelimiter("/");
                while(lineScan.hasNext()) {
                    String data = lineScan.next();
                    switch(index) {
                        case 0 -> getOnlineBank.setBankType(data);
                        case 1 -> getOnlineBank.setAccName(data);
                        case 2 -> getOnlineBank.setAccPassword(data);
                        case 3 -> getOnlineBank.setAccNo(data);
                        case 4 -> getOnlineBank.setTacNo(Integer.parseInt(data));
                    }
                    index++;
                }
                index = 0;
                onlineBankDetails.add(getOnlineBank);
                lineScan.close();
            }
            fileScan.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return onlineBankDetails;
    }

    public static void onlinePay(Payment payDetails, ArrayList<Payment> allPaymentDetails) {
        ArrayList<OnlineBanking> onlineBankDetails = getOnlineBankDetails();
        OnlineBanking onlineBank = new OnlineBanking(payDetails.getPayID(), payDetails.getPayAmount(), payDetails.getPayDate(), payDetails.isPayComplete(), "Online Banking", "", "", "", "", 0);
        viewAmountWhenPay(onlineBank);
        if(accVerification(onlineBank, onlineBankDetails)) {
            payDetails.setPayComplete(true);
            System.out.print(PrintColor.BLUE);
            System.out.printf("\n\n%68s===========================================\n", "");
            System.out.printf("%86s|||||||\n", "");
            System.out.printf("%86s|||||||\n", "");
            System.out.printf("%60s##~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~##\n", "");
            System.out.printf("%60s||          Congratulations, Payment SUCCESSFUL          ||\n", "");
            System.out.printf("%60s||           Thank You For Using Our Services!           ||\n", "");
            System.out.printf("%60s||         Hope To See You Soon & Have A Nice Day        ||\n", "");
            System.out.printf("%60s##~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~##\n", "");
            System.out.print(PrintColor.RESET);
            for(Payment p : allPaymentDetails) {
                if(p.equals(onlineBank)) {
                    p.setPayComplete(true);
                    p.setPayMethod(onlineBank.getPayMethod());
                }
            }
            paymentWriteToFile(allPaymentDetails);
            printReceipt(payDetails);
        } else {
            System.out.print(PrintColor.BLUE);
            System.out.printf("\n\n%68s===========================================\n", "");
            System.out.printf("%86s|||||||\n", "");
            System.out.printf("%86s|||||||\n", "");
            System.out.printf("%60s##~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~##\n", "");
            System.out.printf("%60s||              Sorry, Payment UNSUCCESSFUL              ||\n", "");
            System.out.printf("%60s||   Please Do Payment Again If All Details Confirmed    ||\n", "");
            System.out.printf("%60s||                     Thank You !!!                     ||\n", "");
            System.out.printf("%60s##~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~##\n", "");
            System.out.print(PrintColor.RESET);
        }
    }

    public static boolean accVerification(OnlineBanking onlineBank, ArrayList<OnlineBanking> onlineBankDetails)  {
        int selection, accAttempt = 0, tacAttempt = 0;
        do{
            HostSystem.logoDisplay(14);
            System.out.print(PrintColor.CYAN);
            System.out.printf("%60s##~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~##\n", "");
            System.out.printf("%60s||        Online Banking Is Used To Make Payment         ||\n", "");
            System.out.printf("%60s||  Please Key In Details Below To Complete Reservation  ||\n", "");
            System.out.printf("%60s##~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~##\n", "");
            System.out.print(PrintColor.RESET);
            do {
                System.out.print(PrintColor.CYAN);
                System.out.printf("\n\n%63s        Please Choose Bank Type Stated Below: \n", "");
                System.out.printf("%49s[-------------------------------------------------------------------------------]\n", "");
                System.out.printf("%49s[                         Which Bank Type Desired ?                             ]\n", "");
                System.out.printf("%49s[      [ 1 ]---MAYBANK       [ 2 ]---PUBLIC BANK       [ 3 ]---HONG LEONG       ]\n", "");
                System.out.printf("%49s[      [ 3 ]---CIMB          [ 5 ]---OCBC              [ 6 ]---HSBC             ]\n", "");
                System.out.printf("%49s[-------------------------------------------------------------------------------]\n", "");
                System.out.print(PrintColor.RESET);
                selection = HostSystem.intValidation("%60sPlease Enter Bank Type Preferred(in number): ");
            }while(selection < 1 || selection > 6);
            onlineBank.setBankType(OnlineBanking.BANK_NAME[selection - 1]);
            onlineBank.setAccName(HostSystem.strValidation("%60sEnter Account Name: ", 1));
            onlineBank.setAccNo(accNumberValidation("%60sEnter Account Number: "));
            onlineBank.setAccPassword(accPassValidation("%60sEnter Account Password: "));
            for (OnlineBanking ob : onlineBankDetails) {
                if (onlineBank.equals(ob)) {
                    viewAmountWhenPay(onlineBank);
                    do {
                        tacAttempt++;
                        onlineBank.setTacNo(onlineTACValidation("%72sEnter TAC Number (6-digits): "));
                        if (Objects.equals(ob.getTacNo(), onlineBank.getTacNo())) {
                            System.out.print(PrintColor.CYAN_BRIGHT);
                            System.out.printf("\n%49s   ++++++[     Required To Confirm PAYMENT By Typing 'Y' or 'y' !!     ]++++++\n", "");
                            System.out.printf("%45s-----------------------------------------------------------------------------------------\n", "");
                            System.out.print(PrintColor.RESET);
                            char confirmation = HostSystem.charValidation("%68sAre You Confirm The Details (Y/y = yes): ", 1);
                            onlineBank.setPayComplete(onlineBank.checkConfirmation(confirmation));
                            return true;
                        }
                        System.out.print(PrintColor.BLUE);
                        System.out.printf("\n%68s===========================================\n", "");
                        System.out.printf("%86s|||||||\n", "");
                        System.out.printf("%86s|||||||\n", "");
                        System.out.printf("%60s##~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~##\n", "");
                        System.out.printf("%60s||             Invalid TAC Number Entry !!!              ||\n", "");
                        System.out.printf("%60s||                  Please Try Again...                  ||\n", "");
                        System.out.printf("%60s##~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~##\n", "");
                        System.out.print(PrintColor.RESET);
                    }while(tacAttempt < OnlineBanking.MAX_ATTEMPT);
                }
            }
            if(tacAttempt == OnlineBanking.MAX_ATTEMPT) {return false;}
            System.out.print(PrintColor.BLUE);
            System.out.printf("\n%68s===========================================\n", "");
            System.out.printf("%86s|||||||\n", "");
            System.out.printf("%86s|||||||\n", "");
            System.out.printf("%60s##~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~##\n", "");
            System.out.printf("%60s||       There Are Some Mismatched Information !!!       ||\n", "");
            System.out.printf("%60s||                          OR                           ||\n", "");
            System.out.printf("%60s||              Does Not Confirm Payment...              ||\n", "");
            System.out.printf("%60s||                  Please Try Again...                  ||\n", "");
            System.out.printf("%60s##~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~##\n", "");
            System.out.print(PrintColor.RESET);
            System.out.print(PrintColor.CYAN_BRIGHT);
            System.out.printf("\n%49s   ++++++[    Should Try To Enter Details Again, Type 'Y' or 'y' !!!    ]++++++\n", "");
            System.out.printf("%46s----------------------------------------------------------------------------------------\n", "");
            System.out.print(PrintColor.RESET);
            accAttempt++;
            char nextAttempt = HostSystem.charValidation("%60sRe-Try Account Details Entry For Payment (Y/y = yes) >>> ", 1);
            if(!onlineBank.checkConfirmation(nextAttempt)) { return false; }
        }while(accAttempt < Payment.MAX_ATTEMPT);
        return true;
    }

    public static String accNumberValidation(String prompt) {
        String accNum = HostSystem.strValidation(prompt, 3);
        while(true) {
            int length = accNum.length();
            if(OnlineBanking.checkAccNumLength(length)) {return accNum;}
            System.out.print(PrintColor.RED_BOLD);
            System.out.printf("\n%68s===========================================\n", "");
            System.out.printf("%86s|||||||\n", "");
            System.out.printf("%86s|||||||\n", "");
            System.out.printf("%60s##~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~##\n", "");
            System.out.printf("%60s||      An Account Number Must Have 10-12 Digits !!!     ||\n", "");
            System.out.printf("%60s||                          OR                           ||\n", "");
            System.out.printf("%60s||     The Length Of Account Number Is Incorrect !!!     ||\n", "");
            System.out.printf("%60s||                  Please Try Again...                  ||\n", "");
            System.out.printf("%60s##~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~##\n", "");
            System.out.print(PrintColor.RESET);
            accNum = HostSystem.strValidation("%60sPlease Re-Enter Account Number: ", 3);
        }
    }

    public static String accPassValidation(String prompt) {
        Scanner input = new Scanner(System.in);
        System.out.print(PrintColor.CYAN_BRIGHT);
        System.out.printf(prompt, "");
        String accPass = input.next();
        System.out.print(PrintColor.RESET);
        while(true) {
            if(OnlineBanking.checkAccPass(accPass)) { return accPass; }
            System.out.print(PrintColor.RED_BOLD);
            System.out.printf("\n%68s===========================================\n", "");
            System.out.printf("%86s|||||||\n", "");
            System.out.printf("%86s|||||||\n", "");
            System.out.printf("%60s##~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~##\n", "");
            System.out.printf("%60s||               Invalid Password Entry !!               ||\n", "");
            System.out.printf("%60s##=======================================================##\n", "");
            System.out.printf("%60s||         Rule Of Password In Bank Account : -          ||\n", "");
            System.out.printf("%60s||     1. Length Must Be Between 10 and 16               ||\n", "");
            System.out.printf("%60s||     2. Must Have At Least One Numeric Character       ||\n", "");
            System.out.printf("%60s||     3. Must Have At Least One Lowercase Character     ||\n", "");
            System.out.printf("%60s||     4. Must Have At Least One Uppercase Character     ||\n", "");
            System.out.printf("%60s||     5. Must Have At Least One Special Character       ||\n", "");
            System.out.printf("%60s##~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~##\n", "");
            System.out.print(PrintColor.RESET);
            System.out.print(PrintColor.CYAN_BRIGHT);
            System.out.printf("%60sPlease Re-Enter Password: ", "");
            accPass = input.next();
            System.out.print(PrintColor.RESET);
        }
    }

    public static int onlineTACValidation(String prompt) {
        int tac = HostSystem.intValidation(prompt);
        while(true) {
            if(OnlineBanking.checkTacNum(tac)) { return tac; }
            System.out.print(PrintColor.RED_BOLD);
            System.out.printf("\n%68s===========================================\n", "");
            System.out.printf("%86s|||||||\n", "");
            System.out.printf("%86s|||||||\n", "");
            System.out.printf("%60s##~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~##\n", "");
            System.out.printf("%60s||               TAC Number Is Invalid !!!               ||\n", "");
            System.out.printf("%60s||           ---> It Should Have 6 Digits Only           ||\n", "");
            System.out.printf("%60s||     Without Any Alphabetic & Special Characters...    ||\n", "");
            System.out.printf("%60s||                  Please Try Again...                  ||\n", "");
            System.out.printf("%60s##~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~##\n", "");
            System.out.print(PrintColor.RESET);
            tac = HostSystem.intValidation("%60sPlease Re-Enter TAC Number (6-digits): ");
        }
    }

    public static void viewAmountWhenPay(OnlineBanking onlineBank) {
        System.out.print(PrintColor.BLUE_BRIGHT);
        System.out.printf("\n\n%60s              ##===========================##\n", "");
        System.out.printf("%60s              ##       Online Banking      ##\n", "");
        System.out.printf("%60s              ##===========================##\n", "");
        System.out.printf("%60s                      |||         |||\n", "");
        System.out.printf("%60s                      |||         |||\n", "");
        System.out.printf("%60s #=======================================================#\n", "");
        System.out.printf("%60s #      PaymentID          :    %-18s       #\n", "", onlineBank.getPayID());
        System.out.printf("%60s #      Date & Time        :    %s  %-10s   #\n", "", new SimpleDateFormat("dd/MM/yyyy").format(onlineBank.getPayDate()), new SimpleDateFormat("HH:mm:ss").format(onlineBank.getPayDate()));
        System.out.printf("%60s #      Pay To  %41s#\n", "", "");
        System.out.printf("%60s #      ------  %41s#\n", "", "");
        System.out.printf("%60s #      Party Received     :    %-18s    #\n", "", "Murni Bless Discovery");
        System.out.printf("%60s #      Issuing Bank       :    %-21s    #\n", "", onlineBank.getBankType());
        System.out.printf("%60s #      %49s#\n", "", "");
        System.out.printf("%60s #      Pay From%41s#\n", "", "");
        System.out.printf("%60s #      --------%41s#\n", "", "");
        System.out.printf("%60s #      Account Name       :    %-18s       #\n", "", onlineBank.getAccName());
        System.out.printf("%60s #      Account Number     :    %-18s       #\n", "", onlineBank.getAccNo());
        System.out.printf("%60s #      Bank Name          :    %-21s    #\n", "", onlineBank.getBankType());
        System.out.printf("%60s #      Amount             :    RM  %-16.2f     #\n", "", onlineBank.getPayAmount());
        System.out.printf("%60s #=======================================================#\n", "");
        System.out.print(PrintColor.RESET);
    }


    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //Validation
    public static int intValidation(String prompt) {
        boolean end;
        int num = 0;
        do {
            try {
                Scanner input = new Scanner(System.in);
                System.out.print(PrintColor.CYAN_BRIGHT);
                System.out.printf(prompt, "");
                num = input.nextInt();
                System.out.print(PrintColor.RESET);
                end = false;
            } catch (InputMismatchException e) {
                System.out.print(PrintColor.RED_BOLD);
                System.out.printf("\n%68s===========================================\n", "");
                System.out.printf("%86s|||||||\n", "");
                System.out.printf("%86s|||||||\n", "");
                System.out.printf("%60s##~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~##\n", "");
                System.out.printf("%60s||         Invalid Input !!! Please TRY again...         ||\n", "");
                System.out.printf("%60s||            It Should Be Numeric Form Only.            ||\n", "");
                System.out.printf("%60s##~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~##\n", "");
                System.out.print(PrintColor.RESET);
                end = true;
            }
        }while(end);
        return num;
    }

    public static double doubleValidation(String prompt) {
        boolean end;
        double num = 0;
        do {
            try {
                Scanner input = new Scanner(System.in);
                System.out.print(PrintColor.CYAN_BRIGHT);
                System.out.printf(prompt, "");
                num = input.nextDouble();
                System.out.print(PrintColor.RESET);
                end = false;
            } catch (InputMismatchException e) {
                System.out.print(PrintColor.RED_BOLD);
                System.out.printf("\n%68s===========================================\n", "");
                System.out.printf("%86s|||||||\n", "");
                System.out.printf("%86s|||||||\n", "");
                System.out.printf("%60s##~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~##\n", "");
                System.out.printf("%60s||         Invalid Input !!! Please TRY again...         ||\n", "");
                System.out.printf("%60s||            It Should Be Numeric Form Only.            ||\n", "");
                System.out.printf("%60s##~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~##\n", "");
                end = true;
                System.out.print(PrintColor.RESET);
            }
        }while(end);
        return num;
    }

    public static long longValidation(String prompt) {
        boolean end;
        long num = 0;
        do {
            try {
                Scanner input = new Scanner(System.in);
                System.out.print(PrintColor.CYAN_BRIGHT);
                System.out.printf(prompt, "");
                num = input.nextLong();
                System.out.print(PrintColor.RESET);
                end = false;
            } catch (InputMismatchException e) {
                System.out.print(PrintColor.RED_BOLD);
                System.out.printf("\n%68s===========================================\n", "");
                System.out.printf("%86s|||||||\n", "");
                System.out.printf("%86s|||||||\n", "");
                System.out.printf("%60s##~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~##\n", "");
                System.out.printf("%60s||         Invalid Input !!! Please TRY again...         ||\n", "");
                System.out.printf("%60s||            It Should Be Numeric Form Only.            ||\n", "");
                System.out.printf("%60s##~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~##\n", "");
                System.out.print(PrintColor.RESET);
                end = true;
            }
        }while(end);
        return num;
    }

    public static char charValidation(String prompt, int identify) {
        Scanner input = new Scanner(System.in);
        boolean end;
        char character = 0;
        switch(identify) {
            case 1 -> {              //To check char for Y/y or N/n only
                do {
                    System.out.print(PrintColor.CYAN_BRIGHT);
                    System.out.printf(prompt, "");
                    character = input.next().charAt(0);
                    System.out.print(PrintColor.RESET);
                    end = false;
                    if(Character.toUpperCase(character) != 'Y' && Character.toUpperCase(character) != 'N') {
                        System.out.print(PrintColor.RED_BOLD);
                        System.out.printf("\n%68s===========================================\n", "");
                        System.out.printf("%86s|||||||\n", "");
                        System.out.printf("%86s|||||||\n", "");
                        System.out.printf("%60s##~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~##\n", "");
                        System.out.printf("%60s||         Invalid Input !!! Please TRY again...         ||\n", "");
                        System.out.printf("%60s||        It Should Be (Y/y = Yes) OR (N/n = No).        ||\n", "");
                        System.out.printf("%60s##~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~##\n", "");
                        System.out.print(PrintColor.RESET);
                        end = true;
                    }
                }while(end);
            }
            case 2 -> {              //To check char for M/m = male or F/f = female (customer)
                do {
                    System.out.print(PrintColor.CYAN_BRIGHT);
                    System.out.printf(prompt, "");
                    character = input.next().charAt(0);
                    System.out.print(PrintColor.RESET);
                    end = false;
                    if(Character.toUpperCase(character) != 'M' && Character.toUpperCase(character) != 'F') {
                        System.out.print(PrintColor.RED_BOLD);
                        System.out.printf("\n%68s===========================================\n", "");
                        System.out.printf("%86s|||||||\n", "");
                        System.out.printf("%86s|||||||\n", "");
                        System.out.printf("%60s##~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~##\n", "");
                        System.out.printf("%60s||         Invalid Input !!! Please TRY again...         ||\n", "");
                        System.out.printf("%60s||      It Should Be (M/m = Male) OR (F/f = Female)      ||\n", "");
                        System.out.printf("%60s##~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~##\n", "");
                        System.out.print(PrintColor.RESET);
                        end = true;
                    }
                }while(end);
            }
            case 3 -> {              //To check for input that is a character or not
                do {
                    System.out.print(PrintColor.CYAN_BRIGHT);
                    System.out.printf(prompt, "");
                    character = input.next().charAt(0);
                    System.out.print(PrintColor.RESET);
                    end = false;
                    if(!Character.isLetter(character)) {
                        System.out.print(PrintColor.RED_BOLD);
                        System.out.printf("\n%68s===========================================\n", "");
                        System.out.printf("%86s|||||||\n", "");
                        System.out.printf("%86s|||||||\n", "");
                        System.out.printf("%60s##~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~##\n", "");
                        System.out.printf("%60s||         Invalid Input !!! Please TRY again...         ||\n", "");
                        System.out.printf("%60s||      It Should Be (M/m = Male) OR (F/f = Female)      ||\n", "");
                        System.out.printf("%60s##~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~##\n", "");
                        System.out.print(PrintColor.RESET);
                        end = true;
                    }
                }while(end);
            }
        }
        return character;
    }

    public static String strValidation(String prompt, int identify) {
        Scanner input = new Scanner(System.in);
        boolean end;
        String str = "";
        switch(identify) {
            case 1 -> {       //To check only alphabet character
                do {
                    System.out.print(PrintColor.CYAN_BRIGHT);
                    System.out.printf(prompt, "");
                    str = input.nextLine();
                    System.out.print(PrintColor.RESET);
                    end = false;
                    if (!str.matches("^[a-zA-Z ]+$")) {
                        System.out.print(PrintColor.RED_BOLD);
                        System.out.printf("\n%68s===========================================\n", "");
                        System.out.printf("%86s|||||||\n", "");
                        System.out.printf("%86s|||||||\n", "");
                        System.out.printf("%60s##~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~##\n", "");
                        System.out.printf("%60s||         Invalid Input !!! Please TRY again...         ||\n", "");
                        System.out.printf("%60s||      It Should Contains Only Alphabet Characters      ||\n", "");
                        System.out.printf("%60s##~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~##\n", "");
                        System.out.print(PrintColor.RESET);
                        end = true;
                    }
                }while(end);
            }
            case 2 -> {       //To check at least one alphanumeric (letters + digits) character  - for customer password with a length of 8 to 15
                do {
                    System.out.print(PrintColor.CYAN_BRIGHT);
                    System.out.printf(prompt, "");
                    str = input.nextLine();
                    System.out.print(PrintColor.RESET);
                    end = false;
                   if (!str.matches("^(?=.*\\d)(?=.*[a-zA-Z]).\\S{8,15}$")) {
                       System.out.print(PrintColor.RED_BOLD);
                       System.out.printf("\n%68s===========================================\n", "");
                       System.out.printf("%86s|||||||\n", "");
                       System.out.printf("%86s|||||||\n", "");
                       System.out.printf("%60s##~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~##\n", "");
                       System.out.printf("%60s||         Invalid Input !!! Please TRY again...         ||\n", "");
                       System.out.printf("%60s||   It Should Contains Alphabet & Numeric Characters    ||\n", "");
                       System.out.printf("%60s##~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~##\n", "");
                       System.out.print(PrintColor.RESET);
                       end = true;
                    }
                }while(end);
            }
            case 3 -> {       //To check only digits character
                do {
                    System.out.print(PrintColor.CYAN_BRIGHT);
                    System.out.printf(prompt, "");
                    str = input.nextLine();
                    System.out.print(PrintColor.RESET);
                    end = false;
                    if (!str.matches("^[0-9]+\\S$")) {
                        System.out.print(PrintColor.RED_BOLD);
                        System.out.printf("\n%68s===========================================\n", "");
                        System.out.printf("%86s|||||||\n", "");
                        System.out.printf("%86s|||||||\n", "");
                        System.out.printf("%60s##~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~##\n", "");
                        System.out.printf("%60s||         Invalid Input !!! Please TRY again...         ||\n", "");
                        System.out.printf("%60s||            It Should Be Numeric Form Only.            ||\n", "");
                        System.out.printf("%60s##~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~##\n", "");
                        System.out.print(PrintColor.RESET);
                        end = true;
                    }
                }while(end);
            }
            case 4 -> {       //To check only digits character + '-' (for customer contact no only)
                do {
                    System.out.print(PrintColor.CYAN_BRIGHT);
                    System.out.printf(prompt, "");
                    str = input.nextLine();
                    System.out.print(PrintColor.RESET);
                    end = false;
                    if (!str.matches("^\\d{3}-\\d{7,8}$")) {
                        System.out.print(PrintColor.RED_BOLD);
                        System.out.printf("\n%68s===========================================\n", "");
                        System.out.printf("%86s|||||||\n", "");
                        System.out.printf("%86s|||||||\n", "");
                        System.out.printf("%60s##~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~##\n", "");
                        System.out.printf("%60s||         Invalid Input !!! Please TRY again...         ||\n", "");
                        System.out.printf("%60s||       It Should Be Numeric Form With A '-' Only.      ||\n", "");
                        System.out.printf("%60s||                    eg. 012-8087788                    ||\n", "");
                        System.out.printf("%60s##~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~##\n", "");
                        System.out.print(PrintColor.RESET);
                        end = true;
                    }
                }while(end);
            }
            case 5 -> {       //To check for email validity (for customer)
                do {
                    System.out.print(PrintColor.CYAN_BRIGHT);
                    System.out.printf(prompt, "");
                    str = input.nextLine();
                    System.out.print(PrintColor.RESET);
                    end = false;
                    if (!str.matches("^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+com$")) {
                        System.out.print(PrintColor.RED_BOLD);
                        System.out.printf("\n%68s===========================================\n", "");
                        System.out.printf("%86s|||||||\n", "");
                        System.out.printf("%86s|||||||\n", "");
                        System.out.printf("%60s##~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~##\n", "");
                        System.out.printf("%60s||         Invalid Input !!! Please TRY again...         ||\n", "");
                        System.out.printf("%60s||      It Can Be Any Character With '@' and '.com'      ||\n", "");
                        System.out.printf("%60s||                 eg shawn#_123@gmail.com               ||\n", "");
                        System.out.printf("%60s##~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~##\n", "");
                        System.out.print(PrintColor.RESET);
                        end = true;
                    }
                }while(end);
            }
            case 6 -> {       //For alphanumeric string (for customer userID, foodID) with a length of 4, 12 but at least one letter
                do {
                    System.out.print(PrintColor.CYAN_BRIGHT);
                    System.out.printf(prompt, "");
                    str = input.nextLine();
                    System.out.print(PrintColor.RESET);
                    end = false;
                    if (!str.matches("^(?=.*[a-zA-Z].*)([a-zA-Z0-9]\\S{4,12})$")) {
                        System.out.print(PrintColor.RED_BOLD);
                        System.out.printf("\n%68s===========================================\n", "");
                        System.out.printf("%86s|||||||\n", "");
                        System.out.printf("%86s|||||||\n", "");
                        System.out.printf("%60s##~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~##\n", "");
                        System.out.printf("%60s||         Invalid Input !!! Please TRY again...         ||\n", "");
                        System.out.printf("%60s||   It May Contains Both Alphabet & Digit Characters    ||\n", "");
                        System.out.printf("%60s##~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~##\n", "");
                        System.out.print(PrintColor.RESET);
                        end = true;
                    }
                }while(end);
            }
        }
        return str;
    }

    public static Date dateTimeValidation(String prompt, int identify) throws ParseException {
        Scanner input = new Scanner(System.in);
        boolean end;
        String str;
        Date d = new Date();
        switch(identify) {
            case 1 -> {      //To check for normal date format eg 31-04-2021 is not allowed
                do {
                    System.out.print(PrintColor.CYAN_BRIGHT);
                    System.out.printf(prompt, "");
                    str = input.nextLine();
                    System.out.print(PrintColor.RESET);
                    end = false;
                    if (!str.matches( "^(02-29-(2000|2400|2800|(19|2[0-9](0[48]|[2468][048]|[13579][26]))))$"
                            + "|^((0[1-9]|1[0-9]|2[0-8])-02-((19|2[0-9])[0-9]{2}))$"
                            + "|^((0[1-9]|[12][0-9]|3[01])-(0[13578]|10|12)-((19|2[0-9])[0-9]{2}))$"
                            + "|^((0[1-9]|[12][0-9]|30)-(0[469]|11)-((19|2[0-9])[0-9]{2}))$")) {
                        System.out.print(PrintColor.RED_BOLD);
                        System.out.printf("\n%68s===========================================\n", "");
                        System.out.printf("%86s|||||||\n", "");
                        System.out.printf("%86s|||||||\n", "");
                        System.out.printf("%60s##~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~##\n", "");
                        System.out.printf("%60s||         Invalid Input !!! Please TRY again...         ||\n", "");
                        System.out.printf("%60s||               It Should Be 'dd-MM-yyyy'               ||\n", "");
                        System.out.printf("%60s||             --> eg 31-05-2021, 30-06-2021             ||\n", "");
                        System.out.printf("%60s||           Some Months Contains 30 & 31 Days           ||\n", "");
                        System.out.printf("%60s||   Jan, Mar, May, Jul, Aug, Oct, Dec --> MAX 31 Days   ||\n", "");
                        System.out.printf("%60s||           Apr, Jun, Sep, Nov --> MAX 30 Days          ||\n", "");
                        System.out.printf("%60s||                           OR                          ||\n", "");
                        System.out.printf("%60s||          February Only Contains 28 OR 29 days         ||\n", "");
                        System.out.printf("%60s||      It Should Be 'dd-MM-yyyy' --> eg 28-02-2021      ||\n", "");
                        System.out.printf("%60s||           If It Is A Leap Year --> eg 29-02-2024      ||\n", "");
                        System.out.printf("%60s##~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~##\n", "");
                        System.out.print(PrintColor.RESET);
                        end = true;
                    } else {
                        d = new SimpleDateFormat("dd-MM-yyyy").parse(str);
                    }
                }while(end);
            }
            case 2 -> {      //To check for time format
                do {
                    System.out.print(PrintColor.CYAN_BRIGHT);
                    System.out.printf(prompt, "");
                    str = input.nextLine();
                    System.out.print(PrintColor.RESET);
                    end = false;
                    //if (!str.matches("^\\d{2}:\\d{2}")) {
                    if (!str.matches("([01]?[0-9]|2[0-3]):[0-5][0-9]") || new SimpleDateFormat("HH:mm").parse(str).before(new SimpleDateFormat("HH:mm").parse("09:00"))
                            || new SimpleDateFormat("HH:mm").parse(str).after(new SimpleDateFormat("HH:mm").parse("23:00"))) {
                        System.out.print(PrintColor.RED_BOLD);
                        System.out.printf("\n%68s===========================================\n", "");
                        System.out.printf("%86s|||||||\n", "");
                        System.out.printf("%86s|||||||\n", "");
                        System.out.printf("%60s##~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~##\n", "");
                        System.out.printf("%60s||         Invalid Input !!! Please TRY again...         ||\n", "");
                        System.out.printf("%60s||           It Should Be 'HH:mm' --> eg 13:14           ||\n", "");
                        System.out.printf("%60s||             Business Time : 09:00 - 23:00             ||\n", "");
                        System.out.printf("%60s||    [  Hour Within 9 - 23 , Minute Within 0 - 60  ]    ||\n", "");
                        System.out.printf("%60s##~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~##\n", "");
                        System.out.print(PrintColor.RESET);
                        end = true;
                    } else {
                        d = new SimpleDateFormat("HH:mm").parse(str);
                    }
                }while(end);
            }
            case 3 -> {      //To check for date format ("MM/yy") for credit card
                do {
                    System.out.print(PrintColor.CYAN_BRIGHT);
                    System.out.printf(prompt, "");
                    str = input.nextLine();
                    System.out.print(PrintColor.RESET);
                    end = false;
                    if (!str.matches("|^(0?[1-9]|1[012])/([0-9][0-9])")) {
                        System.out.print(PrintColor.RED_BOLD);
                        System.out.printf("\n%68s===========================================\n", "");
                        System.out.printf("%86s|||||||\n", "");
                        System.out.printf("%86s|||||||\n", "");
                        System.out.printf("%60s##~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~##\n", "");
                        System.out.printf("%60s||         Invalid Input !!! Please TRY again...         ||\n", "");
                        System.out.printf("%60s||                  It Should Be 'MM/yy'                 ||\n", "");
                        System.out.printf("%60s||                  --> eg 05/26, 08/27                  ||\n", "");
                        System.out.printf("%60s||                           OR                          ||\n", "");
                        System.out.printf("%60s||    The Card's Expiry Date Must Exceed Current Date    ||\n", "");
                        System.out.printf("%60s##~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~##\n", "");
                        System.out.print(PrintColor.RESET);
                        end = true;
                    } else {
                        d = new SimpleDateFormat("MM/yy").parse(str);
                    }
                }while(end);
            }
        }
        return d;
    }

    public static void logoDisplay(int identify) {
        System.out.print(PrintColor.BLUE_BOLD);
        switch(identify) {
            case 1 -> {
                System.out.printf("%57s     _     _   _____   _       _____   _____  ___  ___  _____          \n", "");
                System.out.printf("%57s    | |   | | |  ___| | |     /  __ \\ |  _  | |  \\/  | |  ___|       \n", "");
                System.out.printf("%57s    | |   | | | |__   | |     | /  \\/ | | | | | .  . | | |__          \n", "");
                System.out.printf("%57s    | |/`\\| | |  __|  | |     | |     | | | | | |\\/| | |  __|        \n", "");
                System.out.printf("%57s    \\  /`\\  / | |___  | |____ | \\__/\\ \\ \\_/ / | |  | | | |___    \n", "");
                System.out.printf("%57s     \\/   \\/  \\____/  \\_____/  \\____/  \\___/  \\_|  |_/ \\____/  \n", "");
            }
            case 2 -> {
                System.out.printf("%26s    ___  ___                     _           ______  _                   ______  _                                                     \n", "");
                System.out.printf("%26s    |  \\/  |                    (_)          | ___ \\| |                  |  _  \\(_)                                                 \n", "");
                System.out.printf("%26s    | .  . | _   _  _ __  _ __   _   ______  | |_/ /| |  ___  ___  ___   | | | | _  ___   ___  ___ __   __ ___  _ __  _   _            \n", "");
                System.out.printf("%26s    | |\\/| || | | || '__|| '_ \\ | | |______| | ___ \\| | / _ \\/ __|/ __|  | | | || |/ __| / __|/ _ \\\\ \\ / // _ \\| '__|| | | |   \n", "");
                System.out.printf("%26s    | |  | || |_| || |   | | | || |          | |_/ /| ||  __/\\__ \\\\__ \\  | |/ / | |\\__ \\| (__| (_) |\\ V /|  __/| |   | |_| |    \n", "");
                System.out.printf("%26s    \\_|  |_/ \\__,_||_|   |_| |_||_|          \\____/ |_| \\___||___/|___/  |___/  |_||___/ \\___|\\___/  \\_/  \\___||_|    \\__, |  \n", "");
                System.out.printf("%26s                                                                                                                       __/ |           \n", "");
                System.out.printf("%26s                                                                                                                      |___/            \n", "");
            }
            case 3 -> {
                System.out.printf("\n\n%73s   _     ___    ___  ___  _  _       \n", "");
                System.out.printf("%73s  | |   / _ \\  / __||_ _|| \\| |    \n", "");
                System.out.printf("%73s  | |__| (_) || (_ | | | | .` |      \n", "");
                System.out.printf("%73s  |____|\\___/  \\___||___||_|\\_|   \n", "");
            }
            case 4 -> {
                System.out.printf("\n\n%41s      __  __  _   _  ___  _  _  ___    ___  ___  ___  ___  ___ __   __ _  _____  ___  ___   _  _          \n", "");
                System.out.printf("%41s     |  \\/  || | | || _ \\| \\| ||_ _|  | _ \\| __|/ __|| __|| _ \\\\ \\ / //_\\|_   _||_ _|/ _ \\ | \\| |   \n", "");
                System.out.printf("%41s     | |\\/| || |_| ||   /| .` | | |   |   /| _| \\__ \\| _| |   / \\ V // _ \\ | |   | || (_) || .` |        \n", "");
                System.out.printf("%41s     |_|  |_| \\___/ |_|_\\|_|\\_||___|  |_|_\\|___||___/|___||_|_\\  \\_//_/ \\_\\|_|  |___|\\___/ |_|\\_|   \n", "");
            }
            case 5 -> {
                 System.out.printf("\n\n%46s      __  __ __   __    ___  ___  ___  ___  ___ __   __ _  _____  ___  ___   _  _          \n", "");
                 System.out.printf("%46s     |  \\/  |\\ \\ / /   | _ \\| __|/ __|| __|| _ \\\\ \\ / //_\\|_   _||_ _|/ _ \\ | \\| |   \n", "");
                 System.out.printf("%46s     | |\\/| | \\ V /    |   /| _| \\__ \\| _| |   / \\ V // _ \\ | |   | || (_) || .` |       \n", "");
                 System.out.printf("%46s     |_|  |_|  |_|     |_|_\\|___||___/|___||_|_\\  \\_//_/ \\_\\|_|  |___|\\___/ |_|\\_|      \n", "");
            }
            case 6 -> {
                 System.out.printf("\n\n%61s      __  __ __   __     ___   ___  ___   ___  ___       \n", "");
                 System.out.printf("%61s     |  \\/  |\\ \\ / /    / _ \\ | _ \\|   \\ | __|| _ \\   \n", "");
                 System.out.printf("%61s     | |\\/| | \\ V /    | (_) ||   /| |) || _| |   /        \n", "");
                 System.out.printf("%61s     |_|  |_|  |_|      \\___/ |_|_\\|___/ |___||_|_\\       \n", "");
            }
            case 7 -> {
                 System.out.printf("\n\n%55s      _____       _     _         ___        _          _  _         \n", "");
                 System.out.printf("%55s     |_   _|__ _ | |__ | | ___   |   \\  ___ | |_  __ _ (_)| | ___       \n", "");
                 System.out.printf("%55s       | | / _` || '_ \\| |/ -_)  | |) |/ -_)|  _|/ _` || || |(_-<       \n", "");
                 System.out.printf("%55s       |_| \\__,_||_.__/|_|\\___|  |___/ \\___| \\__|\\__,_||_||_|/__/   \n", "");
            }
            case 8 -> {
                System.out.printf("\n\n%57s      ___              _     ___        _          _  _         \n", "");
                System.out.printf("%57s     | __|___  ___  __| |   |   \\  ___ | |_  __ _ (_)| | ___       \n", "");
                System.out.printf("%57s     | _|/ _ \\/ _ \\/ _` |   | |) |/ -_)|  _|/ _` || || |(_-<      \n", "");
                System.out.printf("%57s     |_| \\___/\\___/\\__,_|   |___/ \\___| \\__|\\__,_||_||_|/__/  \n", "");
            }
            case 9 -> {
                System.out.printf("\n\n%54s      ___        _        _       ___        _          _  _         \n", "");
                System.out.printf("%54s     |   \\  _ _ (_) _ _  | |__   |   \\  ___ | |_  __ _ (_)| | ___      \n", "");
                System.out.printf("%54s     | |) || '_|| || ' \\ | / /   | |) |/ -_)|  _|/ _` || || |(_-<       \n", "");
                System.out.printf("%54s     |___/ |_|  |_||_||_||_\\_\\   |___/ \\___| \\__|\\__,_||_||_|/__/   \n", "");
            }
            case 10 -> {
                System.out.printf("\n\n%50s      ___                          _       ___        _          _  _          \n", "");
                System.out.printf("%50s     |   \\  ___  ___ ___ ___  _ _ | |_    |   \\  ___ | |_  __ _ (_)| | ___       \n", "");
                System.out.printf("%50s     | |) |/ -_)(_-<(_-</ -_)| '_||  _|   | |) |/ -_)|  _|/ _` || || |(_-<         \n", "");
                System.out.printf("%50s     |___/ \\___|/__//__/\\___||_|   \\__|   |___/ \\___| \\__|\\__,_||_||_|/__/   \n", "");
            }
            case 11 -> {
                System.out.printf("\n\n%58s   __  __ __   __    ___   _ __   __ __  __  ___  _  _  _____          \n", "");
                System.out.printf("%58s  |  \\/  |\\ \\ / /   | _ \\ /_\\\\ \\ / /|  \\/  || __|| \\| ||_   _|    \n", "");
                System.out.printf("%58s  | |\\/| | \\ V /    |  _// _ \\\\ V / | |\\/| || _| | .` |  | |          \n", "");
                System.out.printf("%58s  |_|  |_|  |_|     |_| /_/ \\_\\|_|  |_|  |_||___||_|\\_|  |_|            \n", "");
            }
            case 12 -> {
                System.out.printf("\n\n%47s    ___   _ __   __ __  __  ___  _  _  _____     __  __  ___  _____  _  _   ___   ___         \n", "");
                System.out.printf("%47s   | _ \\ /_\\\\ \\ / /|  \\/  || __|| \\| ||_   _|   |  \\/  || __||_   _|| || | / _ \\ |   \\   \n", "");
                System.out.printf("%47s   |  _// _ \\\\ V / | |\\/| || _| | .` |  | |     | |\\/| || _|   | |  | __ || (_) || |) |       \n", "");
                System.out.printf("%47s   |_| /_/ \\_\\|_|  |_|  |_||___||_|\\_|  |_|     |_|  |_||___|  |_|  |_||_| \\___/ |___/        \n", "");
            }
            case 13 -> {
                System.out.printf("\n\n%58s    ___  ___  ___  ___  ___  _____      ___    _    ___  ___      \n", "");
                System.out.printf("%58s   / __|| _ \\| __||   \\|_ _||_   _|    / __|  /_\\  | _ \\|   \\    \n", "");
                System.out.printf("%58s  | (__ |   /| _| | |) || |   | |     | (__  / _ \\ |   /| |) |       \n", "");
                System.out.printf("%58s   \\___||_|_\\|___||___/|___|  |_|      \\___|/_/ \\_\\|_|_\\|___/   \n", "");
            }
            case 14 -> {
                System.out.printf("\n\n%55s   ___         _  _                ___              _  _                \n", "");
                System.out.printf("%55s  / _ \\  _ _  | |(_) _ _   ___    | _ ) __ _  _ _  | |(_) _ _   __ _       \n", "");
                System.out.printf("%55s | (_) || ' \\ | || || ' \\ / -_)   | _ \\/ _` || ' \\ | || || ' \\ / _` |  \n", "");
                System.out.printf("%55s  \\___/ |_||_||_||_||_||_|\\___|   |___/\\__,_||_||_||_||_||_||_|\\__, |   \n", "");
                System.out.printf("%55s                                                                |___/       \n", "");
            }
            case 15 -> {
                System.out.printf("%29s|%26s      __  __                  _           ____  _                       %22s|\n", "", "", "");
                System.out.printf("%29s|%26s     |  \\/  |_   _ _ __ _ __ (_)         | __ )| | ___  ___ ___        %23s|\n", "", "", "");
                System.out.printf("%29s|%26s     | |\\/| | | | | '__| '_ \\| |  _____  |  _ \\| |/ _ \\/ __/ __|    %26s|\n", "", "", "");
                System.out.printf("%29s|%26s     | |  | | |_| | |  | | | | | |_____| | |_) | |  __/\\__ \\__ \\      %24s|\n", "", "", "");
                System.out.printf("%29s|%26s     |_|  |_|\\__,_|_|  |_| |_|_|         |____/|_|\\___||___/___/      %24s|\n", "", "", "");
                System.out.printf("%29s|%26s             ____  _                                                    %22s|\n", "", "", "");
                System.out.printf("%29s|%26s            |  _ \\(_)___  ___ _____   _____ _ __ _   _                 %23s|\n", "", "", "");
                System.out.printf("%29s|%26s            | | | | / __|/ __/ _ \\ \\ / / _ \\ '__| | | |              %25s|\n", "", "", "");
                System.out.printf("%29s|%26s            | |_| | \\__ \\ (_| (_) \\ V /  __/ |  | |_| |              %25s|\n", "", "", "");
                System.out.printf("%29s|%26s            |____/|_|___/\\___\\___/ \\_/ \\___|_|   \\__, |            %27s|\n", "", "", "");
                System.out.printf("%29s|%26s                                                 |___/                  %22s|\n", "", "", "");
            }
            case 16 -> {
                System.out.printf("%29s|%29s     ____    _____    ____   _____   ___   ____    _____          %25s|\n", "","", "");
                System.out.printf("%29s|%29s    |  _ \\  | ____|  / ___| | ____| |_ _| |  _ \\  |_   _|       %27s|\n", "","", "");
                System.out.printf("%29s|%29s    | |_) | |  _|   | |     |  _|    | |  | |_) |   | |           %25s|\n", "","", "");
                System.out.printf("%29s|%29s    |  _ <  | |___  | |___  | |___   | |  |  __/    | |           %25s|\n", "","", "");
                System.out.printf("%29s|%29s    |_| \\_\\ |_____|  \\____| |_____| |___| |_|       |_|        %28s|\n", "","", "");
            }
            case 17 -> {
                System.out.printf("\n\n\n%8s   .----------------.  .----------------.  .----------------.  .-----------------. .----------------.  .----------------.  .----------------.  .----------------.           \n", "");
                System.out.printf("%8s  | .--------------. || .--------------. || .--------------. || .--------------. || .--------------. || .--------------. || .--------------. || .--------------. |          \n", "");
                System.out.printf("%8s  | |  _________   | || |  ____  ____  | || |      __      | || | ____  _____  | || |  ___  ____   | || |  ____  ____  | || |     ____     | || | _____  _____ | |          \n", "");
                System.out.printf("%8s  | | |  _   _  |  | || | |_   ||   _| | || |     /  \\     | || ||_   \\|_   _| | || | |_  ||_  _|  | || | |_  _||_  _| | || |   .'    `.   | || ||_   _||_   _|| |        \n", "");
                System.out.printf("%8s  | | |_/ | | \\_|  | || |   | |__| |   | || |    / /\\ \\    | || |  |   \\ | |   | || |   | |_/ /    | || |   \\ \\  / /   | || |  /  .--.  \\  | || |  | |    | |  | |   \n", "");
                System.out.printf("%8s  | |     | |      | || |   |  __  |   | || |   / ____ \\   | || |  | |\\ \\| |   | || |   |  __'.    | || |    \\ \\/ /    | || |  | |    | |  | || |  | '    ' |  | |     \n", "");
                System.out.printf("%8s  | |    _| |_     | || |  _| |  | |_  | || | _/ /    \\ \\_ | || | _| |_\\   |_  | || |  _| |  \\ \\_  | || |    _|  |_    | || |  \\  `--'  /  | || |   \\ `--' /   | |   \n", "");
                System.out.printf("%8s  | |   |_____|    | || | |____||____| | || ||____|  |____|| || ||_____|\\____| | || | |____||____| | || |   |______|   | || |   `.____.'   | || |    `.__.'    | |         \n", "");
                System.out.printf("%8s  | |              | || |              | || |              | || |              | || |              | || |              | || |              | || |              | |          \n", "");
                System.out.printf("%8s  | '--------------' || '--------------' || '--------------' || '--------------' || '--------------' || '--------------' || '--------------' || '--------------' |          \n", "");
                System.out.printf("%8s   '----------------'  '----------------'  '----------------'  '----------------'  '----------------'  '----------------'  '----------------'  '----------------'           \n", "");
            }
            case 18 -> {
                System.out.printf("\n\n%67s   ___  _                     _   _            \n", "");
                System.out.printf("%67s  / __|(_) __ _  _ _    ___  | | | | _ __          \n", "");
                System.out.printf("%67s  \\__ \\| |/ _` || ' \\  |___| | |_| || '_ \\     \n", "");
                System.out.printf("%67s  |___/|_|\\__, ||_||_|        \\___/ | .__/       \n", "");
                System.out.printf("%67s          |___/                     |_|            \n", "");
            }
            case 19 -> {
                System.out.printf("\n\n%59s    ___     _  _  _        ___        _          _  _            \n", "");
                System.out.printf("%59s   | __| __| |(_)| |_     |   \\  ___ | |_  __ _ (_)| | ___          \n", "");
                System.out.printf("%59s   | _| / _` || ||  _|    | |) |/ -_)|  _|/ _` || || |(_-<           \n", "");
                System.out.printf("%59s   |___|\\__,_||_| \\__|    |___/ \\___| \\__|\\__,_||_||_|/__/      \n", "");
            }
            case 20 -> {
                System.out.printf("\n\n%56s    ___             __  _  _          ___        _          _  _          \n", "");
                System.out.printf("%56s   | _ \\ _ _  ___  / _|(_)| | ___    |   \\  ___ | |_  __ _ (_)| | ___       \n", "");
                System.out.printf("%56s   |  _/| '_|/ _ \\|  _|| || |/ -_)   | |) |/ -_)|  _|/ _` || || |(_-<        \n", "");
                System.out.printf("%56s   |_|  |_|  \\___/|_|  |_||_|\\___|   |___/ \\___| \\__|\\__,_||_||_|/__/    \n", "");
            }
            case 21 -> {
                System.out.printf("\n\n%55s    ___                                  ___           _                \n", "");
                System.out.printf("%55s   | _ \\ ___  _ __   ___ __ __ ___      / _ \\  _ _  __| | ___  _ _        \n", "");
                System.out.printf("%55s   |   // -_)| '  \\ / _ \\\\ V // -_)    | (_) || '_|/ _` |/ -_)| '_|      \n", "");
                System.out.printf("%55s   |_|_\\\\___||_|_|_|\\___/ \\_/ \\___|     \\___/ |_|  \\__,_|\\___||_|   \n", "");
            }
            case 22 -> {
                System.out.printf("\n\n%44s    ___                       _      ___                                _    _                    \n", "");
                System.out.printf("%44s   / __| __ _  _ _   __  ___ | |    | _ \\ ___  ___ ___  _ _ __ __ __ _ | |_ (_) ___  _ _             \n", "");
                System.out.printf("%44s  | (__ / _` || ' \\ / _|/ -_)| |    |   // -_)(_-</ -_)| '_|\\ V // _` ||  _|| |/ _ \\| ' \\         \n", "");
                System.out.printf("%44s   \\___|\\__,_||_||_|\\__|\\___||_|    |_|_\\\\___|/__/\\___||_|   \\_/ \\__,_| \\__||_|\\___/|_||_| \n", "");
            }
            case 23 -> {
                System.out.printf("\n\n%68s   _                     ___         _        \n", "");
                System.out.printf("%68s  | |    ___  __ _      / _ \\  _  _ | |_         \n", "");
                System.out.printf("%68s  | |__ / _ \\/ _` |    | (_) || || ||  _|        \n", "");
                System.out.printf("%68s  |____|\\___/\\__, |     \\___/  \\_,_| \\__|    \n", "");
                System.out.printf("%68s             |___/                                \n", "");
            }
            case 24 -> {
                System.out.printf("\n\n%65s   ___                _                _           \n", "");
                System.out.printf("%65s  | _ \\ ___    ___   | |    ___  __ _ (_) _ _         \n", "");
                System.out.printf("%65s  |   // -_)  |___|  | |__ / _ \\/ _` || || ' \\       \n", "");
                System.out.printf("%65s  |_|_\\\\___|         |____|\\___/\\__, ||_||_||_|    \n", "");
                System.out.printf("%65s                                |___/                  \n", "");
            }
            case 25 ->{
                System.out.printf("\n\n%8s ______    _______  _______  _______  ______    __   __  _______  _______  ___   _______  __    _     __   __  ___   _______  _______  _______  ______    __   __ \n"," ");
                System.out.printf("%8s|    _ |  |       ||       ||       ||    _ |  |  | |  ||   _   ||       ||   | |       ||  |  | |    |  | |  ||   | |       ||       ||       ||    _ |  |  | |  |\n"," ");
                System.out.printf("%8s|   | ||  |    ___||  _____||    ___||   | ||  |  |_|  ||  |_|  ||_     _||   | |   _   ||   |_| |    |  |_|  ||   | |  _____||_     _||   _   ||   | ||  |  |_|  |\n"," ");
                System.out.printf("%8s|   |_||_ |   |___ | |_____ |   |___ |   |_||_ |       ||       |  |   |  |   | |  | |  ||       |    |       ||   | | |_____   |   |  |  | |  ||   |_||_ |       |\n"," ");
                System.out.printf("%8s|    __  ||    ___||_____  ||    ___||    __  ||       ||       |  |   |  |   | |  |_|  ||  _    |    |       ||   | |_____  |  |   |  |  |_|  ||    __  ||_     _|\n"," ");
                System.out.printf("%8s|   |  | ||   |___  _____| ||   |___ |   |  | | |     | |   _   |  |   |  |   | |       || | |   |    |   _   ||   |  _____| |  |   |  |       ||   |  | |  |   |  \n"," ");
                System.out.printf("%8s|___|  |_||_______||_______||_______||___|  |_|  |___|  |__| |__|  |___|  |___| |_______||_|  |__|    |__| |__||___| |_______|  |___|  |_______||___|  |_|  |___|  \n"," ");
            }
            case 26 ->{
                System.out.printf("\n\n%35s _______  ______    ______   _______  ______        __   __  ___   _______  _______  _______  ______    __   __ \n"," ");
                System.out.printf("%35s|       ||    _ |  |      | |       ||    _ |      |  | |  ||   | |       ||       ||       ||    _ |  |  | |  |\n"," ");
                System.out.printf("%35s|   _   ||   | ||  |  _    ||    ___||   | ||      |  |_|  ||   | |  _____||_     _||   _   ||   | ||  |  |_|  |\n"," ");
                System.out.printf("%35s|  | |  ||   |_||_ | | |   ||   |___ |   |_||_     |       ||   | | |_____   |   |  |  | |  ||   |_||_ |       |\n"," ");
                System.out.printf("%35s|  |_|  ||    __  || |_|   ||    ___||    __  |    |       ||   | |_____  |  |   |  |  |_|  ||    __  ||_     _|\n"," ");
                System.out.printf("%35s|       ||   |  | ||       ||   |___ |   |  | |    |   _   ||   |  _____| |  |   |  |       ||   |  | |  |   |  \n"," ");
                System.out.printf("%35s|_______||___|  |_||______| |_______||___|  |_|    |__| |__||___| |_______|  |___|  |_______||___|  |_|  |___|  \n"," ");
            }
            case 27 ->{
                System.out.printf("\n\n%34s_______  _______  _______  ___      _______      __   __  ___   _______  _______  _______  ______    __   __ \n"," ");
                System.out.printf("%34s|       ||   _   ||  _    ||   |    |       |    |  | |  ||   | |       ||       ||       ||    _ |  |  | |  |\n"," ");
                System.out.printf("%34s|_     _||  |_|  || |_|   ||   |    |    ___|    |  |_|  ||   | |  _____||_     _||   _   ||   | ||  |  |_|  |\n"," ");
                System.out.printf("%34s  |   |  |       ||       ||   |    |   |___     |       ||   | | |_____   |   |  |  | |  ||   |_||_ |       |\n"," ");
                System.out.printf("%34s  |   |  |       ||  _   | |   |___ |    ___|    |       ||   | |_____  |  |   |  |  |_|  ||    __  ||_     _|\n"," ");
                System.out.printf("%34s  |   |  |   _   || |_|   ||       ||   |___     |   _   ||   |  _____| |  |   |  |       ||   |  | |  |   |  \n"," ");
                System.out.printf("%34s  |___|  |__| |__||_______||_______||_______|    |__| |__||___| |_______|  |___|  |_______||___|  |_|  |___|  \n"," ");
            }
            case 28 ->{
                System.out.printf("\n\n%25s_______  _______  __   __  __   __  _______  __    _  _______    __   __  ___   _______  _______  _______  ______    __   __ \n"," ");
                System.out.printf("%25s|       ||   _   ||  | |  ||  |_|  ||       ||  |  | ||       |  |  | |  ||   | |       ||       ||       ||    _ |  |  | |  |\n"," ");
                System.out.printf("%25s|    _  ||  |_|  ||  |_|  ||       ||    ___||   |_| ||_     _|  |  |_|  ||   | |  _____||_     _||   _   ||   | ||  |  |_|  |\n"," ");
                System.out.printf("%25s|   |_| ||       ||       ||       ||   |___ |       |  |   |    |       ||   | | |_____   |   |  |  | |  ||   |_||_ |       |\n"," ");
                System.out.printf("%25s|    ___||       ||_     _||       ||    ___||  _    |  |   |    |       ||   | |_____  |  |   |  |  |_|  ||    __  ||_     _|\n"," ");
                System.out.printf("%25s|   |    |   _   |  |   |  | ||_|| ||   |___ | | |   |  |   |    |   _   ||   |  _____| |  |   |  |       ||   |  | |  |   |  \n"," ");
                System.out.printf("%25s|___|    |__| |__|  |___|  |_|   |_||_______||_|  |__|  |___|    |__| |__||___| |_______|  |___|  |_______||___|  |_|  |___|  \n"," ");
            }
            case 29 ->{
                System.out.printf("\n\n%69s    _  _  _      _                      \n", "");
                System.out.printf("%69s   | || |(_) ___| |_  ___  _ _  _  _        \n", "");
                System.out.printf("%69s   | __ || |(_-<|  _|/ _ \\| '_|| || |      \n", "");
                System.out.printf("%69s   |_||_||_|/__/ \\__|\\___/|_|   \\_, |    \n", "");
                System.out.printf("%69s                                |__/        \n", "");
            }

        }
        System.out.print(PrintColor.RESET);
    }
}
