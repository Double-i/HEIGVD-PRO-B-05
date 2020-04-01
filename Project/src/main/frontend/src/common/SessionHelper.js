import React from 'react'

/**
 *  TODO : remove if not used... impossible d'updater un state d'un composant depuis un autre composant
 */
export class SessionHelper {
    constructor(userSession, setUserSession) {
        this.userSession = userSession
        this.setUserSession = setUserSession
        console.log(setUserSession)
    }
    isUserLogin = () => {
        return (
            Object.keys(this.userSession).length > 0 &&
            this.userSession.constructor === Object
        )
    }
    isUserAdmin = () => {
        return this.isUserLogin() && this.userSession.admin
    }
    getUserName = () => {
        return this.userSession.username
    }
    getUserFirstName = () => {
        return this.userSession.firstname
    }
    getUserLastName = () => {
        return this.userSession.lastname
    }
    logout = () => {
        this.setUserSession({})
        sessionStorage.removeItem('user')
    }
    login = user => {
        this.setUserSession(user)
        sessionStorage.setItem('user', JSON.stringify(user))
    }

}


export const SessionContext = React.createContext({user:{}})
