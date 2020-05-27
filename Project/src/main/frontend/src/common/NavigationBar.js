import * as React from 'react'
import {Navbar, Nav, NavDropdown, NavItem} from 'react-bootstrap'
import { Link, withRouter } from 'react-router-dom'
import { SessionContext } from './SessionHelper'
import NotificationDropdown from "./notification/NotificationDropdown";

/**
 * Navigation bar. Features button will display depends of the user status : logged, admin ...
 * @param props
 * @returns {*}
 */
function NavigationBar(props) {
    return (
        <Navbar bg="light" expand="lg">
            <Link to="/home">
                <Navbar.Brand to="/home"> EasyToolz</Navbar.Brand>
            </Link>
            <Navbar.Toggle aria-controls="basic-navbar-nav" />
            <Navbar.Collapse id="basic-navbar-nav">
                <Nav className="mr-auto">
                    <SessionContext.Consumer>
                        {({ session }) => {
                            if (session.isUserLogin()) {
                                return (
                                    <>

                                        <Link to="/DashBoard" className="nav-link">
                                            <NavItem>Tableau de bord</NavItem>
                                        </Link>
                                        <NotificationDropdown />

                                    </>
                                )
                            }
                        }}
                    </SessionContext.Consumer>
                    <SessionContext.Consumer>
                        {({ session }) => {
                            if(session.isUserAdmin()){
                                return (
                                    <Nav className="justify-content-end">
                                        <Link to="/AdminPage" className="nav-link">
                                            <NavItem>Admin</NavItem>
                                        </Link>
                                    </Nav>
                                )
                            }
                        }}
                    </SessionContext.Consumer>
                </Nav>
                <Nav className="ml-auto">
                    <SessionContext.Consumer>
                        {({ session }) => {
                            if (session.isUserLogin()) {
                                return (
                                    <React.Fragment>
                                        <NavDropdown
                                            title={session.getUserName()}
                                            id="basic-nav-dropdown"
                                            alignRight
                                        >
                                            <Link
                                                className="dropdown-item"
                                                to="/dashboard/profil"
                                            >
                                                Edition du profil
                                            </Link>

                                            <NavDropdown.Divider />
                                            <NavDropdown.Item onClick={()=>{
                                                session.logout()
                                                props.history.push("/home")

                                            }}>
                                                Déconnexion
                                            </NavDropdown.Item>
                                        </NavDropdown>
                                    </React.Fragment>
                                )
                            } else {
                                return (
                                    <React.Fragment>
                                        <Nav.Link
                                            onClick={() => {
                                                props.showSignInForm()
                                            }}
                                        >
                                            Connexion
                                        </Nav.Link>

                                        <Link to="/signup" className="nav-link">
                                            <NavItem>Inscription</NavItem>
                                        </Link>
                                    </React.Fragment>
                                )
                            }
                        }}
                    </SessionContext.Consumer>
                </Nav>
            </Navbar.Collapse>
        </Navbar>
    )
}
export default withRouter(NavigationBar)
