package com.fiveguys.robocar.auth;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

public class RequestMappings {
    private List<CustomRequestMapping> customRequestMappingList = newEmptyArrayList();

    public void add(String url, String method) {
        customRequestMappingList.add(new CustomRequestMapping(url, method));
    }

    public boolean contains(String url, String method) {
        return customRequestMappingList.contains(new CustomRequestMapping(url, method));
    }

    private static ArrayList<CustomRequestMapping> newEmptyArrayList() {
        return new ArrayList<>();
    }

    @Getter
    @AllArgsConstructor
    public static class CustomRequestMapping {

        private String url;
        private String method;
        @Override
        public boolean equals(Object obj) {
            if (obj instanceof CustomRequestMapping) {
                return ((CustomRequestMapping) obj).getUrl().equals(url)
                        && ((CustomRequestMapping) obj).getMethod().equals(method);
            }
            return false;
        }
    }
}
