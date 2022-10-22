package ATM;

public class Account {
    private String idCard;
    private String password;
    private String name;
    private double money;
    private double qouta;

    public Account() {
    }

    public Account(String idCard, String password, String name, double money, double qouta) {
        this.idCard = idCard;
        this.password = password;
        this.name = name;
        this.money = money;
        this.qouta = qouta;
    }

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getMoney() {
        return money;
    }

    public void setMoney(double money) {
        this.money = money;
    }

    public double getQouta() {
        return qouta;
    }

    public void setQouta(double qouta) {
        this.qouta = qouta;
    }
}
