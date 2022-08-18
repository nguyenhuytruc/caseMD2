package vn.htn.htsport.views;

import vn.htn.htsport.model.Product;
import vn.htn.htsport.services.IProductService;
import vn.htn.htsport.services.ProductService;
import vn.htn.htsport.utils.AppUtils;

import java.util.List;
import java.util.Scanner;

public class ProductView {
    private final IProductService productService;
    private final Scanner scanner = new Scanner(System.in);

    public ProductView() {
        productService = ProductService.getInstance();
    }

    public void add() {
        do {
            long id = System.currentTimeMillis()/1000;
            String title = inputTitle(InputOption.ADD);
            String size = inputSize(InputOption.ADD);
            int quantity = inputQuantity(InputOption.ADD);
            double price = inputPrice(InputOption.ADD);
            Product product = new Product(id, title, size, quantity, price);
            productService.add(product);
            System.out.println("Bạn đã thêm sản phẩm thành công\n");
        } while (AppUtils.isRetry(InputOption.ADD));
    }

    private int inputQuantity(InputOption option) {
        switch (option) {
            case ADD:
                System.out.println("Nhập số lượng: ");
                break;
            case UPDATE:
                System.out.println("Nhập số lượng bạn muốn sửa: ");
                break;
        }
        int quantity;
        do {
            quantity = AppUtils.retryParseInt();
            if (quantity <= 0 )
                System.out.println("Số lượng phải lớn hơn 0!");
        } while (quantity <= 0);
        return quantity;
    }

    private String inputSize(InputOption option) {
        switch (option) {
            case ADD:
                System.out.println("Nhập size của sản phẩm: ");
                break;
            case UPDATE:
                System.out.println("Nhập size bạn muốn sửa: ");
                break;

        }
        String result;
        System.out.println("-->> ");
        while ((result = scanner.nextLine()).isEmpty()) {
            System.err.printf("Size sản phẩm không được để trống \n");
            System.out.print("-->> ");

        }
        return result;
    }

    public void showProducts(InputOption inputOption) {
        System.out.println("|---------------------------------------------------- DANH SÁCH SẢN PHẨM ----------------------------------------------------|");
        System.out.printf("%-15s %-25s %-15s %-15s %-15s\n", "ID", "Tên sản phẩm", "Size", "Số lượng", "Giá");
        for (Product product : productService.getProducts()) {
            System.out.printf("%-15d %-25s %-15s %-15d %-15s\n",
                    product.getId(),
                    product.getTitle(),
                    product.getSize(),
                    product.getQuantity(),
                    AppUtils.doubleToVND(product.getPrice())
            );
        }
        System.out.println("|------------------------------------------------------------------------------------------------------------------------|\n");
        if (inputOption == InputOption.SHOW)
            AppUtils.isRetry(InputOption.SHOW);
    }

    public void remove() {
        showProducts(InputOption.DELETE);
        int id;
        while (!productService.exist(id = inputId(InputOption.DELETE))) {
            System.out.println("Không tìm thấy sản phẩm cần xóa!");
            System.out.println("Nhấn 'y' để thêm tiếp sản phẩm\t|\t 'q' để quay lại \t|\t 't' để thoát chương trình");
            System.out.print("--->> ");
            String option = scanner.nextLine();
            switch (option) {
                case "y":
                    break;
                case "q":
                    return;
                case "t":
                    AppUtils.exit();
                    break;
                default:
                    System.out.println("Chọn không đúng chức năng! Vui lòng nhập lại");
                    break;
            }
        }
        System.out.println("|-------------------- Bạn có chắc chắn muốn xóa! ---------------------|");
        System.out.println("|  Nhấn 1 để xóa                                                      |");
        System.out.println("|  Nhấn 2 để quay lại.                                                |");
        System.out.println("|---------------------------------------------------------------------|");
        int option = AppUtils.retryChoose(1, 2);
        if (option == 1) {
            productService.removeById(id);
            System.out.println("Đã xóa sản phẩm thành công");
            AppUtils.isRetry(InputOption.DELETE);

        }
    }

    private String  inputTitle(InputOption option) {
        switch (option) {
            case ADD:
                System.out.println("Nhập tên sản phẩm: ");
                break;
            case UPDATE:
                System.out.println("Nhập tên bạn muốn sửa: ");
                break;
        }
        String result;
        System.out.print("-->> ");
        while ((result = scanner.nextLine()).isEmpty()) {
            System.out.printf("Tên sản phẩm không được để trống \n");
            System.out.print("-->> ");
        }
        return result;
    }

