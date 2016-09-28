/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.ac.leeds.ccg.andyt.generic.data;

import java.util.Iterator;
import java.util.TreeSet;
import uk.ac.leeds.ccg.andyt.generic.lang.Generic_StaticString;

/**
 *
 * @author geoagdt
 */
public class Generic_UKPostcode_Handler {

    /**
     * For storing a set of all NAA String combinations where: N is a numerical
     * integer from 0 to 9 inclusive; and, Both A are alphabetical characters
     * from _AtoZ_not_CIKMOV.
     */
    private static TreeSet<String> _NAA;
    /**
     * For storing (in lower case) all the letters of the Alphabet except C, I,
     * K, M, O, and V.
     */
    private static TreeSet<String> _AtoZ_not_CIKMOV;
    /**
     * For storing (in lower case) all the letters of the Alphabet except Q, V,
     * and X.
     */
    private static TreeSet<String> _AtoZ_not_QVX;
    /**
     * For storing (in lower case) all the letters of the Alphabet except I, J,
     * and Z.
     */
    private static TreeSet<String> _AtoZ_not_IJZ;
    /**
     * For storing (in lower case) the letters of the Alphabet A, B, C, D, E, F,
     * G, H, J, K, S, T, U, and W.
     */
    private static TreeSet<String> _ABCDEFGHJKSTUW;
    /**
     * For storing (in lower case) the letters of the Alphabet A, B, E, H, M, N,
     * P, R, V, W, X, and Y.
     */
    private static TreeSet<String> _ABEHMNPRVWXY;
    /**
     * For storing the digits 0 to 9 as Strings
     */
    private static TreeSet<String> _0to9;

    /**
     * Creates a new PostcodeChecker
     */
    public Generic_UKPostcode_Handler() {
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        new Generic_UKPostcode_Handler().run();
    }

    public void run() {
        String postcode;
        postcode = "LS2 9JT";
        Object[] postcodeForm;
        postcodeForm = getPostcodeForm(postcode);
        Integer postcodeFormType;
        postcodeFormType = (Integer) postcodeForm[0];
        String postcodeFormTypeDescription;
        postcodeFormTypeDescription = (String) postcodeForm[1];
        System.out.println("postcodeFormType " + postcodeFormType);
        System.out.println("postcodeFormTypeDescription " + postcodeFormTypeDescription);

    }

    /**
     * 
     * @param postcode
     * @return True iff postcode has a valid Postcode Form.
     */
    public static boolean isValidPostcodeForm(String postcode) {
        if (postcode == null) {
            return false;
        }
        while (postcode.contains(" ")) {
            postcode = postcode.replaceAll(" ", "");
        }
        int length = postcode.length();
        if (length < 5) {
            return false;
        }
        String firstPartPostcode;
        firstPartPostcode = postcode.substring(0, length - 3);
        String firstPartPostcodeType;
        firstPartPostcodeType = getFirstPartPostcodeType(firstPartPostcode);
        if (firstPartPostcodeType.equalsIgnoreCase("")) {
            return false;
        }
        String secondPartPostcode;
        secondPartPostcode = postcode.substring(length - 3, length);
        String secondPartPostcodeType;
        secondPartPostcodeType = getSecondPartPostcodeType(secondPartPostcode);
        if (secondPartPostcodeType.equalsIgnoreCase("")) {
            return false;
        }
        Object[] postcodeForm;
        postcodeForm = getPostcodeForm(postcode);
        if (((Integer) postcodeForm[0]) == 4) {
            return true;
        } else {
            return false;
        }
    }

