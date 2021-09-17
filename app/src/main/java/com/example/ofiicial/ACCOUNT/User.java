package com.example.ofiicial.ACCOUNT;

public class User
{
    private int user_id;
    private String nick_name;
    private String email;
    private String password;

    public User(int user_id, String nick_name, String email, String password)
    {
        this.user_id = user_id;
        this.nick_name = nick_name;
        this.email = email;
        this.password = password;
    }


    public int getId()
    {
        return user_id;
    }

    public void setId(int user_id)
    {
        this.user_id = user_id;
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

