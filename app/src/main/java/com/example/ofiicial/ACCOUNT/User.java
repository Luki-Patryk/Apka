package com.example.ofiicial.ACCOUNT;

public class User
{
    private int id;
    private String nick_name;
    private String email;
    private String password;

//    public User(int id, String nick_name, String email, String password)
//    {
//        this.id = id;
//        this.nick_name = nick_name;
//        this.email = email;
//        this.password = password;
//    }

    @Override
    public String toString()
    {
        return "User{" +
                "id=" + id +
                ", nick_name='" + nick_name + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                '}';
    }

    public int getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public String getNick_name()
    {
        return nick_name;
    }

    public void setNick_name(String nick_name)
    {
        this.nick_name = nick_name;
    }

    public String getEmail()
    {
        return email;
    }

    public void setEmail(String email)
    {
        this.email = email;
    }

    public String getPassword()
    {
        return password;
    }

    public void setPassword(String password)
    {
        this.password = password;
    }
}

