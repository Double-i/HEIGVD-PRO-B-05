import React from 'react'

/**
 * Class wrapping the managment of the user session
 */
export class SessionHelper {
    /**
     * 
     * @param {Object} userSession 
     * @param {React.Dispatcher} setUserSession 
     */
    constructor(userSession, setUserSession) {
        this.userSession = userSession
        this.setUserSession = setUserSession
    }
    /**
     * check if the user is connect by checking if the userSession Object 
     *  has attribute
     */
    isUserLogin = () => {
        return (
            (Object.keys(this.userSession).length > 0 &&
            this.userSession.constructor === Object)
        )
    }
    /**
     * Return true if the user is logged and admin
     */
    isUserAdmin = () => {
        return this.isUserLogin() && this.userSession.admin
    }
    /**
     * Return the username/pseudo of the user stored in userSession Object
     */
    getUserName = () => {
        return this.userSession.username
    }
    /**
     * Return the firstname of the user stored in userSession Object
     */
    getUserFirstName = () => {
        return this.userSession.firstname
    }
    /**
     * Return the lastname of the user stored in userSession Object
     */
    getUserLastName = () => {
        return this.userSession.lastname
    }
    /**
     * Log the user out by calling the react dispatcher and by removing the session storage cookie
     */
    logout = () => {
        this.setUserSession({})
        localStorage.removeItem('user')
    }
    /**
     * Log the user in by calling the react dispatcher and by adding a session storage cookie ( for persistence after refresh) 
     */
    login = user => {
        this.setUserSession(user)
        localStorage.setItem('user', JSON.stringify(user))
    }
}
export const SessionContext = React.createContext({user:{}})
