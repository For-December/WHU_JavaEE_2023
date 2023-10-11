package com.forDece.dao;

import com.forDece.framework.annotations.*;

@Autowired("myVip")
public class Vip {
    @Value("tom")
    private String name;
    @Value("12")
    private Integer id;
    @Value("98.88")
    private Double score;

    @ShortType(999)
    private short nativeShort;

    @IntType(8888)
    private int nativeInt;

    @BoolType(true)
    private boolean nativeBool;

    @DoubleType(3.1415926)
    private double nativeDouble;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Double getScore() {
        return score;
    }

    public void setScore(Double score) {
        this.score = score;
    }

    public short getNativeShort() {
        return nativeShort;
    }

    public void setNativeShort(short nativeShort) {
        this.nativeShort = nativeShort;
    }

    public int getNativeInt() {
        return nativeInt;
    }

    public void setNativeInt(int nativeInt) {
        this.nativeInt = nativeInt;
    }

    public boolean isNativeBool() {
        return nativeBool;
    }

    public void setNativeBool(boolean nativeBool) {
        this.nativeBool = nativeBool;
    }

    public double getNativeDouble() {
        return nativeDouble;
    }

    public void setNativeDouble(double nativeDouble) {
        this.nativeDouble = nativeDouble;
    }

    @Override
    public String toString() {
        return "Vip{" +
                "name='" + name + '\'' +
                ", id=" + id +
                ", score=" + score +
                ", nativeShort=" + nativeShort +
                ", nativeInt=" + nativeInt +
                ", nativeBool=" + nativeBool +
                ", nativeDouble=" + nativeDouble +
                '}';
    }
}
