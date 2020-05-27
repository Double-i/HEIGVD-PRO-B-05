/**
 * This JS object is used to store message displayed when there are mistake in a form
 * It could be used later to translate validation messages in other language.
 * @type {{min: string, same: string, max: string, usernameRegex: string, requis: string}}
 */
export const VALIDATION_MSG = {
    min : "Ce champ requiert au moins {0} caractère(s)",
    max : "Ce champ requiert au maximum {0} caractère(s)",
    requis: "Ce champ est requis",
    same: "Ce champ doit être identique à {0}",
    usernameRegex: "Ce champ ne peut contenir que des caractères alphanumériques ainsi que . - '",
    email: "Ce champ doit être une adresse e-mail valide : example@eztool.ch"
}

