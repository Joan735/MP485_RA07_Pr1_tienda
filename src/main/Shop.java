package main;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;
import model.Product;
import model.Sale;
import model.Amount;
import model.Client;
import model.Employee;

public class Shop {

    private Amount cash = new Amount(100.00);
    private ArrayList<Product> inventory;
    private int numberProducts;
    private ArrayList<Sale> sales;
    private Amount totalSale = new Amount(0);

    final static double TAX_RATE = 1.04;

    public Shop() {
        inventory = new ArrayList<>();
        sales = new ArrayList<>();

    }

    public static void main(String[] args) {
        Shop shop = new Shop();

        shop.loadInventory();
        shop.initSession();

        int opcion = 0;
        boolean exit = false;

        do {
            System.out.println("\n");
            System.out.println("===========================");
            System.out.println("Main menu myShop.com");
            System.out.println("===========================");
            System.out.println("1) Count cash register");
            System.out.println("2) Add product");
            System.out.println("3) Add stock");
            System.out.println("4) Mark product next expiration");
            System.out.println("5) See inventory");
            System.out.println("6) Sale");
            System.out.println("7) See sales");
            System.out.println("8) Total sale");
            System.out.println("9) Remove product");
            System.out.println("10) Leave program");
            System.out.print("Select an option: ");

            boolean correct = false;
            do {
                try {
                    Scanner sc = new Scanner(System.in);
                    opcion = sc.nextInt();
                    if (opcion < 1 || opcion > 10) {
                        System.err.println("Incorrect number.");
                        System.out.print("- Try again: ");
                    }
                    correct = true;
                } catch (InputMismatchException e) {
                    System.err.println("You can only write numbers type Int.");
                    System.out.print("- Try again: ");
                }
            } while (correct == false || opcion < 1 || opcion > 10);

            System.out.println("");

            switch (opcion) {
                case 1:
                    shop.showCash();
                    break;

                case 2:
                    shop.addProduct();
                    break;

                case 3:
                    shop.addStock();
                    break;

                case 4:
                    shop.setExpired();
                    break;

                case 5:
                    shop.showInventory();
                    break;

                case 6:
                    shop.sale();
                    break;

                case 7:
                    shop.showSales();
                    break;

                case 8:
                    shop.totalSale();
                    break;
                case 9:
                    shop.removeProduct();
                    break;
                case 10:
                    exit = true;
                    break;
            }
        } while (!exit);
    }

    /**
     * load initial inventory to shop
     */
    public void loadInventory() {
        inventory.add(new Product("Apple", 10.00, true, 10));
        inventory.add(new Product("Pear", 20.00, true, 20));
        inventory.add(new Product("Hamburguer", 30.00, true, 30));
        inventory.add(new Product("Strawberry", 5.00, true, 20));
    }

    public void initSession() {
        int employeeNumber;
        String password;
        boolean login;
        System.out.println("Login:");
        do {
            System.out.print("- Enter the employee's number: ");
            employeeNumber = numberInt();
            System.out.print("- Enter the password: ");
            password = text();
            Employee employee = new Employee(employeeNumber, password, "test");
            login = employee.login(employeeNumber, password);
            System.out.println("");
        } while (login == false);

    }

    /**
     * show current total cash
     */
    private void showCash() {
        System.out.println("1) Count cash register");
        System.out.println("Current balance: " + cash);
    }

    /**
     * add a new product to inventory getting data from console
     */
    public void addProduct() {
        System.out.println("2) Add product");
        System.out.print("- Name: ");
        String name = text();
        Product product = findProduct(name);
        if (product == null) {
            System.out.print("- Wholesaler price: ");
            double wholesalerPrice = numberDouble();
            System.out.print("- Stock: ");
            int stock = numberInt();

            addProduct(new Product(name, wholesalerPrice, true, stock));
            System.out.println("Product added");
        } else {
            System.out.println("Already added product");
        }
    }

    /**
     * add stock for a specific product
     */
    public void addStock() {
        System.out.println("3) Add stock");
        System.out.print("- Enter a product name: ");
        String name = text();
        Product product = findProduct(name);

        if (product != null) {
            // ask for stock
            System.out.print("- Enter the amount to add: ");
            int stock = numberInt();
            // update stock product
            product.addStock(stock);
            System.out.println("The stock of the product: " + name + " has been updated to " + product.getStock());

        } else {
            System.out.println("Couldn't find a product with the name: " + name);
        }
    }

    /**
     * set a product as expired
     */
    private void setExpired() {
        System.out.println("4) Mark product next expiration");
        System.out.print("- Enter a product name: ");
        String name = text();
        Product product = findProduct(name);

        if (product != null) {
            product.expire();
            System.out.println("The price of the product: " + name + " has been updated to " + product.getPublicPrice());
        } else {
            System.out.println("Product not found");
        }
    }

