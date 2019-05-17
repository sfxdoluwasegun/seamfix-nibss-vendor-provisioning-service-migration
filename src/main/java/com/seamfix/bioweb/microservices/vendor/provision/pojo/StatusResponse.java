package com.seamfix.bioweb.microservices.vendor.provision.pojo;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class StatusResponse implements Serializable {

	private static final long serialVersionUID = 5273518087596813053L;
	private int status = -1;
    private String message;
}