    /**
     *
     * @param postcode
     * @return
     */
    public static Object[] getPostcodeForm(String postcode) {
        Object[] result;
        result = new Object[2];
        // Do any modification?
        while (postcode.contains(" ")) {
            postcode = postcode.replaceAll(" ", "");
        }
        int length = postcode.length();
        if (length < 5) {
            result[0] = 0;
            result[1] = "Length < 5";
            return result;
        }
        String firstPartPostcode;
        firstPartPostcode = postcode.substring(0, length - 3);
        String secondPartPostcode;
        secondPartPostcode = postcode.substring(length - 3, length);
        String firstPartPostcodeType;
        firstPartPostcodeType = getFirstPartPostcodeType(firstPartPostcode);
        String secondPartPostcodeType;
        secondPartPostcodeType = getSecondPartPostcodeType(secondPartPostcode);
        if (firstPartPostcodeType.equalsIgnoreCase("")) {
            if (secondPartPostcodeType.equalsIgnoreCase("NAA")) {
                result[0] = 1;
                result[1] = "Incorrect form of firstPartPostcode (" + firstPartPostcode + "). Incorrect form of secondPartPostcode (" + secondPartPostcode + ").";
                return result;
            }
            result[0] = 2;
            result[1] = "Incorrect form of firstPartPostcode (" + firstPartPostcode + "). Correct form of secondPartPostcode (" + secondPartPostcode + "): " + secondPartPostcodeType + ".";
            return result;
        }
        if (secondPartPostcodeType.equalsIgnoreCase("")) {
            result[0] = 3;
            result[1] = "Correct form of firstPartPostcode (" + firstPartPostcode + "): " + firstPartPostcodeType + ". Incorrect form of secondPartPostcode (" + secondPartPostcode + ").";
            return result;
        }
        result[0] = 4;
        result[1] = "Correct form of firstPartPostcode (" + firstPartPostcode + "): " + firstPartPostcodeType + ". Correct form of secondPartPostcode (" + secondPartPostcode + "): " + secondPartPostcodeType + ".";
        return result;
    }

    /**
     * Check SecondPart Postcode is in the format "NAA", "NA" or "N" where a
     * stands for an alphabetical character and n stands for a numerical
     * character.
     *
     * @param secondPartPostcode
     * @return
     */
    public static String getSecondPartPostcodeType(String secondPartPostcode) {
        if (secondPartPostcode.length() < 4) {
            if (secondPartPostcode.length() == 3) {
                String _0 = secondPartPostcode.substring(0, 1);
                String _1 = secondPartPostcode.substring(1, 2);
                String _2 = secondPartPostcode.substring(2, 3);
                if (get_0to9().contains(_0)
                        && get_AtoZ_not_CIKMOV().contains(_1)
                        && get_AtoZ_not_CIKMOV().contains(_2)) {
                    return "NAA";
                }
            } else {
                if (secondPartPostcode.length() == 2) {
                    String _0 = secondPartPostcode.substring(0, 1);
                    String _1 = secondPartPostcode.substring(1, 2);
                    if (get_0to9().contains(_0)
                            && get_AtoZ_not_CIKMOV().contains(_1)) {
                        return "NA";
                    }
                } else {
                    if (secondPartPostcode.length() == 1) {
                        String _0 = secondPartPostcode.substring(0, 1);
                        if (get_0to9().contains(_0)) {
                            return "N";
                        }
                    }
                }
            }
        }
        return "";
    }

    /**
     * If firstPartPostcode is not a valid first part for a postcode return an
     * empty string otherwise return "AANN", "AANA", "ANN", "ANA", "AAN", "AN"
     * respectively where A stands for an alphabetical character and N stands
     * for a numerical character.
     *
     * @param firstPartPostcode
     * @return
     */
    public static String getFirstPartPostcodeType(String firstPartPostcode) {
        // Resolve type from firstPartPostcode
//        String unresolvedMessage = firstPartPostcode + " is not recognised as a first part of a postcode";
        // Return a String or null (AANN, AANA, ANN, ANA, AAN, AN)
        if (firstPartPostcode.length() > 4 || firstPartPostcode.length() < 2) {
//            System.err.println(unresolvedMessage);
            return "";
        }
        if (firstPartPostcode.length() == 4) {
            String _0 = firstPartPostcode.substring(0, 1);
            String _1 = firstPartPostcode.substring(1, 2);
            String _2 = firstPartPostcode.substring(2, 3);
            String _3 = firstPartPostcode.substring(3, 4);
            if (get_AtoZ_not_QVX().contains(_0)
                    && get_AtoZ_not_IJZ().contains(_1)
                    && get_0to9().contains(_2)
                    && get_0to9().contains(_3)) {
                return "AANN";
            } else {
                if (get_AtoZ_not_QVX().contains(_0)
                        && get_AtoZ_not_IJZ().contains(_1)
                        && get_0to9().contains(_2)
                        && get_ABEHMNPRVWXY().contains(_3)) {
                    return "ANNA";
                } else {
//                    System.err.println(unresolvedMessage);
                    return "";
                }
            }
        }
        if (firstPartPostcode.length() == 3) {
            String _0 = firstPartPostcode.substring(0, 1);
            String _1 = firstPartPostcode.substring(1, 2);
            String _2 = firstPartPostcode.substring(2, 3);
            if (get_AtoZ_not_QVX().contains(_0)) {
                if (get_0to9().contains(_1)) {
                    if (get_0to9().contains(_2)) {
                        return "ANN";
                    } else {
                        if (get_ABCDEFGHJKSTUW().contains(_2)) {
                            return "ANA";
                        } else {
//                            System.err.println(unresolvedMessage);
                            return "";
                        }
                    }
                } else {
                    if (get_AtoZ_not_IJZ().contains(_1)) {
                        if (get_0to9().contains(_2)) {
                            return "AAN";
                        } else {
                            if (get_ABCDEFGHJKSTUW().contains(_2)) {
                                return "ANA";
                            } else {
//                                System.err.println(unresolvedMessage);
                                return "";
                            }
                        }
                    }
                }
            } else {
//                System.err.println(unresolvedMessage);
                return "";
            }
        }
        if (firstPartPostcode.length() == 2) {
            String _0 = firstPartPostcode.substring(0, 1);
            String _1 = firstPartPostcode.substring(1, 2);
            if (get_AtoZ_not_QVX().contains(_0)) {
                if (get_0to9().contains(_1)) {
                    return "AN";
                }
            }
        }
//        System.err.println(unresolvedMessage);
        return "";
    }

