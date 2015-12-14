package com.example.aishwarya.melotto;

import com.parse.ParseClassName;
import com.parse.ParseFile;
import com.parse.ParseObject;

import java.util.List;

/**
 * Created by Aishwarya on 12/13/2015.
 */
@ParseClassName("LotteryTicket")
public class LotteryTicket extends ParseObject {

    private String name;
    private List<Integer> numbers;
    private ParseFile photo;

    public LotteryTicket(String name, List<Integer> numbers)
    {

    }

    public ParseFile getPhotoFile() {
        return getParseFile("photo");
    }

    public void setPhotoFile(ParseFile file) {
        put("photo", file);
    }

}
