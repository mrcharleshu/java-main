package com.charles.pinyin_query;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.CharUtils;
import org.apache.commons.lang3.StringUtils;

/**
 * 把汉字转换为拼音储存,用来拼音搜索,新增和更新时分别计算
 */
public final class PinyinParser {

    private final HanyuPinyinOutputFormat formatter = new HanyuPinyinOutputFormat();
    /**
     * 原生的汉字字符串
     */
    private final String hanziStr;
    /**
     * 汉字的长拼音
     */
    private final String hanziCpy;
    /**
     * 汉字短拼音
     */
    private final String hanziDpy;

    public PinyinParser(String hanziStr) {
        formatter.setCaseType(HanyuPinyinCaseType.LOWERCASE);
        formatter.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
        this.hanziStr = hanziStr;
        this.hanziCpy = this.toPinyin(ParseType.LONG);
        this.hanziDpy = this.toPinyin(ParseType.SHORT);
    }

    private enum ParseType {
        /**
         * 长拼音转换
         */
        LONG {
            @Override
            public String parse(String pinyin) {
                return pinyin;
            }
        },
        /**
         * 短拼音转换
         */
        SHORT {
            @Override
            public String parse(String pinyin) {
                return StringUtils.isBlank(pinyin) ? null : CharUtils.toString(pinyin.charAt(0));
            }
        };

        public abstract String parse(String pinyin);
    }

    private String toPinyin(ParseType parseType) {
        StringBuilder sb = new StringBuilder();
        int str_len = getHanziStr().length();
        String pinyin;
        for (int i = 0; i < str_len; ++i) {
            pinyin = toHanyuPinyin(getHanziStr().charAt(i));
            if (null == pinyin) {
                sb.append(getHanziStr().charAt(i));//如果不是汉字，则保持原样
            } else {
                sb.append(parseType.parse(pinyin));
                //if (i < str_len - 1 && null != toHanyuPinyin(hanziStr.charAt(i + 1))) { sb.append("\'"); }//分词
            }
        }
        return sb.toString().trim();
    }

    /**
     * 转换单个中文字符(如果c不是汉字,返回null,多音字则取第一个值)
     * @param c 单个中文字符
     * @return
     */
    private String toHanyuPinyin(char c) {
        String[] pinyin = {};
        try {
            pinyin = PinyinHelper.toHanyuPinyinStringArray(c, formatter);
        } catch (BadHanyuPinyinOutputFormatCombination e) {
            e.printStackTrace();
        }
        return ArrayUtils.isEmpty(pinyin) ? null : pinyin[0];
    }

    public String getHanziStr() {
        return hanziStr;
    }

    public String getHanziCpy() {
        return hanziCpy;
    }

    public String getHanziDpy() {
        return hanziDpy;
    }

    public static void main(String[] args) {
        PinyinParser pp1 = new PinyinParser("哈哈，我爱 Coding");
        System.out.println(pp1.getHanziCpy());
        System.out.println(pp1.getHanziDpy());

        PinyinParser pp2 = new PinyinParser("我是程序员");
        System.out.println(pp2.getHanziCpy());
        System.out.println(pp2.getHanziDpy());
    }
}
