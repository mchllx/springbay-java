package sg.edu.nus.iss.springbay.models;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

public class Form {

    @NotNull(message="Quantity is required")
    @Min(value=1, message="Minimum 1")
    private Integer qty;

    public Form() {
    }

    public Form(Integer qty) {
        this.qty = qty;
    }

    public Integer getQty() { return qty; }
    public void setQty(Integer qty) { this.qty = qty; }

    @Override
    public String toString() {
        return "Form [qty=" + qty + "]";
    } 
 
}
