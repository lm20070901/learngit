package com.tianwen.dcdp.common;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class EdwManageConstants {

	@Value("${upload.host_name}")
	public String HOST_NAME;
	
	
	@Value("${upload.parent.location}")
	public String STATIC_FILE_PATH ;
	
	@Value("${upload.child.content.location}")
	public String CONTENT_PATH;
	
	@Value("${upload.child.content.location}")
	public String CHILDTHUMIR;
	
	@Value("${upload.child.media.location}")
	public String CHILDMEDIADIR;
	
	@Value("${idx.parent.location}")
	public String STATIC_IDX_PATH ;
	
	@Value("${enroll.parent.location}")
	public String STATIC_ENROLL_PATH ;
	
}
