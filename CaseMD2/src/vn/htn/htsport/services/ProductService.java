package vn.htn.htsport.services;

import vn.htn.htsport.model.Product;
import vn.htn.htsport.utils.CSVUtils;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;

public class ProductService implements IProductService{
    public static String path = "data/product.csv";

    private static ProductService instance;

    public ProductService() {
    }

    public static ProductService getInstance() {
        if (instance == null)
            instance = new ProductService();
        return instance;
    }


    @Override
    public List<Product> getProducts() {
        List<Product> newProducts = new ArrayList<>();
        List<String> records = CSVUtils.read(path);
        for (String record : records) {
            newProducts.add(Product.parse(record));
        }
        return newProducts;
    }

    @Override
    public void add(Product newProduct) {
        List<Product> products = getProducts();
        products.add(newProduct);
        CSVUtils.write(path, products);
    }


    @Override
    //id,name,size,quantity,price
    public void update(Product newProduct) {
        List<Product> products = getProducts();
        for (Product product : products) {
            if (product.getId().equals(newProduct.getId())) {
                String name = newProduct.getTitle();
                if (name != null && !name.isEmpty())
                    product.setTitle(newProduct.getTitle());

                String color = newProduct.getSize();
                if (color != null && !color.isEmpty())
                    product.setSize(color);


                Integer quantity = newProduct.getQuantity();
                if (quantity != null)
                    product.setQuantity(quantity);

                Double price = newProduct.getPrice();
                if (price != null)
                    product.setPrice(price);


                CSVUtils.write(path, products);
                break;
            }
        }
    }

    @Override
    public Product getProductById(int id) {
        for (Product product : getProducts()) {
            if (product.getId() == id)
                return product;
        }
        return null;
    }

    @Override
    public boolean exist(int id) {
        return getProductById(id) != null;
    }

    @Override
    public boolean existById(int id) {
        List<Product> products = getProducts();
        for (Product product : products) {
            if (product.getId() == id)
                return true;
        }
        return false;
    }

    @Override
    public void updateQuantity(int id, int quantity) {
        List<Product> products = getProducts();
        for (Product product : products) {
            if (product.getId() == id)
                if (product.getQuantity() >= quantity) {
                    product.setQuantity(product.getQuantity() - quantity);
                    CSVUtils.write(path, products);
                    break;
                }
        }
    }

    @Override
    public boolean checkDuplicateName(String name) {
        for (Product product : getProducts()) {
            if (product.getTitle().equalsIgnoreCase(name))
                return true;
        }
        return false;
    }

    @Override
    public boolean checkDuplicateId(int id) {
        for (Product product : getProducts()) {
            if (product.getId() == id)
                return true;
        }
        return false;
    }

    @Override
    public void removeById(int id) {
        List<Product> products = getProducts();
        products.removeIf(new Predicate<Product>() {
            @Override
            public boolean test(Product product) {
                return product.getId() == id;
            }
        });
        CSVUtils.write(path, products);
    }

    @Override
    public List<Product> findAllOrderByPriceASC() {
        List<Product> products = new ArrayList<>(getProducts());
        products.sort(new Comparator<Product>() {
            @Override
            public int compare(Product o1, Product o2) {
                double result = o1.getPrice() - o2.getPrice();
                if (result == 0)
                    return 0;
                return result > 0 ? 1 : -1;
            }
        });
        return products;
    }

    @Override
    public List<Product> findAllOrderByPriceDESC() {
        List<Product> products = new ArrayList<>(getProducts());
        products.sort(new Comparator<Product>() {
            @Override
            public int compare(Product o1, Product o2) {
                double result = o2.getPrice() - o1.getPrice();
                if (result == 0)
                    return 0;
                return result > 0 ? 1 : -1;
            }
        });
        return products;
    }
}
