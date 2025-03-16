package com.project.wsbuilder.vscapi;

import com.project.wsbuilder.apisetup.ApiService;

public class VSCodeApiRequest {
    String term;

    public VSCodeApiRequest(String term) {
        this.term = term;
    }

    private String getRequestBody() {
        return "{\n" +
                "    \"filters\": [\n" +
                "        {\n" +
                "            \"criteria\": [\n" +
                "                {\n" +
                "                    \"filterType\": 8,\n" +
                "                    \"value\": \"Microsoft.VisualStudio.Code\"\n" +
                "                },\n" +
                "                {\n" +
                "                    \"filterType\": 10,\n" +
                "                    \"value\": \"" + term + "\"\n" +
                "                }\n" +
                "            ],\n" +
                "            \"pageNumber\": 1,\n" +
                "            \"pageSize\": 100,\n" +
                "            \"sortBy\": 0,\n" +
                "            \"sortOrder\": 0\n" +
                "        }\n" +
                "    ],\n" +
                "    \"assetTypes\": [],\n" +
                "    \"flags\": 914\n" +
                "}";
    }

    public String getExtensions() {
        try {
            ApiService apiService = new ApiService("https://marketplace.visualstudio.com/_apis/public/gallery/extensionquery");
            return apiService.postData(getRequestBody());
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}
