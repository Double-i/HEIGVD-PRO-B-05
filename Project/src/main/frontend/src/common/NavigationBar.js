import * as React from 'react'
import { Navbar, Nav, NavDropdown, NavItem } from 'react-bootstrap'
import { Link } from 'react-router-dom'
import { SessionContext } from './SessionHelper'

function NavigationBar(props) {
    return (
        <Navbar bg="light" expand="lg">
            <Link to="/home">
                <Navbar.Brand to="/home"> EasyToolz</Navbar.Brand>
            </Link>
            <Navbar.Toggle aria-controls="basic-navbar-nav" />
            <Navbar.Collapse id="basic-navbar-nav">
                <SessionContext.Consumer>
                    {({ isLogin }) => {
                        if (isLogin()) {
                            return (
                                <Nav className="mr-auto">
                                    <Link to="/DashBoard" className="nav-link">
                                        <NavItem>DashBoard</NavItem>
                                    </Link>
                                </Nav>
                            )
                        } else {
                        }
                    }}
                </SessionContext.Consumer>
                <Nav className="ml-auto">
                    <SessionContext.Consumer>
                        {({ isLogin, getUserName, logout }) => {
                            if (isLogin()) {
                                return (
                                    <React.Fragment>
                                        <NavDropdown
                                            title={getUserName()}
                                            id="basic-nav-dropdown"
                                        >
                                            <NavDropdown.Item>
                                                <Link to="/EditProfil">
                                                    Edit profil
                                                </Link>
                                            </NavDropdown.Item>
                                            <NavDropdown.Divider />
                                            <NavDropdown.Item>
                                                <Link onClick={logout}>
                                                    Deconnexion
                                                </Link>
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
