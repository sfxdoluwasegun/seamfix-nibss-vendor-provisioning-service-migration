package com.seamfix.bioweb.microservices.vendor.provision.utils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.seamfix.bioweb.microservices.vendor.provision.constants.StringConstants;


public final class Utils {
	
	private static final char ALPHABETS[] = "ABCDEFGHJKLMNPQRSTUVWXYZ".toCharArray();
    private static final char DIGITS[] = "123456789".toCharArray();
    private static final char ALPHANUMERIC[] = "ABCDEFGHJKLMNPQRSTUVWXYZ123456789".toCharArray();
    private static final Logger logger = LoggerFactory.getLogger(Utils.class);
    
    private Utils() {}
    
    public static String generateToken(int width) {
        return generate(ALPHANUMERIC, width);
    }

    public static String generateAlphabets(int width) {
        return generate(ALPHABETS, width);
    }

    public static String generateDigits(int width) {
        return generate(DIGITS, width);
    }

    public static String generate(char[] characters, int width) {
        int initSize = 0;
        int characterSize = characters.length - 1;
        StringBuilder buf = new StringBuilder();
        for (int i = 0; i < width; i++) {
            int index = initSize + (int) (Math.random() * (characterSize - initSize + 1));
            buf.append(characters[index]);
        }
        return buf.toString();
    }
    
    public static File checkForTestEnvironment(String testFile) {
		StringBuffer keyFolderPathBufferString = new StringBuffer();
		keyFolderPathBufferString.append(System.getProperty(StringConstants.JBOSS_HOME_DIR)).append(File.separator)
				.append(StringConstants.WILD_FLY_BIN_DIR).append(File.separator);
		
		File propsFile = new File(keyFolderPathBufferString.append(testFile).toString());
		if(!propsFile.exists()) {
			propsFile = generateDatFiles(testFile);	
		}
		return propsFile;
	}
	
	public static File generateDatFiles(String fileName) {
		InputStream inputStream = Utils.class.getResourceAsStream("/"+fileName);
		OutputStream outStream = null;
		File targetFile = null;
        if(null != inputStream)
        {
        	byte[] buffer;
			try {
				buffer = new byte[inputStream.available()];
				inputStream.read(buffer);
				targetFile = new File(fileName);
				outStream =Files.newOutputStream(Paths.get(targetFile.getAbsolutePath()));
	            outStream.write(buffer);
	            
	            if(!targetFile.exists()){
	            	targetFile.createNewFile();
	            }
	            
			} catch (IOException e) {
				logger.error("Exception ", e);
			}finally {
				try {
					inputStream.close();
					outStream.close();
				} catch (IOException e) {
					logger.error("Exception ", e);
				}
			}
        }
        
        return targetFile;
	}

}
