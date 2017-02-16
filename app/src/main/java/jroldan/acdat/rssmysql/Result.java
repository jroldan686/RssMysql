package jroldan.acdat.rssmysql;

import java.io.Serializable;
import java.util.ArrayList;

public class Result implements Serializable {
    boolean code;
    int status;
    String message;
    ArrayList<Site> sites;
    int last;

    public boolean getCode() {
        return code;
    }

    public void setCode(boolean code) {
        this.code = code;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ArrayList<Site> getSites() {
        return sites;
    }

    public void setSites(ArrayList<Site> sites) {
        this.sites = sites;
    }

    public int getLast() {
        return last;
    }

    public void setLast(int last) {
        this.last = last;
    }
}