import React from 'react'
import {useState} from 'react'
import './App.css'
import {Col, Container, NavDropdown, Row} from 'react-bootstrap'
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

import Map from './searchTools/map'
import ToolDetails from './searchTools/toolDetails'
import BorrowerLoans from './userDashboard/loanManagement/BorrowerLoans'
import OwnerLoans from './userDashboard/loanManagement/OwnerLoans'
import AdminPage from "./admin/AdminPage";
import UserToolsList from "./userDashboard/userTools/UserToolsList";

import ConversationList from "./WebChat/ConversationList";
import * as moment from 'moment'

import {SESSION_DURATION, SessionContext, SessionHelper} from './common/SessionHelper'
import EditProfilForm from "./userDashboard/editProfil/EditProfilForm";
import {sendEzApiRequest} from "./common/ApiHelper";
import SearchTools from "./searchTools/searchTools";


import ToolForm from "./toolsUtil/toolForm";

const SESSION_REFRESH_ENDPOINT = "/authrefresh"

function App() {
    const userStorage = localStorage.getItem('user')
    const userObject = userStorage === null ? {} : JSON.parse(userStorage)

    const [showSignInForm, setShowSignInForm] = useState(false)
    const [userSession, setUserSession] = useState(userObject)

    const session = new SessionHelper(userSession, setUserSession)

    // Log out the user if his session has expired
    if(session.isUserLogin()) {


        if (session.isExpired()) {
            session.logout()
        } else {
            const refreshMoment = moment(session.getExpirationDate()).subtract(3, "minutes")
            const duration = moment.duration(refreshMoment.diff(moment()));

            // If the session hasn't yet expired we add a timeout to refresh the token right before expiration
            // If the user quit the page session won't refresh but if the user stay enough time it will
            setTimeout(() => {
                const refreshTokenRequest = () => sendEzApiRequest(SESSION_REFRESH_ENDPOINT, 'GET').then(result => {
                    console.log("Refresh token response", result)
                    session.login({
                        tokenDuration: result.tokenDuration,
                        username: result.user.userName,
                        admin: result.user.admin,
                        lastname: result.user.lastName,
                        firstname: result.user.firstName,
                    })
                    setTimeout(refreshTokenRequest, SESSION_DURATION)
                }, error => {
                    console.log("Refresh token error: ", error)
                })
                refreshTokenRequest()

            }, duration.asMilliseconds())
        }
    }

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
                            <Route exact path="/map" component={Map}/>
                            <Route exact path="/toolDetails/:id" component = {ToolDetails}/>
                            <Route exact path="/searchTools">
                                <SearchTools/>
                            </Route>
                            <Route exact path="/tools/:toolId">
                                <TmpToolDetails/>
                            </Route>
                            <Route exact path="/dashboard/myloans/borrower">
                                {user.session.isUserLogin() ? (
                                    <BorrowerLoans/>
                                ) : (
                                    <NotRigthToBeHere/>
                                )}
                            </Route>
                            <Route exact path="/dashboard/addTool">
                                {user.session.isUserLogin() ? (
                                    <ToolForm
                                        tool = {null}
                                        formTitle={"Ajouter"}
                                        action={"add"}
                                    />
                                ) : (
                                    <NotRigthToBeHere/>
                                )}
                            </Route>
                            <Route exact path="/dashboard/toolList">
                                {user.session.isUserLogin() ? (
                                    <UserToolsList/>
                                ) : (
                                    <NotRigthToBeHere/>
                                )}
                            </Route>


                            <Route exact path="/dashboard/profil">
                                {user.session.isUserLogin() ? (
                                    <EditProfilForm />
                                ) : (
                                    <NotRigthToBeHere/>
                                )}
                            </Route>


                            <Route exact path="/dashboard/myloans/owner">
                                {user.session.isUserLogin() ? (
                                    <OwnerLoans/>
                                ) : (
                                    <NotRigthToBeHere/>
                                )}
                            </Route>
                            <Route exact path="/AdminPage">
                                {user.session.isUserAdmin() ? (
                                    <AdminPage/>
                                ) : (
                                    <NotRigthToBeHere/>
                                )}
                            </Route>

                            <Route exact path="/dashboard/profil/password">
                                {user.session.isUserLogin() ? (
                                    <EditProfilForm />
                                ) : (
                                    <NotRigthToBeHere/>
                                )}
                            </Route>

                            <Route component={UnkownPage} />
                        </Switch>
                    </Container>
                </div>

            </Router>

            <ConversationList currentConnected ={user}/>
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
