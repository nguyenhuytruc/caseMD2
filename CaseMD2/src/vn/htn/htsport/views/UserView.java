package vn.htn.htsport.views;

import vn.htn.htsport.model.Role;
import vn.htn.htsport.model.User;
import vn.htn.htsport.services.IUserService;
import vn.htn.htsport.services.UserService;
import vn.htn.htsport.utils.AppUtils;
import vn.htn.htsport.utils.ValidateUtils;

import java.util.List;
import java.util.Scanner;

public class UserView {
    private final IUserService userService;
    private final Scanner scanner = new Scanner(System.in);

    public UserView() {
        userService =  UserService.getInstance();
    }

    public void addUser() {
        do {
            try {
                Integer id = inputId(InputOption.ADD);
                String username = inputUsername();
                String password = inputPassword();
                String fullName = inputFullName(InputOption.ADD);
                String phone = inputPhone(InputOption.ADD);
                String address = inputAddress(InputOption.ADD);
                String email = inputEmail();
                User user = new User(id, username, password, fullName, phone, email, address, Role.USER);
                setRole(user);
                userService.add(user);
                System.out.println("Đã thêm thành công!");
            } catch (Exception e) {
                System.out.println("Nhập sai. vui lòng nhập lại!");
            }
        } while (isRetry(InputOption.ADD));
    }

    public void setRole(User user) {
        System.out.println("=  * = *** SET ROLE *** = * =");
        System.out.println("∥                           ∥");
        System.out.println("∥   1. USER                 ∥");
        System.out.println("∥   2. ADMIN                ∥");
        System.out.println("∥                           ∥");
        System.out.println("= * = * = * = * = * = * = * = ");
        System.out.println("Chọn Role: ");
        System.out.print(" ⭆ ");
        int option = scanner.nextInt();
        scanner.nextLine();
        switch (option) {
            case 1:
                user.setRole(Role.USER);
                break;
            case 2:
                user.setRole(Role.ADMIN);
                break;
            default:
                System.out.println("Nhập không đúng! Vui lòng nhập lại");
                setRole(user);
        }
    }

    public void showUsers(InputOption inputOption) {
        System.out.println("||  ******  *****  ☻☻☻☻ ☻☻☻☻ **** *****  DANH SÁCH NGƯỜI DÙNG  ***** **** ☻☻☻☻ ☻☻☻☻  *****  ******  ||");
        System.out.printf("%-15s %-25s %-15s %-35s %-20s %-10s \n", "ID", "Tên", "Số điện thoại", "Email", "Địa chỉ", "Người dùng");
        List<User> users = userService.getUsers();
        for (User user : users) {
            System.out.printf("%-15d %-25s %-15s %-35s %-20s %-10s\n", user.getId(), user.getName(), user.getPhone(), user.getEmail(), user.getAddress(), user.getRole());
        }
        System.out.println("|| ☻☻☻☻☻☻☻☻☻☻☻☻☻☻☻☻☻☻☻☻☻☻☻☻☻☻☻☻☻☻☻☻☻☻☻☻☻☻☻☻☻☻☻☻☻☻☻☻☻☻☻☻☻☻☻☻☻☻☻☻☻☻☻☻☻ ||");
        if(inputOption == InputOption.SHOW) AppUtils.isRetry(InputOption.SHOW);
    }

    public void updateUser() {
        boolean isRetry = false;
        do {
            try {
                showUsers(InputOption.UPDATE);
                int id = inputId(InputOption.UPDATE);
                System.out.println("┌ * * * * *  SỬA NGƯỜI DÙNG * * * * * ┐");
                System.out.println("|  1. Đổi tên                       |");
                System.out.println("|  2. Sửa số điện thoại             |");
                System.out.println("|  3. Sửa địa chỉ                   |");
                System.out.println("|  4. Quay lại                      |");
                System.out.println("└ * * * * * *   *****   * * * * * * ┘");

                int option = AppUtils.retryChoose(1,4);
                User newUser = new User();
                newUser.setId(id);
                switch (option) {
                    case 1:
                        String name = inputFullName(InputOption.UPDATE);
                        newUser.setName(name);
                        userService.update(newUser);
                        System.out.println("Bạn đã đổi tên thành công!");
                        break;
                    case 2:
                        String phone = inputPhone(InputOption.UPDATE);
                        newUser.setPhone(phone);
                        userService.update(newUser);
                        System.out.println("Bạn đã đổi số điện thoại thành công!");
                        break;
                    case 3:
                        String address = inputAddress(InputOption.UPDATE);
                        newUser.setAddress(address);
                        userService.update(newUser);
                        System.out.println("Bạn đã đổi thành công!");
                        break;
                }
                isRetry = option != 4 && isRetry(InputOption.UPDATE);

            } catch (Exception e) {
                System.out.println("Nhập sai! vui lòng nhập lại");
            }
        } while (isRetry);
    }

