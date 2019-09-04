package in.app.dequeue.Models;

public class Products {
    String pid;
    String pname;
    String bname;
    String price;

    public Products(String pid, String pname, String bname, String price) {
        this.pid = pid;
        this.pname = pname;
        this.bname = bname;
        this.price = price;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getPname() {
        return pname;
    }

    public void setPname(String pname) {
        this.pname = pname;
    }

    public String getBname() {
        return bname;
    }

    public void setBname(String bname) {
        this.bname = bname;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
