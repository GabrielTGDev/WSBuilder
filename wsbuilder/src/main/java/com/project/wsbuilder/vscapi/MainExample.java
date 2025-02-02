package com.project.wsbuilder.vscapi;

public class MainExample {
    public static void main(String[] args) {
        VSCodeApiRequest request = new VSCodeApiRequest("PHP Stan");
        System.out.println(request.getExtensions());
    }
}
