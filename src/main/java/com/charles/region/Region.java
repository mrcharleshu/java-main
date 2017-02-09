package com.charles.region;

import com.google.common.collect.Lists;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.List;

public class Region {
    private static final String CHINA_CODE = "86";
    private final List<Province> provinces = Lists.newArrayList();

    public List<Province> getProvinces() {
        return provinces;
    }

    public void toSQL() {

        Area china = new Area(CHINA_CODE, "中国");
        print(china, "0");
        for (Province province : this.getProvinces()) {
            print(province, CHINA_CODE);
            for (City city : province.getCities()) {
                print(city, province.getCode());
                for (County county : city.getCounties()) {
                    print(county, city.getCode());
                }
            }
        }
    }

    public static void print(Area area, String parentCode) {
        String sql = "INSERT INTO REGION(`ctime`,`mtime`,`deleted`,`code`,`name`,`parent_code`) "
            + "VALUES(CURRENT_TIMESTAMP,CURRENT_TIMESTAMP,0,"
            + area.getCode() + ",'" + area.getName() + "'," + parentCode + ");";
        System.out.println(sql);
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.JSON_STYLE);
    }
}

class Province extends Area {
    private final List<City> cities = Lists.newArrayList();

    public Province(String code, String name) {
        super(code, name);
    }

    public List<City> getCities() {
        return cities;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.JSON_STYLE);
    }
}

class City extends Area {
    private final List<County> counties = Lists.newArrayList();

    public City(String code, String name) {
        super(code, name);
    }

    public List<County> getCounties() {
        return counties;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.JSON_STYLE);
    }

}

class County extends Area {

    public County(String code, String name) {
        super(code, name);
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.JSON_STYLE);
    }
}

class Area {
    private String code;
    private String name;

    public Area(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}