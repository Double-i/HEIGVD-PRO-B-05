import * as React from "react";
import {
    BrowserRouter as Router,
    Switch,
    Route
} from "react-router-dom";
import {Navbar, Nav, NavDropdown } from 'react-bootstrap';

import DashBoard from "../userDashboard/DashBoard";
import Home from "../Home/Home";
import SignIn from "../signIn/SignIn";
import SignUp from "../signUp/SignUp";

import SessionHelper from "./SessionHelper";

class NavigationBar extends React.Component {
    render() {

        let sessionHelper = new SessionHelper();
        let signIn = null;
        let signUp = null;
        let dashboard = null;
        let userDropDown = null;


        if(sessionHelper.isUserLogged()) {
            dashboard = <Nav.Link href="/DashBoard">DashBoard</Nav.Link>;
            userDropDown =
                <NavDropdown title="UserName" id="basic-nav-dropdown">
                <NavDropdown.Item href="/EditProfil">Edit profil</NavDropdown.Item>
                <NavDropdown.Divider />
                <NavDropdown.Item href="/Disconnect">Deconnexion</NavDropdown.Item>
                </NavDropdown>;
        }else{
            signIn = <Nav.Link href="/SignIn">Sign in</Nav.Link>;
            signUp = <Nav.Link href="/SignUp">Sign up</Nav.Link>;
        }

        return (
            <Router>
                <Navbar bg="light" expand="lg">
                    <Navbar.Brand href="/home">EasyToolz</Navbar.Brand>
                    <Navbar.Toggle aria-controls="basic-navbar-nav" />
                    <Navbar.Collapse id="basic-navbar-nav">
                        <Nav className="mr-auto">
                            {dashboard}
                        </Nav>
                        <Nav className="ml-auto">
                            {signIn}
                            {signUp}
                            {userDropDown}
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