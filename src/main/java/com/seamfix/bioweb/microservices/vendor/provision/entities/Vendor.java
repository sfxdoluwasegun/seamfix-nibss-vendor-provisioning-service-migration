/*13 May 2019
charles
*/
package com.seamfix.bioweb.microservices.vendor.provision.entities;

import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.Immutable;

import com.seamfix.kyc.microservices.utilities.entities.base.BaseLongPkEntity;

@Entity
@Table(name = "KYC_DEALER")
@Immutable
public class Vendor extends BaseLongPkEntity {

}
