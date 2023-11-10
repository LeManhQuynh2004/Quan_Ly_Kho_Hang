package fpoly.quynhlmph32353.quanlykhohang.Model;

public class Invoice {
    private int invoice_id;
    private String username;
    private int invoiceNumber;
    private int invoiceType;
    private String date;

    public Invoice() {
    }

    public Invoice(int invoice_id, String username, int invoiceNumber, int invoiceType, String date) {
        this.invoice_id = invoice_id;
        this.username = username;
        this.invoiceNumber = invoiceNumber;
        this.invoiceType = invoiceType;
        this.date = date;
    }
    public int getInvoice_id() {
        return invoice_id;
    }

    public void setInvoice_id(int invoice_id) {
        this.invoice_id = invoice_id;
    }

    public String getUsername() {return username;}

    public void setUsername(String username) {
        this.username = username;
    }

    public int getInvoiceNumber() {
        return invoiceNumber;
    }

    public void setInvoiceNumber(int invoiceNumber) {
        this.invoiceNumber = invoiceNumber;
    }

    public int getInvoiceType() {
        return invoiceType;
    }

    public void setInvoiceType(int invoiceType) {
        this.invoiceType = invoiceType;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
