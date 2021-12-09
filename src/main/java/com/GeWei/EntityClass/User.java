package com.GeWei.EntityClass;

import com.GeWei.Repository.UserRepository;

import java.util.HashMap;
import java.util.Map;

public class User {
    static public int num;
    private int ID;
    private String Name;
    private String Account;
    private String Password;
    private String E_mail;
    private String Telephone;
    private int root;
    static {
        num= UserRepository.getRows();
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getAccount() {
        return Account;
    }

    public void setAccount(String account) {
        Account = account;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public String getE_mail() {
        return E_mail;
    }

    public void setE_mail(String e_mail) {
        E_mail = e_mail;
    }

    public String getTelephone() {
        return Telephone;
    }

    public void setTelephone(String telephone) {
        Telephone = telephone;
    }

    public int getRoot() {
        return root;
    }

    public void setRoot(int root) {
        this.root = root;
    }

    public User(int id, String account, String password, String E_mail) {
        this.Account = account;
        this.Password = password;
        this.E_mail=E_mail;
        this.ID = id;
        num++;
    }

    public User(){
    }
}
