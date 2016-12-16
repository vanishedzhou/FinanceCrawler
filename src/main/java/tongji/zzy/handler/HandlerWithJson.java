package tongji.zzy.handler;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringReader;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.fasterxml.jackson.databind.SerializationConfig;
import com.fasterxml.jackson.databind.SerializationFeature;
import tongji.zzy.model.NewsInfo;
import tongji.zzy.model.TestModel;
import tongji.zzy.utils.CrawlerDateUtils;

public class HandlerWithJson {
	public static void main(String[] args) {
		NewsInfo info = new NewsInfo();
		Date date = CrawlerDateUtils.parseStringToDate("2016-12-15 15:53:00");
		info.setDate(date);
		info.setTitle("dateTest");

		System.out.println(convertJavaClassToJsonStream(info));
	}

	/**
	 * convert java class to JSon stream
	 * 
	 * @param data results in java class stereo
	 * @return JSon value as string
	 */
	public static String convertJavaClassToJsonStream(Object data) {
		ObjectMapper mapper = new ObjectMapper();
//		TimeZone timeZone = TimeZone.getTimeZone("UTC+8");
//		mapper.setTimeZone(timeZone);
//		mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
		String convertedJsonString = "";

		try {
			convertedJsonString = mapper.writeValueAsString(data);
			System.out.println(convertedJsonString);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}

		return convertedJsonString;
	}

	/**
	 * store the JSon stream to local file
	 * @param data
	 */
	public static void storeJsonStreamToFile(Object data) {
		//the converted JSon stream
		String result = convertJavaClassToJsonStream(data);
		// the file path
		String filePath = "f:\\CrawlerResult\\githubInfo.json";
		BufferedReader reader = null;
		BufferedWriter writer = null;

		try {
			File file = new File(filePath);
			if (!file.getParentFile().exists())
				file.getParentFile().mkdirs();
			reader = new BufferedReader(new StringReader(result));
			writer = new BufferedWriter(new FileWriter(file));
			char[] buf = new char[1024];
			int len;
			while ((len = reader.read(buf)) != -1) {
				writer.write(buf, 0, len);
			}
			writer.flush();
			reader.close();
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (writer != null) {
				try {
					writer.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

	}

}
