import React from 'react'
import { useState } from 'react'
import 'bootstrap/dist/css/bootstrap.min.css'
import './App.css'
import { Container } from 'react-bootstrap'
import NavigationBar from './common/NavigationBar.js'
import { BrowserRouter as Router, Switch, Route } from 'react-router-dom'
import Home from './Home/Home'
import DashBoard from './userDashboard/DashBoard'
import SignUp from './signUp/SignUp'
import SignIn from './signIn/SignIn'
import LoanTable from './userDashboard/loanManagement/LoanTable'

import { SessionContext, SessionHelper } from './common/SessionHelper'

function App() {

    const userStorage = sessionStorage.getItem('user')
    const userObject = userStorage === null ? {} : JSON.parse(userStorage)
    const [showSignInForm, setShowSignInForm] = useState(false)
    const [userSession, setUserSession] = useState(userObject)

    const session = new SessionHelper(userSession, setUserSession)

    const user = {
        userInfo: userSession,
        session: session
    }

    return (
        <SessionContext.Provider value={user}>
            <Router>
                <NavigationBar showSignInForm={() => setShowSignInForm(true)} />
                <SignIn
                    showSignInForm={showSignInForm}
                    setShowSignInForm={value => setShowSignInForm(value)}
                    setLoggedUser={user.session.login}
                />
                <div className="row">
                    <Container>
                        <Switch>
                            <Route exact path="/home">
                                <Home />
                                <LoanTable/>
                            </Route>
                            <Route exact path="/dashboard">
                                {user.session.isUserLogin() ? (
                                    <DashBoard />
                                ) : (
                                    <NotRigthToBeHere />
                                )}
                            </Route>
                            <Route exact path="/disconnect"></Route>
                            <Route exact path="/signup">
                                {user.session.isUserLogin() ? (
                                    <AlreadyConnect />
                                ) : (
                                    <SignUp />
                                )}
                            </Route>
                        </Switch>
                    </Container>
                </div>
            </Router>
        </SessionContext.Provider>
    )
}
function NotRigthToBeHere() {
    return <p> Hey ho biquette ouste ! <img alt="biquette" src="https://images2.minutemediacdn.com/image/upload/c_crop,h_843,w_1500,x_0,y_10/f_auto,q_auto,w_1100/v1555172614/shape/mentalfloss/iStock-177369626_1.jpg" /> </p>
}
function AlreadyConnect() {
    return <p>Vous êtes déjà connecté... <img alt="retarded" src="https://i.imgflip.com/2e1lxv.jpg" /></p>
}
export default App 
