import * as React from 'react'
import ProfilForm from "../userDashboard/editProfil/ProfilForm";
import {Container} from "react-bootstrap";
import {withRouter} from 'react-router-dom'

// EzApi Endpoint for signup
const SIGNUP_ENDPOINT = '/signup'

/**
 *
 * @param props, no props
 * @returns {React.Component}
 * @constructor
 */
function SignUp(props){

    // Default value. Use to fill the signup form
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
    /**
     * Use to redirect the user after the signup has been done.
     */
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
