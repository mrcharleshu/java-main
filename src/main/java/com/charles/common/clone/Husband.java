package com.charles.common.clone;

public class Husband implements Cloneable {
    private int id;
    private String name;
    private Wife wife;

    public Husband(int id, String name, Wife wife) {
        this.id = id;
        this.name = name;
        this.wife = wife;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Wife getWife() {
        return wife;
    }

    public void setWife(Wife wife) {
        this.wife = wife;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Husband husband = (Husband) o;

        if (id != husband.id) return false;
        if (name != null ? !name.equals(husband.name) : husband.name != null) return false;
        return wife != null ? wife.equals(husband.wife) : husband.wife == null;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (wife != null ? wife.hashCode() : 0);
        return result;
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        // return super.clone(); // shallow clone
        Husband husband = (Husband) super.clone();
        husband.wife = (Wife) husband.getWife().clone();
        return husband;
    }

    /**
     * @param args
     * @throws CloneNotSupportedException
     */
    public static void main(String[] args) throws CloneNotSupportedException {
        Wife wife = new Wife(1, "Sonya");
        Husband husband = new Husband(1, "Charles", wife);
        Husband husband2;
        husband2 = (Husband) husband.clone();
        System.out.println("husband class same=" + (husband.getClass() == husband2.getClass()));//true
        System.out.println("husband object same=" + (husband == husband2));//false
        System.out.println("husband object equals=" + (husband.equals(husband)));//true
        System.out.println("wife class same=" + (husband.getWife().getClass() == husband2.getWife().getClass()));//true
        System.out.println("wife object same=" + (husband.getWife() == husband2.getWife()));//true
        System.out.println("wife object equals=" + (husband.getWife().equals(husband.getWife())));//true
    }
}  