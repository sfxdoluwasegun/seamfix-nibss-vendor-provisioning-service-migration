package com.seamfix.bioweb.microservices.vendor.provision.entities.enums;

import java.util.UUID;

/**
 *
 * @author jaohar
 */
@SuppressWarnings("PMD")
public enum SettingsEnum {
	
    JWT_KEY("JWT-KEY", UUID.randomUUID().toString(), "The JWT Key for authentication"),
    OTP_REQUIRED("OTP-REQUIRED", "false", "Used to specify whether otp is required during client login"),
    LOGIN_OFFLINE("LOGIN-OFFLINE", "false", "Determines whether an agent can login offline"),
    LOGIN_OFFLINE_VALIDATION_TYPE("LOGIN-OFFLINE-VALIDATION-TYPE", "CACHED", "Can either be CACHED OR SHORTCODE"),
    AIRTIME_SALES_MANDATORY("AIRTIME-SALES-MANDATORY", "false", "Informs client on the availability of airtime sales"),
    AIRTIME_SALES_URL("AIRTIME-SALES-URL", "http://seamfix.com/", "URL to airtime sales"),
    AVAILABLE_USE_CASE("AVAILABLE-USE-CASE", "NS,NM,SS,RR,AR,BU", "This is used to specify available use case exposed to client"),
    CLIENT_ACTIVITY_LOG_BATCH_SIZE("CLIENT-ACTIVITY-LOG-BATCH-SIZE", "20", "Determines the batch size of client activity log sent to the server from the client"),
    MAXIMUM_MSISDN_ALLOWED_PER_REGISTRATION("MAXIMUM-MSISDN-ALLOWED-PER-REGISTRATION", "5", "Specifies the maximum number of msisdn that can be registered per registration process"),
    AGILITY_SIM_SERIAL_VALID_STATUS_KEYS("AGILITY-SIM-SERIAL-VALID-STATUS-KEYS", "F,E", "Comma separated keys used to specify valid sim serial keys retrieved from agility"),
    AGL_BIO_UPDATE_PLATINUM_CODES("AGL-BIO-UPDATE-PLATINUM-CODES", "PLATM", "Comma separated codes used to specify the category codes valid for registering platinum customers"),
    AGL_BIO_UPDATE_MNP_CODES("AGL-BIO-UPDATE-MNP-CODES", "Port-In", "Comma separated codes used to specify the category codes valid for registering MNP customers"),
    AGL_BIO_UPDATE_POSTPAID_CODES("AGL-BIO-UPDATE-POSTPAID-CODES", "Y", "Comma separated codes used to specify the category codes valid for registering Postpaid customers"),
    SIMSWAP_DATE_VALIDATION("SIMSWAP-DATE-VALIDATION", "true", "Indicates if sim swap date will be compared against activation date"),
    SIMSWAP_TIME_FRAME("SIMSWAP-TIME-FRAME", "7", "Minimum no of days allowed between sim activation and sim swap"),
    ENABLE_VAS_MODULE("ENABLE-VAS-MODULE", "true", "Determines whether or not the VAS module will be enabled on the clients"),
    MINIMUM_ACCEPTABLE_CHARACTER("MINIMUM-ACCEPTABLE-CHARACTER", "2", "This is the minimum number of allowed characters for name field during foreigner's registration"),
    NOTIFICATION_CHECKER_INTERVAL("NOTIFICATION-CHECKER-INTERVAL", "60", "Time interval, in seconds, between which client checks for notifications from server"),
    AGENT_BIOSYNC_INTERVAL("AGENT-BIOSYNC-INTERVAL", "60", "Time interval, in seconds, between which client sends available agents' biometrics to server"),
    AUDIT_XML_SYNC_INTERVAL("AUDIT-XML-SYNC-INTERVAL", "1800", "Time interval, in seconds, between which client sends available audit xml files to SFTP server"),
    THRESHOLD_CHECKER_INTERVAL("THRESHOLD-CHECKER-INTERVAL", "3600", "Time interval, in seconds, between which client checks server for threshold updates"),
    ACTIVATION_CHECKER_INTERVAL("ACTIVATION-CHECKER-INTERVAL", "1200", "Time interval, in seconds, between which client checks server for activation status of msisdns"),
    SYNCHRONIZER_INTERVAL("SYNCHRONIZER-INTERVAL", "2", "Time interval, in seconds, between which client sends available sync files to SFTP server for processing"),
    HARMONIZER_INTERVAL("HARMONIZER-INTERVAL", "120", "Time interval, in seconds, between which client checks for status of each registration using unique ID"),
    SETTINGS_INTERVAL("SETTINGS_INTERVAL", "300", "Time interval, in seconds, between which client checks server for settings"),
    CLIENT_LOCATION_CHECK_POPUP_INTERVAL("CLIENT-LOCATION-CHECK-POPUP-INTERVAL", "3600", "controls the interval at which device location status is checked"),
    UPDATE_DEVICE_ID_INTERVAL("UPDATE-DEVICE-ID-INTERVAL", "600", "the interval at which the thread that updates device id at the backend is called. this call is made iff device id has not been updated before"),
    BLACKLIST_CHECKER_INTERVAL("BLACKLIST-CHECKER-INTERVAL", "600", "Time interval, in seconds, between which client checks server for kit and agent blacklist status"),
    OFFLINE_RESPONSE_SHORTCODE("OFFLINE-RESPONSE-SHORTCODE", "5034", "The response shortcode used during offline login"),
    OFFLINE_REQUEST_SHORTCODE("OFFLINE-REQUEST-SHORTCODE", "799", "The shortcode called during offline login"),
    EMAIL_NOTIFICATION_AUTHENTICATOR_FROM_EMAIL("BS-EMAIL-NOTIFICATION-AUTHENTICATOR-FROM-EMAIL", "noreply@kyc.mtnnigeria.net", "Sender's email address"),
    EMAIL_NOTIFICATION_AUTHENTICATOR_USER_NAME("BS-EMAIL-NOTIFICATION-AUTHENTICATOR-USERNAME", "bm@kyc.mtnnigeria.net", "Sender's user name"),
    EMAIL_NOTIFICATION_AUTHENTICATOR_PASSWORD("BS-EMAIL-NOTIFICATION-AUTHENTICATOR-PASSWORD", "openminds", "Sender's password"),
    EMAIL_NOTIFICATION_AUTHENTICATOR_HOST_NAME("BS-EMAIL-NOTIFICATION-AUTHENTICATOR-HOST-NAME", "10.1.224.7", "The hostname of the outgoing mail server."),
    EMAIL_NOTIFICATION_AUTHENTICATOR_HOST_PORT("BS-EMAIL-NOTIFICATION-AUTHENTICATOR-HOST-PORT", "25", "The port number of the outgoing mail server"),
    CAC_ROLE("CAC-ROLE", "CAC", "The role for CAC users. This should be comma separated should there be need to send email to multiple roles"),
    SIMROP_URL("SIMROP-URL", "https://seamfix.com", "SIMROP url"),
    SAME_LOCATION_RANGE("SAME-LOCATION-RANGE", "30", "Distance in meters that is considered as the same street/location"),
    GEO_FENCE_RADIUS("GEO-FENCE-RADIUS", "50", "Geofence radius in meters, outside which a notification will be sent."),
    GET_GEO_FENCE_LOG_BY_KIT("GET-GEO-FENCE-LOG-BY-KIT", "true", "Pull geofence log by a kit, when getting address"),
    BS_GOOGLE_MAP_API_KEY("BS-GOOGLE-MAP-API-KEY", "AIzaSyCURcOfGBLOEPpV5tCXqwZALoZUcFv_Lks", "This is the google map api key for reverse geocode to retrieve address"),
    OSM_REVERSE_GEOCODING_URL("OSM-REVERSE-GEOCODING-URL", "http://nominatim.openstreetmap.org/reverse?format=json&addressdetails=1&lat=", "OSM url for reverse geocoding"),
    PROXY_IP_FOR_GEOCODING("PROXY-IP-FOR-GEOCODING", "10.1.224.41", "The proxy IP used for reverse geocoding."),
    PROXY_PORT_FOR_GEOCODING("PROXY-PORT-FOR-GEOCODING", "8080", "The proxy port used for reverse geocoding."),
    ACTIVATION_STATUS_MAPPER("ACTIVATION-STATUS-MAPPER", "ACTIVATED:ACTIVATED,FAILED_ACTIVATION:FAILEDACTIVATION,FAILED_VALIDATION:FAILEDVALIDATION", "This is used to map seamfix enum against agility activation status code. Agility code may change but seamfix should not because seamfix code represents an enum in code. Entry format is; SEAMFIX_CODE:AGILITY_CODE"),
    DYA_AVAILABLE_USE_CASE("DYA-USE-CASE", "NS,NM,RR,AR,BU", "This is used to specify available use case to perform request of DYA in client"),
    DYA_ACCOUNT_TYPE("DYA-ACCOUNT-TYPE", "[Select Yellow Account Type],DYA", "This is the different yellow account types"),
    ALLOW_CLIENT_TAGGING("ALLOW-CLIENT-TAGGING", "true", "Determine whether tagging and re-tagging activities can be performed on the clients"),
    LOCATION_MANDATORY("LOCATION-MANDATORY", "true", "Determines whether location should be sent for every registration done on the clients"),
    AVAILABLE_VTU_TRANSACTION_TYPE("AVAILABLE-VTU-TRANSACTION-TYPE", "AIRTIME,DATA", "This is a comma separated enum values (AIRTIME,DATA) indicating the vtu vending transaction types that will be made available to the client"),
    VTU_VENDING_MANDATORY("VTU-VENDING-MANDATORY", "true", "flag to determine whether vtu vending is mandatory after registration on th client"),
    VENDING_SEQUENCE("VENDING-SEQUENCE", "1", "MTN vending sequence for making calls to vending service"),
    VTU_VEND_USERNAME("VTU-VEND-USERNAME", "", "MTN VTU vending username"),
    VTU_VEND_PASSWORD("VTU-VEND-PASSWORD", "", "MTN VTU vending password"),
    VTU_SERVICE_URL("VTU-SERVICE-URL", "http://localhost:7080/mock-vas-services/mock-host-if-service", "MTN VTU service URL"),
    MTN_DATA_TARIFF_TYPE_ID("MTN-DATA-TYPE-ID", "9", "Tariff type id parameter for MTN data"),
    MTN_AIRTIME_TARIFF_TYPE_ID("MTN-AIRTIME-TYPE-ID", "1", "Tariff type id parameter for MTN airtime"),
    CIU_USE_CONSECUTIVE_CONSONANT_FOR_NAME_FIELDS("CIU-USE-CONSECUTIVE-CONSONANT-FOR-NAME-FIELDS", "false", "Indicates if consecutive consonants are allowed in name fields for customer information update across all the channels"),
    CIU_USE_MINIMUM_CHARACTER_FOR_NAME_FIELDS("CIU-USE-MINIMUM-CHARACTER-FOR-NAME-FIELDS", "false", "Indicates if CIU channels should use minumum characters defined in (MINIMUM_ACCEPTABLE_CHARACTER) setting for name fields"),
    OTA_RESTART_INTERVAL("OTA-RESTART-INTERVAL", "600", "Time interval, in seconds after which BioSmart restarts on successful download of an updated version"),
    POSTPONE_OTA_RESTART_LIMIT("POSTPONE-OTA-RESTART-LIMIT", "2", "Number of times one can postpone the restart of BioSmart on successful download of an updated version"),
    CLIENT_DATABASE_SYNC_CLEANER_INTERVAL("CLIENT-DATABASE-SYNC-CLEANER-INTERVAL", "21600", "Time interval, in seconds after which BioSmart runs a job to clear the database of synchronized and harmonized records"),
    RE_REGISTRATION_RULE_DAYS_LIMIT("RE-REGISTRATION-RULE-DAYS-LIMIT", "90", "The number of days required before a number can be re-registered"),
    CHECK_TEMP_SUBSCRIBER_DETAILS("CHECK-TEMP-SUBSCRIBER-DETAILS", "true", "determines whether to check temp db for subscriber details"),
    CHECK_TEMP_SUBSCRIBER_VAL_STATUS("CHECK-TEMP-SUBSCRIBER-VAL-STATUS", "true", "determines whether to check temp subscriber's validation status during rereg"),
    CHECK_TEMP_SUBSCRIBER_BIO_STATUS("CHECK-TEMP-SUBSCRIBER-BIO-STATUS", "true", "determines whether to check temp subscriber's biometrics update status during rereg"),
    SELF_SERVICE_TRANSACTION_ID_SCHEMA_LENGTH("SELF-SERVICE-TRANSACTION-ID-SCHEMA-LENGTH", "10", "Length for the generated transaction id suffix"),
    SELF_SERVICE_DEMOGRAPHIC_THRESHOLD_FILE_NAME("SELF-SERVICE-DEMOGRAPHIC-THRESHOLD-FILE-NAME", "demographic-threshold-3.xml", " Name of Demographics Threshold file for Self Service "),
    SELF_SERVICE_AGILITY_POOL_CODE("SELF-SERVICE-AGILITY-POOL-CODE", "NC26M", "Self service agility pool code"),
    SELF_SERVICE_AGILITY_UPDATE_INITIAL_DURATION("SELF-SERVICE-AGILITY-UPDATE-INITIAL-DURATION", "180000", "3mins In milliseconds"),
    SELF_SERVICE_TRANSACTION_ID_PREFIX("SELF-SERVICE-TRANSACTION-ID-PREFIX", "CUI", "prefix for autogenerated self service transaction id"),
    SELF_SERVICE_TRANSACTION_ID_SCHEMA_TYPE("SELF-SERVICE-TRANSACTION-ID-SCHEMA-TYPE", "ALPHANUMERIC", "Schema type for self service transaction id suffix e.g DIGITS, ALPHABETS, ALPHANUMERIC"),
    SELF_SERVICE_AGILITY_UPDATE_INTERVAL_DURATION("SELF-SERVICE-AGILITY-UPDATE-INTERVAL-DURATION", "180000", "3mins In milliseconds"),
    SELF_SERVICE_EXPIRATION_DURATION("SELF-SERVICE-EXPIRATION-DURATION", "7", "7 days "),
    SELF_SERVICE_SUCCESSFUL_API_RESPONSE_MESSAGE("SELF-SERVICE-SUCCESSFUL-API-RESPONSE-MESSAGE", "Your details have been successfully captured", "This is the message returned to third parties when subscriber detail is successfully saved"),
    SELF_SERVICE_CAPTURE_MESSAGE("SELF-SERVICE-CAPTURE-MESSAGE", "Your transaction ID is {transactionId}. Please visit the nearest MTN store to complete your registration with a valid ID card, your SIM and transaction ID.", "Message sent to user after successful capture of information. Parameter: transactionId"),
    PERFORM_SELF_SERVICE_AGILITY_VALIDATION("PERFORM-SELF-SERVICE-AGILITY-VALIDATION", "true", "Enable agility customer information validation"),
    CHANGE_PASSWORD_ON_KYC_MANAGER("CHANGE-PASSWORD-ON-KYC-MANAGER", "false", "Password change on kyc manager should be deprecated on device id go-live"),
    MIN_MSISDN_LENGTH("MIN-MSISDN-LENGTH", "6", "Min length for msisdn"),
    MAX_MSISDN_LENGTH("MAX-MSISDN-LENGTH", "11", "Max length for msisdn"),
    MIN_SERIAL_LENGTH("MIN-SERIAL-LENGTH", "20", "Min length for serial"),
    MAX_SERIAL_LENGTH("MAX-SERIAL-LENGTH", "20", "Max length for serial"),
    MAX_PUK("MAX-PUK", "8", "Max length for PUK"),
    MIN_PUK("MIN-PUK", "8", "Min length for PUK"),
    PASSPORT_VALIDATION_SETTING_ENABLED("SELF-SERVICE-IDCARD-STATUS-CHECK-ENABLED", "true", "Enables the check for id card"),
    REPLAY_FAILED_SUBSCRIBER_AGILITY_PUSH("REPLAY-FAILED-SUBSCRIBER-AGILITY-PUSH", "false", "Determine whether to retry pushing failed subscriber detail to agility."),
    FAILED_HARMONIZATION_RESYNC_INTERVAL("FAILED-HARMONIZATION-RESYNC-INTERVAL", "21600", "The number of seconds to wait after the failure of harmonization of a record before it is resynced"),
    REPLAY_PENDING_SUBSCRIBER_AGILITY_PUSH("REPLAY-PENDING-SUBSCRIBER-AGILITY-PUSH", "true", "Determine whether to retry pushing pending subscriber detail to agility."),
    ATTEMPT_GEOTRACKER_DOWNLOAD("ATTEMPT-GEOTRACKER-DOWNLOAD", "true", "Determines whether or not the Biosmart clients should download the geotracker application"),
    GEOTRACKER_DOWNLOADER_INTERVAL("GEOTRACKER-DOWNLOADER-INTERVAL", "21600", "Interval at which the Biosmart clients check for the existence of Geotracker app and download if not available"),
    GEOTRACKER_HEARTBEAT_INTERVAL("GEOTRACKER-HEARTBEAT-INTERVAL", "600", "Time interval in which client calls heartbeat service set to 10 mins"),
    GEOTRACKER_SETTINGS_INTERVAL("GEOTRACKER-SETTINGS-INTERVAL", "300", "Time interval in which client calls for settings service set to 5 mins"),
    GEOTRACKER_OTA_INTERVAL("GEOTRACKER-OTA-INTERVAL", "180", "Time interval in which client checks for geotracker OTA update set to 3 mins"),
    GEOTRACKER_CLIENT_LOCATION_MANDATORY("GEOTRACKER-CLIENT-LOCATION-MANDATORY", "false", "Determines whether or not geotracker prompts the system to enable location service"),
    SELF_SERVICE_TRANSACTION_ID_LENGTH("SELF-SERVICE-TRANSACTION-ID-LENGTH", "10", "Length of Transaction Id that would be generated"),
    HEART_BEAT_MODE("HEART-BEAT-MODE", "BIOSMART", "This is the heart beat mode configured. It's either BIOSMART or GEOTRACKER "),
    ASSET_STATUS_TRANSLATION("ASSET-STATUS-TRANSLATION", "A:Active,Q:Quarantine,F:Free,G:Blocked,R:Reserved,P:Pending,E:Paired,O:Port out", "This mapping of agility additionalinfo type value to more intiutive values"),
    CACHE_CLIENT_BIOMETRIC_DATA("CACHE-CLIENT-BIOMETRIC-DATA", "false", "Determine whether or not biometric data can be cached locally on the Biosmart client database"),
    UPDATE_HEARTBEAT_STATUS_RETAG("UPDATE-HEARTBEAT-STATUS-RETAG", "true", "Determines whether to update heartbeat status table (with new tag) during retag"),
    CLIENT_SELF_TAG_REQUEST_INTERVAL("CLIENT-SELF-TAG-REQUEST-INTERVAL", "600", "Interval in seconds, at which biosmart client should send self tagging request"),
    SELF_SERVICE_INTRUSION_CACHE_DURATION_INTERVAL("SELF-SERVICE-INTRUSION-CACHE-DURATION-INTERVAL", "3600", "Interval in seconds, value is stored in mem-cache"),
    UPDATE_KM_USER_ON_LOGIN("UPDATE-KM-USER-ON-LOGIN", "TRUE", "This determines whether to update km user on client login"),
    CREATE_ACCESS_LOG_ON_LOGIN("CREATE-ACCESS-LOG-ON-LOGIN", "TRUE", "This determinse whether to create access log on login"),
    CREATE_CLIENT_ACTIVITY_LOG("CREATE-CLIENT-ACTIVITY-LOG", "FALSE", "This determines whether to create client activity logs"),
    LOG_UPDATE_DEVICE_ID_REQUESTS("LOG-UPDATE-DEVICE-ID-REQUESTS", "TRUE", "Boolean flag to determine whether to log update device id requests to file"),
    UPDATE_DEVICE_ID_ON_FETCH_TAG("UPDATE-DEVICE-ID-ON-FETCH-TAG", "TRUE", "Boolean flag to determine whether to update device id when a client calls for tag fetch"),
    
    
    ;

    private SettingsEnum(String value, String description) {
        this.name = name();
        this.value = value;
        this.description = description;
    }	
    
    private SettingsEnum(String name, String value, String description) {
        this.name = name;
        this.value = value;
        this.description = description;
    }

    private String name;
    private String value;
    private String description;

    public String getName() {
        return name;
    }

    public String getValue() {
        return value;
    }

    public String getDescription() {
        return description;
    }

}
