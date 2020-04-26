import React, {useState} from 'react'
import 'bootstrap/dist/css/bootstrap.min.css'
import {Form, Button, Spinner, Alert} from 'react-bootstrap'
import {Formik} from 'formik'
import * as yup from 'yup'
import regex from '../../common/regex'
import {checkAddress} from '../../common/GoogleApiHelper'
import {sendEzApiRequest, sendRequest} from '../../common/ApiHelper'

// TODO use it when signup
/*{
    userName: '',
    userEmail: '',
    userPassword: '',
    userPasswordRepeat: '',
    userFirstname: '',
    userLastname: '',
    userAddress: '',
    userNpa: '',
    userDistrict: '',
    userCity: '',
    userCountry: '',
}*/
function ProfilForm(props) {

    const [isSigningUp, setIsSigningUp] = useState(false)
    const [hasConnectionProblem, setHasConnectionProblem] = useState(false)
    const [hasBeenSignedUp, setHasBeenSignedUp] = useState(false)
    const [isValidAddress, setIsValidAddress] = useState(true)

    const attemptSignUp = values => {
        checkAddress({
            address: values.userAddress,
            npa: values.userNpa,
            city: values.userCity,
            country: values.userCountry,
        }).then(([isValid, googleAddress]) => {
            if (isValid === 1) {
                setIsValidAddress(true);

                let verb = "PUT";

                const profilData = {
                    firstName: values.userFirstname,
                    lastName: values.userLastname,
                    email: values.userEmail,
                    address: {
                        address: `${googleAddress.street} ${googleAddress.streetNumber}`,
                        district: values.userDistrict,
                        postalCode: googleAddress.zipCode,
                        lat: googleAddress.lat,
                        lng: googleAddress.long,
                        city: {
                            city: googleAddress.city,
                            country: {
                                country: googleAddress.country
                            }
                        }
                    }
                }
                if (!props.editProfil) {
                    verb = "POST"
                    profilData.password = values.userPassword
                    profilData.userName = values.userName
                }

                sendEzApiRequest(props.endpoint, verb, profilData).then(
                    result => {
                        // en principe seul les requete http 200 passe ici
                        console.log('Connection ok et réponse du serveur, ' + values)

                        props.afterEditCb(values)

                        setIsSigningUp(false)
                        setHasBeenSignedUp(true)
                    },
                    error => {
                        console.log(error);
                        // en principe requete à problemes ici => code http 400 & 500 ou pas de co au serveur
                        setHasConnectionProblem(true)
                        setIsSigningUp(false)
                    }
                );
            } else {
                setIsValidAddress(false);
            }
        });
    }
    const schemaSpec = {

        userEmail: yup
            .string()
            .required('Requis')
            .email(),
        userFirstname: yup
            .string()
            .required('Requis')
            .min(2)
            .max(20)
            .matches(regex.validName),
        userLastname: yup
            .string()
            .required('Requis')
            .min(2)
            .max(20)
            .matches(regex.validName),

        userAddress: yup
            .string()
            .required('Requis')
            .min(2)
            .max(50),
        userNpa: yup
            .string()
            .required('Requis')
            .min(2)
            .max(20),
        userDistrict: yup
            .string()
            .required('Requis'),
        userCity: yup
            .string()
            .required('Requis')
            .min(2)
            .max(50),
        userCountry: yup
            .string()
            .required('Requis')
            .min(2)
            .max(50),
    }
    // If the form is used for signup we add spec for username and password
    // which are not needed for editing profil
    if (!props.editProfil) {

        schemaSpec.userName = yup
            .string()
            .required('Requis')

        schemaSpec.userPassword = yup
            .string()
            .required('Requis')
            .min(8)
            .max(32)

        schemaSpec.userPasswordRepeat = yup
            .string()
            .required('Requis')
            .oneOf(
                [yup.ref('userPassword'), null],
                'Les mots de passe doivent être identiques'
            )
    }

    console.log("teststestestest",props.initialValues)

    // validation rules
    const schema = yup.object(schemaSpec)

    return (
        <>
            <Alert variant="success" hidden={!hasBeenSignedUp}>
                Edition du profil enregistrée
            </Alert>
            <Alert variant="danger" hidden={!hasConnectionProblem}>
                hum humm.. problème de connexion au serveur
            </Alert>
            <Alert variant="warning" hidden={isValidAddress}>
                L'adresse semble incorrecte ou peu précise veuillez complétez
            </Alert>
            <Formik
                validationSchema={schema}
                onSubmit={(values, {setSubmitting}) => {
                    setIsSigningUp(!isSigningUp)
                    attemptSignUp(values)
                }}
                enableReinitialize
                initialValues={props.initialValues}

            >
                {({
                      handleSubmit,
                      handleChange,
                      touched,
                      isValid,
                      values,
                      errors,}) => (
                <Form noValidate onSubmit={handleSubmit}>
                    {props.editProfil === false &&
                    <Form.Group controlId="formUsername">
                        <Form.Label>Nom d'utilisateur</Form.Label>
                        <Form.Control
                            name="userName"
                            type="text"
                            placeholder="Nom d'utilisateur"
                            value={values.userName}
                            onChange={handleChange}
                            isValid={touched.userName && !errors.userName}
                        />
                    </Form.Group>

                    }

                    <Form.Group controlId="formEmail">
                        <Form.Label>E-mail</Form.Label>
                        <Form.Control
                            name="userEmail"
                            type="email"
                            placeholder="E-mail"
                            value={values.userEmail}
                            onChange={handleChange}
                            isValid={touched.userEmail && !errors.userEmail}
                        />
                    </Form.Group>
                    <Form.Group controlId="formFirstname">
                        <Form.Label>Prénom</Form.Label>
                        <Form.Control
                            name="userFirstname"
                            type="text"
                            placeholder="Prénom"
                            value={values.userFirstname}
                            onChange={handleChange}
                            isValid={
                                touched.userFirstname &&
                                !errors.userFirstname
                            }
                        />
                    </Form.Group>
                    <Form.Group controlId="formLastname">
                        <Form.Label>Nom</Form.Label>
                        <Form.Control
                            name="userLastname"
                            type="text"
                            placeholder="Nom"
                            value={values.userLastname}
                            onChange={handleChange}
                            isValid={
                                touched.userLastname && !errors.userLastname
                            }
                        />
                    </Form.Group>
                    {/*If we edit profil we don't need password and repeated password*/}
                    {props.editProfil === false &&
                        <>
                    <Form.Group controlId="formPassword">
                        <Form.Label>Mot de passe</Form.Label>
                        <Form.Control
                            name="userPassword"
                            type="password"
                            placeholder="Mot de passe"
                            value={values.userPassword}
                            onChange={handleChange}
                            isValid={
                                touched.userPassword && !errors.userPassword
                            }
                        />
                    </Form.Group>

                    <Form.Group controlId="formPasswordRepeat">
                        <Form.Label>
                            mot de passe encore une fois
                        </Form.Label>
                        <Form.Control
                            name="userPasswordRepeat"
                            type="password"
                            placeholder="Retappez le mot de passe"
                            value={values.userPasswordRepeat}
                            onChange={handleChange}
                            isValid={
                                touched.userPasswordRepeat &&
                                !errors.userPasswordRepeat
                            }
                        />
                    </Form.Group>
                    </>
                    }
                    <Form.Group controlId="formAddress">
                        <Form.Label>Adresse</Form.Label>
                        <Form.Control
                            name="userAddress"
                            type="text"
                            placeholder="Adresse"
                            value={values.userAddress}
                            onChange={handleChange}
                            isValid={
                                touched.userAddress && !errors.userAddress
                            }
                        />
                    </Form.Group>
                    <Form.Group controlId="formNpa">
                        <Form.Label>Code postal</Form.Label>
                        <Form.Control
                            name="userNpa"
                            type="text"
                            placeholder="Adresse"
                            value={values.userNpa}
                            onChange={handleChange}
                            isValid={touched.userNpa && !errors.userNpa}
                        />
                    </Form.Group>
                    <Form.Group controlId="formDistrict">
                        <Form.Label>District</Form.Label>
                        <Form.Control
                            name="userDistrict"
                            type="text"
                            placeholder="District"
                            value={values.userDistrict}
                            onChange={handleChange}
                            isValid={touched.userDistrict && !errors.userDistrict}
                        />
                    </Form.Group>
                    <Form.Group controlId="formCity">
                        <Form.Label>Ville/village</Form.Label>
                        <Form.Control
                            name="userCity"
                            type="text"
                            placeholder="Ville"
                            value={values.userCity}
                            onChange={handleChange}
                            isValid={touched.userCity && !errors.userCity}
                        />
                    </Form.Group>
                    <Form.Group controlId="formCountry">
                        <Form.Label>Pays</Form.Label>
                        <Form.Control
                            name="userCountry"
                            type="text"
                            placeholder="Pays"
                            value={values.userCountry}
                            onChange={handleChange}
                            isValid={
                                touched.userCountry && !errors.userCountry
                            }
                        />
                    </Form.Group>

                    <Button
                        variant="primary"
                        type="submit"
                        size="lg"
                        block
                        disabled={isSigningUp}
                    >
                        {props.editProfil ? "Modifier" :"S'inscrire"}
                        <Spinner
                            as="span"
                            animation="border"
                            size="sm"
                            role="status"
                            aria-hidden="true"
                            hidden={!isSigningUp}
                        />
                    </Button>
                </Form>)}
            </Formik>
        </>
    )
}

export default ProfilForm
