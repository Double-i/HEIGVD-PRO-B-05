import * as React from 'react'
import {Navbar, Nav, NavDropdown, NavItem, Container, Media} from 'react-bootstrap'
import { Link } from 'react-router-dom'
import { SessionContext } from './SessionHelper'
import NotificationDropdown from "./notification/NotificationDropdown";

function NavigationBar(props) {
    return (
        <Navbar bg="light" expand="lg">
            <Link to="/home">
                <Navbar.Brand to="/home"> EasyToolz</Navbar.Brand>
            </Link>
            <Navbar.Toggle aria-controls="basic-navbar-nav" />
            <Navbar.Collapse id="basic-navbar-nav">
                <SessionContext.Consumer>
                    {({ session }) => {
                        if (session.isUserLogin()) {
                            return (
                                <>
                                <Nav className="mr-auto">
                                    <Link to="/DashBoard" className="nav-link">
                                        <NavItem>DashBoard</NavItem>
                                    </Link>
                                    <NotificationDropdown />
                                </Nav>
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
                                                to="/EditProfil"
                                            >
                                                Edit profil
                                            </Link>

                                            <NavDropdown.Divider />
                                            <NavDropdown.Item onClick={session.logout}>
                                                Deconnexion
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
                                            Sign in
                                        </Nav.Link>

                                        <Link to="/signup" className="nav-link">
                                            <NavItem>Sign up</NavItem>
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
export default NavigationBar
