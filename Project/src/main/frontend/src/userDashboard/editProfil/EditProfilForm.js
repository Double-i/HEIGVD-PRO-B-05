import * as React from 'react'
import {useContext, useEffect, useState} from 'react'
import ProfilForm from "./ProfilForm";
import {Button} from "react-bootstrap";
import {SessionContext} from "../../common/SessionHelper";
import {sendEzApiRequest} from "../../common/ApiHelper";
import PasswordForm from "./PasswordForm";
import {formatString} from "../../common/Utils";

const USER_ENDPOINT = '/users/{0}'

/**
 * Edit profil form.
 * @param props, no props
 * @returns {React.Component}
 * @constructor
 */
function EditProfilForm(props) {
    // This state is used to switch between editProfil form and the change password form
    const [showEditProfil, setShowEditProfil] = useState(true)

    const session = useContext(SessionContext);
    const username = session.session.getUserName();

    const editProfilEndpoint = formatString(USER_ENDPOINT, username)

    // Default user info to fill the form. It's only used before the request to get user info has been received
    // or the request failed
    const [userInfo, setUserInfo] = useState({
        userFirstname: '',
        userLastname: '',
        userEmail: '',
        userAddressId: '',
        userAddress: '',
        userNpa: '',
        userDistrict: '',
        userCity: '',
        userCountry: ''
    })

    const updateSession = (info) => {
        // we update the session info with the new one
        session.session.update(info)
    }

    useEffect(() => {

        // Send a request to get the user informations and fill the form with them
        sendEzApiRequest(formatString(USER_ENDPOINT, username), 'GET').then(result => {

            setUserInfo({
                userFirstname: result.firstName,
                userLastname: result.lastName,
                userEmail: result.email,
                userAddressId: result.address.id,
                userAddress: result.address.address,
                userNpa: result.address.postalCode,
                userDistrict: result.address.district,
                userCity: result.address.city.city,
                userCountry: result.address.city.country.country
            })

        }, error => {
            console.log(error)
        })
    }, [])


    return <>
        <br/>
        <Button variant={"outline-primary"}
                onClick={() => setShowEditProfil(!showEditProfil)}>{showEditProfil ? "Editer mot de passe" : "Retour"}  </Button>
        <br/>
        <br/>
        {showEditProfil ?
            <>
                <h4>Edition du profil</h4>
                <ProfilForm editProfil={true} initialValues={userInfo} endpoint={editProfilEndpoint}
                            afterEditCb={updateSession}/>
            </>
            :
            <>
                <h4>Modifier votre mot de passe</h4>
                <PasswordForm/>
            </>
        }

    </>

}

export default EditProfilForm
