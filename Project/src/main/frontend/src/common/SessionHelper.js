import React from 'react'
/*
class SessionHelper {
    SessionHelper() {
        console.log('Create user session')
        this.userLogged = false
        this.admin = false
    }
    signIn(userInfo) {
        console.log('Log user in')
        Object.keys(userInfo).map(key => {
            this[key] = userInfo[key]
        })
    }
    signOut() {
        console.log('Log user out')
        delete this
    }
    isUserLogged() {
        return this.userLogged //TODO
    }
    isUserAdmin() {
        return this.admin
    }
    setUserLogged(value) {
        this.userLogged = value
    }
}
*/
export const SessionContext = React.createContext({user:{}})
