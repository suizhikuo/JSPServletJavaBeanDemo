package net.gicp.suizhikuo.toolsbean;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MyTools {

	public static String changeHTML(String value) {
		value = value.replace("&", "&amp;");
		value = value.replace(" ", "&nbsp;");
		value = value.replace("<", "&lt;");
		value = value.replace(">", "&gt;");
		value = value.replace("\r\n", "<br>;");
		return value;
	}

	public static String ChangeTime(Date date) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		return format.format(date);
	}

	public static int strToint(String value) {
		int i = -1;
		if (value == null || value.equals(""))
			return i;
		try {
			i = Integer.parseInt(value);
		} catch (NumberFormatException e) {
			i = -1;
			e.printStackTrace();
		}
		return i;
	}
}
