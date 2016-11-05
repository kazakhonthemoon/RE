package kz.eugales.re4.model;

import com.google.gson.annotations.Expose;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Adil on 25.09.2016.
 */
public class Result<T> implements Serializable {

    @Expose
    private String error;

    @Expose
    private List<T> value;

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public List<T> getValue() {
        return value;
    }

    public void setValue(List<T> value) {
        this.value = value;
    }
}
