import React from 'react'
import * as moment from 'moment'
import {sendEzApiRequest} from "./ApiHelper";

// Session duration in miliseconds should be the same that the server
export const SESSION_DURATION = 1000000
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
            (!this.isExpired() && Object.keys(this.userSession).length > 0 &&
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
     * Return the email of the user stored in userSession Object
     */
    getUserEmail = () => {
        return this.userSession.email
    }
    /**
     * Log the user out by calling the react dispatcher and by removing the session storage cookie
     */
    logout = () => {
        sendEzApiRequest('/logout', 'GET').then( result=> {
            console.log(result)
        }, error => {
            console.log(error)
        })
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

    update = info => {
        const newUserInfo = {...this.userSession}
        if(info.userFirstname !== 'undefined') newUserInfo.firstname =  info.userFirstname
        if(info.userLastname !== 'undefined') newUserInfo.lastname = info.userLastname
        this.setUserSession(newUserInfo)

        //localStorage.setItem('user', JSON.stringify(newUserInfo))
    }
    isExpired = () => {
        return !(moment(this.userSession.tokenDuration).isAfter(moment()))
    }
    getExpirationDate = ()=> {
        return this.userSession.tokenDuration;
    }
}
export const SessionContext = React.createContext({user:{}})
