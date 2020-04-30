package iwb.service;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.zip.GZIPInputStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;

import iwb.adapter.metadata.MetadataImport;
import iwb.exception.IWBException;
import iwb.util.FtpUtil;
import iwb.util.HttpUtil;

@Service
public class VcsService {

	@Autowired
    ResourceLoader resourceLoader;

	public void importProjectMetadata(String url){
		long startTime = System.currentTimeMillis();
		String s = null;
		byte[] r = null;
		if(url.startsWith("http")) {
			if(!url.toLowerCase().endsWith(".zip"))
				s = HttpUtil.send(url, "");
			else 
				r = HttpUtil.send4bin(url, "", "GET", null);
		} else if(url.startsWith("ftp")) {
			if(!url.toLowerCase().endsWith(".zip"))
				s = FtpUtil.send(url);
			else 
				r = FtpUtil.send4bin(url);
		} else try{//file: always zip
			Resource resource = resourceLoader.getResource(url);//"classpath:projects/067e6162-3b6f-4ae2-a221-2470b63dff00.zip"
			File file = resource.getFile();
			r = new byte[(int)file.length()];
			InputStream input = resource.getInputStream();

			int bytesRead = 0, ix = 0;
			byte[] buffer = new byte[8192];
			while ((bytesRead = input.read(buffer, 0, 8192)) != -1) {
				//out.write(buffer, 0, bytesRead);
				for(int qi=0;qi<bytesRead;qi++)
					r[ix+qi]=buffer[qi];
				ix+=bytesRead;
			}
		}catch (Exception e) {
			throw new IWBException("framework", "Project", 0, null,
					"Read file Error", e);
		}
		if(r!=null) try {
			GZIPInputStream gis = new GZIPInputStream(new ByteArrayInputStream(r));
	        BufferedReader bf = new BufferedReader(new InputStreamReader(gis, "UTF-8"));
	        String outStr = "";
	        String line;
	        while ((line=bf.readLine())!=null) {
	          outStr += line;
	        }
	        s = outStr;
		}catch (IOException e) {
			throw new IWBException("framework", "Project", 0, null,
					"Decompress Error", e);
		}
		System.out.println("Downloaded Metadata from [" + url + "] in " + (System.currentTimeMillis()-startTime) + "ms, " + (s!=null ? s.length():0) + " bytes");
 
		new MetadataImport().fromJson(s);
		
	}
}
