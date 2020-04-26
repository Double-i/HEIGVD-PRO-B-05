import * as React from 'react'
import ProfilForm from "./ProfilForm";
import {useContext, useState, useEffect} from "react";
import {SessionContext} from "../../common/SessionHelper";
import {sendEzApiRequest} from "../../common/ApiHelper";

const USER_ENDPOINT = '/users/'

function EditProfilForm(props){
    const session = useContext(SessionContext);
    const username = session.session.getUserName();
    const editProfilEndpoint = USER_ENDPOINT + username;
    const [userInfo, setUserInfo] = useState({
        userFirstname: 'tamere',
        userLastname :'',
        userEmail:'',
        userAddress:'',
        userNpa :'',
        userDistrict:'',
        userCity:'',
        userCountry:''
    })

    const updateSession =  (info) => {
        console.log("EDIT PROFIL INFO ",info)
    }

    useEffect(() => {
        sendEzApiRequest(USER_ENDPOINT+ `${username}`, 'GET').then(result => {

            setUserInfo({
                userFirstname: result.firstName,
                userLastname : result.lastName,
                userEmail: result.email,
                userAddress: result.address.address,
                userNpa : result.address.postalCode,
                userDistrict: result.address.district,
                userCity: result.address.city.city,
                userCountry:result.address.city.country.country
            })

        }, error => {
            console.log(error)
        })
    }, [])


    return <>
        <h4>Edition du profil</h4>
        <ProfilForm editProfil={true} initialValues={userInfo} endpoint={editProfilEndpoint} afterEditCb={updateSession} />
        </>

}

export default EditProfilForm