    /**
     * show all inventory
     */
    public void showInventory() {
        System.out.println("5) See inventory");
        System.out.println("Current inventory content:");
        int contador = 0;
        for (Product product : inventory) {
            if (product != null) {
                contador++;
                System.out.println(contador + ". " + product.getName() + " - Price: " + product.getPublicPrice() + " - Stock: " + product.getStock());
            }
        }
    }

    /**
     * make a sale of products to a client
     */
    public void sale() {
        System.out.println("6) Sale");
        // ask for client name
        System.out.print("- Make a sale, write the client name: ");
        String clientName = text();
        ArrayList<Product> soldProducts = new ArrayList<>();
        // sale product until input name is not 0
        Amount totalAmount = new Amount(0.0);
        String name = "";

        while (!name.equals("0")) {
            System.out.println("");
            showInventory();
            System.out.println("");
            System.out.print("- Enter the product name, write 0 to finish: ");
            name = text();
            if (name.equals("0")) {
                break;
            }
            Product product = findProduct(name);
            boolean productAvailable = false;

            if (product != null && product.isAvailable()) {
                productAvailable = true;
                totalAmount = totalAmount.plus(product.getPublicPrice());
                product.setStock(product.getStock() - 1);
                // if no more stock, set as not available to sale
                if (product.getStock() == 0) {
                    product.setAvailable(false);
                }
                System.out.println("Product added");
                if (productAvailable == true) {
                    soldProducts.add(new Product(name, product.getWholesalerPrice().getValue(), true, product.getStock()));
                }
            }
            if (!productAvailable) {
                System.out.println("Product not found or without stock.");
            }
        }
        totalAmount = totalAmount.multiply(TAX_RATE);
        cash = cash.plus(totalAmount);
        System.out.println("Succes sale, total: " + totalAmount);

        Client client = new Client(456, new Amount(50), clientName);
        boolean enoughAmount = client.pay(totalAmount);
        if (enoughAmount == true) {
            System.out.println("Amount paid, remaining balance: " + client.getBalance());
        } else if (enoughAmount == false) {
            System.out.println("Amount paid, the client owes: " + client.getBalance());
        }
        sales.add(new Sale(client, soldProducts, totalAmount));
    }

    /**
     * show all sales
     */
    private void showSales() {
        System.out.println("7) See sales");
        int contador = 0;
        if (sales.isEmpty()) {
            System.out.println("No sales.");
        } else {
            System.out.println("Sales list:");
            for (Sale sale : sales) {
                contador++;
                System.out.println(contador + ". " + sale);
            }
        }
    }

    /**
     * add a product to inventory
     *
     * @param product
     */
    public void addProduct(Product product) {
        inventory.add(product);
    }

    /**
     * check if inventory is full or not
     *
     * @return true if inventory is full
     */
    public boolean isInventoryFull() {
        if (numberProducts == 10) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * find product by name
     *
     * @param name
     * @return product found by name
     */
    public Product findProduct(String name) {
        for (int i = 0; i < inventory.size(); i++) {
            if (inventory.get(i) != null && inventory.get(i).getName().equalsIgnoreCase(name)) {
                return inventory.get(i);
            }
        }
        return null;
    }

    public void totalSale() {
        System.out.println("8) Total sale");
        totalSale.setValue(0);
        for (Sale sale : sales) {
            if (sale != null) {
                totalSale = totalSale.plus(sale.getAmount());
            }
        }
        System.out.println("Total sale: " + totalSale);
    }

    public void removeProduct() {
        System.out.println("9) Remove product");
        System.out.print("- Enter the product name to remove it: ");
        String name = text();
        Product product = findProduct(name);
        if (product != null) {
            inventory.remove(product);
            System.out.println("Product: " + product.getName() + " removed.");
        } else {
            System.out.println("Couldn't find the product with the name: " + name);
        }
    }

    public String text() {
        Scanner sc = new Scanner(System.in);
        return sc.nextLine();
    }

    public int numberInt() {
        int number = 0;
        boolean correct = false;
        do {
            try {
                Scanner sc = new Scanner(System.in);
                number = sc.nextInt();
                correct = true;
            } catch (InputMismatchException e) {
                System.err.println("You can only write numbers type Int.");
                System.out.print("- Try again: ");
            }
        } while (correct == false);
        return number;
    }

    public double numberDouble() {
        double number = 0;
        boolean correct = false;
        do {
            try {
                Scanner sc = new Scanner(System.in);
                number = sc.nextInt();
                correct = true;
            } catch (InputMismatchException e) {
                System.err.println("You can only write numbers type Double.");
                System.out.print("- Try again: ");
            }
        } while (correct == false);
        return number;
    }
}
