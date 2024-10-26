package com.example.cinema.po;
import org.springframework.stereotype.Component;
import java.sql.Timestamp;
@Component
public class historyItem {
    private String[] kindList={"购买会员卡","充值会员卡","购买电影票","退票"};
    private int id;
    private int userId;
    private int kind;
    private double money;
    private Timestamp Time;
    private String description;
    public String getDescription(){return this.description;}
    public void setDescription(String i){this.description=i;}
    public void setId(int id){
        this.id=id;
    }
    public int getId(){
        return  id;
    }
    public void setUserId(int id){this.userId=id;}
    public int getUserId(){return  userId;}
    public int getKind(){
        return kind;
    }
    public void setKind(int i){
        this.kind=i;
    }
    public double getMoney(){
        return  money;
    }
    public void setMoney(double money){
        this.money=money;
    }
    public Timestamp getTime(){
        return  Time;
    }
    public void setsTime(Timestamp time){
        this.Time=time;
    }
}
