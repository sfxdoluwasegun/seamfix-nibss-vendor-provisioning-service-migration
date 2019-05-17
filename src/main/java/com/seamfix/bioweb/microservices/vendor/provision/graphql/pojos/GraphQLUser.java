package com.seamfix.bioweb.microservices.vendor.provision.graphql.pojos;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class GraphQLUser {

	private String id;
    private String name;
    private String msisdn;
    private boolean firstLogin;
    private String email;
    private String password;
    private List<String> roles = new ArrayList<String>();
    private List<String> privileges = new ArrayList<String>();
    private Map<String, String> requestHeaders = new ConcurrentHashMap<>();
    
    private String localeStr;
    
    public GraphQLUser(String name, String email, String password) {
        this(null, name, email, password);
    }
    
    public GraphQLUser(String id, String name, String email, String password) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
    }

	
}
