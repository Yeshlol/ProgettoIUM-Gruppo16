package it.unisa.smartfarm;

import androidx.annotation.Nullable;

import java.io.Serializable;
import java.util.ArrayList;

public class Account implements Serializable {
    private String email;
    private String password;
    private String ruolo;
    private ArrayList<String> colture;
    final static long serialVersionUID = 1L;

    public Account(String email, String password, String ruolo) {
        this.email = email;
        this.password = password;
        this.ruolo = ruolo;
        this.colture = new ArrayList<>();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRuolo() {
        return ruolo;
    }

    public void setRuolo(String ruolo) {
        this.ruolo = ruolo;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public ArrayList<String> getColture() { return colture; }

    public void setColture(ArrayList<String> colture) { this.colture = colture; }

    @Override
    public boolean equals(@Nullable Object obj) {
        return ((Account) obj).getEmail().equals(this.email) && ((Account) obj).getPassword().equals(this.password) && ((Account) obj).getRuolo().equals(this.ruolo);
    }
}
