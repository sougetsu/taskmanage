package com.sdmx.framework.util;

/**
 * @author Yan Jingchao
 */
public class StringUtils {

    /**
     * 将字符串部分转换为大写
     *
     * @param original 原始字符串
     * @param from     从第几个字符开始
     * @param to       到第几个字符
     * @return 字符串部分转换为大写后的字符串
     */
    public static String toUpperCase(String original, int from, int to) {
        String start = "",
                middle,
                end;
        if (from != 0) {
            start = original.substring(0, from);
        }
        middle = original.substring(from, to).toUpperCase();
        end = original.substring(to, original.length());
        return start + middle + end;
    }

    /**
     * 将字符串部分转换为小写
     *
     * @param original 原始字符串
     * @param from     从第几个字符开始
     * @param to       到第几个字符
     * @return 字符串部分转换为小写后的字符串
     */
    public static String toLowerCase(String original, int from, int to) {
        String start = "",
                middle,
                end;
        if (from != 0) {
            start = original.substring(0, from);
        }
        middle = original.substring(from, to).toLowerCase();
        end = original.substring(to, original.length());
        return start + middle + end;
    }

    /**
     * 首字母转换为大写
     *
     * @param original 原始字符串
     * @return 首字母转换为大写后的字符串
     */
    public static String firstLetterToUpper(String original) {
        return toUpperCase(original, 0, 1);
    }


    /**
     * 首字符转换为小写
     *
     * @param original 原始字符串
     * @return 首字母转换为小写后的字符串
     */
    public static String firstLetterToLower(String original) {
        return toLowerCase(original, 0, 1);
    }
    
    public static boolean isHave(String[] strs,String s){
    	  /*此方法有两个参数，第一个是要查找的字符串数组，第二个是要查找的字符或字符串
    	   * */
    	  for(int i=0;i<strs.length;i++){
    	   if(strs[i].indexOf(s)!=-1){//循环查找字符串数组中的每个字符串中是否包含所有查找的内容
    	    return true;//查找到了就返回真，不在继续查询
    	   }
    	  }
    	  return false;//没找到返回false
    	 }
}