    private static void init_NAA() {
        _NAA = Generic_UKPostcode_Handler.getTreeSet_String_NAA();
    }

    private static TreeSet<String> get_NAA() {
        if (_NAA == null) {
            init_NAA();
        }
        return _NAA;
    }

    private static void init_AtoZ_not_QVX() {
        _AtoZ_not_QVX = getTreeSet_String_AtoZ_not_QVX();
    }

    private static TreeSet<String> get_AtoZ_not_QVX() {
        if (_AtoZ_not_QVX == null) {
            init_AtoZ_not_QVX();
        }
        return _AtoZ_not_QVX;
    }

    private static void init_AtoZ_not_IJZ() {
        _AtoZ_not_IJZ = getTreeSet_String_AtoZ_not_IJZ();
    }

    private static TreeSet<String> get_AtoZ_not_IJZ() {
        if (_AtoZ_not_IJZ == null) {
            init_AtoZ_not_IJZ();
        }
        return _AtoZ_not_IJZ;
    }

    private static void init_ABCDEFGHJKSTUW() {
        _ABCDEFGHJKSTUW = getTreeSet_String_ABCDEFGHJKSTUW();
    }

    private static TreeSet<String> get_ABCDEFGHJKSTUW() {
        if (_ABCDEFGHJKSTUW == null) {
            init_ABCDEFGHJKSTUW();
        }
        return _ABCDEFGHJKSTUW;
    }

    private static void init_ABEHMNPRVWXY() {
        _ABEHMNPRVWXY = getTreeSet_String_ABEHMNPRVWXY();
    }

    private static TreeSet<String> get_ABEHMNPRVWXY() {
        if (_ABEHMNPRVWXY == null) {
            init_ABEHMNPRVWXY();
        }
        return _ABEHMNPRVWXY;
    }

    private static void init_AtoZ_not_CIKMOV() {
        _AtoZ_not_CIKMOV = getTreeSet_String_AtoZ_not_CIKMOV();
    }

    private static TreeSet<String> get_AtoZ_not_CIKMOV() {
        if (_AtoZ_not_CIKMOV == null) {
            init_AtoZ_not_CIKMOV();
        }
        return _AtoZ_not_CIKMOV;
    }

    private static void init_0to9() {
        _0to9 = getTreeSet_String_0to9();
    }

    private static TreeSet<String> get_0to9() {
        if (_0to9 == null) {
            init_0to9();
        }
        return _0to9;
    }

    public static TreeSet<String> getTreeSet_String_0to9() {
        TreeSet<String> result = new TreeSet<String>();
        result.add("0");
        result.add("1");
        result.add("2");
        result.add("3");
        result.add("4");
        result.add("5");
        result.add("6");
        result.add("7");
        result.add("8");
        result.add("9");
        return result;
    }

    public static TreeSet<String> getTreeSet_String_ABCDEFGHJKSTUW() {
        TreeSet<String> result = new TreeSet<String>();
        result.add("A");
        result.add("B");
        result.add("C");
        result.add("D");
        result.add("E");
        result.add("F");
        result.add("G");
        result.add("H");
        result.add("J");
        result.add("K");
        result.add("S");
        result.add("T");
        result.add("U");
        result.add("W");
        return result;
    }

