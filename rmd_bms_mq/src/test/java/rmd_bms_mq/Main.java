package rmd_bms_mq;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Main {
	
	public static void main(String args[]){
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		StringBuffer sb = new StringBuffer();
		sb.append("time:").append(dateFormat.format(new Date())).append(",");
		sb.append("direction:").append("output/input").append(",");
		sb.append("application:").append("WMS").append(",");
		sb.append("business:").append("order").append(",");
		sb.append("topic:").append("wms2oms").append(",");
		sb.append("tags:").append("orderAdd").append(",");
		sb.append("keys:").append("keystr").append(",");
		sb.append("body:").append("json").append(";");
		System.out.println(sb.toString());
	}
}