    private boolean isRetry(InputOption inputOption) {
        do {
            try {
                switch (inputOption) {
                    case ADD:
                        System.out.println("Nhấn 'y' để thêm tiếp người dùng \t|\t 'q' để quay lại \t|\t 't' để thoát");
                        break;
                    case UPDATE:
                        System.out.println("Nhấn 'y' để sửa tiếp \t|\t 'q' để quay lại\t|\t 't' để thoát chương trình");
                        break;
                    case SHOW:
                        System.out.println("Nhấn 'q' để trở lại \t|\t 't' để thoát chương trình");
                        break;
                    default:
                        throw new IllegalStateException("Unexpected value: " + inputOption);
                }

                System.out.print("➾ ");
                String option = scanner.nextLine();
                switch (option) {
                    case "y":
                        return true;
                    case "q":
                        return false;
                    case "t":
                        AppUtils.exit();
                        break;
                    default:
                        System.out.println("Chọn chức năng không đúng! Vui lòng chọn lại");
                        break;
                }

            } catch (Exception ex) {
                System.out.println("Nhập sai! vui lòng nhập lại");
                ex.printStackTrace();
            }
        } while (true);
    }

    private Integer inputId(InputOption option) {
        Integer id = null;
        switch (option) {
            case ADD:
                System.out.println("Nhập ID");
                System.out.print("➾ ");
                while (userService.existById(id = Integer.parseInt(scanner.nextLine()))) {
                    System.out.println("ID này đã tồn tại. Vui lòng nhập id khác!");
                    System.out.print("➾ ");
                }
                break;
            case UPDATE:
                System.out.println("Nhập ID bạn muốn sửa");
                System.out.print("➾ ");
                while (!userService.existById(id = Integer.parseInt(scanner.nextLine()))) {
                    System.out.println("Không tìm thấy id! Vui lòng nhập lại");
                    System.out.print("➾ ");
                }
                break;
        }
        return id;
    }

    public String inputUsername() {
        System.out.println("Nhập Username");
        System.out.print("➾ ");
        String username;
        while (userService.existByUserName(username = scanner.nextLine())) {
            System.out.println("Username này đã tồn tại. Vui lòng nhập lại");
            System.out.print("➾ ");
        }

        return username;
    }

    private String inputFullName(InputOption option) {
        switch (option) {
            case ADD:
                System.out.println("Nhập họ và tên (vd: Huy Truc) ");
                break;
            case UPDATE:
                System.out.println("Nhập tên mà bạn muốn sửa đổi");
                break;
        }

        System.out.print("➾ ");
        String fullName;
        while (!ValidateUtils.isNameValid(fullName = scanner.nextLine())) {
            System.out.println("Tên " + fullName + "không đúng định dạng." + " Vui lòng nhập lại!" + " (Tên phải viết hoa chữ cái đầu và không dấu)");
            System.out.println("Nhập tên (vd: Huy Truc) ");
            System.out.print("➾ ");
        }
        return fullName;
    }

    public String inputPhone(InputOption option) {
        switch (option) {
            case ADD:
                System.out.println("Nhập số điện thoại (vd: 0901517061): ");
                break;
            case UPDATE:
                System.out.println("Nhập số điện thoại mà bạn muốn đổi");
                break;
        }
        System.out.print("➾ ");
        String phone;
        while (!ValidateUtils.isPhoneValid(phone = scanner.nextLine())) {
            System.out.println("Số " + phone + " của bạn không đúng. Vui lòng nhập lại! " + "(Số điện thoại bao gồm 10 số và bắt đầu là số 0)");
            System.out.println("Nhập số điện thoại (vd: 0901517061");
            System.out.print("➾ ");
            if (userService.existByPhone(phone)) {
                System.out.println("Số này đã tồn tại! Mời bạn nhập lại");
                System.out.print("➾ ");
            }
        }
        return phone;
    }

    private String inputEmail() {
        System.out.println("Nhập email (vd: truc@gmail.com)");
        System.out.print("➾ ");
        String email;
        while (!ValidateUtils.isEmailValid(email=scanner.nextLine())) {
            System.out.println("Email " + email + " của bạn không đúng định dạng! Vui lòng kiểm tra và nhập lại ");
            System.out.println("Nhập email (vd: truc@gmail.com)");
            System.out.print("➾ ");
        }
        if (userService.existByName(email)) {
            System.out.println("Email " + email + " của bạn đã tồn tại! vui lòng kiểm tra và nhập lại");
            inputEmail();
        }
        return email;
    }

    private String inputPassword() {
        System.out.println("Nhập mật khẩu( mật khẩu phải > 8 kí tự )");
        System.out.print("➾ ");
        String password;
        while (!ValidateUtils.isPasswordValid(password = scanner.nextLine())) {
            System.out.println("Mật khẩu yếu! Vui lòng nhập lại ");
            System.out.print("➾ ");
        }
        return password;
    }

    private String inputAddress(InputOption option) {
        switch (option) {
            case ADD:
                System.out.println("Nhập địa chỉ (vd: Huế)");
                break;
            case UPDATE:
                System.out.println("Nhập địa chỉ mà bạn muốn đổi");
                break;
        }
        System.out.print("➾ ");
        String address = scanner.nextLine();
        return address;
    }
}
