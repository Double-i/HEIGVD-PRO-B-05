export const ROLE = {
    owner: "owner",
    borrower: "borrower"
};

export const oppositeRole= (role) =>     {
    return role === ROLE.owner ? ROLE.borrower : ROLE.owner
};