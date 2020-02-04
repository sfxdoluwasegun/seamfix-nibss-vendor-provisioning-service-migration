package com.seamfix.bioweb.microservices.vendor.provision.utils;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;

import com.seamfix.bioweb.microservices.vendor.provision.constants.StringConstants;
import com.seamfix.kyc.microservices.utilities.props.AbstractAppApproperties;
import com.seamfix.kyc.microservices.utilities.props.BaseAppProperties;
import com.seamfix.kyc.microservices.utilities.props.DelegateAppProperties;
import com.seamfix.kyc.microservices.utilities.props.FileAppProperties;
import com.sf.bioweb.auth.utils.IAuthLibProperty;
import org.apache.commons.lang3.StringUtils;

import java.io.File;

@ApplicationScoped
@Named("appProperties")
public class AppProperties extends DelegateAppProperties implements IAuthLibProperty {

    public static final String APP_PROPERTIES_FILE_NAME = "vendor-provisioning.properties";
    public static final boolean dockerDeploymentMode = isDockerDeploymentMode() ;

    /**
     * Instantiates a new base properties, using the default property file name.
     */
    public AppProperties() {
        super(getAppProperties());
    }

    private static String getDefaultPropertyFilePath() {

        StringBuffer keyFolderPathBufferString = new StringBuffer();

        keyFolderPathBufferString.append(System.getProperty(StringConstants.JBOSS_HOME_DIR)).append(File.separator)
                .append(StringConstants.WILD_FLY_BIN_DIR).append(File.separator).append(APP_PROPERTIES_FILE_NAME);

        return keyFolderPathBufferString.toString();
    }

    private static boolean isDockerDeploymentMode() {

        String useDockerDeploymentMode = System.getenv("DOCKER_DEPLOYMENT_MODE");

        return StringUtils.isNotBlank(useDockerDeploymentMode) && Boolean.parseBoolean(useDockerDeploymentMode);
    }

    private static AbstractAppApproperties getAppProperties() {
        if(dockerDeploymentMode) {
            return new BaseAppProperties();
        } else {
            return new FileAppProperties(getDefaultPropertyFilePath());
        }
    }

}
