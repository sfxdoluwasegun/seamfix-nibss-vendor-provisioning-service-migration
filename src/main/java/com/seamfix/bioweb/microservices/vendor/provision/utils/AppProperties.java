package com.seamfix.bioweb.microservices.vendor.provision.utils;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;

import com.seamfix.kyc.microservices.utilities.props.BaseAppProperties;

@ApplicationScoped
@Named("appProperties")
public class AppProperties extends BaseAppProperties {

}
