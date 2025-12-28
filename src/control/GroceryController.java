/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package control;

import model.GroceryModel;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.JFrame;
import javax.swing.table.DefaultTableModel;
import view.Loginpage;

public class GroceryController {
    private ArrayList<GroceryModel> groceryList = new ArrayList<>();

    public void addProduct(String id, String name, String qtyStr, String priceStr, DefaultTableModel tableModel) {
        try {
            if (id.trim().isEmpty() || name.trim().isEmpty() || qtyStr.trim().isEmpty() || priceStr.trim().isEmpty()) {
                throw new Exception("Validation Error: All fields are required!");
            }

            int qty;
            double price;
            try {
                qty = Integer.parseInt(qtyStr);
                price = Double.parseDouble(priceStr);
            } catch (NumberFormatException e) {
                throw new Exception("Validation Error: Quantity must be an Integer and Price must be a Decimal!");
            }

            if (qty < 0 || price < 0) {
                throw new Exception("Validation Error: Price and Quantity cannot be negative!");
            }

            for (GroceryModel item : groceryList) {
                if (item.getProductId().equalsIgnoreCase(id)) {
                    throw new Exception("Validation Error: Product ID " + id + " already exists!");
                }
            }

            GroceryModel newItem = new GroceryModel(id, name, qty, price);
            groceryList.add(newItem);
            tableModel.addRow(new Object[]{id, name, qty, price});
            JOptionPane.showMessageDialog(null, "Product successfully added!");

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Input Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    public void handleLogout(JFrame currentFrame) {
    // 1. Confirm with the user
    int response = JOptionPane.showConfirmDialog(currentFrame, 
            "Are you sure you want to logout?", "Logout", 
            JOptionPane.YES_NO_OPTION);
    
    if (response == JOptionPane.YES_OPTION) {
        // 2. Close the current Admin Dashboard
        currentFrame.dispose();
        
        // 3. Reopen the Login Page
        new Loginpage().setVisible(true);
    }
}

    public void deleteProduct(int selectedRow, DefaultTableModel tableModel) {
        if (selectedRow != -1) {
            groceryList.remove(selectedRow);
            tableModel.removeRow(selectedRow);
            JOptionPane.showMessageDialog(null, "Item Deleted!");
        } else {
            JOptionPane.showMessageDialog(null, "Please select a row from the table first.");
        }
    }

    public void clearTextFields(javax.swing.JTextField id, javax.swing.JTextField name, javax.swing.JTextField qty, javax.swing.JTextField price) {
        id.setText("");
        name.setText("");
        qty.setText("");
        price.setText("");
        id.requestFocus();
    }

    public void updateProduct(int selectedRow, String name, String qtyStr, String priceStr, DefaultTableModel tableModel) {
        try {
            if (selectedRow == -1) {
                throw new Exception("Please select a product from the table to update.");
            }

            int qty = Integer.parseInt(qtyStr);
            double price = Double.parseDouble(priceStr);

            if (qty < 0 || price < 0) {
                throw new Exception("Validation Error: Price and Quantity cannot be negative!");
            }

            GroceryModel item = groceryList.get(selectedRow);
            // FIX: Ensure Name is also updated in the Model
            item.setQuantity(qty);
            item.setPrice(price);
            // item.setName(name); // Add this if you have a setName method in GroceryModel

            tableModel.setValueAt(name, selectedRow, 1);
            tableModel.setValueAt(qty, selectedRow, 2);
            tableModel.setValueAt(price, selectedRow, 3);

            JOptionPane.showMessageDialog(null, "Product updated successfully!");

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Validation Error: Numeric fields required for update!");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }
}