package com.thimu.grapevine.ui;

import java.util.regex.Pattern;

/**
 * The regular expression for ISBN-10 and ISBN-13
 *
 * @author Kĩthia Ngigĩ
 * @version 01.08.2020
 */
public class ISBNRegularExpression {

    Pattern ISBNRegularExpression = Pattern.compile("^(?:ISBN(?:-1[03])?:?●)?(?=[0-9X]{10}$|(?=(?:[0-9]+[-●]){3})[-●0-9X]{13}$|97[89][0-9]{10}$|(?=(?:[0-9]+[-●]){4})[-●0-9]{17}$)(?:97[89][-●]?)?[0-9]{1,5}[-●]?[0-9]+[-●]?[0-9]+[-●]?[0-9X]$");

    public String getRegex() { return ISBNRegularExpression.toString(); }
}
