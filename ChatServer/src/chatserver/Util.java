/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chatserver;

import java.util.regex.Pattern;

/**
 *
 * @author Felipe
 */
public class Util {
    public static boolean ValidValueRegex(String value, String regex) {
        Pattern pattern = Pattern.compile(regex);
        return pattern.matcher(value).matches();
    }
}
