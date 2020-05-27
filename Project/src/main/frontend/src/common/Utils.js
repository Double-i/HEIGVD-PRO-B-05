/**
 * This function is used to format string the same way it's possible in Java or in C with
 * respectively String.format("Here is % example", "the") or printf("Here is % example", "the")
 *
 * based on https://stackoverflow.com/questions/610406/javascript-equivalent-to-printf-string-format/4673436#4673436
 * we use it as a function since extending native prototype is considered anti-pattern for some.
 *  (https://developer.mozilla.org/en-US/docs/Web/JavaScript/Inheritance_and_the_prototype_chain#Bad_practice_Extension_of_native_prototypes)
 *
 * The string to format should integrate placeholder like {0} which will be replace by the args
 * @example formatString("it's a {0} test {1} {0}, "big" , "or not ") => it's a big test or not big
 *
 * @param stringToFormat the string to format
 * @param args list of the replacement
 * @returns {String} the formatted string
 */
export function formatString(stringToFormat, ...args){
    return stringToFormat.replace(/{(\d+)}/g, function(match, number) {
        return typeof args[number] != 'undefined'
            ? args[number]
            : match
            ;
    });
}