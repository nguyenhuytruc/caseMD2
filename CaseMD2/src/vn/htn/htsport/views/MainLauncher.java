package vn.htn.htsport.views;

import java.util.InputMismatchException;
import java.util.Scanner;

public class MainLauncher {
    public static void launch() {
        AdminView adminView = new AdminView();
        adminView.adminLogin();
        menuOption();
    }

    public static void exit() {
        System.exit(3);
    }

    public static void menuOption() {
        do {
            mainMenu();
            try {
                Scanner scanner = new Scanner(System.in);
                System.out.println("\n Chọn chức năng");
                System.out.print("--->> ");
                int number = scanner.nextInt();
                switch (number) {
//                    case 1:
//                        UserViewLauncher.launch();
//                        break;
                    case 1:
                        ProductViewLauncher.run();
                        break;
                    case 2:
                        OrderViewLauncher.run();
                        break;
                    case 3:
                        exit();
                    default:
                        System.out.println("Chọn chức năng không đúng, vui lòng nhập lại!");
                        menuOption();

                }
            } catch (InputMismatchException io) {
                System.out.println("Nhập sai,vui lòng nhập lại!");
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        } while (true);
    }

    public static void mainMenu() {
        System.out.println("\t ✬ ✬ ✬ ✬ ✬ ✬ ✬ MAIN MENU ✬ ✬ ✬ ✬ ✬ ✬ ✬");
        System.out.println("\t ✬                                       ✬");
//        System.out.println("\t ✬     1. Quản lí người dùng             ✬");
        System.out.println("\t ✬     1. Quản lý sản phẩm               ✬");
        System.out.println("\t ✬     2. Quản lý đơn đặt hàng           ✬");
        System.out.println("\t ✬     3. Thoát chương trình             ✬");
        System.out.println("\t ✬                                       ✬");
        System.out.println("\t ✬ ✬ ✬ ✬ ✬ ✬ ✬ ✬ ✬ ✬ ✬ ✬ ✬ ✬ ✬ ✬ ✬ ✬");
    }

    public static void orderMenu() {
        System.out.println("ꕤ ꕤ ꕤ ꕤ ꕤ ORDER MENU ꕤ ꕤ ꕤ ꕤ ꕤ ꕤ");
        System.out.println("ꕤ                                     ꕤ");
        System.out.println("ꕤ     1. Tạo order                    ꕤ");
        System.out.println("ꕤ     2. Xem danh sách order          ꕤ");
        System.out.println("ꕤ     3. Trở lại                      ꕤ");
        System.out.println("ꕤ                                     ꕤ");
        System.out.println("ꕤ ꕤ ꕤ ꕤ ꕤ ꕤ ꕤ ꕤ ꕤ ꕤ ꕤ ꕤ ꕤ ꕤ ꕤ ");
    }
}
