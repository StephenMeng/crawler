package com.stephen.crawler.utils;

public class TransferCode {
	public static String unicode2String(String unicode) {

		StringBuffer string = new StringBuffer();

		String[] hex = unicode.split("\\\\u");

		for (int i = 1; i < hex.length; i++) {

			// 转换出每一个代码点
			int data = Integer.parseInt(hex[i], 16);

			// 追加成string
			string.append((char) data);
		}

		return string.toString();
	}

	public static String decodeUnicode(String theString) {

		char aChar = 0;

		int len = theString.length();

		StringBuffer outBuffer = new StringBuffer(len);

		for (int x = 0; x < len - 3;) {
			try {
				aChar = theString.charAt(x++);
			} catch (Exception e) {
			}

			if (aChar == '\\') {
				try {
					aChar = theString.charAt(x++);
				} catch (Exception e) {
				}
				if (aChar == 'u') {
					int value = 0;
					for (int i = 0; i < 4; i++) {
						try {
							aChar = theString.charAt(x++);
						} catch (Exception e) {
						}
						switch (aChar) {
						case '0':
						case '1':
						case '2':

						case '3':

						case '4':

						case '5':

						case '6':
						case '7':
						case '8':
						case '9':
							value = (value << 4) + aChar - '0';
							break;
						case 'a':
						case 'b':
						case 'c':
						case 'd':
						case 'e':
						case 'f':
							value = (value << 4) + 10 + aChar - 'a';
							break;
						case 'A':
						case 'B':
						case 'C':
						case 'D':
						case 'E':
						case 'F':
							value = (value << 4) + 10 + aChar - 'A';
							break;
						default:
						}

					}
					outBuffer.append((char) value);
				} else {
					if (aChar == 't')
						aChar = '\t';
					else if (aChar == 'r')
						aChar = '\r';

					else if (aChar == 'n')

						aChar = '\n';

					else if (aChar == 'f')

						aChar = '\f';

					outBuffer.append(aChar);

				}

			} else

				outBuffer.append(aChar);

		}

		return outBuffer.toString();

	}
}
