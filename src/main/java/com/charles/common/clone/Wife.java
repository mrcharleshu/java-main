package com.charles.common.clone;

public class Wife implements Cloneable {

    private Integer id; // Integer和int一样
    private String name;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Wife(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Wife wife = (Wife) o;

        if (id != wife.id) return false;
        return name != null ? name.equals(wife.name) : wife.name == null;
    }

    @Override
    public int hashCode() {
        Integer result = id;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        return result;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    /**
     * @param args
     * @throws CloneNotSupportedException
     */
    public static void main(String[] args) throws CloneNotSupportedException {
        Wife wife = new Wife(1, "wang");
        Wife wife2;
        wife2 = (Wife) wife.clone();
        System.out.println(wife.getClass() == wife2.getClass());//true
        System.out.println(wife == wife2);//false
        System.out.println(wife.equals(wife2));//true
    }
}  