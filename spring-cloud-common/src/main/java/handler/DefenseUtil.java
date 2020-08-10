package handler;

import org.springframework.web.util.HtmlUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**************************************************************
 ***       S  T  A  G  E    多模块依赖项目                    ***
 **************************************************************
 *                                                            *
 *         Project Name : base                                *
 *                                                            *
 *         File Name : DefenseUtil.java                       *
 *                                                            *
 *         Programmer : Mr.zhang                              *
 *                                                            *
 *         Start Date : 2020/7/24 17:28                       *
 *                                                            *
 *         Last Update : 2020/7/24 17:28                      *
 *                                                            *
 *------------------------------------------------------------*
 * 功能:                                                       *
 *   转码工具类                                                 *
 * - - - - - - - - - - - - - - - - - - - - - - - - - - - - -  */
public class DefenseUtil {

	public static boolean validateTool(String s){
		//转码
		String val = HtmlUtils.htmlUnescape(s).toLowerCase();
		String validate = "((select|insert|update|truncate)(\\s|`)|"+
									   "((\"|\'|\\s|\\)|/)(or|and)(\"|\'|\\s|\\(|/))|"+
									   "(drop\\s+(table|database))|"+
									   "((\\s|`)from(\\s|`))|"+
									   "order\\s+by|"+
									   "(\\s|\\))union(\\s|\\()|"+
									   "count\\(|"+
									   "exists(\\(|\\s)|"+
									   "delete |char |exec |copy |master |cmd |netlocal |declare |dir |chr |net |" +
									   "administrators|xp_cmdshell|limit|where|hex|substr|format )";
		String validate1 = ";,\n,\r,[,],{,},|,<,>,/*,',\",+,^,&amp,&lt,&gt,&quot,&#x27,&#x2F,%3b,%253b,%3c,%253c,%3e,%253e,%0b,%250b,%0a,%250a,%22,%2522,%23,%2523,%2f,%252f";
		String[] split = validate1.split(",");
		Pattern pattern = Pattern.compile(validate, Pattern.CASE_INSENSITIVE);
		Matcher mat = pattern.matcher(val);
		boolean find = mat.find();
		if(!find){
			for (String string : split) {
				if(val.contains(string)){
					System.out.println("特殊字符拦截："+val);
					return false;
				}
			}
			return true;
		}else{
			System.out.println("关键字拦截："+val);
			return false;
		}
	}
	public static void main(String[] args) {
		System.out.println( HtmlUtils.htmlUnescape(";"));
		System.out.println(DefenseUtil.validateTool(";asda"));
	}
}
