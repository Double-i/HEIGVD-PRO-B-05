import React from 'react'
import {useState} from 'react'
import './App.css'
import {Container} from 'react-bootstrap'
import NavigationBar from './common/NavigationBar.js'
import {
    BrowserRouter as Router,
    Switch,
    Route,
    useParams,
} from 'react-router-dom'
import Home from './Home/Home'
import DashBoard from './userDashboard/DashBoard'
import SignUp from './signUp/SignUp'
import SignIn from './signIn/SignIn'
import BorrowerLoans from './userDashboard/loanManagement/BorrowerLoans'
import OwnerLoans from './userDashboard/loanManagement/OwnerLoans'
import AddToolsForm from "./userDashboard/addTools/AddToolsForm";

import {SessionContext, SessionHelper} from './common/SessionHelper'

function App() {
    const userStorage = localStorage.getItem('user')
    const userObject = userStorage === null ? {} : JSON.parse(userStorage)
    const [showSignInForm, setShowSignInForm] = useState(false)
    const [userSession, setUserSession] = useState(userObject)

    const session = new SessionHelper(userSession, setUserSession)

    const user = {
        userInfo: userSession,
        session: session,
    }

    return (
        <SessionContext.Provider value={user}>
            <Router>
                <NavigationBar showSignInForm={() => setShowSignInForm(true)}/>
                <SignIn
                    showSignInForm={showSignInForm}
                    setShowSignInForm={(value) => setShowSignInForm(value)}
                    setLoggedUser={loginInfo => {
                        user.session.login(loginInfo)
                        setShowSignInForm(false)
                    }}
                />
                <div className="row">
                    <Container>
                        <Switch>
                            <Route exact path="/(home|accueil|)/">
                                <Home/>
                            </Route>
                            <Route exact path="/dashboard">
                                {user.session.isUserLogin() ? (
                                    <DashBoard/>
                                ) : (
                                    <NotRigthToBeHere/>
                                )}
                            </Route>
                            <Route exact path="/disconnect"></Route>
                            <Route exact path="/signup">
                                {user.session.isUserLogin() ? (
                                    <AlreadyConnect/>
                                ) : (
                                    <SignUp/>
                                )}
                            </Route>
                            <Route exact path="/tools/:toolId">
                                <TmpToolDetails/>
                            </Route>
                            <Route exacte path="/dashboard/myloans/borrower">
                                {user.session.isUserLogin() ? (
                                    <BorrowerLoans/>
                                ) : (
                                    <NotRigthToBeHere/>
                                )}

                            </Route>
                            <Route exacte path="/dashboard/addTool">
                                {user.session.isUserLogin() ? (
                                    <AddToolsForm/>
                                ) : (
                                    <NotRigthToBeHere/>
                                )}

                            </Route>
                            <Route exacte path="/dashboard/myloans/owner">
                                {user.session.isUserLogin() ? (
                                    <OwnerLoans/>
                                ) : (
                                    <NotRigthToBeHere/>
                                )}

                            </Route>

                            <Route component={UnkownPage}/>
                        </Switch>
                    </Container>
                </div>
            </Router>
        </SessionContext.Provider>
    )
}

function TmpToolDetails() {
    let {toolId} = useParams()
    return <h1> Affichage de l'outil id: {toolId}</h1>
}

function NotRigthToBeHere() {
    return (
        <Container className={"col-md-6 col-md-offset-3"}>
            <br/>

            <p>
                <h3>Vous n'avez pas le droit d'accéder à cette page.</h3>
                <img
                    alt="forbideen"
                    src="/forbidden.jpg"
                />
            </p>
        </Container>
    )
}

function AlreadyConnect() {
    return (
        <Container className={"col-md-6 col-md-offset-3"}>
            <br/>
            <p>
                <h3> Vous êtes déjà connecté. Vous ne pouvez donc pas accéder à cette page.</h3>
                <img
                    alt="forbideen"
                    src="/useless.png"
                />
            </p>
        </Container>
    )
}

function UnkownPage() {
    return (
        <Container className={"col-md-6 col-md-offset-3"}>
            <br/>
            <p>
                <h3>Cette page n'existe pas</h3>
                <img
                    alt="forbideen"
                    src="/404.jpg"
                />
            </p>
        </Container>
    )
}

export default App
