import React from 'react';
import 'bootstrap/dist/css/bootstrap.min.css';
import './App.css';

import NavigationBar from './common/NavigationBar.js';
import {
  BrowserRouter as Router,
  Switch,
  Route
} from "react-router-dom";

import Home from "./Home/Home";
import DashBoard from "./userDashboard/DashBoard";
import SignUp from "./signUp/SignUp";
import SignIn from "./signIn/SignIn";

function App() {
  return (
      <Router>
        <NavigationBar />
        <div className="row">
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

              <div className="container">
                  <div className="mx-auto w-50 p-3 text-center">
                      <SignUp />
                  </div>
              </div>

            </Route>

            <Route path="/SignIn">
              <SignIn />
            </Route>
          </Switch>
        </div>
      </Router>

  );
}

export default App;
