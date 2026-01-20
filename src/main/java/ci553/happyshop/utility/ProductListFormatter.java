package ci553.happyshop.utility;

import ci553.happyshop.catalogue.Product;

import java.util.ArrayList;

// new imports
import java.util.Map;
import java.util.TreeMap;

/**
 * This class builds a formatted, receipt-like summary from a list of products.
 * It is used by:
 * 1. CustomerModel – to display the trolley and receipt
 * 2. The Order class – to generate a summary for writing to an order's file
 */

public class ProductListFormatter {
    /**
     * Builds a formatted string showing each product's ID, description,
     * quantity ordered, and total price. Also includes a total price at the end.
     *
     * @param proList a List of products
     * @return A nicely formatted string representation of the product list with totals
     */
    public static String buildString(ArrayList<Product> proList) {
        StringBuilder sb = new StringBuilder();
        double totalPrice = 0;
        TreeMap<String, Product> productById = new TreeMap<>();
        TreeMap<String, Integer> qtyById = new TreeMap<>();
        for (Product pr : proList) {
            String id = pr.getProductId();
            int qtyToAdd = pr.getOrderedQuantity();

            if (!productById.containsKey(id)) {
                productById.put(id, pr);
                qtyById.put(id, qtyToAdd);
            } else {
                int currentQty = qtyById.get(id);
                qtyById.put(id, currentQty + qtyToAdd);
            }
        }
        for (String id : productById.keySet()) {
            Product pr2 = productById.get(id);
            int orderedQuantity = qtyById.get(id);
            //%-18.18s, format the argument as a String,
            // -18 → Left-align the string in 18-character wide space.
            //.18 → Truncate the string to at most 18 characters
            String aProduct = String.format(" %-7s %-18.18s (%2d) £%7.2f\n",
                    pr2.getProductId(),
                    pr2.getProductDescription(),
                    orderedQuantity,
                    pr2.getUnitPrice() * orderedQuantity);

            sb.append(aProduct);
            totalPrice = totalPrice + pr2.getUnitPrice() * orderedQuantity;
        }


        String lineSeparator = "-".repeat(44) + "\n";
        String total = String.format(" %-35s £%7.2f\n", "Total", totalPrice);

        sb.append(lineSeparator);
        sb.append(total);
        return sb.toString();
    }
}


