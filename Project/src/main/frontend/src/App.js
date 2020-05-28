import React, {useState} from 'react'
import './App.css'
import {Container} from 'react-bootstrap'
import NavigationBar from './common/NavigationBar.js'
import {BrowserRouter as Router, Route, Switch} from 'react-router-dom'
import Home from './Home/Home'
import DashBoard from './userDashboard/DashBoard'
import SignUp from './signUp/SignUp'
import SignIn from './signIn/SignIn'
import Map from './searchTools/map'
import ToolDetails from './searchTools/toolDetails'
import AdminPage from "./admin/AdminPage";
import UserToolsList from "./userDashboard/userTools/UserToolsList";
import ConversationList from "./WebChat/ConversationList";
import * as moment from 'moment'
import {SESSION_DURATION, SessionContext, SessionHelper} from './common/SessionHelper'
import EditProfilForm from "./userDashboard/editProfil/EditProfilForm";
import {sendEzApiRequest} from "./common/ApiHelper";
import SearchTools from "./searchTools/searchTools";


import ToolForm from "./toolsUtil/toolForm";
import LoansManagement from "./userDashboard/loanManagement/LoansManagement";
import AnonymousHome from "./Home/AnonymousHome";

const SESSION_REFRESH_ENDPOINT = "/authrefresh"

/**
 * App is the component React containing all the others pages / React Components.
 *
 * @returns {*}
 * @constructor
 */
function App() {
    const userStorage = localStorage.getItem('user')
    const userObject = userStorage === null ? {} : JSON.parse(userStorage)

    const [showSignInForm, setShowSignInForm] = useState(false)
    const [userSession, setUserSession] = useState(userObject)

    const session = new SessionHelper(userSession, setUserSession)

    // If the user is logged in, we set a timeout to send a request to refresh the token
    if (session.isUserLogin()) {

        const expirationMoment = moment(session.getExpirationDate()).subtract(3, "minutes")
        const diff = moment.duration(expirationMoment.diff(moment()));

        // Check that the diff is less than
        const duration = diff.asMilliseconds() < 0 ? 3000 : diff.asMilliseconds();

        // If the session hasn't yet expired we add a timeout to refresh the token right before expiration
        setTimeout(() => {
            const refreshTokenRequest = () => sendEzApiRequest(SESSION_REFRESH_ENDPOINT, 'GET').then(result => {
                session.login({
                    tokenDuration: result.tokenDuration,
                    username: result.user.userName,
                    admin: result.user.admin,
                    lastname: result.user.lastName,
                    firstname: result.user.firstName,
                    address: result.user.address
                })
                setTimeout(refreshTokenRequest, SESSION_DURATION)
            }, error => {
                console.log("Refresh token error: ", error)
            })

            refreshTokenRequest()

        }, duration)

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
                        window.location.replace("/home")
                    }}
                />
                <Container style={{marginTop: "20px"}}>
                    <Switch>
                        {/*The home page can be the reach with http://<DOMAIN>/home, http://<DOMAIN>/accueil or http://<DOMAIN>/  */}
                        <Route exact path="/(home|accueil|)/">
                            {user.session.isUserLogin() ? (
                                <Home/>
                            ) : (
                                <AnonymousHome/>
                            )}
                        </Route>
                        <Route exact path="/dashboard">
                            {user.session.isUserLogin() ? (
                                <DashBoard/>
                            ) : (
                                <NotRightToBeHere/>
                            )}
                        </Route>
                        <Route exact path="/signup">
                            {user.session.isUserLogin() ? (
                                <AlreadyConnect/>
                            ) : (
                                <SignUp/>
                            )}
                        </Route>
                        <Route exact path="/map" component={Map}/>
                        <Route exact path="/toolDetails/:id" component={ToolDetails}/>
                        <Route exact path="/searchTools">
                            <SearchTools/>
                        </Route>
                        <Route exact path="/dashboard/myloans/borrower">
                            {user.session.isUserLogin() ? (
                                <LoansManagement borrower={true}/>
                            ) : (
                                <NotRightToBeHere/>
                            )}
                        </Route>
                        <Route exact path="/dashboard/myloans/owner">
                            {user.session.isUserLogin() ? (
                                <LoansManagement borrower={false}/>
                            ) : (
                                <NotRightToBeHere/>
                            )}
                        </Route>
                        <Route exact path="/dashboard/addTool">
                            {user.session.isUserLogin() ? (
                                <ToolForm
                                    tool={null}
                                    formTitle={"Ajouter"}
                                    action={"add"}
                                />
                            ) : (
                                <NotRightToBeHere/>
                            )}
                        </Route>
                        <Route exact path="/dashboard/toolList">
                            {user.session.isUserLogin() ? (
                                <UserToolsList/>
                            ) : (
                                <NotRightToBeHere/>
                            )}
                        </Route>


                        <Route exact path="/dashboard/profil">
                            {user.session.isUserLogin() ? (
                                <EditProfilForm/>
                            ) : (
                                <NotRightToBeHere/>
                            )}
                        </Route>

                        <Route exact path="/AdminPage">
                            {user.session.isUserAdmin() ? (
                                <AdminPage/>
                            ) : (
                                <NotRightToBeHere/>
                            )}
                        </Route>

                        <Route exact path="/dashboard/profil/password">
                            {user.session.isUserLogin() ? (
                                <EditProfilForm/>
                            ) : (
                                <NotRightToBeHere/>
                            )}
                        </Route>

                        <Route component={UnkownPage}/>
                    </Switch>
                </Container>


            </Router>
            {/* Show conversation only if the user is logged in */}
            {user.session.isUserLogin() && <ConversationList currentConnected={user}/> }
        </SessionContext.Provider>
    )
}

/**
 * React component representing the page to show when the user's trying to access a page which cannot be access while
 * he's an anonymous user (not logged in)
 * @returns {*}
 * @constructor
 */
function NotRightToBeHere() {
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

/**
 * React component representing the page to show when the user access a page which cannot be displayed while logged in.
 * (Sign up for example)
 * @returns {React.Component}
 */
function AlreadyConnect() {
    return (
        <Container className={"col-md-6 col-md-offset-3"}>
            <br/>
            <div>
                <h3> Vous êtes déjà connecté. Vous ne pouvez donc pas accéder à cette page.</h3>
                <img
                    alt="forbideen"
                    src="/useless.png"
                />
            </div>
        </Container>
    )
}

/**
 * React component representing the 404 page
 * @returns {React.Component}
 */
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
