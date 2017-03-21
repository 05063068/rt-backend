package com.rottentomatoes.movieapi.domain.converters;

import java.util.ArrayList;
import java.util.List;

public class ListConverter {

    public static List convert(List responseList, AbstractConverter converter) {

        List convertedList = new ArrayList();
        for (Object response : responseList) {
            convertedList.add(converter.convert(response));
        }
        return convertedList;
    }
}