    private int inputId(InputOption option) {
        int id;
        switch (option) {
            case ADD:
                System.out.println("Nhập ID");
                break;
            case UPDATE:
                System.out.println("Nhập ID bạn muốn sửa");
                break;
            case DELETE:
                System.out.println("Nhập ID bạn cần xóa: ");
                break;
        }
        boolean isRetry = false;
        do {
            id = AppUtils.retryParseInt();
            boolean exist = productService.existById(id);
            switch (option) {
                case ADD:
                    if (exist) {
                        System.out.println("ID này đã tồn tại.Vui lòng nhập ID khác!");

                    }
                    isRetry = exist;
                    break;
                case UPDATE:
                    if (!exist) {
                        System.out.println("Không tìm thất ID! Vui lòng nhập lại");
                    }
                    isRetry = !exist;
                    break;
            }
        } while (isRetry);
        return id;
    }

    private double inputPrice(InputOption option) {
        switch (option) {
            case ADD:
                System.out.println("Nhập giá sản phẩm: ");
                break;
            case UPDATE:
                System.out.println("Nhập giá bạn muốn sửa: ");
                break;
        }
        double price;
        do {
            price = AppUtils.retryParseDouble();
            if (price < 1000)
                System.out.println("giá phải lớn hơn 1000!");
        } while (price < 1000);
        return price;
    }

    public void showProductsSort(InputOption inputOption, List<Product> products) {
        System.out.println("*************                 --- DANH SÁCH SẢN PHẨM ---                ****************");
        System.out.printf("%-15s %-25s %-15s %-15s %-15s\n", "ID", "Tên sản phẩm", "Size", "Số lượng", "Giá");
        for (Product product : products) {
            System.out.printf("%-15s %-25s %-15s %-15s %-15s\n", product.getId(), product.getTitle(), product.getSize(), product.getQuantity(), AppUtils.doubleToVND(product.getPrice()));

        }
        System.out.println("*****************************************************************************************");
        if (inputOption == InputOption.SHOW) {
            AppUtils.isRetry(InputOption.SHOW);
        }
    }

    public void sortByPriceOrderByASC() {
        showProductsSort(InputOption.SHOW, productService.findAllOrderByPriceASC());
    }

    public void sortByPriceOrderByDESC() {
        showProductsSort(InputOption.SHOW, productService.findAllOrderByPriceDESC());
    }

    public void update() {
        boolean isRetry;
        do {
            showProducts(InputOption.UPDATE);
            long id = inputId(InputOption.UPDATE);
            System.out.println("|----------------- SỬA SẢN PHẨM -----------------|");
            System.out.println("| 1. Sửa tên sản phẩm                            |");
            System.out.println("| 2. Sửa size sản phẩm                           |");
            System.out.println("| 3. Sửa số lượng sản phẩm                       |");
            System.out.println("| 4. Sửa giá sản phẩm                            |");
            System.out.println("| 5. Quay lại Menu                               |");
            System.out.println("|------------------------------------------------|");
            System.out.println("| Chọn chức năng:                                |");
            int option = AppUtils.retryChoose(1, 5);
            Product newProduct = new Product();
            newProduct.setId(id);

            switch (option) {
                case 1:
                    String title = inputTitle(InputOption.UPDATE);
                    newProduct.setTitle(title);
                    productService.update(newProduct);
                    System.out.println("Tên sản phẩm đã cập nhật thành công");
                    break;
                case 2:
                    String color = inputSize(InputOption.UPDATE);
                    newProduct.setSize(color);
                    productService.update(newProduct);
                    System.out.println("Size sản phẩm đã cập nhật thành công");
                    break;
                case 3:
                    int quantity = inputQuantity(InputOption.UPDATE);
                    newProduct.setQuantity(quantity);
                    productService.update(newProduct);
                    System.out.println("Số lượng sản phẩm đã cập nhật thành công");
                    break;
                case 4:
                    double price = inputPrice(InputOption.UPDATE);
                    newProduct.setPrice(price);
                    productService.update(newProduct);
                    System.out.println("Bạn đã sửa giá sản phẩm thành công");
                    break;
            }
            isRetry = option != 5 && AppUtils.isRetry(InputOption.UPDATE);
        }
        while (isRetry);
    }
}
