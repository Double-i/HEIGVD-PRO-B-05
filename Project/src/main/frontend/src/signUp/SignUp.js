import * as React from 'react'
import ProfilForm from "../userDashboard/editProfil/ProfilForm";
import {Container} from "react-bootstrap";
import {withRouter} from 'react-router-dom'

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
    const afterSignUp = () => {
        props.history.push("/home")
    }


    return <Container>
        <h4>Inscription</h4>
        <ProfilForm editProfil={false} initialValues={userInfo} endpoint={SIGNUP_ENDPOINT} afterEditCb={()=>{
            afterSignUp()
        }} />
    </Container>
}

export default withRouter(SignUp)
