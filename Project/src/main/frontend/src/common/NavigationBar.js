import * as React from "react";
import {
    BrowserRouter as Router,
    Switch,
    Route
} from "react-router-dom";
import { Button, Navbar, Nav, NavDropdown } from 'react-bootstrap';

import DashBoard from "../userDashboard/DashBoard";
import Home from "../Home/Home";
import SignIn from "../signIn/SignIn";
import SignUp from "../signUp/SignUp";


class NavigationBar extends React.Component {
    render() {
        return (
            <Router>
                <Navbar bg="light" expand="lg">
                    <Navbar.Brand href="/home">EasyToolz</Navbar.Brand>
                    <Navbar.Toggle aria-controls="basic-navbar-nav" />
                    <Navbar.Collapse id="basic-navbar-nav">
                        <Nav className="mr-auto">
                            <Nav.Link href="/DashBoard">DashBoard</Nav.Link>
                        </Nav>
                        <Nav className="ml-auto">
                            <Nav.Link href="/SignIn">Sign in</Nav.Link>
                            <Nav.Link href="/SignUp">Sign up</Nav.Link>
                            <NavDropdown title="UserName" id="basic-nav-dropdown">
                                <NavDropdown.Item href="/Something">Something</NavDropdown.Item>
                                <NavDropdown.Divider />
                                <NavDropdown.Item href="/Disconnect">Deconnexion</NavDropdown.Item>
                            </NavDropdown>
                        </Nav>
                    </Navbar.Collapse>
                </Navbar>

                <Switch>
                    <Route path="/Home">
                        <Home />
                    </Route>

                    <Route path="/DashBoard">
                        <DashBoard />
                    </Route>

                    <Route path="/Disconnect">

                    </Route>

                    <Route path="/SignUp">
                        <SignUp />
                    </Route>

                    <Route path="/SignIn">
                        <SignIn />
                    </Route>
                </Switch>
            </Router>
        )
    }
}

export default NavigationBar;