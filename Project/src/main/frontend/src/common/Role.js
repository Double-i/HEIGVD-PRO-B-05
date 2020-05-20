
export const ROLE = {
    owner: "owner",
    borrower: "borrower"
};

export const oppositeRole= (role) =>     {
    return role === ROLE.owner ? ROLE.borrower : ROLE.owner
};
export const isOwner = (role)=> {
    return role === ROLE.owner
}
export const isBorrower = (role) =>{
    return !isOwner(role)
}