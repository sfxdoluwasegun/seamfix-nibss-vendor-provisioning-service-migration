/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.seamfix.bioweb.microservices.vendor.provision.entities.enums;

/**
 *
 * @author Marcel
 * @since Jul 17, 2017 - 8:16:45 AM
 */
public enum NodeActivityStatusEnum {

    STOLEN("Stolen"), RETRIEVED("Retrieved"), RETURNED_TO_B2B("Pending") , DAMAGED("Damaged");
	
	private String displayName;

    private NodeActivityStatusEnum(String displayName) {
        this.displayName = displayName;
    }

        public String getDisplayName() {
        return displayName;
    }

}
