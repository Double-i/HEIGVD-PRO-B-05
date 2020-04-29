import * as React from 'react'
import ProfilForm from "../userDashboard/editProfil/ProfilForm";
import {useContext, useState, useEffect} from "react";

const SIGNUP_ENDPOINT = '/signup'
function SignUp(props){

    const userInfo = {
        userFirstname: '',
        userLastname :'',
        userEmail:'',
        userAddress:'',
        userNpa :'',
        userDistrict:'',
        userCity:'',
        userCountry:''
    }

    return <>
        <h4>Inscription</h4>
        <ProfilForm editProfil={false} initialValues={userInfo} endpoint={SIGNUP_ENDPOINT} afterEditCb={()=>{
        console.log("signup")}
        } />
    </>

}

export default SignUp
