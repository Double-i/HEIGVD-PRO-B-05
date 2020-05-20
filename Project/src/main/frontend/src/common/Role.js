/**
 * Different roles of a user can have for a loan
 * @type {{owner: string, borrower: string}}
 */
export const ROLE = {
    owner: "owner",
    borrower: "borrower"
};

/**
 * Utility function to return the opposite role
 *
 * @param role the role  we would like the opposite
 * @returns {string}
 */
export const oppositeRole= (role) =>     {
    return role === ROLE.owner ? ROLE.borrower : ROLE.owner
};
/**
 * Utility function to check if the given role is owner
 * @param role
 * @returns {boolean}
 */
export const isOwner = (role)=> {
    return role === ROLE.owner
}
/**
 * Utility function to check if the given role is borrower
 * @param role
 * @returns {boolean}
 */
export const isBorrower = (role) =>{
    return !isOwner(role)
}