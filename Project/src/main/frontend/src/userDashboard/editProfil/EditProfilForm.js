import * as React from 'react'
import ProfilForm from "./ProfilForm";
import {useContext, useState, useEffect} from "react";
import {Button} from "react-bootstrap";
import {SessionContext} from "../../common/SessionHelper";
import {sendEzApiRequest} from "../../common/ApiHelper";
import {Link} from 'react-router-dom'
import PasswordForm from "./PasswordForm";
const USER_ENDPOINT = '/users/'


function EditProfilForm(props) {
    const [showEditProfil,setShowEditProfil] = useState(true)
    const session = useContext(SessionContext);
    const username = session.session.getUserName();
    const editProfilEndpoint = USER_ENDPOINT + username;
    const [userInfo, setUserInfo] = useState({
        userFirstname: 'tamere',
        userLastname: '',
        userEmail: '',
        userAddress: '',
        userNpa: '',
        userDistrict: '',
        userCity: '',
        userCountry: ''
    })

    const updateSession = (info) => {
        // TODO mettre à jour présent dans la session pour être cohérent avec les modifications.
        console.log("EDIT PROFIL INFO ", info)
        session.session.update(info)
    }

    useEffect(() => {
        sendEzApiRequest(USER_ENDPOINT + `${username}`, 'GET').then(result => {

            setUserInfo({
                userFirstname: result.firstName,
                userLastname: result.lastName,
                userEmail: result.email,
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
        <br />
        <Button variant={"outline-primary"} onClick={() => setShowEditProfil(!showEditProfil) }>{showEditProfil ? "Editer mot de passe": "Retour"}  </Button>
        <br />
        <br />
        {showEditProfil ?
            <>
                <h4>Edition du profil</h4>
                <ProfilForm editProfil={true} initialValues={userInfo} endpoint={editProfilEndpoint}
                            afterEditCb={updateSession}/>
                </>
                        :
            <>
                <h4>Modifier votre mot de passe</h4>
                <PasswordForm />
            </>
        }

    </>

}

export default EditProfilForm
