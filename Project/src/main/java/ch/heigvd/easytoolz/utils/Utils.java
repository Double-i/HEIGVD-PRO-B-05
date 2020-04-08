package ch.heigvd.easytoolz.utils;

public class Utils {
    /**
     * transform any string in LIKE string for the query
     * for example :
     * s => 'henri'
     * return => '%henri%'
     * @param s a string
     * @return the string updated or null if s == null
     */
    public static String transformLike(String s){
        if(s == null)
            return null;
        return "%" + s + "%";
    }
}