    public static TreeSet<String> getTreeSet_String_abcdefghjkstuw() {
        TreeSet<String> result = new TreeSet<String>();
        result.add("a");
        result.add("b");
        result.add("c");
        result.add("d");
        result.add("e");
        result.add("f");
        result.add("g");
        result.add("h");
        result.add("j");
        result.add("k");
        result.add("s");
        result.add("t");
        result.add("u");
        result.add("w");
        return result;
    }

    public static TreeSet<String> getTreeSet_String_ABEHMNPRVWXY() {
        TreeSet<String> result = new TreeSet<String>();
        result.add("A");
        result.add("B");
        result.add("E");
        result.add("H");
        result.add("M");
        result.add("N");
        result.add("P");
        result.add("R");
        result.add("V");
        result.add("W");
        result.add("X");
        result.add("Y");
        return result;
    }

    public static TreeSet<String> getTreeSet_String_abehmnprvwxy() {
        TreeSet<String> result = new TreeSet<String>();
        result.add("a");
        result.add("b");
        result.add("e");
        result.add("h");
        result.add("m");
        result.add("n");
        result.add("p");
        result.add("r");
        result.add("v");
        result.add("w");
        result.add("x");
        result.add("y");
        return result;
    }

    public static TreeSet<String> getTreeSet_String_AtoZ_not_IJZ() {
        TreeSet<String> result = Generic_StaticString.getUpperCaseStringAlphabet_TreeSet();
        result.remove("I");
        result.remove("J");
        result.remove("Z");
        return result;
    }

    public static TreeSet<String> getTreeSet_String_atoz_not_ijz() {
        TreeSet<String> result = Generic_StaticString.getLowerCaseStringAlphabet_TreeSet();
        result.remove("i");
        result.remove("j");
        result.remove("z");
        return result;
    }

    public static TreeSet<String> getTreeSet_String_AtoZ_not_QVX() {
        TreeSet<String> result = Generic_StaticString.getUpperCaseStringAlphabet_TreeSet();
        result.remove("Q");
        result.remove("V");
        result.remove("X");
        return result;
    }

    public static TreeSet<String> getTreeSet_String_atoz_not_qvx() {
        TreeSet<String> result = Generic_StaticString.getLowerCaseStringAlphabet_TreeSet();
        result.remove("q");
        result.remove("v");
        result.remove("x");
        return result;
    }

    public static TreeSet<String> getTreeSet_String_AtoZ_not_CIKMOV() {
        TreeSet<String> result = Generic_StaticString.getUpperCaseStringAlphabet_TreeSet();
        result.remove("C");
        result.remove("I");
        result.remove("K");
        result.remove("M");
        result.remove("O");
        result.remove("V");
        return result;
    }

    public static TreeSet<String> getTreeSet_String_atoz_not_cikmov() {
        TreeSet<String> result = Generic_StaticString.getLowerCaseStringAlphabet_TreeSet();
        result.remove("c");
        result.remove("i");
        result.remove("k");
        result.remove("m");
        result.remove("o");
        result.remove("v");
        return result;
    }

    public static TreeSet<String> getTreeSet_String_NAA() {
        TreeSet<String> result = new TreeSet<String>();
        Iterator ite1 = getTreeSet_String_AtoZ_not_CIKMOV().iterator();
        String a0;
        String a1;
        int n0;
        while (ite1.hasNext()) {
            a0 = (String) ite1.next();
            Iterator ite2 = getTreeSet_String_AtoZ_not_CIKMOV().iterator();
            while (ite2.hasNext()) {
                a1 = (String) ite2.next();
                for (n0 = 0; n0 < 10; n0++) {
                    result.add(Integer.toString(n0) + a0 + a1);
                }
            }
        }
        return result;
    }

    public static TreeSet<String> getTreeSet_String_Naa() {
        TreeSet<String> result = new TreeSet<String>();
        Iterator ite1 = getTreeSet_String_atoz_not_cikmov().iterator();
        String a0;
        String a1;
        int n0;
        while (ite1.hasNext()) {
            a0 = (String) ite1.next();
            Iterator ite2 = getTreeSet_String_atoz_not_cikmov().iterator();
            while (ite2.hasNext()) {
                a1 = (String) ite2.next();
                for (n0 = 0; n0 < 10; n0++) {
                    result.add(Integer.toString(n0) + a0 + a1);
                }
            }
        }
        return result;
    }

}
